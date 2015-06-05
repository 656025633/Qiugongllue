package com.qiugonglue.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.qiugongllue.R;
import com.qiugonglue.activity.ArmPlaceActivity;
import com.qiugonglue.activity.DetailActivity;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.activity.LocalActivity;
import com.qiugonglue.activity.PlaceChoiceActivity;
import com.qiugonglue.adapter.AlreadyChoiceAdapter;
import com.qiugonglue.adapter.ListViewAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.GuideData;
import com.qiugonglue.domain.BournData.Bourn.Recommand.Board;
import com.qiugonglue.domain.GuideData.Data.Recommand;
import com.qiugonglue.domain.OriginalData;
import com.qiugonglue.domain.OriginalData.Origin.Original;
import com.qiugonglue.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.qiugonglue.pulltorefresh.PullToRefreshListView;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;
import com.qiugonglue.utils.MySQLiteOpenHelper;
import com.qiugonglue.view.RoundImageView;

/**
 * 推荐页面的Fragment
 * 
 * @author dell
 * 
 */
public class HomeFragment extends BaseFragment {

	private View view;

	@ViewInject(R.id.more_choice)
	private ImageView more_choice;
	private LinearLayout container_layout;
	@ViewInject(R.id.pulltorefresh)
	private PullToRefreshListView pulltorefresh;
	private ListView listView;

	private RequestQueue queue;
	private ImageRequest imageRequest;
	private StringRequest stringRequest;
	private StringRequest listRequest;

	private int page = 1;

	private static final int PLACE_CHOICE = 101;

	private ListViewAdapter adapter;

	private List<Original> originals;

	private MySQLiteOpenHelper helper;

	private List<Board> boards;

	private AlreadyChoiceAdapter alreadyChoiceAdapter;

	private GridView gridview_head_home;

	private String select;

	private Cursor cursor;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_home, null);
		ViewUtils.inject(this, view);

		listView = pulltorefresh.getRefreshableView();

		pulltorefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				page = 1;
				reloadListView();
			}
		});

		loadAlreadyPlace();

		return view;
	}

	public void loadAlreadyPlace() {
		helper = AppCtx.getInstance().getSQLHelper();
		View head_home = LayoutInflater.from(getActivity()).inflate(
				R.layout.head_home, null);
		container_layout = (LinearLayout) head_home
				.findViewById(R.id.container_layout);
		gridview_head_home = (GridView) head_home
				.findViewById(R.id.gridview_head_home);
		boards = new ArrayList<Board>();
		alreadyChoiceAdapter = new AlreadyChoiceAdapter(getActivity(), boards);
		gridview_head_home.setAdapter(alreadyChoiceAdapter);

		TextView local_more = (TextView) head_home
				.findViewById(R.id.local_more);
		local_more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HomeFragment.this.getActivity(),
						LocalActivity.class);
				startActivity(intent);
			}
		});

		selectPlaceFromDB();

		gridview_head_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 点击进入该地区的详细页面.
				Bundle bundle = new Bundle();
				bundle.putString("url", GLApi.TOKYO);
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(HomeFragment.this.getActivity(),
						ArmPlaceActivity.class);
				startActivity(intent);
			}
		});

		listView.addHeaderView(head_home);// 添加头视图
	}

	public void selectPlaceFromDB() {
		select = " select board_id, name from tb_place order by create_time desc ";
		cursor = helper.selectCursor(select, null);

		int count = 0;
		boards.clear();
		while (cursor.moveToNext() && count < 4) {
			Board board = new Board();
			board.board_id = cursor.getString(0);
			board.name = cursor.getString(1);
			boards.add(board);
			count++;
		}
		if (boards.size() > 0) {
			alreadyChoiceAdapter.notifyDataSetChanged();
			gridview_head_home.setVisibility(View.VISIBLE);
			setListViewHeightBasedOnChildren(gridview_head_home);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		selectPlaceFromDB();
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
		}, new ErrorResponseListener(getActivity()));
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

	@Override
	public void initData(Bundle savedInstanceState) {
		queue = AppCtx.getInstance().getRequestQueue();
		stringRequest = new StringRequest(GLApi.GUIDE,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						loadData2Layout(response);
					}
				}, new ErrorResponseListener(getActivity()));
		queue.add(stringRequest);

		// 加载listview
		String url = String.format(GLApi.FINDMORE, page);
		listRequest = new StringRequest(url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				loadData2ListView(response);
			}
		}, new ErrorResponseListener(getActivity()));
		queue.add(listRequest);
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
		adapter = new ListViewAdapter(getActivity(), originals);
		listView.setAdapter(adapter);

	}

	protected void loadData2Layout(String response) {
		if (response == null)
			return;
		GuideData guideData = GsonTools.jsonToBean(response, GuideData.class);
		List<Recommand> reList = guideData.data.item_recommend;
		for (int i = 0; i < reList.size(); i++) {
			final Recommand recommand = reList.get(i);
			String url = recommand.mainImage;
			final RoundImageView roundImageView = new RoundImageView(
					getActivity());
			roundImageView.setPadding(5, 5, 5, 5);
			imageRequest = new ImageRequest(url,
					new Response.Listener<Bitmap>() {
						public void onResponse(Bitmap response) {
							if (response != null) {
								roundImageView.setImageBitmap(response);
								roundImageView
										.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {
												Intent intent = new Intent();
												Bundle bundle = new Bundle();
												bundle.putString("linkUrl", recommand.url);
												intent.putExtras(bundle);
												intent.setClass(
														HomeFragment.this
																.getActivity(),
														DetailActivity.class);
												startActivity(intent);
											}
										});
								container_layout.addView(roundImageView);
							}
						}
					}, 170, 170, Config.RGB_565, new ErrorResponseListener(
							getActivity()));
			queue.add(imageRequest);
		}
	}

	@OnClick(R.id.more_choice)
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.more_choice:
			intent.setClass(getActivity(), PlaceChoiceActivity.class);
			startActivityForResult(intent, PLACE_CHOICE);
			break;
		}
	}

	private static void setListViewHeightBasedOnChildren(GridView gridView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		int totalHeight = 0;
		int column = 0;
		int count = listAdapter.getCount();
		if (count > 2) {
			column = 2;
		} else {
			column = 1;
		}
		for (int i = 0; i <= column; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);
	}
}
