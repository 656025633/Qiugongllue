package com.qiugongllue;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.qiugonglue.base.BaseActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tpl.OnLoginListener;
import cn.sharesdk.tpl.SignupPage;

/**
 * 登陆页面
 * 
 * @author dell
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
		PlatformActionListener {

	private ImageView back;// 返回
	private Button btn_sina;// 新浪登陆
	private Button btn_tencent;// 腾讯登陆
	private Button btn_login;// 登陆按钮

	private EditText edt_username;// 账号
	private EditText edt_pwd;// 密码
	
	private TextView txt_register;// 注册

	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR = 3;
	private static final int MSG_AUTH_COMPLETE = 4;
	
	private OnLoginListener signupListener;
	

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_AUTH_CANCEL:
				// 取消授权
				Toast.makeText(LoginActivity.this, "取消授权", Toast.LENGTH_SHORT)
						.show();
				break;
			case MSG_AUTH_ERROR:
				// 授权失败
				Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT)
						.show();
				break;
			case MSG_AUTH_COMPLETE:
				// 授权成功
				Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT)
						.show();
				Object[] objs = (Object[]) msg.obj;
				String platform = (String) objs[0];
				HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
				
				System.out.println("平台:"+platform);
				System.out.println("返回的res:"+res);
				
				if (signupListener != null
						&& signupListener.onSignin(platform, res)) {
					SignupPage signupPage = new SignupPage();
					signupPage.setOnLoginListener(signupListener);
					signupPage.setPlatform(platform);
					signupPage.show(LoginActivity.this, null);
				}

				break;
			}
		};
	};

	@Override
	public void initView() {
		setContentView(R.layout.activity_login);
		findView();
		Toast.makeText(this, "dfdsd", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void initData() {
		Toast.makeText(this, "dfdsd", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "dfdsd", Toast.LENGTH_LONG).show();
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_sina:
			// TODO 微博登陆
			Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			authorize(sina);
			break;
		case R.id.btn_tencent:
			// TODO QQ登陆
			Toast.makeText(this, "点击了腾讯", Toast.LENGTH_LONG).show();
			Platform tencent = ShareSDK.getPlatform(QQ.NAME);
			authorize(tencent);
			break;
		case R.id.btn_login:
			login();
			break;
		case R.id.txt_register:
			register();
			break;
		}
	}
	
	@Override
	public void addListener() {
		back.setOnClickListener(this);
		btn_sina.setOnClickListener(this);
		btn_tencent.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		txt_register.setOnClickListener(this);
		Toast.makeText(this, "dfdsd", Toast.LENGTH_LONG).show();
		
	}

	/**
	 * 登陆操作
	 */
	private void login() {
		String username = edt_username.getText().toString().trim();
		String pwd = edt_pwd.getText().toString().trim();
		if(username.length()>0&&pwd.length()>0){
			
		}else {
			Toast.makeText(this, "请将账号和密码填写完整", Toast.LENGTH_LONG).show();
		}
	}

	private void register() {
		// TODO 注册操作

	}

	private void findView() {
		back = (ImageView) findViewById(R.id.btn_back);
		btn_sina = (Button) findViewById(R.id.btn_sina);
		btn_tencent = (Button) findViewById(R.id.btn_tencent);
		btn_login = (Button) findViewById(R.id.btn_login);
		txt_register = (TextView) findViewById(R.id.txt_register);
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_pwd = (EditText) findViewById(R.id.edt_pwd);
	}

	private void authorize(Platform plat) {
		if (plat == null) {
			throw new IllegalArgumentException(" Platform is null ");
			// popupOthers();//原本是展示其他登陆框的
			// return;
		}

		plat.setPlatformActionListener(this);
		// 关闭SSO授权
		plat.SSOSetting(true);
		plat.showUser(null);
	}

	@Override
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_CANCEL);
		}
	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = new Message();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] { platform.getName(), res };
			handler.sendMessage(msg);
		}
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_ERROR);
		}
		t.printStackTrace();
	}
	
	/** 设置授权回调，用于判断是否进入注册 */
	public void setOnLoginListener(OnLoginListener l) {
		this.signupListener = l;
	}
}
