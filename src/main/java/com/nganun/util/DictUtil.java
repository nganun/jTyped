package com.nganun.util;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictUtil {
    public static void main(String[] args) {
        System.out.println(getTranslate("time").toString());
    }

    public static List<String> getTranslate(String word) {
        List<String> contents = new ArrayList<>();
        word = word.replaceAll(" ", "+");
        String url = "https://www.bing.com/dict/search?q=" + word;
        CloseableHttpClient client = HttpClients.createDefault(); //创建一个可关闭的客户端
        HttpGet httpGet = new HttpGet(url);//创建post方法
        //设置头信息
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-cn");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "___rl__test__cookies=1526297207528; JSESSIONID=abc8T9npcpuvChUlnzEnw; _yd_newbanner_day=14; OUTFOX_SEARCH_USER_ID_NCOO=1006420317.710858; OUTFOX_SEARCH_USER_ID=1297994902@220.180.56.52");
        httpGet.setHeader("Host", "www.bing.com");
        httpGet.setHeader("Referer", "https://www.bing.com");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.0 Mobile/15E148 Safari/604.1");
        CloseableHttpResponse response = null;
        String web = null;

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            web = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(web);
        int i = web.indexOf("该词条暂未被收录");
        if (i == -1) {
            Document document = Jsoup.parse(web, "utf-8");
            String phonetic = document.getElementsByClass("hd_prUS b_primtxt").get(0).text();
            String tense = document.getElementsByClass("hd_div1").get(0).text();
            String description = document.getElementsByTag("ul").get(1).text();
            contents.add(word);
            contents.add(phonetic);
            contents.add(tense);
            contents.add(description);
            return contents;
        } else {
            return null;
        }
    }
}
