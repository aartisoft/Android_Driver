package com.netcabs.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ParseException;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Utils {
	
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,matrix, false);
		return resizedBitmap;

	}
	
	 public static String getBitmapPath(Context inContext, Bitmap inImage) {
		  String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "driver", null);
		  return getRealPathFromURI(inContext, Uri.parse(path));
	 // return Uri.parse(path).getPath().toString();
	}
	 
	public static String getTimeDateFormat(String timeDate) throws java.text.ParseException {
		 String formatedTimeDate = timeDate;
		 try {
			 if (timeDate.contains("/")) {
				 SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy");
				 Date dateObj = sdf.parse(timeDate);
				 //formatedTimeDate = new SimpleDateFormat("dd/MM/yyyy").format(dateObj);
				 
				 sdf.applyPattern("dd/MM/yy");
					
				 formatedTimeDate= sdf.format(dateObj);
				 
			 } else if (timeDate.contains(".")) {
				 SimpleDateFormat sdf = new SimpleDateFormat("d.M.yy");
				 Date dateObj = sdf.parse(timeDate);
				 //formatedTimeDate = new SimpleDateFormat("dd/MM/yyyy").format(dateObj);
				 
				 sdf.applyPattern("dd/MM/yy");
					
				 formatedTimeDate= sdf.format(dateObj);
				 
			 }else if(timeDate.contains(":")) {
				 if (timeDate.split(":").length == 3) {
					  SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
					  Date dateObj = sdf.parse(timeDate);
					  formatedTimeDate = new SimpleDateFormat("HH:mm:ss").format(dateObj);
				 } else {
					 formatedTimeDate = timeDate;
				 }
			}
			 
		 } catch (final ParseException e) {
		     e.printStackTrace();
		 }
		 
		return  formatedTimeDate;
	}
	
	public static double getTimeTraveledInHour(long tripStartTimeMilis, long tripEndTimeMilis) throws java.text.ParseException {
		long millisUntilFinished = tripEndTimeMilis - tripStartTimeMilis;
		int h = (int) (millisUntilFinished / 3600000);
		int s = (int) (millisUntilFinished / 1000) % 60;
	    int m = (int) (millisUntilFinished / 60000) % 60;
	    int  ms = (int) (millisUntilFinished / 10) % 100;
	    double timeTraveledInHour = h+(m/60.0)+(s/3600.0)+(ms/3600000);
	    String timeTravelRounded = String.format("%.4f",  timeTraveledInHour);
	    return Double.parseDouble(timeTravelRounded);
		
	}
	 
	 
	public static String getRealPathFromURI(Context context, Uri contentUri) {
		  String[] proj = { MediaStore.Images.Media.DATA };
		  
		  CursorLoader cursorLoader = new CursorLoader(context, 
		            contentUri, proj, null, null, null);        
		  Cursor cursor = cursorLoader.loadInBackground();
		  
		  int column_index = 
		    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		  cursor.moveToFirst();
		  return cursor.getString(column_index); 
	}
	
	public static LatLngBounds centerIncidentRouteOnMap(ArrayList<LatLng> copiedPoints) {
		double minLat = Integer.MAX_VALUE;
		double maxLat = Integer.MIN_VALUE;
		double minLon = Integer.MAX_VALUE;
		double maxLon = Integer.MIN_VALUE;
		for (LatLng point : copiedPoints) {
			maxLat = Math.max(point.latitude, maxLat);
			minLat = Math.min(point.latitude, minLat);
			maxLon = Math.max(point.longitude, maxLon);
			minLon = Math.min(point.longitude, minLon);
		}
		final LatLngBounds bounds = new LatLngBounds.Builder()
				.include(new LatLng(maxLat, maxLon))
				.include(new LatLng(minLat, minLon)).build();
		
		return bounds;
		
	}
	
	public static String formatedDate(String dateString) throws java.text.ParseException{
		
		/*String created_at = dt;
		try {
			SimpleDateFormat toFullDate = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			TimeZone tz = cal.getTimeZone();
		
			toFullDate.setTimeZone(tz);
			Date fullDate = toFullDate.parse(created_at);

			SimpleDateFormat formatedDate = new SimpleDateFormat("dd-MM-yyyy");
	
			String shortDateStr = formatedDate.format(fullDate);
			
			return shortDateStr;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return "00:00:00";
		}
		*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy");
		String dateInString = dateString;
		String reformattedDate = "";
		try {
			
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			TimeZone tz = cal.getTimeZone();
			sdf.setTimeZone(tz);
			
			Date date = sdf.parse(dateInString);
			sdf.applyPattern("dd-MM-yy");
			reformattedDate = sdf.format(date);
			
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return "N/A";
		}
		
		return reformattedDate;
	}
	
	
	public static String commonDateFormat(String dateString) throws java.text.ParseException{
		String reformattedDate = "";
		if(dateString.contains("/")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateInString = dateString;
			try {
				
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				TimeZone tz = cal.getTimeZone();
				sdf.setTimeZone(tz);
				
				Date date = sdf.parse(dateInString);
				sdf.applyPattern("dd-MM-yyyy");
				reformattedDate = sdf.format(date);
				
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				return dateString;
			}
		} else if(dateString.contains("-")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString = dateString;
			try {
				
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				TimeZone tz = cal.getTimeZone();
				sdf.setTimeZone(tz);
				
				Date date = sdf.parse(dateInString);
				sdf.applyPattern("dd-MM-yyyy");
				reformattedDate = sdf.format(date);
				
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				return dateString;
			}
		}
		
		return reformattedDate;
	}
	
	public static String getFormatedDuration(String duration){
		String formatedduration = "";
		String[] splitDuration = null;

		if (duration.contains("hours") || duration.contains("hour")) {
			splitDuration = duration.split(" ");
			String hour = "";
			String min = "";
			if (splitDuration[0].length() == 1) {
				hour = "0" + splitDuration[0];
			} else {
				hour = splitDuration[0];
			}
			if (splitDuration[2].length() == 1) {
				min = "0" + splitDuration[2];
			} else {
				min = splitDuration[2];
			}
			formatedduration = hour + ":" + min + " h";

		} else {
			splitDuration = duration.split(" ");
			String hour = "00";
			String min = "";
			if (splitDuration[0].length() == 1) {
				min = "0" + splitDuration[0];
			} else {
				min = splitDuration[0];
			}
			formatedduration = hour + ":" + min + " h";
		}
		return formatedduration;
	}
	
	
	public static String getFormatedDistance(String distance){
		String formateddistance = "";
		String[] splitDistance = null;

		if (distance.contains(".")) {
			splitDistance = distance.split(" |\\.");
			String d1 = "";
			String d2 = "";
			if (splitDistance[0].length() == 1) {
				d1 = "0" + splitDistance[0];
			} else {
				d1 = splitDistance[0];
			}
			if (splitDistance[1].length() == 1) {
				d2 = "0" + splitDistance[1];
			} else {
				d2 = splitDistance[1];
			}
			formateddistance = d1 + "." + d2 + " km";

		} else {
			splitDistance = distance.split(" ");
			String d = "";
			if (splitDistance[0].length() == 1) {
				d = "0" + splitDistance[0];
			} else {
				d = splitDistance[0];
			}
			formateddistance = d+" km";
		}
		return formateddistance;
	}
	
	
	public static boolean isPublicHoliday(String dateString){
		String toDay = "";
		if(dateString.contains("/")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateInString = dateString;
			try {
				
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				TimeZone tz = cal.getTimeZone();
				sdf.setTimeZone(tz);
				
				Date date = sdf.parse(dateInString);
				sdf.applyPattern("EEEE d MMMM");
				toDay = sdf.format(date);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		} else if(dateString.contains("-")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString = dateString;
			try {
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				TimeZone tz = cal.getTimeZone();
				sdf.setTimeZone(tz);
				
				Date date = sdf.parse(dateInString);
				sdf.applyPattern("EEEE d MMMM");
				toDay = sdf.format(date);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(!toDay.equalsIgnoreCase("")) {
		Log.e("The day is", ""+toDay);
			for(int i = 0; i<ConstantValues.holidayList.length; i++){
				if(toDay.equalsIgnoreCase(ConstantValues.holidayList[i])) {
					return true;
				} 
			}
		
		}
		
		 return false;
		
/*		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
	//	SimpleDateFormat month_date = new SimpleDateFormat("MMMM")
		SimpleDateFormat formatedDate = new SimpleDateFormat("EEEE d MMMM");
		String toDay = formatedDate.format(calendar.getTime());
		Log.e("The day is", ""+toDay);
		for(int i = 0; i<ConstantValues.holidayList.length; i++){
			if(toDay.equalsIgnoreCase(ConstantValues.holidayList[i])) {
				return true;
			} 
		}
		 
		 return false;*/
	}

	public static String getDay(String dateString) throws java.text.ParseException{
		String dayIs = "";
		if(dateString.contains("/")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateInString = dateString;
			try {
				
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				TimeZone tz = cal.getTimeZone();
				sdf.setTimeZone(tz);
				
				Date date = sdf.parse(dateInString);
				sdf.applyPattern("EEEE");
				dayIs = sdf.format(date);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		} else if(dateString.contains("-")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString = dateString;
			try {
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				TimeZone tz = cal.getTimeZone();
				sdf.setTimeZone(tz);
				
				Date date = sdf.parse(dateInString);
				sdf.applyPattern("EEEE d MMMM");
				dayIs = sdf.format(date);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		return dayIs;
	}
	
//	public static String get24HourFormatedTime(String timeDate) throws java.text.ParseException {
//		String formatedTime = "00:00";
//		SimpleDateFormat readFormat = new SimpleDateFormat("hh:mm:ss aa");
//		SimpleDateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
//
//		if (timeDate != null) {
//			try {
//				formatedTime = writeFormat.format(readFormat.parse(timeDate));
//			} catch (final ParseException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return formatedTime;
//	}
}
