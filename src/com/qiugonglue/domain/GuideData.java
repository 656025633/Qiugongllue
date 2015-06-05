package com.qiugonglue.domain;

import java.util.List;

/**
 * 当地导游数据
 * 
 * @author dell
 * 
 */
public class GuideData {

	public String msg;
	public String success;
	public Data data;

	public static class Data{
		public List<Recommand> item_recommend;
		
		public static class Recommand {
			public String id;
			public String lprice;
			public String mainImage;
			public String title;
			public String url;
		}
	}
	
	
}
