package com.qiugonglue.domain;


/**
 * 查看其它联系人 签名、昵称、粉丝等数据
 * 
 * @author MaDianYong
 * 
 */
public class OtherPersonData {
	public String code;
	public String message;
	public Data data;

	public static class Data{
		public String order_list_url;
		public User user;
	
		public static class User {
			public String avatar;
			public String avatar_big;
			public String been_count;
			public String city;
			public String comment_count;
			public String desc;
			public String fans_count;
			public String follow_count;
			public String gender;
			public String location;
			public String photo_count;
			public String province;
			public String screen_name;
			public String subline;
			public String trends_count;
			public String user_id;
		}
	}
}
