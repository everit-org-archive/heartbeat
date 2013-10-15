package org.everit.heartbeat.api.node;

import java.io.Serializable;

/**
 * A Class that represents a type of heartbeat message.
 */
public class NodeMessage implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3243255321206291319L;

    /**
     * The address of the represented Node.
     */
    private final String address;

    /**
     * The groupId of the represented Node.
     */
    private String groupId = null;

    /**
     * Constructor.
     * 
     * @param address
     *            The address of the represented Node.
     */
    public NodeMessage(final String address) {
        super();
        this.address = address;
    }

    /**
     * Constructor.
     * 
     * @param address
     *            The address of the represented Node.
     * @param groupId
     *            The groupId of the represented Node.
     */
    public NodeMessage(final String address, final String groupId) {
        super();
        this.address = address;
        this.groupId = groupId;
    }

    public String getAddress() {
        return address;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "NodeMessage [address=" + address + ", groupId=" + groupId + "]";
    }

}
