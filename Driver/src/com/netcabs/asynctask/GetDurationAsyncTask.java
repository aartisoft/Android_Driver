package com.netcabs.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.jsonparser.JSONParser;
import com.netcabs.utils.Utils;

public class GetDurationAsyncTask extends AsyncTask<String, Void, Void> {

	//private Context context;
	//private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private LatLng pickUpPosition;
	private LatLng DestinationPosition;
	private String timeDuration;
	private String distance;
	private JSONObject json;

	public GetDurationAsyncTask(Activity context, LatLng pickUpPosition, LatLng DestinationPostion, OnRequestComplete callback2) {
		//this.context = context;
		this.callback = (OnRequestComplete) callback2;
		this.pickUpPosition = pickUpPosition;
		this.DestinationPosition = DestinationPostion;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
//		progressDialog = ProgressDialog.show(context, "", "Loading...", true, false);
	}

	@Override
	protected Void doInBackground(String... params) {
		try {
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			JSONParser jParser = new JSONParser();
			Log.e("URL IS", "" + "http://maps.googleapis.com/maps/api/directions/json?"
		            + "origin=" + pickUpPosition.latitude + "," + pickUpPosition.longitude 
		            + "&destination=" + DestinationPosition.latitude + "," + DestinationPosition.longitude
		            + "&sensor=false&units=metric&mode=driving");
			
			json = jParser.getJSONFromUrl("http://maps.googleapis.com/maps/api/directions/json?"
	                + "origin=" + pickUpPosition.latitude + "," + pickUpPosition.longitude 
	                + "&destination=" + DestinationPosition.latitude + "," + DestinationPosition.longitude
	                + "&sensor=false&units=metric&mode=driving");
			Log.e("RESPONSE for duration", "**" + json);
			if (json != null) {
				JSONArray dataArray = json.getJSONArray("routes");
				if (dataArray.length() > 0) {
					JSONObject durationObj = dataArray.getJSONObject(0);
					JSONArray legs = durationObj.getJSONArray("legs");
					if (legs.length() > 0) {
						JSONObject durationObj2 = legs.getJSONObject(0);
						JSONObject durationObj3 = durationObj2.getJSONObject("duration");
						if (durationObj3.getString("text") != null) {
							timeDuration = Utils.getFormatedDuration(durationObj3.getString("text").trim());
						} else {
							timeDuration = "";
						}
						
						JSONObject distanceObj3 = durationObj2.getJSONObject("distance");
						if (distanceObj3.getString("text") != null) {
							distance = Utils.getFormatedDistance(distanceObj3.getString("text"));
							
						} else {
							distance = "";
						}
					} else {
						distance = "00 k";
						timeDuration = "00:00 h";
					}
				} else {

				}

			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
//		if(progressDialog.isShowing()) {
//			progressDialog.dismiss();
//		}
		
		callback.onRequestComplete((distance+"//"+timeDuration).replace("null", "00"));
	}

}
