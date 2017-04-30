package org.hpin.foreign.service;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.foreign.dao.ErpReportUrlJYDao;
import org.hpin.foreign.entity.ErpReportUrlJY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.foreign.service.ErpReportUrlJYService")
@Transactional()
@SuppressWarnings({"rawtypes"})
public class ErpReportUrlJYService extends BaseService {

	@Autowired
	ErpReportUrlJYDao dao;
	
	public List<ErpReportUrlJY> findByPage(Page page, Map map) {
		return dao.findByPage(page, map);
	}

	public List<ErpReportUrlJY> listByProps(Map<String,String> params) throws Exception{
		List<ErpReportUrlJY> list = null;
		if (params!=null&&params.size()>0){
			list = dao.listByProps(params);
		}
		return list;
	}

	/**
	 * 清除旧的URL数据
	 * @param code 条码
	 * @param name 姓名
	 * @return boolean
	 */
	public boolean cleanOldUrl(String code, String name){
		return dao.cleanOldUrl(code, name);
	}
}
