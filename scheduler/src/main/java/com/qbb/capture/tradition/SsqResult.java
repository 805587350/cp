package com.qbb.capture.tradition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qbb.util.calculator.finance.math.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class SsqResult {

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
        String url = "http://www.zhcw.com/ssq/kjgg/" + htmlId + ".shtml";
        Document doc = Jsoup.parse(new URL(url), 10000);
        Element currentScript = doc.getElementById("currentScript");
        String con = currentScript.text();
        JSONObject kj = JSON.parseArray(con).getJSONObject(0);
        StringBuffer sb = new StringBuffer();

        String kj_issue = kj.getString("KJ_ISSUE");
        String kj_z_num = kj.getString("KJ_Z_NUM");
        String kj_t_num = kj.getString("KJ_T_NUM");
        String awardNum = kj_z_num + kj_z_num;

        String one_z = kj.getString("ONE_Z");
        String one_j = kj.getString("ONE_J");
        String two_z = kj.getString("TWO_Z");
        String two_j = kj.getString("TWO_J");
        String three_z = kj.getString("THREE_Z");
        String three_j = kj.getString("THREE_J");
        String four_z = kj.getString("FOUR_Z");
        String four_j = kj.getString("FOUR_J");
        String five_z = kj.getString("FIVE_Z");
        String five_j = kj.getString("FIVE_J");
        String six_z = kj.getString("SIX_Z");
        String six_j = kj.getString("SIX_J");

        sb.append(kj.getString(""));
        return sb.toString();
    }

    public static String remand(String issueNo) throws IOException {
        try {
            String url = "http://app.zhcw.com/wwwroot/zhcw/jsp/kjggServ.jsp?catalogId=14609&issueNo=" + issueNo;
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
        SsqResult.zhcw("2018063");
    }

}
