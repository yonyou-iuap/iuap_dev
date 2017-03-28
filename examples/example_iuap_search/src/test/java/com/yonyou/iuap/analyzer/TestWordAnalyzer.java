package com.yonyou.iuap.analyzer;

import org.junit.Test;

import com.yonyou.iuap.search.index.WordAnalyzer;

/**
 * 测试中文分词
 * Created by zengxs on 2016/3/7.
 */
public class TestWordAnalyzer {

    @Test
    public void testAnalyzer() {
        System.out.println(WordAnalyzer.analysis("乒乓球拍卖完了"));

        //英文同样可以这样使用
        System.out.println(WordAnalyzer.analysis("i'm fine"));
    }

}
