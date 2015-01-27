package com.netcabs.driver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.netcabs.asynctask.FitToDriveAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.customviews.DialogController;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;

public class FAQActivity extends Activity implements OnClickListener, OnCheckedChangeListener {

	private TextView txtViewQuestion;
	private Button btnNext;
	
	private RadioButton radioBtnOne;
	private RadioButton radioBtnTwo;
	private RadioButton radioBtnThree;
	
	private Dialog dialogFag;
	private String rightAnswer = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_faq);

		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		txtViewQuestion = (TextView) findViewById(R.id.txt_view_question);
		btnNext = (Button) findViewById(R.id.btn_next);
		
		radioBtnOne = (RadioButton) findViewById(R.id.radio_btn_one);
		radioBtnTwo = (RadioButton) findViewById(R.id.radio_btn_two);
		radioBtnThree = (RadioButton) findViewById(R.id.radio_btn_three);
	}

	private void setListener() {
		btnNext.setOnClickListener(this);
		((RadioGroup) findViewById(R.id.radio_group)).setOnCheckedChangeListener(this);
	}

	private void loadData() {
		if(InternetConnectivity.isConnectedToInternet(FAQActivity.this)) {
			new FitToDriveAsyncTask(FAQActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							txtViewQuestion.setText(DriverApp.getInstance().getFaqInfo().getQuestion().toString());
							radioBtnOne.setText(DriverApp.getInstance().getFaqInfo().getAnswerOne().toString());
							radioBtnTwo.setText(DriverApp.getInstance().getFaqInfo().getAnswerTwo().toString());
							radioBtnThree.setText(DriverApp.getInstance().getFaqInfo().getAnswetThree().toString());
						} else {
							new CustomToast(FAQActivity.this, "Data not found").showToast();
						}
					} catch (Exception e ) {
						new CustomToast(FAQActivity.this, "" + e.getMessage()).showToast();
						Log.i("Exception 1003", "***" + e.getMessage());
					}
					
				}
			}).execute("1002", DriverApp.getInstance().getDriverInfo().getTaxiId());
		} else {
			new CustomToast(FAQActivity.this, ConstantValues.internetConnectionMsg).showToast();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			if(radioBtnOne.isChecked() || radioBtnTwo.isChecked() || radioBtnThree.isChecked()) {
				showConfirmPopup();
			} else {
				new CustomToast(FAQActivity.this, "Please select at least one answer").showToast();
			}
			break;

		default:
			break;
		}
	}

	private void showConfirmPopup() {
		dialogFag = new DialogController(FAQActivity.this).confirmDialog();
		
		Button btnExit = (Button) dialogFag.findViewById(R.id.btn_exit);
		
		Button btnTryAgian = (Button) dialogFag.findViewById(R.id.btn_try_agian);
		btnTryAgian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rightAnswer = "";
				radioBtnOne.setChecked(false);
				radioBtnTwo.setChecked(false);
				radioBtnThree.setChecked(false);
				
				radioBtnOne.setButtonDrawable(R.drawable.ans_mark);
				radioBtnTwo.setButtonDrawable(R.drawable.ans_mark);
				radioBtnThree.setButtonDrawable(R.drawable.ans_mark);
				
				dialogFag.dismiss();
			}
		});
		TextView txtViewMsg = (TextView) dialogFag.findViewById(R.id.txt_view_msg);
		
		if(rightAnswer.equals(DriverApp.getInstance().getFaqInfo().getRightAnswer())) {
			btnTryAgian.setVisibility(View.INVISIBLE);
			txtViewMsg.setText("Ready to start");
			btnExit.setVisibility(View.INVISIBLE);
			
			new CountDownTimer(3000, 1000) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					
				}
				
				@Override
				public void onFinish() {
					dialogFag.dismiss();
					finish();
					startActivity(new Intent(FAQActivity.this, MainMenuActivity.class));
				}
			}.start();
		} else {
			txtViewMsg.setText("Wrong Answer.");
			btnTryAgian.setVisibility(View.VISIBLE);
		}
		
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rightAnswer = "";
				radioBtnOne.setChecked(false);
				radioBtnTwo.setChecked(false);
				radioBtnThree.setChecked(false);
				
				radioBtnOne.setButtonDrawable(R.drawable.ans_mark);
				radioBtnTwo.setButtonDrawable(R.drawable.ans_mark);
				radioBtnThree.setButtonDrawable(R.drawable.ans_mark);
				
				dialogFag.dismiss();
			}
		});
		
		dialogFag.show();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab();
	}
	
	private void switchTab() {
		int selectedTab = ((RadioGroup) findViewById(R.id.radio_group)).getCheckedRadioButtonId();
		
		switch (selectedTab) {
			case R.id.radio_btn_one:
				rightAnswer = "1";
				radioBtnOne.setButtonDrawable(R.drawable.ans_mark_over);
				radioBtnTwo.setButtonDrawable(R.drawable.ans_mark);
				radioBtnThree.setButtonDrawable(R.drawable.ans_mark);
				break;
				
			case R.id.radio_btn_two:
				rightAnswer = "2";
				radioBtnOne.setButtonDrawable(R.drawable.ans_mark);
				radioBtnTwo.setButtonDrawable(R.drawable.ans_mark_over);
				radioBtnThree.setButtonDrawable(R.drawable.ans_mark);
				break;
				
			case R.id.radio_btn_three:
				rightAnswer = "3";
				radioBtnOne.setButtonDrawable(R.drawable.ans_mark);
				radioBtnTwo.setButtonDrawable(R.drawable.ans_mark);
				radioBtnThree.setButtonDrawable(R.drawable.ans_mark_over);
				break;
	
			default:
				break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
