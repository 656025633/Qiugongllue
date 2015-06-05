package com.qiugonglue.view;

import com.qiugongllue.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class NumberView extends FrameLayout {
	private TextView textView_number;
	private ImageView imageView_icon;

	public NumberView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.numberview,
				this);
		textView_number = (TextView) view.findViewById(R.id.textView_number);
		imageView_icon = (ImageView) view.findViewById(R.id.imageView_icon);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.NumberView);
		if (array != null) {
			int imageId = array.getResourceId(R.styleable.NumberView_src, -1);
			setImageRes(imageId);
			String num = array.getString(R.styleable.NumberView_number);
			setTextNumber(num);
			int numColor = array.getColor(R.styleable.NumberView_numberColor,
					Color.RED);
			setTextNumberColor(numColor);
		}
	}

	public void setTextNumber(String text) {
		textView_number.setText(text);
	}

	public void setTextNumberColor(int color) {
		textView_number.setTextColor(color);
	}

	public String getTextNumber() {
		return textView_number.getText().toString();
	}

	public void setImageRes(int resId) {
		imageView_icon.setImageResource(resId);
	}

	public void hiddenTextNumber() {
		textView_number.setVisibility(View.INVISIBLE);
	}

	public boolean isTextNumberHidden() {
		if (textView_number.getVisibility() == View.VISIBLE) {
			return false;
		}
		return true;
	}

	public void setOnIconClickListener(OnClickListener l) {
		textView_number.setOnClickListener(l);
	}
}
