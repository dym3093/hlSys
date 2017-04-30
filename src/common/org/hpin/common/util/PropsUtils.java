package org.hpin.common.util;/**
 * Created by admin on 2016/12/20.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * 读取Properties文件
 *
 * @author Dayton
 * @author Dayton
 * @since 2016-12-20 12:37
 */
public class PropsUtils {

    static Logger log = Logger.getLogger(PropsUtils.class);

    private static Properties prop;

    public static void main(String[] args){
    }
    /**
     * @description 加载配置文件
     * @author YoumingDeng
     * @since: 2016/12/20 13:37
     */
    public static Properties loadProp(String propName){
        List<File> fileList = FileUtil.findFile(propName);
        if(fileList!=null&&fileList.size()>0){
            File propFile = fileList.get(0);
            try {
                InputStream is = new FileInputStream(propFile);
                if(is!=null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    prop = new Properties();
                    prop.load(br);
                    if(br!=null){
                        br.close();
                    }
                    is.close();
                }
            } catch (IOException e) {
                log.info(e);
            }
        }
        return prop;
    }

    /**
     * 获取配置文件的中Key对应的值
     * @param propName 属性文件名
     * @param key
     * @return String
     */
    public static String getString(String propName, String key){
        String value = null;
        if(StringUtils.isNotEmpty(propName)&&StringUtils.isNotEmpty(key)){
            if(!propName.contains(".properties")){
                propName = propName+".properties";
            }
            synchronized (PropsUtils.class) {
                if (prop != null) {
                    value = prop.getProperty(key);
                    if (StringUtils.isEmpty(value)) {
                        loadProp(propName);
                    }
                } else {
                    loadProp(propName);
                }
                if (StringUtils.isEmpty(value)) {
                    value = prop.getProperty(key);
                }
            }
        }
        return value;
    }

    /**
     * @description 把配置文件中的值转换成Integer
     * @author YoumingDeng
     * @since: 2016/12/20 13:38
     */
    public static Integer getInt(String propName, String key){
        Integer value = null;
        String tempStr = getString(propName, key);
        if(StringUtils.isNotEmpty(tempStr)){
            value = Integer.valueOf(tempStr);
        }
        return value;
    }
}
