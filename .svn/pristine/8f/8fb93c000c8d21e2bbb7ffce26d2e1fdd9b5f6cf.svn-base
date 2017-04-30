package org.hpin.venueStaffSettlement.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.customerrelationship.entity.ProjectType;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.widget.pagination.Page;
import org.hpin.venueStaffSettlement.entity.ErpNonConference;
import org.hpin.venueStaffSettlement.entity.vo.NonConferenceCost;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Carly
 * @since 2017年1月22日10:33:18
 */
@Repository()
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpNonConferenceDao extends BaseDao {

	public void getNonConferenceList(Page<ErpNonConference> page,ErpNonConference nonConference) {
		StringBuilder sql = new StringBuilder();
		sql.append("select cn.id, cn.projectCode,cn.projectUser,cn.projectName,pt.project_type_name projectTypeName,cn.startTime,cn.endTime,cn.month,cn.OASerial,cn.fees,cn.note ");
		sql.append("from erp_nonconference cn,t_project_type pt where cn.isdeleted=0 and pt.id=cn.projecttype and  pt.IS_DELETED=0 ");
		if(null!=nonConference){
			sql.append(dealQuerySql(nonConference));
		}
		sql.append("order by cn.createTime desc");
		List<Object> params = new ArrayList<Object>();
		page.setTotalCount(this.findJdbcCount(sql, params));
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpNonConference> rowMapper = new BeanPropertyRowMapper<ErpNonConference>(ErpNonConference.class);
		page.setResults(this.findJdbcList(sql, params, rowMapper));
		
	}
	
	private StringBuilder dealQuerySql(ErpNonConference nonConference){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("");
		if(StringUtils.isNotEmpty(nonConference.getProjectCode())){//项目编码
			sqlBuilder.append("and cn.projectCode like '%").append(nonConference.getProjectCode()).append("%' ");
		}
		if(StringUtils.isNotEmpty(nonConference.getProjectUser())){//项目负责人
			sqlBuilder.append("and cn.projectUser like '%").append(nonConference.getProjectUser()).append("%' ");
		}
		if(StringUtils.isNotEmpty(nonConference.getProjectType())){//项目类型
			sqlBuilder.append("and cn.projectType ='").append(nonConference.getProjectType()).append("' ");
		}
		if(StringUtils.isNotEmpty(nonConference.getMonth())){//月份
			sqlBuilder.append("and cn.month='").append(nonConference.getMonth()).append("' ");
		}
		if(StringUtils.isNotEmpty(nonConference.getOASerial())){//OA流水号
			sqlBuilder.append("and cn.OASerial like '%").append(nonConference.getOASerial()).append("%' ");
		}
		return sqlBuilder;
	}

	/**
	 * @param id
	 * @return 删除会议
	 */
	public int deleteNonConference(String id) {
		String sql = "update erp_nonconference set isdeleted=1 where id =?";
		return this.getJdbcTemplate().update(sql,id);
	}

	/**
	 * @param projectId
	 * @return 根据项目id获取信息
	 */
	public ProjectType getProjectInfo(String projectId) {
		ProjectType type = this.getHibernateTemplate().get(ProjectType.class, projectId);
		return type;
	}

	/**
	 * @author Carly
	 * @since 2017年4月7日18:57:30
	 * @param nonConference
	 * @return 根据查询条件获取结果
	 */
	public List<NonConferenceCost> queryForList(ErpNonConference nonConference) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.projectCode projectCode, a.projectUser projectUser, a.projectName projectName, ")
			.append("t.project_type_name projectType, a.OASerial OASerial, a.startTime startTime, a.endTime endTime, a.month month, ")
			.append("enc.name, enc.travelCost travelCost, enc.cityCost cityCost, enc.provinceCost provinceCost, ")
			.append("enc.hotelCost hotelCost, enc.laborCost laborCost, enc.otherCost otherCost, enc.amount amount ")
			.append("from erp_nonconference a, erp_nonconference_cost enc, t_project_type t ")
			.append("where a.isdeleted=0 and enc.nonconferenceid = a.id and t.id=a.projecttype and t.is_deleted=0");
		if (StringUtils.isNotEmpty(nonConference.getProjectCode())) {
			sql.append( "and a.projectCode like '%" ).append(nonConference.getProjectCode()).append("%' ");
		}
		if (StringUtils.isNotEmpty(nonConference.getProjectUser())) {
			sql.append( "and a.projectUser like '%" ).append(nonConference.getProjectUser()).append("%' ");
		}
		if (StringUtils.isNotEmpty(nonConference.getProjectType())) {
			sql.append( "and a.projectType = '" ).append(nonConference.getProjectType()).append("' ");
		}
		if (StringUtils.isNotEmpty(nonConference.getMonth())) {
			sql.append( "and a.month = '" ).append(nonConference.getMonth()).append("' ");
		}
		if (StringUtils.isNotEmpty(nonConference.getOASerial())) {
			sql.append( "and a.oaserial like '%" ).append(nonConference.getOASerial()).append("%' ");
		}
		return this.getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(NonConferenceCost.class));
	}
    
    
	
}
