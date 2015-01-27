package com.netcabs.customviews;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class CustomToast {

	private Context activity;
	private String strMsg;
	
	public CustomToast(Context activity, String strMsg) {
		this.activity = activity;
		this.strMsg = strMsg;
	}
	
	public void showToast() {
		Toast.makeText(activity, strMsg, Toast.LENGTH_SHORT).show();
	}
}
