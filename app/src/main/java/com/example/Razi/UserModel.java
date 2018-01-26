package com.example.Razi;

import java.io.Serializable;


public class UserModel implements Serializable {
	private String name;
	private String email;
	private String password;
	private String phone;
	private String gender;
	private String dob;
	public UserModel()
	{}
	public UserModel(String name, String email, String pass, String phone, String gender, String dob) {
		setName(name);
		setEmail(email);
		setPassword(pass);
		setPhone(phone);
		setGender(gender);
		setDob(dob);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
}