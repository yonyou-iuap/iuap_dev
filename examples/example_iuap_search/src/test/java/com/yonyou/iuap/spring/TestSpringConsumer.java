package com.yonyou.iuap.spring;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * Created by zengxs on 2016/3/4.
 */
@ContextConfiguration("/applicationContext-consumer.xml")
public class TestSpringConsumer extends AbstractJUnit4SpringContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(TestSpringConsumer.class);

    Object lock = new Object();

    @Test
    public void testConsumer() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("lock interrupted",e);
            }
        }
    }

}
