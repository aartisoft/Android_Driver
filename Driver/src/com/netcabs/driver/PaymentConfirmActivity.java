package com.netcabs.driver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;
import com.netcabs.utils.Mail;
import com.netcabs.utils.Utils;

public class PaymentConfirmActivity extends Activity implements OnClickListener {

	private TextView txtViewPassengerName;
	private TextView txtViewPickupAddress;
	private TextView txtViewDestination;
	private TextView txtViewDate;
	private TextView txtViewTime;
	private TextView txtViewDistance;
	private TextView txtViewTotalTripTime;
	private TextView txtViewPayment;
	private TextView txtViewFare;
	
	private Button printReceipt;
	private Button mainMenu;
	
	RelativeLayout relativeLayoutMain;
	
	private boolean isHailed = false;
	public boolean isLock = false;

	private ProgressDialog progressDialog;
	
	private static final String GMAIL_EMAIL_ID = "atomixdroid@gmail.com";
	private static final String GMAIL_ACCOUNT_PASSWORD = "Atomix123";
	private static String TO_ADDRESSES = "atomixdroid@gmail.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_payment_confirm);

		initViews();
		setListener();
		loadData();
		
	}

	private void initViews() {
		txtViewPassengerName = (TextView) findViewById(R.id.txt_view_passenger_name);
		txtViewPickupAddress = (TextView) findViewById(R.id.txt_view_pickup_address);
		txtViewDestination = (TextView) findViewById(R.id.txt_view_destination);
		txtViewDate = (TextView) findViewById(R.id.txt_view_date);
		txtViewTime = (TextView) findViewById(R.id.txt_view_time);
		txtViewDistance = (TextView) findViewById(R.id.txt_view_distance);
		txtViewTotalTripTime = (TextView) findViewById(R.id.txt_view_total_trip_time);
		txtViewPayment = (TextView) findViewById(R.id.txt_view_payment);
		txtViewFare = (TextView) findViewById(R.id.txt_view_fare);
		
		printReceipt = (Button) findViewById(R.id.btn_print_receipt);
		printReceipt.setEnabled(false);
		printReceipt.setAlpha(0.5f);
		mainMenu = (Button) findViewById(R.id.btn_main_menu);
		
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.relative_main);
	}

	private void setListener() {
		printReceipt.setOnClickListener(this);
		mainMenu.setOnClickListener(this);
		((Button) findViewById(R.id.btn_back)).setOnClickListener(this);
	}

	private void loadData() {
		if(getIntent().getExtras() != null) {
			isHailed = getIntent().getExtras().getBoolean("is_hailed");
		}
		
		if(isHailed) {
			txtViewPassengerName.setText(DriverApp.getInstance().getHailedCabInfo().getPassengerFullName());
			txtViewPickupAddress.setText(DriverApp.getInstance().getHailedCabInfo().getPickupName());
			txtViewDestination.setText(DriverApp.getInstance().getHailedCabInfo().getDestinationName());
			try {
				txtViewDate.setText(Utils.getTimeDateFormat(DriverApp.getInstance().getHailedCabInfo().getDate()));
				txtViewTime.setText(Utils.getTimeDateFormat(DriverApp.getInstance().getHailedCabInfo().getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			txtViewDistance.setText(DriverApp.getInstance().getHailedCabInfo().getDistance());
			txtViewTotalTripTime.setText(DriverApp.getInstance().getHailedCabInfo().getDuration());
			
			/*LatLng pickUpLatLng = new LatLng(DriverApp.getInstance().getHailedCabInfo().getPickupLat(), DriverApp.getInstance().getHailedCabInfo().getPickupLong());
			LatLng destinationLatLng = new LatLng(DriverApp.getInstance().getHailedCabInfo().getDestinationLat(), DriverApp.getInstance().getHailedCabInfo().getDestinationLong());
			
			if(InternetConnectivity.isConnectedToInternet(PaymentConfirmActivity.this)) {
				new GetDurationAsyncTask(PaymentConfirmActivity.this, pickUpLatLng, destinationLatLng,  new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						Log.e("Duration is:", "::::" + result);
						
						try {
							if(result.contains("null")){
								result.replace("null", "00");
							}
							//String [] distanceDuration = result.split("//");
							try {
								Log.e("Duration is:", "::::"+result);
								String [] distanceDuration = result.split("//");
								
								DriverApp.getInstance().getHailedCabInfo().setDuration(Utils.getFormatedDuration(distanceDuration[1]));
								DriverApp.getInstance().getHailedCabInfo().setDistance(Utils.getFormatedDistance(distanceDuration[0]));
								txtViewDistance.setText(DriverApp.getInstance().getHailedCabInfo().getDistance());
								txtViewTotalTripTime.setText(DriverApp.getInstance().getHailedCabInfo().getDuration());
							} catch (Exception e) {
								new CustomToast(PaymentConfirmActivity.this, "" + e.getMessage()).showToast();
							}
						} catch (Exception e) {
							new CustomToast(PaymentConfirmActivity.this, "" + e.getMessage()).showToast();
							Log.e("Error 1022", "---" + e.getMessage());
						}
						
					}
				}).execute();
			} else {
				
			}*/
			
			/*int length = DriverApp.getInstance().getPaymentsInfoList().size();
			
			for (int i = 0; i < length; i++) {
				if (DriverApp.getInstance().getPaymentsInfoList().get(i).getId().equalsIgnoreCase(DriverApp.getInstance().getHailedCabInfo().getPaymentType())) {
					txtViewPayment.setText(DriverApp.getInstance().getPaymentsInfoList().get(i).getMethodName());
				}
			}*/
			
			txtViewPayment.setText(DriverApp.getInstance().getHailedCabInfo().getPaymentType());
			txtViewFare.setText("$ " + String.format("%.2f", Double.parseDouble(DriverApp.getInstance().getHailedCabInfo().getFare())));
			
		} else {
			printReceipt.setEnabled(true);
			printReceipt.setAlpha(1.0f);
			txtViewPassengerName.setText(DriverApp.getInstance().getBookingInfo().getFirstName() + " " + DriverApp.getInstance().getBookingInfo().getLastName());
			txtViewPickupAddress.setText(DriverApp.getInstance().getBookingInfo().getPickupAddress());
			txtViewDestination.setText(DriverApp.getInstance().getBookingInfo().getDestinationAddress());
			try {
				txtViewDate.setText(Utils.getTimeDateFormat(DriverApp.getInstance().getBookingInfo().getBookingDate()));
				txtViewTime.setText(Utils.getTimeDateFormat(DriverApp.getInstance().getBookingInfo().getBookingTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			txtViewDistance.setText(DriverApp.getInstance().getBookingInfo().getDistance());
			txtViewTotalTripTime.setText(DriverApp.getInstance().getBookingInfo().getDuration());
			
			/*LatLng pickUpLatLng = new LatLng(DriverApp.getInstance().getBookingInfo().getPickupLat(), DriverApp.getInstance().getBookingInfo().getPickupLon());
			LatLng destinationLatLng = new LatLng(DriverApp.getInstance().getBookingInfo().getDestinationLat(), DriverApp.getInstance().getBookingInfo().getDestinationLon());
			
			if(InternetConnectivity.isConnectedToInternet(PaymentConfirmActivity.this)) {
				new GetDurationAsyncTask(PaymentConfirmActivity.this, pickUpLatLng, destinationLatLng,  new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						
						try {
							Log.e("Duration is:", "::::"+result);
							String [] distanceDuration = result.split("//");
							
							DriverApp.getInstance().getBookingInfo().setDuration(Utils.getFormatedDuration(distanceDuration[1]));
							DriverApp.getInstance().getBookingInfo().setDistance(Utils.getFormatedDistance(distanceDuration[0]));
							txtViewDistance.setText(DriverApp.getInstance().getBookingInfo().getDistance());
							txtViewTotalTripTime.setText(DriverApp.getInstance().getBookingInfo().getDuration());
						} catch (Exception e) {
							new CustomToast(PaymentConfirmActivity.this, "" + e.getMessage()).showToast();
						}
						
					}
				}).execute();
			} else {
				
			}*/
			
			int length = DriverApp.getInstance().getPaymentsInfoList().size();
			if(DriverApp.getInstance().getBookingInfo().getPaymentMethod()!= null) {
				txtViewPayment.setText(DriverApp.getInstance().getBookingInfo().getPaymentMethod());
				Log.e("payment method is", "///"+DriverApp.getInstance().getBookingInfo().getPaymentMethod());
//				if("Auto Payment".equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod().trim()) || "Card Payment".equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod().trim()) || "Cash Payment".equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod().trim())) {
//					Log.e("I am here in else for payment", "------");
//					txtViewPayment.setText(DriverApp.getInstance().getBookingInfo().getPaymentMethod());
//				} else {
//					for (int i = 0; i < length; i++) {
//						if (DriverApp.getInstance().getPaymentsInfoList().get(i).getId().equalsIgnoreCase(DriverApp.getInstance().getBookingInfo().getPaymentMethod().trim())) {
//							txtViewPayment.setText(DriverApp.getInstance().getPaymentsInfoList().get(i).getMethodName());
//						} 
//					}
//				} 
			} else {
				txtViewPayment.setText("Undefined");
			}
			
			txtViewFare.setText("$ " + String.format("%.2f", Double.parseDouble(DriverApp.getInstance().getBookingInfo().getFare())));
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_print_receipt:
			if(!isHailed) {
				new GeneratePdf().execute();
			}
			break;
			
		case R.id.btn_main_menu:
			ConstantValues.isBack = true;
			isLock = true;
			DriverApp.getInstance().setBookingInfo(null);
			DriverApp.getInstance().setDestinationInfo(null);
			DriverApp.getInstance().setHailedCabInfo(null);
			DriverApp.getInstance().setBookingId(null);
			
//			Intent intent = new Intent(PaymentConfirmActivity.this, LoginActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//			startActivity(intent);
			finish();
			
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}
	
	
	private class GeneratePdf extends AsyncTask<String, Void, String> {
		View v1;
		Bitmap bm;
		
		@Override
		protected void onPreExecute() {
			printReceipt.setVisibility(View.INVISIBLE);
			mainMenu.setVisibility(View.INVISIBLE);
			
			v1 = relativeLayoutMain.getRootView();
			v1.setDrawingCacheEnabled(true);
			bm = v1.getDrawingCache();
			
			progressDialog = ProgressDialog.show(PaymentConfirmActivity.this, "Creating PDF", "Please wait...", true, false);
			
		}

		@Override
		protected String doInBackground(String... urls) {
			try {
				
				Mail m = new Mail(GMAIL_EMAIL_ID, GMAIL_ACCOUNT_PASSWORD);
				if(DriverApp.getInstance().getBookingInfo().getPassengerEmailId()!= null) {
					TO_ADDRESSES = DriverApp.getInstance().getBookingInfo().getPassengerEmailId();
				}
			
				String toAddresses = TO_ADDRESSES;
				m.setToAddresses(toAddresses);
				m.setFromAddress(GMAIL_EMAIL_ID);
				m.setMailSubject("Oiii...");
				m.setMailBody("Payment Confirmed Receipt");
				
				String root = Environment.getExternalStorageDirectory().toString();
				
				
				File myDir = new File(root + "/NetCabs");
				myDir.mkdirs();
				Random generator = new Random();
				int n = 10000;
				n = generator.nextInt(n);
				String fname = "Image-" + n + ".png";
				File file = new File(myDir, fname);
				if (file.exists())
					file.delete();
				try {
					FileOutputStream out = new FileOutputStream(file);
					bm.compress(Bitmap.CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					
		
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				Document document = new Document(PageSize.A4.rotate(),0,0,0,0);
				Image image = null;
		
				try {
					PdfWriter.getInstance(document, new FileOutputStream(root + "/NetCabs/" + "YourPDFHere.pdf"));
					document.open();
					image = Image.getInstance(root + "/NetCabs/" + fname);
					image.scalePercent(60f);
					document.add(image);
					document.close();
					
					m.addAttachment(root + "/NetCabs/" + fname);

					if (m.send()) {
						new File(root + "/NetCabs/" + fname).delete();
						return "1";
					} else {
						new File(root + "/NetCabs/" + fname).delete();
						return "2";
					}
					
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				
			}
			
			return "3";

		}

		@Override
		protected void onPostExecute(String result) {
			
			if(progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			
			printReceipt.setVisibility(View.VISIBLE);
			mainMenu.setVisibility(View.VISIBLE);
			
			if ("1".equals(result)) {
				Toast.makeText(PaymentConfirmActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
			} else if ("2".equals(result)) {
				Toast.makeText(PaymentConfirmActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
			} else if ("3".equals(result)) {
				Toast.makeText(PaymentConfirmActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		isLock = true;
		//super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

}
