package com.example.Razi;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class UserAdapterFile extends ArrayAdapter {
	private Context context;
	private ArrayList<UserModel> ums;

	static class HolderView {
		public TextView tvName;
		public TextView tvEmail;
		public ImageView ivPic;
	}

	@SuppressWarnings("unchecked")
	public UserAdapterFile(Context c, ArrayList<UserModel> ums) {
		super(c, R.layout.list_all_mail_user_id, ums);
		this.context = c;
		this.ums = ums;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_all_mail_user_id, parent, false);
			
			HolderView listItem = new HolderView();
			listItem.tvName = (TextView) convertView.findViewById(R.id.tv_user_name);
			listItem.tvEmail = (TextView) convertView.findViewById(R.id.tv_user_email);
			
			convertView.setTag(listItem);
		}

		HolderView listItem = (HolderView) convertView.getTag();

		UserModel um = ums.get(position);
		
		listItem.tvName.setText(um.getName());
		listItem.tvEmail.setText(um.getEmail());

		return convertView;
	}
}
