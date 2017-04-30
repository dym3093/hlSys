package org.hpin.base.dict.dao;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.dict.entity.DictMailBase;
import org.hpin.base.dict.entity.DictMailSysdict;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository(value="org.hpin.base.dict.dao.DictMailBaseDao")
public class DictMailBaseDao extends BaseDao{

	/**
	 * 拼接sql查询条件
	 * @param params
	 * @return
	 */
	public StringBuilder dealDictMailSql(HashMap<String, String> params) {
		StringBuilder jdbcsql=dictMailSql();
		if (params !=null) {
			String userName=(String)params.get("userName");
			if (StringUtils.isNotEmpty(userName)) {
				jdbcsql.append(" and m.USERNAME like '%").append(userName.trim()).append("%' ");
			}
			String isStatus = (String)params.get("isStatus");
			if(StringUtils.isNotEmpty(isStatus)) {
				jdbcsql.append(" and m.IS_STATUS = '").append(isStatus).append("'");
			}
			jdbcsql.append(" group by m.id,").append(" m.USERNAME,").append(" m.MAIL_ADDR,")
			.append(" m.IS_STATUS,").append(" m.IS_DELETED,").append(" m.CREATE_TIME ");
		}
		return jdbcsql;
	}

	/**
	 * 拼接sql
	 * @return
	 */
	private StringBuilder dictMailSql() {
		StringBuilder jdbcsql=new StringBuilder("select "+
				"m.id, " +
				"m.USERNAME userName, " +
				"m.MAIL_ADDR mailAddress, " +
				"m.IS_STATUS isStatus, " +
				"m.IS_DELETED isDeleted, " +
				"m.CREATE_TIME createTime, " +
				"wmsys.wm_concat(s.DICTNAME) dictConcat " +
				"from " +
				"DICT_MAIL_BASE m "+
				"left join DICT_MAIL_SYSDICT sm on m.id=sm.MAIL_BASE_ID " +
				"left join SYS_DICTTYPE s on s.DICTID=sm.SYS_DICTTYPE_DICTID " +
				"where 1=1 " +
				"and m.is_deleted =0 "
				);
		return jdbcsql;
	}

	/**
	 * 保存DictMailBase表
	 * @param dictMailBase
	 */
	public void saveDictMailBase(DictMailBase dictMailBase) {
		this.getHibernateTemplate().save(dictMailBase);
		
	}

	/**
	 * 保存中间表
	 * @param dictMailSysdict
	 */
	public void saveDictMailSysdict(DictMailSysdict dictMailSysdict) {
		this.getHibernateTemplate().save(dictMailSysdict);
		
	}

	/**
	 * 更新
	 * @param dictMailSysdict
	 */
	public void updateDictMailBase(DictMailBase dictMailSysdict) {
		this.getHibernateTemplate().update(dictMailSysdict);
		
	}

	/**
	 * 删除中间表
	 * @param mailBaseId
	 */
	public void deleteDictMailBase(String mailBaseId) {
		String sql = "delete from DICT_MAIL_SYSDICT where MAIL_BASE_ID='"+mailBaseId+"'";
		this.getJdbcTemplate().update(sql);
		
	}

	
}
