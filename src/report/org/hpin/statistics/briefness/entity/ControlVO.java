package org.hpin.statistics.briefness.entity;

import org.hpin.common.core.orm.BaseEntity;

public class ControlVO  extends BaseEntity {
	 private String s1;
		private int f1;
		private int f2;
		public String getS1() {
			return s1;
		}
		public void setS1(String s1) {
			this.s1 = s1;
		}
		public int getF1() {
			return f1;
		}
		public void setF1(int f1) {
			this.f1 = f1;
		}
		public int getF2() {
			return f2;
		}
		public void setF2(int f2) {
			this.f2 = f2;
		}
		
}
