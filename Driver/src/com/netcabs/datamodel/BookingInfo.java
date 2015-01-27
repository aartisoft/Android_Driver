package com.netcabs.datamodel;

public class BookingInfo {
	private String firstName;
	private String lastName;
	private String userId;
	private String paymentMethod;
	private String bookingTime;
	private String pickupAddress;
	private double pickupLat;
	private double pickupLon;
	private int passengerNumber;
	private String bookingId;
	private int isParsel;
	private double destinationLat;
	private double destinationLon;
	private String destinationAddress;
	private String bookingDate;
	private int bookingStatus;
	private String distance;
	private String duration;
	private String fare;
	private String passengerEmailId;
	private String fareAmount;
	private String gst;
	private String extras;
	private int bookingVia;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	
	public double getPickupLat() {
		return pickupLat;
	}
	public void setPickupLat(double pickupLat) {
		this.pickupLat = pickupLat;
	}
	
	public double getPickupLon() {
		return pickupLon;
	}
	public void setPickupLon(double pickupLon) {
		this.pickupLon = pickupLon;
	}
	
	public int getPassengerNumber() {
		return passengerNumber;
	}
	public void setPassengerNumber(int passengerNumber) {
		this.passengerNumber = passengerNumber;
	}
	
	public int getIsParsel() {
		return isParsel;
	}
	public void setIsParsel(int isParsel) {
		this.isParsel = isParsel;
	}
	
	public double getDestinationLat() {
		return destinationLat;
	}
	public void setDestinationLat(double destinationLat) {
		this.destinationLat = destinationLat;
	}
	
	public double getDestinationLon() {
		return destinationLon;
	}
	public void setDestinationLon(double destinationLon) {
		this.destinationLon = destinationLon;
	}
	
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	public int getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(int bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String getPassengerEmailId() {
		return passengerEmailId;
	}
	public void setPassengerEmailId(String passengerEmailId) {
		this.passengerEmailId = passengerEmailId;
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
	public int getBookingVia() {
		return bookingVia;
	}
	public void setBookingVia(int bookingVia) {
		this.bookingVia = bookingVia;
	}
	
}
