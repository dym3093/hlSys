package org.hpin.physical.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.physical.entity.PhyCompleteReport;
import org.hpin.physical.entity.PhyReport;
import org.hpin.physical.entity.PhyReportItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository()
public class PhyReportDao extends BaseDao {
	
	public List<PhyCompleteReport> getAllcompleteReport(){
		String hql = "from PhyCompleteReport where 1=1";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据条形码查找用户信息
	 * 
	 * @param code
	 * @return
	 */
	public List<ErpCustomer> getCustomerByCode(String code) {
		String message = "";
		String hql = "from ErpCustomer where code=? and IS_DELETED=0";
		List<ErpCustomer> list = this.getHibernateTemplate().find(hql, code);
		return list;
	}
	
	/**
	 * 根据ID获取PhyReport
	 * @param reportId
	 * @return
	 * @author tangxing
	 * @date 2016-10-26下午4:59:38
	 */
	public PhyReport getId(String reportId){
		return (PhyReport)this.findById(PhyReport.class, reportId);
	}
	
	/*public List<XreportPrintContent> getPdfContentById(String id){
		String hql = "from XreportPrintContent where pdfContentId = ?";
		return this.getHibernateTemplate().find(hql, new Object[]{id});
	}*/
	
	/**
	 * 根据ID获取XreportPdfInfo
	 * @param reportId
	 * @return
	 * @author tangxing
	 * @date 2016年10月26日17:50:35
	 */
	/*public XreportPdfInfo getPdfInfoById(String id){
		return (XreportPdfInfo)this.findById(XreportPdfInfo.class, id);
	}*/
	
	public List<ErpCustomer> getErpCustomerByCode(String code){
		String hql = "from ErpCustomer where code = ?";
		return this.getHibernateTemplate().find(hql, new Object[]{code});
	}
	
	/**
	 * 根据会员的code和name获取合并PDF 类
	 * @param code
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016-10-21下午5:09:15
	 */
	public List<PhyCompleteReport> getPhyCompleteReport(String code,String name){
		String hql = "from PhyCompleteReport where code = ? and name=? ";
		return this.getHibernateTemplate().find(hql, new Object[]{code,name});
	}
	
	/**
	 * 获取所有未拆分固定页的PDF
	 * @return
	 * @author tangxing
	 * @date 2016-10-26下午4:38:52
	 */
	public List<PhyCompleteReport> getPhyCompleteReportByStatus(){
		String hql = "from PhyCompleteReport where status = null";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据会员的code和name获取对应的申友提供的PDF信息
	 * @param code
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016-10-21下午3:15:47
	 */
	/*public List<XreportPdfInfo> getReportPdfInfo(String code,String name){
		String hql = "from XreportPdfInfo where code = ? and name=? ";
		return this.getHibernateTemplate().find(hql, new Object[]{code,name});
	}*/

	
	/**
	 * 获取所有PhyReport(已有12页报告的,未合并过PDF,可以合成的PDF)
	 * @return
	 * @author tangxing
	 * @date 2016-10-19下午4:12:07
	 */
	public List<PhyReport> getAllPhyReport(){
		String hql = "from PhyReport where isSuccess='0' and pdfStatus=null and isMergeStatus='0' ";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据code查询报告
	 * @param geneCode
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午5:27:13
	 */
	public List<PhyReport> getPhyReportByCode(String geneCode){
		String hql = "from PhyReport where geneCode = ? and isSuccess = null ";
		List<PhyReport> list  = this.getHibernateTemplate().find(hql,new Object[]{geneCode});
		return list;
	}
	
	/**
	 * 根据code查询报告（已经生成PDF的）
	 * @param geneCode
	 * @return
	 * @author tangxing
	 * @date 2016年10月19日15:09:11
	 */
	public List<PhyReport> getPhyReportByCodeAndIsSuccess(String geneCode){
		String hql = "from PhyReport where geneCode = ? and isSuccess = '0' ";
		List<PhyReport> list  = this.getHibernateTemplate().find(hql,new Object[]{geneCode});
		return list;
	}
	
	/**
	 * 根据code查找所属公司、年龄
	 * @return
	 * @author tangxing
	 * @date 2016-7-1上午11:02:20
	 */
	public List<Map<String,Object>> getOwnedCompAndAgeByGeneCode(String geneCode){
		String queryString = "select t.owned_company,t.age,t.sampling_Date from Erp_Customer t where t.code=?";
		return this.getJdbcTemplate().queryForList(queryString, new Object[]{geneCode});
	}
	
	/**
	 * 根据PhyReport 的 id 获取PhyReportItem
	 * @param reportId
	 * @return
	 * @author tangxing
	 * @date 2016-6-30下午2:27:37
	 */
	public List<PhyReportItem> getPhyReportItemByReportId(String reportId){
		String hql = "from PhyReportItem where reportId = ?";
		List<PhyReportItem> list  = this.getHibernateTemplate().find(hql,new Object[]{reportId});
		return list;
	}
	
	
	/**
	 * 根据省份证号查询报告
	 * @param userCode
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午5:27:13
	 */
	public List<PhyReport> getPhyReportByUserIdno(String UserIdno){
		String hql = "from PhyReport where UserIdno = ?";
		List<PhyReport> list  = this.getHibernateTemplate().find(hql,new Object[]{UserIdno});
		return list;
	}
	
	/**
	 * 分页
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午5:29:02
	 */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from PhyReport where 1=1");
        searchMap.put("order_importDate", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /**
     * 根据基因公司的疾病code，查找对应的远盟的疾病code，name
     * @param diseaseCode
     * @return
     * @author tangxing
     * @date 2016-6-29下午5:10:54
     */
   public List<Map<String, Object>> getGeneYmDiseaseByDisCode(String diseaseCode){
    	String queryString = "select ym_disease_name,ym_disease_code from gene_ym_disease_relation where gene_company_disease_code=?";
    	return this.getJdbcTemplate().queryForList(queryString, new Object[]{diseaseCode});
    }
   
   
   
   /*  ********report info query********  */
   /**
    * 查找疾病及疾病目录
    * @param ymDiseaseCode
    * @return
    * @author tangxing
    * @date 2016-6-30下午12:04:28
    */
   public List<Map<String, Object>> getDiseaseAndItemByDisCode(String ymDiseaseCode){
	   String queryString = "select  d.group_name,di.disease_code,di.disease_name from Phy_Disease d join phy_disease_item di "+
			   " on d.group_code=di.group_code where di.disease_code=?";
	   return this.getJdbcTemplate().queryForList(queryString, new Object[]{ymDiseaseCode});
   }
   
   /**
    * 查找PhyProject相关
    * @param ymDiseaseCode
    * @param sex
    * @param riskGrade
    * @return
    * @author tangxing
    * @date 2016-6-30下午2:17:33
    */
   public List<Map<String, Object>> getProjectAndItemByDisCode(String ymDiseaseCode,String sex,Integer riskGrade){
	   String queryString = "select p.project_name,p.project_category,p.project_smallcategory from phy_project p join phy_project_item pi " +
	   		" on p.project_code=pi.project_code and p.project_name=pi.project_name"+ 
			" where pi.disease_code=? and pi.sex=? and pi.risk_grade=?";
	   return this.getJdbcTemplate().queryForList(queryString, new Object[]{ymDiseaseCode,sex,riskGrade});
   }
   
   /**
    * save PhyReport
    * @param report
    * @return
    * @author tangxing
    * @date 2016-7-4下午2:52:26
    */
   public String saveReport(PhyReport report){
	   return (String) getHibernateTemplate().save(report);
   }
   
   /**
    * save PhyReportItem
    * @param report
    * @return
    * @author tangxing
    * @date 2016-7-4下午2:52:26
    */
   public String saveReportItem(PhyReportItem reportItem){
	   return (String) getHibernateTemplate().save(reportItem);
   }
   
   /**
    * 获取 createPdfStatus状态为空的PhyReport
    * @return
    * @author tangxing
    * @date 2016-12-6下午12:23:32
    */
	public List<PhyReport> getPhyReportByCreatePdfStatusNull(){
		String hql = "from PhyReport where  createPdfStatus is null";
		List<PhyReport> list  = this.getHibernateTemplate().find(hql);
		return list;
	}
   
	/**
	 * 根据 createPdf status获取能生成报告的PhyReport
	 * @return
	 * @author tangxing
	 * @date 2016年12月6日15:04:17
	 */
	public List<PhyReport> getPhyReportByCreatePdfStatus(){
		String hql = "from PhyReport where createPdfStatus = '1' ";
		List<PhyReport> list  = this.getHibernateTemplate().find(hql);
		return list;
	}
	
	/**
	 * 分页查询excel文件路径类
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-12-6下午6:02:02
	 */
    public List findByPageExcelFile(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from PhyReportExcelFile where 1=1");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
	/**
	 * @param jdbcSql 要查询的 select * from a 
	 * @param lists 参数,(注意: 跟通配符对应顺序); 
	 * @param 在继承BaseDao的Dao中实现该内部类实现RowMapper<T>接口; 处理要查询的类的封装; 
	 * //RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(Class<T> returnType); //根据类型自动封装rowMapper对象;简化操作;
	 * @return
	 */
	public List<?> findJdbcListLocal(String content, List<Object> lists, RowMapper<?> rowMapper) {
		StringBuilder listSql = new StringBuilder("select *  from( ");
		listSql.append("select ROWNUM RN,");
		listSql.append(content);
		//分页;
		listSql.append(" and rownum <= ? ");
		listSql.append(" ORDER BY t.pimportDate DESC ");
		listSql.append(" ) where RN >＝ ? ");
		return this.getJdbcTemplate().query(listSql.toString(), lists.toArray(), rowMapper);
	}
	
	/* *****  1+X风险数据提交跟踪 方法 start ***** */
	
	/**
	 * 获取项目套餐对应表(isCreatePdf 为 '1'的)
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午4:53:20
	 */
	public List<ErpRelationShipCombo> getErpRelationShipComboByCreatePdf(){
		String hql = "from ErpRelationShipCombo rsc where rsc.isCreatePdf='1'";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据项目ID获取支公司id
	 * @param shipProId
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午5:27:36
	 */
	public String getBranchCompanyIdByShipProId(String shipProId){
		String branchCompanyId = "";
		String sql = "select cr.customer_relationship_id as branchCompanyId from hl_customer_relationship_pro cr where cr.IS_DELETED = '0' and cr.id=? ";
		List<Map<String, Object>> results = this.getJdbcTemplate().queryForList(sql, new Object[]{shipProId});
		if(results!=null&&results.size()>0){
			branchCompanyId = (String) results.get(0).get("branchCompanyId");
		}
		return branchCompanyId;
	}
	
	/**
	 * 根据套餐ID获取套餐名
	 * @param comboId
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午5:31:21
	 */
	public String getComboNameByComboId(String comboId){
		String comboName = "";
		String sql = "select jc.combo_name as comboName from hl_jy_combo jc where jc.IS_DELETE='0' and jc.id = ?";
		List<Map<String, Object>> results = this.getJdbcTemplate().queryForList(sql, new Object[]{comboId});
		if(results!=null&&results.size()>0){
			comboName = (String) results.get(0).get("comboName");
		}
		return comboName;
	}
	
	/**
	 * 根据支公司ID和套餐名获取ErpCustomer
	 * @param branchId
	 * @param comboName
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午5:40:49
	 */
	public List<Map<String, Object>> getCustomerByBranchIdAndComboName(String branchId,String comboName){
		//String hql = "from ErpCustomer c where c.isDeleted = 0 and c.pdffilepath is not null and c.branchCompanyId=? and c.setmealName=? ";
		//return this.getHibernateTemplate().find(hql,new Object[]{branchId,comboName});
		
		String sql = "select distinct c.code from erp_customer c where c.is_deleted=0 and c.PDFFILEPATH is not null and c.BRANCH_COMPANY_ID=? and c.SETMEAL_NAME=?";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{branchId,comboName});
	}
	
	/**
	 * 获取有报告的1+X数据
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午6:43:20
	 */
	public List<Map<String, Object>> getPhyReportByReportPath(){
		String sql = "select distinct pr.GENE_CODE as code from phy_report pr where pr.REPORT_PATH is not null";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 拿到报告的获取时间
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午7:02:40
	 */
	public List<Map<String, Object>> getPdfContentTime(String code){
		String sql = "select rp.createDate,rp.setmeal_name as comboName from erp_reportdetail_pdfcontent rp where rp.matchstate = '2' and rp.code=? ";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{code});
	}
	
	/**
	 * 获取当前code最新的报告时间
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-23上午11:33:52
	 */
	public String getPdfContentMaxTime(String code){
		String createDate = "" ;
		String sql = "select to_char(max(rp.createdate),'yyyy-mm-dd hh24:mi:ss') as createDate from erp_reportdetail_pdfcontent rp where rp.matchstate = '2' and rp.code=?";
		List<Map<String, Object>> results = this.getJdbcTemplate().queryForList(sql,new Object[]{code});
		
		if(results!=null&&results.size()>0){
			createDate = (String) results.get(0).get("createDate");
		}
		
		return createDate;
	}
	
	/**
	 * 获取customer的相关信息（用于生成excel）
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-23下午1:40:09
	 */
	public List<Map<String, Object>> getExcelCustomerInfo(String code){
		String sql = "select e.batchno,e.events_no as eventsNo,c.code,c.name "
				+" from  erp_customer c join erp_events e "
				+" on c.events_no=e.events_no "
				+" where c.is_deleted=0 and e.is_deleted=0 and c.code=?";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{code});
	}
	
	/**
	 * 判断是否存在该code的phyReport
	 * @return
	 * @author tangxing
	 * @date 2017-2-23下午1:23:20
	 */
	public int getReportByCodeJDBC(String code){
		String sql = "select count(1) from phy_report r where r.gene_code=?";
		return this.getJdbcTemplate().queryForInt(sql,new Object[]{code});
	}
	
	/* *****  1+X风险数据提交跟踪 方法 end ***** */
	
}
