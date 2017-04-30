package org.hpin.genepack.service;

import org.hpin.common.core.orm.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.common.util.BeanUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hpin.genepack.dao.GenepackDeliveryDao;
import org.hpin.genepack.entity.GenepackDelivery;
@Service(value = "org.hpin.genepack.service.GenepackDeliveryService")
@Transactional()
public class GenepackDeliveryService extends BaseService {
	
	@Autowired
	private GenepackDeliveryDao genepackDeliveryDao;
	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return genepackDeliveryDao.findByPage(page, searchMap);
	}
	public GenepackDelivery getGenepackDelivery(String id){
		return genepackDeliveryDao.get(id);
	}
	public void update(GenepackDelivery genepackDelivery){
		GenepackDelivery _genepackDelivery = (GenepackDelivery) genepackDeliveryDao.findById(GenepackDelivery.class, genepackDelivery.getId());
		BeanUtils.copyProperties(_genepackDelivery, genepackDelivery);
		_genepackDelivery.setUpdateTime(new Date());
		genepackDeliveryDao.update(_genepackDelivery);
	}
}
