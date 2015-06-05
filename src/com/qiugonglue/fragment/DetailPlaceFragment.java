package com.qiugonglue.fragment;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.adapter.GroupAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.GroupDetailData;
import com.qiugonglue.domain.GroupDetailData.Group;
import com.qiugonglue.pulltorefresh.PullToRefreshListView;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DetailPlaceFragment extends BaseFragment{

	private View view;
	private Bundle bundle;
	
	private PullToRefreshListView pullToRefresh;
	private ListView listView;
	private RequestQueue queue;
	private StringRequest stringRequest;
	
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_detailplace, null);
		pullToRefresh = (PullToRefreshListView) view.findViewById(R.id.pullToRefresh);
		listView = pullToRefresh.getRefreshableView();
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		bundle = getArguments();
		String placeName = bundle.getString("placeName");
		queue = AppCtx.getInstance().getRequestQueue();
		stringRequest = new StringRequest(
				GLApi.CHATBRIEF,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						GroupDetailData detailData = GsonTools.jsonToBean(response, GroupDetailData.class);
						loadGroupData(detailData);
					}
				}, 
				new ErrorResponseListener(getActivity())
		);
		queue.add(stringRequest);
	}

	protected void loadGroupData(GroupDetailData detailData) {
		if(detailData==null){
			throw new IllegalArgumentException(" DetailData is null ");
		}
		List<Group> group_list = detailData.data.group_list;
		
		GroupAdapter adapter = new GroupAdapter(getActivity(), group_list);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				//处理点击事件
			}
		});
	}
}
