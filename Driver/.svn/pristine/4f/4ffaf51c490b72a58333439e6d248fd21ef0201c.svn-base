package com.netcabs.driver;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netcabs.customviews.CustomToast;
import com.netcabs.customviews.DialogController;
import com.netcabs.utils.DriverApp;

public class CalculatorActivity extends Activity implements OnClickListener {

	private EditText edTxtAmount;
	private Dialog dialogPaymentConfirm;
	private boolean isLock = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_calculator);

		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		edTxtAmount = (EditText) findViewById(R.id.ed_txt_amount);
	}

	private void setListener() {
		((Button) findViewById(R.id.btn_one)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_two)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_three)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_four)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_five)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_six)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_seven)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_eight)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_nine)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_zero)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_dot)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_delete)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_end_trip)).setOnClickListener(this);
	}

	private void loadData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_one:
			makeCalculator("1");
			break;
			
		case R.id.btn_two:
			makeCalculator("2");
			break;
			
		case R.id.btn_three:
			makeCalculator("3");
			break;
			
		case R.id.btn_four:
			makeCalculator("4");
			break;
			
		case R.id.btn_five:
			makeCalculator("5");
			break;
			
		case R.id.btn_six:
			makeCalculator("6");
			break;
			
		case R.id.btn_seven:
			makeCalculator("7");
			break;
			
		case R.id.btn_eight:
			makeCalculator("8");
			break;
			
		case R.id.btn_nine:
			makeCalculator("9");
			break;
			
		case R.id.btn_zero:
			makeCalculator("0");
			break;
			
		case R.id.btn_dot:
			makeCalculator(".");
			break;
			
		case R.id.btn_delete:
			makeCalculator("del");
			break;
			
		case R.id.btn_end_trip:
			if(!edTxtAmount.getText().toString().trim().equalsIgnoreCase("")) {
				processConfirmPopup();
			} else {
				new CustomToast(CalculatorActivity.this, "Please enter proper amount").showToast();
			}
			break;

		default:
			break;
		}
	}

	private void processConfirmPopup() {
		dialogPaymentConfirm = new DialogController(CalculatorActivity.this).paymentConfirmDialog();
		
		TextView txtViewAmount = (TextView) dialogPaymentConfirm.findViewById(R.id.txt_view_amount);
		txtViewAmount.setText(String.format("%.2f", Double.parseDouble(edTxtAmount.getText().toString().trim())));
		
		TextView txtViewMsg = (TextView) dialogPaymentConfirm.findViewById(R.id.txt_view_msg);
		txtViewMsg.setText("Are you sure?");
		
		Button btnOkey = (Button) dialogPaymentConfirm.findViewById(R.id.btn_okey);
		btnOkey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogPaymentConfirm.dismiss();
				if(getIntent().getExtras() != null) {
					if(getIntent().getExtras().getBoolean("is_hailed")) {
						isLock = true;
						startActivity(new Intent(CalculatorActivity.this, PaymentTypeActivity.class).putExtra("is_hailed", getIntent().getExtras().getBoolean("is_hailed")));
						DriverApp.getInstance().getHailedCabInfo().setFare(String.format("%.2f", Double.parseDouble(edTxtAmount.getText().toString().trim())));
					} else {
						isLock = true;
						startActivity(new Intent(CalculatorActivity.this, PaymentTypeActivity.class).putExtra("is_hailed", false));
						DriverApp.getInstance().getBookingInfo().setFare(String.format("%.2f", Double.parseDouble(edTxtAmount.getText().toString().trim())));
					}
					
				} else {
					
				}
			}
		});
		
		Button btnExit = (Button) dialogPaymentConfirm.findViewById(R.id.btn_exit);
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogPaymentConfirm.dismiss();
			}
		});
		
		dialogPaymentConfirm.show();
	}

	private void makeCalculator(String value) {
		if("0".equals(value)) {
			if(!edTxtAmount.getText().toString().equalsIgnoreCase("")) {
				edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "0");
			} else {
				
			}
			
		} else if ("del".equals(value)) {
			if(!edTxtAmount.getText().toString().equalsIgnoreCase("")) {
				if(edTxtAmount.getText().toString().trim().startsWith("0.") && edTxtAmount.getText().toString().trim().length() == 2) {
					edTxtAmount.setText(edTxtAmount.getText().toString().trim().subSequence(0, edTxtAmount.getText().toString().trim().length() - 2));
				} else {
					edTxtAmount.setText(edTxtAmount.getText().toString().trim().subSequence(0, edTxtAmount.getText().toString().trim().length() - 1));
				}
			} else {
				
			}
			
		} else if(".".equals(value)) {
			if(!edTxtAmount.getText().toString().trim().contains(".")) {
				if(edTxtAmount.getText().toString().equalsIgnoreCase("")) {
					edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "0.");
				} else {
					edTxtAmount.setText(edTxtAmount.getText().toString().trim() + ".");
				}
			} else {
				
			}
			
		} else if("1".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "1");
		} else if("2".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "2");
		} else if("3".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "3");
		} else if("4".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "4");
		} else if("5".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "5");
		} else if("6".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "6");
		} else if("7".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "7");
		} else if("8".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "8");
		} else if("9".equals(value)) {
			edTxtAmount.setText(edTxtAmount.getText().toString().trim() + "9");
		} else {
			
		}
	}
	@Override
	public void onBackPressed() {
		isLock = true;
	//	super.onBackPressed();
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
