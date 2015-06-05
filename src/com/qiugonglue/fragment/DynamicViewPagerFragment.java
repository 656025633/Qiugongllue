package com.qiugonglue.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.DynamicActivity;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.adapter.MyDynamicAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.DynamicData;
import com.qiugonglue.domain.DynamicData.Data;
import com.qiugonglue.pulltorefresh.PullToRefreshListView;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

import de.greenrobot.event.EventBus;

//find_new Fragment
public class DynamicViewPagerFragment extends BaseFragment {
	private int index;
	private PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private List<Data> totalList;
	private RequestQueue requestQueue;
	private MyDynamicAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		index = bundle.getInt("index");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dynamic_content,
				container, false);

		requestQueue = AppCtx.getInstance().getRequestQueue();
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.listView_dynamic_content);
		listView = pullToRefreshListView.getRefreshableView();
		totalList = new ArrayList<Data>();

		initData(savedInstanceState);
		
		//点击item向详细页面传递id
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//此id为点击的item 的条目的id
				Intent intent = new Intent();
				intent.setClass(getActivity(), DynamicActivity.class);
				
				Data data = totalList.get(position);
				EventBus.getDefault().postSticky(data);
				startActivity(intent);
			}
		});
		return view;
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

	//获取数据
	public void loadData(String response) {
		if (response == null) {
			return;
		}
		DynamicData dynamicData = GsonTools.jsonToBean(response,
				DynamicData.class);
		totalList = dynamicData.data;
		adapter = new MyDynamicAdapter(getActivity(), totalList);
		listView.setAdapter(adapter);
	}
	

	@Override
	public View initView(LayoutInflater inflater) {

		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		switch (index) {
		
		case 0: //求助
			getData(GLApi.DYNAMICBARTRAVEL);
			break;
		case 1:
			getData(GLApi.DYNAMICBARPRAY);
			break;
		case 2:
			getData(GLApi.DYNAMICBARTRAVEL);
			break;
		case 3:
			getData(GLApi.DYNAMICBARSHOP);
			break;
		case 4:
			getData(GLApi.DYNAMCIFOOD);
			break;
		case 5:
			getData(GLApi.DYNAMICBARTRAVEL);
			break;
		case 6:
			getData(GLApi.DYNAMICBARSHOP);
			break;
		case 7:
			getData(GLApi.DYNAMICBUY);
			break;
		default:
			break;
		}
	}
}
