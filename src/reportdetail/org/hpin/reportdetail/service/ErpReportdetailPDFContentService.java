package org.hpin.reportdetail.service;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.reportdetail.dao.ErpPrintTaskContentDao;
import org.hpin.reportdetail.dao.ErpReportUnMatchDao;
import org.hpin.reportdetail.dao.ErpReportdetailPDFContentDao;
import org.hpin.reportdetail.dao.ErpReportdetailPDFTaskDao;
import org.hpin.reportdetail.entity.*;
import org.hpin.reportdetail.util.DealWithFileUtil;
import org.hpin.reportdetail.util.FileList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service(value = "org.hpin.reportdetail.service.ErpReportdetailPDFContentService")
@Transactional()
public class ErpReportdetailPDFContentService extends BaseService {
	
	private Logger log = Logger.getLogger(ErpReportdetailPDFContentService.class);
	
	@Autowired
	ErpReportdetailPDFContentDao dao;
	@Autowired
	ErpCustomerService custoemrService;
	@Autowired
	CustomerRelationshipDao shipDao;
	@Autowired
	ErpReportUnMatchDao unMatchDao;
	@Autowired
	ErpReportdetailPDFTaskDao taskDao;
	@Autowired
	ErpPrintTaskContentDao taskContentDao;
	@Autowired
	private ErpPrintTaskContentService contentService;

	public void save(ErpReportdetailPDFContent pdfContent){
		dao.save(pdfContent);
	}
	
	public void save(List<ErpReportdetailPDFContent> pdfList){
		dao.save(pdfList);
	}
	
	public List<ErpReportdetailPDFContent> queryContents(String batchno){
		return dao.queryContents(batchno);
	}
	
	public List<ErpReportdetailPDFContent> queryContentsByCode(String code){
		return dao.queryContentsByCode(code);
	}
	
	public List<ErpReportdetailPDFContent> queryContentsByCodes(String codes){
		return dao.queryContentsByCodes(codes);
	}
	
	public List<String> dealContents(String batchno){
		List<ErpReportdetailPDFContent> list = queryContents(batchno);
		List<String> results = new ArrayList<String>();
		for(ErpReportdetailPDFContent content : list){
			results.add(content.getCode());
		}
		return results;
	}
	
	//查询当前批次号已入库的文件名
	public List<String> queryPdfFileName(String batchno){
		return dao.queryPdfFileNameByNo(batchno);
	}

	//存储过程数据去重
	public boolean dealRepeatContents(String batchno) throws Exception{
		return dao.dealRepeatContents(batchno);
	}
	
	//匹配未入库的PDF文件
	public List<String> matchPdfInByPath(String pdfpath,List<String> pdfNameList) {
		List<String> unInPath = new ArrayList<String>();
		File pdfFile = new File(pdfpath);
		File[] files = pdfFile.listFiles();
		if(0==files.length){
			return null;
		}
		for(File curFile : files){
			String fileName = curFile.getName();
			if(pdfNameList.contains(fileName)){
				continue;
			}else{
				unInPath.add(curFile.getAbsolutePath());
			}
		}
		return unInPath;
	}
	
	/**
	 * 根据指定路径处理PDF内容并存入数据库并返回处理的PDF数量
	 * @param pdfPath
	 * @throws Exception
	 */
	public int dealPdfContentByPath(String pdfPath,String batchno){
		int pdfnum = 0;
		List<ErpReportdetailPDFContent> list = new ArrayList<ErpReportdetailPDFContent>();
		File pdfDir = new File(pdfPath);
		File files[]=pdfDir.listFiles();
		if(null==files){
			return pdfnum;
		}
		for(int i = 0; i<files.length;i++){
			try{
				File currFile = files[i];
				ErpReportdetailPDFContent pdf = DealWithFileUtil.getInstance().buildPDFContentBean(currFile);
				pdf.setCreatedate(new Date());
				pdf.setBatchno(batchno);
				list.add(pdf);
				//入库
				if(list.size()==500){
					log.info("list size : "+list.size());
					save(list);
					pdfnum += list.size();
					System.out.println("list size : "+list.size());
					list.clear();
				}
				if(i==files.length-1 && list.size()>0){
					save(list);
					pdfnum += list.size();
					log.info("files will over ,list size : "+list.size());
					list.clear();
				}
			}catch(Exception e){
				log.error("dealPdfContentByPath has error", e);
				continue;
			}
		}
		return pdfnum;
	}
	
	/**
	 * @since 2016年11月7日16:44:38
	 * @author Carly
	 * @param pdfPath
	 * @param batchno
	 * @return 
	 */
	public int dealPdfByPath(String curFileDir,String pdfPath,String batchno){
		int pdfnum = 0;
		List<ErpReportdetailPDFContent> list = new ArrayList<ErpReportdetailPDFContent>();
		File pdfDir = new File(pdfPath);
		File files[]=pdfDir.listFiles();
		if(null==files){
			return pdfnum;
		}
		for(int i = 0; i<files.length;i++){
			try{
				File currFile = files[i];
				ErpReportdetailPDFContent pdf = null;
				int t = FileList.pdfType().get(curFileDir);	//获取文件夹类型
				int convertionType = FileList.convertionFileType().get(curFileDir);
				pdf = DealWithFileUtil.getInstance().createPDFContent(currFile,t,convertionType);
				pdf.setCreatedate(new Date());
				pdf.setBatchno(batchno);
				list.add(pdf);
				//入库
				if(list.size()==500){
					log.info("list size : "+list.size());
					save(list);
					pdfnum += list.size();
					list.clear();
				}
				if(i==files.length-1 && list.size()>0){
					save(list);
					pdfnum += list.size();
					log.info("files will over ,list size : "+list.size());
					list.clear();
				}
			}catch(Exception e){
				log.error("dealPdfContentByPath has error", e);
				continue;
			}
		}
		return pdfnum;
	}
	
