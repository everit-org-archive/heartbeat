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

import java.io.Serializable;

/**
 * A Class that represents a type of heartbeat message.
 */
public final class NodeMessage implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3243255321206291319L;

    /**
     * The address of the represented Node.
     */
    private final String address;

    /**
     * The port of the represented Node.
     */
    private final int port;

    /**
     * The groupId of the represented Node.
     */
    private final String groupId;

    /**
     * Constructor.
     * 
     * @param address
     *            The address of the represented Node.
     * @param port
     *            The port of the represented Node.
     */
    public NodeMessage(final String address, final int port) {
        super();
        this.address = address;
        this.port = port;
        groupId = null;
    }

    /**
     * Constructor.
     * 
     * @param address
     *            The address of the represented Node.
     * @param port
     *            The port of the represented Node.
     * @param groupId
     *            The groupId of the represented Node.
     */
    public NodeMessage(final String address, final int port, final String groupId) {
        super();
        this.address = address;
        this.port = port;
        this.groupId = groupId;
    }

    public String getAddress() {
        return address;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "NodeMessage [address=" + address + ", groupId=" + groupId + "]";
    }

}
