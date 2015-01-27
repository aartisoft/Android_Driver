package com.netcabs.customviews;

import android.app.Activity;
import android.util.Log;

public class CustomLog {
	
	private Activity activity;
	private String strTag;
	private String strMsg;
	
	public CustomLog(Activity activity, String strTag, String strMsg) {
		this.activity = activity;
		this.strTag = strTag;
		this.strMsg = strMsg;
	}
	
	public void showLogI() {
		Log.i(strTag, strMsg);
	}
	
	public void showLogE() {
		Log.e(strTag, strMsg);
	}
}
