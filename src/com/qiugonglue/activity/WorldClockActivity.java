package com.qiugonglue.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.qiugongllue.R;
import com.qiugonglue.base.BaseActivity;

/**
 * 世界时钟
 * 
 * @author dell
 * 
 */
public class WorldClockActivity extends BaseActivity {

	private SurfaceView surfaceView_beijing;
	private SurfaceHolder surfaceHolder;

	public void initView() {
		setContentView(R.layout.activity_worldclock);
		surfaceView_beijing = (SurfaceView) findViewById(R.id.surfaceView_beijing);
		surfaceHolder = surfaceView_beijing.getHolder();
	}

	public void initData() {
		
	}

	@Override
	public void addListener() {
		surfaceHolder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				new MyThread().start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}
		});
	}
	
	class MyThread extends Thread {
		private Canvas canvas;

		@Override
		public void run() {
			super.run();
			Bitmap bitmapBg = null;//背景表盘
			Bitmap bitmapHour = null;//时针
			Bitmap bitmapMin = null;//分针
			Bitmap bitmapSe = null;//秒针
			
			AssetManager assetManager = getResources().getAssets();

			try {
				bitmapBg = BitmapFactory.decodeStream(assetManager
						.open("clock_dial.png"));

				bitmapHour = BitmapFactory.decodeStream(assetManager
						.open("clock_hour.png"));
				bitmapMin = BitmapFactory.decodeStream(assetManager
						.open("clock_minute.png"));
				bitmapSe = BitmapFactory.decodeStream(assetManager
						.open("clockgoog_minute.png"));
				
				
				Paint paint = new Paint();//画笔对象

				Matrix matriSe = new Matrix();
				Matrix matriMin = new Matrix();
				Matrix matriHou = new Matrix();
				matriSe.postTranslate(220, 80);
				matriMin.postTranslate(220, 80);
				matriHou.postTranslate(220, 80);
			    
				while (true) {
					paint.setAntiAlias(true);
					canvas = surfaceHolder.lockCanvas();
					canvas.drawColor(Color.WHITE);
					
					/**
					 * 秒针的设置
					 */
					matriSe.postRotate(6, 244, 224);
					
					/**
					 * 分针的设置
					 */
					matriMin.postRotate(3, 244, 224);
					
					/**
					 * 时针的设置
					 */
					matriHou.postRotate(1, 244, 224);
					
					canvas.drawBitmap(bitmapBg, 100, 80, paint);
					canvas.drawBitmap(bitmapHour, matriHou, paint);
					canvas.drawBitmap(bitmapMin, matriMin, paint);
					canvas.drawBitmap(bitmapSe, matriSe, paint);
					
					surfaceHolder.unlockCanvasAndPost(canvas);
					
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
