package com.qiugonglue.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.GroupDetailData;
import com.qiugonglue.domain.GroupDetailData.Group;
import com.qiugonglue.pulltorefresh.PullToRefreshListView;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

public class MessageFragment extends BaseFragment {

	private View view;

	private PullToRefreshListView pullToRefresh;
	private RequestQueue queue;
	private StringRequest followgroupRequest;
	private StringRequest messageRequest;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_message, null);
		pullToRefresh = (PullToRefreshListView) view.findViewById(R.id.pullToRefresh);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		queue = AppCtx.getInstance().getRequestQueue();
		//请求用户加入的群组
		followgroupRequest = new StringRequest(
				GLApi.FLLOWGROUP, 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 获取加入的群组信息 然后请求信息
						GroupDetailData detailData = GsonTools.jsonToBean(response, GroupDetailData.class);
						loadGroupData(detailData);
					}
				}, new ErrorResponseListener(getActivity()));
		queue.add(followgroupRequest);
	}

	protected void loadGroupData(GroupDetailData detailData) {
		if(detailData==null)throw new IllegalArgumentException(" GroupDetailData is null ");
		List<Group> group_list = detailData.data.group_list;
		for (int i = 0; i < group_list.size(); i++) {
			messageRequest = new StringRequest(
					GLApi.MESSAGE + group_list.get(i).group_id, 
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							// 显示群组的信息
							
						}
					}, 
					new ErrorResponseListener(getActivity())
			);
			queue.add(messageRequest);
		}
	}

}
