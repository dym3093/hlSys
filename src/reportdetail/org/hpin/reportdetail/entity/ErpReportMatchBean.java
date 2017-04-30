package org.hpin.reportdetail.entity;

/**
 * 用于PDF内容比对会员信息实体类
 * @author ybc
 * @date 2016-04-05
 */
public class ErpReportMatchBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;

	private String code;//条形码
	
	private String name;//姓名
	
	private String combo;//套餐
	
	private String sex;//性别
	
	private String age;//年龄
	
	private String fileSize;//文件大小
	
	private String filePath;//文件路径
	
	private String pdfBthNo;//报告批次
	
	private String printState;//打印状态
	
	private Integer reportType;//报告类型 0:基因；3：无创
	
	private String pdfName;	//文件名
	
	private Integer convertionFileType;	//需要找对应套餐的报告
	
	private Boolean matchState;	//匹配状态
	
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPdfBthNo() {
		return pdfBthNo;
	}

	public void setPdfBthNo(String pdfBthNo) {
		this.pdfBthNo = pdfBthNo;
	}

	public String getPrintState() {
		return printState;
	}

	public void setPrintState(String printState) {
		this.printState = printState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCombo() {
		return combo;
	}

	public void setCombo(String combo) {
		this.combo = combo;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public Integer getConvertionFileType() {
		return convertionFileType;
	}

	public void setConvertionFileType(Integer convertionFileType) {
		this.convertionFileType = convertionFileType;
	}

	public Boolean getMatchState() {
		return matchState;
	}

	public void setMatchState(Boolean matchState) {
		this.matchState = matchState;
	}

}
