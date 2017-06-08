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

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import com.jfinal.log.Log;

public class TopicListener implements MessageListener {

    protected final Log logger = Log.getLog(getClass());

    protected String serverUrl;
    protected String username;
    protected String password;
    protected String topicName;

    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;
    private IMessageHandler messageHandler;

    public TopicListener(String serverUrl, String username, String password, String topicName,
            IMessageHandler messageHandler) {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
        this.topicName = topicName;
        this.messageHandler = messageHandler;
        initListener();
    }

    private void initListener() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(serverUrl);
            connection = connectionFactory.createConnection(username, password);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //destination = session.createTopic(topicName);
            destination = new ActiveMQTopic(topicName);
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            logger.error("init error", e);
        }
    }

    @Override
    public void onMessage(Message message) {
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        } else {
            logger.error("MessageHandler is null!please set a messageHandler");
            new DefaultMessageHandler().handleMessage(message);
        }
    }

    public void closeConnection() {
        if (consumer != null) {
            try {
                consumer.close();
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
}
