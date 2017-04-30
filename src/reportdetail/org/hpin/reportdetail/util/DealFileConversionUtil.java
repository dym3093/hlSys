package org.hpin.reportdetail.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.hpin.common.core.SpringTool;
import org.hpin.reportdetail.entity.ErpReportdetailImginfo;
import org.hpin.reportdetail.entity.ErpReportdetailImgtask;
import org.hpin.reportdetail.service.ErpReportdetailImginfoService;

/**
 * @author Carly
 * @since 2017年2月9日18:11:56
 * 报告转换图片
 * 
 */
public class DealFileConversionUtil {

	private DealFileConversionUtil(){}
	
	private static DealFileConversionUtil pdfUtil = null;
	
	ErpReportdetailImginfoService imginfoService=(ErpReportdetailImginfoService)SpringTool.getBean(ErpReportdetailImginfoService.class);
	
	/**
	 * 线程安全单例模式
	 * @return DealWithPdfUtil
	 */
	public static synchronized DealFileConversionUtil getInstance(){
		if(pdfUtil == null){
			pdfUtil = new DealFileConversionUtil();
		}
		return pdfUtil;
	}
	
	/**
	 * @author Carly
	 * @since 2017年2月9日18:14:00
	 * @param imgtask	需要转换的报告,state=0
	 * @param filePath 	property里配置的jpg的路径
	 * @param formatDate 格式化之后的时间,用来生成文件夹
	 * @param mapCommonPages 公共页的map
	 * @return pdf转图片
	 */
	public List<ErpReportdetailImginfo> dealPdf2Jpg(ErpReportdetailImgtask imgtask,String filePath,
			String formatDate, Map<Integer, String> mapCommonPages)throws Exception {
		long start = System.currentTimeMillis();
		Logger logger = Logger.getLogger("pdf2jpg");
		List<ErpReportdetailImginfo> imgInfoList = new ArrayList<ErpReportdetailImginfo>();
		String jpgPath = "";
		PDDocument document = null;
		try {
			File loadFile = new File(imgtask.getFilePath());
			document = PDDocument.load(loadFile);
			PDFRenderer renderer = new PDFRenderer(document);
			int pages = document.getNumberOfPages();
			
			logger.info("开始转换图片,共有"+pages+"张图片---");
			for(int i=0 ; i < pages; i++){
				logger.info("开始转换第"+i+"张图片---");
				BufferedImage image = renderer.renderImageWithDPI(i, 130, ImageType.RGB);
				String pdfName = imgtask.getPdfName().substring(0, imgtask.getPdfName().lastIndexOf("."));
				File file = new File(filePath + formatDate + File.separator + pdfName );
				if(!file.exists()){
					file.mkdirs();
					
				}
				jpgPath = file.getAbsolutePath() + File.separator + imgtask.getCode() + "_" + i + ".jpg";
				logger.info("生成图片的位置:"+jpgPath);
				ImageIO.write(image, "png", new File(file.getAbsoluteFile()+File.separator+imgtask.getCode()+"_"+i+".jpg"));
				image.flush();
				logger.info("第"+i+"张图片转换结束---");
				ErpReportdetailImginfo imginfo = new ErpReportdetailImginfo();
				imginfo.setTaskId(imgtask.getId());
				imginfo.setImgPath(jpgPath);
				imginfo.setImgOrder(i);
				imginfo.setCreateTime(new Date());
				imginfo.setIsDeleted(0);
				switch (i) {
				case 0:
					break;

				default:
					imginfo.setImgOrder(i+2);
					break;
				}
				imgInfoList.add(imginfo);
				
			}
			
			logger.info("准备添加公共页---");
			for (int i = 1; i<=mapCommonPages.size(); i++ ) {	//添加公共页
				logger.info("开始添加公共页,添加第" + i + "张公共页---");
				ErpReportdetailImginfo imginfo = new ErpReportdetailImginfo();
				imginfo.setTaskId(imgtask.getId());
				imginfo.setImgPath(mapCommonPages.get(i));
				imginfo.setCreateTime(new Date());
				imginfo.setIsDeleted(0);
				switch (i) {
				case 1:
					imginfo.setImgOrder(1);	//添加第一页
					break;
				case 2:
					imginfo.setImgOrder(2);//添加第二页
					break;
				case 3:
					imginfo.setImgOrder(pages + 2);//添加第三页
					break;
				case 4:
					imginfo.setImgOrder(pages + 3);//添加第四页
					break;
				case 5:
					imginfo.setImgOrder(pages + 4);//添加第五页
					break;
				default:
					break;
				}
				imgInfoList.add(imginfo);
				logger.info("添加第" + i + "张公共页结束---");
			}
			
		} catch (Exception e) {
			logger.error("转换图片失败---", e);
			e.printStackTrace();
			throw e;
			
		} finally {
			if (null != document) {
				try {
					document.close();
					logger.info("转换图片结束,用时:"+(System.currentTimeMillis()-start)/1000+"s");
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("关闭document失败", e2);
					
				}
			}
		}
			
		return imgInfoList;
	}
	
	
	
}
