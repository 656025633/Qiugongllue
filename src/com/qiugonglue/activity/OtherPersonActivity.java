package com.qiugonglue.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.domain.OtherPersonData;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;
import com.qiugonglue.view.RoundImageView;

public class OtherPersonActivity extends Activity {
	private RequestQueue requestQueue;
	private TextView txt_sign_other;
	private RoundImageView img_icon_other;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo_dynamic_other);
		initView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	private void initView() {
		requestQueue = AppCtx.getInstance().getRequestQueue();
		txt_sign_other = (TextView) findViewById(R.id.txt_sign_other);
		img_icon_other = (RoundImageView) findViewById(R.id.img_icon_other);

		getPersonData(GLApi.PERSONMESSAGE);
	}

	// 得到个人信息内容
	public void getPersonData(String URL) {
		System.out.println("Other中的url" + URL);
		StringRequest request = new StringRequest(URL, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("Other中的response" + response);
				loadPersonData(response);
			}
		}, new ErrorResponseListener(this));
		requestQueue.add(request);
	}

	/**
	 * 初始化个人信息数据
	 * 
	 * @param response
	 */
	protected void loadPersonData(String response) {
		System.out.println(response);

		if (response == null)
			return;

		OtherPersonData otherPersonData = GsonTools.jsonToBean(response,
				OtherPersonData.class);

		txt_sign_other.setText(otherPersonData.data.user.desc);
		// Picasso.with(this).load(otherPersonData.data.user.avatar).into(img_icon_other);

		ImageRequest imageRequest = new ImageRequest(
				otherPersonData.data.user.avatar,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						img_icon_other.setImageBitmap(response);
					}
				}, 300, 300, Config.RGB_565, new Response.ErrorListener() { // 此处会可能出错，注意导包
					@Override
					public void onErrorResponse(VolleyError error) {
						img_icon_other.setImageResource(R.drawable.ic_launcher);
					}
				});
		requestQueue.add(imageRequest);
	}

	public void ClickButton(View view) {
		switch (view.getId()) {
		case R.id.imageView_personinfo_back:
			this.finish();
			break;
		case R.id.imageView_personinfo_moredo:

			break;
		case R.id.rLayout_TADynamic:// TA的动态
			Intent intent = new Intent(this, TADynamicActivity.class);
			startActivity(intent);
			break;
		case R.id.rLayout_comment_other: // 足迹
			Intent intent3 = new Intent(this, FootPrintActivity.class);
			startActivity(intent3);
			break;

		default:
			break;
		}
	}

}
