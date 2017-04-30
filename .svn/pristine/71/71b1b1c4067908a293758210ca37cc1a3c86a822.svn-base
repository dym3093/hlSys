package org.hpin.events.service;

import java.util.List;

import org.hpin.events.dao.ExpressDao;
import org.hpin.events.entity.Express;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.events.service.ExpressService")
@Transactional()
public class ExpressService {
	@Autowired
	ExpressDao dao;
	public List<Express>  listExpress(String postid){
		return dao.listExpress(postid);
	}
}
