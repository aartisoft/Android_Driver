package com.netcabs.datamodel;

public class ProfileInfo {
	private String profilePic;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String id;
	private int isMobileVerified;
	private int isEmailVerified;
	private String email;
	private String taxiNumber;
	private String vehicleClass;
	private String drivingLicenseImage;
	private String nidImage;

	public String getTaxiNumber() {
		return taxiNumber;
	}

	public void setTaxiNumber(String taxiNumber) {
		this.taxiNumber = taxiNumber;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(int isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public int getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(int isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDrivingLicenseImage() {
		return drivingLicenseImage;
	}

	public void setDrivingLicenseImage(String drivingLicenseImage) {
		this.drivingLicenseImage = drivingLicenseImage;
	}

	public String getNidImage() {
		return nidImage;
	}

	public void setNidImage(String nidImage) {
		this.nidImage = nidImage;
	}

}