	/**
	 * 根据文件列表存入数据库
	 * @param filePath
	 */
	public int dealPdfContentByFile(List<String> filePath,String batchno){
		int pdfnum = 0;
		List<ErpReportdetailPDFContent> list = new ArrayList<ErpReportdetailPDFContent>();
		File currFile = null;
		for(int i=0;i<filePath.size();i++){
			try{
				currFile = new File(filePath.get(i));
				ErpReportdetailPDFContent pdf = DealWithFileUtil.getInstance().buildPDFContentBean(currFile);
				pdf.setBatchno(batchno);
				list.add(pdf);
				//入库
				if(list.size()==500){
					save(list);
					log.info("list size : "+list.size());
					pdfnum += list.size();
					list.clear();
				}
				if(i==filePath.size()-1 && list.size()>0){
					save(list);
					log.info("files will over ,list size : "+list.size());
					pdfnum += list.size();
					list.clear();
				}
			}catch(Exception e){
				log.error("dealPdfContentByFile has error", e);
				continue;
			}
		}
		return pdfnum;
	}

	/**
	 * 查询PDF客户信息未入库的信息
	 */
	public List<Map<String,String>> queryPdfCntUnInDB(){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		List<ErpReportdetailPDFContent> list = dao.queryPdfCusInfoNotInDB();
		for(ErpReportdetailPDFContent content : list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("batchno",content.getBatchno());
			map.put("filepath",content.getFilepath());
			result.add(map);
		}
		return result;
	}
	
	public List<ErpReportdetailPDFContent> queryPdfUnRecord(){
		return dao.queryPdfCusInfoNotInDB();
	}
	
	/**
	 * 将本批次号未成功提取的PDF重复读取
	 */
	public Map<String,String> addPdfCusInfo(String batchno){
		Map<String,String> result = new HashMap<String,String>();
		List<String> pdfPath = new ArrayList<String>();
		List<ErpReportdetailPDFContent> list = dao.queryPdfCusInfoNotInDB(batchno);
		List<ErpReportdetailPDFContent> readPdf = new ArrayList<ErpReportdetailPDFContent>();
		List<ErpReportdetailPDFContent> inDBPdf = new ArrayList<ErpReportdetailPDFContent>();
		if(null==list||0==list.size()){
			result.put("needNum", "0");
			return result;
		}
		for(ErpReportdetailPDFContent content:list){
			pdfPath.add(content.getFilepath());
		}
		if(pdfPath.size()!=0){
			for(String path : pdfPath){
				File currFile = new File(path);
				ErpReportdetailPDFContent pdf = DealWithFileUtil.getInstance().buildPDFContentBean(currFile);
				if(1==pdf.getIsrecord()){
					readPdf.add(pdf);
				}
			}
		}
		for(ErpReportdetailPDFContent pdf :readPdf){
			String fileName = pdf.getPdfname();
			String md5 = pdf.getMd5();
			for(ErpReportdetailPDFContent dbContent:list){
				if(fileName.equals(dbContent.getPdfname())&&md5.equals(dbContent.getMd5())){
					dbContent.setUsername(pdf.getUsername());
					dbContent.setAge(pdf.getAge());
					dbContent.setCode(pdf.getCode());
					dbContent.setSex(pdf.getSex());
					dbContent.setIsrecord(1);
					dbContent.setUpdatedate(new Date());
					inDBPdf.add(dbContent);
				}
			}
		}
		dao.addPdfCusInfo(inDBPdf);
		result.put("needNum", String.valueOf(list.size()));
		result.put("successNum", String.valueOf(inDBPdf.size()));
		return result;
	}
	
	//提取需要打印未分批的PDF
	public List<ErpReportdetailPDFContent> getWillPrintPdf(){
		return dao.getWillPrintPdf();
	}
	public List<ErpPrintTaskContent> getPrintPDF(){
		return dao.getPrintPDF();
	}
	
	//分组查询
	public List<PrintBatchInfoBean> getBatchPrint() {
		String branchCompanyId = "";
		String branchCompanyName = "";
		List<PrintBatchInfoBean> batchInfoController = new ArrayList<PrintBatchInfoBean>();	//存放设置好支公司名字的集合
		List<PrintBatchInfoBean> batchInfoBeans = dao.getBatchPrint();
		log.info("getBatchPrint batchInfoBeans size : "+batchInfoBeans.size());
		try{
			for (PrintBatchInfoBean printBatchInfoBean : batchInfoBeans) {
				branchCompanyId = printBatchInfoBean.getBranch_company();		//获取到支公司ID
				branchCompanyName = getBranchCompanyNameById(branchCompanyId);	//根据id查到支公司name
				printBatchInfoBean.setBranch_company_name(branchCompanyName);	//设置支公司name
				CustomerRelationShip ship = shipDao.findShipByCompanyId(branchCompanyId);
				if(null!=ship){
					printBatchInfoBean.setOwnedcompany(ship.getCustomerNameSimple());
					printBatchInfoBean.setOwnedcompanyid(ship.getOwnedCompany());
				}
				batchInfoController.add(printBatchInfoBean);					//将修改好的Bean添加到集合
				
			}
		}catch(Exception e){
			log.error("ErpReportdetailPDFContentService getBatchPrint", e);
		}
		log.info("getBatchPrint batchInfoController size : "+batchInfoBeans.size());
		return batchInfoController;
	}
	
