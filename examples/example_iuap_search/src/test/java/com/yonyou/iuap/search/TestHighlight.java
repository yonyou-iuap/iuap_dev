package com.yonyou.iuap.search;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.pojo.Article;
import com.yonyou.iuap.search.processor.SearchClient;
import com.yonyou.iuap.search.processor.impl.HttpSearchClient;
import com.yonyou.iuap.search.query.component.Criteria;
import com.yonyou.iuap.search.query.component.SolrRestrictions;
import com.yonyou.iuap.search.query.exception.SearchException;
import com.yonyou.iuap.search.query.pojo.HighlightParam;
import com.yonyou.iuap.search.query.pojo.SearchResult;

/**
 * Created by zengxs on 2016/5/16.
 */

@ContextConfiguration(locations = "/applicationContext.xml")
public class TestHighlight extends AbstractJUnit4SpringContextTests {


    @Value("${solr.http.url}")
    private String httpUrl;

    @Test
    public void testFacetField() throws SolrServerException, IOException, SearchException {
        // 初始化查询对象
        String q = "主从复制";
        SearchClient searchClient = new SearchClient();
        HttpSearchClient client = searchClient.createHttpSearchClient(httpUrl);
        Criteria<Article> criteria = client.createCriteria(Article.class);
        criteria.addQuery(SolrRestrictions.eq("filecontent", q));

        HighlightParam highlightParam = new HighlightParam();
        highlightParam.setHlFields("filecontent").setHlPrefix("<font color='red'>").setHlPost("</font>")
                .setHighlightSnippets(1).setHighlightFragsize(20);
        criteria.addHighlightParam(highlightParam);

        SearchResult searchResult = client.searchForResult(criteria);
        // 得到Facet的结果
        System.out.println("FacetField结果：");
        System.out.println(searchResult.getHighlightMap());
    }
}
