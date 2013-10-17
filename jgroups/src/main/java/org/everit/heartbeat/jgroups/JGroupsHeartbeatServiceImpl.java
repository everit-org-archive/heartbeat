package org.everit.heartbeat.jgroups;

/*
 * Copyright (c) 2013, Everit Kft.
 *
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import org.everit.heartbeat.api.HeartbeatService;
import org.everit.heartbeat.api.MessageListener;
import org.everit.heartbeat.api.node.Node;
import org.everit.heartbeat.api.node.NodeManager;
import org.everit.heartbeat.api.node.NodeMessage;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of {@link HeartbeatService} that sends and receives heartbeat messages via JGroups.
 * 
 * @see http://www.jgroups.org/
 * 
 * @param <NodeMessage>
 *            The type of the message that will be sent in the heartbeat message.
 */
public class JGroupsHeartbeatServiceImpl implements HeartbeatService<NodeMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JGroupsHeartbeatServiceImpl.class);
    private final NodeManager nodeManager;
    private JChannel channel;
    private long period = 1000;
    private NodeMessage ownMessage;
    private MessageListener messageListener = null;
    private String clusterName;
    private Timer timer;

    public JGroupsHeartbeatServiceImpl(final NodeManager nodeManager, final NodeMessage message,
            final String clusterName) {
        super();
        this.nodeManager = nodeManager;
        ownMessage = message;
        this.clusterName = clusterName;
    }

    private void messageSender() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message(null, ownMessage);
                try {
                    channel.send(msg);
                } catch (Exception e) {
                    LOGGER.error("Failed to send heartbeat message", e);
                }
            }
        }, 0, period);
    }

    @Override
    public void setMessage(final NodeMessage message) {
        ownMessage = message;
    }

    @Override
    public void setMessageListener(final MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public void setPeriod(final long period) {
        if (period <= 0) {
            throw new IllegalArgumentException();
        }
        this.period = period;
    }

    @Override
    public void start() {
        try {
            channel = new JChannel();
        } catch (Exception e) {
            LOGGER.error("Failed to create JChannel. " + e.getMessage(), e);
            throw new RuntimeException("Failed to create JChannel. " + e.getMessage(), e);
        }
        try {
            channel.connect(clusterName);
        } catch (Exception e) {
            LOGGER.error("Failed to connect to the Cluster. " + e.getMessage(), e);
            throw new RuntimeException("Failed to connect to the Cluster. " + e.getMessage(), e);
        }
        channel.setReceiver(new ReceiverAdapter() {
            @Override
            public void receive(final Message msg) {
                NodeMessage message = (NodeMessage) msg.getObject();

                Node nodeToAdd = new Node(InetSocketAddress.createUnresolved(message.getAddress(),
                        message.getPort()), System
                        .currentTimeMillis(), message.getGroupId());
                nodeManager.addNode(nodeToAdd);

                if (messageListener != null) {
                    messageListener.afterMessageReceived(msg);
                }

            }
        });
        messageSender();
    }

    @Override
    public void stop() {
        timer.cancel();
        channel.close();
    }
}
