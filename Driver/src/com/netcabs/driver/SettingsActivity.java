package com.netcabs.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.netcabs.asynctask.CheckAvailabilityAsyncTask;
import com.netcabs.asynctask.DriverIdentificationAsyncTask;
import com.netcabs.asynctask.SettingAsyncTask;
import com.netcabs.customviews.CustomToast;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.DriverApp;
import com.netcabs.utils.Utils;

public class SettingsActivity extends Activity implements OnClickListener {

	private EditText edTxtFirstName;
	private EditText edTxtLastName;
	private EditText edTxtEmailAddress;
	private EditText edTxtMobileNumber;
	private ImageView imgViewDriverId;
	private ImageView imgViewDriverLicence;
	private final int PICK_FROM_CAMERA = 1;
	private final int PICK_FROM_FILE = 2;
	private String selectedImagePath = "";
	private String reSizedBitmapPath = "";
	private Bitmap resizedBitmap;
	private AQuery lazyImageLoader;
	private boolean isCancel = false;
	private boolean isLock = false;
	private static int rotate = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_settings);

		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		lazyImageLoader= new AQuery(SettingsActivity.this);
		edTxtFirstName = (EditText) findViewById(R.id.ed_txt_first_name);
		edTxtLastName = (EditText) findViewById(R.id.ed_txt_last_name);
		edTxtEmailAddress = (EditText) findViewById(R.id.ed_txt_email_address);
		edTxtMobileNumber = (EditText) findViewById(R.id.ed_txt_mobile_number);
		//edTxtTaxiNumber = (EditText) findViewById(R.id.ed_txt_taxi_number);
		//edTxtVehicleClass = (EditText) findViewById(R.id.ed_txt_vehicle_class);
		imgViewDriverId = (ImageView) findViewById(R.id.img_view_driver_authority);
		imgViewDriverLicence = (ImageView) findViewById(R.id.img_view_driver_licence);
	}

	private void setListener() {
		((Button) findViewById(R.id.btn_save)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_cancel)).setOnClickListener(this);
		imgViewDriverId.setOnClickListener(this);
		imgViewDriverLicence.setOnClickListener(this);
	}

	private void loadData() {
		if(DriverApp.getInstance().getProfileInfo() != null) {
			edTxtFirstName.setText(DriverApp.getInstance().getProfileInfo().getFirstName().toString());
			edTxtLastName.setText(DriverApp.getInstance().getProfileInfo().getLastName().toString());
			edTxtEmailAddress.setText(DriverApp.getInstance().getProfileInfo().getEmail().toString());
			edTxtMobileNumber.setText(DriverApp.getInstance().getProfileInfo().getMobileNumber());
			
			if (DriverApp.getInstance().getProfileInfo().getNidImage() != null) {
				lazyImageLoader.id(imgViewDriverId).image(DriverApp.getInstance().getProfileInfo().getNidImage(), true, true, 300, R.drawable.driver_licence_img);
			}
			
			if (DriverApp.getInstance().getProfileInfo().getDrivingLicenseImage() != null) {
				lazyImageLoader.id(imgViewDriverLicence).image(DriverApp.getInstance().getProfileInfo().getDrivingLicenseImage(), true, true,300, R.drawable.driver_licence_img);
			}
			
			//edTxtTaxiNumber.setText(DriverApp.getInstance().getProfileInfo().getTaxiNumber().toString());
			//edTxtVehicleClass.setText(DriverApp.getInstance().getProfileInfo().getVehicleClass().toString());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			processSaveProfile();
			break;
			
		case R.id.btn_cancel:
			ConstantValues.isBack = false;
			isCancel = true;
			onBackPressed();
			break;
			
		case R.id.img_view_driver_authority:
			ConstantValues.imgType = 0;
			ConstantValues.mImageCaptureUri = null;
			startDialog();
			break;
			
		case R.id.img_view_driver_licence:
			ConstantValues.mImageCaptureUri = null;
			ConstantValues.imgType = 1;
			startDialog();
			break;

		default:
			break;
		}
	}


	private void processSaveProfile() {
		if(!edTxtFirstName.getText().toString().trim().equalsIgnoreCase("") && !edTxtLastName.getText().toString().trim().equalsIgnoreCase("") && !edTxtEmailAddress.getText().toString().trim().equalsIgnoreCase("") && isEmailValid(edTxtEmailAddress.getText().toString().trim()) &&!edTxtMobileNumber.getText().toString().trim().equalsIgnoreCase("") ) {
			if(!DriverApp.getInstance().getProfileInfo().getFirstName().equals(edTxtFirstName.getText().toString().trim()) || !DriverApp.getInstance().getProfileInfo().getLastName().equals(edTxtLastName.getText().toString().trim()) || !DriverApp.getInstance().getProfileInfo().getEmail().equals(edTxtEmailAddress.getText().toString().trim()) || !DriverApp.getInstance().getProfileInfo().getMobileNumber().equals(edTxtMobileNumber.getText().toString().trim())) {
				if(InternetConnectivity.isConnectedToInternet(SettingsActivity.this)) {
					new SettingAsyncTask(SettingsActivity.this, new OnRequestComplete() {
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									DriverApp.getInstance().getProfileInfo().setFirstName(edTxtFirstName.getText().toString().trim());
									DriverApp.getInstance().getProfileInfo().setLastName(edTxtLastName.getText().toString().trim());
									DriverApp.getInstance().getProfileInfo().setEmail(edTxtEmailAddress.getText().toString().trim());
									DriverApp.getInstance().getProfileInfo().setMobileNumber(edTxtMobileNumber.getText().toString().trim());
									//DriverApp.getInstance().getProfileInfo().setTaxiNumber(edTxtTaxiNumber.getText().toString().trim());
									//DriverApp.getInstance().getProfileInfo().setVehicleClass(edTxtVehicleClass.getText().toString().trim());
									new CustomToast(SettingsActivity.this, "Profile successfully updated").showToast();
								} else {
									new CustomToast(SettingsActivity.this, "Sorry ! Please try again").showToast();
								}

							} catch (Exception e) {
								new CustomToast(SettingsActivity.this, "" + e.getMessage()).showToast();
								Log.e("Error 1029", "---" + e.getMessage());
							}
							
							
						}
					}).execute("1013", DriverApp.getInstance().getDriverInfo().getTaxiId(), DriverApp.getInstance().getProfileInfo().getId(), edTxtFirstName.getText().toString().trim(), edTxtLastName.getText().toString().trim(), edTxtEmailAddress.getText().toString().trim(), edTxtMobileNumber.getText().toString().trim());
				} else {
					new CustomToast(SettingsActivity.this, ConstantValues.internetConnectionMsg).showToast();
				}
			} else {
				new CustomToast(SettingsActivity.this, "There are no update available").showToast();
			}
		} else {
			if(edTxtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtFirstName.setError("Required");
			}
			
			if(edTxtLastName.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtLastName.setError("Required");
			}
			
			if(edTxtEmailAddress.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtEmailAddress.setError("Required");
			} else {
				if(!isEmailValid(edTxtEmailAddress.getText().toString().trim())) {
					edTxtEmailAddress.setError("Invalid email id");
				} else {
					
				}
			}
			
			if(edTxtMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtMobileNumber.setError("Required");
			}
			
//			if(edTxtTaxiNumber.getText().toString().trim().equalsIgnoreCase("")) {
//				edTxtTaxiNumber.setError("Required");
//			}
//			
//			if(edTxtVehicleClass.getText().toString().trim().equalsIgnoreCase("")) {
//				edTxtVehicleClass.setError("Required");
//			}
		}
	}
	
	private boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	
	 private void startDialog() {
		    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(SettingsActivity.this);
		    myAlertDialog.setTitle("Upload Pictures Option");
		    myAlertDialog.setMessage("How do you want to set your picture?");

		    myAlertDialog.setNegativeButton("Gallery",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface arg0, int arg1) {
		                	isLock = true;
		    				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    				i.setType("image/*");
		    				i.setAction(Intent.ACTION_GET_CONTENT);
		    				startActivityForResult(i, PICK_FROM_FILE);
		                }
		            });

		    myAlertDialog.setPositiveButton("Camera",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface arg0, int arg1) {
		                	isLock = true;
		                	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		                	ConstantValues.mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "/driver" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
		                	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, ConstantValues.mImageCaptureUri);
		                	intent.putExtra("return-data", true);
		                	startActivityForResult(intent, PICK_FROM_CAMERA);
		                }
		            });
		    myAlertDialog.show();
		}
	 
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			Log.e("RequesT Code is", "::"+resultCode + ":::"+ requestCode);
			if (resultCode != -1) {
				return;
				
			} else if (resultCode == 0) {
				return;
				
			} else if (resultCode == 1) {
				return;
				
			}
				

				switch (requestCode) {
				
				case PICK_FROM_CAMERA:
				
					if(ConstantValues.mImageCaptureUri != null){
						selectedImagePath = ConstantValues.mImageCaptureUri.getPath().toString();
						getCameraPhotoOrientation(SettingsActivity.this,ConstantValues.mImageCaptureUri, selectedImagePath);
						ConstantValues.mImageCaptureUri = null;
						
					}else{
						Log.e("capture uri is null", "--------------");
						return;
					}

					try {
						BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
						btmapOptions.inSampleSize = 6;  
						Bitmap btmapImg = BitmapFactory.decodeFile(selectedImagePath, btmapOptions);
						resizedBitmap = Utils.getResizedBitmap(btmapImg, 102, 104);
						
						reSizedBitmapPath = Utils.getBitmapPath(SettingsActivity.this, resizedBitmap);
						Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
						if(InternetConnectivity.isConnectedToInternet(SettingsActivity.this)) {
								new DriverIdentificationAsyncTask(SettingsActivity.this, new OnRequestComplete() {
									@Override
									public void onRequestComplete(String result) {
										if("2001".equals(result)) {
											Log.e("img type:", "---"+ConstantValues.imgType + ":::"+resizedBitmap);
											setBitMapImage();
											/*if(ConstantValues.imgType == 0) {
												imgViewDriverId.setImageBitmap(resizedBitmap);
											} else if (ConstantValues.imgType == 1) {
												imgViewDriverLicence.setImageBitmap(resizedBitmap);
											}*/
											new CustomToast(SettingsActivity.this, "Image successfully updated").showToast();
											return;
										} else {
											new CustomToast(SettingsActivity.this, "Sorry ! Please try again").showToast();
											return;
										}
									}
								}).execute("1014", DriverApp.getInstance().getDriverInfo().getTaxiId(), DriverApp.getInstance().getProfileInfo().getId(), Integer.toString(ConstantValues.imgType), reSizedBitmapPath);
							} else {
								new CustomToast(SettingsActivity.this, ConstantValues.internetConnectionMsg).showToast();
							}
					
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
	
				case PICK_FROM_FILE:
					//imgType = Integer.parseInt(data.getStringExtra("type"));
					ConstantValues.mImageCaptureUri = data.getData();
					
					if(ConstantValues.mImageCaptureUri != null) {
						Log.i("In case 2", "--------------------PICK_FROM_FILE__________"+ConstantValues.imgType);
						Log.e("Bitmap Path is:", "---"+ConstantValues.mImageCaptureUri);
						String url = data.getData().toString();
							Bitmap bitmap = null;
						java.io.InputStream is = null;
						if (url.startsWith("content://com.google.android.apps.photos.content") || url.startsWith("content://com.google.android.gallery3d.provider")) {
						     try {
						    	 is = SettingsActivity.this.getContentResolver().openInputStream(Uri.parse(url));
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					       bitmap = BitmapFactory.decodeStream(is);
					       resizedBitmap = Utils.getResizedBitmap(bitmap, 102, 104);
					       reSizedBitmapPath = Utils.getBitmapPath(SettingsActivity.this, resizedBitmap);
					      // getCameraPhotoOrientation(SettingsActivity.this,ConstantValues.mImageCaptureUri, reSizedBitmapPath);
							
					       Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
					       if(InternetConnectivity.isConnectedToInternet(SettingsActivity.this)) {
								new DriverIdentificationAsyncTask(SettingsActivity.this, new OnRequestComplete() {
									@Override
									public void onRequestComplete(String result) {
										
										try {
											if("2001".equals(result)) {
												Log.e("img type:", "---"+ConstantValues.imgType + ":::"+resizedBitmap);
												setBitMapImage();
	//											if(ConstantValues.imgType == 0) {
	//												imgViewDriverId.setImageBitmap(resizedBitmap);
	//											} else if (ConstantValues.imgType == 1) {
	//												imgViewDriverLicence.setImageBitmap(resizedBitmap);
	//											}
												new CustomToast(SettingsActivity.this, "Image successfully updated").showToast();
											} else {
												new CustomToast(SettingsActivity.this, "Sorry ! Please try again").showToast();
											}

										} catch (Exception e) {
											new CustomToast(SettingsActivity.this, "" + e.getMessage()).showToast();
											Log.e("Error 1030", "---" + e.getMessage());
										}
										
									}
								}).execute("1014", DriverApp.getInstance().getDriverInfo().getTaxiId(), DriverApp.getInstance().getProfileInfo().getId(), Integer.toString(ConstantValues.imgType), reSizedBitmapPath);
							} else {
								new CustomToast(SettingsActivity.this, ConstantValues.internetConnectionMsg).showToast();
							}
						     
						} else {
							String[] filePathColumn = { MediaStore.Images.Media.DATA };
							Cursor cursor = SettingsActivity.this.getContentResolver().query(ConstantValues.mImageCaptureUri, filePathColumn, null, null, null);
							if(cursor == null) {
								selectedImagePath = ConstantValues.mImageCaptureUri.getPath().toString();
								//getCameraPhotoOrientation(SettingsActivity.this,ConstantValues.mImageCaptureUri, selectedImagePath);
							} else {
								cursor.moveToFirst();
								int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					
								selectedImagePath = cursor.getString(columnIndex);
								cursor.close();
							}
						
							try {
								BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
								btmapOptions.inSampleSize = 6;  
								Log.e("selected Path is:", "---"+selectedImagePath);
								Bitmap btmapImg = BitmapFactory.decodeFile(selectedImagePath, btmapOptions);
								final Bitmap resizedBitmap = Utils.getResizedBitmap(btmapImg, 102, 104);
								reSizedBitmapPath = Utils.getBitmapPath(SettingsActivity.this,resizedBitmap);
								Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
								if(InternetConnectivity.isConnectedToInternet(SettingsActivity.this)) {
									new DriverIdentificationAsyncTask(SettingsActivity.this, new OnRequestComplete() {
										@Override
										public void onRequestComplete(String result) {
											
											try {
												if("2001".equals(result)) {
													Log.e("img type:", "---"+ConstantValues.imgType + ":::"+resizedBitmap);
													setBitMapImage();
													/*if(ConstantValues.imgType == 0) {
														imgViewDriverId.setImageBitmap(resizedBitmap);
													} else if (ConstantValues.imgType == 1) {
														imgViewDriverLicence.setImageBitmap(resizedBitmap);
													}*/
													new CustomToast(SettingsActivity.this, "Image successfully updated").showToast();
												} else {
													new CustomToast(SettingsActivity.this, "Sorry ! Please try again").showToast();
												}

											} catch (Exception e) {
												new CustomToast(SettingsActivity.this, "" + e.getMessage()).showToast();
												Log.e("Error 1031", "---" + e.getMessage());
											}
											
										}
									}).execute("1014", DriverApp.getInstance().getDriverInfo().getTaxiId(), DriverApp.getInstance().getProfileInfo().getId(), Integer.toString(ConstantValues.imgType), reSizedBitmapPath);
								} else {
									new CustomToast(SettingsActivity.this, ConstantValues.internetConnectionMsg).showToast();
								}
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					break;
					
				default:
					break;
			}
	 }
	 
	 private void setBitMapImage() {
		 Log.e("Rotation is ", ":::::::::::::::"+rotate);
		 AQuery aq = new AQuery(SettingsActivity.this);
		    BitmapAjaxCallback bmCallBack = new BitmapAjaxCallback();
			bmCallBack.url(reSizedBitmapPath).targetWidth(300).rotate(true);
			bmCallBack.memCache(true);
			bmCallBack.fileCache(true);
			imgViewDriverId.setRotation(rotate);
			if(ConstantValues.imgType == 0) {
				aq.id(imgViewDriverId).image(bmCallBack);
			} else if (ConstantValues.imgType == 1) {
				aq.id(imgViewDriverLicence).image(bmCallBack);
			}
			rotate = 0;
			
			
			
	}
	 
	 
	@Override
	public void onBackPressed() {
		isLock = true;
		if (isCancel) {
			super.onBackPressed();
		} else {

		}

	}
	 
    @Override
	protected void onResume() {
		if (isLock) {
			isLock = false;
			//Make available
			if(InternetConnectivity.isConnectedToInternet(SettingsActivity.this)) {
				new CheckAvailabilityAsyncTask(SettingsActivity.this, false, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
							//new CustomToast(MainMapActivity.this, "Came to available").showToast();
					
								Log.i("Available check", "Came to available");
							} else {
								new CustomToast(SettingsActivity.this, "Data not found").showToast();
							}

						} catch (Exception e) {
							new CustomToast(SettingsActivity.this, "" + e.getMessage()).showToast();
							Log.e("Error 1032", "---" + e.getMessage());
						}
						
						
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "1");
				
			} else {
				new CustomToast(SettingsActivity.this, ConstantValues.internetConnectionMsg).showToast();
			}
		} else {
			
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (isLock) {
			isLock = false;
		} else {
			DriverApp.getInstance().setLock(true);
			isLock = true;
			//Make unavailable
			if(InternetConnectivity.isConnectedToInternet(SettingsActivity.this)) {
				new CheckAvailabilityAsyncTask(SettingsActivity.this, false, new OnRequestComplete() {
					@Override
					public void onRequestComplete(String result) {
						
						try {
							if("2001".equals(result)) {
								Log.i("Available check", "Gone to unavailable");
								new CustomToast(SettingsActivity.this, "Gone to unavailable").showToast();
								
							} else {
								new CustomToast(SettingsActivity.this, "Data not found").showToast();
							}
							
						} catch (Exception e) {
							new CustomToast(SettingsActivity.this, "" + e.getMessage()).showToast();
							Log.e("Error 1033", "---" + e.getMessage());
						}
						
						
					}
				}).execute("1003", DriverApp.getInstance().getDriverInfo().getTaxiId(), "0");
			} else {
				new CustomToast(SettingsActivity.this, ConstantValues.internetConnectionMsg).showToast();
			}
		}
		super.onPause();
	}
	
	
    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
    //int rotate = 0;
    try {
        context.getContentResolver().notifyChange(imageUri, null);
        File imageFile = new File(imagePath);
        ExifInterface exif = new ExifInterface(
                imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_270:
            rotate = 270;
            break;
            
        case ExifInterface.ORIENTATION_ROTATE_180:
            rotate = 180;
            break;
        case ExifInterface.ORIENTATION_ROTATE_90:
            rotate = 90;
            break;
            
        default:
        	rotate = 0;
			break;
        }

        Log.e("Orientation is", "Exif orientation: " + orientation+"::::"+rotate);
    } catch (Exception e) {
        e.printStackTrace();
    }
   return rotate;
}
	 
	 
}
