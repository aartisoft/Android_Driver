package com.netcabs.driver;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.asynctask.CardListAsyncTask;
import com.netcabs.asynctask.HolidayCheckingAsyncTask;
import com.netcabs.asynctask.PaymentAsyncTask;
import com.netcabs.asynctask.RegionFareAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.customviews.DialogController;
import com.netcabs.datamodel.FareCalculationModel;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.obdmonitor.BluetoothChatService;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;
import com.netcabs.utils.Utils;

public class CalculateFareActivity extends Activity implements OnClickListener{
	
	private TextView txtViewCalculatedTripFareTotal;
	private TextView txtViewCalculatedTripFareGst;
	private TextView txtViewCalculatedTripFare;
	private TextView txtViewCalculatedTripFareExtra;
	
	private Button btnOkey;
	private Geocoder gcd;
	private String tripLocationZone = "";
	private String selectedType = "";
	
	private boolean ishighSpeed;
	private int timeOftheDay;
	private boolean isHighOccupacy;
	private boolean isHoliday;
	private double totalDistance;
	private double totalTimeInMinute;
	private boolean isBooking;
	private boolean isCardPay;
	private boolean isLateNight;
	private boolean isAirport;
	private boolean isAirportRank;
	private double tollAmount = 0.0;
	
	private boolean isHailed;
	private String payment_method_id = "";
	public int defaultCardItemNo;
	public boolean isLock = false;
	private String year;
	private String month;
	private String day;
	
	private String regionflag ="3";
	private Dialog dialogPaymentTypeConfirm;
	private Dialog dialogExtraCharge;
	private double speed_KMperHour = 0.0;
	
	
	//Device related variable;
	public double travelledDistance = 0.0;
	
	public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int REQUEST_ENABLE_BT = 3;
	private BluetoothChatService mChatService = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	
	private String mConnectedDeviceName = null;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final String DEVICE_NAME = "device_name";
	
