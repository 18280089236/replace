package com.fyx.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hezhao on 2018/3/8.
 */
public class JsonpFileUtils {

    public static Document parserJsoup(File file) throws Exception {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String s = null;
        while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
            buffer.append(s.trim()+"\r\n");
        }

        String xml = buffer.toString();
        Document doc= Jsoup.parse(xml);
        bf.close();
        return doc;
    }

    public static int replace(Map<String, String> properties, Elements allElements) {
        int size = allElements.size();
        AtomicInteger num = new AtomicInteger(0);
        for (int i = 0; i < size; i++) {

            Element element = allElements.get(i);

            //查找是否包含map键
            String textContent = element.ownText();
            if (textContent == null || textContent.trim().equals("")) {
                continue;
            }
            properties.forEach((k, v) -> {
                if (textContent.length() < 20 && textContent.trim().indexOf(k) != -1) {
                    if (!element.hasClass("i18n")) {
                        element.addClass("i18n");
                    }
                    element.attr("fyx", v);
                    num.addAndGet(1);
                }
            });
        }
        return num.get();
    }

    public static void write(Document document, File file) {
        String html = document.html();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fileOutputStream);
            ps.println(html);// 往文件里写入字符串
            fileOutputStream.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


