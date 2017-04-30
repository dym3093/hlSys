package org.hpin.base.customerrelationship.service;

import java.util.List;

import org.hpin.base.customerrelationship.dao.GeneTestInstitutionDao;
import org.hpin.base.customerrelationship.entity.GeneTestInstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 增加修改ErpCustomer页面【检测结构】下拉选项 service
 * @author LeslieTong
 * @date 2017-4-12下午5:03:34
 */

@Service(value = "org.hpin.base.customerrelationship.service.GeneTestInstitutionService")
@Transactional()
public class GeneTestInstitutionService {
	@Autowired
	private GeneTestInstitutionDao dao;

	public List<GeneTestInstitution> findGeneTestInstitution(){
		return dao.findGeneTestInstitution();
	}
	
}
