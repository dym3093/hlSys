/**
 * @author DengYouming
 * @since 2016-7-30 下午6:35:25
 */
package org.hpin.common.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 通用工具类
 * @author DengYouming
 * @since 2016-7-30 下午6:35:25
 */
public class Tools {

	public static final String DATE_FORMAT_DEFAULT = "yyyyMMddHHmmss";

	public static final String DATE_FORMAT_ALL = "yyyyMMddHHmmssSSS";

	public static final String DATE_FORM_SIMPLE_NO_LINE = "yyyyMMdd";

	public static final String DATE_FORM_SIMPLE = "yyyy-MM-dd";

	public static final String DATE_SIMPLE_STD_NO_LINE= "yyyyMMdd";

	public static final String DATE_SIMPLE_STD= "yyyy-MM-dd";

	public static final String DATE_SIMPLE_STD_TIME = "yyyy-MM-dd HH:mm:ss";
	//RFC822时区
	public static final String DATE_TIME_ZONE_STD = "yyyy-MM-dd HH:mm:ssZ";
	//格林尼治日期时间
	public static final String DATE_TIME_ZONE_GMT = "yyyy-MM-dd HH:mm:ssz";

	public static final String DATE_SIMPLE_DIAGONAL = "yyyy/MM/dd";

	public static final String DATE_SIMPLE_DIAGONAL_TIME = "yyyy/MM/dd HH:mm:ss";

	public static final String DATE_TIME_ZONE_DIAGONAL = "yyyy/MM/dd HH:mm:ssZ";

	public static final String DATE_TIME_ZONE_DIAGONAL_GMT = "yyyy/MM/dd HH:mm:ssz";

	public static final String UTF8 = "UTF8";

	//英文输入状态的[逗号]标点
	public static final String COMMA = ",";

	static SimpleDateFormat sdf = new SimpleDateFormat();

	public static boolean isEmpty(String content){
		return content==null||content.length()==0;
	}
	
	public static boolean isNotEmpty(String content){
		return !isEmpty(content);
	}
	
   /**
    * 列名转换成属性名
    * @param orgName
    * @return String
    * @author DengYouming
    * @since 2016-10-25 下午8:20:09
    */
   public static String colToField(String orgName){

	   StringBuffer buff = null;
	   String propName = ""; 
		//如果有下划线，转换成驼峰写法
		if(orgName.indexOf("_")>-1){
			buff = new StringBuffer();
			String[] strArr = orgName.split("_");				
			for (int j = 0; j < strArr.length; j++) {
				if(j==0){
					//第一个单词小写
					buff.append(strArr[j].toLowerCase());
					continue;
				}
				buff.append(firstCharUpcase(strArr[j]));
			}
			//说明已经过转换
			propName = buff.toString();
		}else{
			//如果是单个单词，直接转换成小写属性名
			propName = firstCharUpcase(orgName.toLowerCase());
		}
		return propName;
   }
 
	/**
	 * 首字母大写，其他全小写
	 * @param content
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-6 下午6:19:01
	 */
	private static String firstCharUpcase(String content){
		return content.substring(0, 1).toUpperCase().concat(content.substring(1).toLowerCase());
	}

	/**
	 * 获取 yyyyMMddHHmmss格式的时间字符串(例：201611251155)
	 * @return String
     */
	public static String getTimeStr(){
		return getTimeStr(null, null);
	}

	/**
	 * 根据传入的格式获取对应时间格式的字符串
	 * @param format 格式类型
	 * @return String
     */
	public static String getTimeStr(String format){
		return getTimeStr(null, format);
	}

	/**
	 * 根据传入的格式获取对应时间格式的字符串
	 * @param date 日期，为null时，默认为当前日期
	 * @param format 格式，为null时，默认为格式 yyyyMMddHHmmss
	 * @return String yyyyMMddHHmmss格式的字符串
     * @author Damian
	 * @Since 2017-02-22
	 */
	public static String getTimeStr(Date date, String format){
	    if (date==null){
	    	date = Calendar.getInstance().getTime();
		}
		if(format==null||format.trim().length()==0){
			format = DATE_FORMAT_DEFAULT;
		}
		sdf.applyPattern(format);
		return sdf.format(date);
	}
	 /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * create by henry.xu 20161125
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

