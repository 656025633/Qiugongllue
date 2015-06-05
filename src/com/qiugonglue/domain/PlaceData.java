package com.qiugonglue.domain;

import java.util.List;

public class PlaceData {
	public String code;
	public String message;
	public List<Group> data;
	
	public static class Group{
		public String board_id;
		public String client_name;
		public String place_name;
	}
}
