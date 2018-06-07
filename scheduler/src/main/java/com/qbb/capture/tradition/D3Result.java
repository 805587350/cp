package com.qbb.capture.tradition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbb.util.calculator.finance.math.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class D3Result {

    /**
     * 根据期号获取双色球开奖号码
     * 格式：一等奖/一等奖奖金/中奖注数#二等奖/二等奖奖金/二等奖中奖注数
     *
     * @param issueNo
     * @return
     */
    public static String zhcw(String issueNo) {
        try {
            String htmlId = remand(issueNo);
            System.out.println("获取3D第（"+issueNo+"）期开奖htmlId=" + htmlId);
            if (StringUtils.isEmpty(htmlId)) {
                return null;
            }
            return parsingAward(htmlId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("中彩网获取双色球开奖号码异常！");
        }
        return null;
    }

    public static String parsingAward(String htmlId) throws IOException {
        String url = "http://www.zhcw.com/3d/kjgg/" + htmlId + ".shtml";
        Document doc = Jsoup.parse(new URL(url), 10000);
        StringBuffer sb = new StringBuffer();

        Elements balls = doc.select("blueball_bigst");
        List<String> awards = new ArrayList();
        for (Element item : balls) {
            awards.add(item.text());
        }
        String awardNum = getD3AwardNum(awards);

        String singleRecords = "", singleMoney = "";
        String group3Records = "", group3Money = "";
        String group6Records = "", group6Money = "";
        Elements trs = doc.select("result_tab tr");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            if(tds.get(0).text().equals("单选")){
                singleRecords = replaceNonnumeric(tds.get(1).text());
                singleMoney = replaceNonnumeric(tds.get(1).text());
            }
            if(tds.get(0).text().equals("组选3")){
                group3Records = replaceNonnumeric(tds.get(1).text());
                group3Money = replaceNonnumeric(tds.get(2).text());
            }
            if(tds.get(0).text().equals("组选6")){
                group6Records = replaceNonnumeric(tds.get(1).text());
                group6Money = replaceNonnumeric(tds.get(2).text());
            }
        }

        sb.append("单选").append("/").append(singleRecords).append("/").append(singleMoney).append("#");
        sb.append("组选3").append("/").append(group3Records).append("/").append(group3Money).append("#");
        sb.append("组选6").append("/").append(group6Records).append("/").append(group6Money);

        return sb.toString();
    }

    private static String replaceNonnumeric(String text) {
        return text.replaceAll("\\D+", "");
    }

    private static String getD3AwardNum(List<String> list) {
        Collections.sort(list);
        return list.get(0) + "/" + list.get(1) + "/" + list.get(2);
    }

    public static String remand(String issueNo) throws IOException {
        try {
            String url = "http://app.zhcw.com/wwwroot/zhcw/jsp/kjggServ.jsp?catalogId=14757&issueNo=" + issueNo;
            Document doc = Jsoup.parse(new URL(url), 10000);
            JSONObject jsonObject = JSON.parseObject(doc.text());
            System.out.println("获取开奖页面html返回结果" + jsonObject.toString());
            if (!jsonObject.isEmpty() && jsonObject.getInteger("code") == 1) {
                return jsonObject.getString("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        D3Result.zhcw("2018063");
    }

}
