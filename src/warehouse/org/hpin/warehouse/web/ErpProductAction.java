package org.hpin.warehouse.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpProduct;
import org.hpin.warehouse.service.ErpProductService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 产品信息Action create by henry.xu 20161008
 */
@Namespace("/warehouse")
@Action("product")
@Results({
	@Result(name = "list", location = "/WEB-INF/warehouse/product/listErpProduct.jsp"),
	@Result(name = "edit", location = "/WEB-INF/warehouse/product/editErpProduct.jsp"),
	@Result(name = "view", location = "/WEB-INF/warehouse/product/viewErpProduct.jsp"),
	@Result(name = "imageJsp", location = "/WEB-INF/warehouse/product/showImagePdialog.jsp"),
	@Result(name = "showImage", type="stream", params={"contentType", "image/png", "inputName","inputStream", "bufferSize", "4096"}),
	@Result(name = "query", location = "/WEB-INF/warehouse/product/queryErpProduct.jsp")	
})
public class ErpProductAction extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2906610141786477589L;
	private static final Logger log = Logger.getLogger(ErpProductAction.class);

	@Autowired
	private ErpProductService erpProductService; // 产品信息service

	private ErpProduct product; // 产品信息实体类
	
	private String productId; //产品信息ID
	
	private HashMap<String, String> params; //查询参数处理;
	
	private static final int BUFFER_SIZE = 16 * 1024;  
    // 封装上传文件域的属性  
    private File upload;
    // 封装上传文件类型的属性  
    private String uploadContentType;
    // 封装上传文件名的属性  
    private String uploadFileName;
    
    private String storageFileName;  
    
    private InputStream inputStream;
    
    public String imageJsp() {
    	HttpTool.setAttribute("numMath", new Date().getTime()+"");
    	return "imageJsp";
    }
    
    /**
     * 图片查看
     */
    public String showImage() {
    	if(StringUtils.isNotEmpty(id)) {
    		//查找图片路径;
    		product = this.erpProductService.findErpProductById(id);
    		
    		if(StringUtils.isEmpty(product.getImagePath())){
    			return null;
    		}
    		
    		//路径拼接;
    		String url = product.getImagePath();
    		String fileRealPath = null;
    		int pathCount = url.lastIndexOf("\\");
    		if(pathCount < 0) {
    			fileRealPath = url;
    		} else {
    			fileRealPath = ServletActionContext.getServletContext().getRealPath("/uploadFile/images/product/") + File.separator + url.substring(pathCount+1, url.length());
    			
    		}
    		log.info("文件路劲: " + fileRealPath);
    		
    		//创建file对象;
    		File file = new File(fileRealPath);
    		
    		//返回
    		try {
    			inputStream = new FileInputStream(file);    				
			} catch (FileNotFoundException e) {
				log.error("图片没有发现", e);
			}
    	}
    	
        return "showImage";
    }
    
    /**
     * 图片上传 20161101
     */
    public void uploadFile() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String filePath = "";
		
		String isUpload = HttpTool.getParameter("isUpload", "");
		
		try {
			/*
			 * isUpload为1时表示要进行文件处理; 否则没有变动;
			 * */
			if("1".equals(isUpload)) {
				//文件处理;更新名字;
				storageFileName = new Date().getTime() + getExtention(uploadFileName);
				filePath = ServletActionContext.getServletContext().getRealPath("/uploadFile/images/product/") + File.separator + storageFileName;
				
				File storageFile = new File(filePath); 
				copy(upload, storageFile);
				
				product.setImagePath(filePath);
				
			}
			
			User userInfo = (User)HttpTool.getSession().getAttribute("currentUser");
			if(product != null) {
				if(StringUtils.isEmpty(product.getId())) {
					erpProductService.saveProduct(product, userInfo);
				} else {
					erpProductService.updateProduct(product, userInfo, isUpload);
				}
			}
		
		} catch(Exception e) {
			statusCode = "300";
			log.error("文件上传错误!", e);
		}
		
		json.put("path", filePath);
		json.put("statusCode", statusCode);
		renderJson(json);
	}
    
    public static String getExtention(String fileName) {  
        int pos = fileName.lastIndexOf(".");  
        return fileName.substring(pos);  
    }  

	public void copy(File src, File dst) {  
        try {  
            InputStream in = null;  
            OutputStream out = null;  
            try {  
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);  
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                while (in.read(buffer) > 0) {
                    out.write(buffer);  
                }  
                //试着删除数据;
                src.delete();
            } finally {  
                if (null != in) {  
                    in.close();  
                }  
                if (null != out) {  
                    out.close();  
                }  
            }  
        } catch (Exception e) {  
            log.error("产品文件copy错误!", e);
        }  
    } 
	
	public void dealIsClose() {
		JSONObject json = new JSONObject();
		String status = HttpTool.getParameter("status", "");
		boolean flag = true;
		try {
			this.erpProductService.dealIsClose(id, status) ;
			flag = true;
		} catch(Exception e) {
			flag = true;
			log.error("产品启用关闭错误!", e);
		}
		json.put("result", flag);
		renderJson(json);
	}
	
	/**
	 * 验证是否可以删除产品
	 */
	public void validDel() {
		JSONObject json = new JSONObject();
		String ids = HttpTool.getParameter("ids", "");
		//查询是否存在;排除在该id中;
		json.put("result", this.erpProductService.validDel(ids));
		
		renderJson(json);
	}
	
	/**
	 * 数据删除;
	 */
	public void delete() {
		JSONObject json = new JSONObject();
		String ids = HttpTool.getParameter("ids", "");
		//查询是否存在;排除在该id中;
		json.put("result", this.erpProductService.deleteProductById(ids));
		
		renderJson(json);
	}
	
	/**
	 * ajax验证产品名称是否存在;
	 */
	public void validProductNameIsExits() {
		JSONObject json = new JSONObject();
		String productName = HttpTool.getParameter("productName", "");
		//查询是否存在;排除在该id中;
		json.put("result", this.erpProductService.validProductNameIsExits(productId, productName));
		
		renderJson(json);
	}

	/**
	 * 保存修改;
	 */
	public void saveOrUpdate() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String callbackType= "closeCurrent";
		try {
			User userInfo = (User)HttpTool.getSession().getAttribute("currentUser");
			if(product != null) {
				if(StringUtils.isEmpty(product.getId())) {
					erpProductService.saveProduct(product, userInfo);
				} else {
					erpProductService.updateProduct(product, userInfo, null);
				}
			}
			
		} catch (Exception e) {
			statusCode = "300";
			log.error("产品信息保存错误!ErpProductAction.saveOrUpdate", e);
		}
		json.put("statusCode", statusCode);
		json.put("navTabId", navTabId);
		json.put("callbackType", callbackType);
		renderJson(json);
	}

	/**
	 * 查看列表显示页;
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list() {
		try {
			page = 	new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			this.erpProductService.findErpProductByPage(page, params);
		} catch (ParseException e) {
			log.error("查询PageNum/pagesize错误!ErpProductAction.list", e);
		}
		
		return "list";
	}

	/**
	 * 编辑显示页;
	 * 
	 * @return
	 */
	public String edit() {
		
		if(StringUtils.isNotEmpty(productId)) {
			product = this.erpProductService.findErpProductById(productId);
		}
		
		return "edit";
	}

	/**
	 * 详细显示页;
	 * 
	 * @return
	 */
	public String view() {
		product = this.erpProductService.findErpProductById(productId);
		HttpTool.setAttribute("numMath", new Date().getTime()+"");
		return "view";
	}

	/**
	 * 放大镜查询列表显示页;
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String query() {
		try {
			page = 	new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			this.erpProductService.findErpProductByPage(page, params);
		} catch (ParseException e) {
			log.error("查询PageNum/pagesize错误!ErpProductAction.list", e);
		}
		return "query";
	}

	/* get/set方法 */
	public ErpProduct getProduct() {
		return product;
	}

	public void setProduct(ErpProduct product) {
		this.product = product;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getStorageFileName() {
		return storageFileName;
	}

	public void setStorageFileName(String storageFileName) {
		this.storageFileName = storageFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
}
