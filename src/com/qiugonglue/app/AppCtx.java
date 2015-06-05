package com.qiugonglue.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qiugonglue.utils.MySQLiteOpenHelper;
import com.qiugonglue.utils.SharedPreferencesHelper;

import android.app.Application;

public class AppCtx extends Application {
	private static AppCtx sAppCtx;
	private RequestQueue mRequestQueue;
	private MySQLiteOpenHelper sqlHelper;
	private SharedPreferencesHelper prefs;

	@Override
	public void onCreate() {
		super.onCreate();

		firstCreateInstance();
	}

	protected void firstCreateInstance() {
		if (sAppCtx == null)
			sAppCtx = this;
		getRequestQueue();
	}

	public static AppCtx getInstance() {
		return sAppCtx;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null)
			mRequestQueue = Volley.newRequestQueue(this);
		return mRequestQueue;
	}
	
	public SharedPreferencesHelper getPrefs(){
		if(prefs==null){
			prefs = new SharedPreferencesHelper(this);
		}
		return prefs;
	}
	
	public MySQLiteOpenHelper getSQLHelper(){
		if(sqlHelper==null){
			sqlHelper = new MySQLiteOpenHelper(this);
		}
		return sqlHelper;
	}
}
