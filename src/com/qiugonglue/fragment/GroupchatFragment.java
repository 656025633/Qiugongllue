package com.qiugonglue.fragment;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiugongllue.R;
import com.qiugonglue.adapter.FragmentAdapter;
import com.qiugonglue.base.BaseFragment;

public class GroupchatFragment extends BaseFragment{
	private View view;
	private ViewPager viewPager;
	private ImageView image_anim;
	
	private LinearLayout container_tab;
	
	private List<Fragment> fragments;
	
	private Animation trAnimation = null;
	
	private String [] title = {"消息","所有群"};
	private int columnWidth;
	private int currentLeftWidth;
	
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_groupchat, null);
		viewPager = (ViewPager) view.findViewById(R.id.viewpager_group);
		container_tab = (LinearLayout) view.findViewById(R.id.container_tab);
		image_anim = (ImageView) view.findViewById(R.id.image_anim);
		return view;
	}
	
	public void addListener(){
		
		columnWidth = 390;
		currentLeftWidth = 0;
		for (int i = 0; i < title.length; i++) {
			TextView  textView = new TextView(getActivity());
			textView.setText(title[i]);
			textView.setWidth(columnWidth);
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(18);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 处理点击事件
					TextView textView = (TextView) v;
					String tabText = textView.getText().toString().trim();
					for (int j = 0; j < title.length; j++) {
						if(title[j] == tabText){
							//处理点击事件.执行动画
							
							trAnimation = new TranslateAnimation(currentLeftWidth, columnWidth*(j), 0f, 0f);
							trAnimation.setFillAfter(true);
							trAnimation.setDuration(200);
							image_anim.startAnimation(trAnimation);
							
							currentLeftWidth = columnWidth*(j);
							
							//显示viewPager
							viewPager.setCurrentItem(j);
						}
					}
				}
			});
			container_tab.addView(textView);

		}
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				//执行动画操作,按钮颜色变化
				trAnimation = new TranslateAnimation(currentLeftWidth, columnWidth*(position), 0f, 0f);
				trAnimation.setFillAfter(true);
				trAnimation.setDuration(200);
				image_anim.startAnimation(trAnimation);
				currentLeftWidth = columnWidth*(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});

	}
	
	@Override
	public void initData(Bundle savedInstanceState) {
		fragments = new ArrayList<Fragment>();
		//创建Fragment
		for (int i = 0; i < 2; i++) {
			switch (i) {
			case 0:
				//创建左边的碎片
				MessageFragment mFragment = new MessageFragment();
				fragments.add(mFragment);
				break;
			case 1:
				//创建右边的碎片
				GroupFragment gFragment = new GroupFragment();
				fragments.add(gFragment);
				break;
			}
		}
		
		FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), fragments,new String[]{"消息","我的群"});
		viewPager.setAdapter(adapter);
		
		//添加监听事件
		addListener();
	}
}
