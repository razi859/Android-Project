package com.example.Razi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by Razi Rehman on 1/24/2018.
 */

public class LoginChoiceFile extends Activity implements OnClickListener {
	private Button gotoLogin;
	private Button gotoRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_option_view);
		
		gotoLogin = (Button) findViewById(R.id.bt_to_login);
		gotoLogin.setOnClickListener(this);
		gotoRegister = (Button) findViewById(R.id.bt_to_register);
		gotoRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_to_login: {
			startLoginView();
		}
		break;
		case R.id.bt_to_register: {
			startRegisterView();
		}
		break;
		default:
			break;
		}
	}

	private void startLoginView() {
		// TODO Auto-generated method stub

		Intent lvc = new Intent(this, LoginViewFile.class);
		startActivity(lvc);
	}

	private void startRegisterView() {
		// TODO Auto-generated method stub

		Intent rvc = new Intent(this, RegisterControllerFile.class);
		startActivity(rvc);
	}

}
