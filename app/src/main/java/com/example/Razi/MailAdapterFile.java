package com.example.Razi;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class MailAdapterFile extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<UserModel> ums;
	private ArrayList<ArrayList<MessageFile>> mms;

	public MailAdapterFile(Context c, ArrayList<UserModel> ums, ArrayList<ArrayList<MessageFile>> mms) {
		this.context = c;
		this.ums = ums;
		this.mms = mms;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group_mail_user_id, null);
		}
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tv_user_id);
		ImageView ivMore = (ImageView) convertView.findViewById(R.id.iv_more);
		
		UserModel um = ums.get(groupPosition);
		
		tvName.setText(um.getName());
		
		if (isExpanded) {
			ivMore.setVisibility(View.GONE);
		}
		else {
			ivMore.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item_mail_messege, null);
		}

		TextView tvMessageSubject = (TextView) convertView.findViewById(R.id.tv_set_message_subject);
		TextView tvMessage = (TextView) convertView.findViewById(R.id.tv_set_message);
		TextView tvMessageDate = (TextView) convertView.findViewById(R.id.tv_set_message_date);
		TextView tvMessageTime = (TextView) convertView.findViewById(R.id.tv_set_message_time);
		
		MessageFile mm = mms.get(groupPosition).get(childPosition);

		tvMessageSubject.setText(mm.getSubject());
		tvMessage.setText(mm.getMessegeText());
		tvMessageDate.setText(mm.getDate());
		tvMessageTime.setText(mm.getTime());
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mms.get(groupPosition).size();
	}	

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mms.get(groupPosition).get(childPosition);
	}	

	@Override
	public int getGroupCount() {
		return ums.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return ums.get(groupPosition);
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
