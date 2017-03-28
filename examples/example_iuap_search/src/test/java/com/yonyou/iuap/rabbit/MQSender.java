package com.yonyou.iuap.rabbit;

import com.yonyou.iuap.mq.rabbit.RabbitMQProducer;
import com.yonyou.iuap.search.msg.MessageSender;
import com.yonyou.iuap.search.query.exception.SearchException;

/**
 * Created by zengxs on 2016/5/28.
 */
public class MQSender implements MessageSender {

    private RabbitMQProducer rabbitMQProducer;

    @Override
    public void sendDoc(String content, String routeKey) throws SearchException {
        rabbitMQProducer.sendMsg("iuap-direct-exchange", "simple_queue_key", content);
    }

    public RabbitMQProducer getRabbitMQProducer() {
        return rabbitMQProducer;
    }

    public void setRabbitMQProducer(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }
}
