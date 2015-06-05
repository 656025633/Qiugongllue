package com.qiugonglue.activity;

import java.util.ArrayList;
import java.util.List;

import com.qiugongllue.R;
import com.qiugonglue.fragment.MyFragmentAdapter;
import com.qiugonglue.fragment.TADynamicViewPagerFragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 他人动态
 * 
 * @author dell
 * 
 */
public class TADynamicActivity extends FragmentActivity {

	private ViewPager viewPager_TAdynamic;
	private LinearLayout layout_TAdynamic_title;
	private LinearLayout layout_TADynamci_linedown;
	private List<Fragment> Flist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tadynamic);

		init();
	}

	public void init() {
		viewPager_TAdynamic = (ViewPager) findViewById(R.id.viewPager_TAdynamic);
		layout_TAdynamic_title = (LinearLayout) findViewById(R.id.layout_TAdynamic_title);
		layout_TADynamci_linedown = (LinearLayout) findViewById(R.id.layout_TADynamci_linedown);
		Flist = new ArrayList<Fragment>();
		addFragment();

		// 添加ViewPager滑动监听
		viewPager_TAdynamic
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int i, float v, int i1) {
					}

					@Override
					public void onPageSelected(int i) {

						initColor();
						TextView textView = (TextView) layout_TAdynamic_title
								.getChildAt(i);
						textView.setTextColor(Color.BLUE);
						TextView textView2 = (TextView) layout_TADynamci_linedown
								.getChildAt(i);
						textView2.setBackgroundColor(Color.BLUE);
					}

					@Override
					public void onPageScrollStateChanged(int i) {
					}
				});
	}

	/**
	 * 初始化条目的颜色
	 */
	private void initColor() {
		for (int j = 0; j < 2; j++) {
			TextView textView_find = (TextView) layout_TAdynamic_title
					.getChildAt(j);
			TextView textView_line = (TextView) layout_TADynamci_linedown
					.getChildAt(j);
			textView_find.setTextColor(Color.BLACK);
			textView_line.setBackgroundColor(Color.WHITE);
		}
	}

	/**
	 * List添加碎片
	 */
	private void addFragment() {

		TADynamicViewPagerFragment fragment = new TADynamicViewPagerFragment();
		Bundle bundle_new = new Bundle();
		bundle_new.putInt("index", 0);
		fragment.setArguments(bundle_new);
		TADynamicViewPagerFragment fragment2 = new TADynamicViewPagerFragment();
		Bundle bundle = new Bundle();
		bundle_new.putInt("index", 1);
		fragment2.setArguments(bundle);

		Flist.add(fragment);
		Flist.add(fragment2);

		// 添加适配器
		MyFragmentAdapter adapter = new MyFragmentAdapter(
				getSupportFragmentManager(), Flist);
		viewPager_TAdynamic.setAdapter(adapter);
	}

	public void ClickButton(View view) {
		switch (view.getId()) {
		case R.id.imageView_commentEdit_back:
			this.finish();
			break;

		default:
			break;
		}
	}
}
