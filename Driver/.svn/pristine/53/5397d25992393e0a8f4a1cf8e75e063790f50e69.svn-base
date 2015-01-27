package com.netcabs.driver;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.netcabs.asynctask.CheckAvailabilityAsyncTask;
import com.netcabs.asynctask.GetDurationAsyncTask;
import com.netcabs.asynctask.HailedAddAsyncTask;
import com.netcabs.customviews.CustomLog;
import com.netcabs.customviews.CustomToast;
import com.netcabs.datamodel.HailedCabInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;
import com.netcabs.utils.GPSTracker;
import com.netcabs.utils.Utils;

public class StartFareActivity extends Activity implements OnClickListener, OnMyLocationChangeListener {

	private boolean isValue = false;
	
	private EditText edTxtDestination;
	
	private Geocoder gcd;
	private GoogleMap map;
	private String duration;
	private String distance;
	private String fare;
	private LinearLayout linearWithOutData;
	private Marker currentPositionMarker;
	private ImageButton imgBtnTrafficView;
	private boolean isLock = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_start_fare);
		
		if (!DriverApp.getInstance().isLock()) {
			DriverApp.getInstance().setLock(false);
		}
		
		resetMeterConstant();
		initViews();
		setListener();
		initGoolgeMap();
		loadData();
	}
	
	private void resetMeterConstant() {
		
		ConstantValues.METER_START_TIME = 0;
		ConstantValues.METER_END_TIME = 0;
		ConstantValues.METER_HIT_COUNTER = 0;
		ConstantValues.METER_END_TOTAL_SPEED_MPH = 0;
		ConstantValues.METER_SPEED_MPH_TOTAL = 0.0;
		ConstantValues.METER_SPEED_AVERAGE = 0.0;
		ConstantValues.UPDATE_TIME = 0;
		ConstantValues.TIME_LOST = 0;
		ConstantValues.CURRENT_SPEED = "";
		
	}
	
	private void initViews() {
		gcd = new Geocoder(StartFareActivity.this, Locale.getDefault());
		imgBtnTrafficView = (ImageButton) findViewById(R.id.img_btn_traffic);
		if(ConstantValues.isTrafficViewEnabled){
			imgBtnTrafficView.setBackgroundResource(R.drawable.ans_mark_over);
		} else {
			imgBtnTrafficView.setBackgroundResource(R.drawable.ans_mark);
		}
		edTxtDestination = (EditText) findViewById(R.id.ed_txt_destination);
		linearWithOutData = (LinearLayout) findViewById(R.id.linear);
	}

	private void setListener() {
		((Button) findViewById(R.id.btn_back)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_start_trip)).setOnClickListener(this);
		imgBtnTrafficView.setOnClickListener(this);

	}

	private void loadData() {
			edTxtDestination.setFocusable(false);
			edTxtDestination.setOnClickListener(this);
	}
	
	private void initGoolgeMap() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		final LatLng cur_Latlng = new LatLng(new GPSTracker(StartFareActivity.this).getLatitude(), new GPSTracker(StartFareActivity.this).getLongitude());
		
		if(map != null) {
			map.moveCamera(CameraUpdateFactory.newLatLng(cur_Latlng));
			map.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
			
			currentPositionMarker = map.addMarker(new MarkerOptions()
					.title("My Current Position")
					.position(cur_Latlng)
					.flat(true)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon)));
			
			map.setMyLocationEnabled(true);
			if(ConstantValues.isTrafficViewEnabled){
				map.setTrafficEnabled(true);
			} else {
				map.setTrafficEnabled(false);
			}
			map.setOnMyLocationChangeListener(this);
			
		} else {
			new CustomLog(StartFareActivity.this, "MainMap", "Map is null").showLogI();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			ConstantValues.isBack = false;
			onBackPressed();
			break;
			
		case R.id.img_btn_traffic:
			if(ConstantValues.isTrafficViewEnabled){
				ConstantValues.isTrafficViewEnabled = false;
				map.setTrafficEnabled(false);
				imgBtnTrafficView.setBackgroundResource(R.drawable.ans_mark);
			} else {
				ConstantValues.isTrafficViewEnabled = true;
				map.setTrafficEnabled(true);
				imgBtnTrafficView.setBackgroundResource(R.drawable.ans_mark_over);
			}
			break;
			
		case R.id.btn_start_trip:
			if(!InternetConnectivity.isConnectedToInternet(StartFareActivity.this)) {
				new CustomToast(StartFareActivity.this, ConstantValues.internetConnectionMsg).showToast();
			}
			
			try {
				ConstantValues.METER_START_TIME = System.currentTimeMillis();
				LoginActivity.startTransmission();
				
			} catch (Exception e) {
				Toast.makeText(StartFareActivity.this, "Please connect your OBD meter", Toast.LENGTH_SHORT).show();
			}
			
			new CheckAvailabilityAsyncTask(StartFareActivity.this, false, new OnRequestComplete() {
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
						new CustomToast(StartFareActivity.this, "" + e.getMessage()).showToast();
						Log.i("Exception 1001", "***" + e.getMessage());
					}
					
				}
				
			}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
			
				
			GPSTracker gps = new GPSTracker(StartFareActivity.this);
			LatLng cur_Latlng = new LatLng(gps.getLatitude(), gps.getLongitude());
			if(cur_Latlng != null) {
				Log.e("current laat & long", cur_Latlng.latitude+":::::::::"+cur_Latlng.longitude);
				new GeoAddressAsyncTask(cur_Latlng.latitude, cur_Latlng.longitude).execute();
			}
			
			break;
			
		case R.id.ed_txt_destination:
			DriverApp.getInstance().hideKeyboard(StartFareActivity.this, v);
			isLock = true;
			startActivity(new Intent(StartFareActivity.this, SearchAddressLocaion.class));
			break;

		default:
			break;
		}
		
	}
	
	private class GeoAddressAsyncTask extends AsyncTask<Void, Void, Void> {
		double Lat, Lon;
		List<Address> addresses = null;
		public GeoAddressAsyncTask(double Lat, double Lon) {
			this.Lat = Lat;
			this.Lon = Lon;
		}
	
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				addresses = gcd.getFromLocation(Lat, Lon, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			if (addresses != null) {
				if (addresses.size() > 0) {
					HailedCabInfo hailedCabInfo = new HailedCabInfo();
					hailedCabInfo.setPassengerFullName("Guest");
					hailedCabInfo.setPickupLat(Lat);
					hailedCabInfo.setPickupLong(Lon);
					String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
					String addressWithoutNullValue = address.replace("null", "");
					Log.e("Address is 1", "" + addressWithoutNullValue);
					String formatedAddress = "";
					formatedAddress = addressWithoutNullValue.replace(", ,", ",");
					Log.e("Address is 2", "" + formatedAddress);
					if (formatedAddress.length() > 0 && formatedAddress.charAt(formatedAddress.length()-1)==',') {
						formatedAddress = formatedAddress.substring(0, formatedAddress.length()-2);
					}
					hailedCabInfo.setPickupName(formatedAddress);
					
					Calendar calendar = Calendar.getInstance();
					try {
						//edTxtDate.setText(Utils.getTimeDateFormat(calendar.get(Calendar.DAY_OF_MONTH) +"/"+ (calendar.get((Calendar.MONTH))+1) +"/"+ calendar.get(Calendar.YEAR)));
						hailedCabInfo.setDate(Utils.getTimeDateFormat(calendar.get(Calendar.DAY_OF_MONTH) +"/"+ (calendar.get((Calendar.MONTH))+1) +"/"+ calendar.get(Calendar.YEAR)));
						hailedCabInfo.setTime(Utils.getTimeDateFormat(calendar.get(Calendar.HOUR_OF_DAY) +":"+ (calendar.get((Calendar.MINUTE)) +":"+ calendar.get(Calendar.SECOND))));
					} catch (ParseException e) {
						hailedCabInfo.setDate("00/00/00");
						hailedCabInfo.setTime("00:00:00");
						e.printStackTrace();
					}
					
					if(!edTxtDestination.getText().toString().equals("")) {
						hailedCabInfo.setDestinationLat(DriverApp.getInstance().getDestinationInfo().getLocationLatitude());
						hailedCabInfo.setDestinationLong(DriverApp.getInstance().getDestinationInfo().getLocationLongitude());
						hailedCabInfo.setDestinationName(DriverApp.getInstance().getDestinationInfo().getLocationName());
						hailedCabInfo.setDistance(distance);
						hailedCabInfo.setDuration(duration);
						hailedCabInfo.setFare(fare);
						
						DriverApp.getInstance().setHailedCabInfo(hailedCabInfo);
						
						if(InternetConnectivity.isConnectedToInternet(StartFareActivity.this)) {
							new HailedAddAsyncTask(StartFareActivity.this, new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									try {
										if("2001".equals(result)) {
											isLock = true;
											
											DriverApp.getInstance().getHailedCabInfo().setBookingId(DriverApp.getInstance().getBookingId());
											startActivity(new Intent(StartFareActivity.this, StartOrEndTripActivity.class).putExtra("is_hailed", true));
											finish();
										}
									} catch (Exception e) {
										new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
									}
									
								}
							}).execute("1016", DriverApp.getInstance().getDriverInfo().getTaxiId(), DriverApp.getInstance().getProfileInfo().getId(), DriverApp.getInstance().getDriverInfo().getRegoNo(), Double.toString(DriverApp.getInstance().getHailedCabInfo().getPickupLat()), Double.toString(DriverApp.getInstance().getHailedCabInfo().getPickupLong()), DriverApp.getInstance().getHailedCabInfo().getPickupName(), Double.toString(DriverApp.getInstance().getHailedCabInfo().getDestinationLat()), Double.toString(DriverApp.getInstance().getHailedCabInfo().getDestinationLong()), DriverApp.getInstance().getHailedCabInfo().getDestinationName(), DriverApp.getInstance().getHailedCabInfo().getDate(), DriverApp.getInstance().getHailedCabInfo().getTime());
						} else {
						//	new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
						}
					} else {
						DriverApp.getInstance().setHailedCabInfo(hailedCabInfo);
						if(InternetConnectivity.isConnectedToInternet(StartFareActivity.this)) {
							new HailedAddAsyncTask(StartFareActivity.this, new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									try {
										if("2001".equals(result)) {
											isLock = true;
											DriverApp.getInstance().getHailedCabInfo().setBookingId(DriverApp.getInstance().getBookingId());
											startActivity(new Intent(StartFareActivity.this, StartOrEndTripActivity.class).putExtra("is_hailed", true));
											finish();
										}
									} catch (Exception e) {
										new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
									}
									
								}
							}).execute("1016", DriverApp.getInstance().getDriverInfo().getTaxiId(),DriverApp.getInstance().getProfileInfo().getId(), DriverApp.getInstance().getDriverInfo().getRegoNo(), Double.toString(DriverApp.getInstance().getHailedCabInfo().getPickupLat()), Double.toString(DriverApp.getInstance().getHailedCabInfo().getPickupLong()), DriverApp.getInstance().getHailedCabInfo().getPickupName(), "", "", "", DriverApp.getInstance().getHailedCabInfo().getDate(), DriverApp.getInstance().getHailedCabInfo().getTime());
						} else {
						//	new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
						}
					}
					
				} else {
					Log.i("Value is null", "______empty__________");
				}
				   
			} else {
				new CustomToast(getApplicationContext(), ConstantValues.geoCoderAddressRetrieveingProblemeMessage).showToast();
				Log.i("Value is null", "________________");
			}
			
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
			if(InternetConnectivity.isConnectedToInternet(StartFareActivity.this)) {
				new CheckAvailabilityAsyncTask(StartFareActivity.this, false, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
								Log.i("Available check", "Came to available");
							} else {
								//new CustomToast(getApplicationContext(), "Data not found").showToast();
							}	

						} catch (Exception e) {
							new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
							Log.e("Error 1037", "---" + e.getMessage());
						}
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
				
			} else {
				new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
			}
		} else {
			
		}
		
		super.onResume();
		
		if(DriverApp.getInstance().getDestinationInfo() != null) {
			edTxtDestination.setText(DriverApp.getInstance().getDestinationInfo().getLocationName());
			if(InternetConnectivity.isConnectedToInternet(StartFareActivity.this)) {
				GPSTracker gps = new GPSTracker(StartFareActivity.this);
				LatLng destinationLatLng = new LatLng(gps.getLatitude(), gps.getLongitude());
				LatLng pickUpLatLng = new LatLng(DriverApp.getInstance().getDestinationInfo().getLocationLatitude(), DriverApp.getInstance().getDestinationInfo().getLocationLongitude());
				
				new GetDurationAsyncTask(StartFareActivity.this, pickUpLatLng, destinationLatLng,  new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						
						try {
							Log.e("Duration is:", "::::"+result);
							String [] distanceDuration = result.split("//");
							
							duration = Utils.getFormatedDuration(distanceDuration[1]);
						//	DriverApp.getInstance().getHailedCabInfo().setDistance(Utils.getFormatedDistance(distanceDuration[0]));
							distance = Utils.getFormatedDistance(distanceDuration[0]);
							if(distanceDuration[0].contains(" ")) {
								String[] distance = distanceDuration[0].split(" ");
								Log.i("Distance is ", "______________"+distance[0]);
								double fareAmount = Double.parseDouble(distance[0].replace(",", "")) * ConstantValues.fareValue;
								Log.i("fareAmount is ", "______________"+String.format("%.4f", fareAmount));
								//DriverApp.getInstance().getHailedCabInfo().setFare(Double.toString(fareAmount));
								fare = String.format("%.4f", fareAmount);
							} else {
								Log.i("fare not found", "___________");
							}

						} catch (Exception e) {
							new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
							Log.e("Error 1035", "---" + e.getMessage());
						}
						
					}
				}).execute();
			} else {
				new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
			}
        }
	}


	@Override
	protected void onPause() {
		if (isLock) {
			isLock = false;
		} else {
			DriverApp.getInstance().setLock(true);
			isLock = true;
			//Make unavailable
			if(InternetConnectivity.isConnectedToInternet(StartFareActivity.this)) {
				new CheckAvailabilityAsyncTask(StartFareActivity.this, false, new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
								Log.i("Available check", "Gone to unavailable ");
								//new CustomToast(getApplicationContext(), "Gone to unavailable start fare").showToast();
								
							} else {
								new CustomToast(getApplicationContext(), "Data not found").showToast();
							}

						} catch (Exception e) {
							new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
							Log.e("Error 1036", "---" + e.getMessage());
						}
						
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
			} else {
				new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
			}
		}
		super.onPause();
	}

	@Override
	public void onMyLocationChange(Location location) {
		// Getting latitude of the current location
        double latitude = location.getLatitude();
 
        // Getting longitude of the current location
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        LatLng curenLatLng = new LatLng(latitude, longitude);
 
        // Showing the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(curenLatLng));
 
        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(map.getCameraPosition().zoom));
        animateMarker(currentPositionMarker, curenLatLng, false);
		
	}
	
	
	public void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
	     final Handler handler = new Handler();
	     final long start = SystemClock.uptimeMillis();
	     com.google.android.gms.maps.Projection proj = map.getProjection();
	     Point startPoint = proj.toScreenLocation(marker.getPosition());
	     final LatLng startLatLng = proj.fromScreenLocation(startPoint);
	     final long duration = 5000;
	     final Interpolator interpolator = new LinearInterpolator();
	
	     handler.post(new Runnable() {
	      long elapsed;
	      float t;
	      float v;
	         @Override
	         public void run() {
	             elapsed = SystemClock.uptimeMillis() - start;
	             t = interpolator.getInterpolation((float) elapsed/ duration);
	             v = interpolator.getInterpolation(t);
	             double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
	             double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;
	             marker.setPosition(new LatLng(lat, lng));
	             //marker.setPosition();
	             if (t < 1.0) {
	         // Post again 16ms later.
	                 handler.postDelayed(this, 16);
	             } else {
	                 if (hideMarker) {
	                     marker.setVisible(false);
	                 } else {
	                     marker.setVisible(true);
	                 }
	             }
	         }
	     });
	     
	}
	 

}
