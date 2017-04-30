/**
 * 
 */
package org.hpin.reportdetail.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* @author Carly
* @since 2016年9月8日11:00:10
*/
public class ErpPrintTaskBean extends BaseEntity{

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -5967023453226776115L;
	private String id;
    private String printTaskNo; 		//打印任务号
    private String printCompany;		//打印公司
    private String address;				//报告寄送地址
    private String expressRecipient;	//快递签收人
    private String telephone;			//接收人电话
    private String expressNo;			//快递单号
    private Date expectTime;			//预计时间
    private Date createTime;  			//创建时间
    private String printState;			//打印状态
    private String downLoadPath;		//下载路径
    private String downloadStatus = "0";		//下载状态;'0'未下载 '1'已下载; 默认为0 create by henry.xu 20161024
    
    public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public ErpPrintTaskBean() {
    }
    
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(Date expectTime) {
		this.expectTime = expectTime;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressRecipient() {
		return expressRecipient;
	}

	public void setExpressRecipient(String expressRecipient) {
		this.expressRecipient = expressRecipient;
	}

	public String getPrintCompany() {
		return printCompany;
	}

	public void setPrintCompany(String printCompany) {
		this.printCompany = printCompany;
	}
	

	public String getPrintState() {
		return printState;
	}

	public void setPrintState(String printState) {
		this.printState = printState;
	}

	public String getDownLoadPath() {
		return downLoadPath;
	}

	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}

	/**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the printTaskNo
     */
    public String getPrintTaskNo() {
        return printTaskNo;
    }
    /**
     * @param printTaskNo the printTaskNo to set
     */
    public void setPrintTaskNo(String printTaskNo) {
        this.printTaskNo = printTaskNo;
    }
    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "ErpPrintTask [id=" + id + ", printTaskNo=" + printTaskNo
				+ ", createTime=" + createTime +  ", downLoadPath=" + downLoadPath
				+ ", printCompany=" + printCompany + ", printState=" + printState
				+ ", expectTime=" + expectTime + ", expressNo=" + expressNo
				+ ", expressRecipientTime=" + expressRecipient
				+ ", createUsername="+ ", updateTime=" + updateTime+"]";
	}
    
}
