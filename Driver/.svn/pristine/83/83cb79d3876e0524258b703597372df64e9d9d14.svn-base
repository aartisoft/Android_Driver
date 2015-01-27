package com.netcabs.gcm;

import java.util.List;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.netcabs.driver.MainMapActivity;
import com.netcabs.driver.R;
import com.netcabs.driver.SplashScreenActivity;

public class GcmIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GcmIntentService() {
		super("GcmIntentServive");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				for (int i = 0; i < 5; i++) {
					Log.i("",
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
				}
			}

			sendNotification(extras.getString("message"));

			Log.i("Message is ", "____________" + extras.getString("message"));
		}

		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	private void sendNotification(String msg) {
		if (msg != null) {
			mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			PendingIntent contentIntent = null;
			
			ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

			List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(1);
			ActivityManager.RunningTaskInfo aTask = taskList.get(0);
			
			if(aTask.topActivity.getClassName().equals("com.netcabs.driver.MainMenuActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.SettingsActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.FastTripsAcitivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.StartFareActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.SupportActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.SupportDetailsActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.SearchAddressLocation") || aTask.topActivity.getClassName().equals("com.netcabs.driver.PastTripDetailsActivity")) {
				contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainMapActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
			} else if(aTask.topActivity.getClassName().equals("com.netcabs.driver.StartOrEndTripActivity")|| aTask.topActivity.getClassName().equals("com.netcabs.driver.CalculateFareActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.CalculatorActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.MainMapActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.PaymentConfirmActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.PaymentTypeActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.FAQActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.LoginActivity") || aTask.topActivity.getClassName().equals("com.netcabs.driver.SplashScreenActivity")) {
				
			} else {
				contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashScreenActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
			}
			
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.app_icon)
					.setContentTitle(getResources().getString(R.string.app_name))
					.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
					.setContentText(msg).setAutoCancel(true);
			
			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		}
	}

}
