package com.qiugonglue.domain;

import java.util.List;


public class ArmPlaceData {

	public int code;
	public String message;
	public PlaceData data;
	
	public static class PlaceData{
		public String place_id;
		public String place_name;
		public List<Command> cms_list;
		public Command_Pin recommend_pin_list;
		
		public static class Command{
			public String cover_image;
			public String ctime;
			public String desc;
			public String id;
			public String like_count;
			public String tags;
			public String title;
			public String url;
			public String view_count;
		}
		
		public static class Command_Pin{
			public List<Pin> entertainment;
			public List<Pin> restaurant;
			public List<Pin> sight;
			
			public static class Pin{
				public String cover_image;
				public String pin_id;
				public String title;
				public String url;
				public String want_count;
			}
		}
	}
}
