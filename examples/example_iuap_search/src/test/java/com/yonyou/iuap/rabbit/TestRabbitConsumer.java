package com.yonyou.iuap.rabbit;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * Created by zengxs on 2016/3/4.
 */
@ContextConfiguration("/rabbit/applicationContext-mq-consumer.xml")
public class TestRabbitConsumer extends AbstractJUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(TestRabbitConsumer.class);

    Object lock = new Object();

    @Test
    public void testConsumer() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("lock interrupted", e);
            }
        }
    }

}
