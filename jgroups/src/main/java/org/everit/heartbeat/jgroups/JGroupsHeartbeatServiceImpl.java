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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.everit.heartbeat.api.HeartbeatService;
import org.everit.heartbeat.api.MessageListener;
import org.everit.heartbeat.api.node.Node;
import org.everit.heartbeat.api.node.NodeManager;
import org.everit.heartbeat.api.node.NodeMessage;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

/**
 * The implementation of {@link AbstractClusteringHeartbeatService} that sends and receives heartbeat messages via
 * JGroups.
 * 
 * @see http://www.jgroups.org/
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public class JGroupsHeartbeatServiceImpl implements HeartbeatService<NodeMessage> {

    private final NodeManager nodeManager;
    private JChannel channel;
    private long period = 1000;
    private NodeMessage ownMessage;
    private MessageListener messageListener;
    private boolean loopCond;

    public JGroupsHeartbeatServiceImpl(final NodeManager nodeManager, final NodeMessage message) {
        super();
        this.nodeManager = nodeManager;
        ownMessage = message;
    }

    public void eventLoop() {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (loopCond) {
                    try {
                        Message msg = new Message(null, ownMessage);
                        channel.send(msg);
                        // System.out.println(ownMessage + " says -> message sent: " + ownMessage);
                        Thread.sleep(period);
                    } catch (Exception e) {

                    }
                }
            }
        };
        t.start();
        /*
         * while (loopCond) { try { Message msg = new Message(null, ownMessage); channel.send(msg);
         * Thread.sleep(period); } catch (Exception e) {
         * 
         * } }
         */
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
            channel.connect("Cluster1");
            channel.setReceiver(new ReceiverAdapter() {
                @Override
                public void receive(final Message msg) {
                    NodeMessage message = (NodeMessage) msg.getObject();
                    try {
                        Node nodeToAdd = new Node(InetAddress.getByName(message.getAddress()), System
                                .currentTimeMillis(), message.getGroupId());
                        nodeManager.addNode(nodeToAdd);
                        // System.out.println(ownMessage + " says -> message recieved and added: " + nodeToAdd);

                        // messageListener.afterMessageReceived(msg);
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        loopCond = true;
        eventLoop();
    }

    @Override
    public void stop() {
        loopCond = false;
        channel.close();
        // System.out.println(ownMessage + " says -> Stopped! ");
    }
}
