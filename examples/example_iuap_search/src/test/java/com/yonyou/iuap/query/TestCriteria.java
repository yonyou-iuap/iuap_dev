package com.yonyou.iuap.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.pojo.Wood;
import com.yonyou.iuap.search.processor.SearchClient;
import com.yonyou.iuap.search.processor.impl.CloudSearchClient;
import com.yonyou.iuap.search.processor.impl.HttpSearchClient;
import com.yonyou.iuap.search.query.component.Criteria;
import com.yonyou.iuap.search.query.component.SolrRestrictions;
import com.yonyou.iuap.search.query.exception.SearchException;
import com.yonyou.iuap.search.query.pojo.FacetParam;

/**
 * Created by zengxs on 2016/3/3.
 */
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestCriteria extends AbstractJUnit4SpringContextTests {


    @Value("${solr.http.url}")
    private String httpUrl;

    @Value("${solr.zk.url}")
    private String zkUrl;

    @Value("${solr.zk.collection}")
    private String collection;

    @Test
    public void testHttp() throws SearchException {
        SearchClient searchClient = new SearchClient();
        HttpSearchClient client = searchClient.createHttpSearchClient(httpUrl);
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        Page<Wood> result =
                criteria.addQuery(SolrRestrictions.like("name", "zx"))
                        .addQuery(
                                SolrRestrictions.or(SolrRestrictions.eq("name", "zxs"),
                                        SolrRestrictions.like("title", "zx")))
                        .addPageParam(
                                new PageRequest(0, 1, new Sort(new Sort.Order(Sort.Direction.ASC, "id"),
                                        new Sort.Order(Sort.Direction.DESC, "name"))))
                        .addCustomParam("shard.torlert", true).addCustomParam("aaa", 1).addCustomParam("bbb", "2")
                        .page();
        System.out.println(result.getContent());
    }

    @Test
    public void testCloud() throws SearchException {
        SearchClient searchClient = new SearchClient();
        CloudSearchClient client = searchClient.createCloudSearchClient(zkUrl, collection);
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        Page<Wood> result =
                criteria.addQuery(SolrRestrictions.like("title", "this"))
                        .addQuery(
                                SolrRestrictions.or(SolrRestrictions.eq("id", "C!doc50"),
                                        SolrRestrictions.like("title", "th")))
                        .addPageParam(
                                new PageRequest(0, 1, new Sort(new Sort.Order(Sort.Direction.ASC, "id"),
                                        new Sort.Order(Sort.Direction.DESC, "name"))))
                        .addCustomParam("shard.torlert", true).addCustomParam("aaa", 1).addCustomParam("bbb", "2")
                        .addFilterQuery(SolrRestrictions.eq("id", "C!doc50")).page();
        System.out.println(result.getContent());
    }

    @Test
    public void testCloudDate() throws SearchException {
        SearchClient searchClient = new SearchClient();
        CloudSearchClient client = searchClient.createCloudSearchClient(zkUrl, collection);
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        Date startDate = calendar.getTime();
        Date endDate = new Date();
        FacetParam facetParam = new FacetParam();
        facetParam.addField("name", "tenentid");
        List<Wood> result =
                criteria.addFilterQuery(SolrRestrictions.between("createtime", startDate, endDate))
                        .addFacetParam(facetParam).list();
        System.out.println(result);
    }

    @Test
    public void testDate() throws ParseException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-03-05 13:26:51"));
    }

}
