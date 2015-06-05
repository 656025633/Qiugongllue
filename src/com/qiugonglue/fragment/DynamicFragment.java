package com.qiugonglue.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qiugongllue.R;
import com.qiugonglue.activity.ErrorResponseListener;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.domain.TagData;
import com.qiugonglue.utils.GLApi;
import com.qiugonglue.utils.GsonTools;
import com.qiugonglue.utils.SDCardHelper;

//FindFragment
public class DynamicFragment extends BaseFragment {

	private View view;
	private HorizontalScrollView hScrollView;
	private int mCurrentCheckedRadioLeft;
	private ImageView ImageView_mian_line;
	private ImageView imageView_Dynamic_addDynamic;
	private RadioGroup radioGroup;
	private int column;
	private ViewPager viewPager_main;

	private RequestQueue requestQueue;
	private RadioButton button = null;
	private List<Fragment> Flist;
	private MyFragmentAdapter adapter;
	private Intent intent;
	private boolean isMounted;
	private File headImage;
	private String fileName = "image.jpg";
	private String[] items = new String[] { "从相册选择", "拍照", "取消" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initView(inflater);
		return view;
	}

	// 初始化数据
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_dynamic, null);
		requestQueue = Volley.newRequestQueue(getActivity());
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup_title);
		viewPager_main = (ViewPager) view.findViewById(R.id.viewPager_main);
		hScrollView = (HorizontalScrollView) view
				.findViewById(R.id.ScrollView_title);
		ImageView_mian_line = (ImageView) view
				.findViewById(R.id.ImageView_mian_line);
		imageView_Dynamic_addDynamic = (ImageView) view
				.findViewById(R.id.imageView_Dynamic_addDynamic);
		Flist = new ArrayList<Fragment>();

		// 获得标题
		getTitleData(GLApi.DYNAMICBAR);

		adapter = new MyFragmentAdapter(getChildFragmentManager(), Flist);
		viewPager_main.setAdapter(adapter);
		// 添加ViewPager滑动监听
		viewPager_main
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int i, float v, int i1) {
					}

					@Override
					public void onPageSelected(int position) {
						// pager对应的标题变成红色,靠左边栏显示
						// 1让标题变成红色
						initTitleText();
						button = (RadioButton) radioGroup.getChildAt(position);
						if (button != null) {
							button.setTextColor(Color.RED);
							// 2 滑动
							hScrollView.scrollTo(column * position, 0);
							// 3动画效果
						}
					}

					@Override
					public void onPageScrollStateChanged(int i) {
					}
				});

		// 添加发布按钮
		imageView_Dynamic_addDynamic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				isMounted = SDCardHelper.isSDCardMounted();
				String directory = SDCardHelper
						.getSDCardPrivateCacheDir(getActivity())
						+ File.separator + fileName;
				headImage = new File(directory);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("选择图片");
				builder.setIcon(R.drawable.ic_launcher);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 选择更换头像的条目
						switch (which) {
						case 0:
							intent = new Intent(Intent.ACTION_PICK, null);
							// String type = getMIMEType(file);
							intent.setDataAndType(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									null);
							startActivityForResult(intent, 299);
							break;

						case 1:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断SD存储卡是否已经绑定
							if (isMounted) {
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(headImage));
								startActivityForResult(intent, 299);
							} else {
								Toast.makeText(getActivity(), "SD卡未绑定",
										Toast.LENGTH_LONG).show();
							}
							break;

						case 2:
							break;
						}

					}

				});
				builder.create().show();

			}
		});
		return view;

	}

	// 初始化标题颜色
	private void initTitleText() {
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			((RadioButton) radioGroup.getChildAt(i)).setTextColor(Color.BLACK);
		}
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}

	// 得到标题
	public void getTitleData(String URL) {
		StringRequest request = new StringRequest(URL, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				loadTitle(response);
			}
		}, new ErrorResponseListener(getActivity()));
		requestQueue.add(request);

	}

	/**
	 * 初始化标题栏数据
	 * 
	 * @param response
	 */
	protected void loadTitle(String response) {
		if (response == null)
			return;

		// 初始化导航标题
		final TagData tagData = GsonTools.jsonToBean(response, TagData.class);

		for (int i = 0; i < tagData.data.size(); i++) {
			String title = tagData.data.get(i).tag_name;
			button = new RadioButton(getActivity(), null, R.style.radioButton);
			button.setText(title);
			button.setWidth(120);

			if (i == 0) {
				button.setTextColor(Color.rgb(194, 41, 10));
			} else {
				button.setTextColor(Color.rgb(37, 37, 37));
			}
			button.setGravity(Gravity.CENTER);
			button.setTextSize(18);
			column = button.getWidth();
			// 添加标题监听并添加动画
			button.setOnClickListener(new OnClickListener() {
				Animation _TrAnimation = null;
				@Override
				public void onClick(View view) {
					RadioButton rbutton = (RadioButton) view;
					for (int j = 0; j < tagData.data.size(); j++) {
						if (rbutton == radioGroup.getChildAt(j)) {
							hScrollView.scrollTo(j * 120, 0);
							column = view.getWidth();
							_TrAnimation = new TranslateAnimation(
									mCurrentCheckedRadioLeft, column * j, 0f,
									0f);

							_TrAnimation.setFillAfter(true);
							_TrAnimation.setDuration(100);
							ImageView_mian_line.startAnimation(_TrAnimation);
							mCurrentCheckedRadioLeft = column * j;
							hScrollView.smoothScrollTo(
									(int) mCurrentCheckedRadioLeft
											- (int) column, 0);

							viewPager_main.setCurrentItem(j, false);
						}
					}
				}
			});

			DynamicViewPagerFragment dvpFragment = new DynamicViewPagerFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("index", i);
			dvpFragment.setArguments(bundle);

			Toast.makeText(getActivity(), i+"", Toast.LENGTH_SHORT).show();
			Flist.add(dvpFragment);
			radioGroup.addView(button);
		}
		adapter.notifyDataSetChanged();

	}
}
