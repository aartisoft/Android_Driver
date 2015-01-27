package com.netcabs.driver;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.netcabs.asynctask.DriverInfoAsyncTask;
import com.netcabs.asynctask.PaymentsMethodAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.customviews.DialogController;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;
import com.netcabs.utils.PreferenceUtil;

public class SplashScreenActivity extends Activity {

	private Intent intent;
	
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    
    String SENDER_ID = "700985639314";
    
    static final String TAG = "SplashScreenActivity";
    
    private GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    private String regid;
    
    private PreferenceUtil preferenceUtil;
	private Dialog dialogeEnterDeviceName;
	private String deviceName = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_splash_screen);
		
		preferenceUtil = new PreferenceUtil(SplashScreenActivity.this);
		
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(getApplicationContext());
			preferenceUtil.setRegistrationKey(regid);
			Log.i("Reg id is : ", "______Driver_______" + regid);

			if (regid.isEmpty()) {
				registerInBackground();
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}
		
		intent = new Intent(SplashScreenActivity.this, com.netcabs.services.ServiceTaxiLocationUpdate.class);
		
		if(InternetConnectivity.isConnectedToInternet(SplashScreenActivity.this)) {
			new PaymentsMethodAsyncTask(SplashScreenActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							/*new DriverInfoAsyncTask(SplashScreenActivity.this, new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									if("2001".equals(result)) {
										if(InternetConnectivity.isConnectedToInternet(SplashScreenActivity.this)) {
											if (DriverApp.getInstance().getDriverInfo() != null) {
												startService(intent);
											}
								        } 
										startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
										finish();
									} else {
										new CustomToast(SplashScreenActivity.this, "No taxi assigned to this Device. Please contact with administrator.").showToast();
										finish();
									}
								}
							}).execute("1018", getDeviceToken(), preferenceUtil.getRegistrationKey());
*/
							callDriverInfoAsyncTask(deviceName, "1");
							
						} else {
							new CustomToast(SplashScreenActivity.this, ConstantValues.errorMessage).showToast();
							finish();
						}
					} catch (Exception e) {
						new CustomToast(SplashScreenActivity.this, "" + e.getMessage()).showToast();
						finish();
					}
						
				} 
			}).execute("25");
			
		} else {
			new CustomToast(SplashScreenActivity.this, ConstantValues.internetConnectionMsg).showToast();
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		checkPlayServices();
	}
	
	private String getDeviceToken() {
		return Secure.getString(SplashScreenActivity.this.getContentResolver(), Secure.ANDROID_ID);
	}
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
	            
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID since the existing regID is not guaranteed to work with the new app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but how you store the regID in your app is up to you.
	    return getSharedPreferences(SplashScreenActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } 
	    catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private void registerInBackground() {
		class APITaskGCM extends AsyncTask<Void, Void, String> {
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				 String msg = "";
		            try {
		                if (gcm == null) {
		                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
		                }
		                regid = gcm.register(SENDER_ID);
		                preferenceUtil.setRegistrationKey(regid);
		                msg = "Device registered, registration id=" + regid;
	
		                storeRegistrationId(getApplicationContext(), regid);
		            } 
		            catch (IOException ex) {
		                msg = "Error :" + ex.getMessage();
		            }
		            return msg;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v(TAG, "Successful !");
				Log.v(TAG, "gcm_regid: " + regid);
			}
		}
		
		new APITaskGCM().execute();
	}
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void enterDeviceNameDialog() {
		dialogeEnterDeviceName = new DialogController(SplashScreenActivity.this).EnterDeviceNameDialog();
		
		final EditText edtTxtDeviceName;
		final Button btnOk;
		edtTxtDeviceName = (EditText) dialogeEnterDeviceName.findViewById(R.id.edt_txt_device_name);
		btnOk = (Button) dialogeEnterDeviceName.findViewById(R.id.btn_ok);
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					if(!edtTxtDeviceName.getText().toString().trim().equals("")) {
						deviceName = edtTxtDeviceName.getText().toString().trim();
						callDriverInfoAsyncTask(deviceName, "0");
						if(dialogeEnterDeviceName != null) {
							if(dialogeEnterDeviceName.isShowing()) {
								dialogeEnterDeviceName.dismiss();
							}
						}
					} else {
						deviceName = "";
						edtTxtDeviceName.setError("Required");
						new CustomToast(getApplicationContext(), "Enter Device Name").showToast();
					}
				
				}	
			});
		
		if(!dialogeEnterDeviceName.isShowing()) {
			dialogeEnterDeviceName.show();
		}
	}
	
	private void callDriverInfoAsyncTask(String deviceName, String check) {
		if(InternetConnectivity.isConnectedToInternet(SplashScreenActivity.this)) {
			new DriverInfoAsyncTask(SplashScreenActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					if("1001".equals(result)) {
						if(dialogeEnterDeviceName != null) {
							if(dialogeEnterDeviceName.isShowing()) {
								dialogeEnterDeviceName.dismiss();
							}
						}
						new CustomToast(getApplicationContext(), "No taxi assigned to this Device. Please contact with administrator.").showToast();
						finish();
						
					} else if("2001".equals(result)) {
						if(dialogeEnterDeviceName != null) {
							if(dialogeEnterDeviceName.isShowing()) {
								dialogeEnterDeviceName.dismiss();
							}
						}
						if(InternetConnectivity.isConnectedToInternet(SplashScreenActivity.this)) {
							if(DriverApp.getInstance().getDriverInfo() != null) {
								startService(intent);
							}
				        } 
						startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
						finish();
					} else if("3001".equals(result)) {
						enterDeviceNameDialog();
						
					} else if("4001".equals(result)) {
						if(dialogeEnterDeviceName != null) {
							if(dialogeEnterDeviceName.isShowing()) {
								dialogeEnterDeviceName.dismiss();
							}
						}
						
						new CustomToast(getApplicationContext(), "Device successfully registered, please contact with administration").showToast();
						finish();
					} else if("5001".equals(result)) {
						if(dialogeEnterDeviceName != null) {
							if(!dialogeEnterDeviceName.isShowing()) {
								dialogeEnterDeviceName.show();
							}
						}
						new CustomToast(getApplicationContext(), "Duplicate Device Name, please try again").showToast();
						
					} else {
						new CustomToast(getApplicationContext(), "Invalid Status Code").showToast();
					}
					
				}
			}).execute("1018", deviceName, getDeviceToken(), preferenceUtil.getRegistrationKey(), check);
		} else {
			dialogeEnterDeviceName.dismiss();
			new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
			finish();
		
			return;
		}
		
	}

}
