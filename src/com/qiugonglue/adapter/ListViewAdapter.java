package com.qiugonglue.adapter;

import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.activity.DetailActivity;
import com.qiugonglue.domain.OriginalData.Origin.Original;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	private Context context;
	private List<Original> data;
	
	public ListViewAdapter(Context context,List<Original> data){
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
		LocalHolder viewHolder = null;
		final int index = position;
		
		
		
		
		
		if (convertView == null) {
			viewHolder = new LocalHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_listview, null);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.title_item);
			viewHolder.tag = (TextView)convertView.findViewById(R.id.tag_item);
			viewHolder.like = (TextView)convertView.findViewById(R.id.like_item);
			viewHolder.background = (ImageView) convertView.findViewById(R.id.background_local);
			
			/**
			 * 图片的监听
			 */
			viewHolder.background.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 点击图片 进入详细页面查看明细
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("linkUrl", data.get(index).link);
					intent.putExtras(bundle);
					intent.setClass(context, DetailActivity.class);
					context.startActivity(intent);
				}
			});
			
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (LocalHolder) convertView.getTag();
		}

		viewHolder.title.setText(data.get(position).title);
		viewHolder.tag.setText(data.get(position).tags);
		viewHolder.like.setText(data.get(position).like);
		String imageUrl = data.get(position).src;
		if(imageUrl!=null){
			Picasso.with(context).load(imageUrl).into(viewHolder.background);
		}
		return convertView;
	}
	
    /**
     * 当地专题的holder
     */
    private static class LocalHolder{
    	public TextView title;
    	public TextView tag;
    	public TextView like;
    	public ImageView background; 
    }
}
