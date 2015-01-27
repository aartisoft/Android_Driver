package com.netcabs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {
	
	Context mContext;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor spEditor;
	
	private final String REGISTRATION_KEY = "registration_key";
	
	public PreferenceUtil(Context mContext) {
		super();
		this.mContext = mContext;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public String getRegistrationKey() {
		return sharedPreferences.getString(REGISTRATION_KEY, "");  
	}
	
	public void setRegistrationKey(String reg_key) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(REGISTRATION_KEY, reg_key);
		spEditor.commit();
	}



	
	


}