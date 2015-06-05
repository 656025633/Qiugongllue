package com.qiugonglue.utils;

import com.google.gson.Gson;

public class GsonTools {

	public static<T> T jsonToBean(String jsonResult,Class<T> clz){
		Gson gson = new Gson();
		T t = gson.fromJson(jsonResult, clz);
		return t;
	}
}
