package com.netcabs.driver;

import android.app.Activity;
import android.content.Intent;
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

public class MainMenuActivity extends Activity implements OnClickListener {
	private boolean isLock = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main_menu);

		initViews();
		setListener();
		loadData();
		
	}

	private void initViews() {
		
	}

	private void setListener() {
		((Button) findViewById(R.id.btn_map)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_start_fare)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_settings)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_support)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_logout)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_past_trips)).setOnClickListener(this);
	}

	private void loadData() {
		try {
			if(DriverApp.getInstance().getDriverInfo().getTaxiId() != null) {
				if(InternetConnectivity.isConnectedToInternet(MainMenuActivity.this)) {
					new CheckAvailabilityAsyncTask(MainMenuActivity.this, false, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									//new CustomToast(MainMapActivity.this, "Came to available").showToast();
									Log.i("Available check", "Came to available");
								} else {
									new CustomToast(MainMenuActivity.this, "Data not found").showToast();
								}
							} catch (Exception e) {
								new CustomToast(MainMenuActivity.this, "" + e.getMessage()).showToast();
							}
							
						}
						
					}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
					
				} else {
					new CustomToast(MainMenuActivity.this, ConstantValues.internetConnectionMsg).showToast();
				}
			} else {
				
			}
		} catch (Exception e) {
			Log.e("Exception", "***" + e.getMessage());
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_map:
			isLock = true;
			startActivity(new Intent(MainMenuActivity.this, MainMapActivity.class));
			break;

		case R.id.btn_start_fare:
			isLock = true;
			startActivity(new Intent(MainMenuActivity.this, StartFareActivity.class));
			break;

		case R.id.btn_settings:
			isLock = true;
			startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
			break;

		case R.id.btn_support:
			isLock = true;
			startActivity(new Intent(MainMenuActivity.this, SupportActivity.class));
			//startActivity(new Intent(MainMenuActivity.this, WelcomeActivity.class));
			break;

		case R.id.btn_logout:
			ConstantValues.isBack = false;
			//isLock = true;
			makeUavailable();
			/*Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);*/
			finish();
			break;

		case R.id.btn_past_trips:
			isLock = true;
			startActivity(new Intent(MainMenuActivity.this, FastTripsActivity.class));
			break;

		default:
			break;
		}

	}
	
	protected void onResume() {
		
		if(ConstantValues.isBack) {
			finish();
		}
		
		if (isLock) {
			isLock = false;
			//Make available
			if(InternetConnectivity.isConnectedToInternet(MainMenuActivity.this)) {
				new CheckAvailabilityAsyncTask(MainMenuActivity.this, false, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								//new CustomToast(getApplicationContext(), "Came to available MAIN").showToast();
								Log.i("Available check", "Came to available MAIN");
							} else {
								new CustomToast(getApplicationContext(), "Data not found").showToast();
							}
						} catch (Exception e ) {
							new CustomToast(MainMenuActivity.this, "" + e.getMessage()).showToast();
						}
						
					}
					
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
				
			} else {
				new CustomToast(MainMenuActivity.this, ConstantValues.internetConnectionMsg).showToast();
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
			isLock = true;
			//Make unavailable
			makeUavailable();
		}
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		isLock = false;
		//moveTaskToBack(true);
		super.onBackPressed();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void makeUavailable() {
		if(InternetConnectivity.isConnectedToInternet(MainMenuActivity.this)) {
			new CheckAvailabilityAsyncTask(MainMenuActivity.this, false, new OnRequestComplete() {
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							//new CustomToast(getApplicationContext(), "Gone to Unavailabe check MAIN").showToast();
							Log.i("Gone to Unavailabe check", "Gone to Unavailabe check out");
						} else {
							new CustomToast(getApplicationContext(), "Data not found").showToast();
						}
					} catch (Exception e ) {
						new CustomToast(MainMenuActivity.this, "" + e.getMessage()).showToast();
						Log.i("Exception 1001", "***" + e.getMessage());
					}
					
				}
				
			}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
		} else {
			new CustomToast(MainMenuActivity.this, ConstantValues.internetConnectionMsg).showToast();
		}
	}
	
}