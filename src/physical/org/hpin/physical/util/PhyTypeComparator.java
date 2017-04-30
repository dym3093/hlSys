package org.hpin.physical.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 个性化体检排序
 * @author ybc
 * @since 2016-07-01
 */
public class PhyTypeComparator implements Comparator<Entry<String, String>> {
	
	private PhyTypeComparator(){}
	
	private static final PhyTypeComparator phyTypeComparator = new PhyTypeComparator();
	
	public static PhyTypeComparator getInstance(){
		
		return phyTypeComparator;
	}

	@Override
	public int compare(Entry<String, String> o1, Entry<String, String> o2) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("血液检验",6);
		map.put("体液检验",5);
		map.put("超声",4);
		map.put("影像",3);
		map.put("激素六项",2);
		map.put("其他",1);
		int result = map.get(o1.getKey()) - map.get(o2.getKey()) < 0 ? 1 : -1 ;
		return result;
	}

}
