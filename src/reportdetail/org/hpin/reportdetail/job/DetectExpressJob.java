package org.hpin.reportdetail.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.util.HttpClientTool;
import org.hpin.reportdetail.entity.ErpExpressInfo;
import org.hpin.reportdetail.entity.ErpExpressInfoTemp;
import org.hpin.reportdetail.service.ErpExpressInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 检测快递状态
 * 
 * @author ybc
 * @since 2016-08-22
 */
public class DetectExpressJob {
	@Autowired
	ErpExpressInfoService erpExpressInfoService;
	
	private Logger log = Logger.getLogger(DetectExpressJob.class);

	// API地址
	//private static String APIUrl = "http://api.kuaidi.com/openapi.html?id=408bf6b3d9ad61e042944b2d0a1e5e15&show=0&muti=1&order=desc&com=zhongtong&nu=409607720396";
	
	public synchronized  void execute() {
		JSONObject jsonObject = null;
		JSONObject jsonObject2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//获取所有未收货的快递信息
		List<ErpExpressInfoTemp> expressInfoTemps = erpExpressInfoService.getExpressInfoTempList();
		
		if(expressInfoTemps!=null&&expressInfoTemps.size()>0){
			for (ErpExpressInfoTemp erpExpressInfoTemp : expressInfoTemps) {
				
				int count = erpExpressInfoTemp.getAccessCount();			//获取访问次数
				String expressNo = erpExpressInfoTemp.getExpressNo();
				String expressCompanyCode = erpExpressInfoTemp.getExpressCompanyCode();
				String APIUrl = assembleExpress(expressNo,expressCompanyCode);	//组装url
				log.info("APIUrl--"+APIUrl);
				String str = HttpClientTool.getInstance().httpGet(APIUrl);	//调用接口，返回的结果
				jsonObject = JSONObject.fromObject(str);
				log.info("接口返回数据--"+str);
				ErpExpressInfoTemp expressInfoTemp = null;
				ErpExpressInfo erpExpressInfo = null;
				List<ErpExpressInfoTemp> expressInfoTempsSun = erpExpressInfoService.getExpressInfoTempByNoCompany(expressNo, expressCompanyCode);
				List<ErpExpressInfo> expressInfos = erpExpressInfoService.getExpressInfoByNoCompany(expressNo, expressCompanyCode);
				
				if(expressInfoTempsSun!=null&&expressInfoTempsSun.size()>0){
					expressInfoTemp = expressInfoTempsSun.get(0);
				}
				if(expressInfos!=null&&expressInfos.size()>0){
					erpExpressInfo = expressInfos.get(0);
				}
				
				/* 解析JSON start*/
				boolean success = (Boolean) jsonObject.get("success");
				String data = jsonObject.getString("data");
				String transitTime = "";
				String context = "";
				if(data!=null&&!StringUtils.isEmpty(data)){
					jsonObject2 = JSONObject.fromObject(data.substring(1, data.length()-1));
					transitTime = jsonObject2.getString("time");
					context = jsonObject2.getString("context");
				}
				
				String status = jsonObject.getString("status");
				String reason = jsonObject.getString("reason");
				/* 解析JSON end*/
				
				if(success){	//是否查询成功
					try {
						if(expressInfoTemp!=null){
							expressInfoTemp.setAccessCount(count+1);	//访问次数+1
							expressInfoTemp.setStatus(status);
							expressInfoTemp.setUpdateTime(new Date());
						}
						if(erpExpressInfo!=null){
							erpExpressInfo.setStatus(status);
							erpExpressInfo.setExpressContent(context);
							erpExpressInfo.setUpdateTime(new Date());
							erpExpressInfo.setExpressCompanyCode(expressInfoTemp.getExpressCompanyCode());	//
							if(transitTime!=null&&!StringUtils.isEmpty(transitTime)){		//快递实时时间
								erpExpressInfo.setTransitTime(sdf.parse(transitTime));
							}
						}
						
						erpExpressInfoService.updateExpressInfo(erpExpressInfo);
						erpExpressInfoService.updateExpressInfoTemp(expressInfoTemp);
					} catch (Exception e) {
						log.info("success true : updateExpressInfo and updateExpressInfoTemp--"+e);
					}
					
				}else{
					expressInfoTemp = new ErpExpressInfoTemp();
					expressInfoTemp.setAccessCount(count+1);	//访问次数+1
					expressInfoTemp.setFailCause(reason);		//失败原因
					expressInfoTemp.setUpdateTime(new Date());	
					
					erpExpressInfo.setErrorCause(reason);
					erpExpressInfo.setUpdateTime(new Date());
					try {
						erpExpressInfoService.updateExpressInfoTemp(expressInfoTemp);
						erpExpressInfoService.updateExpressInfo(erpExpressInfo);
					} catch (Exception e) {
						log.info("success false : updateExpressInfo and updateExpressInfoTemp--"+e);
					}
				}
			}
		}
	}
	
	private String assembleExpress(String expressNo,String expressCompany){
		String str = "http://api.kuaidi.com/openapi.html?id=408bf6b3d9ad61e042944b2d0a1e5e15&show=0&muti=1&order=desc&com="+expressCompany+"&nu="+expressNo+"";
		return str;
	}
	
	public void test(){
		String APIUrl = "http://api.kuaidi.com/openapi.html?id=408bf6b3d9ad61e042944b2d0a1e5e15&show=0&muti=1&order=desc&com=zhongtong&nu=409607720396";
		JSONObject jsonObject = null;
		JSONObject jsonObject2 = null;
		String str = HttpClientTool.getInstance().httpGet(APIUrl);	//调用接口，返回的结果
		jsonObject = JSONObject.fromObject(str);
		
		String data = jsonObject.getString("data");
		jsonObject2 = JSONObject.fromObject(data.substring(1, data.length()-1));
		System.out.println("data="+data);
		String status = jsonObject.getString("status");
		String reason = jsonObject.getString("reason");
		
		String transitTime =  jsonObject2.getString("time");
		String content = jsonObject2.getString("context");
		
		System.out.println("status="+status+","+"reason="+reason);
		System.out.println("transitTime="+transitTime+","+"content="+content);
	}

}
