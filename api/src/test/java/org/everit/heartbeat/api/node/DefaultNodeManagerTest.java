package org.everit.heartbeat.api.node;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.everit.heartbeat.api.node.DefaultNodeManager;
import org.everit.heartbeat.api.node.Node;
import org.junit.Assert;
import org.junit.Test;

public class DefaultNodeManagerTest {

    @Test
    public void addAndGetNodeTest() throws UnknownHostException {
        DefaultNodeManager cHBServ = new DefaultNodeManager();

        InetAddress inetAddress = InetAddress.getByName("192.168.1.1");

        Node node = new Node(inetAddress, 111, "111");
        Node node2 = new Node(inetAddress, 222, "333");

        Assert.assertNull(cHBServ.addNode(node));

        Assert.assertEquals(node, cHBServ.addNode(node2));

        Assert.assertFalse(Arrays.asList(cHBServ.getAllNodes()).contains(node));
        Assert.assertTrue(Arrays.asList(cHBServ.getAllNodes()).contains(node2));

        Node node3 = new Node(InetAddress.getByName("192.168.1.2"), 222, "333");
        cHBServ.addNode(node3);
        Node node4 =
                new Node(InetAddress.getByName("192.168.1.3"), 333, "333");
        cHBServ.addNode(node4);

        Assert.assertTrue(Arrays.asList(cHBServ.getAllNodes()).size() == 3);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullTest() {
        DefaultNodeManager cHBServ = new DefaultNodeManager();
        cHBServ.addNode(null);
    }

    @Test
    public void getLiveNodesTest() throws UnknownHostException {
        DefaultNodeManager cHBServ = new DefaultNodeManager();

        InetAddress inetAddress = InetAddress.getByName("192.168.1.1");
        long thresholdInMs = 2000;
        long lastHeartbeatReveivedAt = System.currentTimeMillis();

        Node node = new Node(inetAddress, 100, "111");
        Node node2 = new Node(InetAddress.getByName("192.168.1.2"), lastHeartbeatReveivedAt, "333");
        Node node3 = new Node(InetAddress.getByName("192.168.1.3"), lastHeartbeatReveivedAt - thresholdInMs, "333");
        cHBServ.addNode(node);
        cHBServ.addNode(node2);
        cHBServ.addNode(node3);

        for (Node i : Arrays.asList(cHBServ.getLiveNodes(thresholdInMs))) {
            Assert.assertTrue(i.getLastHeartbeatReceivedAt() > (lastHeartbeatReveivedAt - thresholdInMs));
        }

    }

    @Test
    public void test() {
        DefaultNodeManager test = new DefaultNodeManager();
        Assert.assertNotNull(test);
    }

}
