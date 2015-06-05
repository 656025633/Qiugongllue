package com.qiugonglue.adapter;

import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.domain.GroupDetailData.Group;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter {

	private Context context;
	private List<Group> data;
	
	public GroupAdapter(Context context,List<Group> data){
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
		if (convertView == null) {
			viewHolder = new LocalHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_listview_group, null);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.name_item_group);
			viewHolder.number = (TextView)convertView.findViewById(R.id.number_item_group);
			viewHolder.desc = (TextView)convertView.findViewById(R.id.desc_item_group);
			viewHolder.background = (ImageView) convertView.findViewById(R.id.img_item_group);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (LocalHolder) convertView.getTag();
		}

		viewHolder.title.setText(data.get(position).group_name);
		String limit = data.get(position).limit;
		String member = data.get(position).member_count;
		
		viewHolder.number.setText(member+"/"+limit);
		viewHolder.desc.setText(data.get(position).group_desc);
		String imageUrl = data.get(position).icon;
		if(imageUrl!=null){
			Picasso.with(context).load(imageUrl).into(viewHolder.background);
		}
		return convertView;
	}
	
    /**
     * 群组信息的holder
     */
    private static class LocalHolder{
    	public TextView title;
    	public TextView number;
    	public TextView desc;
    	public ImageView background; 
    }
}
