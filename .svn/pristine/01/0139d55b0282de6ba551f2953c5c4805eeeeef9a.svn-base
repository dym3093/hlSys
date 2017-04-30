/**
 * 
 */
package org.hpin.reportdetail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpPrintTask;
import org.hpin.reportdetail.entity.ErpPrintTaskBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: dengqin 
* createDate: 2016-4-14 下午5:32:15
*/
/**
 * @author dengqin
 *
 */

@Repository()
public class ErpPrintTaskDao extends BaseDao{

    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        System.out.println("查询的Dao");
//      StringBuffer query = new StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");//没有isEnable
        StringBuffer query = new StringBuffer(" from ErpPrintTask where 1=1 and isdelete='0'");
        searchMap.put("order_createTime", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /*
     * 统计
     */
    public List getAllInputNumsList(String userName){
        List list=null;
        String queryString="select printTaskNo,count(*) as total from ERP_PRINTTASK where isdeleted=0 group by printTaskNo";
        list = this.getJdbcTemplate().queryForList(queryString);
         return list;
    }
    
    /**
     * 查询当天最大批次号
     * @param date
     * @return
     */
    public String maxNo(String date){
        String conferenceNo="HL1512142048000";
        String sql = "select max(conference_no) as conference_no from Erp_Conference where to_char(conference_Date,'yyyy-mm-dd')='"+date+"'";
        List list = this.getJdbcTemplate().queryForList(sql);
        Map map = (Map) list.get(0);
        String maxNo = (String) map.get("CONFERENCE_NO");
        if(StrUtils.isNotNullOrBlank(maxNo)){
            conferenceNo=maxNo.toString();
        }
        return conferenceNo;
    }
    
    
    /**
     * 根据ID获取批次
     * @description 
     * @param id
     * @return
     *
     * @return ErpConference
     * @throws
     *
     */
    public ErpPrintTask get(String id){
        return (ErpPrintTask)this.findById(ErpPrintTask.class, id);
    }
    
    /**
     * 是否存在已有的打印
     * @param compannyName 支公司名称 eventDate 场次日期
     * @return
     */
    public String isNotRepeatNo(String printTaskNo){
        String repeat="";
        String hql = "from ErpPrintTask where printTaskNo";
//      String hql = "from ErpEvents where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 ";
        System.out.println("校验批次："+hql);
        List list = this.getHibernateTemplate().find(hql,new Object[]{printTaskNo});
//      List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate});
        if(list.size()>0){
            repeat="该时间已经存在批次";
        }
        return repeat;
    }
    
    /**
     * 根据条码查询其所在的打印任务号
     * @param codes
     * @param exact 是否精确查找，true：是，false：否
     * @return
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-27 下午5:50:51
     */
    public List listPrintTaskNoByCodes(String codes, boolean exact) throws Exception{
    	List list = null;
    	if(!"".equals(codes)&&null!=codes&&codes.length()>0){
    		String sql = null;
    		if(codes.indexOf(",")!=-1){    		
    			sql = " select p.* from erp_printtask p where p.printtaskno in ( select distinct(t.printtaskno) from erp_reportdetail_pdfcontent t where t.code in ("+codes+") ) ";
    		}else{
    			if(exact){
        			sql = " select p.* from erp_printtask p where p.printtaskno in ( select t.printtaskno from erp_reportdetail_pdfcontent t where t.code = "+codes+" ) ";
    			}else{
    				sql = " select p.* from erp_printtask p where p.printtaskno in ( select t.printtaskno from erp_reportdetail_pdfcontent t where instr(t.code,"+codes+")>0 )";
    			}
    		}

    		if(sql!=null&&sql.length()>0){
     			list = this.getJdbcTemplate().queryForList(sql);
    		}
    	}
    	
    	return list;
    }
    
    /**
     * 根据条码查询打印批次(精确查找)
     * @param code
     * @return ErpPrintTask
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-27 下午7:41:26
     */
    public ErpPrintTask findPrintTaskByCode(String code) throws Exception{
    	ErpPrintTask entity = null;
    	String sql = " select p.* erp_printtask p where p.printtaskno exists ( select t.printtaskno from erp_reportdetail_pdfcontent t where t.code = '"+code+"' ) ";
    	List list = this.getJdbcTemplate().queryForList(sql);
    	if(!CollectionUtils.isEmpty(list)){
    		Object obj = list.get(0);
    		if(obj instanceof ErpPrintTask){
    			entity = (ErpPrintTask) obj;
    		}
    	}
    	return entity;
    }

	public List<ErpPrintTaskBean> getPrintTaskInfoByCode(String codes,Page page,String expressBySlh){
		StringBuilder sql = new StringBuilder();
//		StringBuilder helps = new StringBuilder();
		List<Object> objs = new ArrayList<Object>();
		objs.add(expressBySlh);
		sql.append("select p.id,p.printtaskno,p.printcompany,p.address,p.expressrecipient,p.telephone,p.expressno,p.expecttime,p.createtime,p.printstate,p.downloadpath, p.downloadStatus from erp_printtask p,");
		sql.append("(select distinct(printtaskno) from erp_print_task_content where code in("+codes+"))b where isdelete='0' and expressbyslh=? and p.printtaskno IN b.printtaskno");
		page.setTotalCount(this.findJdbcCount(sql, objs));
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		List<ErpPrintTaskBean> lists = (List<ErpPrintTaskBean>) this.findJdbcList(sql, objs, new ReportValidQueryRowMapper());
		return lists;
	}
	
	class ReportValidQueryRowMapper implements RowMapper<ErpPrintTaskBean> {

		@Override
		public ErpPrintTaskBean mapRow(ResultSet rs, int index) throws SQLException {
			ErpPrintTaskBean bean = new ErpPrintTaskBean();
			bean.setAddress(rs.getString("address"));
			bean.setId(rs.getString("id"));
			bean.setPrintTaskNo(rs.getString("printTaskNo"));
			bean.setPrintCompany(rs.getString("printCompany"));
			bean.setAddress(rs.getString("address"));
			bean.setExpressRecipient(rs.getString("expressRecipient"));
			bean.setTelephone(rs.getString("telephone"));
			bean.setExpressNo(rs.getString("expressNo"));
			bean.setExpectTime(rs.getDate("expectTime"));
			bean.setCreateTime(rs.getDate("createTime"));
			bean.setPrintState(rs.getString("printState"));
			bean.setDownLoadPath(rs.getString("downloadPath"));
			return bean;
		}
		
	}

	public List<ErpPrintTask> getPrintTaskById(String printTaskId) {
		StringBuilder helps = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		String [] printTaskNos = printTaskId.split(",");
		for (int i = 0; i < printTaskNos.length; i++) {
			if(i==0) {
				helps.append("'"+printTaskNos[i]+"'");
			} else {
				helps.append(",").append("'"+printTaskNos[i]+"'");
			}
		}
		sql.append("select * from erp_printtask where isdelete='0' and id in("+helps+")  order by createtime desc");
		return this.getJdbcTemplate().query(sql.toString(),new BeanPropertyRowMapper(ErpPrintTask.class));
	
	}

	/**
	 * 根据Id修改下载状态为已下载;
	 * create by henry.xu 20161024
	 * @param id
	 */
	public void modifiedDownloadStatusById(String id) {
		String updateSql = "UPDATE erp_printtask SET DOWNLOADSTATUS='1' WHERE ID='"+ id +"'";
		this.getJdbcTemplate().update(updateSql);
		
	}

	/**
	 * @description 查找超时的打印任务
	 * @param hours 超时的小时数
	 * @return list
	 * @author YoumingDeng
	 * @since: 2016/12/16 14:05
	 */
	public List<ErpPrintTask> listOvertimeTask(Integer hours){
		String sql = "  select t.* from erp_printtask t where t.isdelete = 0 and t.printstate = '3' and round(TO_NUMBER(sysdate - t.createtime)*24,4) > ? order by t.createtime desc ";
		List<ErpPrintTask> list = this.getJdbcTemplate().query(sql, new Object[]{hours}, new BeanPropertyRowMapper<ErpPrintTask>(ErpPrintTask.class));
		return list;
	}
}






























