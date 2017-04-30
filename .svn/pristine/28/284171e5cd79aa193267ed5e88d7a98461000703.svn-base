package org.hpin.physical.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProjectMapComparator {



	
	public static List<LinkedHashMap<String, List<Map<String,String>>>> mapComparator(List<HashMap<String, List<Map<String,String>>>> projectMapList ){
		List<String> listDis = new ArrayList<String>();
		
		listDis.add("血液检验");
		listDis.add("体液检验");
		listDis.add("超声");
		listDis.add("影像");
		listDis.add("激素六项");
		listDis.add("其他");
		
		List<LinkedHashMap<String, List<Map<String,String>>>> hashMap = new ArrayList<LinkedHashMap<String, List<Map<String,String>>>>();
		
		LinkedHashMap<String, List<Map<String,String>>> linkedMap = new LinkedHashMap<String, List<Map<String,String>>>();
		for (int j = 0; j < listDis.size(); j++) {
			String list = listDis.get(j);
			System.out.println("该次的目录----"+list);
			for (HashMap<String, List<Map<String,String>>> mapListMap : projectMapList) {
				for (Entry<String, List<Map<String,String>>> entry : mapListMap.entrySet()) {
					if(list.equals(entry.getKey())){
						linkedMap.put(entry.getKey(), entry.getValue());
						break;
					}
				}
			}
			
			/*for (int i=0; i<projectMapList.size();++i) {
				Iterator<Entry<String, List<Map<String, String>>>> it = projectMapList.get(i).entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, List<Map<String, String>>> entry = it.next();
					
				}
			}*/
		}
		hashMap.add(linkedMap);
		return hashMap;
	}
	
}
