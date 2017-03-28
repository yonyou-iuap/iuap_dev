package com.yonyou.iuap.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yonyou.iuap.pojo.Wood;

/**
 * Created by zengxs on 2016/3/4.
 */
public class TestJson {

    @Test
    public void testJson() throws IOException {
        Wood wood = new Wood();
        wood.setId("1");
        wood.setName("zxstest");
        wood.setTitle(new ArrayList<String>() {
            {
                add("zxstest");
            }
        });
        wood.setCreateTime(new Date());

        DocumentObjectBinder binder = new DocumentObjectBinder();
        SolrInputDocument document = binder.toSolrInputDocument(wood);
        System.out.println(document);
        System.out.println(document.toString());
        Map<String, Object> valueMap = new HashMap<String, Object>();
        for (Map.Entry<String, SolrInputField> entry : document.entrySet()) {
            System.out.println(entry.getKey() + "==" + entry.getValue());
            valueMap.put(entry.getValue().getName(), entry.getValue().getValue());
        }
        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // ObjectOutputStream oos = new ObjectOutputStream(bos);
        // oos.writeObject(document);
        // System.out.println(bos.toByteArray());
        ObjectMapper customMapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        customMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 禁止使用int代表Enum的order()来反序列化Enum,非常危险
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 所有日期格式都统一为以下样式
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        Version ver = new Version(0, 0, 0, "SNP");
        SimpleModule module = new SimpleModule("StringModule", ver);
        customMapper.registerModule(module);
        System.out.println(customMapper.writeValueAsString(valueMap));
    }

}
