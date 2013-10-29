package org.everit.heartbeat.api;

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

import java.io.Serializable;

/**
 * Service interface to manage heartbeat messaging.
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public interface HeartbeatService<M extends Serializable> {

    /**
     * Sets the heartbeat message that is sent by this component. It can be a payload of the heartbeat message, it
     * depends on the implementation.
     * 
     * @param message
     *            The heartbeat message. It can be <code>null</code> if no custom message content is required.
     */
    void setMessage(M message);

    /**
     * Sets the message listener that will be invoked after receiving a heartbeat message. The message listener can be
     * configured before the heartbeating started by {@link #start()}.
     * 
     * @param messageListener
     *            The message listener to use when a heartbeat message is received or <code>null</code> if no
     *            customization is required.
     * @throws IllegalStateException
     *             If the method invoked while the message sending is still in progress.
     */
    void setMessageListener(MessageListener messageListener);

    /**
     * Sets the period in milliseconds between heartbeat messages.
     * 
     * @param period
     *            The milliseconds between sending two heartbeat message. Must be greater than 0.
     * @throws IllegalArgumentException
     *             If the period parameter is less than or equal to 0.
     */
    void setPeriod(long period);

    /**
     * Starts the process that sends and receives the heartbeat messages. The heartbeating period is configured by
     * {@link #setPeriod(long)} and the message content is configured by {@link #setMessage(Serializable)}.
     */
    void start();

    /**
     * Stops the sending and receiving of heartbeat messages.
     */
    void stop();

}
