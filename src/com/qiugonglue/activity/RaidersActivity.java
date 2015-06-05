package com.qiugonglue.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiugongllue.R;

public class RaidersActivity extends FragmentActivity implements OnClickListener {

	private ImageView back;
	private ViewPager viewPager;
	private HorizontalScrollView scrollView;
	
	private String [] tabHost = {"美食","景点","当地体验","购物","住宿","交通/出入境","推荐行程","简介","实用信息",};
	private int width;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		initData();
		addListener();
	}
	
	
	public void initView() {
		setContentView(R.layout.activity_raiders);
		back = (ImageView) findViewById(R.id.back);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		scrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				//scrollView移动
				scrollView.scrollTo(width*position, 0);
				//设置字体颜色
				((TextView)scrollView.getChildAt(position)).setTextColor(Color.BLUE);
				//执行动画
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		initSrcollView();
	}

	/**
	 * 初始化导航栏
	 */
	private void initSrcollView() {
		width = getResources().getDisplayMetrics().widthPixels/4;
		for (int i = 0; i < tabHost.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(tabHost[i]);
			textView.setWidth(getResources().getDisplayMetrics().widthPixels/4);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView textView = (TextView) v;
					String text = textView.getText().toString().trim();
					for (int j = 0; j < tabHost.length; j++) {
						if(tabHost[j].equals(text)){
							viewPager.setCurrentItem(j);
							scrollView.scrollTo(width * j, 0);
							textView.setTextColor(Color.BLUE);
						}
					}
				}
			});
			//添加到scrollView当中
			scrollView.addView(textView);
		}
	}


	public void addListener() {
		back.setOnClickListener(this);
		
		
	}

	public void initData() {
		for (int i = 0; i < tabHost.length; i++) {
			
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		}
	}
}
