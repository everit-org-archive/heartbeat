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

import org.everit.heartbeat.api.MessageListener;
import org.everit.heartbeat.api.clustering.Node;
import org.everit.heartbeat.api.clustering.NodeManager;
import org.everit.heartbeat.api.clustering.NodeMessage;
import org.jgroups.Message;

public class ClusteringJGroupsHeartbeatServiceImpl extends JGroupsHeartbeatServiceImpl<NodeMessage> {

    public ClusteringJGroupsHeartbeatServiceImpl(final NodeManager nodeManager, final NodeMessage nodeMessage,
            final long period, final String clusterName) {
        super(nodeMessage, period, clusterName, new MessageListener() {

            @Override
            public void afterMessageReceived(final Object object) {
                if (!(object instanceof Message)) {
                    throw new IllegalStateException("Unsupported object ["
                            + object.getClass().getName() + "]");
                }
                Message message = (Message) object;
                Object messageObject = message.getObject();
                if (!(messageObject instanceof NodeMessage)) {
                    throw new IllegalStateException("Unsupported message object ["
                            + messageObject.getClass().getName() + "]");
                }
                NodeMessage nodeMessage = (NodeMessage) message.getObject();
                InetSocketAddress inetSocketAddress =
                        InetSocketAddress.createUnresolved(nodeMessage.getAddress(), nodeMessage.getPort());
                Node nodeToAdd = new Node(inetSocketAddress, System.currentTimeMillis());
                nodeManager.addNode(nodeToAdd);
            }

        });
    }

}
