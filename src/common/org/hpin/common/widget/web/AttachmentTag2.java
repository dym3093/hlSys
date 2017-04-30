package org.hpin.common.widget.web;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts2.views.jsp.TagUtils;
import org.hpin.base.accessories.entity.TawCommonsAccessoriesConfig;
import org.hpin.base.accessories.service.TawCommonsAccessoriesConfigService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.log.util.HpinLog;
/**
 * 
 * <p>Title:附件管理标签类
 * </p>
 * <p>Description:
 * </p>
 * <p>Apr 22, 2007 7:07:22 PM</p> 
 *
 * @author sherry
 * @version 1.0
 *
 */
public class AttachmentTag2 extends BodyTagSupport {
 
  /** 应用模块编号*/
  private String appCode= "";
  /**文件ID*/
  private String idList = "";
  /** 查看标志*/
  private String viewFlag = "";

  /** formBean名字*/
  private String name;
  /** 附件在formBean中的属性名*/
  private String property;
  /**查询的范围*/
  private String scope;
  
  private String attList;
  
  private String listIndex;
  
  private String path;
   
  private String attachmentIds;
  private String idField;
  
  private String alt;
  
  private String startsWith;
  /**
   * 为了找input的id而后加的
   */
  private String id;
  

public int doStartTag() {
    javax.servlet.ServletRequest request = pageContext.getRequest();
    path = ((javax.servlet.http.HttpServletRequest)request).getContextPath();
    //获取附件ID
    if (request.getParameter(this.idList) != null) {
       this.attachmentIds = request.getParameter(this.idList);
    }
    else {
       this.attachmentIds = "";
    }
    try {
      //根据给定的formbean、属性名得到附件ID值
    if(name !=null && property !=null && scope !=null){
     /*  this.attachmentIds=(String)TagUtils.getInstance().lookup(this.pageContext, 
    			            name, property, scope);*/
   
    	if( attList != null && listIndex !=null ){
    		property = property.replace(name+".", attList+"["+listIndex+"].");
    	}
    	
       this.attachmentIds=TagUtils.getStack(this.pageContext).findString(property);
    }
    if (startsWith == null || "".equals(startsWith)) {
		this.startsWith ="0";
	} else {
		this.startsWith = startsWith;
	}
    }
    catch (Exception e) {
      HpinLog.error(this, e.toString());
    }
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspTagException {

    try {
      String html = "";
      TawCommonsAccessoriesConfigService  tawCommonsAccessoriesConfigService = (TawCommonsAccessoriesConfigService)SpringTool.getBean(TawCommonsAccessoriesConfigService.class);
      TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig = tawCommonsAccessoriesConfigService
		.getAccessoriesConfigByAppcode(appCode);
/*	 if("L".equals(viewFlag)){
    	  if(attachmentIds !=null && !"ognl.NoConversionPossible".equals(attachmentIds)){
    		  html = "<iframe id=\"UIFrame1-" +idField+
		  		"\" name=\"UIFrame1-"+idField+"\" class=\"uploadframe\" frameborder=\"0\" "
		        + "scrolling=\"auto\" src=\"" + path + "/accessories/pages/uploadList.jsp?appId=" + appCode+"&type="+tawCommonsAccessoriesConfig.getAllowFileType()+
		        "&startsWith="
				+ startsWith
		        + "&filelist=" + attachmentIds + "&idField="+idField+"\" style=\"height:30%;width:100%\"></iframe><input type=\"hidden\" "
		        + "name=\"" + idField +"\" id=\""+idField+ "\" value=\""+attachmentIds+"\" />";    	  
    	  }
      } else */
      if (!viewFlag.equals("Y")) {
        html = "<iframe id=\"UIFrame1-" +idField+
        		"\" name=\"UIFrame1-"+idField+"\" class=\"uploadframe\" frameborder=\"0\" "
            + "scrolling=\"auto\" src=\"" + path + "/accessories/pages/upload.jsp?appId=" + appCode+"&type="+tawCommonsAccessoriesConfig.getAllowFileType()+
            "&startsWith="
			+ startsWith + "&attList=" + attList + "&listIndex=" + listIndex
            + "&filelist=" + attachmentIds + "&idField="+idField+"\" style=\"height:100%;width:100%\"></iframe><input type=\"hidden\" "
            + "name=\"" + id +"\" id=\""+id+ "\" value=\""+attachmentIds+"\"";
         if(alt!=null&&!alt.equals("")){
         	html=html+" alt=\"blankText:'请上传附件。',iframeId:'IFrame1-"+idField+"',"+alt+"\"/>";
         }
         else {
         	html=html+" />";
         }
      }else {
//        html = "<iframe id=\"VIFrame1-" +idField+
//        		"\" name=\"VIFrame1-"+idField+"\" class=\"uploadframe\" frameborder=\"0\" "
//            + "scrolling=\"auto\" src=\"" + path + "/accessories/pages/view.jsp?appId=" + appCode
//            + "&filelist=" + attachmentIds + "&idField="+idField+"\" style=\"height:100%;width:100%\"></iframe><input type=\"hidden\" "
//            + "name=\"" + idField +"\" id=\""+idField+ "\" value=\""+attachmentIds+"\"/>";
    	  if(attachmentIds !=null && !"ognl.NoConversionPossible".equals(attachmentIds)){
    		  html = "<iframe id=\"VIFrame1-" +idField+
		       "\" name=\"VIFrame1-"+idField+"\" frameborder=\"0\" "
               + " src=\"" + path + "/accessories/pages/view.jsp?appId=" + appCode+"&type="+tawCommonsAccessoriesConfig.getAllowFileType()
               + "&startsWith=" + startsWith + "&attList=" + attList + "&listIndex=" + listIndex
               + "&filelist=" + attachmentIds + "&idField="+idField+"\" scrolling=\"no\" style=\"height:100%;width:100%\"" +
               		" onload=\"this.height=window.document.body.scrollHeight+100;\"></iframe>";
    	  }
      }
      
      pageContext.getOut().write(html); 
    }
    catch (Exception e) {
    }
    return EVAL_PAGE;
  }

  public String getIdList() {
    return idList;
  }

  public void setIdList(String idList) {
    this.idList = idList;
  }

  public String getViewFlag() {
    return viewFlag;
  }

  public void setViewFlag(String viewFlag) {
    this.viewFlag = viewFlag;
  }

  public String getName() {
	    return name;
	  }

  public void setName(String name) {
	    this.name = name;
	  }

  public String getProperty() {
	    return property;
	  }

  public void setProperty(String property) {
	    this.property = property;
	  }

  public String getScope() {
	    return scope;
	  }

  public void setScope(String scope) {
	    this.scope = scope;
	  }

public String getAppCode() {
	return appCode;
}

public void setAppCode(String appCode) {
	this.appCode = appCode;
}

public String getIdField() {
	return idField;
}

public void setIdField(String idField) {
	this.idField = idField;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}
  
/**
 * @return Returns the alt.
 */
public String getAlt() {
	return alt;
}
/**
 * @param alt The alt to set.
 */
public void setAlt(String alt) {
	this.alt = alt;
}

public String getAttList() {
	return attList;
}

public void setAttList(String attList) {
	this.attList = attList;
}

public String getListIndex() {
	return listIndex;
}

public void setListIndex(String listIndex) {
	this.listIndex = listIndex;
}


}