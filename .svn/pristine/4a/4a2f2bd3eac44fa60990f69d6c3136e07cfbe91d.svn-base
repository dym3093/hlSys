package org.hpin.venueStaffSettlement.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carly
 * @since 2017年2月22日10:57:59
 * 用于会议费用管理
 */
public class FeesTypeList {
	
	private static List<String> list = new ArrayList<String>();
	private static Map<String,String> map = new HashMap<String, String>();
	private static Map<String,String> belongMap = new HashMap<String, String>();
	static {
		/*
		 * 费用名称
		 */
		list.add("市内交通费");
		list.add("往返交通费");
		list.add("住宿费");
		list.add("劳务费");
		list.add("其他");
		
		/*
		 * 费用所对应的类型
		 */
		map.put("市内交通费", "0");
		map.put("往返交通费", "1");
		map.put("住宿费", "2");
		map.put("劳务费", "3");
		map.put("其他", "4");
		
		/*
		 * 所属费用名称
		 */
		belongMap.put("0", "市内交通费");
		belongMap.put("1", "往返交通费");
		belongMap.put("2", "住宿费");
		belongMap.put("3", "劳务费");
		belongMap.put("4", "其他");
		
	}
	
	/**
	 * @since 2017年2月22日10:57:46
	 * @author Carly
	 * 返回是否包含这些费用类型
	 */
	public static final List<String> getFeesList(){
		
		return list;
	}
	
	/**
	 * @since 2017年2月22日11:10:53
	 * @author Carly
	 * @return 返回费用类型
	 */
	public static final Map<String,String> getFeesTypeMap(){
		
		return map;
	}
	
	/**
	 * @since 2017年2月22日16:23:31
	 * @author Carly
	 * 返回费用类型对应的名称
	 * @return
	 */
	public static final Map<String,String> getBelongName() {
		
		return belongMap;
	}
}
