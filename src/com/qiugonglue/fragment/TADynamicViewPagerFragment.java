package com.qiugonglue.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.adapter.MyDynamicAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.DynamicData;
import com.qiugonglue.domain.DynamicData.Data;
import com.qiugonglue.pulltorefresh.PullToRefreshListView;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

/**
 * 他的动态碎片
 * 
 * @author dell
 * 
 */
public class TADynamicViewPagerFragment extends BaseFragment {
	private int index;
	private PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private List<Data> totalList;
	private RequestQueue requestQueue;
	private MyDynamicAdapter adapter;

	@Override
	public View initView(LayoutInflater inflater) {
		Bundle bundle = getArguments();
		index = bundle.getInt("index");
		view = inflater.inflate(R.layout.fragment_dynamic_content, null);

		requestQueue = AppCtx.getInstance().getRequestQueue();
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.listView_dynamic_content);
		listView = pullToRefreshListView.getRefreshableView();
		totalList = new ArrayList<Data>();

		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		getData(GLApi.TAGYNAMIC);
	}

	// 得到内容
	public void getData(String URL) {
		StringRequest request = new StringRequest(URL, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				loadData(response);
			}
		}, new ErrorResponseListener(getActivity()));
		requestQueue.add(request);
	}

	// 获取数据
	public void loadData(String response) {
		if (response == null) {
			return;
		}
		DynamicData dynamicData = GsonTools.jsonToBean(response,
				DynamicData.class);
		totalList = dynamicData.data;

		if (index == 1) {
			adapter = new MyDynamicAdapter(getActivity(), totalList);
		} else if (index == 0) {
			// 反转list集合中的内容
			Collections.reverse(totalList);
			adapter = new MyDynamicAdapter(getActivity(), totalList);
		}
		listView.setAdapter(adapter);
	}
}