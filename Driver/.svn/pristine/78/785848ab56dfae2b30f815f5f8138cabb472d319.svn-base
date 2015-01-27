package com.netcabs.asynctask;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.utils.DriverApp;

public class DriverIdentificationAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity context;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public DriverIdentificationAsyncTask(Activity context, OnRequestComplete callback2) {
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
		String type = params[3];
		String image = params[4];
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(DriverApp.getInstance().getBaseUrl());

			MultipartEntity reqEntry = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntry.addPart("func_id", new StringBody(func_id));
			reqEntry.addPart("taxi_id", new StringBody(taxi_id));
			reqEntry.addPart("driver_id", new StringBody(driver_id));
			reqEntry.addPart("type", new StringBody(type));
			
			if(!image.equalsIgnoreCase("")) {
				FileBody bin = new FileBody(new File(image), "image/jpeg");
				reqEntry.addPart("image ", bin);
			} else {
				reqEntry.addPart("image", new StringBody(""));
			}
			
			httppost.setEntity(reqEntry);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			InputStream is = resEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			is.close();
			String thisdata = "";
			thisdata = sb.toString().trim();
			Log.i("update ", "__________"+thisdata);
			JSONObject jDataObj = new JSONObject(thisdata);
			if(jDataObj.getString("status").equals("1")) {
				if ("2001".equals(jDataObj.getString("status_code"))) {
					if(type.equals("1")) {
						DriverApp.getInstance().getProfileInfo().setDrivingLicenseImage(jDataObj.getString("pic_path"));
					} else {
						DriverApp.getInstance().getProfileInfo().setNidImage(jDataObj.getString("pic_path"));
						
					}
				}
			}

			responseStatus = jDataObj.getString("status_code");

		} catch (Exception ex) {
			ex.printStackTrace();
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
