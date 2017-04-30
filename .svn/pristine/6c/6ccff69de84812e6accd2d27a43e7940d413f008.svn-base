package org.hpin.barcode.service;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.hpin.barcode.dao.BarCodeBatDao;
import org.hpin.barcode.entity.BarCodeBat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.barcode.service.BarCodeBatService")
@Transactional()
public class BarCodeBatService extends BaseService{
	@Autowired
	private BarCodeBatDao barCodeBatDao = null;
	public String findNewBatNo(String batdate){
		String no="";
		int count=barCodeBatDao.findBatdateNextNum(batdate);
		if(count<=9){
			no="0"+count;
		}else{
			no=String.valueOf(count);
		}		
		return batdate+no;
	}
	public void update(BarCodeBat barCodeBat){
		BarCodeBat bat = (BarCodeBat)barCodeBatDao.findById(BarCodeBat.class, barCodeBat.getId());
		BeanUtils.copyProperties(bat, barCodeBat);
		barCodeBatDao.update(bat);
	}
	public void updateStatus(int statuts,String code){
		barCodeBatDao.updateStatus(statuts, code);
	}
	public void updateBarCodeStatus(int status,String batNo){
		barCodeBatDao.updateBarCodeStatus(status, batNo);
	}
}
