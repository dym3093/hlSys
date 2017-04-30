package org.hpin.base.accessories.entity;

import java.util.List;

/**
 * <p>@desc : 附件信息Vo</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 29, 2012 6:14:24 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
public class HpinAccessInfBZ {

	
	//图片类型
	private int phototype;
	//图片路径
	private List listphotopath;
	
	//图片路径
	private String strphotopath;
	
	//图片分类
	private String photoalagn;
	
	//图片编号
	private String photono;
	
	public int getPhototype() {
		return phototype;
	}

	public void setPhototype(int phototype) {
		this.phototype = phototype;
	}

	public List getListphotopath() {
		return listphotopath;
	}

	public void setListphotopath(List listphotopath) {
		this.listphotopath = listphotopath;
	}

	public String getStrphotopath() {
		return strphotopath;
	}

	public void setStrphotopath(String strphotopath) {
		this.strphotopath = strphotopath;
	}

	public String getPhotoalagn() {
		return photoalagn;
	}

	public void setPhotoalagn(String photoalagn) {
		this.photoalagn = photoalagn;
	}

	public String getPhotono() {
		return photono;
	}

	public void setPhotono(String photono) {
		this.photono = photono;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((listphotopath == null) ? 0 : listphotopath.hashCode());
		result = prime * result
				+ ((photoalagn == null) ? 0 : photoalagn.hashCode());
		result = prime * result + ((photono == null) ? 0 : photono.hashCode());
		
		result = prime * result
				+ ((strphotopath == null) ? 0 : strphotopath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (getClass() != obj.getClass())
			return false;
		HpinAccessInfBZ other = (HpinAccessInfBZ) obj;
		if (listphotopath == null) {
			if (other.listphotopath != null)
				return false;
		} else if (!listphotopath.equals(other.listphotopath))
			return false;
		if (photoalagn == null) {
			if (other.photoalagn != null)
				return false;
		} else if (!photoalagn.equals(other.photoalagn))
			return false;
		if (photono == null) {
			if (other.photono != null)
				return false;
		} else if (!photono.equals(other.photono))
			return false;
		
		if (strphotopath == null) {
			if (other.strphotopath != null)
				return false;
		} else if (!strphotopath.equals(other.strphotopath))
			return false;
		return true;
	}

	
	
}
