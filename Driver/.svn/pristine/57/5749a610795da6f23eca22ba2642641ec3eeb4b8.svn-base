package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.jsonparser.CommunicationLayer;

public class SettingAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity context;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public SettingAsyncTask(Activity context, OnRequestComplete callback2) {
		this.context = context;
		this.callback = (OnRequestComplete) callback2;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "Loading...", true, false);
	}

	@Override
	protected Void doInBackground(String... params) {
		String func_id = params[0];
		String taxi_id = params[1];
		String driver_id = params[2];
		String first_name = params[3];
		String last_name = params[4];
		String email_address = params[5];
		String mobile_number = params[6];
		try {
			responseStatus = CommunicationLayer.getSettingData(func_id, taxi_id, driver_id, first_name, last_name, email_address, mobile_number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			if ((progressDialog != null) && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		} catch (final IllegalArgumentException e) {
		} catch (final Exception e) {
		} finally {
			progressDialog = null;
		}
		
		callback.onRequestComplete(responseStatus);
	}

}
