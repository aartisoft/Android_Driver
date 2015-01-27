package com.netcabs.driver;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netcabs.asynctask.CheckAvailabilityAsyncTask;
import com.netcabs.asynctask.LocationByName;
import com.netcabs.customviews.CustomAutoCompleteView;
import com.netcabs.customviews.CustomToast;
import com.netcabs.datamodel.DestinationInfo;
import com.netcabs.interfacecallback.OnComplete;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;


public class SearchAddressLocaion extends Activity implements OnClickListener  {
	private RelativeLayout relativeLayout;
	public static CustomAutoCompleteView myAutoComplete;
	private static final String TAG_RESULT = "predictions";
	private Button btnBack;
	public boolean isLock = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_search_address_locaion);
		
		initViews();
		setListener();

	}

	private void setListener() {
		btnBack.setOnClickListener(this);
		
		 myAutoComplete.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
	                DriverApp.getInstance().hideKeyboard(SearchAddressLocaion.this, arg1);
	                RelativeLayout rl = (RelativeLayout) arg1;
	                final TextView tv = (TextView) rl.getChildAt(2);
	                myAutoComplete.setText(tv.getText().toString().trim());
	               // ConstantValues.locationNamel = tv.getText().toString().trim();
	                Log.e("ref id is ", "----"+ConstantValues.refId.get(pos));
	                
	                DriverApp.getInstance().hideKeyboard(SearchAddressLocaion.this, arg1);
	                Log.e("ref id is ", "----"+ConstantValues.refId.get(pos));
	                if(InternetConnectivity.isConnectedToInternet(SearchAddressLocaion.this)) {
		                new LocationByName(SearchAddressLocaion.this, new OnComplete() {
							@Override
							public void onLocationComplete(int result, double lat, double lon) {
								try {
									DestinationInfo info = new DestinationInfo();
									info.setLocationName(tv.getText().toString().trim());
									info.setLocationLatitude(lat);
									info.setLocationLongitude(lon);
									Log.i("Value is ", "_____"+info.getLocationName() +info.getLocationLongitude() + info.getLocationLongitude());
									DriverApp.getInstance().setDestinationInfo(info);
									Log.e("_ place lat & lon", info.getLocationName()+info.getLocationLatitude()+info.getLocationLongitude()+"");
									isLock = true;
									finish();
								} catch (Exception e) {
									new CustomToast(SearchAddressLocaion.this, "" + e.getMessage()).showToast();
								}
								
							}
	
							@Override
							public void onComplete(int result, ArrayList<String> data, ArrayList<String> refId) {
								
							}
	
							@Override
							public void onAddressComplete(int result, String address) {
								// TODO Auto-generated method stub
							}
							
						}, ConstantValues.refId.get(pos)).execute();
		                
	                } else {
	                	
	                }
	              
	            }

	        });
	         
	        // add the listener so it will tries to suggest while the user types
	        myAutoComplete.addTextChangedListener(new com.netcabs.customviews.CustomAutoCompleteTextChangedListener(this));
		
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.ed_txt_pickup_location);
		btnBack = (Button) findViewById(R.id.btn_back);

		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
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
			if(InternetConnectivity.isConnectedToInternet(SearchAddressLocaion.this)) {
				new CheckAvailabilityAsyncTask(SearchAddressLocaion.this, false, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
								//new CustomToast(MainMapActivity.this, "Came to available").showToast();
								Log.i("Available check", "Came to available");
							} else {
								//new CustomToast(SearchAddressLocaion.this, "Data not found").showToast();
							}

						} catch (Exception e) {
							//new CustomToast(SearchAddressLocaion.this, "" + e.getMessage()).showToast();
							Log.e("Error 1027", "---" + e.getMessage());
						}
						
						
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
				
			} else {
				new CustomToast(SearchAddressLocaion.this, ConstantValues.internetConnectionMsg).showToast();
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
			if(InternetConnectivity.isConnectedToInternet(SearchAddressLocaion.this)) {
				new CheckAvailabilityAsyncTask(SearchAddressLocaion.this, false, new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
								Log.i("Available check", "Gone to unavailable on pause");
								//new CustomToast(SearchAddressLocaion.this, "Gone to unavailable on pause").showToast();
								
							} else {
								//new CustomToast(SearchAddressLocaion.this, "Data not found").showToast();
							}

						} catch (Exception e) {
							new CustomToast(SearchAddressLocaion.this, "" + e.getMessage()).showToast();
							Log.e("Error 1028", "---" + e.getMessage());
						}
						
						
						
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
			} else {
				new CustomToast(SearchAddressLocaion.this, ConstantValues.internetConnectionMsg).showToast();
			}
		}
		super.onPause();
	}
	
}
