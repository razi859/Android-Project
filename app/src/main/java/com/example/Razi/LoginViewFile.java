package com.example.Razi;

import android.app.Activity;
import android.database.Cursor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class LoginViewFile extends Activity implements OnClickListener {

	private EditText etEmail;
	private EditText etPassword;
	private Button btLogin;
	private TextView tvForget;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_user_view);

		etEmail = (EditText) findViewById(R.id.et_login_email);
		etPassword = (EditText) findViewById(R.id.et_login_pass);

		btLogin = (Button) findViewById(R.id.bt_login);
		btLogin.setOnClickListener(this);
		
		tvForget = (TextView) findViewById(R.id.tv_forgotPassword);
		tvForget.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login: {
			loginUser();
		}
		break;
		case R.id.tv_forgotPassword: {
			recoverPassword();
		}
		break;
		default:
			break;
		}
	}

	private void recoverPassword() {
		// TODO Auto-generated method stub
		//goto recovery screen
		Toast.makeText(this, "Recover Password", Toast.LENGTH_LONG).show();
		Intent rvc = new Intent(this, RecoveryFile.class);
		startActivity(rvc);
	}

	private void loginUser() {
		// TODO Auto-generated method stub
		if ( !DatabaseFile.getSharedInstance(this).isEmailAlreadyExist(etEmail.getText().toString().trim()) )
		{
			etEmail.requestFocus();
			etEmail.setError(Html.fromHtml("<font color='red'>Email Not Registered</font>"));

			Toast.makeText(this, "User Doesnot Exists", Toast.LENGTH_SHORT).show();
		}
		//insert data to DB
		else if (!DatabaseFile.getSharedInstance(this).isLoginValid(etEmail.getText().toString().trim(), etPassword.getText().toString().trim()))
		{
			etPassword.requestFocus();
			etPassword.setError(Html.fromHtml("<font color='red'>Invalid Password</font>"));

			Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Cursor res = DatabaseFile.getSharedInstance(this).getUser(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
			if (res.getCount() > 0)
				res.moveToFirst();
			//insert data to shared preference
			SharedPreferences sharedpreferences = getSharedPreferences("user_login_detail", this.MODE_PRIVATE);
			Editor editor = sharedpreferences.edit();
			editor.putString("name", res.getString(1).toString());
			editor.putString("email", res.getString(2).toString());
			editor.putString("phone", res.getString(4).toString());
			editor.putString("gender", res.getString(5).toString());
			editor.putString("dob", res.getString(6).toString());
			editor.commit();
			//goto startup screen
			Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
			//Intent suvc = new Intent(this, StartUpFile.class);
			//startActivity(suvc);
			this.finish();
		}
		
		
	}
	
}
