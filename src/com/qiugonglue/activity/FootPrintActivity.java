package com.qiugonglue.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.adapter.MyFootPrintAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.domain.FootPrintData;
import com.qiugonglue.domain.FootPrintData.Data.BeenList;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class FootPrintActivity extends Activity {
	private List<BeenList> list;
	private RequestQueue requestQueue;
	private ListView listView_foodprint_showInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foot_print);

		init();

	}

	public void init() {
		requestQueue = AppCtx.getInstance().getRequestQueue();
		listView_foodprint_showInfo = (ListView) findViewById(R.id.listView_foodprint_showInfo);
		list = new ArrayList<BeenList>();

		getData(GLApi.TAFOOT);
	}

	// 得到内容
	public void getData(String URL) {
		StringRequest request = new StringRequest(URL, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				loadData(response);

			}
		}, new ErrorResponseListener(this));
		requestQueue.add(request);
	}

	// 获取数据
	public void loadData(String response) {
		if (response == null) {
			return;
		}
		FootPrintData footPrintData = GsonTools.jsonToBean(response,
				FootPrintData.class);
		list = footPrintData.data.been_list;
		MyFootPrintAdapter adapter = new MyFootPrintAdapter(this, list);
		listView_foodprint_showInfo.setAdapter(adapter);
	}
}
