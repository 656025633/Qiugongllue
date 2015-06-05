package com.qiugonglue.activity;

import com.qiugongllue.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 我的足迹
 * @author dell
 *
 */
public class MineStepActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_step);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_step, menu);
		return true;
	}

}
