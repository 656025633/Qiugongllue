package com.qiugonglue.domain;

import java.util.List;

/**
 * 导航数据
 * 
 * @author dell
 * 
 */
public class TagData {

	public String code;
	public String message;
	public List<Tag> data;

	public static class Tag {
		public String tag_id;
		public String tag_name;
	}
}
