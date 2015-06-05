package com.qiugonglue.activity;

import com.qiugongllue.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 我的订单
 * @author dell
 *
 */
public class OderListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oder_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.oder_list, menu);
		return true;
	}

}
