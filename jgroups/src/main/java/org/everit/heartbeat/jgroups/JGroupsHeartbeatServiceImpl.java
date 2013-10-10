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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.everit.heartbeat.api.AbstractClusteringHeartbeatService;
import org.everit.heartbeat.api.MessageListener;
import org.everit.heartbeat.api.dto.Node;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

/**
 * The implementation of {@link AbstractClusteringHeartbeatService} that sends and receives heartbeat messages via
 * JGroups.
 * 
 * @see http://www.jgroups.org/
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public class JGroupsHeartbeatServiceImpl<M extends Serializable> extends AbstractClusteringHeartbeatService<M> implements Receiver{

    private JChannel channel;
    private long period;
    private M message;
    
    @Override
    public void setMessage(final M message) {
        // TODO Implement
        this.message = message;
    }

    @Override
    public void setMessageListener(final MessageListener messageListener) {
        // TODO Implement

    }

    @Override
    public void setPeriod(final long period) {
        // TODO Implement
        if (period <= 0) {
            throw new IllegalArgumentException();
        }
        this.period = period;

    }

    @Override
    public void start() {
        // TODO Implement
        try {
            channel = new JChannel();
            channel.connect("Cluster1");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        eventLoop();
    }

    @Override
    public void stop() {
        // TODO Implement
    }

    @Override
    public void receive(Message msg) {
        // TODO Auto-generated method stub
        addNode((Node) msg.getObject());
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setState(InputStream input) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewAccepted(View new_view) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void suspect(Address suspected_mbr) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void block() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unblock() {
        // TODO Auto-generated method stub
        
    }

    public void eventLoop() {
        while(true) {

            try {

                Message msg=new Message(null, null, message);
                channel.send(msg);
                
                wait(period);

            }

            catch(Exception e) {

            }

        }
    }
}