	public List<PrintBatchInfoBean> getBatchPrint2() {
		String branchCompanyId = "";
		String branchCompanyName = "";
		List<PrintBatchInfoBean> batchInfoController = new ArrayList<PrintBatchInfoBean>();	//存放设置好支公司名字的集合
		List<PrintBatchInfoBean> batchInfoBeans = dao.getBatchPrint2();
		log.info("getBatchPrint batchInfoBeans size : "+batchInfoBeans.size());
		try{
			for (PrintBatchInfoBean printBatchInfoBean : batchInfoBeans) {
				branchCompanyId = printBatchInfoBean.getBranch_company();		//获取到支公司ID
				branchCompanyName = getBranchCompanyNameById(branchCompanyId);	//根据id查到支公司name
				printBatchInfoBean.setBranch_company_name(branchCompanyName);	//设置支公司name
				CustomerRelationShip ship = shipDao.findShipByCompanyId(branchCompanyId);
				if(null!=ship){
					printBatchInfoBean.setOwnedcompany(ship.getCustomerNameSimple());
					printBatchInfoBean.setOwnedcompanyid(ship.getOwnedCompany());
				}
				batchInfoController.add(printBatchInfoBean);					//将修改好的Bean添加到集合
				
			}
		}catch(Exception e){
			log.error("ErpReportdetailPDFContentService getBatchPrint", e);
		}
		log.info("getBatchPrint batchInfoController size : "+batchInfoBeans.size());
		return batchInfoController;
	}
	
	/**
	 * 根据id查找对应公司的name
	 * @return
	 * @author tangxing
	 * @date 2016-5-9下午6:56:19
	 */
	private String getBranchCompanyNameById(String branchCompanyId){
		String companyName="";
		List<Map<String, Object>> lists = dao.getBranchCompanyName(branchCompanyId);
		log.info("getBranchCompanyNameById list size :"+lists.size());
		if(null!=lists&&0<lists.size()){
			companyName = lists.get(0).get("branch_commany").toString();
			return companyName;
		}else{
			return branchCompanyId;
		}
		
	}
	
	//更新打印信息
	public void updateContentPrintInfo(List<ErpReportdetailPDFContent> willPrintPdfs){
		List<ErpReportdetailPDFContent> list = new ArrayList<ErpReportdetailPDFContent>();
		for(ErpReportdetailPDFContent content : willPrintPdfs){
			if(null!=content.getPrintbthno()||!("").equals(content.getPrintbthno())){
				list.add(content);
			}
		}
		if(list.isEmpty()){
			return ;
		}
		dao.updateContentPrintInfo(list); 
	}
	
	//根据打印批次号获取PDF信息
	public List<ErpReportdetailPDFContent> getContentsByPrintNo(String printNo){
		List<ErpReportdetailPDFContent> list = new ArrayList<ErpReportdetailPDFContent>();
		list = dao.findContentsByPrintNo(printNo);
//		if(null==list||list.size()==0){
//			return null;
//		}
		return list;
	}
	
	/**
	 * @param nostr
	 * @return 根据打印批次号获取taskContent信息
	 */
	public List<ErpPrintTaskContent> getTaskContentByBatchNo(String nostr) {
		List<ErpPrintTaskContent> list = new ArrayList<ErpPrintTaskContent>();
		list = dao.getTaskContentByBatchNo(nostr);
//		if(null==list||list.size()==0){
//			return null;
//		}
		return list;
	} 
	
	public void updateContentPrintInfo2(List<ErpPrintBatch> list) throws Exception{
		dao.updateContentPrintInfo2(list); 
	}

	public void updateContentPrintInfo(List<ErpReportdetailPDFContent> contentList, String printTaskNos) throws Exception {
		dao.updateContentPrintInfo(contentList, printTaskNos);
	}
	/**
	 * @param contentList
	 * 更新ErpPrintTaskContent，erp_repeat_print_task表为已打印
	 */
	public void updatePrintTaskContent(List<ErpPrintTaskContent> contentList) throws Exception{
		dao.updatePrintTaskContent(contentList);
	}
	
	/**
	 * @param taskContentList 
	 * @param printTaskNo
	 * 更新ErpPrintTaskContent，erp_repeat_print_task表为已打印
	 */
	public void updateTaskContentPrintInfo(List<ErpPrintTaskContent> taskContentList, String printTaskNo)  throws Exception{
		dao.updateTaskContentPrintInfo(taskContentList, printTaskNo);
	}

	//根据打印ID获取PDF信息
	public List<ErpReportdetailPDFContent> getContentsByPrintId(String printNo) {
		return dao.findContentsByPrintId(printNo);
	}

	public List<ErpReportUnRecordView> getUnRecordView(List<ErpReportdetailPDFContent> list) {
		List<ErpReportUnRecordView> viewList = new ArrayList<ErpReportUnRecordView>();
		for(int i=0;i<list.size();i++){
			ErpReportUnRecordView view = new ErpReportUnRecordView();
			view.setContentid(list.get(i).getId());
			view.setPdfname(list.get(i).getPdfname());
			view.setFilepath(list.get(i).getFilepath());
			view.setCreatedate(list.get(i).getCreatedate());
			/*File currFile = new File(list.get(i).getFilepath());
			Map<String,String> pdfMap = DealWithFileUtil.getInstance().readPdfContent(currFile);
			if(pdfMap.containsKey("content")&&pdfMap.get("content")!=null){
				view.setContent(pdfMap.get("content"));
			}else{
				view.setContent("");
			}*/
			viewList.add(view);
		}
		return viewList;
	}

	public String getPdfPathById(String modifyid) {
		ErpReportdetailPDFContent content = (ErpReportdetailPDFContent)dao.findById(ErpReportdetailPDFContent.class, modifyid);
		return content.getFilepath();
	}

