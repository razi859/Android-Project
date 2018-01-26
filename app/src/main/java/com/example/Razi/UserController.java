package com.example.Razi;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class UserController {
	private static UserController uc;
	private UserModel u;
	private UserDelegate ud;
	
	public static UserController getSharedInstance(){
		if (uc == null) {
			uc = new UserController();
		}
		return uc;
	}
	
	public void SetUserObject(UserModel user)
	{
		this.u = user;
	}
	
	public void startUserDetailViewController(UserDelegate sd) {
		this.ud = sd;
		if (this.ud != null) {
			ud.startUserDetailViewController();
		}
	}
}