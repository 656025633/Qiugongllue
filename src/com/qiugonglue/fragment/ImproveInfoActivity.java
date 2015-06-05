//package com.qiugonglue.fragment;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.*;
//import com.ra.leopard.R;
//import com.ra.leopard.app.AppCtx;
//import com.ra.leopard.common.utils.SDCardHelper;
//import com.ra.leopard.common.utils.SharedPreferencesHelper;
//import com.squareup.picasso.Picasso;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
///**
// * Created by Administrator on 2015/4/29.
// */
//public class ImproveInfoActivity extends Activity {
//    private ImageView header_img;
//    private EditText editText_username;
//    private EditText editText_height;
//    private EditText editText_weight;
//    private RadioButton radio_man;
//    private RadioButton radio_woman;
//    private Button btn_done;
//
//    private String[] items = new String[] { "选择本地图片", "拍照" };
//    /*头像名称*/
//    private static final String IMAGE_FILE_NAME = "headerImg.jpg";
//
//    /* 请求码*/
//    private static final int IMAGE_REQUEST_CODE = 0;
//    private static final int CAMERA_REQUEST_CODE = 1;
//    private static final int RESULT_REQUEST_CODE = 2;
//    private String headerIcon;
//    private SDCardHelper helper;
//    private boolean isSDCardMounted;
//    private SharedPreferencesHelper prefs;
//    private String username;
//    private String sex;
//    private String height;
//    private String weight;
//    private Intent intent;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_improveinfo);
//        initView();
//        initData();
//        addListener();
//    }
//
//    /**
//     *  初始化视图
//     */
//    public void initView(){
//        header_img = (ImageView) findViewById(R.id.header_img);
//        editText_username = (EditText) findViewById(R.id.editText_username);
//        editText_height = (EditText) findViewById(R.id.editText_height);
//        editText_weight = (EditText) findViewById(R.id.editText_weight);
//        radio_man = (RadioButton) findViewById(R.id.radio_man);
//        radio_woman = (RadioButton) findViewById(R.id.radio_woman);
//        btn_done = (Button) findViewById(R.id.btn_done);
//        helper = new SDCardHelper();
//        isSDCardMounted = helper.isSDCardMounted();
//        prefs = AppCtx.getInstance().getPrefs();
//        intent = new Intent();
//        headerIcon = helper.getSDCardPrivateCacheDir(this) + File.separator + IMAGE_FILE_NAME;
//        File icon = new File(headerIcon);
//        if(icon.exists()){
//            Picasso.with(this).load(icon).into(header_img);
//        }
//    }
//
//    /**
//     *  初始化数据
//     */
//    public void initData(){
//        String useFirst = prefs.getUseFirst();
//        String isLegal = prefs.getIsLegal();
//        if (useFirst != null && isLegal.equals("yes")) {
//            intent.setClass(ImproveInfoActivity.this,UserActivity.class);
//            username = prefs.getUsername();
//            sex = prefs.getSex();
//            height = prefs.getHeight();
//            weight = prefs.getWeight();
//            if (username != null) {
//                editText_username.setText(username);
//            }
//            if (height != null) {
//                editText_height.setText(height);
//            }
//            if (weight != null) {
//                editText_weight.setText(weight);
//            }
//            if(sex!=null) {
//                if (sex.equals("男")) {
//                    radio_man.setChecked(true);
//                } else {
//                    radio_woman.setChecked(true);
//                }
//            }
//        }else{
//            intent.setClass(ImproveInfoActivity.this,MainActivity.class);
//        }
//    }
//
//    /**
//     *  添加监听
//     */
//    public void addListener(){
//        header_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog();
//            }
//        });
//        btn_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                username = editText_username.getText().toString().trim();
//                height = editText_height.getText().toString().trim();
//                weight = editText_weight.getText().toString().trim();
//                sex = "男";// 性别 默认 男
//                if(radio_woman.isChecked()){
//                    sex = "女";
//                }
//                prefs.setUserName(username);
//                prefs.setSex(sex);
//                if(Integer.parseInt(height)>250 || Integer.parseInt(height)<30){
//                    Toast.makeText(ImproveInfoActivity.this,"您填写的身高不合法，请重新填写",Toast.LENGTH_SHORT).show();
//                    return;
//                }else{
//                    prefs.setHeight(height);
//                }
//                if(Integer.parseInt(weight)>300 || Integer.parseInt(weight)<30){
//                    Toast.makeText(ImproveInfoActivity.this,"您填写的体重不合法，请重新填写",Toast.LENGTH_SHORT).show();
//                    return;
//                }else{
//                    prefs.setWeight(weight);
//                }
//                prefs.setIsLegal("yes");
//                startActivity(intent);
//                ImproveInfoActivity.this.finish();
//            }
//        });
//    }
//    /**
//     * 显示选择对话框
//     */
//    private void showDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("设置头像")
//                .setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                                startActivityForResult(intent1,
//                                        IMAGE_REQUEST_CODE);
//                                break;
//                            case 1:
//                                Intent intentFromCapture = new Intent(
//                                        MediaStore.ACTION_IMAGE_CAPTURE);
//                                // 判断存储卡是否可以用，可用进行存储
//                                if (isSDCardMounted) {
//                                    intentFromCapture.putExtra(
//                                            MediaStore.EXTRA_OUTPUT,
//                                            Uri.fromFile(new File(headerIcon)));
//                                }
//                                startActivityForResult(intentFromCapture,
//                                        CAMERA_REQUEST_CODE);
//                                break;
//                        }
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case IMAGE_REQUEST_CODE:
//                startPhotoZoom(data.getData());
//                break;
//            case CAMERA_REQUEST_CODE:
//                if (isSDCardMounted) {
//                    File tempFile = new File(
//                            headerIcon);
//                    startPhotoZoom(Uri.fromFile(tempFile));
//                } else {
//                    Toast.makeText(ImproveInfoActivity.this, "未找到存储卡，无法存储照片！",
//                            Toast.LENGTH_LONG).show();
//                }
//                break;
//            case RESULT_REQUEST_CODE:
//                if (data != null) {
//                    getImageToView(data);
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//    /**
//     * 裁剪图片方法实现
//     *
//     * @param uri
//     */
//    public void startPhotoZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 120);
//        intent.putExtra("outputY", 120);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, 2);
//    }
//
//    /**
//     * 保存裁剪之后的图片数据
//     *
//     * @param data
//     */
//    private void getImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            header_img.setImageBitmap(photo);
//            setPicToView(photo);//保存在SD卡中
//        }
//    }
//    private void setPicToView(Bitmap mBitmap) {
//        if (!isSDCardMounted) { // 检测sd是否可用
//            return;
//        }
//        FileOutputStream b = null;
//        File file = new File(headerIcon);
//        try {
//            b = new FileOutputStream(file);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                //关闭流
//                b.flush();
//                b.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}