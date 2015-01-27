package com.netcabs.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.netcabs.datamodel.BookingInfo;
import com.netcabs.datamodel.CreditCardInfo;
import com.netcabs.datamodel.DestinationInfo;
import com.netcabs.datamodel.DriverInfo;
import com.netcabs.datamodel.FaqInfo;
import com.netcabs.datamodel.FastTripsInfo;
import com.netcabs.datamodel.HailedCabInfo;
import com.netcabs.datamodel.PaymentsInfo;
import com.netcabs.datamodel.ProfileInfo;

public class DriverApp {

	public static DriverApp instance;

	private String baseUrl = "http://114.134.91.91:8000/action";
	private ArrayList<FastTripsInfo> fastTripsInfoList;
	private ArrayList<CreditCardInfo> creditCardInfoList;
	private ProfileInfo profileInfo;
	private FaqInfo faqInfo;
	private BookingInfo bookingInfo;
	private HailedCabInfo hailedCabInfo;
	private DestinationInfo destinationInfo;
	private ArrayList<PaymentsInfo> paymentsInfoList;
	private String bookingId;
	private DriverInfo driverInfo;
	
	
	private boolean isLock = false;

	public static DriverApp getInstance() {
		if (instance == null) {
			instance = new DriverApp();
		}
		return instance;
	}

	public static void destroyInstance() {
		instance = null;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public ArrayList<FastTripsInfo> getFastTripsInfoList() {
		return fastTripsInfoList;
	}

	public void setFastTripsInfoList(ArrayList<FastTripsInfo> fastTripsInfoList) {
		this.fastTripsInfoList = fastTripsInfoList;
	}

	public ProfileInfo getProfileInfo() {
		return profileInfo;
	}

	public void setProfileInfo(ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;
	}

	public FaqInfo getFaqInfo() {
		return faqInfo;
	}

	public void setFaqInfo(FaqInfo faqInfo) {
		this.faqInfo = faqInfo;
	}

	public BookingInfo getBookingInfo() {
		return bookingInfo;
	}

	public void setBookingInfo(BookingInfo bookingInfo) {
		this.bookingInfo = bookingInfo;
	}
	
	public void hideKeyboard(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public HailedCabInfo getHailedCabInfo() {
		return hailedCabInfo;
	}

	public void setHailedCabInfo(HailedCabInfo hailedCabInfo) {
		this.hailedCabInfo = hailedCabInfo;
	}

	public DestinationInfo getDestinationInfo() {
		return destinationInfo;
	}

	public void setDestinationInfo(DestinationInfo destinationInfo) {
		this.destinationInfo = destinationInfo;
	}

	public ArrayList<PaymentsInfo> getPaymentsInfoList() {
		return paymentsInfoList;
	}

	public void setPaymentsInfoList(ArrayList<PaymentsInfo> paymentsInfoList) {
		this.paymentsInfoList = paymentsInfoList;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public DriverInfo getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(DriverInfo driverInfo) {
		this.driverInfo = driverInfo;
	}
	
	public ArrayList<CreditCardInfo> getCreditCardInfoList() {
		return creditCardInfoList;
	}

	public void setCreditCardInfoList(ArrayList<CreditCardInfo> creditCardInfoList) {
		this.creditCardInfoList = creditCardInfoList;
	}

}
