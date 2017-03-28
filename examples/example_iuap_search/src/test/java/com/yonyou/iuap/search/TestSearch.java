package com.yonyou.iuap.search;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.yonyou.iuap.pojo.Wood;
import com.yonyou.iuap.search.processor.impl.HttpSearchClient;
import com.yonyou.iuap.search.query.component.Criteria;
import com.yonyou.iuap.search.query.component.SolrRestrictions;
import com.yonyou.iuap.search.query.exception.SearchException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 测试搜索功能
 * 
 * Created by zengxs on 2016/3/2.
 */

@ContextConfiguration(locations = "/applicationContext.xml")
public class TestSearch extends AbstractJUnit4SpringContextTests{

    private HttpSearchClient client;

    @Value("${solr.http.url}")
    private String httpUrl;

    @Before
    public void init() {
        client = new HttpSearchClient(httpUrl);
    }

    @Test
    public void testQuery() throws IOException, SolrServerException {
        String query = "this";
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        try {
            criteria.addQuery(SolrRestrictions.like("content", query.toString())).addPageParam(
                    new PageRequest(0, 10, new Sort(new Sort.Order(Sort.Direction.ASC, "id"), new Sort.Order(
                            Sort.Direction.DESC, "name"))));
            Page<Wood> result = client.search(criteria);

            System.out.println("搜索结果数：" + result.getContent().size());
            System.out.println("搜索结果：" + result.getContent());
        } catch (SearchException e) {
            System.err.println("搜索失败!");
        }
    }
}
