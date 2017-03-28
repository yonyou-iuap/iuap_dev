package com.yonyou.iuap.rabbit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.pojo.Wood;
import com.yonyou.iuap.search.processor.SearchClient;
import com.yonyou.iuap.search.query.exception.SearchException;

/**
 * Created by zengxs on 2016/3/4.
 */
@ContextConfiguration("/rabbit/applicationContext-mq-provider.xml")
public class TestRabbitProducer extends AbstractJUnit4SpringContextTests {

    @Value("${solr.zk.url}")
    private String zkUrl;

    @Value("${solr.zk.collection}")
    private String collection;

    @Autowired
    private SearchClient searchClient;

    @Test
    public void testQuery() throws SearchException, InterruptedException {
        List<Wood> woodList = new ArrayList<Wood>();
        for (int i = 1; i < 22; i++) {
            Wood wood = new Wood();
            wood.setTenentid("zxstest");
            wood.setId(i + "");
            wood.setCreateTime(new Date());
            wood.setName("zxs" + i);
            wood.setTitle(new ArrayList<String>() {
                {
                    add("testtest" + Math.random() * 100);
                }
            });
            woodList.add(wood);
        }
        searchClient.createCloudSearchClient(zkUrl, collection).addBean(woodList);
        Thread.sleep(2000);
    }
}
