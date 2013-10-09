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
import java.net.InetAddress;

/**
 * Class that represents a node in a cluster.
 */
public class Node implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 177732739546836602L;

    /**
     * The address of the node.
     */
    private final InetAddress inetAddress;

    /**
     * The timestamp of the last received heartbeat message from this node.
     */
    private final long lastHeartbeatReveivedAt;

    /**
     * The ID of the group inside the cluster.
     */
    private final String gourpId;

    /**
     * Constructor.
     * 
     * @param inetAddress
     *            The address of the node.
     * @param lastHeartbeatReveivedAt
     *            The timestamp of the last received heartbeat message from this node.
     * @param gourpId
     *            The ID of the group inside the cluster.
     */
    public Node(final InetAddress inetAddress, final long lastHeartbeatReveivedAt, final String gourpId) {
        super();
        this.inetAddress = inetAddress;
        this.lastHeartbeatReveivedAt = lastHeartbeatReveivedAt;
        this.gourpId = gourpId;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        if (gourpId == null) {
            if (other.gourpId != null) {
                return false;
            }
        } else if (!gourpId.equals(other.gourpId)) {
            return false;
        }
        if (inetAddress == null) {
            if (other.inetAddress != null) {
                return false;
            }
        } else if (!inetAddress.equals(other.inetAddress)) {
            return false;
        }
        if (lastHeartbeatReveivedAt != other.lastHeartbeatReveivedAt) {
            return false;
        }
        return true;
    }

    public String getGourpId() {
        return gourpId;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public long getLastHeartbeatReveivedAt() {
        return lastHeartbeatReveivedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((gourpId == null) ? 0 : gourpId.hashCode());
        result = (prime * result) + ((inetAddress == null) ? 0 : inetAddress.hashCode());
        result = (prime * result) + (int) (lastHeartbeatReveivedAt ^ (lastHeartbeatReveivedAt >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Node [inetAddress=" + inetAddress + ", lastHeartbeatReveivedAt=" + lastHeartbeatReveivedAt
                + ", gourpId=" + gourpId + "]";
    }

}
