package com.qiugonglue.domain;

import java.util.List;


/**
 * 动态数据
 * @author dell
 *
 */
public class DynamicData {

	public String code;
	public String message;
	public List<Data> data;
	
	public static class Data{
		public String action_user_count;
		public String board_id;
		public String client_name;
		public String comment_count;
		public String content;
		public String cover_image;
		public String cover_image_height;
		public String cover_image_width;
		public String ctime;
		public String fav_count;
		public String group_id;
		public String human_ctime;
		public String id;
		public String image_count;
		public String is_fav;
		public String is_reported;
		public String mtime;
		public String object;
		public String object_id;
		public String place_id;
		public String source;
		public String status;
		public String tag_id;
		public String tag_name;
		public String uuid;
		public String visible;
		
		public List<ActionUser> action_user_list;
		public Author author;
		public List<Comment> comment_list;
		public List<Image> image_list;
		public List<Tag> tags;
		
		public static class ActionUser{
			public String action_type;
			public String avatar;
			public String id;
			public String is_cli;
			public String mtime;
			public String status;
			public String trend_id;
			public String user_avatar;
			public String user_id;
			public String user_name;
		}
		
		public static class Author{
			public String avatar;
			public String user_id;
			public String user_name;
		}
		
		public static class Comment{
			public String board_id;
			public String comment_content;
			public String comment_id;
			public String comment_object;
			public String comment_object_id;
			public String comment_object_type;
			public String ctime;
			
			public String mtime;
			public String reply_to_user_id;
			public String reply_to_user_name;
			public String reply_to_user_name_cn;
			public String source;
			public String source_client;
			public String status;
			public String user_id;
			public String user_name;
		}
		
		public static class Image{
			public String image_height;
			public String image_thumbnail_url;
			public String image_url;
			public String image_width;
		}
		
		public static class Tag{
			public String tag_id;
			public String tag_name;
		}
	}
}
