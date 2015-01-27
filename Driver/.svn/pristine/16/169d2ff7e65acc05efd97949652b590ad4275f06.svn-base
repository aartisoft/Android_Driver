package com.netcabs.services;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.netcabs.obdmonitor.BluetoothChatService;
import com.netcabs.utils.ConstantValues;

public class SpeedGettingService  extends Service {
	int counter = 0;
	int count = 0;
	
	private static final String TAG = "BroadcastService";
	public static final String BROADCAST_ACTION = "com.netcabs.driver.SplashScreenActivity";
	private final Handler handler = new Handler();
	private Intent intent;
	private LatLng current_taxi_latLng;
	private Geocoder gcd;
	private String geoAddress = "";
	
	private BluetoothChatService mChatService = null;
	private StringBuffer mOutStringBuffer;
	
	private int message_number = 0;
	
	//Setting constatnt
	public static final int MESSAGE_READ = 2;
	public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int REQUEST_ENABLE_BT = 3;
	
	private String mConnectedDeviceName = null;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static String DEVICE_NAME = "device_name";
	
	//Device value constant
	public static String METER_SPEED = null;
	//public static String ge = 
	
	public Context context;
	
	//*****
	private BluetoothAdapter mBluetoothAdapter = null;
	
	
	
	////// Start Constant value
	public static int SERVICE_SIGNAL_STATE = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//mChatService = new BluetoothChatService(this, mHandler);
	       // Initialize the buffer for outgoing messages
		this.context = this;
	    mOutStringBuffer = new StringBuffer("");
	    
