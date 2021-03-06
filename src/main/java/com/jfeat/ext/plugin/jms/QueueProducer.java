/*
 * Copyright (C) 2014-2015 by ehngjen @ www.jfeat.com
 *
 *  The program may be used and/or copied only with the written permission
 *  from JFeat.com, or in accordance with the terms and
 *  conditions stipulated in the agreement/contract under which the program
 *  has been supplied.
 *
 *  All rights reserved.
 */
package com.jfeat.ext.plugin.jms;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.jfinal.log.Log;

public class QueueProducer {

    protected final Log logger = Log.getLog(getClass());

    protected String serverUrl;
    protected String username;
    protected String password;
    protected String queueName;
    protected int reConnectTimes;
    protected int reConnectInterval;

    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;

    public QueueProducer(String serverUrl, String username, String password, String queueName) {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
        this.queueName = queueName;
        initConnection();
    }

    public QueueProducer(String serverUrl, String username, String password, String queueName, int reConnectTimes,
            int reConnectInterval) {
        this(serverUrl, username, password, queueName);
        this.reConnectTimes = reConnectTimes;
        this.reConnectInterval = reConnectInterval;
    }

    private void initConnection() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(serverUrl);
        try {
            connection = connectionFactory.createConnection(username, password);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            connection.start();
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void closeConnection() {
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException e) {
                logger.error(e.getMessage(), e);

            }
        }
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public boolean sendMessage(Serializable object, int msgType) {
        try {
            if (session == null) {
                if (!reConnect()) {
                    return false;
                }
            }
            logger.debug("send message, msg_type:" + msgType);
            ObjectMessage om = session.createObjectMessage(object);
            om.setIntProperty(JMSConstants.JMS_MESSAGE_TYPE, msgType);
            producer.send(destination, om);
        } catch (JMSException e) {
            logger.error("send error", e);
            return false;
        }

        return true;
    }

    private boolean reConnect() {
        int times = reConnectTimes;
        while (times-- > 0) {
            logger.debug("reConnectTimes" + times);
            initConnection();
            if (session != null) {
                return true;
            }
            try {
                TimeUnit.MINUTES.sleep(reConnectInterval);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }
}
