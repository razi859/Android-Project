package com.example.Razi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Razi Rehman on 1/26/2018.
 */

public class StartUpFile extends Activity implements OnClickListener, OnItemClickListener,OnGroupClickListener, UserDelegate{

	private RelativeLayout rlInbox;
	private RelativeLayout rlLoginOption;
	private RelativeLayout rlDrawerRight;
	private LinearLayout llDrawerLeft;
	private ImageButton ibLeftMenu;
	private ImageButton ibRightUsers;
	private TextView tvTitle;
	//private DrawerLayout drawerLayout;
	android.support.v4.widget.DrawerLayout drawerLayout;
	private ListView lvMenu;
	private ListView lvUsers;
	private ArrayList<String> alMenu;
	private ArrayList<String> alUsers;
	private ArrayAdapter<String> aaMenu;
	private ArrayAdapter<String> aaUsers;
	private ExpandableListView elvUserMail;
	private Button gotoLogin;
	private Button gotoRegister;

	private ArrayList<UserModel> ums;
	private MessageFile mm;
	private ArrayList<ArrayList<MessageFile>> mms;
	private MailAdapterFile mela;
	private UserAdapterFile audla;
	
	SharedPreferences sharedpreferences;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.on_startup_view);
		///////////////////////////////////////////////////////////////////////////////

		rlInbox = (RelativeLayout) findViewById(R.id.rl_open_inbox);
		rlLoginOption = (RelativeLayout) findViewById(R.id.rl_login_option);
		rlDrawerRight = (RelativeLayout) findViewById(R.id.rl_drawer_right);
		llDrawerLeft = (LinearLayout) findViewById(R.id.ll_drawer_left);
		
		///////////////////////////////////////////////////////////////////////////////

		drawerLayout = (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Inbox");
		ibLeftMenu = (ImageButton) findViewById(R.id.ib_left_menu);
		ibRightUsers = (ImageButton) findViewById(R.id.ib_right_users);
		lvMenu = (ListView) findViewById(R.id.lv_menu);
		lvUsers = (ListView) findViewById(R.id.lv_users);
		elvUserMail = (ExpandableListView) findViewById(R.id.elv_user_mails);
			
		
		ibLeftMenu.setOnClickListener(this);
		ibRightUsers.setOnClickListener(this);
		lvMenu.setOnItemClickListener(this);
		lvUsers.setOnItemClickListener(this);
		elvUserMail.setOnItemClickListener(this);
		elvUserMail.setOnGroupClickListener(this);
		
		///////////////////////////////////////////////////////////////////////////////

		gotoLogin = (Button) findViewById(R.id.bt_to_login);
		gotoLogin.setOnClickListener(this);
		gotoRegister = (Button) findViewById(R.id.bt_to_register);
		gotoRegister.setOnClickListener(this);
		///////////////////////////////////////////////////////////////////////////////////
		sharedpreferences = getSharedPreferences("user_login_detail", this.MODE_PRIVATE);
		if (!sharedpreferences.getString("email", "").equals(""))
		{
			populateList();
			
			alMenu = new ArrayList<String>();
			alMenu.add(sharedpreferences.getString("email", ""));
			alMenu.add("Inbox");
			alMenu.add("Sent Items");
			alMenu.add("Message Thread");
			alMenu.add("Log Out");

			alUsers = new ArrayList<String>();
			alUsers.add("See Online Users");
			alUsers.add("See All Users");

			aaMenu = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alMenu);
			//aaUsers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alUsers);

			audla = new UserAdapterFile(this, ums);
			
			lvMenu.setAdapter(aaMenu);
			lvUsers.setAdapter(audla);

			mela = new MailAdapterFile(this, ums, mms);
			if (ums != null)
				elvUserMail.setAdapter(mela);
			
			startLoginChoiceViewController();
		}
		else
		{
			startLoginChoiceViewController();
		}
		///////////////////////////////////////////////////////////////////////////////////
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!sharedpreferences.getString("email", "").equals(""))
		{
			populateList();
			
			alMenu = new ArrayList<String>();
			alMenu.add(sharedpreferences.getString("email", ""));
			alMenu.add("Inbox");
			alMenu.add("Sent Items");
			alMenu.add("Message Thread");
			alMenu.add("Log Out");

			alUsers = new ArrayList<String>();
			alUsers.add("See Online Users");
			alUsers.add("See All Users");

			aaMenu = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alMenu);
			//aaUsers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alUsers);

			audla = new UserAdapterFile(this, ums);
			
			lvMenu.setAdapter(aaMenu);
			lvUsers.setAdapter(audla);

			mela = new MailAdapterFile(this, ums, mms);
			if (ums != null)
				elvUserMail.setAdapter(mela);
			
			startUserDetailViewController();
		}
		else
		{
			startLoginChoiceViewController();
		}
	}
	@Override
	public void printUserDetails() {
		// TODO Auto-generated method stub
		
	}
	public void startUserDetailViewController()
	{
		rlLoginOption.setVisibility(View.GONE);		
		rlInbox.setVisibility(View.VISIBLE);
		//rlDrawerRight.setVisibility(View.VISIBLE);
		//llDrawerLeft.setVisibility(View.VISIBLE);
		elvUserMail.setVisibility(View.VISIBLE);
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		
		//drawerLayout.setVisibility(View.VISIBLE);
		//gotoLogin.setVisibility(View.GONE);
		//gotoRegister.setVisibility(View.GONE);
		//ibLeftMenu.setVisibility(View.VISIBLE);
		//ibRightUsers.setVisibility(View.VISIBLE);
		//lvMenu.setVisibility(View.VISIBLE);
		//lvUsers.setVisibility(View.VISIBLE);
	}
	
	private void startLoginChoiceViewController() {

		rlLoginOption.setVisibility(View.VISIBLE);
		rlInbox.setVisibility(View.GONE);
		//rlDrawerRight.setVisibility(View.GONE);
		//llDrawerLeft.setVisibility(View.GONE);
		elvUserMail.setVisibility(View.GONE);
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		//drawerLayout.setVisibility(View.VISIBLE);
		//gotoLogin.setVisibility(View.VISIBLE);
		//gotoRegister.setVisibility(View.VISIBLE);
		//ibLeftMenu.setVisibility(View.GONE);
		//ibRightUsers.setVisibility(View.GONE)
		//lvMenu.setVisibility(View.GONE);
		//lvUsers.setVisibility(View.GONE);
	}
	@Override
	public void saveUserDetails(UserModel um) {
		// TODO Auto-generated method stub	
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
		case R.id.ib_left_menu:
			drawerLayout.openDrawer(Gravity.LEFT);
			break;
		case R.id.ib_right_users:
			drawerLayout.openDrawer(Gravity.RIGHT);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (lvMenu == parent)
		{
			String s = alMenu.get(position);
			Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
			toast.show(); 

			if (s.equals("Log Out"))
			{
				sharedpreferences.edit().clear().commit();
				//elvUserMail.removeAllViewsInLayout();
				aaMenu.clear();
				aaUsers.clear();
				startLoginChoiceViewController();
			}
			if (s.equals("Inbox"))
			{
				tvTitle.setText("Inbox");
				mms = DatabaseFile.getSharedInstance(this).readAllMessegesForInbox(sharedpreferences.getString("email", ""),ums);

				mela = new MailAdapterFile(this, ums, mms);
				if (ums != null)
					elvUserMail.setAdapter(mela);
			}
			if (s.equals("Sent Items"))
			{
				tvTitle.setText("Sent Items");
				mms = DatabaseFile.getSharedInstance(this).readAllMessegesForSentItem(sharedpreferences.getString("email", ""),ums);

				mela = new MailAdapterFile(this, ums, mms);
				if (ums != null)
					elvUserMail.setAdapter(mela);
			}
			if (s.equals("Message Thread"))
			{
				tvTitle.setText("Message Thread");
				mms = DatabaseFile.getSharedInstance(this).readAllMessegesForSentItem(sharedpreferences.getString("email", ""),ums);

				mela = new MailAdapterFile(this, ums, mms);
				if (ums != null)
					elvUserMail.setAdapter(mela);
			}
		}
		else
		{
			Toast toast = Toast.makeText(this, ums.get(position).getEmail(), Toast.LENGTH_SHORT);
			toast.show();

			Intent smc = new Intent(this, SendMessageFile.class);
			mm = new MessageFile(sharedpreferences.getString("email", ""),ums.get(position).getEmail(),"","","","","");
			smc.putExtra("isForResult", true);
			smc.putExtra("MessageFile", mm);
			//smc.putExtra("email", ums.get(position).getEmail());
			smc.putExtra("recieverName", ums.get(position).getName());
			startActivityForResult(smc, 100);
			
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == 1 && data != null) {
			mm = (MessageFile) data.getSerializableExtra("MessageFile");
			if (mm.getFrom().equals(mm.getTo()))
			{
				int i = -1;
				for (i = 0; i < ums.size(); i++)
				{
					if (mm.getFrom().equals(ums.get(i).getEmail()))
					{
						break;
					}
				}
				if (i >= 0 && i < ums.size())
				{
					mms.get(i).add(mm);
					mela.notifyDataSetChanged();
				}
			}
			else if (tvTitle.getText().toString().equals("Sent Items"))
			{

				int i = -1;
				for (i = 0; i < ums.size(); i++)
				{
					if (mm.getTo().equals(ums.get(i).getEmail()))
					{
						break;
					}
				}
				if (i >= 0 && i < ums.size())
				{
					mms.get(i).add(mm);
					mela.notifyDataSetChanged();
				}
			}
			DatabaseFile.getSharedInstance(this).insertMessage(mm);
		}
		else
		{
			
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
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

private void populateList() {
	ums = DatabaseFile.getSharedInstance(this).readAllUsers();
	if(tvTitle.getText().toString().equals("Inbox"))
		mms = DatabaseFile.getSharedInstance(this).readAllMessegesForInbox(sharedpreferences.getString("email", ""),ums);
	else if(tvTitle.getText().toString().equals("Sent Items"))
		mms = DatabaseFile.getSharedInstance(this).readAllMessegesForSentItem(sharedpreferences.getString("email", ""),ums);
	else if(tvTitle.getText().toString().equals("Message Thread"))
		mms = DatabaseFile.getSharedInstance(this).readAllMessegesForInbox(sharedpreferences.getString("email", ""),ums);
	
	/*um = new ArrayList<Department>();
	students = new ArrayList<ArrayList<Student>>();

	Department d1 = new Department();
	d1.name = "Computer Science";
	departments.add(d1);
	Department d2 = new Department();
	d2.name = "Information Technology";
	departments.add(d2);
	Department d3 = new Department();
	d3.name = "Software Engineering";
	departments.add(d3);

	for (int i = 0; i < 3; i++) {
		ArrayList<Student> deptStd = new ArrayList<Student>();
		for (int j = 0; j < 5; j++) {
			Student s = new Student();
			switch (i) {
			case 0: {
				s.name = "Ali Farooq CS "+j;
			}
			break;

			case 1: {
				s.name = "Ali Farooq IT "+j;
			}
			break;

			case 2: {
				s.name = "Ali Farooq SE "+j;
			}
			break;

			default:
				break;
			}
			deptStd.add(s);
		}
		students.add(deptStd);
	}*/
}

}
