package org.hpin.reportdetail.entity;

import java.util.Date;

import org.hpin.common.core.orm.BaseEntity;

/**
 * 读取PDF文本内容匹配类
 * @author tangxing
 * @date 2017-3-8下午3:38:10
 */
public class ErpReadPDFMatchInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	/** 客户条码 */
	private String code;
	
	/** 客户姓名 */
	private String name;
	
	/** 客户性别 */
	private String sex;
	
	/** 客户年龄 */
	private String age;
	
	/** 该客户pdf文件路径 */
	private String pdfFilePath;
	
	/** pdf的页数 */
	private int pdfPages;
	
	/** 是否读取 */
	private String isReadPdf;
	
	/** 出现问号的页码 */
	private String errorPages;
	
	/**pdf错误状态  （默认为空；0--没有问题；  1--男：男性报告出现女性疾病；女：女性报告出现男性疾病；  2--第一页封面有问题；） */
	private String pdfWrongStatus;
	
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPdfFilePath() {
		return pdfFilePath;
	}

	public void setPdfFilePath(String pdfFilePath) {
		this.pdfFilePath = pdfFilePath;
	}

	public int getPdfPages() {
		return pdfPages;
	}

	public void setPdfPages(int pdfPages) {
		this.pdfPages = pdfPages;
	}

	public String getIsReadPdf() {
		return isReadPdf;
	}

	public void setIsReadPdf(String isReadPdf) {
		this.isReadPdf = isReadPdf;
	}

	public String getErrorPages() {
		return errorPages;
	}

	public void setErrorPages(String errorPages) {
		this.errorPages = errorPages;
	}

	public String getPdfWrongStatus() {
		return pdfWrongStatus;
	}

	public void setPdfWrongStatus(String pdfWrongStatus) {
		this.pdfWrongStatus = pdfWrongStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((errorPages == null) ? 0 : errorPages.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isReadPdf == null) ? 0 : isReadPdf.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((pdfFilePath == null) ? 0 : pdfFilePath.hashCode());
		result = prime * result + pdfPages;
		result = prime * result
				+ ((pdfWrongStatus == null) ? 0 : pdfWrongStatus.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErpReadPDFMatchInfo other = (ErpReadPDFMatchInfo) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (errorPages == null) {
			if (other.errorPages != null)
				return false;
		} else if (!errorPages.equals(other.errorPages))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isReadPdf == null) {
			if (other.isReadPdf != null)
				return false;
		} else if (!isReadPdf.equals(other.isReadPdf))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pdfFilePath == null) {
			if (other.pdfFilePath != null)
				return false;
		} else if (!pdfFilePath.equals(other.pdfFilePath))
			return false;
		if (pdfPages != other.pdfPages)
			return false;
		if (pdfWrongStatus == null) {
			if (other.pdfWrongStatus != null)
				return false;
		} else if (!pdfWrongStatus.equals(other.pdfWrongStatus))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErpReadPDFMatchInfo [id=" + id + ", code=" + code + ", name="
				+ name + ", sex=" + sex + ", age=" + age + ", pdfFilePath="
				+ pdfFilePath + ", pdfPages=" + pdfPages + ", isReadPdf="
				+ isReadPdf + ", errorPages=" + errorPages
				+ ", pdfWrongStatus=" + pdfWrongStatus + ", createTime="
				+ createTime + "]";
	}
}
