package com.qiugonglue.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.GroupPlaceData;
import com.qiugonglue.domain.GroupPlaceData.GroupPlace;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

/**
 * 所有群Fragment
 * @author dell
 *
 */
public class GroupFragment extends BaseFragment {

	private ListView listview;
	private RequestQueue queue;
	private StringRequest placeRequest;
	private List<GroupPlace> places;

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.layout_place_group, null);
		listview = (ListView) view.findViewById(R.id.listview_group);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// 初始化数据
		queue = AppCtx.getInstance().getRequestQueue();

		initPlaceData();// 初始化地区数据
		
	}
	
	private void initPlaceData() {
		placeRequest = new StringRequest(GLApi.CHATPLACE,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 将数据填充到视图中
						GroupPlaceData placeData = GsonTools.jsonToBean(
								response, GroupPlaceData.class);
						loadPlaceData(placeData);
					}
				}, new ErrorResponseListener(getActivity()));
		queue.add(placeRequest);
	}

	protected void loadPlaceData(GroupPlaceData placeData) {
		if (placeData == null) {
			throw new IllegalArgumentException(" GroupPlaceData is null ");
		}
		places = placeData.data;
		String[] placeNames = new String[places.size()];
		for (int i = 0; i < placeNames.length; i++) {
			placeNames[i] = places.get(i).place_name;
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, placeNames);
		adapter.setDropDownViewResource(R.layout.item_child_place);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				// 向群组详细信息的Fragment传值
				Bundle bundle = new Bundle();
				bundle.putString("placeName", places.get(position).client_name);
				DetailPlaceFragment fragment = new DetailPlaceFragment();
				fragment.setArguments(bundle);
				getChildFragmentManager().beginTransaction().replace(R.id.container_frag_detail, fragment,"detailFragment").commit();
			}
		});
		
		initGroupData();// 初始化群组数据
	}

	private void initGroupData() {
		DetailPlaceFragment fragment = new DetailPlaceFragment();
		Bundle bundle = new Bundle();
		bundle.putString("placeName", places.get(0).client_name);
		fragment.setArguments(bundle);
		getChildFragmentManager().beginTransaction().replace(R.id.container_frag_detail, fragment,"detailFragment").commit();
	}
}
