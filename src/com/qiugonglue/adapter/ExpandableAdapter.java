package com.qiugonglue.adapter;

import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.domain.BournData.Bourn.Recommand;
import com.qiugonglue.domain.BournData.Bourn.Recommand.Board;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter {

	private List<Recommand> data;

	private LayoutInflater inflater;

	public ExpandableAdapter(List<Recommand> data, Context context) {
		this.data = data;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * 返回有多少个组
	 * 
	 * @return
	 */
	@Override
	public int getGroupCount() {
		return data.size();
	}

	/**
	 * @param groupPosition
	 * @return
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		Recommand recommand = data.get(groupPosition);
		List<Board> boards = recommand.list;
		return boards.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return data.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		Object ret = null;
		Recommand recommand = data.get(groupPosition);
		List<Board> boards = recommand.list;
		ret = boards.get(childPosition);
		return ret;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition * 1000 + childPosition;
	}

	/**
	 * getGroupId,getChildId 返回的数据是否固定，默认是false
	 * 
	 * @return
	 */
	@Override
	public boolean hasStableIds() {
		return false;
	}

	/**
	 * 获取 组 显示的界面
	 * 
	 * @param groupPosition
	 *            组位置
	 * @param isExpanded
	 *            是否展开 每次点击的展开或者收起都会调用这个方法，true/false不同
	 * @param convertView
	 *            用于复用的
	 * @param parent
	 *            父容器
	 * @return
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View ret = null;

		Log.d("MyContactAdapter", "group:" + groupPosition + "<---->isExpand:"
				+ isExpanded);
		
		Recommand recommand = data.get(groupPosition);
		
		// 获取名称
		String name = recommand.label;
		ret = inflater.inflate(R.layout.item_place, parent, false);

		TextView txtTitle = (TextView) ret.findViewById(R.id.place_choice);

		txtTitle.setText(name);

		return ret;
	}

	/**
	 * 获取 子内容的界面
	 * 
	 * @param groupPosition
	 *            组的位置
	 * @param childPosition
	 *            子元素位置
	 * @param isLastChild
	 *            是否是最后一个元素
	 * @param convertView
	 *            复用布局
	 * @param parent
	 *            父容器
	 * @return
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View ret = null;
		
		Recommand recommand = data.get(groupPosition);
		List<Board> boards = recommand.list;
		Board board = boards.get(childPosition);
		
		ret = inflater.inflate(R.layout.item_child_place, parent, false);

		TextView txtName = (TextView) ret.findViewById(R.id.child_place_choice);

		txtName.setText(board.name);

		return ret;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
