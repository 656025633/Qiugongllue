package com.qiugonglue.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiugongllue.R;
import com.qiugonglue.domain.FootPrintData.Data.BeenList;
import com.squareup.picasso.Picasso;

public class MyFootPrintAdapter extends BaseAdapter {
	private List<BeenList> list;
	private LayoutInflater inflater;
	private Context context;

	public MyFootPrintAdapter(Context context, List<BeenList> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder mHolder = null;
		if (view == null) {
			mHolder = new ViewHolder();
			view = inflater.inflate(R.layout.layout_footprint_itme, null);

			mHolder.imageView_footprint_coverImage = (ImageView) view
					.findViewById(R.id.imageView_footprint_coverImage);
			mHolder.textView_footprint_title = (TextView) view
					.findViewById(R.id.textView_footprint_title);
			mHolder.textView_footprint_area = (TextView) view
					.findViewById(R.id.textView_footprint_area);
			mHolder.textView_footprint_subline = (TextView) view
					.findViewById(R.id.textView_footprint_subline);

			view.setTag(mHolder);
		} else {

			mHolder = (ViewHolder) view.getTag();
		}

		if (list.size() > 0) {
			
				System.out.println("size"+list.size());
				mHolder.textView_footprint_title.setText(list.get(position).title);
				mHolder.textView_footprint_area.setText(list.get(position).p_area);
				mHolder.textView_footprint_subline
						.setText(list.get(position).sub_line);
				System.out.println("ttttt"+list.get(position).title);
				String picUrl = list.get(position).ol_cover_image;

				Picasso.with(context).load(picUrl)
						.into(mHolder.imageView_footprint_coverImage);
			}
		return view;
	}

	class ViewHolder {
		private ImageView imageView_footprint_coverImage;
		private TextView textView_footprint_title;
		private TextView textView_footprint_area;
		private TextView textView_footprint_subline;
	}
	
}
