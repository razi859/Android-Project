package com.example.Razi;

//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Razi Rehman on 1/26/2018.
 */

public class SendMessageFile extends Activity implements OnClickListener {

	private ImageButton ibDiscardMessage;
	private ImageButton ibSendMessage;
	private ImageView ivPic;
	private TextView tvName;
	private EditText etMessageSubject;
	private EditText etMessageText;
	
	private MessageFile mm;

	private boolean isForResult;
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message_view);
		ibDiscardMessage = (ImageButton) findViewById(R.id.ib_discard_message);
		ibSendMessage = (ImageButton) findViewById(R.id.ib_send_message);
		ivPic = (ImageView) findViewById(R.id.iv_reciever_pic);
		tvName = (TextView) findViewById(R.id.tv_reciever_name);
		etMessageSubject = (EditText) findViewById(R.id.et_message_subject);
		etMessageText = (EditText) findViewById(R.id.et_message_text);

		ibDiscardMessage.setOnClickListener(this);
		ibSendMessage.setOnClickListener(this);
		
	
		tvName.setText(getIntent().getStringExtra("recieverName"));
		isForResult = getIntent().getBooleanExtra("isForResult", false);

	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_discard_message: {

			Toast.makeText(this, "Message Discarded", Toast.LENGTH_LONG).show();
			finish();
		}
		break;
		case R.id.ib_send_message: {
			if (isForResult && validateFields()) {
				Intent reply = new Intent();
				mm = (MessageFile) getIntent().getSerializableExtra("MessageFile");
				/////////////////////////////////////////////////////////////////////
				Calendar c = Calendar.getInstance();
		        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
		        String formattedDate = dateFormatter.format(c.getTime());
		        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		        String formattedTime = timeFormatter.format(c.getTime());
		        mm.setDate(formattedDate);
		        mm.setTime(formattedTime);
		        //////////////////////////////////////////////////////////////////////
		        mm.setSubject(etMessageSubject.getText().toString());
		        mm.setMessegeText(etMessageText.getText().toString());
		        //////////////////////////////////////////////////////////////////////
				reply.putExtra("MessageFile", mm);
				setResult(1, reply);
				Toast.makeText(this, "Message Sent", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		break;
		default:
			break;
		}
	}


	private boolean validateFields() {
		// TODO Auto-generated method stub
		if (etMessageSubject.getText().toString().length() > 50 )
		{
			etMessageSubject.requestFocus();
			etMessageSubject.setError(Html.fromHtml("<font color='red'>Subject Should be less than 50 Characters!</font>"));
			return false;
		}
		else if (etMessageText.getText().toString().length() > 250 )
		{
			etMessageText.requestFocus();
			etMessageText.setError(Html.fromHtml("<font color='red'>Subject Should be less than 250 Characters!</font>"));
			return false;
		}
		return true;
	}
}
