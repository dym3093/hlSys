package org.hpin.security.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;
import org.hpin.base.usermanager.entity.User;

/**
 * 安全Tag
 * 
 * @author thinkpad
 * 
 */
public class SecurityTag extends TagSupport {
	private String tag = null;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("currentUser");
		boolean hasPass = hasSecurity(user, tag);
		if (hasPass) {
			return EVAL_BODY_INCLUDE;
		}
		tag = null;
		return SKIP_BODY;
	}

	public static boolean hasSecurity(User user, String identifier) {
		Integer flag = user.getRoleCodes().indexOf(identifier)  ;
		Integer flagSec = user.getResources().indexOf(identifier)  ;
		if("admin".equals(user.getAccountName())){ //admin加入标签权限
			return true ;
		}
		else if(flag < 0 && flagSec < 0){
			return false ;
		}else {
			return true ;
		}
		
	}
}

