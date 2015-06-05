package com.qiugonglue.adapter;

import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.domain.ArmPlaceData.PlaceData.Command;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

	private Context context;
	private List<Command> data;
	
	public GridViewAdapter(Context context,List<Command> data){
		this.context = context;
		this.data = data;
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
					R.layout.item_listview, null);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.title_item);
			viewHolder.image_tag_item = (ImageView) convertView.findViewById(R.id.image_tag_item);
			viewHolder.background = (ImageView) convertView.findViewById(R.id.background_local);
			viewHolder.like = (TextView) convertView.findViewById(R.id.like_item);
			viewHolder.image_tag_item.setVisibility(View.GONE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (PlaceHolder) convertView.getTag();
		}

		viewHolder.title.setText(data.get(position).title);
		viewHolder.like.setText(data.get(position).like_count);
        Picasso.with(context).load(data.get(position).cover_image).into(viewHolder.background);
		return convertView;
	}
	
	/**
	 * 目的地的holder
	 * @author dell
	 *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	 */
    private static class PlaceHolder{
    	public TextView title;
    	public TextView like;
    	public ImageView background;
    	public ImageView image_tag_item;
    }
}
