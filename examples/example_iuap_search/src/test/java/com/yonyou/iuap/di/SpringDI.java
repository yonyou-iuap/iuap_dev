package com.yonyou.iuap.di;

import com.yonyou.iuap.search.processor.SearchClient;
import com.yonyou.iuap.search.processor.impl.CloudSearchClient;
import com.yonyou.iuap.search.processor.impl.HttpSearchClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by zengxs on 2016/5/26.
 */
@ContextConfiguration("/applicationContext.xml")
public class SpringDI extends AbstractJUnit4SpringContextTests {

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private HttpSearchClient httpSearchClient;

    @Autowired
    private CloudSearchClient cloudSearchClient;

    @Value("${solr.http.url}")
    private String httpUrl;

    @Value("${solr.zk.url}")
    private String zkHost;

    @Value("${solr.zk.collection}")
    private String collection;


    @Test
    public void testGetBean() {
        HttpSearchClient httpSearchClientBySearchClient = searchClient.createHttpSearchClient(httpUrl);
        CloudSearchClient cloudSearchClientBySearchClient = searchClient.createCloudSearchClient(zkHost, collection);
    }
}