	public void readPdfContent(String[] pdfid) {
		List<ErpReportdetailPDFContent> list = new ArrayList<ErpReportdetailPDFContent>();
		for(int i=0;i<pdfid.length;i++){
			String filePath = getPdfPathById(pdfid[i]);
			File file = new File(filePath);
			ErpReportdetailPDFContent content = DealWithFileUtil.getInstance().buildPDFContentBean(file);
			if(null!=content.getUsername()){
				content.setId(pdfid[i]);
				list.add(content);
			}
		}
		dao.updateContentInfo(list);
	}

	public void dealPdfAndCusView(Page page) {
		List<ErpReportdetailPDFContent> result = page.getResults();
		List<MatchPdfAndCusBean> lists = new ArrayList<MatchPdfAndCusBean>();
		for(ErpReportdetailPDFContent content : result){
			MatchPdfAndCusBean pdfcusBean = new MatchPdfAndCusBean();
			pdfcusBean.setPdfid(content.getId());
			pdfcusBean.setPdfcode(content.getCode());
			pdfcusBean.setPdfusername(content.getUsername());
			pdfcusBean.setPdffilename(content.getPdfname());
			pdfcusBean.setCreatedate(content.getCreatedate());
			List<ErpCustomer> cuslist = custoemrService.getCustomerByCode(content.getCode());
			if(cuslist!=null&&cuslist.size()!=0){
				pdfcusBean.setCuscode(cuslist.get(0).getCode());
				pdfcusBean.setCusid(cuslist.get(0).getId());
				pdfcusBean.setCusname(cuslist.get(0).getName());
			}
			lists.add(pdfcusBean);
		}
		page.setResults(lists);
	}
	
	public List<ErpReportdetailPDFContent> queryContentsByUnMatch3(String batchno){
		List<ErpReportdetailPDFContent> result = dao.queryUnMatchContents3(batchno);
		return result;
	}
	
	public List<ErpReportdetailPDFContent> queryContentsByUnMatch4(String batchno){
		List<ErpReportdetailPDFContent> result = dao.queryUnMatchContents4(batchno);
		return result;
	}

	public List<PrintBatchInfoBean> getManualPrintBatch(){
		return dao.getManualPrintBatch();
	}
	
	public void updateContentState2(String id){
		dao.updateContentState2(id);
	}
	
	public void updateMoreContentInfo(ErpCustomer customer,String ymPrint,String pdfid){
		dao.updateMoreContentInfo(customer, ymPrint, pdfid);
	}
	
	//查询matchstate为4的id
	public List<String> queryContentInit(String batchno){
		return dao.queryContentInit(batchno);
	}
	
	//获取未匹配成功的数据
	public List<ErpReportdetailPDFContent> queryContentUnMatch(){
		return dao.queryContentMatchBy3();
	}
	
	//获取异常数据
	public List<ErpReportdetailPDFContent> queryContentAbnormal(){
		return dao.queryContentMatchBy4();
	}
	
	//会员信息多条
	public List<ErpReportdetailPDFContent> queryContentMoreCustomer(){
		return dao.queryContentMatchBy5();
	}

	public int countMatchState(String batchno, int state) {
		return dao.countMatchState(batchno, state);
	}
		
	public List<String> dealListId2Str(List<ErpReportdetailPDFContent> contentList){
		List<String> pdfids = new ArrayList<String>();
		for(ErpReportdetailPDFContent content : contentList){
			if(null!=content.getCode()&&!("").equals(content.getCode())){
				pdfids.add(content.getId());
			}
		}
		return pdfids;
	}
		
	//获取批次号
	public HashSet<String> dealListBatchno2Set(List<ErpReportdetailPDFContent> contentList){
		HashSet<String> batchnoSet = new HashSet<String>();
		if(contentList.size()>0){
			for(ErpReportdetailPDFContent content : contentList){
				if(null!=content.getBatchno()&&!("").equals(content.getBatchno())){
					batchnoSet.add(content.getBatchno());
				}
			}
		}
		return batchnoSet;
	}
	
