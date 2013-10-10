package org.everit.heartbeat.api;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.everit.heartbeat.api.dto.Node;
import org.junit.Assert;
import org.junit.Test;

public class AbstractClusteringHeartbeatServiceTest {

    class ClusteringHeartbeatServiceTest extends AbstractClusteringHeartbeatService<String> {

        @Override
        public void setMessage(String message) {

        }

        @Override
        public void setMessageListener(MessageListener messageListener) {

        }

        @Override
        public void setPeriod(long period) {

        }

        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }

    }
    
    @Test
    public void test() {
        ClusteringHeartbeatServiceTest test = new ClusteringHeartbeatServiceTest();
        Assert.assertNotNull(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullTest() {
        ClusteringHeartbeatServiceTest cHBServ = new ClusteringHeartbeatServiceTest();
        cHBServ.addNode(null);
    }

    @Test
    public void addAndGetNodeTest() throws UnknownHostException {
        ClusteringHeartbeatServiceTest cHBServ = new ClusteringHeartbeatServiceTest();

        InetAddress inetAddress = InetAddress.getByName("192.168.1.1");

        Node node = new Node(inetAddress, 111, "111");
        Node node2 = new Node(inetAddress, 222, "333");

        Assert.assertNull(cHBServ.addNode(node));
        Assert.assertTrue(cHBServ.getAllNodes().contains(node));
        Assert.assertEquals(node, cHBServ.addNode(node2));
        Assert.assertFalse(cHBServ.getAllNodes().contains(node));
        Assert.assertTrue(cHBServ.getAllNodes().contains(node2));

        Node node3 = new Node(InetAddress.getByName("192.168.1.2"), 222, "333");
        cHBServ.addNode(node3);
        Node node4 = new Node(InetAddress.getByName("192.168.1.3"), 333, "333");
        cHBServ.addNode(node4);

        Assert.assertTrue(cHBServ.getAllNodes().size() == 3);
    }

    @Test
    public void getLiveNodesTest() throws UnknownHostException {
        ClusteringHeartbeatServiceTest cHBServ = new ClusteringHeartbeatServiceTest();

        InetAddress inetAddress = InetAddress.getByName("192.168.1.1");
        long thresholdInMs = 2000;
        long lastHeartbeatReveivedAt = System.currentTimeMillis();

        Node node = new Node(inetAddress, 100, "111");
        Node node2 = new Node(InetAddress.getByName("192.168.1.2"), lastHeartbeatReveivedAt, "333");
        Node node3 = new Node(InetAddress.getByName("192.168.1.3"), lastHeartbeatReveivedAt - thresholdInMs, "333");
        cHBServ.addNode(node);
        cHBServ.addNode(node2);
        cHBServ.addNode(node3);

        for (Node i : cHBServ.getLiveNodes(thresholdInMs, false)) {
            Assert.assertTrue(i.getLastHeartbeatReveivedAt() > (lastHeartbeatReveivedAt - thresholdInMs));
        }

    }

}
