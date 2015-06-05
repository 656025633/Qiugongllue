package com.qiugonglue.activity;

import java.util.List;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.qiugongllue.R;
import com.qiugonglue.adapter.ListViewAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseActivity;
import com.qiugonglue.domain.OriginalData;
import com.qiugonglue.domain.OriginalData.Origin.Original;
import com.qiugonglue.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.qiugonglue.pulltorefresh.PullToRefreshListView;
import com.qiugonglue.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;

/**
 * 当地专题
 * 
 * @author dell
 * 
 */
public class LocalActivity extends BaseActivity {

	@ViewInject(R.id.place_choice)
	private Button place_choice;
	@ViewInject(R.id.pulltorefresh)
	private PullToRefreshListView pulltorefresh;

	@ViewInject(R.id.back_detail)
	private ImageView back_detail;

	private PopupWindow pWindow;
	private StringRequest listRequest;
	private RequestQueue queue;

	private int page = 1;

	private List<Original> originals;
	private ListViewAdapter adapter;
	private ListView listView;

	public void initView() {
		setContentView(R.layout.activity_local);
		ViewUtils.inject(this);
		listView = pulltorefresh.getRefreshableView();

	}

	public void initData() {
		queue = AppCtx.getInstance().getRequestQueue();
		// 加载listview
		String url = String.format(GLApi.FINDMORE, page);
		listRequest = new StringRequest(url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				loadData2ListView(response);
			}
		}, new ErrorResponseListener(this));
		queue.add(listRequest);
	}


	@OnClick({ R.id.back_detail, R.id.place_choice })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back_detail:
			finish();
			break;
		case R.id.place_choice:
			if (pWindow.isShowing()) {
				pWindow.dismiss();
			} else {
				pWindow.showAtLocation(place_choice, Gravity.BOTTOM, 0, 0);
			}
			break;
		}
	}

	@Override
	public void addListener() {
		//下拉加载
		pulltorefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				page = 1;
				reloadListView();
			}
		});

		//上拉加载
		pulltorefresh
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						page++;
						reloadListView();
					}
				}
		);
		
	}
	
	/**
	 * 填充listView数据
	 * 
	 * @param response
	 */
	protected void loadData2ListView(String response) {
		if (response == null)
			return;
		OriginalData originalData = GsonTools.jsonToBean(response,
				OriginalData.class);
		originals = originalData.data.cms_list;
		adapter = new ListViewAdapter(this, originals);
		listView.setAdapter(adapter);
	}

	/**
	 * 刷新listView
	 */
	protected void reloadListView() {
		String url = String.format(GLApi.FINDMORE, page);
		listRequest = new StringRequest(url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				reloadData2ListView(response);
			}
		}, new ErrorResponseListener(this));
		queue.add(listRequest);
	}
	
	protected void reloadData2ListView(String response) {
		if (response == null)
			return;
		OriginalData originalData = GsonTools.jsonToBean(response,
				OriginalData.class);
		List<Original> orls = originalData.data.cms_list;
		originals.clear();
		originals.addAll(orls);
		adapter.notifyDataSetChanged();
	}
}
