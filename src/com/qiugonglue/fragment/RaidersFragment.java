package com.qiugonglue.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;

/**
 * 攻略Fragment： 实现各种功能 比如,展示美食,交通,推荐等等
 * 
 * @author dell
 */
public class RaidersFragment extends BaseFragment {

	private View view;

	private ExpandableListView listview_raiders;

	private int index;
	
	private String url ;
	private RequestQueue queue;
	private StringRequest stringRequest;
	
//	private List<T> dataList;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_raiders, null);
		listview_raiders = (ExpandableListView) view.findViewById(R.id.listview_raiders);
		
		listview_raiders.setGroupIndicator(null);//设置去掉下拉的箭头
		listview_raiders.setOnGroupCollapseListener(null);//设置ExpandableListView折叠事件
		listview_raiders.setOnGroupExpandListener(null);//设置ExpandableListView的展开事件
		
		listview_raiders.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				//开启新的Activity
			}
		});
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		queue = AppCtx.getInstance().getRequestQueue();
		Bundle bundle = getArguments();
		index = bundle.getInt("index");
		switch (index) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		case 8:

			break;
		}
	}

	public void loadData() {
		stringRequest = new StringRequest(
				url, 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//处理数据
						
					}
				}, 
				new ErrorResponseListener(getActivity())
		);
		queue.add(stringRequest);
	}
}
