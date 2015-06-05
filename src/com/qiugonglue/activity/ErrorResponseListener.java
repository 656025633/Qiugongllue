package com.qiugonglue.activity;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public class ErrorResponseListener implements ErrorListener{

	private Context context;
	public ErrorResponseListener(Context context){
		this.context = context;
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(context, "请求数据错误", Toast.LENGTH_SHORT).show();
	}

}
