package org.everit.heartbeat.api.node;

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
