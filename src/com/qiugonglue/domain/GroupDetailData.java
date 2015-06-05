package com.qiugonglue.domain;

import java.util.List;

import com.qiugonglue.domain.TagData.Tag;

/**
 * 某个地区的群组列表详细信息
 * @author dell
 *
 */
public class GroupDetailData {

	public String code;
	public String message;
	public GroupData data;
	
	public static class GroupData{
		public List<Group> group_list;
	}
	public static class Group{
		public String apply_required;
		public String client_name;
		public String group_desc;
		public String group_id;
		public String group_name;
		public String has_join;
		public String icon;
		public String is_local;
		public String is_official;
		public String is_recommend;
		public String limit;
		public String member_count;
		public String place_name;
		
		
		public List<Tag>tags;
		
		public String travel_time;
		public String travel_time_required;
		
		public List<User> users;
		public Owner owner;
		public List<UserTag> user_tags;
		
		
		public static class User{
			public String avatar;
			public String user_id;
		}
		
		public static class Owner{
			public String avatar;
			public String desc;
			public String gender;
			public String screen_name;
			public String user_id;
			public String user_name;
		}
		
		public static class UserTag{
			public String bg_color;
			public String tag_name;
		}
	}
}