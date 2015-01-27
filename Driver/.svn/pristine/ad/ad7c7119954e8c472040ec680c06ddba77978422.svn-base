package com.netcabs.adapter;

import java.text.ParseException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netcabs.datamodel.FastTripsInfo;
import com.netcabs.driver.R;
import com.netcabs.utils.Utils;

public class PastTripAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private ArrayList<FastTripsInfo> pastTripList;

	public PastTripAdapter(Context context, ArrayList<FastTripsInfo> pastTripList) {
		this.context = context;
		this.pastTripList = pastTripList;
	}

	@Override
	public int getCount() {
		if(pastTripList != null) {
			return pastTripList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return pastTripList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.past_trips_rwo, null);
			holder = new ViewHolder();
			holder.txtViewName = (TextView) convertView.findViewById(R.id.txt_view_name);
			holder.txtViewAddress = (TextView) convertView.findViewById(R.id.txt_view_location_name);
			holder.txtViewDate = (TextView) convertView.findViewById(R.id.txt_view_date);
			holder.txtViewDistance = (TextView) convertView.findViewById(R.id.txt_view_distance);
			holder.txtViewFare = (TextView) convertView.findViewById(R.id.txt_view_fare);
			holder.txtViewPayment = (TextView) convertView.findViewById(R.id.txt_view_payment);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewName.setText(pastTripList.get(position).getName());
		holder.txtViewAddress.setText(pastTripList.get(position).getDestinationName());
		Log.e("Date is", ""+pastTripList.get(position).getDate());
		try {
			//holder.txtViewDate.setText(Utils.formatedDate(pastTripList.get(position).getDate()));
			holder.txtViewDate.setText(Utils.commonDateFormat(pastTripList.get(position).getDate()));
		} catch (ParseException e) {
			holder.txtViewDate.setText(pastTripList.get(position).getDate());
			e.printStackTrace();
		}
		holder.txtViewFare.setText("$ "+String.format("%.2f", Double.parseDouble(pastTripList.get(position).getFare())));
		holder.txtViewDistance.setText(pastTripList.get(position).getDistance());
		holder.txtViewPayment.setText(pastTripList.get(position).getPaymentType());
		

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewName;
		TextView txtViewAddress;
		TextView txtViewDate;
		TextView txtViewFare;
		TextView txtViewDistance;
		TextView txtViewPayment;
	}

}
