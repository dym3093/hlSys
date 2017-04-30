package org.hpin.base.accessories.dao;

import java.util.List;

import org.hpin.base.accessories.entity.TawCommonsAccessories;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.log.util.HpinLog;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;
/**
 * 
 * <p>Title:
 * </p>
 * <p>Description:附件管理DAO类实现类
 * </p>
 * <p>Apr 10, 2007 10:58:30 AM</p> 
 *
 * @author sherry
 * @version 1.0
 *
 */
@Repository()
public class TawCommonsAccessoriesDao extends BaseDao
		 {

	/**
	 * 获取所有附件
	 * @see org.hpin.system.accessories.dao.TawCommonsAccessoriesDao#getTawCommonsAccessoriess(org.hpin.system.accessories.entity.TawCommonsAccessories)
	 */
	public List<TawCommonsAccessories> getTawCommonsAccessoriess( ) {
		return getHibernateTemplate().find("from TawCommonsAccessories");

	}

	/**
	 * 根据Id获取附件
	 * @see org.hpin.system.accessories.dao.TawCommonsAccessoriesDao#getTawCommonsAccessories(String
	 *      id)
	 */
	public TawCommonsAccessories getTawCommonsAccessories(final String id) {
		TawCommonsAccessories tawCommonsAccessories = (TawCommonsAccessories) getHibernateTemplate()
				.get(TawCommonsAccessories.class, id);
		if (tawCommonsAccessories == null) {
			HpinLog.warn(this,"uh oh, tawCommonLogDeploy with id '" + id + "' not found...");
			throw new ObjectRetrievalFailureException(
					TawCommonsAccessories.class, id);
		}

		return tawCommonsAccessories;
	}
	/**
	 * 根据实际文件的编号查询文件信息（附件上传接口用）
	 * 
	 * @author 赵东亮
	 * @param accesspriesFileName
	 *            文件的编号（类似20081214092334）
	 * @return
	 */
	public List getFileByName(String accesspriesFileName) {
		if (accesspriesFileName == null
				|| "".equals(accesspriesFileName.trim())) {
			accesspriesFileName = "''";
		}
		String sql = "from TawCommonsAccessories as hpinCommonsAccessories "
				+ "where  hpinCommonsAccessories.accessoriesName like '%"
				+ accesspriesFileName + "%'";
		List list = getHibernateTemplate().find(sql);
		return list;
	}


	/**
	 * 保存附件
	 * @see org.hpin.system.accessories.dao.TawCommonsAccessoriesDao#saveTawCommonsAccessories(TawCommonsAccessories
	 *      tawCommonsAccessories)
	 */
	public void saveTawCommonsAccessories(
			final TawCommonsAccessories tawCommonsAccessories) {
		if ((tawCommonsAccessories.getId() == null)
				|| (tawCommonsAccessories.getId().equals("")))
			getHibernateTemplate().save(tawCommonsAccessories);
		else
			getHibernateTemplate().saveOrUpdate(tawCommonsAccessories);
	}

	/**
	 * 删除附件
	 * @see org.hpin.system.accessories.dao.TawCommonsAccessoriesDao#removeTawCommonsAccessories(String
	 *      id)
	 */
	public void removeTawCommonsAccessories(final String id) {
		getHibernateTemplate().delete(getTawCommonsAccessories(id));
	}

	/**
	 * 根据文件名称查询文件信息
	 * @author 
	 * @param accesspriesFileNames 文件名称
	 * @return
	 */
	public List<TawCommonsAccessories> getAllFileByName(String accesspriesFileNames) {
		if(accesspriesFileNames==null || "".equals(accesspriesFileNames.trim())){
			accesspriesFileNames="''";
			return null;
		}
		if( accesspriesFileNames.equals("ognl.NoConversionPossible")){
			accesspriesFileNames = "";
			return null;
		}
		String sql = "from TawCommonsAccessories as tawCommonsAccessories "
				+ "where  tawCommonsAccessories.accessoriesName in ("
				+ accesspriesFileNames + ")";
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	
	/**
	 * 根据时间戳名字取汉语名字
	 */

	public List getNameByDateName(String name) {
		String sql = "from TawCommonsAccessories where accessoriesName in ("
				+ name + ")";
		List list = getHibernateTemplate().find(sql);
		return list;
	}
}
