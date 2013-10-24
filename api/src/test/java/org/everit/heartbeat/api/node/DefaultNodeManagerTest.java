package org.everit.heartbeat.api.node;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test the DefaultNodeManager class.
 */
public class DefaultNodeManagerTest {

    /**
     * Testing the addNode() and getAllNodes() function of the DefaultNodeManager class.
     * 
     * @throws UnknownHostException
     *             If can't get InetAddress.
     */
    @Test
    public void addAndGetNodeTest() throws UnknownHostException {
        DefaultNodeManager defaultNodeManager = new DefaultNodeManager();

        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.1.1", 400);

        Node node = new Node(inetSocketAddress, 111, "111");
        Node node2 = new Node(inetSocketAddress, 222, "333");

        Assert.assertNull(defaultNodeManager.addNode(node));

        Assert.assertEquals(node, defaultNodeManager.addNode(node2));

        Assert.assertFalse(Arrays.asList(defaultNodeManager.getAllNodes()).contains(node));
        Assert.assertTrue(Arrays.asList(defaultNodeManager.getAllNodes()).contains(node2));

        Node node3 = new Node(InetSocketAddress.createUnresolved("192.168.1.2", 400), 222, "333");
        defaultNodeManager.addNode(node3);
        Node node4 =
                new Node(InetSocketAddress.createUnresolved("192.168.1.3", 400), 333, "333");
        defaultNodeManager.addNode(node4);

        Assert.assertTrue(Arrays.asList(defaultNodeManager.getAllNodes()).size() == 3);

    }

    /**
     * Testing the addNode method with null parameter. Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void addNullTest() {
        DefaultNodeManager defaultNodeManager = new DefaultNodeManager();
        defaultNodeManager.addNode(null);
    }

    /**
     * Testing the getLiveNodes() method.
     * 
     * @throws UnknownHostException
     *             If can't get InetAddress.
     */
    @Test
    public void getLiveNodesTest() throws UnknownHostException {
        DefaultNodeManager defaultNodeManager = new DefaultNodeManager();

        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.1.1", 400);
        long thresholdInMs = 2000;
        long lastHeartbeatReveivedAt = System.currentTimeMillis();

        Node node = new Node(inetSocketAddress, 100, "111");
        Node node2 = new Node(InetSocketAddress.createUnresolved("192.168.1.2", 400), lastHeartbeatReveivedAt, "333");
        Node node3 = new Node(InetSocketAddress.createUnresolved("192.168.1.3", 400), lastHeartbeatReveivedAt
                - thresholdInMs, "333");
        defaultNodeManager.addNode(node);
        defaultNodeManager.addNode(node2);
        defaultNodeManager.addNode(node3);

        List<Node> liveNodes = Arrays.asList(defaultNodeManager.getLiveNodes(thresholdInMs));
        for (Node i : liveNodes) {
            Assert.assertTrue(i.getLastHeartbeatReceivedAt() > (lastHeartbeatReveivedAt - thresholdInMs));
        }

    }

}
