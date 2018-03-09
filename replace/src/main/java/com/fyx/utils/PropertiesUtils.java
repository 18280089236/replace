package com.fyx.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by hezhao on 2018/3/7.
 */
public class PropertiesUtils {
    /**
     * key value 是反的
     * @param path
     * @return
     * @throws Exception
     */
    public static Map<String,String> read(String path) throws Exception {
        final Properties properties = new Properties();
        InputStream in = new FileInputStream(path);
        properties.load(new InputStreamReader(in, "UTF-8"));
        Set<String> strings =properties.stringPropertyNames();
        Map<String,String> maps = new HashMap();
        for (String str : strings){
            maps.put(properties.getProperty(str),str);
        }
        in.close();
        return maps;
    }
}
