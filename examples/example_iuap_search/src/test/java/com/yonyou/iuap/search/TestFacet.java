package com.yonyou.iuap.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.yonyou.iuap.pojo.Wood;
import com.yonyou.iuap.search.processor.SearchClient;
import com.yonyou.iuap.search.processor.impl.HttpSearchClient;
import com.yonyou.iuap.search.query.component.Criteria;
import com.yonyou.iuap.search.query.component.SolrRestrictions;
import com.yonyou.iuap.search.query.exception.SearchException;
import com.yonyou.iuap.search.query.pojo.FacetDateRangeParam;
import com.yonyou.iuap.search.query.pojo.FacetParam;
import com.yonyou.iuap.search.query.pojo.SearchResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 测试Facet查询
 * 
 * 示例一：测试Facet Field 示例二：测试Facet Query 示例三：测试Facet Range
 * 
 * @author puyy
 *
 */

@ContextConfiguration(locations = "/applicationContext.xml")
public class TestFacet extends AbstractJUnit4SpringContextTests {


    @Value("${solr.http.url}")
    private String httpUrl;

    @Test
    public void testFacetField() throws SolrServerException, IOException, SearchException {
        // 初始化查询对象
        String q = "前端";
        SearchClient searchClient = new SearchClient();
        HttpSearchClient client = searchClient.createHttpSearchClient(httpUrl);
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        criteria.addQuery(SolrRestrictions.like("content", q));

        FacetParam facetParam = new FacetParam();
        // 添加Facet Field参数
        List<String> facetFields = new ArrayList<String>();
        facetFields.add("id");
        facetParam.setFields(facetFields);
        criteria.addFacetParam(facetParam);

        SearchResult searchResult = client.searchForResult(criteria);
        // 得到Facet的结果
        List<FacetField.Count> list = searchResult.getFacetField("id").getValues();
        System.out.println("FacetField结果：");
        for (FacetField.Count count : list) {
            System.out.println(count.getAsFilterQuery() + "\t" + count.getCount());
        }
    }

    @Test
    public void testFacetQuery() throws SolrServerException, IOException, SearchException {
        // 初始化查询对象
        String q = "前端";
        SearchClient searchClient = new SearchClient();
        HttpSearchClient client = searchClient.createHttpSearchClient(httpUrl);
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        criteria.addQuery(SolrRestrictions.like("content", q));

        // 添加Facet Query参数
        FacetParam facetParam = new FacetParam();
        List<String> facetQueries = new ArrayList<String>();
        facetQueries.add("id:1");
        facetQueries.add("id:2");
        facetParam.setFacetQuerys(facetQueries);
        criteria.addFacetParam(facetParam);

        SearchResult searchResult = client.searchForResult(criteria);
        // 得到Facet的结果
        System.out.println("FacetQuery结果：");
        Map<String, Integer> map = searchResult.getFacetQueries();
        System.out.println(map);
    }

    @Test
    public void testFacetRange() throws SolrServerException, IOException, SearchException {
        // 初始化查询对象
        String q = "前端";
        SearchClient searchClient = new SearchClient();
        HttpSearchClient client = searchClient.createHttpSearchClient(httpUrl);
        Criteria<Wood> criteria = client.createCriteria(Wood.class);
        criteria.addQuery(SolrRestrictions.like("content", q));

        // 添加FacetRange参数
        FacetParam facetParam = new FacetParam();
        List<FacetDateRangeParam> dateRangeParams = new ArrayList<FacetDateRangeParam>();
        FacetDateRangeParam startTimeRangeParam = new FacetDateRangeParam();
        startTimeRangeParam.setField("joinworkdate");
        startTimeRangeParam.setGap("+1MONTH");
        long now = System.currentTimeMillis();
        startTimeRangeParam.setStart(new Date(now - 10000000000l));
        startTimeRangeParam.setEnd(new Date(now));
        dateRangeParams.add(startTimeRangeParam);
        facetParam.setDateRangeParams(dateRangeParams);
        criteria.addFacetParam(facetParam);
        SearchResult searchResult = client.searchForResult(criteria);

        // 得到Facet的结果
        List<RangeFacet> list = searchResult.getRangeFacet();
        System.out.println("FacetRange结果：");
        for (RangeFacet range : list) {
            // 得到一组FacetRange的结果
            System.out.println(range.getName());
            List<RangeFacet.Count> counts = range.getCounts();
            for (RangeFacet.Count count : counts) {
                System.out.println(count.getValue() + "\t" + count.getCount());
            }
        }
    }
}
