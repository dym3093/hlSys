package org.hpin.reportdetail.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.dao.ErpReportdetailImgtaskDao;
import org.hpin.reportdetail.entity.ErpReportdetailImginfo;
import org.hpin.reportdetail.entity.ErpReportdetailImgtask;
import org.hpin.reportdetail.util.DealFileConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service(value = "org.hpin.reportdetail.service.ErpReportdetailImgtaskService")
@Transactional()
@SuppressWarnings("unchecked")
public class ErpReportdetailImgtaskService extends BaseService {
	private Logger log = Logger.getLogger(ErpReportdetailImgtaskService.class);
	
	@Autowired
	ErpReportdetailImgtaskDao dao;
	
	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap){
		return dao.findByPage(page, searchMap);
	}
	
	/**
	 * 获取 erp_customer_temp_wuchuang 的code和age
	 * @param code
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-30上午11:59:49
	 */
	public Map<String, Object> getCustomerWuChuangByCode(String code){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> result = dao.getCustomerWuChuangByCode(code);
		if(result!=null&&result.size()>0){
			log.info("getCustomerWuChuangByCode -- "+result.toString());
			map = result.get(0);
		}
		return map;
	}

	/**
	 * @param ids
	 * @param mapCommonPages 公共页的map
	 * @param jpgToPath jpg存放的位置
	 * @return 通过勾选的信息转图片
	 */
	
	public boolean pdf2Jpg(String ids, String jpgToPath, Map<Integer, String> mapCommonPages) throws Exception {
		ids = assembleIds(ids);
		Date date = new Date();
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		String filePathSql = "from ErpReportdetailImgtask where id in(" + ids + ") and isDeleted=0";	//获取未转换的报告
		String updateStateSql = "update erp_reportdetail_imgtask set state=? where id=?";
		List<ErpReportdetailImgtask> imgtaskList = dao.getHibernateTemplate().find(filePathSql);
		
		int count = 1;
		int size = imgtaskList.size();
		for(ErpReportdetailImgtask imgtask:imgtaskList){
			log.info("共有" + size + "个报告,开始转换第" + count + "个报告,剩余" + (size-count) + "个报告");
			if (existedReportStatus(imgtask.getId())) {
				dao.getJdbcTemplate().update(updateStateSql,4,imgtask.getId());	// 更新为正在转的报告
				List<ErpReportdetailImginfo> imgInfoList = DealFileConversionUtil.getInstance().dealPdf2Jpg(imgtask,jpgToPath,formatDate,mapCommonPages);
				dao.getHibernateTemplate().saveOrUpdateAll(imgInfoList);
				dao.getJdbcTemplate().update(updateStateSql,1,imgtask.getId());

				count++;
			}
		}
		return true;
	}
	
	private boolean existedReportStatus(String id) {
		// state = 4是手动正在转的状态
		String sql = "select count(1) from erp_reportdetail_imgtask where id=? and state not in(2, 3)";
		int count = dao.getJdbcTemplate().queryForInt(sql, id);
		return count == 0 ? false : true;
	}
	
	/**
	 * @param ids
	 * @author Carly
	 * @since 2017年4月12日15:00:00
	 * @return 拼接字符串
	 */
	private String assembleIds(String ids) {
		String [] idArr = ids.split(",");
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < idArr.length; i ++){
			result.append("'").append(idArr[i]).append("'").append(",");
		}
		return result.substring(0, result.length()-1);
	}
}
