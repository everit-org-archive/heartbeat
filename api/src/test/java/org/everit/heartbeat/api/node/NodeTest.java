package org.everit.heartbeat.api.node;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for class Node.
 */
public class NodeTest {

    /**
     * Test the constructor of the Node class for null parameter.
     */
    @Test
    public void testConstructorInetAddressNullArg() {
        try {
            new Node(null, 0, null);
            Assert.fail("constructor should fail with IllegalArgumentException in case of null inetAddress");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Test the constructor of the Node class for nul parameter no2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInetAddressNullArg2() {
        new Node(null, 0, null);
    }

    /**
     * Test the constructor of the Node class with valid parameters.
     */
    @Test
    public void testSuccess() throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getByName("192.168.1.1");
        long lastHeartbeatReceivedAt = 123;
        String gourpId = "123";
        Node testNode = new Node(inetAddress, lastHeartbeatReceivedAt, gourpId);

        Assert.assertNotNull(testNode);
        Assert.assertEquals(inetAddress,
                testNode.getInetAddress());
        Assert.assertEquals(lastHeartbeatReceivedAt,
                testNode.getLastHeartbeatReceivedAt());
        Assert.assertEquals(gourpId,
                testNode.getGourpId());

    }

}
