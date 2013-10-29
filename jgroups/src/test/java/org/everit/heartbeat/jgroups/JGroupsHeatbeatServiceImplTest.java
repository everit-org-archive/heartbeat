package org.everit.heartbeat.jgroups;

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

import java.util.Arrays;
import java.util.List;

import org.everit.heartbeat.api.node.DefaultNodeManager;
import org.everit.heartbeat.api.node.Node;
import org.everit.heartbeat.api.node.NodeManager;
import org.everit.heartbeat.api.node.NodeMessage;
import org.junit.Assert;
import org.junit.Test;

public class JGroupsHeatbeatServiceImplTest {

    private NodeManager manager1;
    private NodeManager manager2;
    private NodeManager manager3;
    private List<Node> list1;
    private List<Node> list2;

    @Test
    public void test() throws InterruptedException {

        manager1 = new DefaultNodeManager();
        manager2 = new DefaultNodeManager();
        manager3 = new DefaultNodeManager();
        JGroupsHeartbeatServiceImpl service1 = new JGroupsHeartbeatServiceImpl(manager1,
                new NodeMessage("192.1.1.1", 10), "theCluster", null);
        JGroupsHeartbeatServiceImpl service2 = new JGroupsHeartbeatServiceImpl(manager2,
                new NodeMessage("192.1.1.2", 10), "theCluster", null);
        JGroupsHeartbeatServiceImpl service3 = new JGroupsHeartbeatServiceImpl(manager3,
                new NodeMessage("192.1.1.3", 10), "anotherCluster", null);

        service1.setPeriod(1000);
        service2.setPeriod(2000);

        service1.start();
        Thread.sleep(200);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertFalse(list1.isEmpty());
        Assert.assertTrue(list1.size() == 1);

        service2.start();
        Thread.sleep(200);

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
            Assert.assertTrue((node.getInetSocketAddress().getHostString().equals("192.1.1.1"))
                    || (node.getInetSocketAddress().getHostString().equals("192.1.1.2")));
        }

        for (Node node : list2) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());
            Assert.assertTrue((node.getInetSocketAddress().getHostString().equals("192.1.1.1"))
                    || (node.getInetSocketAddress().getHostString().equals("192.1.1.2")));
        }

        service3.stop();

        service1.setMessage(new NodeMessage("192.1.1.1", 10, "testGroupId"));
        Thread.sleep(2000);

        list1 = Arrays.asList(manager1.getAllNodes());
        boolean contains = false;
        for (Node node : list1) {
            if ("testGroupId".equals(node.getGourpId())) {
                contains = true;
            }
        }
        Assert.assertTrue(contains);

        service1.setMessage(new NodeMessage("192.2.2.1", 10, "hello"));

        Thread.sleep(2000);

        list1 = Arrays.asList(manager1.getAllNodes());
        Assert.assertTrue((list1.get(2).getGourpId().equals("hello")));

        service1.stop();
        service2.stop();

    }
}
