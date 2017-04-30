package org.hpin.base.accessories.entity;

/**
 * <p>@desc : 附件Vo</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 29, 2012 6:15:02 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */

public class TawAttachment {

	/**
	 * 附件id
	 */
	private int attachmentId;
	
	/**
	 * 附件名称
	 */
	private String attachmentName;
	
	/**
	 * 附件原始文件名称
	 */
	private String attachmentFilename;
	
	/**
	 * 附件大小
	 */
	private int size;
	
	/**
	 * 创建人
	 */
	private String cruser;
	
	/**
	 * 创建时间
	 */
	private String crtime;
	
	/**
	 * 附件应用模块id
	 */
	private int appId;
	
	  public TawAttachment() {
	  }
	  public int getAppId() {
	    return appId;
	  }
	  public void setAppId(int appId) {
	    this.appId = appId;
	  }
	  public String getAttachmentFilename() {
	    return attachmentFilename;
	  }
	  public void setAttachmentFilename(String attachmentFilename) {
	    this.attachmentFilename = attachmentFilename;
	  }
	  public void setAttachmentId(int attachmentId) {
	    this.attachmentId = attachmentId;
	  }
	  public int getAttachmentId() {
	    return attachmentId;
	  }
	  public String getAttachmentName() {
	    return attachmentName;
	  }
	  public void setAttachmentName(String attachmentName) {
	    this.attachmentName = attachmentName;
	  }
	  public String getCrtime() {
	    return crtime;
	  }
	  public void setCrtime(String crtime) {
	    this.crtime = crtime;
	  }
	  public String getCruser() {
	    return cruser;
	  }
	  public void setCruser(String cruser) {
	    this.cruser = cruser;
	  }
	  public int getSize() {
	    return size;
	  }
	  public void setSize(int size) {
	    this.size = size;
	  }

}