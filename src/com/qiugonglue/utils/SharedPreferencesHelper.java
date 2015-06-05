package com.qiugonglue.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper {

	private static SharedPreferencesHelper helper;
	private SharedPreferences prefs;
	private Editor editor;
	private String PREFS_FILE_NAME = "loginState";
	private String STATE = "state";
	//用户名
	private String SCREEN_NAME = "screen_name";
	//用户id
	private String USER_ID = "user_id";
	//城市
	private String CITY = "city";
	//我的评论条数
	private String COMMENT_COUNT = "comment_count";
	//个人描述
	private String DESC = "desc";
	//关注的人数
	private String FOLLOW_COUNT = "follow_count";
	//粉丝人数
	private String FANS_COUNT = "fans_count";
	//性别
	private String GENDER = "gender";
	//省份
	private String PROVINCE = "province";
	//去过的地点
	private String SUBLINE = "subline";
	//照片数量
	private String PHOTO_COUNT = "photo_count";
	//头像
	private String AVATAR = "avatar";
	//大头像
	private String AVATAR_BIG = "avatar_big";

	
	public static SharedPreferencesHelper getInstance(Context context){
		if(helper==null){
			helper = new SharedPreferencesHelper(context);
		}
		return helper;
	}
	
	public SharedPreferencesHelper(Context context) {
		super();
		this.prefs = context.getSharedPreferences(PREFS_FILE_NAME,
				Context.MODE_PRIVATE);
		this.editor = this.prefs.edit();
	}

	public boolean updateState(boolean state) {
		try {
			editor.putBoolean(STATE, state);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean getState() {
		boolean value = prefs.getBoolean(STATE, false);
		return value;
	}

	public String getScreenName() {
		return SCREEN_NAME;
	}

	public String getUserId() {
		return USER_ID;
	}

	public String getPrefsFileName() {
		return PREFS_FILE_NAME;
	}

	public String getCity() {
		return CITY;
	}

	public String getCommentCount() {
		return COMMENT_COUNT;
	}

	public String getDesc() {
		return DESC;
	}

	public String getFollowCount() {
		return FOLLOW_COUNT;
	}

	public String getFansCount() {
		return FANS_COUNT;
	}

	public String getGender() {
		return GENDER;
	}

	public String getProvince() {
		return PROVINCE;
	}

	public String getSubline() {
		return SUBLINE;
	}

	public String getPhotoCount() {
		return PHOTO_COUNT;
	}

	public String getAvatar() {
		return AVATAR;
	}

	public String getAvatarBig() {
		return AVATAR_BIG;
	}
}
