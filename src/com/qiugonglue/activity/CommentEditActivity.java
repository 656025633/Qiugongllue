package com.qiugonglue.activity;

import com.qiugongllue.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class CommentEditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	public void ClickButton(View v)  {
		switch (v.getId()) {
		case R.id.imageView_commentEdit_back:
			this.finish();
			break;
		case R.id.imageView_commentEdit_send:
			Toast.makeText(this, "你的评论已经成功发送", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
