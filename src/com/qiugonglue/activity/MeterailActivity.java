package com.qiugonglue.activity;

import com.qiugongllue.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MeterailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meterail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meterail, menu);
		return true;
	}

}
