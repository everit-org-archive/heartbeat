package org.everit.heartbeat.api.dto;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TestWatchman;

public class NodeTest {

    @Test
    public void testSuccess() throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getByName("192.168.1.1");
        long lastHeartbeatReveivedAt = 123;
        String gourpId = "123";
        Node testNode = new Node(inetAddress, lastHeartbeatReveivedAt, gourpId);

        Assert.assertNotNull(testNode);
        Assert.assertEquals(inetAddress,
                testNode.getInetAddress());
        Assert.assertEquals(lastHeartbeatReveivedAt,
                testNode.getLastHeartbeatReveivedAt());
        Assert.assertEquals(gourpId,
                testNode.getGourpId());

    }

    @Test
    public void testConstructorInetAddressNullArg() {
        try {
            new Node(null, 0, null);
            Assert.fail("constructor should fail with IllegalArgumentException in case of null inetAddress");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInetAddressNullArg2() {
        new Node(null, 0, null);
    }

}
