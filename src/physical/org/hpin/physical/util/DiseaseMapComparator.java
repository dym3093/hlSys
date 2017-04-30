package org.hpin.physical.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于对Disease map集合进行排序
 * @author tangxing
 * @date 2016-7-29下午1:34:46
 */

public class DiseaseMapComparator {

	public static LinkedHashMap<String, List<List<String>>> comparatorDiseaseMap(Map<String, List<List<String>>> diseaseMap){
		List<String> listDis = new ArrayList<String>();
		LinkedHashMap<String, List<List<String>>> hashMap = new LinkedHashMap<String, List<List<String>>>();
		listDis.add("头颈部疾病");
		listDis.add("呼吸系统疾病");
		listDis.add("消化系统疾病");
		listDis.add("泌尿系统疾病");
		listDis.add("精神类疾病");
		listDis.add("心脑血管疾病");
		listDis.add("代谢类疾病");
		listDis.add("皮肤系统疾病");
		listDis.add("血液系统疾病");
		listDis.add("免疫系统疾病");
		listDis.add("女性专科疾病");
		listDis.add("男性专科疾病");
		
		for (int i=0; i<listDis.size();++i) {
			Iterator<Map.Entry<String, List<List<String>>>> it = diseaseMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, List<List<String>>> entry = it.next();
				if(listDis.get(i).equals(entry.getKey())){
					hashMap.put(entry.getKey(), entry.getValue());
					break;
				}
			}
		}
		
		return hashMap;
	}
	
}
