package org.hpin.children.service;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hpin.children.dao.ErpGroupOrderComboDao;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.children.service.ErpGroupOrderComboService")
@Transactional()
public class ErpGroupOrderComboService extends BaseService{
	
	@Autowired
	private ErpGroupOrderComboDao egocDao;
	
	/**
	 * @param page
	 * @param searchMap
	 * @return 儿童套餐信息
	 */
	public List getComboPriceList(Page page, Map<String,Object> searchMap) {
		
		return egocDao.findByPage(page, searchMap);
	}

	/**
	 * @param page
	 * @param company
	 * @param totalCompany
	 * @return 要操作的儿童套餐
	 */
	public List<ErpCompanyComboPrice> getComboPrice(String company,String totalCompany) {
		List <Map<String, Object>> combolist=egocDao.getComboList(company,totalCompany);
		List <Map<String, Object>> priceList= egocDao.getComboPriceList(company);
		List <ErpCompanyComboPrice> list=new ArrayList<ErpCompanyComboPrice>();
		if(combolist.size()>=1){
//			BigDecimal bd=null;					//套餐价格
			BigDecimal guidancePrice=null;		//指导价格
			BigDecimal settlementPrice=null;	//渠道价格
			BigDecimal payedPrice=null;			//客户支付价格
			String  comboNickname=null;			//套餐价格
			Map<String, Object> map1=new HashMap<String,Object>();
			Map<String, Object> map2=new HashMap<String,Object>();
			map1=(Map)combolist.get(0);
			String combo=map1.get("COMBO").toString();
			String comboName="";
			for(String str:combo.split(",")){
				if(!str.contains("儿童")){
					continue;
				}
				ErpCompanyComboPrice eccp=new ErpCompanyComboPrice();
				for (int i=0;i<priceList.size();i++) {
					map2=(Map)priceList.get(i);
					if(str.equals(map2.get("COMBO").toString())){
//						bd=(BigDecimal)map2.get("COMBO_PRICE");
						guidancePrice=(BigDecimal)map2.get("GUIDANCE_PRICE");
						settlementPrice=(BigDecimal)map2.get("SETTLEMENT_PRICE");
						payedPrice=(BigDecimal)map2.get("PAYED_PRICE");
						comboNickname=(String)map2.get("COMBO_NICKNAME");
						comboName=map2.get("COMBO").toString();
					}
				}
				if (!str.equals("") && comboName.equals(str)) {
					eccp.setCombo(str);
//					eccp.setComboPrice(bd);
					eccp.setGuidancePrice(guidancePrice);
					eccp.setSettlementPrice(settlementPrice);
					eccp.setPayedPrice(payedPrice);
					eccp.setComboNickname(comboNickname);
					list.add(eccp);
				}else {
					eccp.setCombo(str);
					list.add(eccp);
				}
			}
		}
		return list;
	}

	public String getCompanyId(String company) {
		return egocDao.getCompanyId(company);
	}
	
	public int setComboPrice(List<Map<String, Object>> jsonList,String company) {
		int count1 =0;
		int count2=0;
		try {
			for(Map<String, Object> mapList :jsonList){
				if (egocDao.isExistsCombo(company,mapList.get("combo").toString())>=1) {
					StringBuilder sql = new StringBuilder();
					String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					sql.append("update erp_company_combo_price ecp set ecp.combo_nickname ='"+mapList.get("nickName")+"',ecp.payed_price='"+mapList.get("payedPrice")+"',"
							+ "ecp.guidance_price='"+mapList.get("guidancePrice")+"',ecp.settlement_price='"+mapList.get("settlementPrice")+"'"
							+ ",ecp.update_time=to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),ecp.update_user='"+mapList.get("userName")+"'"
									+ " WHERE ecp.company='"+mapList.get("company")+"' AND ecp.combo ='"+mapList.get("combo")+"'");
					count1 = egocDao.updateChildrenCombo(sql.toString());
				}else {
					String sql="insert into erp_company_combo_price(id,combo,guidance_price,settlement_price,combo_nickname,payed_price,company,company_id,create_time,create_user_id,create_user,combo_id,status)"
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";	
					Object[] params = new Object[] { getUUID(), mapList.get("combo"),mapList.get("guidancePrice"),mapList.get("settlementPrice"),
							mapList.get("nickName"),mapList.get("payedPrice"), mapList.get("company"),mapList.get("companyId"),new Date(),mapList.get("userId"),
							mapList.get("userName"),getUUID(),"0" };
					int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.DECIMAL,Types.VARCHAR, Types.DECIMAL, Types.VARCHAR,Types.VARCHAR,
							Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR };
					count2 = egocDao.insertChildrenCombo(sql,params,types);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count1+count2;
		
	}
	
	public String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
