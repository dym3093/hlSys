package org.hpin.barcode.service;

import org.hpin.barcode.dao.BarCodeIssueDao;
import org.hpin.barcode.entity.BarCodeIssue;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.barcode.service.BarCodeIssueService")
@Transactional()
public class BarCodeIssueService extends BaseService{
	@Autowired
	private BarCodeIssueDao barCodeIssueDao = null;
	public void update(BarCodeIssue barCodeIssue){
		BarCodeIssue issue = (BarCodeIssue)barCodeIssueDao.findById(BarCodeIssue.class, barCodeIssue.getId());
		BeanUtils.copyProperties(issue, barCodeIssue);
		barCodeIssueDao.update(issue);
	}
	public void deleteIds(String[] ids) {
		for(String id:ids){
			BarCodeIssue issue = (BarCodeIssue)barCodeIssueDao.findById(BarCodeIssue.class, id);
			issue.setIsDeleted(1);
			barCodeIssueDao.update(issue);
		}
		
	}
	public Boolean isEnoughStock(int barcodeCount){
		int stockcount=barCodeIssueDao.findStockNums();
		if(barcodeCount>stockcount){
			return false;
		}else{
			return true;
		}
	}
}