	//处理未匹配的数据
	public void dealUnMachPdf(List<ErpReportdetailPDFContent> pdfUnMatchList)  throws Exception{
		List<ErpReportMatchBean> contentBeanList = new ArrayList<ErpReportMatchBean>();
		List<String> batchnoList = new ArrayList<String>();
		Date date = new Date();
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		String userName = user.getAccountName();
		for(ErpReportdetailPDFContent content : pdfUnMatchList){
			ErpReportMatchBean bean = getMatchBeanDO(content);
			contentBeanList.add(bean);
		}
		
		//循环匹配(与会员信息匹配) start
		for(ErpReportMatchBean bean : contentBeanList){
			Date now = new Date();
			boolean flag = true;
			String name = bean.getName().replaceAll(" ", "");
			List<ErpCustomer> cusLists = dao.getCustomerDto(bean,name); 	//根据条码,性别,条码获取客户信息
			//会员信息没有此数据(未匹配成功)
			if (0 == cusLists.size()) {
				dao.updateMatchState(userName,3,bean.getId());
				dao.updateUnmatchState(userName,"1",bean.getId());
				//获取PDF的批次号
				if (!batchnoList.contains(bean.getPdfBthNo())) {
					batchnoList.add(bean.getPdfBthNo());
					
				}
				if(0<batchnoList.size()){//更新报告任务
					for(String batchno : batchnoList){
						int [] matchState = countFileTaskState(batchno); 
						taskDao.updateFileTaskState(batchno, matchState);
					}
				}
				continue;
				
			} else {
				for(ErpCustomer customer:cusLists){
					if (!batchnoList.contains(bean.getPdfBthNo())) {
						batchnoList.add(bean.getPdfBthNo());
					}
					String ymPrint = "9";// 不需要远盟打印
					String [] comboArray = customer.getSetmealName().split("[+]");
					for(int i=0;i<comboArray.length;i++){
						if(comboArray[i].equals(bean.getCombo())){
							boolean existedPrintCombo = dao.existedPrintCombo(bean.getCombo());//是否是打印套餐
							
							if (existedPrintCombo) {
								ymPrint = "88";// 是打印套餐
								boolean existedUnPrint = dao.existedUnprint(bean);
								boolean existedProjectCodePrint = dao.existedProjectCodePrint(customer.getEventsNo(),bean.getCombo());
								
								if (existedUnPrint) {
									ymPrint = "99";// 人工确认不需要打印
									
								} else if (existedProjectCodePrint) {
									ymPrint = "0";	//如果项目编码对应的有这个套餐则打印
								}
							} 
							
							ErpPrintTaskContent printTaskContent = getPrintTaskContent(bean,customer,date);
							boolean isPA = customer.getEventsNo().toUpperCase().startsWith("PA");	//是否是平安的场次
							if (ymPrint.equals("0") && !isPA) {
								dao.getHibernateTemplate().save(printTaskContent);

							} else if (isPA) {
								dao.getHibernateTemplate().save(getPAReportTask(bean,date));
							}
							dao.updateExistedCustomerPdf(customer);
							dao.updatePdfContentInfo(userName, customer, ymPrint, printTaskContent, 2, now, bean);

//							Integer ycj = PropsUtils.getInt("status","statusYm.ycj"); // add by Damian 2017-03-08
							dao.updateCustomerFilePath(bean, 300, customer.getId());
							if(FileList.fileType().contains(bean.getReportType())){
								ErpReportdetailImgtask imgtask = getReportdetailImgTask(bean, customer);
								dao.getHibernateTemplate().save(imgtask);	//保存需要转换的pdf
							}
							dao.updateUnmatchState(userName, "3", bean.getId());
							flag = false;
							break;
						}
					}
				}
				
				if(0<batchnoList.size()){//更新报告任务
					for(String batchno : batchnoList){
						int [] matchState = countFileTaskState(batchno);
						taskDao.updateFileTaskState(batchno, matchState);
					}
				}
				if(flag){//如果通过条码找到多个客户并且没有匹配的套餐时
					dao.updateUnmatchState(userName,"1",bean.getId());
				}
			}
			
		}
	}
	
	//通过ID获取批次号
	public String findBatchnoById(String unmatchid) {
		String batchno = "";
		ErpReportdetailPDFContent content = (ErpReportdetailPDFContent)dao.findById(ErpReportdetailPDFContent.class, unmatchid);
		if(null!=content){
			batchno = content.getBatchno();
		}
		return batchno;
	}
	
	//查询本批次需要进行匹配的数据
	public List<ErpReportdetailPDFContent> queryWillMathPdfByBthno(String batchno) {
		return dao.queryWillMathPdfByBthno(batchno);
	}
	
	//取消当前PDF打印
	/**
	 * @author Carly
	 * @since 2017年3月23日17:28:50
	 * @param pdfid id
	 * @param batchNo 批次号
	 * @param unMatchId 不匹配表id
	 * @throws Exception
	 */
	public void cancelPrint(String pdfid, String batchNo, String unMatchId) throws Exception {
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		dao.cancelPrint(pdfid);
		unMatchDao.updateState(user, "3", unMatchId);
		int [] matchState = countFileTaskState(batchNo); 
		taskDao.updateFileTaskState(batchNo, matchState);
	}
	
	//通过条形码查询打印批次号
	public HashSet<String> queryPrintBthnoByCode(String code) {
		HashSet<String> printBthnos = dao.queryPrintBthnoByCode(code);
		return printBthnos;
	}

	public List<ErpReportdetailPDFContent> getPdfSize(String code) {
		String sql ="from ErpReportdetailPDFContent where code=?";
		return dao.getHibernateTemplate().find(sql, code);
	}
	
	//判断是否在不打印表中  true 是 ; false 否
	public boolean isUnPrint(String code,String name,String sex){
		if(dao.hasDataInUnPrint(code,name,sex)>=1){
			return true;
		}
		return false;
	}

	/**
	 * @param printPdfs
	 * 处理完批次信息之后更新printTaskContent的ps状态
	 */
	public void updateTaskContentInfo(List<ErpPrintTaskContent> printPdfs) {
		List<ErpPrintTaskContent> list = new ArrayList<ErpPrintTaskContent>();
		for(ErpPrintTaskContent content : printPdfs){
			if(null!=content.getPrintBatchNo()||!("").equals(content.getPrintBatchNo())){
				list.add(content);
			}
		}
		if(list.isEmpty()){
			return ;
		}
		dao.updateTaskContentInfo(list); 
		
	}

	public void updateTaskContentInfo2(List<ErpPrintBatch> batchBeanList) {
		dao.updateTaskContentInfo2(batchBeanList);
	}

	public void updateTaskContentPrintTaskNos (String printBatchNos,String printTaskNos) throws Exception{
		String sql = "update erp_print_task_content set printtaskno=? where printbatchno=?";
		String [] printBatchNo = printBatchNos.split(",");
		for(int i = 0;i <printBatchNo.length;i++){
			dao.getJdbcTemplate().update(sql, printTaskNos,printBatchNo[i]);
		}
	}
	
