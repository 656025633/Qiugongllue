package com.qiugonglue.domain;

import java.util.List;

public class BannerBeen {

	public int code;
	public String message;
	public BannerData data;
	
	public static class BannerData{
		public ADBanner ad_banner;
		
		public List<Banner> banner_list;
		
		public static class ADBanner{
			public String actionURL;
			public String description;
			public String imageURL;
		}
		
		public static class Banner{
			public String actionURL;
			public String description;
			public String imageURL;
		}
		
	}
	
}
