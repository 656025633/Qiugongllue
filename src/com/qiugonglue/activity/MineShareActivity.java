package com.qiugonglue.activity;

import com.qiugongllue.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 我的分享
 * @author dell
 *
 */
public class MineShareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_share);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_share, menu);
		return true;
	}

}
