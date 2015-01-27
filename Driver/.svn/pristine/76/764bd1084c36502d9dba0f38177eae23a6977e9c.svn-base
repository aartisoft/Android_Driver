package com.netcabs.driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.netcabs.asynctask.CardListAsyncTask;
import com.netcabs.asynctask.PaymentAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;

public class PaymentTypeActivity extends Activity implements OnClickListener {
	
	private int length;
	private boolean isHailed = false;
	public boolean isLock = false;
	
	private String payment_method_id = "";
	public int defaultCardItemNo;
	private Button btnCardPay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_payment_type);
		
		if (!DriverApp.getInstance().isLock()) {
			DriverApp.getInstance().setLock(false);
		}
		
		initViews();
		setListener();
		loadData();
	}

	

	private void initViews() {
		btnCardPay = (Button) findViewById(R.id.btn_card_pay);
		btnCardPay.setEnabled(false);
		btnCardPay.setAlpha(0.5f);
		
	}

	private void setListener() {
		btnCardPay.setOnClickListener(this);
		((Button) findViewById(R.id.btn_cash_pay)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_back)).setOnClickListener(this);
	}

	private void loadData() {
		length = DriverApp.getInstance().getPaymentsInfoList().size();
		
		if(getIntent().getExtras() != null) {
			isHailed = getIntent().getExtras().getBoolean("is_hailed");
			if(!isHailed) {
				btnCardPay.setEnabled(true);
				btnCardPay.setAlpha(1.0f);
				loadCardInfo();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_card_pay:
			//processCreditCardReading();
			
			if(!InternetConnectivity.isConnectedToInternet(PaymentTypeActivity.this)) {
				Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
				return;
			}
			
			for (int i = 0; i < length; i++) {
				if (DriverApp.getInstance().getPaymentsInfoList().get(i).getMethodName().equalsIgnoreCase("Card Payment")) {
					payment_method_id = DriverApp.getInstance().getPaymentsInfoList().get(i).getId();
				}
			}
			
			if(isHailed) {
				DriverApp.getInstance().getHailedCabInfo().setPaymentType("Card Payment");
				new PaymentAsyncTask(PaymentTypeActivity.this, new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
								if(getIntent().getExtras() != null) {
									isLock = true;
									startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
									finish();
								} else {
									isLock = true;
									startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", false));
									finish();
								}
							}
						} catch (Exception e) {
							new CustomToast(PaymentTypeActivity.this, "" + e.getMessage()).showToast();
						}
						
					}
				}).execute("1011", 
						DriverApp.getInstance().getDriverInfo().getTaxiId(), 
						DriverApp.getInstance().getProfileInfo().getId(), 
						"545ca872481a597cb66ce738", 
						DriverApp.getInstance().getHailedCabInfo().getBookingId(), 
						DriverApp.getInstance().getHailedCabInfo().getFare(), 
						DriverApp.getInstance().getHailedCabInfo().getFareAmount(), 
						DriverApp.getInstance().getHailedCabInfo().getGst(), 
						DriverApp.getInstance().getHailedCabInfo().getExtras(),
						payment_method_id, 
						DriverApp.getInstance().getHailedCabInfo().getDate(), 
						DriverApp.getInstance().getHailedCabInfo().getTime(),
						"",
						"",
						"",
						"",
						"",
						"",
						"");
			
			} else {
				DriverApp.getInstance().getBookingInfo().setPaymentMethod("Card Payment");
				if(DriverApp.getInstance().getCreditCardInfoList() != null ) {
					new PaymentAsyncTask(PaymentTypeActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									if(getIntent().getExtras() != null) {
										isLock = true;
										startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
									} else {
										isLock = true;
										startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", false));
									}
								}
							} catch (Exception e) {
								new CustomToast(PaymentTypeActivity.this, "" + e.getMessage()).showToast();
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
				} else {
					Toast.makeText(getApplicationContext(), "Passenger do not have any registered card in his account! Try other payment methods.", Toast.LENGTH_SHORT).show();
				}
			
			}
			
			break;
			
		case R.id.btn_cash_pay:
			/*if(getIntent().getExtras() != null) {
				startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
			} else {
				startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", false));
			}*/
			
			if(!InternetConnectivity.isConnectedToInternet(PaymentTypeActivity.this)) {
				Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
				return;
			}
			
			for (int i = 0; i < length; i++) {
				if (DriverApp.getInstance().getPaymentsInfoList().get(i).getMethodName().equalsIgnoreCase("Cash Payment")) {
					payment_method_id = DriverApp.getInstance().getPaymentsInfoList().get(i).getId();
				}
			}
			
			if(isHailed) {
				DriverApp.getInstance().getHailedCabInfo().setPaymentType("Cash Payment");
				new PaymentAsyncTask(PaymentTypeActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								if(getIntent().getExtras() != null) {
									isLock = true;
									startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
									finish();
								} else {
									isLock = true;
									startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", false));
									finish();
								}
							}
						} catch (Exception e) {
							new CustomToast(PaymentTypeActivity.this, "" + e.getMessage()).showToast();
						}
					}
					
				}).execute("1011", 
						DriverApp.getInstance().getDriverInfo().getTaxiId(), 
						DriverApp.getInstance().getProfileInfo().getId(), 
						"544790ef481a590ebabe165b", 
						DriverApp.getInstance().getHailedCabInfo().getBookingId(), 
						DriverApp.getInstance().getHailedCabInfo().getFare(), 
						DriverApp.getInstance().getHailedCabInfo().getFareAmount(), 
						DriverApp.getInstance().getHailedCabInfo().getGst(), 
						DriverApp.getInstance().getHailedCabInfo().getExtras(), 
						payment_method_id, 
						DriverApp.getInstance().getHailedCabInfo().getDate(), 
						DriverApp.getInstance().getHailedCabInfo().getTime(),
						"",
						"",
						"",
						"",
						"",
						"",
						"");
			
			} else {
				DriverApp.getInstance().getBookingInfo().setPaymentMethod("Cash Payment");
				new PaymentAsyncTask(PaymentTypeActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								if(getIntent().getExtras() != null) {
									isLock = true;
									startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
								} else {
									isLock = true;
									startActivity(new Intent(PaymentTypeActivity.this, PaymentConfirmActivity.class).putExtra("is_hailed", false));
								}
							}
						} catch (Exception e) {
							new CustomToast(PaymentTypeActivity.this, "" + e.getMessage()).showToast();
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
			
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}
	
	private void loadCardInfo() {
		new CardListAsyncTask(PaymentTypeActivity.this, new OnRequestComplete() {
			
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

	private void processCreditCardReading() {
		if(InternetConnectivity.isConnectedToInternet(PaymentTypeActivity.this)) {
			new PaymentAsyncTask(PaymentTypeActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					
				}
			});
		} else {
			new CustomToast(PaymentTypeActivity.this, ConstantValues.internetConnectionMsg).showToast();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		isLock = true;
		//super.onBackPressed();
	}
	
}