    	intent = new Intent(BROADCAST_ACTION);
	}
	
    @Override
    public void onStart(Intent intent, int startId) {
    	Log.e("********Start My Service", "*****");
    
    	if (mChatService == null) {
			 mChatService = new BluetoothChatService(this, mHandler);
		}
    	
    	if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
				
			}
		}
    	
        //handler.removeCallbacks(sendUpdatesToUI);
        //handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
        //startTransmission();
    }
    
    int prev_intake = 0;
 	int prev_load = 0;
 	int prev_coolant = 0;
 	int prev_MPH = 0;
 	int prev_RPM = 0;
 	int prev_voltage = 0;
 	
    private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			String dataRecieved;
        	int value = 0;
        	int value2 = 0;
        	int PID = 0;
			
        	Log.e("********Thread is running", "OK......." + msg.what);
        	
			switch (msg.what) {
				case MESSAGE_STATE_CHANGE:
					Log.i("LOGIN ACTIVITY", "MESSAGE_STATE_CHANGE: " + msg.arg1);
					switch (msg.arg1) {
					case BluetoothChatService.STATE_CONNECTED:
	                    setStatus("Connected to " + mConnectedDeviceName);
	                    Log.e("CONNECTION FROM SERVICE", "&&&&****" + mConnectedDeviceName);
	                    startTransmission();
	                    //mConversationArrayAdapter.clear();
	                    break;
	                case BluetoothChatService.STATE_CONNECTING:
	                	Log.e("CONNECTION FROM SERVICE", "Connecting");
	                    setStatus("Connecting");
	                    break;
	                case BluetoothChatService.STATE_LISTEN:
	                case BluetoothChatService.STATE_NONE:
	                    setStatus("Not connected");
	                    Log.e("CONNECTION FROM SERVICE", "Not connected");
	                    break;
					}
					
				break;
				case MESSAGE_DEVICE_NAME:
	                // save the connected device's name
	                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
	            break;
	            
				case MESSAGE_READ:
					
					byte[] readBuf = (byte[]) msg.obj;
					// construct a string from the valid bytes in the buffer
					String readMessage = new String(readBuf, 0, msg.arg1);
	
					// ------- ADDED CODE FOR OBD -------- //
					dataRecieved = readMessage;
	
					// RX.setText(dataRecieved);
	
					if ((dataRecieved != null) && (dataRecieved.matches("\\s*[0-9A-Fa-f]{2} [0-9A-Fa-f]{2}\\s*\r?\n?"))) {
	
						dataRecieved = dataRecieved.trim();
						String[] bytes = dataRecieved.split(" ");
	
						if ((bytes[0] != null) && (bytes[1] != null)) {
	
							PID = Integer.parseInt(bytes[0].trim(), 16);
							value = Integer.parseInt(bytes[1].trim(), 16);
						}
	
						switch (PID) {
	
						case 13:// PID(0D): MPH
	
							value = (value * 5) / 8; // convert KPH to MPH
							int needle_value = ((value * 21) / 20) - 85;
	
							if (prev_MPH == 0) {
								prev_MPH = needle_value;
							} else {
								prev_MPH = needle_value;
							}
	
							String displayMPH = String.valueOf(value);
							// MPH.setText(displayMPH);
							if (value > 0) {
								ConstantValues.METER_HIT_COUNTER +=1;
								ConstantValues.METER_SPEED_MPH_TOTAL += value;
							}
							
							Log.e("SPEED MPH 1", "*********1**** " + displayMPH);
							break;
	
						default:
							;
	
						}
	
					} else if ((dataRecieved != null) && (dataRecieved.matches("\\s*[0-9A-Fa-f]{1,2} [0-9A-Fa-f]{2} [0-9A-Fa-f]{2}\\s*\r?\n?"))) {
	
						dataRecieved = dataRecieved.trim();
						String[] bytes = dataRecieved.split(" ");
	
						if ((bytes[0] != null) && (bytes[1] != null) && (bytes[2] != null)) {
	
							PID = Integer.parseInt(bytes[0].trim(), 16);
							value = Integer.parseInt(bytes[1].trim(), 16);
							value2 = Integer.parseInt(bytes[2].trim(), 16);
						}
	
						// PID(0C): RPM
						if (PID == 12) {
	
							int RPM_value = ((value * 256) + value2) / 4;
							int needle_value = ((RPM_value * 22) / 1000) - 85;
	
							if (prev_RPM == 0) {
								prev_RPM = needle_value;
							} else {
								prev_RPM = needle_value;
							}
	
							String displayRPM = String.valueOf(RPM_value);
							// RPM.setText(displayRPM);
	
						} else if ((PID == 1) || (PID == 65)) {
	
							switch (value) {
	
							case 13:// PID(0D): MPH
	
								value2 = (value2 * 5) / 8; // convert to MPH
								int needle_value = ((value2 * 21) / 20) - 85;
	
								if (prev_MPH == 0) {
									prev_MPH = needle_value;
								} else {
									prev_MPH = needle_value;
								}
	
								String displayMPH = String.valueOf(value2);
								
								if (value2 > 0) {
									ConstantValues.METER_HIT_COUNTER +=1;
									ConstantValues.METER_SPEED_MPH_TOTAL += value2;
								}
								
								Log.e("SPEED MPH2 ", "****2***** " + displayMPH);
								
								Log.e("SPEED MPH2 ", "****2***** " + displayMPH);
								// MPH.setText(displayMPH);
								break;
	
							default:
								;
							}
						}
	
					} else if ((dataRecieved != null) && (dataRecieved.matches("\\s*[0-9]+(\\.[0-9]?)?V\\s*\r*\n*"))) {
	
						dataRecieved = dataRecieved.trim();
						String volt_number = dataRecieved.substring(0, dataRecieved.length() - 1);
						double needle_value = Double.parseDouble(volt_number);
						needle_value = (((needle_value - 11) * 21) / 0.5) - 100;
						int volt_value = (int) (needle_value);
	
						if (prev_voltage == 0) {
							prev_voltage = volt_value;
						} else {
							prev_voltage = volt_value;
						}
						// voltage.setText(dataRecieved);
	
					} else if ((dataRecieved != null) && (dataRecieved.matches("\\s*[0-9]+(\\.[0-9]?)?V\\s*V\\s*>\\s*\r*\n*"))) {
	
						dataRecieved = dataRecieved.trim();
						String volt_number = dataRecieved.substring(0, dataRecieved.length() - 1);
						double needle_value = Double.parseDouble(volt_number);
						needle_value = (((needle_value - 11) * 21) / 0.5) - 100;
						int volt_value = (int) (needle_value);
	
						if (prev_voltage == 0) {
							prev_voltage = volt_value;
						} else {
							prev_voltage = volt_value;
						}
	
						// voltage.setText(dataRecieved);
	
					} else if((dataRecieved != null) && (dataRecieved.matches("\\s*[ .A-Za-z0-9\\?*>\r\n]*\\s*>\\s*\r*\n*" ))) {
		            	
						if(message_number == 2) {
							message_number = 1;
						}
		            	
						getData(message_number++);
		            	   
		            } else {
	
						;
					}
	     
				break;
			
			}

		}
	};
    

    private Runnable sendUpdatesToUI = new Runnable() {
    	public void run() {
    	   // handler.postDelayed(this, 10000); // 3 seconds
    	}
    };    

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {		
        handler.removeCallbacks(sendUpdatesToUI);		
		super.onDestroy();
	}
	
	public class GetPostResult extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
		}
	}
	
	
	private final void setStatus(CharSequence subTitle) {
		SpeedGettingService.DEVICE_NAME = mConnectedDeviceName;
		SpeedGettingService.METER_SPEED = "";
		Log.i("SET TITLE SET FROM MY SERVICE", "===== " + subTitle);
		//Toast.makeText(this, text, duration)
		//((TextView) findViewById(R.id.txt_view_connection_status)).setText(subTitle);
    }
	
	public void startTransmission() {
		ConstantValues.METER_START_TIME = System.currentTimeMillis();
		sendMessage("01 00" + '\r');

	}
	
	private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            //Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }
	
	public void getData(int messagenumber) {
		
		//final TextView TX = (TextView) findViewById(R.id.TXView2); 
		
		switch(messagenumber) {
    	
        	case 1:
        		sendMessage("01 0C" + '\r'); //get RPM
        		//TX.setText("01 0C");
        		messagenumber++;
        		break;
        		
        	case 2:
        		sendMessage("01 0D" + '\r'); //get MPH
        		//TX.setText("01 0D");
        		messagenumber++;
        		break;
        	case 3:
        		//sendMessage("01 04" + '\r'); //get Engine Load
        		sendMessage("01 31" + '\r'); //Distance command
        		//TX.setText("01 04");
        		messagenumber++;
        		break;
        	case 4:
        		sendMessage("01 05" + '\r'); //get Coolant Temperature
        		//TX.setText("01 05");
        		messagenumber++;
        		break;
        	case 5:
        		sendMessage("01 0F" + '\r'); //get Intake Temperature
        		//TX.setText("01 0F");
        		messagenumber++;
        		break;
        	
        	case 6:
        		sendMessage("AT RV" + '\r'); //get Voltage
        		//TX.setText("AT RV");
        		messagenumber++;
        		break;
        		
        	default: ; 		 
		}
    }
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
}
