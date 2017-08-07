package com.lyl.base.util;

public class SystemEnum {
	

	/**
	 * 用户性别
	 */
	public enum USER_GENDER {
		m("男"), f("女"), u("未知");
		String	text;

		private USER_GENDER(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}
	
	public enum OrderDir {
		asc, desc
	}
	
	
	public enum CODE_TYPE{
		USER_CODE("用户代码"),USER_CODE1("用户代码"),USER_CODE2("用户代码"),USER_CODE3("用户代码");
		private String text;
		CODE_TYPE(String text){
			this.text=text;
		}
		public String getText() {
			return text;
		}
	}

	

	

}