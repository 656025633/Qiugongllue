package com.qiugonglue.domain;

import java.util.List;


/**
 * 群聊数据
 * @author dell
 *
 */
public class GroupPlaceData {

	public String code;
	public String message;
	public List<GroupPlace> data;
	
	public static class GroupPlace{
		public String board_id;
		public String client_name;
		public String place_name;
	}
}
