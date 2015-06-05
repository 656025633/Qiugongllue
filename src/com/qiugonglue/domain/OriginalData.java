package com.qiugonglue.domain;

import java.util.List;

/**
 * 发现新奇数据
 * 
 * @author dell
 * 
 */
public class OriginalData {

	public String code;
	public String message;

	public Origin data;
	
	public static class Origin{
		public List<Original> cms_list;
		
		public static class Original {
			public String like;
			public String link;
			public String src;
			public String tags;
			public String title;
		}
	}
}
