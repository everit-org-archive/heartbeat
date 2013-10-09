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

import java.io.Serializable;

import org.everit.heartbeat.api.HeartbeatService;
import org.everit.heartbeat.api.MessageListener;
import org.everit.heartbeat.api.node.NodeProvider;

/**
 * The implementation of {@link HeartbeatService} that sends and receives heartbeat messages via JGroups.
 * 
 * @see http://www.jgroups.org/
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public class JGroupsHeartbeatServiceImpl<M extends Serializable> implements HeartbeatService<M> {

    private final NodeProvider nodeProvider;

    public JGroupsHeartbeatServiceImpl(final NodeProvider nodeProvider) {
        super();
        this.nodeProvider = nodeProvider;
    }

    @Override
    public void setMessage(final M message) {
        // TODO Implement
    }

    @Override
    public void setMessageListener(final MessageListener messageListener) {
        // TODO Implement

    }

    @Override
    public void setPeriod(final long period) {
        // TODO Implement

    }

    @Override
    public void start() {
        // TODO Implement
    }

    @Override
    public void stop() {
        // TODO Implement
    }

}
