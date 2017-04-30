package org.hpin.statistics.briefness.util;


/**
 * <p>@desc : 报表固定sql</p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-9-7 下午9:22:07</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
public class SqlConstant {
	
	/**
	 * 产品数据来源的会员采集信息报表
	 */
	public static String product_data_source = "select hc.customer_name, " +  //客户名称 
		       "hc.customer_code, " +  	//客户编号
		       "hc.id customer_id, " + 	//客户ID
		       "hp.product_name, " + 	//产品名称	
		       "hp.id product_id, " +	//产品ID
		       "hp.date_tran," +
		       "hsd.dictname datasource, " +	//数据来源
		       "count(hpm.id) total, " +	//总量
		       							//	"--01.新增 02修改 03续服 04退服" +
		       "sum(case when hpm.data_type='01' then 1 else 0 end) news, " +	//新增数量
		       "sum(case when hpm.data_type='02' then 1 else 0 end) edit, " +	//修改数量
		       "sum(case when hpm.data_type='03' then 1 else 0 end) continue, " +	//续服数量
		       "sum(case when hpm.data_type='04' then 1 else 0 end) outof   " +	//退服数量
		  "from HL_CUSTOMER          hc, " +
		       "HL_PRODUCT           hp, " +
		       "HL_PRODUCT_MEMBERS   hpm, " +
		       "hpin_system_dicttype hsd " +
		 "where hc.id = hpm.customer_id(+) " +
		   "and hpm.product_id = hp.id(+) " +
		   "and hp.date_tran = hsd.dictcode(+) " +
		 "group by hc.customer_name, " +
		          "hp.product_name, " +
		          "hc.customer_code, " +
		          "hp.id, " +
		          "hc.id, " +
		          "hsd.dictname ,  " +
		          "hp.date_tran";
	/**
	 * 会员数据采集汇总表
	 */
	public static String member_data_collection = "select " +
				"to_char(hpm.create_time,'yyyy-mm-dd') , " +
				"count(hpm.id) total " +		//总量
			"from HL_PRODUCT           hp , " +
				 "HL_PRODUCT_MEMBERS   hpm , " +
				 "hpin_system_dicttype hsd , " +
				 "Hl_Customer          hc " +
		    "where 1=1 " + 
		    	"and hpm.product_id=hp.id " +
		    	"and hp.date_tran = hsd.dictcode " +
		    	"and hpm.customer_id = hc.id " +
		    	/*"and hc.customer_name = ? " +	//合作机构
		    	"and and hp.product_name = ? " +		//产品
		    	"and hpm.create_time >= ? " +
		    	"and hpm.create_time<=? " +		//时间条件
		    	"and hp.date_tran=? " + 		//数据来源*/      
	  "group by to_char(hpm.create_time,'yyyy-mm-dd')" +
	  "order by to_char(hpm.create_time,'yyyy-mm-dd')";
   
	/**
	 * 会员信息统计
	 */
	public static String member_info_count = "select hp.product_name , " + //产品名称
				"hc.customer_name , " + 			//客户名称
    			"hp.product_type , " +
    			"count(hpm.id) total , " +		//总量	
    											//01.新增 02修改 03续服 04退服
    			"sum(case when hpm.data_type in ('01','02') then 1 else 0 end) valid , " + 	//有效客户数量
    			"sum(case when hpm.mobile1 is null then 0 " +
    			"when hpm.mobile2 is null then 0 " +
    			"when hpm.tel1 is null then 0 " +
    			"when hpm.tel2 is null then 0 else 1 end) contract , " + 	//有联系方式
    			"sum(case when hpm.mobile1 is null then 1 " +
    			"when hpm.mobile2 is null then 1 " +
    			"when hpm.tel1 is null then 1 " +
    			"when hpm.tel2 is null then 1 else 0 end) nocontract , " + 	//无联系方式 
    			"sum(case when hpm.idcard_num is null then 0 else 1 end) idcard , " + //有证件号数量 
    			"sum(case when hpm.idcard_num is null then 1 else 0 end) noidcard , " +  //无证件号数量  
    			"sum(case when hpm.plate_num is null then 0 else 1 end) carno , " + 	//有车牌号数量 
    			"sum(case when hpm.plate_num is null then 1 else 0 end) nocarno , " + //无车牌号数量 
    			"sum(case when hpm.email is null then 0 else 1 end) mail , " +	//有邮箱数量 
    			"sum(case when hpm.email is null then 1 else 0 end) nomail , " + 	//无邮箱数量
    			"sum(case when hpm.sex = '1011202' then 1 else 0 end) man , " +	//男数量 
    			"sum(case when hpm.sex = '1011201' then 1 else 0 end) woman , " +	//女数量
    			"sum(case when hpm.sex in ('1011201','1011202') then 0 else 1 end) gay ," +		//性别未知
    			"hsd1.dictname , " +
    			"hsd.dictname datasource " + 	//数据来源
    		"from HL_CUSTOMER          hc , " +
    			 "HL_PRODUCT           hp , " +
    			 "HL_PRODUCT_MEMBERS   hpm , " +
    			 "hpin_system_dicttype hsd , " +
    			 "hpin_system_dicttype hsd1 " +
    		"where 1=1 "+
    			"and hp.id = hpm.product_id (+) " +
    			"and hpm.customer_id=hc.id(+) " +
    			"and hp.date_tran = hsd.dictcode(+) " +
    			"and hp.product_type= hsd1.dictcode(+) " +
    			/*"and hc.customer_name = ? " + //合作机构
    			"and hp.product_name = ? " + //产品名称
    			"and hpm.create_time >= ? " +
    			"and hpm.create_time<= ? " + 	//时间条件
    			"and hp.date_tran = ? " +    	//数据来源
    			"and hp.product_type= ? " +*/ 	//产品类型
    		"group by hc.customer_name , " +
    				 "hp.product_name , " +
    				 "hp.product_type , " +
    				 "hsd1.dictname , " +
    				 "hsd.dictname ";   
	
	
			
}

