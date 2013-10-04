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
import java.util.Collection;

import org.everit.heartbeat.api.dto.Node;

/**
 * A {@link HeartbeatService} that supports clustering functionalities, i.e. querying the {@link Node}s that are in the
 * same cluster as the current one.
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public interface ClusteringHeartbeatService<M extends Serializable> extends HeartbeatService<M> {

    /**
     * Queries all the {@link Node}s that are in the same cluster as the current one.
     * 
     * @return The {@link Node}s.
     */
    Collection<Node> getAllNodes();

    /**
     * Queries the {@link Node}s that fulfills the following criteria:
     * <ul>
     * <li>The timestamp of the last heartbeat message received from the node is greater than
     * <code>(System.currentTimeMillis() - thresholdInMs)</code>.</li>
     * <li>The address of the node equals to the address of the current node or not, depending on the
     * <code>includingCurrentNode</code> parameter</li>
     * </ul>
     * 
     * @param thresholdInMs
     *            The threshold in milliseconds.
     * @param includingCurrentNode
     *            <code>true</code> if the query must include the current node in the result, <code>false</code> if the
     *            query must exclude the current node from the result.
     * @return The {@link Node}s.
     */
    Collection<Node> getLiveNodes(long thresholdInMs, boolean includingCurrentNode);

}