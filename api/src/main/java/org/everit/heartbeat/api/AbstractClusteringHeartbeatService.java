package org.everit.heartbeat.api;

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
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.everit.heartbeat.api.dto.Node;

/**
 * An abstract implementation of {@link ClusteringHeartbeatService} that gives the default node handling (adding and
 * querying) method implementations.
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public abstract class AbstractClusteringHeartbeatService<M extends Serializable> implements
        ClusteringHeartbeatService<M> {

    /**
     * The nodes belonging to this cluster.
     */
    private final Map<InetAddress, Node> nodes = new HashMap<InetAddress, Node>();

    /**
     * Adds the given node to the {@link #nodes}.
     * 
     * @param node
     *            The node to add.
     * @return The previous value associated to the node or <code>null</code> if the node was not present in the list.
     * @throws IllegalArgumentException
     *             If the <code>node</code> argument is <code>null</code>.
     */
    protected final Node addNode(final Node node) {
        // TODO Implement
        if (node == null) {
            throw new IllegalArgumentException();
        }
        return nodes.put(node.getInetAddress(), node);
    }

    @Override
    public Collection<Node> getAllNodes() {
        // TODO Implement
        List<Node> listOfNodes = new ArrayList<Node>();

        for (Entry<InetAddress, Node> i : nodes.entrySet()) {
            listOfNodes.add(i.getValue());
        }
        return listOfNodes;
    }

    @Override
    public Collection<Node> getLiveNodes(final long thresholdInMs, final boolean includingCurrentNode) {
        // TODO Implement
        List<Node> listOfNodes = new ArrayList<Node>();

        for (Entry<InetAddress, Node> i : nodes.entrySet()) {
            if (i.getValue().getLastHeartbeatReveivedAt() > (System.currentTimeMillis() - thresholdInMs)) {
                listOfNodes.add(i.getValue());
            }
        }
        return listOfNodes;
    }

}
