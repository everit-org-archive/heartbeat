package org.everit.heartbeat.api.node;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The default implementation of the {@link NodeProvider} that gives the default node handling (adding and querying)
 * method implementations.
 */
public class DefaultNodeManager implements NodeManager {

    /**
     * The nodes belonging to this cluster.
     */
    private final Map<InetAddress, Node> nodes = new HashMap<InetAddress, Node>();

    @Override
    public Node addNode(final Node node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        return nodes.put(node.getInetAddress(), node);
    }

    @Override
    public Node[] getAllNodes() {
        List<Node> listOfNodes = new ArrayList<Node>();

        for (Entry<InetAddress, Node> entry : nodes.entrySet()) {
            listOfNodes.add(entry.getValue());
        }

        return listOfNodes.toArray(new Node[] {});
    }

    @Override
    public Node[] getLiveNodes(final long thresholdInMs) {
        List<Node> listOfNodes = new ArrayList<Node>();

        for (Entry<InetAddress, Node> entry : nodes.entrySet()) {
            if (entry.getValue().getLastHeartbeatReceivedAt() > (System.currentTimeMillis() - thresholdInMs)) {
                listOfNodes.add(entry.getValue());
            }
        }

        return listOfNodes.toArray(new Node[] {});
    }

}
