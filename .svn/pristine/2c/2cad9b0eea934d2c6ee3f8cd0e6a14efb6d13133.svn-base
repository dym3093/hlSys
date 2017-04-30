package org.hpin.physical.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 体检分析排序
 * @author ybc
 * @since 2016-07-01
 */
public class AnalyseTypeComparator implements Comparator<Entry<String,List<String>>> {
	
	private AnalyseTypeComparator(){}
	
	private static final AnalyseTypeComparator analyseTypeComparator = new AnalyseTypeComparator();
	
	public static AnalyseTypeComparator getInstance(){
		
		return analyseTypeComparator;
	}

	@Override
	public int compare(Entry<String,List<String>> o1, Entry<String,List<String>> o2) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("头颈部疾病",12);
		map.put("呼吸系统疾病",11);
		map.put("消化系统疾病",10);
		map.put("泌尿系统疾病",9);
		map.put("精神类疾病",8);
		map.put("心脑血管疾病",7);
		map.put("代谢类疾病",6);
		map.put("皮肤系统疾病",5);
		map.put("血液系统疾病",4);
		map.put("免疫系统疾病",3);
		map.put("女性专科疾病",2);
		map.put("男性专科疾病",1);
		int result = map.get(o1.getKey()) - map.get(o2.getKey()) < 0 ? 1 : -1 ;
		return result;
	}

}
