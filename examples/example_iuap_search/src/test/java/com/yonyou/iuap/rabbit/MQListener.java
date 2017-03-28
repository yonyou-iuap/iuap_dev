package com.yonyou.iuap.rabbit;

import com.yonyou.iuap.search.msg.MessageConsumer;
import com.yonyou.iuap.search.query.exception.SearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zengxs on 2016/5/28.
 */
public class MQListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MQListener.class);

    private MessageConsumer msgConsumer;

    @Override
    public void onMessage(Message message) {
        try {
            msgConsumer.onMessage(new String(message.getBody()));
        } catch (SearchException e) {
            logger.error("Fail to consumer msg " + message, e);
        }
    }

    public MessageConsumer getMsgConsumer() {
        return msgConsumer;
    }

    public void setMsgConsumer(MessageConsumer msgConsumer) {
        this.msgConsumer = msgConsumer;
    }
}
