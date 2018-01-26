package com.example.Razi;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class RegisterControllerFile extends Activity implements OnClickListener, OnItemSelectedListener, UserDelegate {
	private EditText etName;
	private EditText etEmail;
	private EditText etPass;
	private EditText etCnfrmPass;
	private EditText etPhone;
	private Spinner spGender;
	private DatePicker dpDob;
	private CheckBox cbAgree;
	private Button btRegister;
	private boolean isForResult = true;
	
	private ArrayList<String> genders;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_user_view);

		etName = (EditText) findViewById(R.id.et_reg_name);
		etEmail = (EditText) findViewById(R.id.et_reg_email);
		etPass = (EditText) findViewById(R.id.et_reg_pass);
		etCnfrmPass = (EditText) findViewById(R.id.et_reg_cnfrm_pass);
		etPhone = (EditText) findViewById(R.id.et_reg_phone);
		spGender = (Spinner) findViewById(R.id.sp_reg_gender);
		dpDob = (DatePicker) findViewById(R.id.dp_reg_dob);
		cbAgree = (CheckBox) findViewById(R.id.cb_reg_agree);
		//////////////////////////////////////////////////////////
		btRegister = (Button) findViewById(R.id.bt_register);
		btRegister.setOnClickListener(this);
		//////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////
		spGender.setOnItemSelectedListener(this);

		genders = new ArrayList<String>();
		genders.add("Male");
		genders.add("Female");

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spGender.setAdapter(adapter);
		spGender.setPrompt("Select Gender");
		//////////////////////////////////////////////////////////
		//isForResult = getIntent().getBooleanExtra("isForResult", false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_register: {
				registerUser();
		}
		break;
		default:
			break;
		}
	}

	private void registerUser() {
		// TODO Auto-generated method stub
		if (validateFields()
			&& validateEmailRegex(etEmail.getText().toString().trim())
			&& validatePasswordLength()
			&& confirmPasswordMatch()
			&& agreeTermsConditions()
		)
		{
			//check if user Already Exist
			if ( DatabaseFile.getSharedInstance(this).isEmailAlreadyExist(etEmail.getText().toString().trim()) )
			{
				etEmail.requestFocus();
				etEmail.setError(Html.fromHtml("<font color='red'>Email Already Exists</font>"));

				Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show();
			}
			//insert data to DB
			else
			{
				int mon = dpDob.getMonth() + 1;
				UserModel um = new UserModel(etName.getText().toString().trim(),
						 etEmail.getText().toString().trim(),
						 etPass.getText().toString().trim(),
						 etPhone.getText().toString().trim(),
						 spGender.getSelectedItem().toString(),
						 dpDob.getYear()+ "/" + mon + "/" + dpDob.getDayOfMonth());
				DatabaseFile.getSharedInstance(this).insertUser(um);
				//insert data to shared preference
				SharedPreferences sharedpreferences = getSharedPreferences("user_login_detail", this.MODE_PRIVATE);
				Editor editor = sharedpreferences.edit();
				editor.putString("name", um.getName());
				editor.putString("email", um.getEmail());
				editor.putString("phone", um.getPhone());
				editor.putString("gender", um.getGender());
				editor.putString("dob", um.getDob() );
				editor.commit();
				//goto startup screen
				Toast.makeText(this, "New User Is Registered", Toast.LENGTH_LONG).show();
				this.finish();
			}
		}
	}
	
	private boolean agreeTermsConditions() {
		// TODO Auto-generated method stub
		if (cbAgree.isChecked())
		{
			cbAgree.requestFocus();
			cbAgree.setError(null);
			return true;
		}
		else
		{
			cbAgree.requestFocus();
			cbAgree.setError(Html.fromHtml("<font color='red'>Accept !!!</font>"));
			Toast.makeText(this, "Accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
			return false;
		}
		
	}

	private boolean confirmPasswordMatch() {
		// TODO Auto-generated method stub
		if (etPass.getText().toString().trim().equals(etCnfrmPass.getText().toString().trim()))
        {
        	return true;
        }
        else
        {
			etCnfrmPass.requestFocus();
			etCnfrmPass.setError(Html.fromHtml("<font color='red'>Password MisMatch</font>"));
			return false;
        }
	}
	private boolean validatePasswordLength() {
		// TODO Auto-generated method stub
		if (etPass.getText().toString().trim().length() >=6 && etPass.getText().toString().trim().length() <= 12)
        {
        	return true;
        }
        else
        {
			etPass.requestFocus();
			etPass.setError(Html.fromHtml("<font color='red'>Password should be 6-12 characters long</font>"));
			return false;
        }
	}
	private boolean validateEmailRegex(String emailStr) {
		// TODO Auto-generated method stub
		final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        if (matcher.find())
        {
        	return true;
        }
        else
        {
			etEmail.requestFocus();
			etEmail.setError(Html.fromHtml("<font color='red'>Two Regex Fromat is Required</font>"));
			return false;
        }
	}
	private boolean validateFields() {
		String str = etName.getText().toString();
		if ( !etName.getText().toString().trim().equals("")
			&& !etEmail.getText().toString().trim().equals("")
			&& !etPass.getText().toString().trim().equals("")
			&& !etCnfrmPass.getText().toString().trim().equals("")
			&& !etPhone.getText().toString().trim().equals("")
				)
			return true;
		else
		{
			if (etName.getText().toString().trim().equals(""))
			{
				etName.requestFocus();
				etName.setError(Html.fromHtml("<font color='red'>Field Required</font>"));
			}
			if (etEmail.getText().toString().trim().equals(""))
			{
				etEmail.requestFocus();
				etEmail.setError(Html.fromHtml("<font color='red'>Field Required</font>"));
			}
			if (etPass.getText().toString().trim().equals(""))
			{
				etPass.requestFocus();
				etPass.setError(Html.fromHtml("<font color='red'>Field Required</font>"));
			}
			if (etCnfrmPass.getText().toString().trim().equals(""))
			{
				etCnfrmPass.requestFocus();
				etCnfrmPass.setError(Html.fromHtml("<font color='red'>Field Required</font>"));
			}
			if (etPhone.getText().toString().trim().equals(""))
			{
				etPhone.requestFocus();
				etPhone.setError(Html.fromHtml("<font color='red'>Field Required</font>"));
			}
				return false;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void printUserDetails() {
		// TODO Auto-generated method stub
		
	}

	public void startUserDetailViewController() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveUserDetails(UserModel um) {
		// TODO Auto-generated method stub
		
	}
}
