package org.hpin.reportdetail.entity;

import org.hpin.events.entity.ErpCustomer;

public class ErpCusPdfViewBean extends ErpCustomer{

	private static final long serialVersionUID = 1L;
	
	private String matchstateView;

	public String getMatchstateView() {
		return matchstateView;
	}

	public void setMatchstateView(String matchstateView) {
		this.matchstateView = matchstateView;
	}
	
	
}
