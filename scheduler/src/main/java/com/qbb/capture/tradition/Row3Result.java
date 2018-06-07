package com.qbb.capture.tradition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qbb.util.calculator.finance.math.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Row3Result {

    public static String lottery(String issueNo)
    {
        StringBuilder sb = new StringBuilder();
        try {
            String url = "http://www.lottery.gov.cn/historykj/history.jspx?page=false&_ltype=pls&termNum=0&startTerm="+issueNo+"&endTerm="+issueNo;



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        lottery("");
    }

}