	private int message_number = 0;
	private StringBuffer mOutStringBuffer;
	
	
	private TextView txtViewMPH;
	private TextView txtViewDistance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_calculate_fare);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		intView();
		setListener();

	}
	
	private void setListener() {
		btnOkey.setOnClickListener(this);		
	}

	private void loadData() {
		isHailed = getIntent().getExtras().getBoolean("is_hailed");
		
		if(getIntent().getExtras().getBoolean("is_hailed")) {
			try {
				//double formatedTime = Double.parseDouble(Utils.get24HourFormatedTime(DriverApp.getInstance().getHailedCabInfo().getTime()).split(":")[0]);
			//	Log.e("current time & formated time", ""+DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0] +"||||"+formatedTime );
				Log.e("current time, distance & duration", ""+DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0]+"~~~"+DriverApp.getInstance().getHailedCabInfo().getDistance() +"~~~"+DriverApp.getInstance().getHailedCabInfo().getDuration());
				
				if(Utils.getDay(DriverApp.getInstance().getHailedCabInfo().getDate()).equalsIgnoreCase("Friday") || Utils.getDay(DriverApp.getInstance().getHailedCabInfo().getDate()).equalsIgnoreCase("Saturday")){
					if(((Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])>10) && (Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])<24)) || ((Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])>0) && (Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])<4)) ) {
					timeOftheDay = 2;
					}
				} else {
					if((Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])>9) && (Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])<=17)) {
						timeOftheDay = 0;
					} else if (((Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])>17) && (Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])<=24)) || ((Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])>0) && (Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])<=9))) {
						timeOftheDay = 1;
					}
				}
				if((Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])>=0) && (Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getTime().split(":")[0])<6)) {
					isLateNight = true;
				} else {
					isLateNight = false;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(speed_KMperHour > 21.0) {
				ishighSpeed = true; // ------
			} else {
				ishighSpeed = false;
			}
			totalDistance = Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getDistance().split(" ")[0]);
			//totalTimeInMinute = Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getDuration().split(" ")[0]);
			totalTimeInMinute = Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getDuration().split(" ")[0].split(":")[0]) * 60 +Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getDuration().split(" ")[0].split(":")[1]); //---------
			
			isBooking = false;
			isCardPay = false;
			//new GeoAddressAsyncTask(-37.814251,144.963169).execute();
			if(DriverApp.getInstance().getHailedCabInfo().getDate() != null) {
				if(DriverApp.getInstance().getHailedCabInfo().getDate().contains("-")) {
					day = DriverApp.getInstance().getHailedCabInfo().getDate().split("-")[0];
					month = DriverApp.getInstance().getHailedCabInfo().getDate().split("-")[1];
					year = DriverApp.getInstance().getHailedCabInfo().getDate().split("-")[2];
				} else if (DriverApp.getInstance().getHailedCabInfo().getDate().contains("/")) {
					day = DriverApp.getInstance().getHailedCabInfo().getDate().split("/")[0];
					month = DriverApp.getInstance().getHailedCabInfo().getDate().split("/")[1];
					year = DriverApp.getInstance().getHailedCabInfo().getDate().split("/")[2];
				}
			}
			
			if(!InternetConnectivity.isConnectedToInternet(CalculateFareActivity.this)) {
				Toast.makeText(CalculateFareActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
				return;
			} else {
				
			}
			
			new HolidayCheckingAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							isHoliday = true;
						} else if("1001".equals(result)){
							isHoliday = false;
						}
						new GeoAddressAsyncTask(DriverApp.getInstance().getHailedCabInfo().getPickupLat(),DriverApp.getInstance().getHailedCabInfo().getPickupLong()).execute();
							
					} catch (Exception e) {
						new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
					}
				}
			}).execute("1019", 	year, month, day);
			
		} else {
			for (int i = 0; i < DriverApp.getInstance().getPaymentsInfoList().size(); i++) {
				if (DriverApp.getInstance().getPaymentsInfoList().get(i).getMethodName().equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod())) {
					payment_method_id = DriverApp.getInstance().getPaymentsInfoList().get(i).getId();
				}
			}
			// 0 for 9AM -5PM, 1 for 5PM - 9AM, 2 for (10AM to 4AM Friday & Saturday night)
			if("Card Payment".equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod()) || "Auto Payment".equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod())) {
				isCardPay = true;
			} else {
				isCardPay = false;
			}
			try {
				Log.e("current time , distance & duration", ""+DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0]+"~~~"+DriverApp.getInstance().getBookingInfo().getDistance() +"~~~"+DriverApp.getInstance().getBookingInfo().getDuration());
				if(Utils.getDay(DriverApp.getInstance().getBookingInfo().getBookingDate()).equalsIgnoreCase("Friday") || Utils.getDay(DriverApp.getInstance().getBookingInfo().getBookingDate()).equalsIgnoreCase("Saturday")){
					if(((Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])>10) && (Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])<24)) || ((Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])>0) && (Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])<4)) ) {
					timeOftheDay = 2;
					}
				} else {
					if((Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])>9) && (Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])<=17)) {
						timeOftheDay = 0;
					} else if (((Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])>17) && (Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])<=24)) || ((Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])>0) && (Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])<=9))) {
					
						timeOftheDay = 1;
					}
				}
				if((Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])>=0) && (Double.parseDouble(DriverApp.getInstance().getBookingInfo().getBookingTime().split(":")[0])<6)) {
					isLateNight = true;
				} else {
					isLateNight = false;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(speed_KMperHour> 21.0) {
				ishighSpeed = true; // ------
			} else {
				ishighSpeed = false;
			}
			timeOftheDay = 1;
			totalDistance = Double.parseDouble(DriverApp.getInstance().getBookingInfo().getDistance().split(" ")[0]);
			totalTimeInMinute = Double.parseDouble(DriverApp.getInstance().getBookingInfo().getDuration().split(" ")[0].split(":")[0]) * 60 +Double.parseDouble(DriverApp.getInstance().getBookingInfo().getDuration().split(" ")[0].split(":")[1]); //---------
			if(DriverApp.getInstance().getBookingInfo().getBookingVia() == 4) {
				isBooking = false;
			} else {
				isBooking = true;
			}
			
			if(DriverApp.getInstance().getBookingInfo().getBookingDate() != null) {
				if(DriverApp.getInstance().getBookingInfo().getBookingDate().contains("-")) {
					day = DriverApp.getInstance().getBookingInfo().getBookingDate().split("-")[0];
					month = DriverApp.getInstance().getBookingInfo().getBookingDate().split("-")[1];
					year = DriverApp.getInstance().getBookingInfo().getBookingDate().split("-")[2];
				} else if (DriverApp.getInstance().getBookingInfo().getBookingDate().contains("/")) {
					day = DriverApp.getInstance().getBookingInfo().getBookingDate().split("/")[0];
					month = DriverApp.getInstance().getBookingInfo().getBookingDate().split("/")[1];
					year = DriverApp.getInstance().getBookingInfo().getBookingDate().split("/")[2];
				}
			}
			
			if(!InternetConnectivity.isConnectedToInternet(CalculateFareActivity.this)) {
				Toast.makeText(CalculateFareActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
				return;
			} else {
				
			}
			new HolidayCheckingAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							isHoliday = true;
						} else if("1001".equals(result)){
							isHoliday = false;
						}
						new GeoAddressAsyncTask(DriverApp.getInstance().getBookingInfo().getPickupLat(),DriverApp.getInstance().getBookingInfo().getPickupLon()).execute();
						
					} catch (Exception e) {
						new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
					}
					
				}
			}).execute("1019", 	year, month, day);
			
		}
		
	
	}

	private void intView() {
		gcd = new Geocoder(CalculateFareActivity.this, Locale.getDefault());
		txtViewCalculatedTripFareTotal = (TextView) findViewById(R.id.txt_view_calculate_trip_fare_total);
		txtViewCalculatedTripFareGst = (TextView) findViewById(R.id.txt_view_calculate_trip_fare_gst);
		txtViewCalculatedTripFare = (TextView) findViewById(R.id.txt_view_calculate_trip_fare);
		txtViewCalculatedTripFareExtra = (TextView) findViewById(R.id.txt_view_calculate_trip_fare_extra);
		btnOkey = (Button) findViewById(R.id.btn_okey);
		extraChargePopup() ;
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_okey :
			
			//Log.i("Payment Method ", "**####" + DriverApp.getInstance().getBookingInfo().getPaymentMethod());
			//Toast.makeText(getApplicationContext(), "Payment Method " + DriverApp.getInstance().getBookingInfo().getPaymentMethod(), Toast.LENGTH_SHORT).show();
			if(isHailed) {
				startActivity(new Intent(CalculateFareActivity.this, PaymentTypeActivity.class).putExtra("is_hailed", isHailed));
				finish();
			} else {
				if (DriverApp.getInstance().getBookingInfo().getPaymentMethod().equalsIgnoreCase("Auto Payment")) {
					autoPayment() ;
				} else {
					processPaymentTypeConfirmPopup();
//					startActivity(new Intent(CalculateFareActivity.this, CalculatorActivity.class).putExtra("is_hailed", isHailed));
//					finish();
				}
			}
			
			
			break;
		
		default: 
			break;
		
		}
	}
	
	private class GeoAddressAsyncTask extends AsyncTask<Void, Void, Void> {
		double Lat, Lon;
		List<Address> addresses = null;
		private ProgressDialog Dialog;
		boolean isProgress = false;
		public GeoAddressAsyncTask(double Lat, double Lon) {
			this.Lat = Lat;
			this.Lon = Lon;
		}
	
		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(CalculateFareActivity.this);
			Dialog.setMessage("Loading...");
			Dialog.setCancelable(false);
			if(isProgress) {
				Dialog.show();
			}
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
			try {
				if (Dialog.isShowing()) {
					Dialog.dismiss();
					
				}
				if (addresses != null) {
					if (addresses.size() > 0) {
						if(addresses.get(0).getLocality() != null) {
							tripLocationZone = addresses.get(0).getLocality(); 
						}
						Log.e("tripLocationZone", "" + tripLocationZone);
						
					} else {
						Log.i("Value is null", "______empty__________");
					}

				} else {
					Log.i("Value is null", "________________");
				}
				if(tripLocationZone.contains("Melbourne") || tripLocationZone.contains("Frankston") || tripLocationZone.contains("Dandenong") || tripLocationZone.contains("Port Phillip")) {
					regionflag = "1";
				} else if (tripLocationZone.contains("Geelong") || tripLocationZone.contains("Ballarat") || tripLocationZone.contains("Bendigo")) {
					regionflag = "2";
				} else {
					regionflag = "3";
				}
				
				
				new RegionFareAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							FareCalculationModel fareCalModel =  new FareCalculationModel(Integer.parseInt(regionflag), ishighSpeed, timeOftheDay, isHighOccupacy, isHoliday, totalDistance, totalTimeInMinute, isBooking, isCardPay, isLateNight, isAirport, isAirportRank, tollAmount);
							
							txtViewCalculatedTripFareTotal.setText("Total fare amount : "+ String.format("%.2f", fareCalModel.gettotalCalculateFare()) + "$");
							txtViewCalculatedTripFareGst.setText("5% GST : "+ String.format("%.2f", fareCalModel.getGst()) + "$");
							txtViewCalculatedTripFare.setText("Trip fare amount : "+ String.format("%.2f", fareCalModel.getOnlyFareAmount()) + "$");
							txtViewCalculatedTripFareExtra.setText("Extras : "+ String.format("%.2f", fareCalModel.getExtras()) + "$");
							
							if(isHailed){
								DriverApp.getInstance().getHailedCabInfo().setFare(String.format("%.2f", fareCalModel.gettotalCalculateFare()));
								DriverApp.getInstance().getHailedCabInfo().setFareAmount(String.format("%.2f", fareCalModel.getOnlyFareAmount()));
								DriverApp.getInstance().getHailedCabInfo().setGst(String.format("%.2f", fareCalModel.getGst()));
								DriverApp.getInstance().getHailedCabInfo().setExtras(String.format("%.2f", fareCalModel.getExtras()));
							} else {
								DriverApp.getInstance().getBookingInfo().setFare(String.format("%.2f", fareCalModel.gettotalCalculateFare()));
								DriverApp.getInstance().getBookingInfo().setFareAmount(String.format("%.2f", fareCalModel.getOnlyFareAmount()));
								DriverApp.getInstance().getBookingInfo().setGst(String.format("%.2f", fareCalModel.getGst()));
								DriverApp.getInstance().getBookingInfo().setExtras(String.format("%.2f", fareCalModel.getExtras()));
							}
								
						} catch (Exception e) {
							new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
						}
					}
				}).execute("1020", regionflag, isHoliday+"", timeOftheDay+"", isHighOccupacy+"");
				

				
				//autoPayment();
				
			} catch (Exception e) {
				
			}
			
		}

		
	}
	
	
	private void autoPayment() {
		if(!InternetConnectivity.isConnectedToInternet(CalculateFareActivity.this)) {
			Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
			return;
		}
		
/*		for (int i = 0; i < DriverApp.getInstance().getPaymentsInfoList().size(); i++) {
			if (DriverApp.getInstance().getPaymentsInfoList().get(i).getMethodName().equalsIgnoreCase("Auto Payment")) {
				payment_method_id = DriverApp.getInstance().getPaymentsInfoList().get(i).getId();
			}
		}*/
		
		Log.i("Auto Payment","Active");
		
		if(isHailed) {
//			DriverApp.getInstance().getHailedCabInfo().setPaymentType(payment_method_id);
//			new PaymentAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
//				@Override
//				public void onRequestComplete(String result) {
//					
//					try {
//						if("2001".equals(result)) {
//							if(getIntent().getExtras() != null) {
//								isLock = true;
//								startActivity(new Intent(CalculateFareActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
//							} else {
//								isLock = true;
//								startActivity(new Intent(CalculateFareActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", false));
//							}
//						}
//					} catch (Exception e) {
//						new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
//					}
//					
//				}
//			}).execute("1011", DriverApp.getInstance().getDriverInfo().getTaxiId(), DriverApp.getInstance().getProfileInfo().getId(), "544790ef481a590ebabe165b", DriverApp.getInstance().getHailedCabInfo().getBookingId(), DriverApp.getInstance().getHailedCabInfo().getFare(), payment_method_id, DriverApp.getInstance().getHailedCabInfo().getDate(), DriverApp.getInstance().getHailedCabInfo().getTime(),
//					"",
//					"",
//					"",
//					"",
//					"",
//					"",
//					"");
		
		} else {
			new PaymentAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							Log.i("Auto Payment","Success");
							startActivity(new Intent(CalculateFareActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", isHailed));
							finish();
						}
					} catch (Exception e) {
						new CustomToast(getApplicationContext(), "" + e.getMessage()).showToast();
					}
					
				}
			}).execute("1011", 
					DriverApp.getInstance().getDriverInfo().getTaxiId(), 
					DriverApp.getInstance().getProfileInfo().getId(), 
					DriverApp.getInstance().getBookingInfo().getUserId(), 
					DriverApp.getInstance().getBookingInfo().getBookingId(), 
					DriverApp.getInstance().getBookingInfo().getFare(), 
					DriverApp.getInstance().getBookingInfo().getFareAmount(), 
					DriverApp.getInstance().getBookingInfo().getGst(), 
					DriverApp.getInstance().getBookingInfo().getExtras(), 
					payment_method_id, 
					DriverApp.getInstance().getBookingInfo().getBookingDate(), 
					DriverApp.getInstance().getBookingInfo().getBookingTime(),
					"",
					"",
					"",
					"",
					"",
					"",
					"");
			//loadCardInfo();
		}
		
	}
	
	
	private void loadCardInfo() {
		new CardListAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
			
			@Override
			public void onRequestComplete(String result) {
				try {
					if("2001".equals(result)) {
						if(DriverApp.getInstance().getCreditCardInfoList() != null) {
							String cardNumber = null;
							int CardSize = DriverApp.getInstance().getCreditCardInfoList().size();
							for (int i = 0; i < CardSize; i++) {
								
								if (DriverApp.getInstance().getCreditCardInfoList().get(i).getIsDefault() == 1) {
									defaultCardItemNo = i;
									cardNumber = DriverApp.getInstance().getCreditCardInfoList().get(i).getCardNumber();
									DriverApp.getInstance().getBookingInfo().setPaymentMethod("Card Payment");
									new PaymentAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
										
										@Override
										public void onRequestComplete(String result) {
											try {
												if("2001".equals(result)) {
													startActivity(new Intent(CalculateFareActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", isHailed));
													finish();
												}
											} catch (Exception e) {
												new CustomToast(CalculateFareActivity.this, "" + e.getMessage()).showToast();
											}
											
										}
									}).execute(
											"1011", 
											DriverApp.getInstance().getDriverInfo().getTaxiId(), 
											DriverApp.getInstance().getProfileInfo().getId(), 
											DriverApp.getInstance().getBookingInfo().getUserId(), 
											DriverApp.getInstance().getBookingInfo().getBookingId(), 
											DriverApp.getInstance().getBookingInfo().getFare(),
											DriverApp.getInstance().getBookingInfo().getFareAmount(), 
											DriverApp.getInstance().getBookingInfo().getGst(), 
											DriverApp.getInstance().getBookingInfo().getExtras(), 
											payment_method_id, 
											DriverApp.getInstance().getBookingInfo().getBookingDate(), 
											DriverApp.getInstance().getBookingInfo().getBookingTime(),
											DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getCardNumber(),
											Integer.toString(DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getCvv()),
											DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getExpireDate(),
											DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getZip(),
											DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getCareType(),
											DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getCountryId(),
											DriverApp.getInstance().getCreditCardInfoList().get(defaultCardItemNo).getCardHolderName());
								}
								
							}
							//paymentAdapter = new PaymentCardAdapter(getActivity(), DriverApp.getInstance().getCreditCardInfoList());
						} else {
							//lstViewCard.setAdapter(null);
						}
					} else if("3001".equals(result)) {
						
					} else if("4001".equals(result)) {
										
					} else {
						
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
				}
			}
		}).execute("17", DriverApp.getInstance().getBookingInfo().getUserId());
	}
	
	@Override
	public void onBackPressed() {
	//	super.onBackPressed();
	}
	
	private void processPaymentTypeConfirmPopup() {
		dialogPaymentTypeConfirm = new DialogController(CalculateFareActivity.this).paymentTypeConfirmDialog();
		
		TextView txtViewMsg= (TextView) dialogPaymentTypeConfirm.findViewById(R.id.txt_view_msg);
		//txtViewMsg.setText(String.format("%.2f", Double.parseDouble(edTxtAmount.getText().toString().trim())));
		Log.e("Payment type is", ""+ DriverApp.getInstance().getBookingInfo().getPaymentMethod());
		String changeTo = "";
		if(DriverApp.getInstance().getBookingInfo().getPaymentMethod().contains("Cash")){
			 selectedType = "CASH";
			 changeTo = "CARD";
		} else {
			selectedType = "CARD";
			 changeTo = "CASH";
		}
		
		String msg = "The passenger has selected to pay by\n"+selectedType+".Press Ok to confirm payment, or click \nChange to change the payment method \nto "+changeTo;
		txtViewMsg.setText(msg);
		
		Button btnOkey = (Button) dialogPaymentTypeConfirm.findViewById(R.id.btn_okey);
		btnOkey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogPaymentTypeConfirm.dismiss();
				if(getIntent().getExtras() != null) {
					if(selectedType.equals("CARD")) {
						loadCardInfo();
					} else {
						DriverApp.getInstance().getBookingInfo().setPaymentMethod("Cash Payment");
						new PaymentAsyncTask(CalculateFareActivity.this, new OnRequestComplete() {
							
							@Override
							public void onRequestComplete(String result) {
								try {
									if("2001".equals(result)) {
										startActivity(new Intent(CalculateFareActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", isHailed));
										finish();
									}
								} catch (Exception e) {
									new CustomToast(CalculateFareActivity.this, "" + e.getMessage()).showToast();
								}
								
							}
						}).execute("1011", 
								DriverApp.getInstance().getDriverInfo().getTaxiId(), 
								DriverApp.getInstance().getProfileInfo().getId(), 
								DriverApp.getInstance().getBookingInfo().getUserId(), 
								DriverApp.getInstance().getBookingInfo().getBookingId(), 
								DriverApp.getInstance().getBookingInfo().getFare(), 
								DriverApp.getInstance().getBookingInfo().getFareAmount(), 
								DriverApp.getInstance().getBookingInfo().getGst(), 
								DriverApp.getInstance().getBookingInfo().getExtras(), 
								payment_method_id, 
								DriverApp.getInstance().getBookingInfo().getBookingDate(), 
								DriverApp.getInstance().getBookingInfo().getBookingTime(),
								"",
								"",
								"",
								"",
								"",
								"",
								"");
					}
//					if(getIntent().getExtras().getBoolean("is_hailed")) {
//						isLock = true;
//						startActivity(new Intent(CalculatorActivity.this, PaymentTypeActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
//						DriverApp.getInstance().getHailedCabInfo().setFare(String.format("%.2f", Double.parseDouble(edTxtAmount.getText().toString().trim())));
//					} else {
//						isLock = true;
//						startActivity(new Intent(CalculatorActivity.this, PaymentTypeActivity.class).putExtra("is_hailed", false));
//						DriverApp.getInstance().getBookingInfo().setFare(String.format("%.2f", Double.parseDouble(edTxtAmount.getText().toString().trim())));
//					}
					
				} else {
					
				}
			}
		});
		
		Button btnChange= (Button) dialogPaymentTypeConfirm.findViewById(R.id.btn_change);
		btnChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogPaymentTypeConfirm.dismiss();
				Log.e("Is Hailed Status from Calculate Fare", "----"+isHailed);
				startActivity(new Intent(CalculateFareActivity.this, PaymentTypeActivity.class).putExtra("is_hailed", isHailed));
				finish();
			}
		});
		
		Button btnExit = (Button) dialogPaymentTypeConfirm.findViewById(R.id.btn_exit);
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogPaymentTypeConfirm.dismiss();
			}
		});
		
		if (dialogPaymentTypeConfirm != null) {
			dialogPaymentTypeConfirm.show();
		}
		
	}
	
	private void extraChargePopup() {
		dialogExtraCharge = new DialogController(CalculateFareActivity.this).ExtraChargeDialog();
		
		final CheckBox checkBox1, checkBox2, checkBox3;
		checkBox1 = (CheckBox) dialogExtraCharge.findViewById(R.id.checkBox1);
		checkBox2 = (CheckBox) dialogExtraCharge.findViewById(R.id.checkBox2);
		checkBox3 = (CheckBox) dialogExtraCharge.findViewById(R.id.checkBox3);
		LinearLayout linearTolLayout = (LinearLayout) dialogExtraCharge.findViewById(R.id.linear_toll);
		final EditText edtTxtTollAmount = (EditText) dialogExtraCharge.findViewById(R.id.edt_txt_toll);
		boolean isToll = false;
		
		Button btnOk = (Button) dialogExtraCharge.findViewById(R.id.btn_ok);
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(InternetConnectivity.isConnectedToInternet(CalculateFareActivity.this)) {
					
				} else {
					new CustomToast(getApplicationContext(), ConstantValues.internetConnectionMsg).showToast();
					dialogExtraCharge.dismiss();
					return;
				}
				
				dialogExtraCharge.dismiss();
				if (checkBox1.isChecked()) {
					isAirport = true;
				} else {
					isAirport = false;
				}
				
				if (checkBox2.isChecked()) {
					isAirportRank = true;
				} else {
					isAirportRank = false;
				}
				
				if (checkBox3.isChecked()) {
					isHighOccupacy = true;
				} else {
					isHighOccupacy = false;
				}
				
				if(!edtTxtTollAmount.getText().toString().equals("")) {
					tollAmount = Double.parseDouble(edtTxtTollAmount.getText().toString());
				}
				
				double travelledHours = 0.0;
				
				try {
					long final_meter_end_time = ConstantValues.METER_END_TIME - ConstantValues.TIME_LOST;
					travelledHours = Utils.getTimeTraveledInHour(ConstantValues.METER_START_TIME, final_meter_end_time);
					travelledDistance = (ConstantValues.METER_END_TOTAL_SPEED_MPH/ConstantValues.METER_HIT_COUNTER) * travelledHours;
					speed_KMperHour = (ConstantValues.METER_END_TOTAL_SPEED_MPH/ConstantValues.METER_HIT_COUNTER) * ConstantValues.MPH_TO_KPH_CONVERTION;
					
				} catch (ParseException e) {
					travelledDistance = 0.0;
					speed_KMperHour = 0.0;
					e.printStackTrace();
				}
				
				loadData();
			}
			
			
		});
		
		dialogExtraCharge.show();
	}
	
	//Device related module
	@Override
	protected void onResume() {
		super.onResume();
	}
	
		
}
