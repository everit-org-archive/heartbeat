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

import org.everit.heartbeat.api.HeartbeatService;
import org.everit.heartbeat.api.clustering.DefaultNodeManager;
import org.everit.heartbeat.api.clustering.Node;
import org.everit.heartbeat.api.clustering.NodeManager;
import org.everit.heartbeat.api.clustering.NodeMessage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClusteringJGroupsHeartbeatServiceImplTest {

    private static final String H1 = "192.1.1.1";
    private static final String H2 = "192.1.1.2";
    private static final String H3 = "192.1.1.3";
    private static final NodeMessage NM1 = new NodeMessage(H1, 10);
    private static final NodeMessage NM2 = new NodeMessage(H2, 10);
    private static final NodeMessage NM3 = new NodeMessage(H3, 10);
    private static final String C1 = "c1";
    private static final String C2 = "c2";
    private static final int P1 = 100;
    private static final int P2 = 200;
    private NodeManager nodeManager1 = new DefaultNodeManager();
    private NodeManager nodeManager2 = new DefaultNodeManager();
    private NodeManager nodeManager3 = new DefaultNodeManager();
    private HeartbeatService<NodeMessage> heartbeatService1;
    private HeartbeatService<NodeMessage> heartbeatService2;
    private HeartbeatService<NodeMessage> heartbeatService3;

    @After
    public void after() {
        heartbeatService1.stop();
        heartbeatService2.stop();
        heartbeatService3.stop();
    }

    @Before
    public void before() throws InterruptedException {
        heartbeatService1 = new ClusteringJGroupsHeartbeatServiceImpl(nodeManager1, NM1, P1, C1);
        heartbeatService2 = new ClusteringJGroupsHeartbeatServiceImpl(nodeManager2, NM2, P2, C1);
        heartbeatService3 = new ClusteringJGroupsHeartbeatServiceImpl(nodeManager3, NM3, P1, C2);
    }

    @Test
    public void test() throws InterruptedException {
        heartbeatService1.start();
        Thread.sleep(P1);

        Assert.assertEquals(1, nodeManager1.getAllNodes().length);

        heartbeatService2.start();
        Thread.sleep(P2);

        Assert.assertEquals(2, nodeManager1.getAllNodes().length);
        Assert.assertEquals(2, nodeManager2.getAllNodes().length);

        heartbeatService3.start();
        Thread.sleep(P2);

        Assert.assertEquals(2, nodeManager1.getAllNodes().length);
        Assert.assertEquals(2, nodeManager2.getAllNodes().length);

        for (Node node : nodeManager1.getAllNodes()) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());
            Assert.assertTrue((node.getInetSocketAddress().getHostString().equals(H1))
                    || (node.getInetSocketAddress().getHostString().equals(H2)));
        }

        for (Node node : nodeManager2.getAllNodes()) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());
            Assert.assertTrue((node.getInetSocketAddress().getHostString().equals(H1))
                    || (node.getInetSocketAddress().getHostString().equals(H2)));
        }
    }
}
