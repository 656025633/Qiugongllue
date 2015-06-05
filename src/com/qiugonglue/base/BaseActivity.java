package com.qiugonglue.base;


import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doCreate();
	}

	private void doCreate() {
		initView();
		initData();
		addListener();
	}

	public abstract void initView();

	public abstract void addListener();

	public abstract void initData();
}
