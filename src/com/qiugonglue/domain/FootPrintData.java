package com.qiugonglue.domain;

import java.util.List;



public class FootPrintData {
	public String code;
	public String message;
	public Data data;
	
	public static class Data{
		public List<BeenList> been_list;
		
		public static class BeenList{
			public String ol_cover_image;
			public String p_area;
			public String pin_id;
			public String poi_id;
			public String sub_line;
			public String title;
		}
	}
}
