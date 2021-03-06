package com.netcabs.customviews;


import com.netcabs.driver.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class DialogController {

	private Activity activity;
	private Dialog dialogFaq;
	private Dialog dialogPaymentConfrim;
	private Dialog dialogEnterDeviceName;
	private Dialog dialogExtraCharge;
	private Dialog dialogDecline;
	private Dialog dialogConfirmCommon;
	private Dialog dialogPaymentTypeConfirm;
	private Dialog dialogNavigation;
	
	public DialogController(Activity activity) {
		this.activity = activity;
	}
	
	public Dialog confirmDialog() {
		dialogFaq = new Dialog(this.activity);
		dialogFaq.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogFaq.setContentView(R.layout.faq_popup);
		dialogFaq.setCanceledOnTouchOutside(true);
		dialogFaq.setCancelable(true);
		dialogFaq.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogFaq.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogFaq;
	}
	
	public Dialog paymentConfirmDialog() {
		dialogPaymentConfrim = new Dialog(this.activity);
		dialogPaymentConfrim.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogPaymentConfrim.setContentView(R.layout.payment_confirm_popup);
		dialogPaymentConfrim.setCanceledOnTouchOutside(true);
		dialogPaymentConfrim.setCancelable(true);
		dialogPaymentConfrim.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogPaymentConfrim.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogPaymentConfrim;
	}
	
	public Dialog paymentTypeConfirmDialog() {
		dialogPaymentTypeConfirm = new Dialog(this.activity);
		dialogPaymentTypeConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogPaymentTypeConfirm.setContentView(R.layout.payment_type_confirmation_popup);
		dialogPaymentTypeConfirm.setCanceledOnTouchOutside(true);
		dialogPaymentTypeConfirm.setCancelable(true);
		dialogPaymentTypeConfirm.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogPaymentTypeConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogPaymentTypeConfirm;
	}
	
	public Dialog declineDialog() {
		dialogDecline = new Dialog(this.activity);
		dialogDecline.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogDecline.setContentView(R.layout.decline_popup);
		dialogDecline.setCanceledOnTouchOutside(true);
		dialogDecline.setCancelable(true);
		dialogDecline.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogDecline.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogDecline;
	}
	
	public Dialog ConfirmCommonDialog() {
		dialogConfirmCommon = new Dialog(this.activity);
		dialogConfirmCommon.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogConfirmCommon.setContentView(R.layout.confirm_popup);
		dialogConfirmCommon.setCanceledOnTouchOutside(true);
		dialogConfirmCommon.setCancelable(true);
		dialogConfirmCommon.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogConfirmCommon.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogConfirmCommon;
	}
	
	public Dialog ExtraChargeDialog() {
		dialogExtraCharge = new Dialog(this.activity);
		dialogExtraCharge.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogExtraCharge.setContentView(R.layout.extra_charge_popup);
		dialogExtraCharge.setCanceledOnTouchOutside(false);
		dialogExtraCharge.setCancelable(true);
		dialogExtraCharge.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogExtraCharge.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogExtraCharge;
	}
	
	public Dialog EnterDeviceNameDialog() {
		dialogEnterDeviceName = new Dialog(this.activity);
		dialogEnterDeviceName.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogEnterDeviceName.setContentView(R.layout.enter_device_name_popup);
		dialogEnterDeviceName.setCanceledOnTouchOutside(false);
		dialogEnterDeviceName.setCancelable(true);
		dialogEnterDeviceName.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogEnterDeviceName.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogEnterDeviceName;
	}
	
	
	public Dialog NavigationDialog() {
		dialogNavigation = new Dialog(this.activity);
		dialogNavigation.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogNavigation.setContentView(R.layout.dialog_obd_reading);
		dialogNavigation.setCanceledOnTouchOutside(false);
		dialogNavigation.setCancelable(true);
		dialogNavigation.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		dialogNavigation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogNavigation;
	}
	
	
}
