/**
 * @author DengYouming
 * @since 2016-5-19 上午10:53:48
 */
package org.hpin.base.dict.service;

import java.util.List;
import java.util.Map;

import org.hpin.base.dict.dao.ErpDictDao;
import org.hpin.base.dict.entity.ErpDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 数据字典Service
 * @author DengYouming
 * @since 2016-5-19 上午10:53:48
 */
@Service(value = "org.hpin.base.dict.service.ErpDictService")
@Transactional()
public class ErpDictService {

	@Autowired
	private ErpDictDao dao;
	
	/**
	 * 批量保存
	 * @param list
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-19 上午10:54:49
	 */
	public boolean saveList(List<ErpDict> list) throws Exception{
		boolean flag = false;
		if(list!=null&&list.size()>0){
			flag = dao.saveList(list);
		}
		return flag;
	}
	
	/**
	 * 单个保存
	 * @param entity
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-19 上午10:58:34
	 */
	public boolean save(ErpDict entity) throws Exception{
		boolean flag = false;
		if(null!=entity){
			dao.save(entity);
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 条件查询
	 * @param params 查询条件
	 * @param exact true:精确查询， false：模糊查询
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-19 上午11:00:02
	 */
	public List<ErpDict> listByProps(Map<String,Object> params, boolean exact) throws Exception{
		 List<ErpDict> list = null;
		 list = dao.listByProps(params, exact);
		 return list;
	}
}