	private ErpPrintTaskContent getPrintTaskContent(ErpReportMatchBean content,ErpCustomer customer,Date date){
//		CustomerRelationShip companyShip = dao.getHibernateTemplate().get(CustomerRelationShip.class,customer.getBranchCompanyId());
		//根据客户ID获取HL_CUSTOMER_RELATIONSHIP_PRO项目信息表的ID
		String eventSql = "from ErpEvents where events_no=?";
		ErpEvents events = (ErpEvents) dao.getHibernateTemplate().find(eventSql,customer.getEventsNo()).get(0);	
		//根据查询到的场次获取项目编码
		CustomerRelationShipPro project = dao.getHibernateTemplate().get(CustomerRelationShipPro.class,events.getCustomerRelationShipProId());
		ErpPrintTaskContent taskContent = new ErpPrintTaskContent();
		taskContent.setPdfContentId(content.getId());
		taskContent.setFilePath(content.getFilePath());
		taskContent.setCode(content.getCode());
		taskContent.setUserName(content.getName());
		taskContent.setGender(content.getSex());
		taskContent.setCombo(content.getCombo());
		taskContent.setBatchNo(content.getPdfBthNo());
		
		taskContent.setAge(customer.getAge());
		taskContent.setProvince(customer.getProvice());
		taskContent.setCity(customer.getCity());
		taskContent.setSaleman(customer.getYmSalesman());//远盟营销员
		taskContent.setCustomerId(customer.getId());
		taskContent.setDept(customer.getDepartment());
		
		taskContent.setBranchCompanyId(events.getBranchCompanyId());
		taskContent.setOwnedCompanyId(events.getOwnedCompanyId());
		taskContent.setProjectCode("");
		if (null != project) {
			taskContent.setProjectCode(project.getProjectCode());
		}
		taskContent.setCreateTime(date);
		taskContent.setIsManuallyAdd("0");
		taskContent.setPs("0");
		String type = FileList.reportType().get(content.getReportType());
		taskContent.setType(type);
		taskContent.setReportType(content.getReportType());//0基因报告,3无创，4微磁
		return taskContent;
	}
	
	/**
	 * @param bean
	 * @param date
	 * @return 需要传给平安的报告
	 */
	private ErpReportPATask getPAReportTask(ErpReportMatchBean bean, Date date) {
		ErpReportPATask paTask = new ErpReportPATask();
		paTask.setCode(bean.getCode());
		paTask.setName(bean.getName());
		paTask.setUploadState(0);
		paTask.setBatchNo(bean.getPdfBthNo());
		paTask.setFilePath(bean.getFilePath());
		paTask.setCreateTime(date);
		paTask.setIsDeleted(0);
		
		return paTask;
	}
	
	/**
	 * @since 2017年2月28日15:57:46
	 * @author Carly
	 * @param bean
	 * @param customer
	 * @return 需要转换图片的报告
	 */
	private ErpReportdetailImgtask getReportdetailImgTask(ErpReportMatchBean bean,ErpCustomer customer) {
		
		String birthdaySql = "from ErpCustomerTempWuChuang where code =? ";		//获取生日
		List<ErpCustomerTempWuChuang> customerWuChuangList = dao.getHibernateTemplate().find(birthdaySql,bean.getCode());
		ErpReportdetailImgtask imgtask = new ErpReportdetailImgtask();
		imgtask.setCustomerId(customer.getId());
		imgtask.setCode(bean.getCode());
		imgtask.setUserName(bean.getName());
		if(null != customer.getIdno()){
			imgtask.setIdNo(customer.getIdno());
		}
		if(null != customer.getPhone()){
			imgtask.setPhoneNo(customer.getPhone());
		}
		imgtask.setState(0);
		imgtask.setPdfName(bean.getPdfName());
		imgtask.setFilePath(bean.getFilePath());
	    imgtask.setBatchNo(bean.getPdfBthNo());
	    if(customerWuChuangList.size()!=0){
	    	String birthday = customerWuChuangList.get(0).getBirthday();
	    	imgtask.setBirthday(birthday);
	    	if (birthday.length()>=10) {//截取生日,获取年月日--1984-05-24 00:00:00
	    		imgtask.setBirthday(birthday.substring(0,10));
	    	}
	    }
	    imgtask.setCreateTime(new Date());
	    imgtask.setIsDeleted(0);
	    return imgtask;
	}
	
