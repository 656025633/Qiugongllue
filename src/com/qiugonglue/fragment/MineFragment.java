package com.qiugonglue.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.qiugongllue.LoginActivity;
import com.qiugongllue.R;
import com.qiugonglue.activity.MeterailActivity;
import com.qiugonglue.activity.MineCommentActivity;
import com.qiugonglue.activity.MineShareActivity;
import com.qiugonglue.activity.MineStepActivity;
import com.qiugonglue.activity.OderListActivity;
import com.qiugonglue.activity.SettingActivity;
import com.qiugonglue.app.AppCtx;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.utils.SDCardHelper;
import com.qiugonglue.utils.SharedPreferencesHelper;

/**
 * 我的页面的Fragment
 * @author dell
 *
 */
public class MineFragment extends BaseFragment {

	private View view;

	@ViewInject(R.id.img_setting_mine)
	private ImageView setting_mine;// 设置
	@ViewInject(R.id.img_icon_mine)
	private ImageView icon_mine;// 头像

	@ViewInject(R.id.txt_sign_mine)
	private TextView sign_mine;
	@ViewInject(R.id.txt_order_mine)
	private TextView order_mine;
	@ViewInject(R.id.txt_share_mine)
	private TextView share_mine;
	@ViewInject(R.id.txt_comment_mine)
	private TextView txt_comment;
	@ViewInject(R.id.txt_step_mine)
	private TextView txt_step;
	@ViewInject(R.id.txt_nickname_mine)
	private TextView nickname_mine;
	@ViewInject(R.id.txt_follow_mine)
	private TextView follow_mine;
	@ViewInject(R.id.number_comment)
	private TextView number_commant;
	@ViewInject(R.id.number_step)
	private TextView number_step;

	private AppCtx appCtx = AppCtx.getInstance();

	private SharedPreferencesHelper prefs = appCtx.getPrefs();

	boolean signin;// 是否已登录

	@Override
	public View initView(LayoutInflater inflater) {
		// 1、检查登陆状态,是否已经登陆
		signin = prefs.getState();
		// 2、根据检查结果显示布局
		if (!signin) {
			Intent intent = new Intent();
			intent.setClass(MineFragment.this.getActivity(), LoginActivity.class);
			startActivity(intent);
			// 1)未登陆 显示未登陆的状态
//			view = inflater.inflate(R.layout.activity_login, null);
		} else {
			// 2)已登陆 显示已登陆的状态
			view = inflater.inflate(R.layout.fragment_mine, null);
		}

		ViewUtils.inject(this, view);// 添加注解
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (!signin) {
			// 初始化未登陆的页面数据
		} else {
			// 初始化已登录的页面数据
			String comment = prefs.getCommentCount();// 评论
			String step = prefs.getSubline();// 地点
			String fans = prefs.getFansCount() == "fans_count" ? "0" : prefs
					.getFansCount();// 粉丝
			String follow = prefs.getFollowCount() == "follow_count" ? "0"
					: prefs.getFollowCount();// 关注
			number_commant.setText(comment == "comment_count" ? "0" : comment);
			number_step.setText(step == "subline" ? "0" : step);
			follow_mine.setText("关注" + follow + "/粉丝" + fans);
		}
	}

	private static final int SETTING = 101;// 设置
	private static final int SIGN = 102;// 签名
	private static final int COMMENT = 103;// 评论
	private static final int STEP = 104;// 足迹
	private static final int CAMERA = 106;// 拍照
	private static final int IMAGE_CODE = 111;
	private static final int CAMERA_CODE = 112;
	private static final int PHOTO_RESULT_CODE = 113;

	@OnClick({ R.id.img_setting_mine, R.id.img_icon_mine, R.id.txt_order_mine,
			R.id.txt_sign_mine, R.id.txt_share_mine, R.id.txt_comment_mine,
			R.id.txt_step_mine, R.id.txt_photo_dim, R.id.txt_photo_camera })
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.img_setting_mine:
			intent.setClass(getActivity(), SettingActivity.class);
			startActivityForResult(intent, SETTING);
			break;
		case R.id.img_icon_mine:
			photoDialog(getActivity());
			break;
		case R.id.txt_sign_mine:
			intent.setClass(getActivity(), MeterailActivity.class);
			startActivityForResult(intent, SIGN);
			break;
		case R.id.txt_order_mine:
			intent.setClass(getActivity(), OderListActivity.class);
			startActivity(intent);
			break;
		case R.id.txt_share_mine:
			intent.setClass(getActivity(), MineShareActivity.class);
			startActivity(intent);
			break;
		case R.id.txt_comment_mine:
			intent.setClass(getActivity(), MineCommentActivity.class);
			startActivityForResult(intent, COMMENT);
			break;
		case R.id.txt_step_mine:
			intent.setClass(getActivity(), MineStepActivity.class);
			startActivityForResult(intent, STEP);
			break;
		case R.id.txt_nickname_mine:

			break;
		case R.id.txt_follow_mine:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getActivity();
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case SETTING:
				break;
			case SIGN:
				break;
			case COMMENT:
				break;
			case STEP:
				break;
			case IMAGE_CODE:
				if (data != null) {
					// if(data.hasExtra("data")){
					startPhotoZoom(data.getData());
					// }
				}
				break;
			case CAMERA:
				if (isMounted) {// SD卡已经安装
					startPhotoZoom(Uri.fromFile(headImage));
				} else {
					Toast.makeText(getActivity(), "未找到可用的SD卡",
							Toast.LENGTH_LONG).show();
				}
				break;
			case PHOTO_RESULT_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
	}

	/**
	 * 对话框
	 */

	public String[] items = new String[] { "选择本地图片", "拍照" };
	public Intent intent;
	public String type = "image/*";
	public String fileName = "headImage.png";

	public String directory;
	public File headImage;
	public boolean isMounted;

	public void photoDialog(final Context context) {
		isMounted = SDCardHelper.isSDCardMounted();
		directory = SDCardHelper.getSDCardPrivateCacheDir(context)
				+ File.separator + fileName;
		headImage = new File(directory);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("更换头像");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//选择更换头像的条目
				switch (which) {
				case 0:
					intent = new Intent(Intent.ACTION_PICK, null);
					intent.setDataAndType(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI, type);
					startActivityForResult(intent, IMAGE_CODE);
					break;

				case 1:
					intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 判断SD存储卡是否已经绑定
					if (isMounted) {
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(headImage));
						startActivityForResult(intent, CAMERA_CODE);
					} else {
						Toast.makeText(context, "SD卡未绑定", Toast.LENGTH_LONG)
								.show();
					}
					break;
				}
			}
		});

		// 取消按钮
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.create().show();
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, type);
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 120);
		intent.putExtra("outputY", 120);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_RESULT_CODE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param data
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			icon_mine.setImageBitmap(photo);
			savePic2SDcard(photo);// 保存在SD卡中
		}
	}

	private void savePic2SDcard(Bitmap mBitmap) {
		if (!isMounted) { // 检测SD卡是否可用
			return;
		}
		FileOutputStream b = null;
		try {
			b = new FileOutputStream(headImage);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
