package com.qiugonglue;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;


import cn.sharesdk.framework.ShareSDK;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.qiugongllue.R;
import com.qiugonglue.base.BaseActivity;

/**
 * 详细信息页面
 * 
 * @author dell
 * 
 */
public class DetailActivity extends BaseActivity {

	@ViewInject(R.id.webview_detail)
	private WebView webview_detail;
	@ViewInject(R.id.back_detail)
	private ImageView back;
	@ViewInject(R.id.share_detail)
	private ImageView share;
	@ViewInject(R.id.top_btn)
	private Button top_btn;

	private GestureDetector gestureDetector = null;

	@Override
	public void initView() {
		setContentView(R.layout.activity_detail);
		ViewUtils.inject(this);
		Bundle bundle = getIntent().getExtras();
		linkUrl = bundle.getString("linkUrl");

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO 初始化PopupWindow
		if (hasFocus) {
			loadPwindow();
		}
	}

	/**
	 * 初始化poputwindow
	 */

	private void loadPwindow() {
		view = getLayoutInflater().inflate(R.layout.pwindow_detail, null);
		ImageView back_pwindow = (ImageView) view
				.findViewById(R.id.back_pwindow);
		ImageView go_pwindow = (ImageView) view.findViewById(R.id.go_pwindow);
		ImageView share_pwindow = (ImageView) view
				.findViewById(R.id.share_pwindow);
		ImageView refresh_pwindow = (ImageView) view
				.findViewById(R.id.refresh_pwindow);
		back_pwindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DetailActivity.this.finish();
			}
		});
		go_pwindow.setOnClickListener(null);
		share_pwindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 显示分享对话框
				shareDialog();
			}
		});
		refresh_pwindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 刷新页面
				initData();
			}
		});
		window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

	}

	@Override
	public void addListener() {
		// TODO 对控件进行添加监听
		gestureDetector = new GestureDetector(this,
				new GestureDetector.OnGestureListener() {
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						float startY = e1.getY();
						float endY = e2.getY();
						if ((endY - startY) < 30) {
							if (window.isShowing()) {
								window.dismiss();
							}
						} else if ((endY - startY) > 30) {
							if (!window.isShowing()) {
								window.showAtLocation(view, Gravity.BOTTOM, 0,
										0);
							}
						}
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {

					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
						return false;
					}

					@Override
					public boolean onDown(MotionEvent e) {
						return false;
					}
				});
	}

	@Override
	public void initData() {
		if (linkUrl == null)
			return;
		WebSettings settings = webview_detail.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		settings.setAppCacheEnabled(true);
		settings.setJavaScriptEnabled(true);// 设置支持Javascript
		webview_detail.loadUrl(linkUrl);
		// 设置在本WebView当中显示点击的链接
		webview_detail.setWebViewClient(new WebViewClient());
	}

	@OnClick({ R.id.back_detail, R.id.share_detail })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back_detail:
			finish();
			// 写上动画
			break;
		case R.id.share_detail:
			shareDialog();
			break;
		}
	}

	public String[] items = new String[] { "分享到群聊", "新浪微博", "微信朋友圈", "更多" };
	private String linkUrl;
	private PopupWindow window;
	private View view;

	// private OnekeyShare oks;
	private void shareDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择分享方式");
		builder.setIcon(R.drawable.ic_launcher);
		ShareSDK.initSDK(this);// 初始化
		// oks = new OnekeyShare();
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 选择分享条目
				switch (which) {
				case 0:
					break;
				case 1:
					// oks.setPlatform(SinaWeibo.NAME);//
					// oks.show(this);
					break;
				case 2:
					// oks.setPlatform(WechatMoments.NAME);

					break;
				case 3:
					break;
				}
			}
		});

		// 取消按钮
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.create().show();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		webview_detail.onTouchEvent(event);
		return super.dispatchTouchEvent(event);
	}
}
