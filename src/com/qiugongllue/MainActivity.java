package com.qiugongllue;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qiugonglue.fragment.DynamicFragment;
import com.qiugonglue.fragment.GroupchatFragment;
import com.qiugonglue.fragment.HomeFragment;
import com.qiugonglue.fragment.MineFragment;
import com.qiugonglue.fragment.ServiceFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {

	@ViewInject(R.id.main_radio)
	private RadioGroup radioGroup;
	
	private int checkedId = R.id.rb_recommand;
	
	private HomeFragment homeFragment ;//推荐
	private ServiceFragment serviceFragment ;//服务
	private GroupchatFragment groupFragment ;//群聊
	private DynamicFragment dynamicFragment ;//动态
	private MineFragment mineFragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		homeFragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
	
		initView();//初始化视图
		initData();//初始化数据
		addListener();//添加监听
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void initView() {
		// TODO 初始化界面
		
	}

	public void addListener() {
		
		// TODO 控件添加监听
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 判断 哪个RadioButton被点击了
				switch(checkedId){
					case R.id.rb_recommand:
						checkedId = 0;
						getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
						break;
					case R.id.rb_service:
						serviceFragment = new ServiceFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.container, serviceFragment).commit();
						checkedId = 1;
						break;
					case R.id.rb_groupchat:
						groupFragment = new GroupchatFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.container, groupFragment).commit();
						checkedId = 2;
						break;
					case R.id.rb_dynamic:
						checkedId = 3;
						dynamicFragment = new DynamicFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.container, dynamicFragment).commit();
						break;
					case R.id.rb_mine:
						checkedId = 4;
						mineFragment = new MineFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.container, mineFragment).commit();
						break;
				}
			}
		});
		radioGroup.check(checkedId);
	}

	public void initData() {
		// TODO 初始化数据
		ViewUtils.inject(this);
	}
}
