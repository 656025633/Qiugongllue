package com.qiugonglue.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.adapter.AlreadyChoiceAdapter;
import com.qiugonglue.adapter.PlaceChoiceAdapter;
import com.qiugonglue.adapter.PlaceChoiceAdapter.OnPlaceChoiceListener;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseActivity;
import com.qiugonglue.domain.BournData;
import com.qiugonglue.domain.BournData.Bourn.Recommand;
import com.qiugonglue.domain.BournData.Bourn.Recommand.Board;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;
import com.qiugonglue.utils.MySQLiteOpenHelper;

/**
 * 目的地选择
 * 
 * @author dell
 * 
 */
public class PlaceChoiceActivity extends BaseActivity implements
		OnClickListener {

	// 已选目的地
	private LinearLayout already_layout;
	private GridView already_choice;
	private List<Board> already_list;
	private AlreadyChoiceAdapter already_Adapter;

	// 热门地区
	private RelativeLayout layout_place_hot;
	private TextView txt_place_hot;
	private GridView gridView_hot;

	// 亚洲地区
	private RelativeLayout layout_place_asia;
	private TextView txt_place_asia;
	private GridView gridView_aina;

	// 欧美地区
	private RelativeLayout layout_place_europe;
	private TextView txt_place_europe;
	private GridView gridView_europe;

	// 澳洲地区
	private RelativeLayout layout_place_australia;
	private TextView txt_place_australia;
	private GridView gridView_australia;

	// 国内地区
	private RelativeLayout layout_place_china;
	private TextView txt_place_china;
	private GridView gridView_china;

	private boolean[] isShow = new boolean[5];

	private AppCtx appCtx;
	private RequestQueue queue;
	private StringRequest stringRequest;
	private PlaceChoiceAdapter adapter;// 填充各个地区数据的适配器
	private MySQLiteOpenHelper helper;// 保存已选地区数据的helper

	@Override
	public void initView() {
		setContentView(R.layout.activity_place_choice);
		findView();
	}

	@Override
	public void initData() {
		appCtx = AppCtx.getInstance();
		queue = appCtx.getRequestQueue();
		helper = AppCtx.getInstance().getSQLHelper();

		stringRequest = new StringRequest(GLApi.CHOOSEPLACE,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 处理数据
						BournData bournData = GsonTools.jsonToBean(response,
								BournData.class);
						loadData(bournData);
					}
				}, new ErrorResponseListener(this));
		queue.add(stringRequest);
		
		//查询数据库中已选地区,并设置已选地区是否显示
		loadAlreadyPlace();
	}

	/**
	 * 查询数据库中已选地区的数据,并设置已选地区是否显示
	 */
	private void loadAlreadyPlace() {
		String select = " select board_id, name from tb_place order by create_time desc ";
		Cursor cursor = helper.selectCursor(select, null);
		already_list = new ArrayList<Board>();
		while(cursor.moveToNext()){
			Board board = new Board();
			board.board_id = cursor.getString(0);
			board.name = cursor.getString(1);
			already_list.add(board);
		}
		cursor.close();
		
		already_Adapter = new AlreadyChoiceAdapter(this, already_list);
		already_choice.setAdapter(already_Adapter);
		if(already_list.size()>0){
			already_layout.setVisibility(View.VISIBLE);
			already_choice.setVisibility(View.VISIBLE);
			setListViewHeightBasedOnChildren(already_choice);
		}
	}

	/**
	 * 将数据初始化到视图当中
	 * 
	 * @param bournData
	 */
	protected void loadData(BournData bournData) {
		if (bournData == null) {
			throw new IllegalArgumentException(" BournData is null ");
		}
		List<Recommand> recommend_list = bournData.data.recommend_list;
		// 获取数据放入到相应的GridView视图当中去
		for (int i = 0; i < recommend_list.size(); i++) {
			List<Board> list = recommend_list.get(i).list;
			adapter = new PlaceChoiceAdapter(this, list,
					new OnPlaceChoiceListener() {
						@Override
						public void onPlaceChoice(String board_id, String name,
								Board board) {

							// 执行添加已选地区数据的操作
							addPlaceData(board);
						}
					});
			switch (i) {
			case 0:
				gridView_hot.setAdapter(adapter);
				setListViewHeightBasedOnChildren(gridView_hot);
				break;
			case 1:
				gridView_aina.setAdapter(adapter);
				setListViewHeightBasedOnChildren(gridView_aina);
				break;
			case 2:
				gridView_europe.setAdapter(adapter);
				setListViewHeightBasedOnChildren(gridView_europe);
				break;
			case 3:
				gridView_australia.setAdapter(adapter);
				setListViewHeightBasedOnChildren(gridView_australia);
				break;
			case 4:
				gridView_china.setAdapter(adapter);
				setListViewHeightBasedOnChildren(gridView_china);
				break;
			}
		}
	}

	/**
	 * 执行添加已选地区数据的操作
	 * 
	 * @param board
	 */
	protected void addPlaceData(Board board) {
		// 1、先检查该地区是否已经选择过
		String select = " select board_id from tb_place where board_id = ? ";
		String insert;
		int count = helper.selectCount(select, new String[] { board.board_id });
		// 2、如果选择了.删除掉,重新添加
		if (count > 0) {
			String delete = " delete from tb_place where board_id = ? ";
			boolean flag = helper.execData(delete,
					new String[] { board.board_id });
			if (flag) {
				insert = " insert into tb_place(board_id,name,create_time) values(?,?,?) ";
				helper.execData(insert, new String[] { board.board_id,
						board.name, System.currentTimeMillis() + "" });
			}
		} else {
			// 3、如果没有选择,直接进行添加
			insert = " insert into tb_place(board_id,name,create_time) values(?,?,?) ";
			helper.execData(insert, new String[] { board.board_id, board.name,
					System.currentTimeMillis() + "" });
		}

		// 4、查询数据并进行展示,按照添加的时间倒序展示,最近的先展示
		selectPlaceFromDB();
	}

	/**
	 * 控件点击事件的处理
	 */
	@Override
	public void onClick(View v) {
		// 处理点击事件
		switch (v.getId()) {
		case R.id.layout_place_hot:
		case R.id.txt_place_hot:
			isShow[0] = !isShow[0];
			if (isShow[0])
				gridView_hot.setVisibility(View.VISIBLE);
			else
				gridView_hot.setVisibility(View.GONE);
			break;
		case R.id.layout_place_asia:
		case R.id.txt_place_asia:
			isShow[1] = !isShow[1];
			if (isShow[1])
				gridView_aina.setVisibility(View.VISIBLE);
			else
				gridView_aina.setVisibility(View.GONE);

			break;
		case R.id.layout_place_europe:
		case R.id.txt_place_europe:
			isShow[2] = !isShow[2];
			if (isShow[2])
				gridView_europe.setVisibility(View.VISIBLE);
			else
				gridView_europe.setVisibility(View.GONE);
			break;
		case R.id.layout_place_australia:
		case R.id.txt_place_australia:
			isShow[3] = !isShow[3];
			if (isShow[3])
				gridView_australia.setVisibility(View.VISIBLE);
			else
				gridView_australia.setVisibility(View.GONE);
			break;
		case R.id.layout_place_china:
		case R.id.txt_place_china:
			isShow[4] = !isShow[4];
			if (isShow[4])
				gridView_china.setVisibility(View.VISIBLE);
			else
				gridView_china.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * 为控件添加监听器
	 */
	@Override
	public void addListener() {
		// 热门地区
		layout_place_hot.setOnClickListener(this);
		txt_place_hot.setOnClickListener(this);

		// 亚洲地区
		layout_place_asia.setOnClickListener(this);
		txt_place_asia.setOnClickListener(this);

		// 欧美地区
		layout_place_europe.setOnClickListener(this);
		txt_place_europe.setOnClickListener(this);

		// 澳洲地区
		layout_place_australia.setOnClickListener(this);
		txt_place_australia.setOnClickListener(this);

		// 国内地区
		layout_place_china.setOnClickListener(this);
		txt_place_china.setOnClickListener(this);

		// 已选地区gridView的点击监听
		already_choice.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 单击事件,跳回到主页面,执行数据刷新

			}
		});

		// 监听已选地区条目的长按事件
		already_choice
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View parent, int position, long id) {
						// 弹出删除对话框
						showDeleteDialog(already_list.get(position));
						selectPlaceFromDB();
						return false;
					}
				});
	}

	/**
	 * 从数据库查询已选的地区并执行刷新操作
	 */
	private void selectPlaceFromDB() {
		String select = " select board_id, name from tb_place order by create_time desc ";
		Cursor cursor = helper.selectCursor(select, null);
		already_list.clear();
		while (cursor.moveToNext()) {
			Board board = new Board();
			board.board_id = cursor.getString(0);
			board.name = cursor.getString(1);
			already_list.add(board);
		}
		cursor.close();

		if(already_list.size() < 1){
			already_layout.setVisibility(View.GONE);
			already_choice.setVelocityScale(View.GONE);
		}
		if(already_list.size()>3){
			setListViewHeightBasedOnChildren(already_choice);
		}
		
		already_Adapter.notifyDataSetChanged();
		
		// 设置可见
		already_choice.setVisibility(View.VISIBLE);
	}

	/**
	 * 为控件执行FindViewById()
	 */
	private void findView() {
		// 已选择的目的地
		already_layout = (LinearLayout) findViewById(R.id.already_layout);
		already_choice = (GridView) findViewById(R.id.already_choice);

		// 热门
		layout_place_hot = (RelativeLayout) findViewById(R.id.layout_place_hot);
		txt_place_hot = (TextView) findViewById(R.id.txt_place_hot);
		gridView_hot = (GridView) findViewById(R.id.gridView_hot);

		// 亚洲
		layout_place_asia = (RelativeLayout) findViewById(R.id.layout_place_asia);
		txt_place_asia = (TextView) findViewById(R.id.txt_place_asia);
		gridView_aina = (GridView) findViewById(R.id.gridView_aina);

		// 欧美地区
		layout_place_europe = (RelativeLayout) findViewById(R.id.layout_place_europe);
		txt_place_europe = (TextView) findViewById(R.id.txt_place_europe);
		gridView_europe = (GridView) findViewById(R.id.gridView_europe);

		// 澳洲地区
		layout_place_australia = (RelativeLayout) findViewById(R.id.layout_place_australia);
		txt_place_australia = (TextView) findViewById(R.id.txt_place_australia);
		gridView_australia = (GridView) findViewById(R.id.gridView_australia);

		// 国内地区
		layout_place_china = (RelativeLayout) findViewById(R.id.layout_place_china);
		txt_place_china = (TextView) findViewById(R.id.txt_place_china);
		gridView_china = (GridView) findViewById(R.id.gridView_china);
	}

	/**
	 * 确保GONE掉的GridView在显示的时候能够全部展示
	 * 
	 * @param gridView
	 */
	private static void setListViewHeightBasedOnChildren(GridView gridView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		int totalHeight = 0;
		int column = 0;
		int count = listAdapter.getCount();
		if (count % 3 == 0) {
			column = count / 3;
		} else {
			column = count / 3 + 1;
		}
		for (int i = 0; i <= column; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + (17 * count);
		gridView.setLayoutParams(params);
	}

	/**
	 * 已选目的地条目的删除对话框
	 */
	protected void showDeleteDialog(final Board board) {
		// 执行删除或者取消
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("删除已选目的地?");
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行删除操作
				String delete = " delete from tb_place where board_id = ? ";
				boolean flag = helper.execData(delete,
						new String[] { board.board_id });
				if (flag) {
					selectPlaceFromDB();
				}
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取消删除操作
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
