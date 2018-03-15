package com.fyx.service;

import com.fyx.utils.FileUtils;
import com.fyx.utils.JsonpFileUtils;
import com.fyx.utils.PropertiesUtils;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by hezhao on 2018/3/7.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Map<String, String> properties = PropertiesUtils.read("C:\\Users\\hezhao\\Desktop\\版本1\\home\\zh-CN\\index.properties");
        properties.forEach((k,v) -> {
            System.out.println(k + "   =====   "+ v);
        });

        File file = new File("C:\\Users\\hezhao\\Desktop\\版本1\\");
        List<File> files = FileUtils.findFiles(file);
        for (int i = 0; i < files.size(); i++) {
            //解析html文件
            Document document = JsonpFileUtils.parserJsoup(files.get(i));
            //替换文档中的标签
            int replace = JsonpFileUtils.replace(properties, document.getAllElements());
//            FileUtils.replace(properties,document.getDocumentElement());
            //输出document 到文件
            JsonpFileUtils.write(document,files.get(i));
            System.out.println("替换项：  "+replace + " ===== " +files.get(i).getPath());
        }
    }
}
