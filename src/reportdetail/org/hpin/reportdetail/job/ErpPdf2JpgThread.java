package org.hpin.reportdetail.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.daoWrapper.DaoSupport;
import org.hpin.reportdetail.entity.ErpReportdetailImginfo;
import org.hpin.reportdetail.entity.ErpReportdetailImgtask;
import org.hpin.reportdetail.util.DealFileConversionUtil;



/**
 * @author Carly
 * @since 2017年2月8日12:18:29
 * pdf转jpg
 */
@SuppressWarnings("unchecked")
public class ErpPdf2JpgThread implements Callable<Map<String, String>> {

	private String jpgToPath;
	private Map<Integer, String> mapCommonPages;
	private DaoSupport dao = null;
	
	public ErpPdf2JpgThread(String jpgToPath, Map<Integer, String> mapCommonPages) {
		this.jpgToPath = jpgToPath;
		this.mapCommonPages = mapCommonPages;
		if (null == dao) {
			dao = (DaoSupport) SpringTool.getBean("DaoSupport");
		}
	}
	
	
	@Override
	public Map<String, String> call() {
		long start = System.currentTimeMillis();
		Logger logger = Logger.getLogger("pdf2jpg");
		logger.info("ErpPdf2JpgThread run start time");
		Map<String, String> result = new HashMap<String, String>();
		Date date = new Date();
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		
		String filePathSql = "from ErpReportdetailImgtask where state=0 and isDeleted=0";	//获取未转换的报告
		String updateStateSql = "update erp_reportdetail_imgtask set state=? where id=?";
		String updateStateSql2 = "update erp_reportdetail_imgtask set state=? where state=3";
		dao.getJdbcTemplate().update(updateStateSql2,0);	//防止重启后状态未变的
		
		List<ErpReportdetailImgtask> imgtaskList = dao.getHibernateTemplate().find(filePathSql);
		int count = 1;
		int size = imgtaskList.size();
		for(ErpReportdetailImgtask imgtask:imgtaskList){
			try {
				logger.info("共有" + size + "个报告,开始转换第" + count + "个报告,剩余" + (size-count) + "个报告");
				if (existedReportStatus(imgtask.getId())) {
					dao.getJdbcTemplate().update(updateStateSql,3,imgtask.getId());	// 更新为正在转的报告
					List<ErpReportdetailImginfo> imgInfoList = DealFileConversionUtil.getInstance().dealPdf2Jpg(imgtask,jpgToPath,formatDate,mapCommonPages);
					dao.getHibernateTemplate().saveOrUpdateAll(imgInfoList);
					dao.getJdbcTemplate().update(updateStateSql,1,imgtask.getId());
					result.put("code", imgtask.getCode());
					
				}
				
				count++;
			} catch (Exception e) {
				logger.error("ErpPdf2JpgThread run error",e);
				result.put("exception", e.toString());
				dao.getJdbcTemplate().update(updateStateSql,2,imgtask.getId());
				continue;
			}
		}
		logger.info("ErpPdf2JpgThread run end  use time : " + (System.currentTimeMillis() - start));
		return result;
	}
	
	private boolean existedReportStatus(String id) {
		// state = 4是手动正在转的状态
		String sql = "select count(1) from erp_reportdetail_imgtask where id=? and state=0";
		int count = dao.getJdbcTemplate().queryForInt(sql, id);
		return count == 0 ? false : true;
	}
	
}
