package com.qiugonglue.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.qiugongllue.R;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.domain.DynamicData.Data;
import com.qiugonglue.view.RoundImageView;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

//群动态点击查看本条动态
public class DynamicActivity extends Activity {

	private RequestQueue requestQueue;
	private TextView textView_dynamicItem_username;
	private TextView textView_dydetail_tags;
	private TextView textView_dydetail_comment;
	private RoundImageView imageView_dynamicItem2_usericon;
	private String avatarString;
	private GridLayout gridLayout_dydetail_pic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_detail);

		initView();

		EventBus.getDefault().registerSticky(DynamicActivity.this,
				"DynamicData", Data.class);

	}

	public void DynamicData(Data data) {
		if (data != null) {
			String usernameString = data.author.user_name;
			textView_dynamicItem_username.setText(usernameString);
			if (data.content != null) {
				String contentString = data.content;
				textView_dydetail_comment.setText(contentString);
			}
			avatarString = data.author.avatar;// 头像
			if (avatarString != null) {

				ImageRequest imageRequest = new ImageRequest(avatarString,
						new Response.Listener<Bitmap>() {
							@Override
							public void onResponse(Bitmap response) {
								imageView_dynamicItem2_usericon
										.setImageBitmap(response);
							}
						}, 300, 300, Config.RGB_565,
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								imageView_dynamicItem2_usericon
										.setImageResource(R.drawable.ic_launcher);
							}
						});
				requestQueue.add(imageRequest);
			}

			// 标签
			if (data.tags.size() > 0) {
				StringBuffer sbBuffer = new StringBuffer();
				for (int i = 0; i < data.tags.size(); i++) {
					sbBuffer.append(data.tags.get(i).tag_name + " ");
				}
				sbBuffer.toString();
				textView_dydetail_tags.setText(sbBuffer);
			}

			// 图片
			gridLayout_dydetail_pic.removeAllViews();
			for (int i = 0; i < data.image_list.size(); i++) {
				ImageView imageView = new ImageView(this);
				LayoutParams layoutParams = new LayoutParams(500, 600);
				imageView.setLayoutParams(layoutParams);
				String picUrl = data.image_list.get(i).image_url;
				Picasso.with(this).load(picUrl).into(imageView);

				gridLayout_dydetail_pic.addView(imageView);
			}
		}
	}

	private void initView() {
		requestQueue = AppCtx.getInstance().getRequestQueue();
		textView_dydetail_tags = (TextView) findViewById(R.id.textView_dydetail_tags);
		textView_dynamicItem_username = (TextView) findViewById(R.id.textView_dynamicItem_username);
		textView_dydetail_comment = (TextView) findViewById(R.id.textView_dydetail_comment);// 内容
		imageView_dynamicItem2_usericon = (RoundImageView) findViewById(R.id.imageView_dynamicItem2_usericon);
		gridLayout_dydetail_pic = (GridLayout) findViewById(R.id.gridLayout_dydetail_pic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	public void ClickButton(View v) {
		switch (v.getId()) {
		case R.id.textView_namicDetail_edit: // 跳转到发表评论的编辑页面
			Intent intent = new Intent(this, CommentEditActivity.class);
			startActivity(intent);
			break;
		case R.id.imageView_dynamicDetail_back: // 返回
			this.finish();
			break;
		case R.id.imageView_dynamicItem2_usericon: // 跳转到个人资料
			Intent intent2 = new Intent(this, OtherPersonActivity.class);
			startActivity(intent2);
			break;
		case R.id.imageView_dynamicDetail_share:
			showShare();
			break;
		default:
			break;
		}
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		// OnekeyShare oks = new OnekeyShare();
		// //关闭sso授权
		// oks.disableSSOWhenAuthorize();
		//
		// // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		// oks.setTitle("分享");
		// // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://sharesdk.cn");
		// // text是分享文本，所有平台都需要这个字段
		// oks.setText("我是分享文本");
		// // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// // url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://sharesdk.cn");
		// // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("我是测试评论文本");
		// // site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(getString(R.string.app_name));
		// // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://sharesdk.cn");
		//
		// // 启动分享GUI
		// oks.show(this);
	}
}
