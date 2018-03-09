package com.fyx.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hezhao on 2018/3/7.
 */
public class FileUtils {
    public static void write(Document document, File file) throws Exception {
        TransformerFactory tfac = TransformerFactory.newInstance();
        Transformer tra = tfac.newTransformer();
        DOMSource doms = new DOMSource(document);

        FileOutputStream outstream = new FileOutputStream(file);
        StreamResult sr = new StreamResult(outstream);
        tra.transform(doms, sr);
        outstream.close();

    }

    //    public static void replace(Map<String, String> properties, Element element) {
//        if (element == null) {
//            return;
//        }
//        String textContent = element.getTextContent();
//        if (textContent ==null ){
//            return;
//        }
//        textContent = textContent.trim();
//        System.out.println("*************         "+element.getNodeName()+"      "+ textContent.trim());
//        if (element != null && properties.containsKey(textContent)) {
//            //写属性  匹配到对应的中文后 标签里面添加 class="i18n" fyx="对应的key" 这两个内容
//            String classText = element.getAttribute("class");
//            if (null != classText) {
//                element.setAttribute("class", classText + "i18n");
//            } else {
//                element.setAttribute("class", "i18n");
//            }
//            element.setAttribute("fyx", properties.get(element.getTextContent()));
//            System.out.println("--------");
//        }
//        NodeList childNodes = element.getChildNodes();
//        if (childNodes.getLength() != 0) {
//            for (int i = 0; i < childNodes.getLength(); i++) {
//                HTMLElementImpl item = (HTMLElementImpl) childNodes.item(1);
//                if (item.getNodeName().indexOf("BODY") != -1){
//                    System.out.println("aaa");
//                }
//                Element node1 = item.getPreviousElementSibling();
//
////                Element node1 = childNodes.item(i);
//                replace(properties, node1);
//            }
//        }
//
//    }
    public static void replace(Map<String, String> properties, Node node) {
        if (node == null) {
            return;
        }


        if (node.getNodeType() == Node.TEXT_NODE) {//如果是一个文本节点
            //查找是否包含map键
            String textContent = node.getTextContent();
            if (textContent == null || textContent.trim().equals("")) {
                return;
            }
            properties.forEach((k, v) -> {
                if (textContent.length() < 20 && textContent.contains(k)) {
                    Element element = (Element) node.getParentNode();
                    String classText = element.getAttribute("class");
                    if (null == classText) {
                        element.setAttribute("class", "i18n");
                    } else if (classText.indexOf("i18n") == -1) {
                        element.setAttribute("class", classText + " i18n");
                    } else {
                    }
                    element.setAttribute("fyx", properties.get(element.getTextContent()));
                    System.out.println("element.getTagName" + element.getTagName());
                }
            });

        }
        if (node.hasChildNodes()) {// 如果该节点含有子节点，表明它是一个Element。这时候遍历
            NodeList childNodes = node.getChildNodes();
            System.out.println("childNodes.getLength()++++=====" + childNodes.getLength());
            if (childNodes.getLength() != 0) {
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node item = childNodes.item(i);
                    if (item.getNodeName().indexOf("BODY") != -1) {
                        System.out.println("aaa");
                    }
//                Element node1 = childNodes.item(i);
                    replace(properties, item);
                }
            }
        }

    }

    private static List<File> files = new ArrayList<>();

    public static List<File> findFiles(File file) {
        if (file.isFile()) {
            if (file.getName().indexOf(".html") != -1) {
                files.add(file);
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                findFiles(files[i]);
            }
        }
        return files;
    }


}