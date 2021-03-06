package org.hpin.foreign.service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.PropsUtils;
import org.hpin.common.util.Tools;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.foreign.dao.ErpReportScheduleJYDao;
import org.hpin.foreign.entity.ErpReportDetail;
import org.hpin.foreign.entity.ErpReportOrgJY;
import org.hpin.foreign.entity.ErpReportScheduleJY;
import org.hpin.foreign.entity.ErpReportUrlJY;
import org.hpin.foreign.util.DownloadThread;
import org.hpin.foreign.util.HttpUtils;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service(value = "org.hpin.foreign.service.ErpReportScheduleJYService")
@Transactional()
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpReportScheduleJYService extends BaseService {

	static Logger log = Logger.getLogger(ErpReportScheduleJYService.class);

	@Autowired
	ErpReportScheduleJYDao scheduleJYDao;

	@Autowired
	private ErpReportScheduleJYDao dao;

	@Autowired
	private ErpReportUrlJYService urlJYService;

	@Autowired

	private ErpCustomerService customerService;

	@Autowired
	private ErpEventsService eventsService;

	@Autowired
	private ErpReportOrgJYService orgJYService;

	@Autowired
	private ErpReportDetailService reportDetailService;

	@Autowired
	private ErpReportdetailPDFContentService pdfContentService;

	//报告保存的盘符
	private static final String DISK_NO = PropsUtils.getString("foreign","disk.no");
	//PDF报告保存的文件夹
	private static final String DIR_JY_RP = PropsUtils.getString("foreign","dir.jyRp");
	//报告单保存的文件夹
	private static final String DIR_JY_RP_DETAIL = PropsUtils.getString("foreign","dir.jyRpDetail");
	//预览路径前缀
	private static final String VIEW_PATH_HEAD = PropsUtils.getString("foreign","viewPath_head");

	static {
		log.info("DISK_NO: "+ DISK_NO );
		log.info("DIR_JY_RP: "+ DIR_JY_RP);
		log.info("DIR_JY_RP_DETAIL: "+ DIR_JY_RP_DETAIL);
		log.info("VIEW_PATH_HEAD: "+ VIEW_PATH_HEAD);
	}

	/**
	 * 根据条件精确查询
	 * @param params 查询条件
	 * @return List
	 * @throws Exception
	 */
	public List<ErpReportScheduleJY> listByProps(Map<String, String> params) throws Exception{
		return dao.listScheduleJobByProps(params, true);
	}

	/**
	 * 转换原始数据到定时任务表和URL信息表
	 * @param entity 原始数据表
	 */
	public void transferOrgData(ErpReportOrgJY entity){
		//原始数据转换为定时任务
		log.info("开始转换原始数据:  "+entity.toString());
		ErpReportScheduleJY scheduleJY = this.orgDataToSechedule(entity);

		if (scheduleJY!=null) {
			log.info("原始数据转换完成，定时任务：  "+scheduleJY.toString());
			//清除旧的定时任务数据
			boolean flag = dao.cleanOld(scheduleJY.getCode(), scheduleJY.getName());

			log.info("清除旧的定时任务：" + flag);
			if(flag) {
				//保存
				log.info("开始保存定时任务：" + scheduleJY.toString());
				dao.save(scheduleJY);
				log.info("定时任务：" + scheduleJY.toString() + " 保存成功!!!");

				//清除旧的URL数据
				boolean urlFlag = urlJYService.cleanOldUrl(scheduleJY.getCode(), scheduleJY.getName());
				log.info("清除旧的URL：" + urlFlag);
				if (urlFlag) {
					//PDF报告的URL
					String pdfUrl = entity.getPdfUrl();
					log.info("获取PDF报告地址：" + pdfUrl);
					//报告的URL信息
					ErpReportUrlJY urlJYPdf;
					//定时任务转换成报告地址表
					log.info("[" + pdfUrl + "] 转换成地址表... ");
					urlJYPdf = trasferToUrlInfo(scheduleJY, pdfUrl);
					log.info("转换地址表完成: " + urlJYPdf.toString());
					//保存
					if (urlJYPdf != null) {
						log.info("开始保存地址表...");
						urlJYService.save(urlJYPdf);
						log.info("保存地址表完成!!!");
					}
					try {

						//报告单
						ErpReportUrlJY urlJYPic;
						//报告单URL
						String rawResults = entity.getRawResults();
						JSONArray rawArr = new JSONArray(rawResults);
						String urlStr;
						for (int i = 0; i < rawArr.length(); i++) {
							//URL
							urlStr = rawArr.getString(i);
							//定时任务转换成报告地址表
							log.info("[" + urlStr + "] 转换成地址表... ");
							urlJYPic = trasferToUrlInfo(scheduleJY, urlStr);
							log.info("转换地址表完成: " + urlJYPdf.toString());
							if (urlJYPic != null) {
								log.info("开始保存地址表...");
								urlJYService.save(urlJYPic);
								log.info("保存地址表完成: " + urlJYPic.toString());
							}
						}
					} catch (JSONException e) {
						log.info(e);
					} catch (Exception e) {
						log.info(e);
					}

					//更新原始数据信息
					Integer count = entity.getCounter() + 1;

					entity.setCounter(count);
					entity.setUpdateTime(Calendar.getInstance().getTime());
					entity.setUpdateBy("websGene");
					entity.setUpdateUserId("0");
					entity.setStatus(1);//已处理状态
					orgJYService.update(entity);
					log.info("更新原始数据信息：" + entity.toString());
				}
			}

		}else {
			log.info("原始数据转换错误: "+entity.toString());
		}
	}


	/**
	 * 原始报告单信息转换成定时任务信息表
	 * @param orgObj 原始报告单信息表
	 * @return ErpReportScheduleJY
	 */
	private ErpReportScheduleJY orgDataToSechedule(ErpReportOrgJY orgObj){
		ErpReportScheduleJY scheduleJY = null;
		if(orgObj!=null) {
			String name = orgObj.getUserName();//会员姓名
			String code = orgObj.getBarcode();//会员条码
			//数据匹配情况标志
			Integer status = 9;//数据库会员信息不匹配
			//提示信息
			StringBuilder tipMsg = new StringBuilder("信息不匹配");
			//场次信息
			ErpEvents events = null;
			//数据库中的会员
			ErpCustomer customer = null;
			//添加查询条件
			Map<String,String> params = new HashMap<String, String>();
			params.put(ErpCustomer.F_NAME, name);
			params.put(ErpCustomer.F_CODE, code);
			params.put(ErpCustomer.F_ISDELETED, "0");//未删除的数据
			try {
				List<ErpCustomer> customerList = customerService.listCustomerByProps(params);
				if(!CollectionUtils.isEmpty(customerList)){
					if(customerList.size()==1){
						//根据条码姓名查到1条有效数据
						customer = customerList.get(0);
						if(customer!=null) {
							//根据条码查询场次信息
							List<ErpEvents> eventsList = eventsService.listEventsByInfo(code, "code");
							if (!CollectionUtils.isEmpty(eventsList)) {
								//且对应一个场次
								if (eventsList.size() == 1) {
									events = eventsList.get(0);
									if(events!=null){
										tipMsg = new StringBuilder("信息匹配");
										status = 0; //信息匹配
									}
								}
							}
						}
					}
				}

				/**
				 else {
				 //没有数据库中没有该会员
				 status = 5;
				 tipMsg = new StringBuilder("["+orgObj.getBarcode()+","+orgObj.getUserName()+" ] 该会员在数据库中不存在");
				 }
				 //根据条码查询场次信息
				 List<ErpEvents> eventsList = eventsService.listEventsByInfo(code, "code");
				 if (!CollectionUtils.isEmpty(eventsList)) {
				 if (eventsList.size() == 1) {
				 events = eventsList.get(0);
				 } else {
				 tipMsg.append("["+orgObj.getBarcode()+","+orgObj.getUserName()+" ] 该会员归属多个场次");
				 status = 7;//该会员归属多个场次
				 }
				 } else {
				 tipMsg.append("["+orgObj.getBarcode()+","+orgObj.getUserName()+" ] 该会员没有所属场次");
				 status = 6; //该会员没有归属场次
				 }
				 */

				scheduleJY = new ErpReportScheduleJY();

				scheduleJY.setIdRelated(orgObj.getId());
				scheduleJY.setCode(orgObj.getBarcode());
				scheduleJY.setName(orgObj.getUserName());

				scheduleJY.setGender(orgObj.getGender());
				scheduleJY.setBirthday(orgObj.getBirthday());
				scheduleJY.setPhone(orgObj.getPhone());

				if(customer!=null){
					scheduleJY.setCombo(customer.getSetmealName());
					scheduleJY.setEventsNo(customer.getEventsNo());
					scheduleJY.setIdNo(customer.getIdno());
				}

				if(events!=null) {
					scheduleJY.setBatchNo(events.getBatchNo());
					scheduleJY.setGroupOrderNo(events.getGroupOrderNo());
				}

				scheduleJY.setReportId(orgObj.getReportId());
				scheduleJY.setReportName(orgObj.getReportName());

				String samplingAtStr = orgObj.getSamplingAt();
				if(StringUtils.isNotEmpty(samplingAtStr)) {
					Date samplingDate = Tools.getDateFromStr(samplingAtStr, Tools.DATE_FORM_SIMPLE);
					scheduleJY.setSamplingDate(samplingDate);
				}

				String entryDateStr = orgObj.getEntryAt();
				if(StringUtils.isNotEmpty(entryDateStr)) {
					Date entryDate = Tools.getDateFromStr(entryDateStr, Tools.DATE_FORM_SIMPLE);
					scheduleJY.setEntryDate(entryDate);
				}

				String publishedDateStr = orgObj.getPublishedAt();
				if(StringUtils.isNotEmpty(publishedDateStr)) {
					Date publishedDate = Tools.getDateFromStr(publishedDateStr, Tools.DATE_FORM_SIMPLE);
					scheduleJY.setPublishedDate(publishedDate);
				}

				scheduleJY.setIsDeleted(0);
				scheduleJY.setCreateTime(Calendar.getInstance().getTime());
				scheduleJY.setCreateUserId("0");
				scheduleJY.setCreateUserName("金域");

				//备注信息
				scheduleJY.setRemark(tipMsg.toString());
				scheduleJY.setStatus(status);
				scheduleJY.setCounter(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return scheduleJY;
	}

	/**
	 * 根据定时任务表和URL转换成URL信息表
	 * @param entity 定时任务表
	 * @param url URL地址
	 * @return ErpReportUrlJY
	 */
	private static ErpReportUrlJY trasferToUrlInfo(ErpReportScheduleJY entity, String url){
		ErpReportUrlJY obj = null;
		if(entity!=null&&StringUtils.isNotEmpty(url)){
			obj = new ErpReportUrlJY();

			obj.setIdRelated(entity.getId());
			obj.setCode(entity.getCode());
			obj.setName(entity.getName());
			obj.setPhone(entity.getPhone());

			String fileType = Tools.getSuffix(url);
			obj.setFileType(fileType);
			obj.setUrl(url);

			obj.setIsDeleted(0);
			obj.setStatus(0);//初始状态为0
			obj.setCreateTime(Calendar.getInstance().getTime());
			obj.setCreateUserId(entity.getCreateUserId());
			obj.setCreateUserName(entity.getCreateUserName());

			obj.setCounter(0);
		}
		return obj;
	}

	/**
	 * @description 定时修改异常的定时任务
	 * @param arr 定时任务的状态
	 * @Integer 修改的条数
	 * @author YoumingDeng
	 * @since: 2016/12/8 10:29
	 */
	public Integer updateUnmatchSechedule(Integer[] arr)throws Exception{
		Integer updateNum = 0;
		Integer[] statusArr;
		if(arr!=null&&arr.length>0){
			statusArr = arr;
		}else {
			statusArr = new Integer[]{0, 1};
		}
		log.info("状态列表"+Arrays.toString(statusArr));
		log.info("根据状态列表查询不在状态列表内的定时任务...");
		log.info("开始查询金域定时任务...");
		//查询不匹配的定时任务
		List<ErpReportScheduleJY> list = dao.listByStatus(statusArr, "notIn");
		log.info("查询金域定时任务结束, 定时任务记录条数["+ list.size()+"]");
		if(!CollectionUtils.isEmpty(list)){
			//默认状态
			Integer status ;
			String remark ;
			for (ErpReportScheduleJY entiy: list) {
				status = 9;
				remark = "[name="+entiy.getName()+", code="+entiy.getCode()+"]定时任务信息已更新,信息不匹配!!!";
				//根据姓名和条码查询
				Map<String, String> params = new HashMap<String, String>();
				params.put(ErpCustomer.F_NAME, entiy.getName());
				params.put(ErpCustomer.F_CODE, entiy.getCode());
				params.put(ErpCustomer.F_ISDELETED, "0");//未删除的数据
				log.info("根据[name="+entiy.getName()+", code="+entiy.getCode()+"]查询会员信息...");
				List<ErpCustomer> customerList = customerService.listCustomerByProps(params);
				log.info("根据[name="+entiy.getName()+", code="+entiy.getCode()+"]查询完成！！！");
				ErpCustomer customer = null;
				ErpEvents events = null;
				if(!CollectionUtils.isEmpty(customerList)) {
					if(customerList.size()==1){
						customer = customerList.get(0);
						Map<String,String> eventsQuery = new HashMap<String, String>();
						eventsQuery.put(ErpEvents.F_EVENTSNO, customer.getEventsNo());
						eventsQuery.put(ErpEvents.F_ISDELETED, "0");
						List<ErpEvents> eventsList = eventsService.listEventsByProps(eventsQuery);
						if(!CollectionUtils.isEmpty(eventsList)){
							events = eventsList.get(0);
						}
						//满足该条件，则为匹配
						status = 0;
						remark = "[name="+entiy.getName()+", code="+entiy.getCode()+"]定时任务信息已更新,信息匹配";
					}
				}
				//计数
				//Integer counter = entiy.getCounter()==null?0:entiy.getCounter() + 1;
				//更新信息
				if(events!=null) {
					entiy.setBatchNo(events.getBatchNo());
					entiy.setEventsNo(events.getEventsNo());
					entiy.setGroupOrderNo(events.getGroupOrderNo());
				}
				if(customer!=null){
					entiy.setIdNo(customer.getIdno());
					entiy.setCombo(customer.getSetmealName());
				}
				//更新状态
				//entiy.setCounter(counter);
				entiy.setStatus(status);
				entiy.setRemark(remark);
				entiy.setUpdateTime(Calendar.getInstance().getTime());
				entiy.setUpdateUserId("0");
				entiy.setUpdateUserName("websGene");
				this.update(entiy);
				log.info(remark);
				updateNum += 1;
			}
		}
		log.info("本次执行的人数 ["+updateNum+"] 人");
		return updateNum;
	}

	public List<ErpReportScheduleJY> findByPage(Page page, Map map) {
		return scheduleJYDao.findByPage(page, map);
	}

	/**
     * @param customerId
	 * @param jyId
	 * 更新客户信息
	 */
	public void updateUserInfo(String customerId,String jyId)throws Exception {
		scheduleJYDao.updateupdateUserInfo(customerId,jyId);
	}

	/**
	 * 执行下载任务
	 * @param scheduleJYList
	 */
	public void dealDownload(List<ErpReportScheduleJY> scheduleJYList){
		log.info("查询未执行的定时任务数量: " + scheduleJYList.size());
		String savePath;
		String fileName;
		String url;
		//获取当前日期
		String nowDate = Tools.getTimeStr(Tools.DATE_FORM_SIMPLE_NO_LINE);
		Map<String, String> urlParams = new HashMap<String, String>();
		//子表
		List<ErpReportUrlJY> urlJYList = null;
		//会员
		ErpCustomer customer = null;
		List<ErpCustomer> customerList;
		//场次
		ErpEvents events = null;
		List<ErpEvents> eventsList;
		//查询条件Map
		Map<String, String> params;
		//文件夹后缀名
		String suffix;
		for (ErpReportScheduleJY scheduleJY : scheduleJYList) {
			//下载成功的数量
			int okNum = 0;
			//HTTP状态码
			String httpCode = null;
			String tipMsg = null;
			//文件保存的物理地址
			String filePath;
			//下载结果
			Map<String, String> result;
			//获取URL信息
			//urlJYList = scheduleJY.getReportUrlList();
			urlParams.clear();
			urlParams.put(ErpReportUrlJY.F_IDRELATED, scheduleJY.getId());
			urlParams.put(ErpReportUrlJY.F_ISDELETED, "0");
			try {
				urlJYList = urlJYService.listByProps(urlParams);
			} catch (Exception e) {
				log.info(e);
			}
			log.info("定时任务[" + scheduleJY.getName() + "," + scheduleJY.getCode() + "]的链接地址数量：" + urlJYList.size());
			if (!CollectionUtils.isEmpty(urlJYList)) {
				int numUrl = 0;
				for (ErpReportUrlJY urlObj : urlJYList) {
					if (urlObj.getStatus() == 0&&urlObj.getIsDeleted()==0) {
						savePath = null;
						url = urlObj.getUrl();
						suffix = urlObj.getFileType();
						//根据后缀名区分报告和报告单保存在不同的文件夹中
						if ("pdf".equalsIgnoreCase(suffix)) {
							savePath = DISK_NO + File.separator + DIR_JY_RP + File.separator + nowDate
										+ File.separator + scheduleJY.getBatchNo();
							//文件名
							fileName = urlObj.getCode() + "." + suffix;
						} else {
							//报告单: 按批次+套餐名分配
							savePath = DISK_NO + File.separator + DIR_JY_RP_DETAIL + File.separator + nowDate +
									File.separator + scheduleJY.getBatchNo() + File.separator + scheduleJY.getCombo();
							numUrl +=1;
							//文件名
							fileName = urlObj.getCode()+"_"+ numUrl + "." + suffix;
						}
						log.info("开始执行下载...");
						//下载
						if(StringUtils.isEmpty(url)||StringUtils.isEmpty(fileName)||StringUtils.isEmpty(savePath)){
							tipMsg = "url/fileName/savePath不完整: "+"[url: "+url+"], [fileName: "+fileName+"], [savePath: "+savePath+"]";
							log.info(tipMsg);
							continue;
						}
						result = downloadByUrl(url, fileName, savePath);
						log.info("下载执行完毕...");
						if (!CollectionUtils.isEmpty(result)){
							//获取提示信息
							tipMsg = "处理结果:["+result.get("msg") + "], 保存路径:[" +result.get(fileName)+"]";
							//获取状态码
							httpCode = result.get(HttpUtils.CODE);
						}
						log.info("状态码：" + httpCode);
						// 3. 根据下载结果更新 ERP_REPORT_SCHEDULE_JY 和 ERP_REPORT_URL_JY 表的信息
						//修改 erp_report_url_jy表中的状态
						if (httpCode != null) {
							urlObj.setHttpCode(Integer.valueOf(httpCode));
						}
						//下载成功
						if ("200".equals(httpCode)) {
							//获取文件保存的物理地址
							filePath = result.get(fileName);
							//文件大小
							Integer fileSize = Integer.valueOf(result.get("fileSize"));
							//更新为已下载状态
							urlObj.setStatus(1);

							params = new HashMap<String, String>();
							params.put(ErpCustomer.F_CODE, urlObj.getCode());
							params.put(ErpCustomer.F_NAME, urlObj.getName());
							params.put(ErpCustomer.F_ISDELETED, "0");

							try {
								customerList = customerService.listCustomerByProps(params);
								if (!CollectionUtils.isEmpty(customerList)) {
									customer = customerList.get(0);
								}
								//清除
								params.remove(ErpCustomer.F_NAME);
								params.remove(ErpCustomer.F_CODE);
								//按照场次号查询
								params.put(ErpReportScheduleJY.F_EVENTSNO, scheduleJY.getEventsNo());
								eventsList = eventsService.listEventsByProps(params);
								if (!CollectionUtils.isEmpty(eventsList)) {
									events = eventsList.get(0);
								}
							} catch (Exception e) {
								tipMsg = e.getMessage();
								log.info(e.getMessage());
							}
							if ("pdf".equalsIgnoreCase(suffix)) {
								//更新 ERP_CUSTOMER表的预览路径
								//更新预览地址
								if(StringUtils.isNotEmpty(filePath)) {
									String viewPath = filePath.replace(DISK_NO, VIEW_PATH_HEAD);
									log.info("["+customer.getCode()+" , "+ customer.getName()+"]的报告预览路径["+viewPath+"]");
									customer.setPdffilepath(viewPath);
									//把客户状态变更为 电子报告状态已出 add by Dayton 2016-12-21
									customer.setStatusYm(PropsUtils.getInt("status","statusYm.ycj"));
									//更新会员信息
									customerService.update(customer);
									log.info("["+customer.getCode()+" , "+ customer.getName()+" , "+customer.getEventsNo()+" ]的报告预览路径["+viewPath+"] 已更新!!!");
								}
							} else {
								//添加到报告单明细表
								if (customer != null && events != null) {
									this.saveErpReportDetail(customer, events, fileName, filePath, scheduleJY.getReportId(), reportDetailService);
								}
							}
							//添加到打印任务中
							//this.deal4PrintTask(filePath, fileSize, customer, events, pdfContentService, taskContentService);
							boolean doneFlag = pdfContentService.deal4PrintTask(filePath, fileSize, customer, events); // modify 2017-01-01
							if(doneFlag){
								log.info("["+customer.getCode()+" , "+ customer.getName()+"] 已添加到打印任务!!!");
							}else{
								log.info("["+customer.getCode()+" , "+ customer.getName()+"] 添加到打印任务失败...");
							}
						}
						urlObj.setRemark(tipMsg);
						urlObj.setUpdateUserId("0");
						urlObj.setUpdateUserName("websGene");
						urlObj.setUpdateTime(Calendar.getInstance().getTime());

						//计数
						Integer urlCounter = 0;
						if(urlObj.getCounter()==null){
							urlCounter += 1;
						}else{
							urlCounter = urlObj.getCounter()+1;
						}
						urlObj.setCounter(urlCounter);
						urlJYService.update(urlObj);

					}else{
						log.info("["+urlObj.getCode()+","+urlObj.getName()+"] 附件已下载，不再重复下载...");
					}
					//已下载的状态
					if (urlObj.getStatus() == 1) {
						okNum += 1;
					}
					//如果全部的报告和报告单都已下载customer,则修改 erp_report_schedule_jy 表中对应的状态
					if (okNum == urlJYList.size()) {
						//更新状态为已全部处理
						scheduleJY.setStatus(1);
					}
				}
			}
			//更新定时任务表
			Integer times = scheduleJY.getCounter()==null?0:scheduleJY.getCounter()+1;

			scheduleJY.setCounter(times);
			scheduleJY.setUpdateUserName("websGene");
			scheduleJY.setUpdateUserId("0");
			scheduleJY.setUpdateTime(Calendar.getInstance().getTime());
			log.info("更新定时任务: "+scheduleJY.toString());
			this.update(scheduleJY);
			log.info("定时任务: "+scheduleJY.toString()+ " 更新成功!!!");
		}
	}

	/**
	 * @description 根据信息下载报告/报告单
	 * @author YoumingDeng
	 * @since: 2016/12/1 14:43
	 */
	private Map<String, String> downloadByUrl(String url, String fileName, String savePath) {
		Map<String, String> result = null;
		if (StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(fileName) && StringUtils.isNotEmpty(savePath)) {
			DownloadThread downloadThread = new DownloadThread(url, fileName, savePath);
			ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) SpringTool.getBean("taskExecutor");
			Future<Map<String, String>> future = taskExecutor.submit(downloadThread);
			try {
				result = future.get();
			} catch (InterruptedException e) {
				log.info(e);
			} catch (ExecutionException e) {
				log.info(e);
			}
		}
		return result;
	}

	/**
	 * @description 保存报告单明细
	 * @author YoumingDeng
	 * @since: 2016/12/1 19:53
	 */
	private boolean saveErpReportDetail(ErpCustomer entity, ErpEvents events, String fileName, String reportDetailPath, String reportId,
										ErpReportDetailService reportDetailService) {
		boolean flag;
		Map<String, String> params = new HashMap<String, String>();
		ErpReportDetail reportDetailObj;
		List<ErpReportDetail> detailList;
		params.clear();
		params.put(ErpReportDetail.F_CODE, entity.getCode());
		params.put(ErpReportDetail.F_FILENAME, fileName);
		detailList = reportDetailService.listReportDetailByProps(params);
		if (!CollectionUtils.isEmpty(detailList)) {
			reportDetailObj = detailList.get(0);

			reportDetailObj.setFilePath(reportDetailPath);//物理地址

			String suffix = Tools.getSuffix(reportDetailPath);
			reportDetailObj.setFileSuffix(suffix);//后缀名

			//预览地址
			String viewPathDetail = reportDetailPath.replace(DISK_NO, VIEW_PATH_HEAD);
			log.info("["+entity.getCode()+" , "+ entity.getName()+"]的报告单预览路径["+viewPathDetail+"]");
			reportDetailObj.setViewPath(viewPathDetail);//预览地址

			reportDetailObj.setReportId(reportId);//金域报告ID，可用于重新获取报告
			reportDetailObj.setUpdateTime(Calendar.getInstance().getTime());
			//更新
			reportDetailService.update(reportDetailObj);
			log.info("方法[saveReportDetail]，场次号[" + events.getEventsNo() + "]，批次号[" + events.getBatchNo() + "]，团单号[" + events.getGroupOrderNo() + "]，" +
					" 姓名[" + entity.getName() + "]， 条码[" + entity.getCode() + "]，报告单[" + fileName + "]，保存位置[" + reportDetailPath + "]，已更新到报告单明细表!");
			flag = true;
		} else {
			//保存报告单信息
			reportDetailObj = new ErpReportDetail();

			reportDetailObj.setFileName(fileName);//文件名
			reportDetailObj.setFilePath(reportDetailPath);//物理地址
			String suffix = Tools.getSuffix(reportDetailPath);
			reportDetailObj.setFileSuffix(suffix);//后缀名

			//预览地址
			String viewPathDetail = reportDetailPath.replace(DISK_NO, VIEW_PATH_HEAD);
			log.info("["+entity.getCode()+" , "+ entity.getName()+"]的报告单预览路径["+viewPathDetail+"]");

			reportDetailObj.setViewPath(viewPathDetail);//预览地址
			reportDetailObj.setCustomerId(entity.getId());

			reportDetailObj.setName(entity.getName());
			reportDetailObj.setCode(entity.getCode());
			reportDetailObj.setGender(entity.getSex());
			reportDetailObj.setEventsNo(events.getEventsNo());
			reportDetailObj.setBatchNo(events.getBatchNo());

			reportDetailObj.setGroupOrderNo(events.getGroupOrderNo());
			reportDetailObj.setCreateTime(Calendar.getInstance().getTime());
			reportDetailObj.setReportId(reportId);//金域报告ID，可用于重新获取报告
			reportDetailObj.setStatus(0);//初始状态默认为0

			//保存
			reportDetailService.saveReportDetail(reportDetailObj);
			log.info("方法[saveReportDetail]，场次号[" + events.getEventsNo() + "]，批次号[" + events.getBatchNo() + "]，团单号[" + events.getGroupOrderNo() + "]，" +
					" 姓名[" + entity.getName() + "]， 条码[" + entity.getCode() + "]，报告单[" + fileName + "]，保存位置[" + reportDetailPath + "]，已保存到报告单明细表!");
			flag = true;
		}
		return flag;
	}
}
