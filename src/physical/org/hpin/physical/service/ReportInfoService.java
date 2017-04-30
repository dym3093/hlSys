package org.hpin.physical.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.ProjectInfo;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.physical.dao.PhyReportDao;
import org.hpin.physical.entity.DiseaseProject;
import org.hpin.physical.entity.PhyReport;
import org.hpin.physical.entity.PhyReportItem;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Transactional()
@Service(value = "org.hpin.physical.service.ReportInfoService")
public class ReportInfoService extends BaseService{

	private Logger log = Logger.getLogger(ReportInfoService.class);

	/*@Autowired
	private PhyReportDao dao;*/
	private PhyReportDao dao = (PhyReportDao)SpringContextHolder.getBean("phyReportDao");

	@Autowired
	private ErpCustomerDao customerDao;
	
	
	/**
	 * 获取该code的所属公司
	 * @param geneCode
	 * @return
	 * @author tangxing
	 * @date 2016-7-1上午11:08:41
	 */
	public String getOwnedCompanyByGeneCode(String geneCode){
		/*
		 * Map key info
		 * 
		 * OWNED_COMPANY
		 * AGE
		 */
		List<Map<String, Object>> listMap = dao.getOwnedCompAndAgeByGeneCode(geneCode);
		String ownedCompany = "";
		if(listMap!=null&&listMap.size()>0){
			for (Map<String, Object> map : listMap) {
				ownedCompany = (String) map.get("OWNED_COMPANY");
			}
		}
		log.info("getOwnedCompanyByGeneCode ownedCompany--"+ownedCompany);
		
		return ownedCompany;
	}
	
	/**
	 * 通过geneCode获取采样日期
	 * @param geneCode
	 * @return
	 * @author tangxing
	 * @throws ParseException 
	 * @date 2016-10-25下午2:24:11
	 */
	public Date getSamplingTimeByGeneCode(String geneCode) throws ParseException{
		
		List<Map<String, Object>> listMap = dao.getOwnedCompAndAgeByGeneCode(geneCode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date samplingTime = null;
		if(listMap!=null&&listMap.size()>0){
			for (Map<String, Object> map : listMap) {
				Date time = (Date) map.get("sampling_Date");
				if(time!=null){
					log.info("getCheckTimeByGeneCode sampling_Date--"+time);
					samplingTime = time;
				}
			}
		}
		return samplingTime;
	}
	
	/**
	 * 根据geneCode查找到PhyReport
	 */
	public PhyReport getPhyReportByCode(String geneCode){
		List<PhyReport> report = new ArrayList<PhyReport>();
		report = dao.getPhyReportByCode(geneCode);
		dao.getOwnedCompAndAgeByGeneCode(geneCode);
		if(null!=report&&0<report.size()){
			return report.get(0);
		}
		return null;
	}
	
	/**
	 * 根据geneCode查找到PhyReport(已生成报告)
	 */
	public PhyReport getPhyReportByCodeAndIsSuccess(String geneCode){
		List<PhyReport> report = dao.getPhyReportByCodeAndIsSuccess(geneCode);
		if(null!=report&&0<report.size()){
			return report.get(0);
		}
		return null;
	}
	
	/**
	 * 根据geneCode查找到PhyReport and PhyReportItem
	 * @param geneCode
	 * @return
	 * @author tangxing
	 * @date 2016-6-30下午4:14:38
	 */
	public Map<String, List> getReportAndReportItemByGeneCode(String geneCode){
		/*
		 * report  对应PhyReport类集合
		 * 
		 * reportItem  对应PhyReportItem类集合
		 */
		
		Map<String, List> maps =null;
		List<PhyReportItem> items = null;
		List<PhyReport> report = dao.getPhyReportByCode(geneCode);
		if(report.size()>0){
			items = new ArrayList<PhyReportItem>();
			maps = new HashMap<String, List>();
			List<PhyReportItem> reportItem = dao.getPhyReportItemByReportId(report.get(0).getId());
			if(reportItem.size()>0){
				maps.put("report", report);
				for (PhyReportItem phyReportItem : reportItem) {
					items.add(phyReportItem);
				}
				maps.put("reportItem", items);
			}
		}
		
		log.info("getReportAndReportItemByGeneCode reportItem and report--"+maps.toString());
		return maps;
	}
	
	/* ****disease信息(常规)**** */
	public Map<String, List<List<String>>> getDiseaseInfoRoutine(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCode(geneCode);
		List<List<DiseaseProject>> bigDiseaseProjects = new ArrayList<List<DiseaseProject>>();
		Map<String, List<List<String>>> resultMap = new HashMap<String, List<List<String>>>();
		if(report.size()>0){
			String sAge = report.get(0).getUserAge();
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			
			for (PhyReportItem reportItem : reportItems) {
				Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					log.info("getDiseaseInfo show diseaseName--"+reportItem.getYmDiseaseName());
					//获取检测建议信息
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					
					//查找该疾病code对应的信息
					List<Map<String, Object>>  diseaseMaps= dao.getDiseaseAndItemByDisCode(reportItem.getYmDiseaseCode());
					String riseRemark = judgeRiseLevelShowTwo(reportItem.getGeneRiskRemark(),reportItem.getGeneRiskGrade());//疾病等级
					List<DiseaseProject> diseaseProjects = formatDiseaseProject(diseaseMaps,projectMaps,riseRemark);	//组装对象
					if(diseaseProjects.size()>0){//用基因公司的疾病code去查询远盟疾病，有可能对应的远盟疾病为null
						bigDiseaseProjects.add(diseaseProjects);
					}
				}
			}
			
			resultMap = this.translatedUtil(bigDiseaseProjects);	//格式化数据
		}
		
		return resultMap;
	}
	//用于jsp页面显示
	public Map<String, List<List<String>>> getDiseaseInfoRoutineTwo(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCodeAndIsSuccess(geneCode);
		List<List<DiseaseProject>> bigDiseaseProjects = new ArrayList<List<DiseaseProject>>();
		Map<String, List<List<String>>> resultMap = new HashMap<String, List<List<String>>>();
		if(report.size()>0){
			String sAge = report.get(0).getUserAge();
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			
			for (PhyReportItem reportItem : reportItems) {
				Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					log.info("getDiseaseInfo show diseaseName--"+reportItem.getYmDiseaseName());
					//获取检测建议信息
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					
					//查找该疾病code对应的信息
					List<Map<String, Object>>  diseaseMaps= dao.getDiseaseAndItemByDisCode(reportItem.getYmDiseaseCode());
					String riseRemark = judgeRiseLevelShowTwo(reportItem.getGeneRiskRemark(),reportItem.getGeneRiskGrade());//疾病等级
					List<DiseaseProject> diseaseProjects = formatDiseaseProject(diseaseMaps,projectMaps,riseRemark);	//组装对象
					if(diseaseProjects.size()>0){//用基因公司的疾病code去查询远盟疾病，有可能对应的远盟疾病为null
						bigDiseaseProjects.add(diseaseProjects);
					}
				}
			}
			
			resultMap = this.translatedUtil(bigDiseaseProjects);	//格式化数据
		}
		
