package com.yonyou.iuap.pojo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by zengxs on 2016/5/16.
 */
public class Article implements Serializable {

    @Field
    private String id;

    @Field
    private long filesize;

    @Field
    private String filename;

    @Field
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
