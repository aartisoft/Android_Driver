package com.netcabs.driver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.netcabs.asynctask.CheckAvailabilityAsyncTask;
import com.netcabs.asynctask.ContactEmailAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;

public class SupportDetailsActivity extends Activity implements OnClickListener {

	private Button btnBack;
	private Button btnSend;
	private EditText edTxtSubject;
	private EditText edTxtMessage;
	private RelativeLayout relativeLayout;
	private boolean isLock = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_support_details);
		
		initViews();
		setListerner();
		loadData();
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		btnSend = (Button) findViewById(R.id.btn_send);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		edTxtSubject = (EditText) findViewById(R.id.ed_txt_subject);
		edTxtMessage = (EditText) findViewById(R.id.ed_txt_msg);
	}

	private void setListerner() {
		btnBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		relativeLayout.setOnClickListener(this);
	}

	private void loadData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			DriverApp.getInstance().hideKeyboard(SupportDetailsActivity.this, v);
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_send:
			if(!edTxtSubject.getText().toString().trim().equalsIgnoreCase("") && !edTxtMessage.getText().toString().trim().equalsIgnoreCase("")) {
				if(InternetConnectivity.isConnectedToInternet(SupportDetailsActivity.this)) {
					new ContactEmailAsyncTask(SupportDetailsActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									new CustomToast(SupportDetailsActivity.this, "Email is sent to 13 NetCabs").showToast();
								} else {
									new CustomToast(SupportDetailsActivity.this, "Please try again...").showToast();
								}
							} catch (Exception e) {
								new CustomToast(SupportDetailsActivity.this, "" + e.getMessage()).showToast();
							}
							
						}
					}).execute("31", DriverApp.getInstance().getProfileInfo().getId(), edTxtSubject.getText().toString().trim(), edTxtMessage.getText().toString().trim());
			
				} else {
					new CustomToast(SupportDetailsActivity.this, ConstantValues.internetConnectionMsg).showToast();
				}
			} else {
				if(edTxtSubject.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtSubject.setError("Required");
				}
				if(edTxtMessage.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtMessage.setError("Required");
				}
			}
			
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		isLock = true;
		super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		if (isLock) {
			isLock = false;
			//Make available
			if(InternetConnectivity.isConnectedToInternet(SupportDetailsActivity.this)) {
				new CheckAvailabilityAsyncTask(SupportDetailsActivity.this, false, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						if("2001".equals(result)) {
							//new CustomToast(MainMapActivity.this, "Came to available").showToast();
							Log.i("Available check", "Came to available");
						} else {
							new CustomToast(SupportDetailsActivity.this, "Data not found").showToast();
						}
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
				
			} else {
				new CustomToast(SupportDetailsActivity.this, ConstantValues.internetConnectionMsg).showToast();
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
			if(InternetConnectivity.isConnectedToInternet(SupportDetailsActivity.this)) {
				new CheckAvailabilityAsyncTask(SupportDetailsActivity.this, false, new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						if("2001".equals(result)) {
							Log.i("Available check", "Gone to unavailable");
							new CustomToast(SupportDetailsActivity.this, "Gone to unavailable").showToast();
							
						} else {
							new CustomToast(SupportDetailsActivity.this, "Data not found").showToast();
						}
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
			} else {
				new CustomToast(SupportDetailsActivity.this, ConstantValues.internetConnectionMsg).showToast();
			}
		}
		
		super.onPause();
	}

}