		return resultMap;
	}
	/* ****project信息(常规)**** */
	public List<HashMap<String, List<Map<String,String>>>>  getProjectInfoRoutine(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCode(geneCode);
		List<List<DiseaseProject>> diseaseProjects = new ArrayList<List<DiseaseProject>>();
		List<HashMap<String, List<Map<String,String>>>> projectMap = null;
		if(report.size()>0){
			projectMap = new ArrayList<HashMap<String, List<Map<String,String>>>>();
			String sAge = report.get(0).getUserAge();
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			for (PhyReportItem reportItem : reportItems) {
				
				Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				//Integer level = reportItem.getGeneRiskGrade();
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					List<DiseaseProject> tempList =this.formatDiseaseProjectTwo(projectMaps);
					diseaseProjects.add(tempList);
				}
			}
			projectMap = this.translatedUtilTwo(diseaseProjects);
			try {
				Integer age = Integer.valueOf(sAge);
				if(age>50){			//如果年龄大于50，就加上激素六项的检验
					HashMap<String, List<Map<String,String>>> temp = new HashMap<String, List<Map<String,String>>>();
					List<Map<String,String>> info = new ArrayList<Map<String,String>>();
					Map<String,String> infoTemp = new HashMap<String, String>();
					infoTemp.put("six", ProjectInfo.HORMONE_SIX);
					info.add(infoTemp);
					temp.put("激素六项", info);
					projectMap.add(temp);
				}
			} catch (Exception e) {
				log.error("getDiseaseAndItemByDisCode age translated:",e);
			}
		}	
		return projectMap;
	}
	//用于jsp页面显示
	public List<HashMap<String, List<Map<String,String>>>>  getProjectInfoRoutineTwo(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCodeAndIsSuccess(geneCode);
		List<List<DiseaseProject>> diseaseProjects = new ArrayList<List<DiseaseProject>>();
		List<HashMap<String, List<Map<String,String>>>> projectMap = null;
		if(report.size()>0){
			projectMap = new ArrayList<HashMap<String, List<Map<String,String>>>>();
			String sAge = report.get(0).getUserAge();
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			for (PhyReportItem reportItem : reportItems) {
				
				Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				//Integer level = reportItem.getGeneRiskGrade();
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					List<DiseaseProject> tempList =this.formatDiseaseProjectTwo(projectMaps);
					diseaseProjects.add(tempList);
				}
			}
			projectMap = this.translatedUtilTwo(diseaseProjects);
			try {
				Integer age = Integer.valueOf(sAge);
				if(age>50){			//如果年龄大于50，就加上激素六项的检验
					HashMap<String, List<Map<String,String>>> temp = new HashMap<String, List<Map<String,String>>>();
					List<Map<String,String>> info = new ArrayList<Map<String,String>>();
					Map<String,String> infoTemp = new HashMap<String, String>();
					infoTemp.put("six", ProjectInfo.HORMONE_SIX);
					info.add(infoTemp);
					temp.put("激素六项", info);
					projectMap.add(temp);
				}
			} catch (Exception e) {
				log.error("getDiseaseAndItemByDisCode age translated:",e);
			}
		}	
		return projectMap;
	}
	
	
	/* ********disease信息(基础一、二、三)******** */
	public Map<String, List<List<String>>> getDiseaseInfo(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCode(geneCode);
		List<List<DiseaseProject>> bigDiseaseProjects = new ArrayList<List<DiseaseProject>>();
		Map<String, List<List<String>>> resultMap = new HashMap<String, List<List<String>>>();
		if(report.size()>0){
			String sAge = report.get(0).getUserAge();
			int count = 0;
			int threeDisease = 0;	//只拿三个疾病
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			
			for (PhyReportItem reportItem : reportItems) {
				//Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				Integer level = reportItem.getGeneRiskGrade();
				
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					count++;
					threeDisease++;
					log.info("getDiseaseInfo show diseaseName--"+reportItem.getYmDiseaseName());
					//获取检测建议信息
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					
					//查找该疾病code对应的信息
					List<Map<String, Object>>  diseaseMaps= dao.getDiseaseAndItemByDisCode(reportItem.getYmDiseaseCode());
					String riseRemark = judgeRiseLevelShowTwo(reportItem.getGeneRiskRemark(),reportItem.getGeneRiskGrade());//疾病等级
					List<DiseaseProject> diseaseProjects = formatDiseaseProject(diseaseMaps,projectMaps,riseRemark);	//组装对象
					if(diseaseProjects.size()>0){//用基因公司的疾病code去查询远盟疾病，有可能对应的远盟疾病为null
						bigDiseaseProjects.add(diseaseProjects);
					}
					reportItem.setShowStatus("0");	//展示了该疾病后，修改状态为0
					dao.update(reportItem);
					if(3==threeDisease){
						break;
					}
				}
			}
			
			//如果没有一个大于风险等级4的疾病，就取风险等级等于4的
			if(0==count){
				for (PhyReportItem reportItem : reportItems) {
					Integer level = reportItem.getGeneRiskGrade();
					if(level!=null&&level==4){		//该疾病code的风险等级大于4
						threeDisease++;
						log.info("getDiseaseInfo show level equal 4 diseaseName--"+reportItem.getYmDiseaseName()+" and "+reportItem.getGeneRiskGrade());
						//获取检测建议信息
						List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
						
						//查找该疾病code对应的信息
						List<Map<String, Object>>  diseaseMaps= dao.getDiseaseAndItemByDisCode(reportItem.getYmDiseaseCode());
						String riseRemark = judgeRiseLevelShow(sAge,reportItem.getGeneRiskRemark(),reportItem.getGeneRiskGrade());//疾病等级
						List<DiseaseProject> diseaseProjects = formatDiseaseProject(diseaseMaps,projectMaps,riseRemark);	//组装对象
						if(diseaseProjects.size()>0){//用基因公司的疾病code去查询远盟疾病，有可能对应的远盟疾病为null
							bigDiseaseProjects.add(diseaseProjects);
						}
						reportItem.setShowStatus("0");	//展示了该疾病后，修改状态为0
						dao.update(reportItem);
						if(3==threeDisease){
							break;
						}
					}
				}
			}
			
			resultMap = this.translatedUtil(bigDiseaseProjects);	//格式化数据
		}
		
		return resultMap;
	}
	
	/* ***DiseaseInfo用于jsp页面展示(基础一、二、三)*** */
	//已生成pdf的code
	public Map<String, List<List<String>>> getDiseaseInfoTwo(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCodeAndIsSuccess(geneCode);
		List<List<DiseaseProject>> bigDiseaseProjects = new ArrayList<List<DiseaseProject>>();
		Map<String, List<List<String>>> resultMap = new HashMap<String, List<List<String>>>();
		if(report.size()>0){
			String sAge = report.get(0).getUserAge();
			//int count = 0;
			//int threeDisease = 0;	//只拿三个疾病
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			
			for (PhyReportItem reportItem : reportItems) {
				if(reportItem.getShowStatus()!=null&&reportItem.getShowStatus().equals("0")){		//拿到该会员检测的疾病
					//Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
					Integer level = reportItem.getGeneRiskGrade();
					if(level!=null&&level>4){		//该疾病code的风险等级大于4
						log.info("getDiseaseInfoTwo show level equal 4 diseaseName--"+reportItem.getYmDiseaseName()+" and "+reportItem.getGeneRiskGrade());
						//count++;
						//threeDisease++;
						//获取检测建议信息
						List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
						
						//查找该疾病code对应的信息
						List<Map<String, Object>>  diseaseMaps= dao.getDiseaseAndItemByDisCode(reportItem.getYmDiseaseCode());
						String riseRemark = judgeRiseLevelShowTwo(reportItem.getGeneRiskRemark(),reportItem.getGeneRiskGrade());//疾病等级
						List<DiseaseProject> diseaseProjects = formatDiseaseProject(diseaseMaps,projectMaps,riseRemark);	//组装对象
						if(diseaseProjects.size()>0){//用基因公司的疾病code去查询远盟疾病，有可能对应的远盟疾病为null
							bigDiseaseProjects.add(diseaseProjects);
						}
						/*if(3==threeDisease){
							break;
						}*/
					}
				}
			}
			
			//如果没有一个大于风险等级4的疾病，就取风险等级等于4的
			/*if(0==count){
				for (PhyReportItem reportItem : reportItems) {
					Integer level = reportItem.getGeneRiskGrade();
					if(level!=null&&level==4){		//该疾病code的风险等级大于4
						threeDisease++;
						
						//获取检测建议信息
						List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
						
						//查找该疾病code对应的信息
						List<Map<String, Object>>  diseaseMaps= dao.getDiseaseAndItemByDisCode(reportItem.getYmDiseaseCode());
						String riseRemark = judgeRiseLevelShow(sAge,reportItem.getGeneRiskRemark(),reportItem.getGeneRiskGrade());//疾病等级
						List<DiseaseProject> diseaseProjects = formatDiseaseProject(diseaseMaps,projectMaps,riseRemark);	//组装对象
						if(diseaseProjects.size()>0){//用基因公司的疾病code去查询远盟疾病，有可能对应的远盟疾病为null
							bigDiseaseProjects.add(diseaseProjects);
						}
						
						if(3==threeDisease){
							break;
						}
					}
				}
			}*/
			resultMap = this.translatedUtil(bigDiseaseProjects);	//格式化数据
		}
		
		return resultMap;
	}
	
	/**
	 * 组装对象
	 * @param diseaseMaps
	 * @param projectMaps
	 * @return
	 * @author tangxing
	 * @date 2016-8-3下午12:27:17
	 */
	private List<DiseaseProject> formatDiseaseProject(List<Map<String, Object>> diseaseMaps,List<Map<String, Object>> projectMaps,String riskRemark){
		List<DiseaseProject> diseaseProjects = new ArrayList<DiseaseProject>();
		String groupName="";
		String diseaseName = "";
		if(diseaseMaps.size()>0){
			for (Map<String, Object> diseaseMap : diseaseMaps) {
				groupName = (String) diseaseMap.get("GROUP_NAME");
				diseaseName = (String) diseaseMap.get("DISEASE_NAME");
			}
			if(projectMaps.size()>0){
				for (Map<String, Object> projectMap : projectMaps) {
					DiseaseProject diseaseProject = new DiseaseProject();
					diseaseProject.setGroupName(groupName);
					diseaseProject.setDiseaseName(diseaseName);
					diseaseProject.setGeneRiskRemark(riskRemark);
					String projectCategory = (String) projectMap.get("PROJECT_CATEGORY");
					String projectName = (String) projectMap.get("PROJECT_NAME");
					diseaseProject.setProjectCategory(projectCategory);
					diseaseProject.setProjectName(projectName);
					log.info("formatDiseaseProject diseaseProject toStrong--"+diseaseProject.toString());
					diseaseProjects.add(diseaseProject);
				}
			}else{
				DiseaseProject diseaseProject = new DiseaseProject();
				diseaseProject.setGroupName(groupName);
				diseaseProject.setDiseaseName(diseaseName);
				diseaseProject.setGeneRiskRemark(riskRemark);
				log.info("formatDiseaseProject diseaseProject toStrong--"+diseaseProject.toString());
				diseaseProjects.add(diseaseProject);
			}
		}
		
		return diseaseProjects;
	}
	
	/**
	 * 组装好显示的数据
	 * @param bigDiseaseProjects
	 * @return
	 * @author tangxing
	 * @date 2016-8-3下午3:25:26
	 */
	private Map<String, List<List<String>>> translatedUtil(List<List<DiseaseProject>> bigDiseaseProjects){
		Map<String, List<List<String>>> bigMap = new HashMap<String, List<List<String>>>();
		Set<String> groupNameSets = new TreeSet<String>();		//存放groupName（排重）
		List<String> comboList = null;
		List<String> projectNameList = null;
		List<List<String>> lists = null;
		Set<String> projectNameSets = null;
		for (List<DiseaseProject> diseaseProjects : bigDiseaseProjects) {
			String groupName = diseaseProjects.get(0).getGroupName();		//集合里面每个类的groupName、diseaseName、riskRemark都是一样的
			if(!StringUtils.isEmpty(groupName)&&!groupNameSets.contains(groupName)){
				groupNameSets.add(groupName);
			}
		}
		
		for (String groupNameStr : groupNameSets) {
			lists = new ArrayList<List<String>>();
			comboList = new ArrayList<String>();
			projectNameList = new ArrayList<String>();
			projectNameSets = new TreeSet<String>();
			for (List<DiseaseProject> diseaseProjects : bigDiseaseProjects) {
				String combo = "";
				String groupName = diseaseProjects.get(0).getGroupName();
				if(groupNameStr.equals(groupName)){			//集合里面每个类的groupName、diseaseName、riskRemark都是一样的只做一次
					String disName = diseaseProjects.get(0).getDiseaseName();
					String riskRemark = diseaseProjects.get(0).getGeneRiskRemark();
					combo = disName+" : "+riskRemark;
				}
				if(!StringUtils.isEmpty(combo)){
					log.info("disease translatedUtil disName and riskRemark --"+combo);
					comboList.add(combo);
				}
				for (DiseaseProject diseaseProject : diseaseProjects) {
					if(groupNameStr.equals(groupName)){
						String projetcCategory= diseaseProject.getProjectCategory();
						if(!StringUtils.isEmpty(projetcCategory)){
							if(projetcCategory.equals("超声")||projetcCategory.equals("影像")){
								String projectName = diseaseProject.getProjectName();
								if(!StringUtils.isEmpty(projectName)){
									log.info("project translatedUtil projectName --"+projectName);
									projectNameSets.add(projectName);
								}
							}
						}
					}
				}
			}
			for (String string : projectNameSets) {
				projectNameList.add(string);
			}
			lists.add(comboList);
			lists.add(projectNameList);
			bigMap.put(groupNameStr, lists);
		}
		groupNameSets = null;
		comboList = null;
		projectNameList = null;
		lists = null;
		projectNameSets = null;
		return bigMap;
	}
	
	
	/* ********project信息(基础一、二、三)******** */
	/**
	 * 获取project信息
	 * @param geneCode
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-8-3下午6:01:57
	 */
	public List<HashMap<String, List<Map<String,String>>>>  getProjectInfo(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCode(geneCode);
		List<List<DiseaseProject>> diseaseProjects = new ArrayList<List<DiseaseProject>>();
		List<HashMap<String, List<Map<String,String>>>> projectMap = null;
		if(report.size()>0){
			projectMap = new ArrayList<HashMap<String, List<Map<String,String>>>>();
			String sAge = report.get(0).getUserAge();
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			for (PhyReportItem reportItem : reportItems) {
				if(reportItem.getShowStatus()!=null&&reportItem.getShowStatus().equals("0")){		//拿到该code的检测疾病
					log.info("getProjectInfo show ShowStatus equal 0 diseaseName--"+reportItem.getYmDiseaseName()+" and "+reportItem.getGeneRiskGrade());
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					List<DiseaseProject> tempList =this.formatDiseaseProjectTwo(projectMaps);
					diseaseProjects.add(tempList);
				}
				
				//Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				/*Integer level = reportItem.getGeneRiskGrade();
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					List<DiseaseProject> tempList =this.formatDiseaseProjectTwo(projectMaps);
					diseaseProjects.add(tempList);
				}*/
			}
			projectMap = this.translatedUtilTwo(diseaseProjects);
			try {
				Integer age = Integer.valueOf(sAge);
				if(age>50){			//如果年龄大于50，就加上激素六项的检验
					HashMap<String, List<Map<String,String>>> temp = new HashMap<String, List<Map<String,String>>>();
					List<Map<String,String>> info = new ArrayList<Map<String,String>>();
					Map<String,String> infoTemp = new HashMap<String, String>();
					infoTemp.put("six", ProjectInfo.HORMONE_SIX);
					info.add(infoTemp);
					temp.put("激素六项", info);
					projectMap.add(temp);
				}
			} catch (Exception e) {
				log.error("getDiseaseAndItemByDisCode age translated:",e);
			}
		}	
		return projectMap;
	}
	
	/**
	 * project信息用于JSP页面展示(基础一、二、三) 
	 * 已生成pdf的code
	 * @param geneCode
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016年10月19日15:14:46
	 */
	public List<HashMap<String, List<Map<String,String>>>>  getProjectInfoTwo(String geneCode) throws Exception{
		List<PhyReport> report = dao.getPhyReportByCodeAndIsSuccess(geneCode);
		List<List<DiseaseProject>> diseaseProjects = new ArrayList<List<DiseaseProject>>();
		List<HashMap<String, List<Map<String,String>>>> projectMap = null;
		if(report.size()>0){
			projectMap = new ArrayList<HashMap<String, List<Map<String,String>>>>();
			String sAge = report.get(0).getUserAge();
			
			//该geneCode下所有的疾病code
			List<PhyReportItem> reportItems = dao.getPhyReportItemByReportId(report.get(0).getId());
			for (PhyReportItem reportItem : reportItems) {
				if(reportItem.getShowStatus()!=null&&reportItem.getShowStatus().equals("0")){		//拿到该code的检测疾病
					log.info("getProjectInfoTwo show ShowStatus equal 0 diseaseName--"+reportItem.getYmDiseaseName()+" and "+reportItem.getGeneRiskGrade());
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					List<DiseaseProject> tempList =this.formatDiseaseProjectTwo(projectMaps);
					diseaseProjects.add(tempList);
				}
				
				//Integer level = judgeRiseLevel(sAge,reportItem.getGeneRiskGrade());
				/*Integer level = reportItem.getGeneRiskGrade();
				if(level!=null&&level>4){		//该疾病code的风险等级大于4
					List<Map<String, Object>>  projectMaps = dao.getProjectAndItemByDisCode(reportItem.getYmDiseaseCode(), report.get(0).getUserSex(), reportItem.getGeneRiskGrade());
					List<DiseaseProject> tempList =this.formatDiseaseProjectTwo(projectMaps);
					diseaseProjects.add(tempList);
				}*/
			}
			projectMap = this.translatedUtilTwo(diseaseProjects);
			try {
				Integer age = Integer.valueOf(sAge);
				if(age>50){			//如果年龄大于50，就加上激素六项的检验
					HashMap<String, List<Map<String,String>>> temp = new HashMap<String, List<Map<String,String>>>();
					List<Map<String,String>> info = new ArrayList<Map<String,String>>();
					Map<String,String> infoTemp = new HashMap<String, String>();
					infoTemp.put("six", ProjectInfo.HORMONE_SIX);
					info.add(infoTemp);
					temp.put("激素六项", info);
					projectMap.add(temp);
				}
			} catch (Exception e) {
				log.error("getDiseaseAndItemByDisCode age translated:",e);
			}
		}	
		return projectMap;
	}
	
	/**
	 * 组装对象2
	 * @param projectMaps
	 * @return
	 * @author tangxing
	 * @date 2016-8-3下午3:34:52
	 */
	private List<DiseaseProject> formatDiseaseProjectTwo(List<Map<String, Object>>  projectMaps){
		List<DiseaseProject> diseaseProjects = new ArrayList<DiseaseProject>();
		for (Map<String, Object> projectMap : projectMaps) {
			if(projectMap.size()>0){
				DiseaseProject diseaseProject = new DiseaseProject();
				String projectCategory = (String) projectMap.get("PROJECT_CATEGORY");
				String projectName = (String) projectMap.get("PROJECT_NAME");
				String projectSmallCategory = (String) projectMap.get("PROJECT_SMALLCATEGORY");
				diseaseProject.setProjectCategory(projectCategory);
				diseaseProject.setProjectName(projectName);
				diseaseProject.setProjectSmallCategory(projectSmallCategory);
				log.info("formatDiseaseProjectTwo diseaseProject toString--"+diseaseProject.toString());
				diseaseProjects.add(diseaseProject);
			}
		}
		return diseaseProjects;
	}
	
	/**
	 * 显示projectMap格式化
	 * @param diseaseProjectList
	 * @return
	 * @author tangxing
	 * @date 2016-8-3下午6:05:18
	 */
	private List<HashMap<String, List<Map<String,String>>>> translatedUtilTwo(List<List<DiseaseProject>> diseaseProjectList){
		List<HashMap<String, List<Map<String,String>>>> mapLists = new ArrayList<HashMap<String, List<Map<String,String>>>>();
		Set<String> projectCategorysets = new TreeSet<String>();
		HashMap<String,String> samllCategory = null;
		HashMap<String,String> otherCategory = null;
		Set<String> setCategory = null;
		Set<String> setSun = null;
		Set<String> samllCategorySun = null;
		List<Map<String,String>> samllCategorySum = null;
		HashMap<String, List<Map<String,String>>> projectCategoryMap = null;
		
		for (List<DiseaseProject> diseaseProjects : diseaseProjectList) {
			for (DiseaseProject diseaseProject : diseaseProjects) {
				String projectCategory = diseaseProject.getProjectCategory();
				if(!StringUtils.isEmpty(projectCategory)&&!projectCategorysets.contains(projectCategory)){
					
					projectCategorysets.add(projectCategory);
				}
			}
		}
		
		for (String projectCategoryStr : projectCategorysets) {
			log.info("project translatedUtilTwo projectCategorystr --"+projectCategoryStr);
			samllCategorySum = new ArrayList<Map<String,String>>();	//存放血液检查下的小分类+详细检测对应
			samllCategory = new HashMap<String,String>();			//存放血液检查下的小分类
			otherCategory = new HashMap<String,String>();			//存放非血液检查信息
			setCategory = new TreeSet<String>();					//血液检测下分类排重
			setSun = new TreeSet<String>();							//详细检测项排重
			samllCategorySun = new TreeSet<String>();			//血液小分类下详细检测项排重
			projectCategoryMap = new HashMap<String, List<Map<String,String>>>();	//存放头为key的集合 
			String temp = "";
			for (List<DiseaseProject> diseaseProjects : diseaseProjectList) {
				for (DiseaseProject diseaseProject : diseaseProjects) {
					String projectCategory = diseaseProject.getProjectCategory();	//大分类
					String projectName = diseaseProject.getProjectName();			//详细检测
					String projectNames = "";
					if(!StringUtils.isEmpty(projectCategory)&&projectCategory.equals(projectCategoryStr)){
						if(projectCategory.equals("血液检验")){
							String projectSmallcategory = diseaseProject.getProjectSmallCategory();	//血液检测下的小分类
							setCategory.add(projectSmallcategory);
						}else{										//不是血液检测，就直接拿详细检测项
							if(!setSun.contains(projectName)){		//排重PROJECT_NAME
								projectNames = projectNames+"、"+projectName;
								temp=temp + projectNames;
								setSun.add(projectName);
							}
						}
					}
				}
			}
			if(temp.length()>0){
				temp = temp.substring(1);
			}
			log.info("project translatedUtilTwo other projectName --"+temp);
			otherCategory.put("other", temp);	//非血液检测
			for (String smallCategoryStr : setCategory) {
				String samllCategorytemp = "";
				for (List<DiseaseProject> diseaseProjects : diseaseProjectList) {
					for (DiseaseProject diseaseProject : diseaseProjects) {
						String projectSmallcategory = diseaseProject.getProjectSmallCategory();	//血液检测下的小分类
						if(!StringUtils.isEmpty(projectSmallcategory)&&projectSmallcategory.equals(smallCategoryStr)){
							String projectName = diseaseProject.getProjectName();
							if(!samllCategorySun.contains(projectName)){		//排重PROJECT_NAME
								samllCategorytemp = samllCategorytemp+"、"+projectName;
								samllCategorySun.add(projectName);
							}
						}
					}
				}
				if(samllCategorytemp.length()>0){
					samllCategorytemp = samllCategorytemp.substring(1);
				}
				log.info("project translatedUtilTwo bloodCategory projectName --"+samllCategorytemp);
				samllCategory.put(smallCategoryStr,samllCategorytemp);
			}
			
			if(samllCategory.size()>0){
				samllCategorySum.add(samllCategory);
			}
			if(otherCategory.size()>0){
				samllCategorySum.add(otherCategory);
			}
			projectCategoryMap.put(projectCategoryStr,samllCategorySum);
			mapLists.add(projectCategoryMap);
		}
		
		projectCategorysets = null;
		samllCategory = null;
		otherCategory = null;
		setCategory = null;
		setSun = null;
		samllCategorySun = null;
		samllCategorySum = null;
		projectCategoryMap = null;
		
		return mapLists;
	}
	
	
	/**
	 * 年龄小于35岁，疾病风险等级-1
	 * @param age
	 * @param riseLevel
	 * @return
	 * @author tangxing
	 * @date 2016-7-1下午12:05:32
	 */
	public Integer judgeRiseLevel(String age,Integer riseLevel) throws Exception{
		if(age==null&&StringUtils.isEmpty(age)){		//如果年龄为空，不做处理
			return riseLevel;
		}else{
			Integer ageInt = Integer.valueOf(age);
			if(ageInt<35){
				if(0!=riseLevel){		//如果等于0，就不-1
					return riseLevel-1;
				}else{
					return 0;
				}
			}else{
				return riseLevel;
			}
		}
	}
	
	/**
	 * 创建了PDF后的信息修改
	 * @param path
	 * @param reportName
	 * @param pdfPathTwo
	 * @param reportPrintName
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-7-1下午5:54:04
	 */
	public void createPdfInfoAlter(String path,String reportName,String pdfPathTwo,String reportPrintName,PhyReport phyReport) throws Exception{
		phyReport.setId(phyReport.getId());
		phyReport.setReportPath(path);
		phyReport.setReportCreateDate(new Date());
		phyReport.setReportName(reportName);
		phyReport.setPrintReportName(reportPrintName);
		phyReport.setPrintReportPath(pdfPathTwo);
		
		try {
			dao.update(phyReport);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("createPdfInfo update ",e);
		}
	}
	
	/**
	 * 创建了PDF后的信息修改(生成单页版)
	 * @param path
	 * @param reportName
	 * @param phyReport
	 * @param isSuccess
	 * @param isMerge
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-7-1下午5:54:04
	 */
	public void createPdfInfoAlterETC(String path,String reportName,PhyReport phyReport,String isSuccess,String isMerge) throws Exception{
		boolean flag = false;
		String geneCode = phyReport.getGeneCode();
		/* ****1+X PhyReport更新**** */
		phyReport.setId(phyReport.getId());
		phyReport.setReportPath(path);
		phyReport.setReportCreateDate(new Date());
		phyReport.setReportName(reportName);
		phyReport.setIsSuccess(isSuccess);
		phyReport.setIsMergeStatus(isMerge);
		try {
			dao.update(phyReport);
			flag = true;
		} catch (Exception e) {
			flag = false;
			log.error("createPdfInfo update ",e);
		}
		
		/* ****入库分批表**** */
		if(flag){
			ErpPrintTaskContent content = new ErpPrintTaskContent();
			List<ErpCustomer> customers = dao.getCustomerByCode(geneCode);
			if(customers!=null&&customers.size()>0){
				ErpCustomer customer = customers.get(0);
				content.setBranchCompanyId(customer.getBranchCompanyId());
				content.setOwnedCompanyId(customer.getOwnedCompanyId());
				content.setProvince(customer.getProvice());
				content.setCity(customer.getCity());
				content.setCombo(customer.getSetmealName());
				content.setCustomerId(customer.getId());
				content.setUserName(customer.getName());
				content.setGender(customer.getSex());
				content.setAge(customer.getAge());
				content.setCreateTime(new Date());
				content.setDept(customer.getDepartment());
			}
			content.setType("4");			//1+X报告为 '4'
			content.setReportType(2);		
			content.setPs("0");
			content.setCode(phyReport.getGeneCode());
			content.setFilePath(path);		//文件路径
			content.setPdfContentId(phyReport.getId());
			
			dao.save(content);
		}
		
	}
	
	/**
	 * 转换显示的等级描述数据
	 * @param age
	 * @param riskRemark
	 * @param riseLevel
	 * @return
	 * @author tangxing
	 * @date 2016-7-8上午11:58:49
	 */
	public String judgeRiseLevelShow(String age,String riskRemark,Integer riseLevel) throws Exception{
		if(age==null&&StringUtils.isEmpty(age)){		//如果年龄为空、不为数字，不做处理
			return riskRemark;
		}else{
			Integer ageInt = Integer.valueOf(age);
			if(ageInt<35){
				if(null!=riseLevel){
					Integer tepmInt = riseLevel-1;
					switch(tepmInt){
						case 4:return "平均";
						case 5:return "略高";
						case 6:return "较高";
						case 7:return "高";
						default:return riskRemark;
					}
				}
			}
		}
		return riskRemark;
	}
	
	public String judgeRiseLevelShowTwo(String riskRemark,Integer riseLevel) throws Exception{
		if(null!=riseLevel){
			switch(riseLevel){
				case 2:return "较低";
				case 3:return "略低";
				case 4:return "平均";
				case 5:return "略高";
				case 6:return "较高";
				case 7:return "高";
				default:return riskRemark;
			}
		}
		return riskRemark;
	}
	
	/**
	 * 将体检建议格式化（排重检测项）
	 * @param value
	 * @return
	 * @author tangxing
	 * @date 2016-8-1下午3:27:35
	 */
	public String dealList2strTwo(List<String> value) {
		Set<String> sets = new TreeSet<String>();
		String proName="";
		String projectName = "";
		if(value.size()>0){
			if(value.toString().indexOf("、")!=-1){
				for(int i=0;i<value.size();i++){
					String str = value.get(i);
					if(str.indexOf("、")==-1&&StringUtils.isEmpty(str)){	//如果不包含且不为空 、 就直接add
						sets.add(str);
					}else{			
						String[] sArr = str.split("、");
						for (int j = 0; j < sArr.length; j++) {
							String sTemp = sArr[j];
							sets.add(sTemp);
						}
					}
				}
			}else{		//如果不包含 、就直接处理
				for (String string : value) {
					if(!StringUtils.isEmpty(string)){
						sets.add(string);
					}
				}
			}
			
			for (String projectCategory : sets) {
				projectName = projectName +"、"+projectCategory;
			}
			
			if(!StringUtils.isEmpty(projectName)){
				proName = projectName.substring(1,projectName.length());
			}
			return proName;
		}else{
			return "";
		}
		
	}
	
}
