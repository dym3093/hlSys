package org.hpin.events.dao;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hpin.common.util.StrUtils;
import org.hpin.events.entity.Express;
import org.hpin.events.util.ExpressUtil;
import org.springframework.stereotype.Repository;

@Repository()
public class ExpressDao {
	
	public List listExpress(String postid){
		String param  = ExpressUtil.expressQuery(postid);
		List<Express> list = new ArrayList<Express>();
		if(StrUtils.isNotNullOrBlank(param)){
			if(param.indexOf("successed")>0){
				String compTyp = param.substring(param.indexOf("compTyp")+10,param.indexOf("resultcode")-4);
				System.out.println(compTyp);
				param = param.substring(param.indexOf("["),param.indexOf("]")+1);
				JSONArray array = JSONArray.fromObject(param);
				if(array.size()>0){
					for(int i=0;i<array.size();i++){
						JSONObject json  = array.getJSONObject(i);
						Express express = new Express();
						express.setTime(json.getString("time"));
						express.setContext(json.getString("context"));
						express.setCompTyp(compTyp);
						express.setId(postid);
						list.add(express);
					}
				}
			}else{
				return null;
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		ExpressDao dao = new ExpressDao();
		dao.listExpress("700074134800");
	}
}
