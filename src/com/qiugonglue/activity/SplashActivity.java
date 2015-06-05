package com.qiugonglue.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;


import com.qiugongllue.MainActivity;
import com.qiugongllue.R;
import com.qiugonglue.base.BaseActivity;

/**
 * 进入软件欢迎界面
 * 
 * @author dell
 * 
 */
public class SplashActivity extends BaseActivity {

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, MainActivity.class);
			startActivity(intent);
		};
	};
	
	@Override
	public void initView() {
		setContentView(R.layout.activity_splash);
		
		long start = System.currentTimeMillis();
		//1、检查网络
		
		//2、检查是否有版本更新信息
		
		//3、注册广播
		
		long end = System.currentTimeMillis();
		final long duration = end - start;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 睡眠剩余的时间
				try {
					Thread.sleep(5000-duration);
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void addListener() {

	}

	@Override
	public void initData() {

	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();// 结束掉本Activity,退出时直接退出,不在进入该页面
	}

}