package org.hpin.events.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpCustomerBakDao;
import org.hpin.events.entity.ErpCustomerBak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.events.service.ErpCustomerBakService")
@Transactional()
public class ErpCustomerBakService extends BaseService {
	@Autowired
	private ErpCustomerBakDao dao;
	
	/*非物理删除*/
	public void deleteInfo(List<ErpCustomerBak> list) {
		for(ErpCustomerBak c:list){
			dao.update(c);
		}
	}
}
