package com.netcabs.datamodel;

public class HailedCabInfo {
	private UserInfo user;
	private String passengerFullName;
	private String pickupName;
	private double pickupLat;
	private double pickupLong;
	private String destinationName;
	private double destinationLat;
	private double destinationLong;
	private String date;
	private String fare;
	private String distance;
	private String paymentType;
	private String bookingId;
	private String time;
	private String duration;
	private int isParcel;
	private int passengerNumber;
	private String fareAmount;
	private String gst;
	private String extras;
	
	
	public String getPickupName() {
		return pickupName;
	}
	public void setPickupName(String pickupName) {
		this.pickupName = pickupName;
	}
	public double getPickupLat() {
		return pickupLat;
	}
	public void setPickupLat(double pickupLat) {
		this.pickupLat = pickupLat;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public double getDestinationLat() {
		return destinationLat;
	}
	public void setDestinationLat(double destinationLat) {
		this.destinationLat = destinationLat;
	}
	public double getPickupLong() {
		return pickupLong;
	}
	public void setPickupLong(double pickupLong) {
		this.pickupLong = pickupLong;
	}
	public double getDestinationLong() {
		return destinationLong;
	}
	public void setDestinationLong(double destinationLong) {
		this.destinationLong = destinationLong;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPassengerFullName() {
		return passengerFullName;
	}
	public void setPassengerFullName(String passengerFullName) {
		this.passengerFullName = passengerFullName;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int getIsParcel() {
		return isParcel;
	}
	public void setIsParcel(int isParcel) {
		this.isParcel = isParcel;
	}
	public int getPassengerNumber() {
		return passengerNumber;
	}
	public void setPassengerNumber(int passengerNumber) {
		this.passengerNumber = passengerNumber;
	}
	public String getFareAmount() {
		return fareAmount;
	}
	public void setFareAmount(String fareAmount) {
		this.fareAmount = fareAmount;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}

}
