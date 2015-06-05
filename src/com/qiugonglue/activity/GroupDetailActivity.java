package com.qiugonglue.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseActivity;
import com.qiugonglue.utils.GLApi;

/**
 * 群组详细页面
 * 
 * @author dell
 * 
 */
public class GroupDetailActivity extends BaseActivity implements OnClickListener {

	// 返回按钮
	private Button back_detail;

	// 群组名称
	private TextView text_name_place;

	// 群组号码
	private TextView text_id_place;

	// 群成员
	private TextView member_group;

	// 群成员头像布局
	private RelativeLayout member_layout;
	//头像容器
	private LinearLayout icon_layout;

	// 邀请新成员
	private RelativeLayout invite_layout;

	// 加入群组
	private Button btn_enter;
	
	private RequestQueue queue;
	private StringRequest groupRequest;

	@Override
	public void initView() {
		setContentView(R.layout.activity_groupdetail);
		findView();
	}
	
	@Override
	public void initData() {
		Bundle bundle = getIntent().getExtras();
		String group_id = bundle.getString("group_id");
		
		queue = AppCtx.getInstance().getRequestQueue();
		groupRequest = new StringRequest(
				GLApi.GROUP + group_id, 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//处理群组数据
						
					}
				}, 
				new ErrorResponseListener(this)
		);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_detail:
			finish();//结束
			break;
		case R.id.member_layout:
			break;
		case R.id.invite_layout:
			break;
		case R.id.btn_enter:
			break;
		default:
			break;
		}
	}

	@Override
	public void addListener() {
		back_detail.setOnClickListener(this);
		member_layout.setOnClickListener(this);
		invite_layout.setOnClickListener(this);
		btn_enter.setOnClickListener(this);
	}

	private void findView() {
		//返回
		back_detail = (Button) findViewById(R.id.back_detail);

		//群名,群号,群成员数目
		text_name_place = (TextView) findViewById(R.id.text_name_place);
		text_id_place = (TextView) findViewById(R.id.text_id_place);
		member_group = (TextView) findViewById(R.id.member_group);
		
		//会员头像布局
		member_layout = (RelativeLayout) findViewById(R.id.member_layout);
		//头像容器
		icon_layout = (LinearLayout) findViewById(R.id.icon_layout);
		
		//邀请新成员
		invite_layout = (RelativeLayout) findViewById(R.id.invite_layout);
		//加入群组
		btn_enter = (Button) findViewById(R.id.btn_enter);
	}
}
