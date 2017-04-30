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
import org.hpin.genepack.dao.GenepackDetailDao;
import org.hpin.genepack.entity.Genepack;
import org.hpin.genepack.entity.GenepackDetail;

@Service(value = "org.hpin.genepack.service.GenepackDetailService")
@Transactional()
public class GenepackDetailService extends BaseService {
	@Autowired
	private GenepackDetailDao dao;
	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public GenepackDetail getGenepackDetail(String id){
		return dao.get(id);
	}
	/**
	 * 根据批次号查询
	 * @param batchno
	 * @return
	 */
	public List getGenepackDetailList(String batchno){
		return dao.getGenepackDetailList(batchno);
	}
	/**
	 * 增加保存
	 * @param genepackDetail
	 */
	public void save(GenepackDetail genepackDetail){
		genepackDetail.setIdcount(getMaxId(genepackDetail.getBatchno())+1);
		genepackDetail.setIsDeleted(0);
		dao.save(genepackDetail);
	}
	public void update(GenepackDetail genepackDetail){
		GenepackDetail _genepackDetail = (GenepackDetail) dao.findById(GenepackDetail.class, genepackDetail.getId());
		BeanUtils.copyProperties(_genepackDetail, genepackDetail);
		_genepackDetail.setUpdateTime(new Date());
		dao.update(_genepackDetail);
	}
	/**
	 * 获取最大ID
	 * @param batchno
	 * @return
	 */
	public int getMaxId(String batchno){
		return dao.getMaxIdcount(batchno);
	}
}