	/**
	 * 根据指定的日期格式获取日期
	 * @param dateStr 日期字符串
	 * @param datePattern 日期格式
     * @return Date
     */
	public static Date getDateFromStr(String dateStr, String datePattern){
		Date date = null;
		if(datePattern==null|| datePattern.trim().length()==0){
			datePattern = DATE_SIMPLE_STD;
		}
		if(dateStr!=null&&dateStr.trim().length()>0){
			sdf.applyPattern(datePattern);
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 根据文件名获取其文件类型
	 * @param str 文件名
	 * @return String 后缀名
     */
	public static String getSuffix(String str){
		String suffix = null;
		if(isNotEmpty(str)&&str.contains(".")){
			suffix = str.substring(str.lastIndexOf(".")+1);
		}
		return suffix;
	}

	/**
	 * @description 查找项目中的文件
	 * @param fileName 文件名
	 * @author YoumingDeng
	 * @since: 2016/12/21 3:19
	 */
	public static List<File> findFile(String fileName){
		return findFile(fileName,null,true) ;
	}
	/**
	 * @description 查找项目中的文件
	 * @param fileName 文件名
	 * @param dir 查找的目录，如果为空，则从 /WEB-INF/classes中查找
	 * @param isExact 是否完全匹配查找，true:是，false:否
	 * @author YoumingDeng
	 * @since: 2016/12/20 2:57
	 */
	public static List<File> findFile(String fileName, String dir, boolean isExact ) {
		List<File> list = null;
		if (fileName != null && fileName.trim().length() > 0) {
			if (StringUtils.isEmpty(dir)) {
				//dir = System.getProperty("user.dir");
				dir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			}
			File dirFile = new File(dir);
			list = new ArrayList<File>();
			if (dirFile.exists() && dirFile.isDirectory()) {
				File[] files = dirFile.listFiles();
				if (files != null && files.length > 0) {
					File existDir = null;
					for (int i = 0; i < files.length; i++) {
						existDir = files[i];
						if (existDir.isDirectory() || !existDir.getPath().contains("lib")) {
							list.addAll(findFile(fileName, files[i].getPath(), isExact));
						}
					}
				}
			} else {
				//严格等于
				if (isExact) {
					if (fileName.equals(dirFile.getName())) {
						list.add(dirFile);
					}
				} else {
					if (StringUtils.containsIgnoreCase(dirFile.getName(), fileName)) {
						list.add(dirFile);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 随机产生7位长度的数字字符串
	 * @return String
	 * @author YoumingDeng
     */
	public static String getNumber7FromRandom(){
		Random r = new Random();
		int ranNum = r.nextInt(10000000);
		while(ranNum<1000000){
			ranNum = r.nextInt(10000000);
		}
		return String.valueOf(ranNum);
	}

	/**
	 * 字符串根据编码转换,默认UTF8编码格式
	 * @param content
	 * @return String
	 * @author Damian
	 * @since 2016-12-28
	 */
	public static String decodeStr(String content){
		return decodeStr(content,UTF8);
    }

	/**
	 * 字符串根据编码转换,默认UTF8编码格式
	 * @param content
	 * @param format 编码格式，null默认为UTF8
	 * @return String
	 * @author Damian
	 * @since 2016-12-28
	 */
	public static String decodeStr(String content, String format){
		String respStr = null;
		if(StringUtils.isNotEmpty(content)&& StringUtils.isNotEmpty(format)){
			if(StringUtils.isEmpty(format)){
				format = UTF8;
			}
			respStr = new String(content.getBytes(), Charset.forName(format));
		}
		return respStr;
	}

	/**
	 * 获取Map中的value
	 * @param map
	 * @param key
	 * @return String
	 */
	public static String getValTrim(Map<String,String> map, String key){
		String val = null;
		if(!CollectionUtils.isEmpty(map)&& StringUtils.isNotEmpty(key)){
			val = map.get(key);
			if(StringUtils.isNotEmpty(val)){
				val = val.trim();
			}
		}
		return val;
	}

	/**
	 * 自动匹配时间(试用版 mark 2017-02-23 15:47)
	 * @param dateStr
	 * @return Date
	 * @throws Exception
	 * @author Damian
	 * @since 2017-02-23 15:47
	 */
	public static Date parseDateAuto(String dateStr) throws Exception{
		Date date = null;
		if (StringUtils.isNotEmpty(dateStr)) {
			if (dateStr.indexOf("-")>-1){
				if (dateStr.length()==10){
					date = getDateFromStr(dateStr, DATE_SIMPLE_STD);
				} else if(dateStr.length()==19){
					date = getDateFromStr(dateStr, DATE_SIMPLE_STD_TIME);
				} else if(dateStr.length()>19&&dateStr.indexOf("GMT")>-1){
					date = getDateFromStr(dateStr, DATE_TIME_ZONE_GMT);
				} else {
					date = getDateFromStr(dateStr, DATE_TIME_ZONE_STD);
				}
			} else if (dateStr.indexOf("/")>-1){
				if (dateStr.length()==10) {
					date = getDateFromStr(dateStr, DATE_SIMPLE_DIAGONAL);
				} else if (dateStr.length()==19){
					date = getDateFromStr(dateStr, DATE_SIMPLE_DIAGONAL_TIME);
				} else if(dateStr.length()>19&&dateStr.indexOf("GMT")>-1){
					date = getDateFromStr(dateStr, DATE_TIME_ZONE_DIAGONAL_GMT);
				} else {
					date = getDateFromStr(dateStr, DATE_TIME_ZONE_DIAGONAL);
				}
			}
			else {
				throw new NoSuchFieldException("No such time format!!!");
			}
		}
	   	return date;
	}

	/**
	 * 切割带分隔符的字符串为数组，不带分隔符时返回其唯一元素的数组
	 * @param str 要分隔的字符串
	 * @param sign 分隔符,可为null
	 * @return String[]
	 * @author Damian
	 * @since 2017-03-10
	 */
	public static String[] strSplitArr(String str, String sign){
		String[] arr = null;
		if (StringUtils.isNotEmpty(str)){
			if (StringUtils.isNotEmpty(sign)){
				if (str.indexOf(sign)>-1){
					arr = str.split(sign);
				} else {
					arr = new String[]{str};
				}
			}else {
				arr = new String[]{str};
			}
		}
		return arr;
	}

	/**
	 * 切割以英文的逗号(,)为分隔符的字符串为数组
	 * @param str 要分隔的字符串
	 * @return String[]
	 * @author Damian
	 * @since 2017-03-10
	 */
	public static String[] strSplitArr(String str){
		return strSplitArr(str, COMMA);
	}

	/**
	 * 切割带分隔符的字符串List
	 * @param str 要分隔的字符串
     * @param sign 分隔符
	 * @return String[]
	 * @author Damian
	 * @since 2017-03-10
	 */
	public static List<String> strSplitList(String str, String sign){
		List<String> list = null;
		if (StringUtils.isNotEmpty(str)){
			String[] arr = strSplitArr(str, sign);
			if (arr!=null&&arr.length>0) {
			    list = new ArrayList<String>();
				for (int i = 0; i < arr.length; i++) {
					list.add(arr[i]);
				}
			}
		}
		return list;
	}

	/**
	 * 切割带英文逗号(,)的字符串为List
	 * @param str 要分隔的字符串
	 * @return String[]
	 * @author Damian
	 * @since 2017-03-10
	 */
	public static List<String> strSplitList(String str){
		return strSplitList(str, COMMA);
	}

	/**
	 * 转化成ORACLE的IN格式所需的字符串
	 * @param str
	 * @return String
	 * @author Damian
	 * @since 2017-03-10
	 */
	public static String strToOracleInLine(String str){
		return strToOracleInLine(str, COMMA);
	}

	/**
	 * 转化成ORACLE的IN格式所需的字符串
	 * @param str
	 * @param sign 原先字符串的分隔符号
	 * @return String
	 * @author Damian
	 * @since 2017-03-10
	 */
	public static String strToOracleInLine(String str, String sign){
		StringBuilder paramSql = new StringBuilder();
		String[] arr = strSplitArr(str, sign);
		if (arr!=null&&arr.length>0) {
			for (int i = 0; i < arr.length; i++) {
				paramSql.append("'" + arr[i] + "'"+COMMA);
			}
		}
		return paramSql.toString().substring(0, paramSql.toString().length()-1).trim();

	}
	public static void main(String[] args){
//		String prm = strToOracleInLine("taskId,sssk,sss");
//		System.out.println("prm: "+prm);
	}
}













