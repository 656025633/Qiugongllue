package com.qiugonglue.adapter;

import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.domain.BournData.Bourn.Recommand.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 选择目的地的GridView的适配器
 * @author dell
 *
 */
public class PlaceChoiceAdapter extends BaseAdapter {

	private Context context;
	private List<Board> data;
	private OnPlaceChoiceListener listener;
	
	public interface OnPlaceChoiceListener{
		public void onPlaceChoice(String board_id,String name,Board board);
	}
	
	public PlaceChoiceAdapter(Context context,List<Board> data,OnPlaceChoiceListener listener){
		this.context = context;
		this.data = data;
		this.listener = listener;
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
		final String board_id = data.get(position).board_id;
		final String name = data.get(position).name;
		final Board board = data.get(position);
		viewHolder.placeName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//返回所点击的具体的值或者相应的标识
				listener.onPlaceChoice(board_id,name,board);
			}
		});
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
