package com.netcabs.datamodel;

import com.netcabs.utils.ConstantValues;

import android.util.Log;

public class FareCalculationModel {
	private int tripLocationZone;
	private int timeOftheDay; // 0 for 9AM -5PM, 1 for 5PM - 9AM, 2 for (10AM to 4AM Friday & Saturday night)
	private double totalCalculateFare = 0.0;
	private boolean isHighOccupacy;
	private boolean isHoliday;
	private boolean ishighSpeed;
	private double totalDistance;
	private double totalTime;
	private boolean isCardPay;
	private boolean isBooking;
	private boolean isLateNight;
	private boolean isAirport;
	private boolean isAirportRank;
	private double gst = 0.0;
	private double extras = 0.0 ;
	private double onlyFareAmount = 0.0;
	private double tollAmount = 0.0;
	
	public FareCalculationModel(int tripLocationZone, boolean ishighSpeed, int timeOftheDay, boolean isHighOccupacy, boolean isHoliday, double totalDistance, double totalTimeInMinute, boolean isBooking,  boolean isCardPay, boolean isLateNight, boolean isAirport, boolean isAirportRank, double tollAmount){
		this.tripLocationZone = tripLocationZone;
		this.timeOftheDay = timeOftheDay;
		this.isHighOccupacy = isHighOccupacy;
		this.isHoliday = isHoliday;
		this.ishighSpeed = ishighSpeed;
		this.totalDistance = totalDistance;
		this.totalTime = totalTimeInMinute;
		this.isBooking = isBooking;
		this.isCardPay = isCardPay;
		this.isLateNight = isLateNight;
		this.isAirport = isAirport;
		this.isAirportRank = isAirportRank;
		this.tollAmount = tollAmount;
		
		
		if(tripLocationZone == 1) {
			Log.e("In MFDP", ""+"---------------");
			if(ishighSpeed) {
				totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
			} else {
				totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
			}
			onlyFareAmount = totalCalculateFare;
			extraChargeMFDP();
		} else if (tripLocationZone == 2 || tripLocationZone == 3) {
			if(ishighSpeed) {
				totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
			} else {
				totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
			}
			onlyFareAmount = totalCalculateFare;
			extraChargeGB_and_CR();
		}
	}
	
	
	private void extraChargeGB_and_CR() {
		totalCalculateFare = totalCalculateFare + tollAmount;
		extras = extras + tollAmount;
		if(isLateNight) {
			totalCalculateFare = totalCalculateFare + ConstantValues.lateNightFree;
			extras = ConstantValues.lateNightFree;
		}
		if(isHoliday) {
			totalCalculateFare = totalCalculateFare + ConstantValues.holidayFee;
			extras = extras + ConstantValues.holidayFee;
		}
		if(isBooking & !isAirport) {
			totalCalculateFare = totalCalculateFare + ConstantValues.bookingFee;
			extras = extras + ConstantValues.bookingFee;
		}
		
		if(isAirport) {
			totalCalculateFare = totalCalculateFare + ConstantValues.airportBookingFee;
			extras = extras + ConstantValues.airportBookingFee;
		}
		
		if(isCardPay) {
			totalCalculateFare = totalCalculateFare + (totalCalculateFare *5 /100) ;
			gst = totalCalculateFare * 5 /100;
			//extras = extras + (totalCalculateFare *5 /100);
		}
		
	}
	
	private void extraChargeMFDP() {
			totalCalculateFare = totalCalculateFare + tollAmount;
			extras = extras + tollAmount;
			if(isHighOccupacy) {
				totalCalculateFare = totalCalculateFare + ConstantValues.highOccupancyFee;
				extras = ConstantValues.highOccupancyFee;
			}
			if(isBooking & !isAirport) {
				totalCalculateFare = totalCalculateFare + ConstantValues.bookingFee;
				extras = extras + ConstantValues.bookingFee;
			}
			
			if(isAirport) {
				totalCalculateFare = totalCalculateFare + ConstantValues.airportBookingFee;
				extras = extras + ConstantValues.airportBookingFee;
			}
			if(isAirportRank) {
				totalCalculateFare = totalCalculateFare + ConstantValues.airportRankFee;
				extras = extras + ConstantValues.airportRankFee;
			}
		
			if(isCardPay) {
				totalCalculateFare = totalCalculateFare + (totalCalculateFare *5 /100) ;
				gst = totalCalculateFare * 5 /100;
				//extras = extras + (totalCalculateFare *5 /100);
			}
			
			}
	
	public double gettotalCalculateFare(){
		return totalCalculateFare;
		
	}


	public double getGst() {
		return gst;
	}


	public double getExtras() {
		return extras;
	}

	public double getOnlyFareAmount() {
		return onlyFareAmount;
	}



	/*private void highOccupancyFareCR() {
		
		if(ishighSpeed) {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		
		onlyFareAmount = totalCalculateFare;
		extraChargeGB_and_CR();
	}



	private void standardFareCR() {
		double flagFall = 3.70;
		double disPerKm = 1.879;
		double timePerMin = 0.658;	
		
		if(ishighSpeed) {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		onlyFareAmount = totalCalculateFare;
		extraChargeGB_and_CR();
	}



	private void highOccupancyFareGB() {
		double flagFall = 3.60;
		double disPerKm = 2.757;
		double timePerMin = 0.965;	
		
		if(ishighSpeed) {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		onlyFareAmount = totalCalculateFare;
		extraChargeGB_and_CR();
	}



	


	private void standardFareGB() {
		double flagFall = 3.60;
		double disPerKm = 1.838;
		double timePerMin = 0.643;	
		
		if(ishighSpeed) {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		onlyFareAmount = totalCalculateFare;
		extraChargeGB_and_CR();
	}



	private void peakFareMFDP() {
//		double flagFall = 6.20;
//		double disPerKm = 1.986;
//		double timePerMin = 0.695;	
	
		if(ishighSpeed) {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		onlyFareAmount = totalCalculateFare;
		extraChargeMFDP();
		
	}

	


	private void overNightFareMFDP() {
		double flagFall = 5.20;
		double disPerKm = 1.804;
		double timePerMin = 0.631;
		
		if(ishighSpeed) {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		onlyFareAmount = totalCalculateFare;
		extraChargeMFDP();
	}

	
	private void dayFareMFDP() {
		double flagFall = 4.20;
		double disPerKm = 1.622;
		double timePerMin = 0.568;
		
		if(ishighSpeed) {
			Log.e("In day fare high speed", ""+"---------------");
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.disPerKm * totalDistance);
		} else {
			Log.e("In day fare low speed", ""+"---------------");
			totalCalculateFare = ConstantValues.flagFall + (ConstantValues.timePerMin * totalTime);
		}
		onlyFareAmount = totalCalculateFare;
		extraChargeMFDP();
		
	}*/
	
	
}
