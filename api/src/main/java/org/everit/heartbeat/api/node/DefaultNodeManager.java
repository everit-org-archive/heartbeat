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
import java.util.HashMap;
import java.util.Map;

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
        // TODO Implement
        return null;
    }

    @Override
    public Node[] getAllNodes() {
        // TODO Implement
        return null;
    }

    @Override
    public Node[] getLiveNodes(final long thresholdInMs, final boolean includingCurrentNode) {
        // TODO Implement
        return null;
    }

}
