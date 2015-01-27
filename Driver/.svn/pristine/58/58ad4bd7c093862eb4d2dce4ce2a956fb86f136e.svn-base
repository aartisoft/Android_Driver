package com.netcabs.driver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.netcabs.asynctask.CheckAvailabilityAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;

public class SupportActivity extends Activity implements OnClickListener {
	private Button btnBack;
	private Button btnMsg;
	private Button btnContact;
	private boolean isLock = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_support);

		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		btnMsg = (Button) findViewById(R.id.btn_msg);
		btnContact = (Button) findViewById(R.id.btn_contact);
	}

	private void setListener() {
		btnBack.setOnClickListener(this);
		btnMsg.setOnClickListener(this);
		btnContact.setOnClickListener(this);
	}

	private void loadData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;

		case R.id.btn_msg:
			Intent intent_phone = new Intent(Intent.ACTION_DIAL);
			intent_phone.setData(Uri.parse("tel:"+ ConstantValues.LetsGo_SUPPORT_CONTACT));
			isLock = true;
			startActivity(intent_phone); 
			break;

		case R.id.btn_contact:
			isLock = true;
			startActivity(new Intent(SupportActivity.this, SupportDetailsActivity.class));
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		ConstantValues.isBack = false;
		isLock = true;
		super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		if (isLock) {
			isLock = false;
			//Make available
			if(InternetConnectivity.isConnectedToInternet(SupportActivity.this)) {
				new CheckAvailabilityAsyncTask(SupportActivity.this, false, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						if("2001".equals(result)) {
							//new CustomToast(MainMapActivity.this, "Came to available").showToast();
							Log.i("Available check", "Came to available");
						} else {
							new CustomToast(SupportActivity.this, "Data not found").showToast();
						}
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
				
			} else {
				new CustomToast(SupportActivity.this, ConstantValues.internetConnectionMsg).showToast();
			}
		} else {
			
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (isLock) {
			isLock = false;
		} else {
			DriverApp.getInstance().setLock(true);
			isLock = true;
			//Make unavailable
			if(InternetConnectivity.isConnectedToInternet(SupportActivity.this)) {
				new CheckAvailabilityAsyncTask(SupportActivity.this, false, new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						if("2001".equals(result)) {
							Log.i("Available check", "Gone to unavailable");
							new CustomToast(SupportActivity.this, "Gone to unavailable").showToast();
							
						} else {
							new CustomToast(SupportActivity.this, "Data not found").showToast();
						}
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
			} else {
				new CustomToast(SupportActivity.this, ConstantValues.internetConnectionMsg).showToast();
			}
		}
		super.onPause();
	}

}
