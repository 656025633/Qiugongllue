package com.qiugonglue.activity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseActivity;
import com.qiugonglue.utils.GLApi;


/**
 * 我的评论
 * @author dell
 *
 */
public class MineCommentActivity extends BaseActivity {

	private RequestQueue queue;
	private StringRequest stringRequest;
	@Override
	public void initView() {
		setContentView(R.layout.activity_mine_comment);
		
	}
	
	@Override
	public void addListener() {
		
	}

	@Override
	public void initData() {
		queue = AppCtx.getInstance().getRequestQueue();
		stringRequest = new StringRequest(
				GLApi.MINECOMMENTS, 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//处理数据
						
					}
				}, 
				new ErrorResponseListener(this)
		);
		
		queue.add(stringRequest);
	}
	
	
}
