package com.qiugonglue.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qiugongllue.R;
import com.qiugonglue.base.BaseFragment;
import com.qiugonglue.utils.GLApi;

public class ServiceFragment extends BaseFragment{
	
	private View view;
	@ViewInject(R.id.webview_fragment_service)
	private WebView webview;
	
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_service, null);
		ViewUtils.inject(this,view);
		return view;
	}
	
	@Override
	public void initData(Bundle savedInstanceState) {
		
		WebSettings settings = webview.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		settings.setAppCacheEnabled(true);
		settings.setJavaScriptEnabled(true);//设置支持Javascript
		webview.loadUrl(GLApi.SERVICE);
		//设置在本WebView当中显示点击的链接
		webview.setWebViewClient(new WebViewClient());
	}
}
