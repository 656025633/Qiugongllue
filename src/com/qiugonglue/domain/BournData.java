package com.qiugonglue.domain;

import java.util.List;

/**
 * 目的地数据
 * 
 * @author dell
 * 
 */
public class BournData {

	public String code;
	public String message;
	public Bourn data;

	public static class Bourn {

		public List<Recommand> recommend_list;

		public static class Recommand {

			public String label;
			public List<Board> list;

			public static class Board {
				public String board_id;
				public String name;
			}
		}
	}
}
