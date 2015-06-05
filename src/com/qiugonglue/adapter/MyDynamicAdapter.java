package com.qiugonglue.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.qiugongllue.R;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.domain.DynamicData.Data;
import com.qiugonglue.domain.DynamicData.Data.Tag;
import com.qiugonglue.view.RoundImageView;
import com.squareup.picasso.Picasso;

public class MyDynamicAdapter extends BaseAdapter {
	private List<Data> list;
	private Context context;
	private LayoutInflater inflater;
	private RequestQueue requestQueue;
	private ViewHolder mHolder = null;

	public MyDynamicAdapter(Context context, List<Data> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		requestQueue = AppCtx.getInstance().getRequestQueue();
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
	public View getView(int position, View contentView, ViewGroup viewGroup) {

		if (contentView == null) {
			mHolder = new ViewHolder();
			contentView = inflater
					.inflate(R.layout.fragment_dynamic_item, null);

			mHolder.textView_dynamicItem_username = (TextView) contentView
					.findViewById(R.id.textView_dynamicItem_username);
			mHolder.textView_humanCtime = (TextView) contentView
					.findViewById(R.id.textView_humanCtime);
			mHolder.textView_dynamicItem_content = (TextView) contentView
					.findViewById(R.id.textView_dynamicItem_content);
			mHolder.textView_dynamicItem_tagname = (TextView) contentView
					.findViewById(R.id.textView_dynamicItem_tagname);
			mHolder.linearLayout_dynamicItem_comment = (LinearLayout) contentView
					.findViewById(R.id.linearLayout_dynamicItem_comment);
			mHolder.textView_dynamicItem_favCount = (TextView) contentView
					.findViewById(R.id.textView_dynamicItem_favCount);
			mHolder.imageView_dynamicItem_usericon = (RoundImageView) contentView
					.findViewById(R.id.imageView_dynamicItem_usericon);
			mHolder.gridLayout = (GridLayout) contentView
					.findViewById(R.id.gridLayout_dynamicItem_coverImage);
			mHolder.gridLayout1_dynamicItem_coverImage = (GridLayout) contentView
					.findViewById(R.id.gridLayout1_dynamicItem_coverImage);
			mHolder.gridLayout4_dynamicItem_coverImage = (GridLayout) contentView
					.findViewById(R.id.gridLayout4_dynamicItem_coverImage);
			mHolder.gridLayout_dynamicItem_avatar = (GridLayout) contentView
					.findViewById(R.id.gridLayout_dynamicItem_avatar);

			contentView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) contentView.getTag();
		}

		mHolder.textView_dynamicItem_username
				.setText(list.get(position).author.user_name);
		mHolder.textView_humanCtime.setText(list.get(position).human_ctime);
		mHolder.textView_dynamicItem_content
				.setText(list.get(position).content);

		List<Tag> tags = list.get(position).tags;
		if (tags.size() > 0) {
			StringBuffer sbBuffer = new StringBuffer();
			for (int i = 0; i < tags.size(); i++) {
				sbBuffer.append(tags.get(i).tag_name + " ");
			}
			sbBuffer.toString();
			mHolder.textView_dynamicItem_tagname.setText(sbBuffer);
		}

		mHolder.textView_dynamicItem_favCount
				.setText(list.get(position).fav_count);

		// 加载头像
		String auatarURL = list.get(position).author.avatar; // 头像

		if (auatarURL != null) {

			ImageRequest imageRequest = new ImageRequest(auatarURL,
					new Response.Listener<Bitmap>() {
						@Override
						public void onResponse(Bitmap response) {
							mHolder.imageView_dynamicItem_usericon
									.setImageBitmap(response);
						}
					}, 300, 300, Config.RGB_565, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							mHolder.imageView_dynamicItem_usericon
									.setImageResource(R.drawable.ic_launcher);
						}
					});
			requestQueue.add(imageRequest);

		}

		// 存放发表内容的图片地址

		int urlsize = list.get(position).image_list.size();
		mHolder.gridLayout.removeAllViews();
		mHolder.gridLayout1_dynamicItem_coverImage.removeAllViews();
		mHolder.gridLayout4_dynamicItem_coverImage.removeAllViews();
		for (int i = 0; i < urlsize; i++) {
			ImageView imageView = new ImageView(context);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//					GridLayout.LayoutParams.WRAP_CONTENT,
//					GridLayout.LayoutParams.WRAP_CONTENT);
//			lp.setMargins(10, 20, 30, 40);
//			imageView.setLayoutParams(lp);
			
			GridLayout.LayoutParams params = new GridLayout.LayoutParams();
			
			params.setMargins(5, 5, 5, 5);
			imageView.setLayoutParams(params);
			if (list.get(position).image_count.equals("1")) {
				String url = list.get(position).image_list.get(i).image_url;
				Picasso.with(context).load(url).into(imageView);
				mHolder.gridLayout1_dynamicItem_coverImage.addView(imageView);
			} else if (list.get(position).image_count.equals("4")) {
				params.height = 130;
				params.width = 130;
				String url = list.get(position).image_list.get(i).image_thumbnail_url;
				Picasso.with(context).load(url).into(imageView);
				mHolder.gridLayout4_dynamicItem_coverImage.addView(imageView);
			} else {
				params.height = 130;
				params.width = 130;
				String url = list.get(position).image_list.get(i).image_thumbnail_url;
				Picasso.with(context).load(url).into(imageView);
				mHolder.gridLayout.addView(imageView);
			}
		}

		// 加载评论
		String comment_count = list.get(position).comment_count;
		mHolder.linearLayout_dynamicItem_comment.removeAllViews();
		if (comment_count != null) {
			for (int i = 0; i < list.get(position).comment_list.size(); i++) {
				TextView textView = new TextView(context);
				String name = list.get(position).comment_list.get(i).user_name;
				String text = list.get(position).comment_list.get(i).comment_content;
				// 截取字符串后给相应的地方设置颜色
				SpannableStringBuilder style = new SpannableStringBuilder(name
						+ ":" + text);
				style.setSpan(new ForegroundColorSpan(Color.BLUE), 0,
						name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				textView.setText(style);
				mHolder.linearLayout_dynamicItem_comment.addView(textView);
			}
		}

		// 点赞的图片地址
		int avatarUrlSize = list.get(position).action_user_list.size();
		
		mHolder.gridLayout_dynamicItem_avatar.removeAllViews();
		for (int i = 0; i < avatarUrlSize; i++) {
			final RoundImageView imageView = new RoundImageView(context);
			String url = list.get(position).action_user_list.get(i).avatar;
			if (url != null) {
				ImageRequest imageRequest = new ImageRequest(url,
						new Response.Listener<Bitmap>() {
							@Override
							public void onResponse(Bitmap response) {
								imageView.setImageBitmap(response);
							}
						}, 300, 300, Config.RGB_565,
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								imageView
										.setImageResource(R.drawable.ic_launcher);
							}
						});
				requestQueue.add(imageRequest);
			}
			imageView.setLayoutParams(new LayoutParams(50, 50));
			mHolder.gridLayout_dynamicItem_avatar.addView(imageView);
		}
		return contentView;
	}

	class ViewHolder {
		private TextView textView_dynamicItem_username; // 昵称
		private TextView textView_humanCtime; // 创建时间
		private TextView textView_dynamicItem_content; // 内容
		private TextView textView_dynamicItem_tagname; // 标签名
		private LinearLayout linearLayout_dynamicItem_comment; // 评论
		private TextView textView_dynamicItem_favCount; // 点赞数

		private RoundImageView imageView_dynamicItem_usericon;
		private GridLayout gridLayout;
		private GridLayout gridLayout1_dynamicItem_coverImage;
		private GridLayout gridLayout4_dynamicItem_coverImage;
		private GridLayout gridLayout_dynamicItem_avatar;

	}
}
