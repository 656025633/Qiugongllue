package com.qiugonglue.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{

	private List<Fragment> list;
	
	private String [] title;
	public FragmentAdapter(FragmentManager fm,List<Fragment> list,String [] title) {
		super(fm);
		this.list = list;
		this.title = title;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}
	
	
	@Override
	public CharSequence getPageTitle(int position) {
		return title[position];
	}

}
