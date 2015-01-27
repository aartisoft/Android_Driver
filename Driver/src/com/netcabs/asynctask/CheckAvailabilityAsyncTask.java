package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.jsonparser.CommunicationLayer;

public class CheckAvailabilityAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity context;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;
	private boolean isProgressOn = true;

	public CheckAvailabilityAsyncTask(Activity context, boolean isProgressOn, OnRequestComplete callback2) {
		this.context = context;
		this.callback = (OnRequestComplete) callback2;
		this.isProgressOn = isProgressOn;
	}

	@Override
	protected void onPreExecute() {
		if (this.isProgressOn) {
			progressDialog = ProgressDialog.show(context, "", "Loading...", true, false);
		}
		
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params) {
		String func_id = params[0];
		String taxi_id = params[1];
		String is_available = params[2];
		try {
			responseStatus = CommunicationLayer.getCheckAvailabilityData(func_id, taxi_id, is_available);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if (isProgressOn) {
			try {
				if ((progressDialog != null) && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			} catch (final IllegalArgumentException e) {
			} catch (final Exception e) {
			} finally {
				progressDialog = null;
			}
		}
		
		callback.onRequestComplete(responseStatus);
	}

}
