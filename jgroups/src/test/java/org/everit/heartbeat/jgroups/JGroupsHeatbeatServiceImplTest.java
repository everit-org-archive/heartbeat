package org.everit.heartbeat.jgroups;

import java.util.Arrays;
import java.util.List;

import org.everit.heartbeat.api.node.DefaultNodeManager;
import org.everit.heartbeat.api.node.Node;
import org.everit.heartbeat.api.node.NodeManager;
import org.everit.heartbeat.api.node.NodeMessage;
import org.junit.Assert;
import org.junit.Test;

public class JGroupsHeatbeatServiceImplTest {

    NodeManager manager1;
    NodeManager manager2;
    NodeManager manager3;
    List<Node> list1;
    List<Node> list2;

    @Test
    public void test() throws InterruptedException {

        manager1 = new DefaultNodeManager();
        manager2 = new DefaultNodeManager();
        manager3 = new DefaultNodeManager();
        JGroupsHeartbeatServiceImpl service1 = new JGroupsHeartbeatServiceImpl(manager1,
                new NodeMessage("192.1.1.1"), "theCluster");
        JGroupsHeartbeatServiceImpl service2 = new JGroupsHeartbeatServiceImpl(manager2,
                new NodeMessage("192.1.1.2"), "theCluster");
        JGroupsHeartbeatServiceImpl service3 = new JGroupsHeartbeatServiceImpl(manager3,
                new NodeMessage("192.1.1.3"), "anotherCluster");

        service1.setPeriod(1000);
        service2.setPeriod(2000);

        service1.start();
        Thread.sleep(100);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertFalse(list1.isEmpty());
        Assert.assertTrue(list1.size() == 1);

        service2.start();
        Thread.sleep(100);

        list2 = Arrays.asList(manager2.getAllNodes());
        Assert.assertFalse(list2.isEmpty());
        Assert.assertTrue(list2.size() == 1);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertTrue(list1.size() == 2);

        Thread.sleep(2000);

        list2 = Arrays.asList(manager2.getAllNodes());
        Assert.assertTrue(list2.size() == 2);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertTrue(list1.size() == 2);

        service3.start();
        Thread.sleep(2000);

        list2 = Arrays.asList(manager2.getAllNodes());
        Assert.assertTrue(list2.size() == 2);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertTrue(list1.size() == 2);

        for (Node node : list1) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());
            Assert.assertTrue((node.getInetAddress().getHostAddress().equals("192.1.1.1"))
                    || (node.getInetAddress().getHostAddress().equals("192.1.1.2")));
        }

        for (Node node : list2) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());
            Assert.assertTrue((node.getInetAddress().getHostAddress().equals("192.1.1.1"))
                    || (node.getInetAddress().getHostAddress().equals("192.1.1.2")));
        }

        service3.stop();

        service1.setMessage(new NodeMessage("192.2.2.1", "hello"));

        Thread.sleep(2000);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertTrue((list1.get(2).getGourpId().equals("hello")));

        service1.stop();
        service2.stop();

    }
}