	/**
	 * @param copytToJsonDir json文件路径
	 * @param batchNo 批次号
	 */
	public int dealJsonByPath(String copytToJsonDir, String batchNo){
		File jsonFile = new File(copytToJsonDir);
		File files[]=jsonFile.listFiles();
		int count = 0;
		if(null!=files){
			log.info("开始保存json文件数据---");
			for(int i = 0; i<files.length;i++){
				log.info("开始保存第"+count+"个json文件");
				try {
					File currFile = files[i];
					String [] nameArray = currFile.getName().substring(0, currFile.getName().indexOf(".")).split("_");
					log.info("json文件名："+Arrays.toString(nameArray));
					JSONObject jsonObject = new JSONObject(getJsonText(currFile).toString());
					String userId = jsonObject.getString("user_id");
					List<ErpCustomerExamWC> examWCList = new ArrayList<ErpCustomerExamWC>();
					ErpCustomerInfoWC wcInfo = new ErpCustomerInfoWC();
					wcInfo.setPdfName(currFile.getName());
					if(nameArray.length==4){//W0005441_姚文雯_无创生物电套餐四_女.pdf.data
						wcInfo.setCode(nameArray[0]);
						wcInfo.setName(nameArray[1]);
						wcInfo.setCombo(nameArray[2]);
						wcInfo.setGender(nameArray[3]);
					}
					Date date = new Date();
					wcInfo.setExamId(jsonObject.getLong("exam_id"));
					wcInfo.setHeight(jsonObject.getInt("height"));
					wcInfo.setWeight(jsonObject.getInt("weight"));
					wcInfo.setMarks(jsonObject.getString("marks"));
					wcInfo.setWcCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("create_time")));
					wcInfo.setDeviceId(jsonObject.getString("device_id"));
					wcInfo.setUserId(userId);
					wcInfo.setServiceCode(jsonObject.getString("service_code"));
					wcInfo.setIsDeleted(0);
					wcInfo.setCreateTime(date);
					wcInfo.setBatchNo(batchNo);
					
					JSONArray jsonArray = jsonObject.getJSONArray("exam_report");
					log.info("开始获取第"+i+"个人的详细检测内容---");
					for(int j=0;j<jsonArray.length();j++){
						ErpCustomerExamWC examWC = new ErpCustomerExamWC();
						log.info("第"+i+"个人的第"+j+"列详细检测内容---");
						examWC.setUserId(userId);
						examWC.setWcId(jsonArray.getJSONObject(j).getLong("id"));
						examWC.setExamItemWeight(jsonArray.getJSONObject(j).getInt("exam_item_weight"));
						examWC.setExamId(jsonArray.getJSONObject(j).getLong("exam_id"));
						examWC.setSysName(jsonArray.getJSONObject(j).getString("sys_name"));
						examWC.setExamContent(jsonArray.getJSONObject(j).getString("exam_content"));
						examWC.setSysId(jsonArray.getJSONObject(j).getLong("sys_id"));
						examWC.setTransition(jsonArray.getJSONObject(j).getString("transition"));
						examWC.setExamItemIndex(jsonArray.getJSONObject(j).getInt("exam_item_index"));
						examWC.setExamItemId(jsonArray.getJSONObject(j).getLong("exam_item_id"));
						examWC.setFunctionalStatus(jsonArray.getJSONObject(j).getString("functional_status"));
						examWC.setMaxtermInfoId(jsonArray.getJSONObject(j).getLong("maxterm_info_id"));
						examWC.setCreateTime(date);
						examWC.setIsDeleted(0);
						examWC.setBatchNo(batchNo);
						examWCList.add(examWC);
						log.info("第"+i+"个人的第"+j+"列详细检测内容获取结束---");
					}
					count ++;
					dao.save(wcInfo);
					dao.getHibernateTemplate().saveOrUpdateAll(examWCList);
					
				} catch (Exception e) {
					log.error("保存json文件出错:----"+e);
				}
			}
		}
		log.info("保存json文件结束---");
		return count;
	}
	
	private static StringBuilder getJsonText(File file){
		StringBuilder result = new StringBuilder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(file,"utf-8");
			while (scanner.hasNext()) {
				result.append(scanner.nextLine().replace(" ", ""));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(scanner!=null){
				scanner.close();
			}
		}
		return result; 
		
	}

	/**
	 *
	 * @param pdfName
	 * @param code
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-30 下午8:47:43
	 */
	public List<ErpReportdetailPDFContent> findByProps(String code, String pdfName) throws Exception{
		List<ErpReportdetailPDFContent> list = null;
		if(StringUtils.isNotEmpty(code)&&StringUtils.isNotEmpty(pdfName)){
			list = dao.findByProps( code,pdfName);
		}
		return list;
	}
	/**
	 * 把要打印的文件添加到打印任务
	 * @param filePath 文件的物理路径
	 * @param entity 客户信息
	 * @param events 场次信息
	 * @return boolean
	 * @author DengYouming
	 * @since 2016-10-26 下午2:14:56
	 */
	public boolean deal4PrintTask(String filePath, Integer fileSize, ErpCustomer entity, ErpEvents events){
		//返回标志
		boolean flag = false;
		ErpReportdetailPDFContent pdfContentObj;

		String name = entity.getName();
		String barcode = entity.getCode();
		String age = entity.getAge();
		String gender = entity.getSex();
		String combo = entity.getSetmealName();
		Date date = Calendar.getInstance().getTime();
		//查找PDF匹配报告
		List<ErpReportdetailPDFContent> pdfList = null;

		String pdfName = filePath.substring(filePath.lastIndexOf(File.separator)+1);

		try {
			pdfList = this.findByProps(barcode, pdfName);

			if(StringUtils.isNotEmpty(filePath)&&filePath.contains(".")){

				String type = "3";
				if(StringUtils.containsIgnoreCase(filePath, "pdf")){
					type = "2";
				}

				if(!CollectionUtils.isEmpty(pdfList)){
					pdfContentObj = pdfList.get(0);
					if(pdfContentObj!=null){
						//无文件路径或者文件路径不正常，则更新，否则不更新
						pdfContentObj.setUsername(name); //修复bug用
						pdfContentObj.setFilepath(filePath);
						pdfContentObj.setFilesize(""+fileSize); // add by me 2016-12-19
						pdfContentObj.setUpdateUser("金域");
						pdfContentObj.setUpdateTime(date);

						/**@since 2016年10月8日15:18:37 @author Carly*/
						List<ErpPrintTaskContent> contentList = contentService.getContentByPdfId(pdfContentObj.getId());
						if (!CollectionUtils.isEmpty(contentList)) {
							ErpPrintTaskContent content2 = contentList.get(0);
							content2.setFilePath(filePath);
							content2.setUserName(name);
							content2.setUpdateUser("金域");
							content2.setUpdateTime(date);
							content2.setType(type);
							contentService.update(content2);
							this.update(pdfContentObj);//先更新打印任务 modify by Damian 2017-04-01
							flag = true;
							log.info("姓名："+name+",条码："+barcode+",ERP_REPORTDETAIL_PDFCONTENT , ERP_PRINTTASK_CONTENT表中信息已更新！");
						}
					}

				}else{

					pdfContentObj = new ErpReportdetailPDFContent();

					pdfContentObj.setPdfname(pdfName);
					pdfContentObj.setUsername(name);
					pdfContentObj.setAge(""+age);
					pdfContentObj.setCode(barcode);
					pdfContentObj.setSex(gender);
					pdfContentObj.setSetmeal_name(combo);

					pdfContentObj.setFilesize(""+fileSize);
//					pdfContentObj.setMd5(md5);
					pdfContentObj.setBatchno(events.getBatchNo());
					pdfContentObj.setFilepath(filePath);
					pdfContentObj.setIsrecord(2);
					pdfContentObj.setIsrepeat(0);

					pdfContentObj.setMatchstate(2); //匹配状态 add 2016-12-30

					pdfContentObj.setCreatedate(Calendar.getInstance().getTime());
					pdfContentObj.setProvice(entity.getProvice());
					pdfContentObj.setCity(entity.getCity());
					pdfContentObj.setBranch_company(events.getBranchCompanyId());
					pdfContentObj.setEvents_no(events.getEventsNo());

					pdfContentObj.setPs("0");
					pdfContentObj.setSettlement_status("0");


					//add by chenqi @since 2016年10月11日12:01:34 添加到需要打印的表（ErpPrintTaskContent）中
					List<ErpCustomer> customerList = contentService.getCustomerInfoByCode(pdfContentObj.getCode());
					CustomerRelationShipPro shipPro = contentService.getProjectCodeByEvent(customerList.get(0).getEventsNo());
					ErpPrintTaskContent contents = new ErpPrintTaskContent();

					contents.setAge(pdfContentObj.getAge());
					contents.setBatchNo(pdfContentObj.getBatchno());
					contents.setBranchCompanyId(pdfContentObj.getBranch_company());
					contents.setProvince(pdfContentObj.getProvice());
					contents.setCity(pdfContentObj.getCity());

					contents.setCode(pdfContentObj.getCode());
					contents.setUserName(pdfContentObj.getUsername());
					contents.setCombo(pdfContentObj.getSetmeal_name());
					contents.setGender(pdfContentObj.getSex());
					contents.setBatchNo(pdfContentObj.getBatchno());

					contents.setFilePath(pdfContentObj.getFilepath());
					contents.setSaleman(pdfContentObj.getSales_man());
					contents.setPdfContentId(pdfContentObj.getId());
					contents.setCustomerId(customerList.get(0).getId());
					contents.setDept(customerList.get(0).getDepartment());

					contents.setOwnedCompanyId(customerList.get(0).getOwnedCompanyId());
					if (shipPro!=null) {
						contents.setProjectCode(shipPro.getProjectCode());
					}
					contents.setPs("0");
					contents.setType(type);
					contents.setIsManuallyAdd("2");

					contents.setCreateTime(date);
					contents.setCreateUser("金域");
					contents.setReportType(1); // modify 2016-12-12

					contentService.save(contents);
					this.saveEntity(pdfContentObj); //

					flag = true;
				}
			}
		} catch (Exception e) {
			log.info(e);
		}

		return flag;
	}

	/**
	 *
	 * @param obj
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-10-10 下午12:06:17
	 */
	public void saveEntity(ErpReportdetailPDFContent obj) throws Exception{
		dao.saveEntity(obj);
	}

	/**
	 *
	 * @param obj
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-30 下午8:47:40
	 */
	public void update(ErpReportdetailPDFContent obj) throws Exception{
		dao.update(obj);
	}
	
	/**
	 * 根据code、name、场次号查找ErpReportdetailPDFContent
	 * @param code
	 * @param name
	 * @param eventsNo
	 * @return
	 * @author tangxing
	 * @date 2017-3-7下午5:15:08
	 */
	public List<ErpReportdetailPDFContent> gePdfContentByEventsNoAndCodeAndName(String code,String name,String eventsNo){
		return dao.gePdfContentByEventsNoAndCodeAndName(code,name,eventsNo);
	}
	
	/**
	 * @author Carly
	 * @since 2017年3月9日11:45:43
	 * @param batchNo 批次号
	 * 更新匹配状态
	 */
	private int [] countFileTaskState(String batchNo) {
		
		int updatenum = dao.countMatchState(batchNo,2);
		int unmatchnum = dao.countMatchState(batchNo,3);
		int abnormalnnum = dao.countMatchState(batchNo,4);
		int cusmorenum = dao.countMatchState(batchNo,5);
		int noYMCombo = dao.countMatchState(batchNo,7);	//没有和远盟对应的套餐
		//当对应多个远盟套餐时，客户的套餐和申友所获取的对应远盟套餐不一致（用客户条码，姓名和套餐去去确定唯一套餐）
		int noCustomerCombo2SY = dao.countMatchState(batchNo,8);	
		int noSYCombo = dao.countMatchState(batchNo,9);	//没有找到对应的申友套餐
		int errorPdfName = dao.countMatchState(batchNo,10);
		int stopCombo = dao.countMatchState(batchNo, 11);	//
		int stopReport = dao.countMatchState(batchNo, 13);	//不打印报告
		int [] matchState = {updatenum, unmatchnum, abnormalnnum, cusmorenum, noYMCombo, noCustomerCombo2SY, noSYCombo,
				errorPdfName, stopCombo, stopReport, 1};
		return matchState;
	}
	
	/**
	 * @author Carly
	 * @since 2017年3月9日15:18:58
	 * @param content 查找到的PDFcontent数据
	 * @return 组装好的需要的pdf信息
	 */
	private ErpReportMatchBean getMatchBeanDO(ErpReportdetailPDFContent content) {
		ErpReportMatchBean bean = new ErpReportMatchBean();
		bean.setId(content.getId());
		bean.setCode(content.getCode());
		bean.setCombo(content.getSetmeal_name());
		bean.setName(content.getUsername());
		bean.setSex(content.getSex());
		bean.setAge(String.valueOf(content.getAge()));
		bean.setFilePath(content.getFilepath());
		bean.setFileSize(content.getFilesize());
		bean.setPdfBthNo(content.getBatchno());
		bean.setPrintState(content.getPs());
		bean.setReportType(content.getReportType());
		bean.setPdfName(content.getPdfname());
		bean.setConvertionFileType(content.getConvertionFileType());
		bean.setMatchState(false);
		return bean;
	}
}
