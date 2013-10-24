package org.everit.heartbeat.api.node;

import java.net.InetSocketAddress;
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
            Assert.assertTrue(true);
        }
    }

    /**
     * Test the constructor of the Node class with valid parameters.
     * 
     * @throws UnknownHostException
     *             If can't get InetAddress.
     */
    @Test
    public void testSuccess() throws UnknownHostException {

        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.1.1", 500);
        long lastHeartbeatReceivedAt = 123;
        String gourpId = "123";
        Node testNode = new Node(inetSocketAddress, lastHeartbeatReceivedAt, gourpId);

        Assert.assertNotNull(testNode);
        Assert.assertEquals(inetSocketAddress,
                testNode.getInetSocketAddress());
        Assert.assertEquals(lastHeartbeatReceivedAt,
                testNode.getLastHeartbeatReceivedAt());
        Assert.assertEquals(gourpId,
                testNode.getGourpId());

    }

}
