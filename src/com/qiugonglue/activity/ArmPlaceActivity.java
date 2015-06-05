package com.qiugonglue.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.qiugongllue.R;
import com.qiugonglue.adapter.ArmPlaceAdapter;
import com.qiugonglue.adapter.ImagerPagerAdapter;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseActivity;
import com.qiugonglue.domain.ArmPlaceData;
import com.qiugonglue.domain.BannerBeen;
import com.qiugonglue.domain.ArmPlaceData.PlaceData.Command;
import com.qiugonglue.domain.BannerBeen.BannerData.Banner;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;
import com.qiugonglue.view.CustomIndicator;
import com.squareup.picasso.Picasso;

/**
 * 目的地详细信息页面
 * 
 * @author dell
 * 
 */
public class ArmPlaceActivity extends BaseActivity implements OnClickListener {

	private ViewPager gongnengPager;
	private ViewPager viewPager_banner;
	private ListView list_armplace;

	private CustomIndicator indicator_banner;

	private ImageView meishi, jingdian, dangdijingyan, gouwu, jishifanyi,
			dangditianqi, huilvhuansuan, shijieshizhong;

	private ImageView zhusu, jiaotong, tuijianxingcheng, jianjie, shiyongxinxi,
			shoucang;

	private RequestQueue queue;
	private StringRequest bannerRequest;
	private StringRequest armPlaceRequest;
	private int count;

	@Override
	public void initView() {
		setContentView(R.layout.activity_armplace);
		gongnengPager = (ViewPager) findViewById(R.id.viewPager_gongneng);

		viewPager_banner = (ViewPager) findViewById(R.id.viewPager_banner);
		indicator_banner = (CustomIndicator) findViewById(R.id.indicator_banner);
		list_armplace = (ListView) findViewById(R.id.list_armplace);
	}

	@Override
	public void initData() {
		queue = AppCtx.getInstance().getRequestQueue();
		initBannerView();
		initGongNengView();
		initArmPlaceView();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.meishi:
			break;
		case R.id.shijieshizhong:
			intent.setClass(ArmPlaceActivity.this, WorldClockActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void addListener() {
		meishi.setOnClickListener(this);
		jingdian.setOnClickListener(this);
		dangdijingyan.setOnClickListener(this);
		gouwu.setOnClickListener(this);
		jishifanyi.setOnClickListener(this);
		dangditianqi.setOnClickListener(this);
		huilvhuansuan.setOnClickListener(this);
		shijieshizhong.setOnClickListener(this);

		zhusu.setOnClickListener(this);
		jiaotong.setOnClickListener(this);
		tuijianxingcheng.setOnClickListener(this);
		jianjie.setOnClickListener(this);
		shiyongxinxi.setOnClickListener(this);
		shoucang.setOnClickListener(this);

		viewPager_banner.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				indicator_banner.setCurrentPosition(position % count);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	
	private void initBannerView() {
		bannerRequest = new StringRequest(GLApi.BANNER,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 处理数据
						BannerBeen banner = GsonTools.jsonToBean(response,
								BannerBeen.class);

						loadBanner(banner);
					}
				}, new ErrorResponseListener(this));
		queue.add(bannerRequest);
	}

	protected void loadBanner(BannerBeen banner) {
		// 将数据放入banner的viewpager
		List<Banner> banners = banner.data.banner_list;
		count = banners.size();
		indicator_banner.setCount(count);
		List<View> listViews = new ArrayList<View>();
		for (int i = 0; i < banners.size(); i++) {
			ImageView imageView = new ImageView(this);
			Picasso.with(this).load(banners.get(i).imageURL).into(imageView);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			final String linkUrl = banners.get(i).actionURL;
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 传递到图片详细连接详细页面
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("linkUrl", linkUrl);
					intent.putExtras(bundle);
					intent.setClass(ArmPlaceActivity.this, DetailActivity.class);
					startActivity(intent);
				}
			});
			listViews.add(imageView);
		}
		ImagerPagerAdapter imageAdapter = new ImagerPagerAdapter(listViews);
		Toast.makeText(this, listViews.size() + "个图片", Toast.LENGTH_LONG)
				.show();

		viewPager_banner.setAdapter(imageAdapter);

	}

	private void initGongNengView() {
		List<View> list = new ArrayList<View>();
		View view = LayoutInflater.from(this).inflate(
				R.layout.viewpager_recommand, null);
		meishi = (ImageView) view.findViewById(R.id.meishi);
		jingdian = (ImageView) view.findViewById(R.id.jingdian);
		dangdijingyan = (ImageView) view.findViewById(R.id.dangdijingyan);
		gouwu = (ImageView) view.findViewById(R.id.gouwu);
		jishifanyi = (ImageView) view.findViewById(R.id.jishifanyi);
		dangditianqi = (ImageView) view.findViewById(R.id.dangditianqi);
		huilvhuansuan = (ImageView) view.findViewById(R.id.huilvhuansuan);
		shijieshizhong = (ImageView) view.findViewById(R.id.shijieshizhong);

		View view2 = LayoutInflater.from(this).inflate(
				R.layout.viewpager_recommand2, null);

		zhusu = (ImageView) view2.findViewById(R.id.zhusu);
		jiaotong = (ImageView) view2.findViewById(R.id.jiaotong);
		tuijianxingcheng = (ImageView) view2
				.findViewById(R.id.tuijianxingcheng);
		jianjie = (ImageView) view2.findViewById(R.id.jianjie);
		shiyongxinxi = (ImageView) view2.findViewById(R.id.shiyongxinxi);
		shoucang = (ImageView) view2.findViewById(R.id.shoucang);

		list.add(view);
		list.add(view2);
		ImagerPagerAdapter adapter = new ImagerPagerAdapter(list);
		gongnengPager.setAdapter(adapter);
	}

	// 初始化目的地数据
	private void initArmPlaceView() {
		armPlaceRequest = new StringRequest(GLApi.XINQI,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 处理数据
						ArmPlaceData armplace = GsonTools.jsonToBean(response,
								ArmPlaceData.class);
						loadArmPlaceData(armplace);
					}
				}, new ErrorResponseListener(this));
		queue.add(armPlaceRequest);
	}

	// 将数据填充到视图当中
	protected void loadArmPlaceData(ArmPlaceData armplace) {
		// 将数据填充到视图当中
		if (armplace == null) {
			throw new IllegalArgumentException(" armplace is null ");
		}
		List<Command> cms_list = armplace.data.cms_list;
		Toast.makeText(this, "...."+cms_list.size(), Toast.LENGTH_LONG).show();
		
		ArmPlaceAdapter armPlaceAdapter = new ArmPlaceAdapter(this, cms_list);
		list_armplace.setAdapter(armPlaceAdapter);
	}
}
