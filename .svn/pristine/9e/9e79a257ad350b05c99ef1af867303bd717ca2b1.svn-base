package org.hpin.genepack.service;

import org.hpin.common.core.orm.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hpin.genepack.dao.GenepackDao;
import org.hpin.genepack.dao.GenepackDetailDao;
import org.hpin.genepack.entity.Genepack;
@Service(value = "org.hpin.genepack.service.GenepackService")
@Transactional()
public class GenepackService extends BaseService {
	@Autowired
	private GenepackDao dao;
	@Autowired
	private GenepackDetailDao detailDao;
	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	public Genepack getGenepack(String id){
		return dao.get(id);
	}
	
	/**
	 * 查找指定基因物品，并修改
	 * @description 
	 * @param genepack
	 *
	 * @return void
	 * @throws
	 *
	 */
	public void update(Genepack genepack){
		Genepack _genepack = (Genepack) dao.findById(Genepack.class, genepack.getId());
		BeanUtils.copyProperties(_genepack, genepack);
		_genepack.setUpdatetime(new Date());
		dao.update(_genepack);
//	    genepack.setUpdatetime(new Date());
//        dao.update(genepack);
	    
	}
	public List<Genepack> findListByDeliverybatchno(String deliverybatchno){
		return dao.findByProperty(Genepack.class, "deliverybatchno", deliverybatchno, null, null);
	}
}
