package com.yonyou.iuap.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import com.yonyou.iuap.search.query.pojo.BaseSolrEntity;

/**
 * Created by zengxs on 2016/3/2.
 */
public class Wood extends BaseSolrEntity implements Serializable {

    @Field
    private String id;

    @Field
    private String name;

    @Field("content")
    private List<String> title;

    @Field("createtime")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
