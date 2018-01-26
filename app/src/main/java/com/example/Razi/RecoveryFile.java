package com.example.Razi;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class RecoveryFile extends Activity implements OnClickListener {
	
	private EditText etEmail;
	private Button btRecoverPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recover_password_view);
		etEmail = (EditText) findViewById(R.id.et_recovery_email);
		btRecoverPassword = (Button) findViewById(R.id.bt_recover_password);
		btRecoverPassword.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_recover_password: {
			if ( !DatabaseFile.getSharedInstance(this).isEmailAlreadyExist(etEmail.getText().toString().trim()) )
			{
				etEmail.requestFocus();
				etEmail.setError(Html.fromHtml("<font color='red'>Email Not Registered</font>"));
	
				Toast.makeText(this, "User Doesnot Exists", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this, "OK: Password is sent to the Email", Toast.LENGTH_SHORT).show();
				this.finish();
			}
		}
		break;
		default:
			break;
		}
	}

}
