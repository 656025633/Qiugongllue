package com.qiugonglue.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> lruCache = null;
	private static BitmapCache imageCache = null;
	private Map<String, SoftReference<Bitmap>> softMap = new HashMap<String, SoftReference<Bitmap>>();

	private BitmapCache() {
		int memoryCache = (int) (Runtime.getRuntime().maxMemory() / 8);
		lruCache = new LruCache<String, Bitmap>(memoryCache) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				if (evicted) {
					SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(
							oldValue);
					softMap.put(key, softReference);
				}
			};
		};
	}

	public static BitmapCache getInstance() {
		if (imageCache == null) {
			return new BitmapCache();
		}
		return imageCache;
	}

	@Override
	public Bitmap getBitmap(String url) {
		Bitmap bm = null;
		bm = lruCache.get(url);
		if (bm != null) {
			return bm;
		} else {
			SoftReference<Bitmap> softReference = softMap.get(url);
			if (softReference != null) {
				bm = softReference.get();
				if (bm != null) {
					softMap.remove(bm);
					lruCache.put(url, bm);
					return bm;
				}
			}
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		lruCache.put(url, bitmap);
	}

}
