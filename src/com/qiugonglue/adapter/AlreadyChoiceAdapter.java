package com.qiugonglue.adapter;

import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.domain.BournData.Bourn.Recommand.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 选择目的地的GridView的适配器
 * @author dell
 *
 */
public class AlreadyChoiceAdapter extends BaseAdapter {

	private Context context;
	private List<Board> data;
	
	public AlreadyChoiceAdapter(Context context,List<Board> data){
		this.context = context;
		this.data = data;
	}
	
	public void addData(Board board){
		data.add(board);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new PlaceHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_gridview_place, null);

			viewHolder.placeName = (TextView) convertView
					.findViewById(R.id.txt_name_palce);
			convertView.setTag(viewHolder);
			
			//对地区进行监听,返回相应的值,添加到已选目的地列表当中
			
			
		} else {
			viewHolder = (PlaceHolder) convertView.getTag();
		}

		viewHolder.placeName.setText(data.get(position).name);
		return convertView;
	}
	
	/**
	 * 目的地的holder
	 * @author dell
	 *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	 */
    private static class PlaceHolder{
    	public TextView placeName;
    }
}
