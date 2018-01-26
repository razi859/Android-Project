package com.example.Razi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Razi Rehman on 1/24/2018.
 */

public class DatabaseFile extends SQLiteOpenHelper {

   private static DatabaseFile sInstance;
   
   public static final String DATABASE_NAME = "BlackMail.db";

   public static final String UD_TABLE_NAME = "userDetails";
   public static final String MD_TABLE_NAME = "messageDetails";
   
   private static final int DATABASE_VERSION = 1;
   
   private static final UserModel um = new UserModel("name" , "email" , "password" , "phone" , "gender" , "dob");
   private static final MessageFile mm = new MessageFile("sender", "reciever", "time" , "date" , "subject" , "messegeText", "content");
   
   public static final String COLUMN_ID = "id";
   
   /*public static final String UD_COLUMN_NAME = "name";
   public static final String UD_COLUMN_EMAIL = "email";
   public static final String UD_COLUMN_PHONE = "phone";
   public static final String UD_COLUMN_PASSWORD = "password";
   public static final String UD_COLUMN_GENDER = "gender";
   public static final String UD_COLUMN_DOB = "dob";
    */


   public static synchronized DatabaseFile getSharedInstance(Context context) {

     // Use the application context, which will ensure that you 
     // don't accidentally leak an Activity's context.
     // See this article for more information: http://bit.ly/6LRzfx
     if (sInstance == null) {
       sInstance = new DatabaseFile(context.getApplicationContext());
     }
     return sInstance;
   }

   /**
    * Constructor should be private to prevent direct instantiation.
    * make call to static method "getInstance()" instead.
    */
   private DatabaseFile(Context context) {
     super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
    		  "create table "+ UD_TABLE_NAME +
    		  "("+COLUMN_ID+" integer primary key autoincrement,"+
    				  um.getName()+" text,"+
    				  um.getEmail()+" text,"+
    				  um.getPassword()+" text,"+
    				  um.getPhone()+" text,"+
    				  um.getGender()+" text,"+
    				  um.getDob()+" text" +
    				  		")"
      );
      db.execSQL(
    		  "create table "+ MD_TABLE_NAME +
    		  "("+COLUMN_ID+" integer primary key autoincrement,"+
    				  mm.getFrom()+" text,"+
    				  mm.getTo()+" text,"+
    				  mm.getTime()+" text,"+
    				  mm.getDate()+" text,"+
    				  mm.getSubject()+" text,"+
    				  mm.getMessegeText()+" text,"+
    				  mm.getContent()+" text" +
    				  		")"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
	      db.execSQL("DROP TABLE IF EXISTS "+ UD_TABLE_NAME);
	      db.execSQL("DROP TABLE IF EXISTS "+ MD_TABLE_NAME);
      onCreate(db);
   }

   public String getPassword(String email){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from userDetails where "+um.getEmail()+"='"+email+"'", null );
      return res.getString(res.getColumnIndex(um.getPassword()));
   }
   
   public ArrayList<UserModel> readAllUsers()
   {
	   ArrayList<UserModel> ums = new ArrayList<UserModel>();
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor res =  db.rawQuery( "select * from " + UD_TABLE_NAME , null );
	   if (res.moveToFirst()) {
		   do {
			   UserModel obj = new UserModel();
			   obj.setName(res.getString(res.getColumnIndex(um.getName())));
			   obj.setEmail(res.getString(res.getColumnIndex(um.getEmail())));
			   obj.setPhone(res.getString(res.getColumnIndex(um.getPhone())));
			   obj.setGender(res.getString(res.getColumnIndex(um.getGender())));
			   obj.setDob(res.getString(res.getColumnIndex(um.getDob())));
			   ums.add(obj);
		   } while (res.moveToNext());
		 }
	   return ums;
   }
   public ArrayList<MessageFile> readAllMesseges(String email)
   {
	   ArrayList<MessageFile> mms = new ArrayList<MessageFile>();
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor res =  db.rawQuery( "select * from " + MD_TABLE_NAME +" where "+mm.getFrom()+"='"+email+"'" , null );
	   if (res.moveToFirst()) {
		   do {
			   MessageFile obj = new MessageFile();
			   obj.setFrom(res.getString(res.getColumnIndex(mm.getFrom())));
			   obj.setTo(res.getString(res.getColumnIndex(mm.getTo())));
			   obj.setTime(res.getString(res.getColumnIndex(mm.getTime())));
			   obj.setDate(res.getString(res.getColumnIndex(mm.getDate())));
			   obj.setSubject(res.getString(res.getColumnIndex(mm.getSubject())));
			   obj.setMessegeText(res.getString(res.getColumnIndex(mm.getMessegeText())));
			   obj.setContent(res.getString(res.getColumnIndex(mm.getContent())));
			   mms.add(obj);
		   } while (res.moveToNext());
		 }
	   return mms;
   }
   public ArrayList<ArrayList<MessageFile>> readAllMessegesForInbox(String userEmail,ArrayList<UserModel>ums)
   {
	   ArrayList<ArrayList<MessageFile>> almms = new ArrayList<ArrayList<MessageFile>>();
	   ArrayList<MessageFile> mms = new ArrayList<MessageFile>();
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor res =  db.rawQuery( "select * from " + MD_TABLE_NAME +" where " +mm.getTo()+ "='" +userEmail+ "' order by date desc, time desc", null );
	   if (res.moveToFirst()) {
		   do {
			   MessageFile obj = new MessageFile();
			   obj.setFrom(res.getString(res.getColumnIndex(mm.getFrom())));
			   obj.setTo(res.getString(res.getColumnIndex(mm.getTo())));
			   obj.setTime(res.getString(res.getColumnIndex(mm.getTime())));
			   obj.setDate(res.getString(res.getColumnIndex(mm.getDate())));
			   obj.setSubject(res.getString(res.getColumnIndex(mm.getSubject())));
			   obj.setMessegeText(res.getString(res.getColumnIndex(mm.getMessegeText())));
			   obj.setContent(res.getString(res.getColumnIndex(mm.getContent())));
			   mms.add(obj);
		   } while (res.moveToNext());
		 }
	   if (ums != null && mms!=null)
	   {
		   for (int i = 0 ; i < ums.size(); i++)
		   {
			   ArrayList<MessageFile> obj = new ArrayList<MessageFile>();
			   for (int j = 0 ; j < mms.size(); j++)
			   {
				   if (ums.get(i).getEmail().equals(mms.get(j).getFrom()))
				   {
					   obj.add(mms.get(j));
				   }				   
			   } 
			   almms.add(obj);
		   }
	   }
	   return almms;
   }
   public ArrayList<ArrayList<MessageFile>> readAllMessegesForSentItem(String userEmail,ArrayList<UserModel>ums)
   {
	   ArrayList<ArrayList<MessageFile>> almms = new ArrayList<ArrayList<MessageFile>>();
	   ArrayList<MessageFile> mms = new ArrayList<MessageFile>();
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor res =  db.rawQuery( "select * from " + MD_TABLE_NAME +" where " +mm.getFrom()+ "='" +userEmail+ "' order by date desc, time desc", null );
	   if (res.moveToFirst()) {
		   do {
			   MessageFile obj = new MessageFile();
			   obj.setFrom(res.getString(res.getColumnIndex(mm.getFrom())));
			   obj.setTo(res.getString(res.getColumnIndex(mm.getTo())));
			   obj.setTime(res.getString(res.getColumnIndex(mm.getTime())));
			   obj.setDate(res.getString(res.getColumnIndex(mm.getDate())));
			   obj.setSubject(res.getString(res.getColumnIndex(mm.getSubject())));
			   obj.setMessegeText(res.getString(res.getColumnIndex(mm.getMessegeText())));
			   obj.setContent(res.getString(res.getColumnIndex(mm.getContent())));
			   mms.add(obj);
		   } while (res.moveToNext());
		 }
	   if (ums != null && mms!=null)
	   {
		   for (int i = 0 ; i < ums.size(); i++)
		   {
			   ArrayList<MessageFile> obj = new ArrayList<MessageFile>();
			   for (int j = 0 ; j < mms.size(); j++)
			   {
				   if (ums.get(i).getEmail().equals(mms.get(j).getTo()))
				   {
					   obj.add(mms.get(j));
				   }				   
			   } 
			   almms.add(obj);
		   }
	   }
	   return almms;
   }
   
   public boolean isEmailAlreadyExist(String email){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from userDetails where email='"+email+"'", null );
	      return  (res.getCount() > 0);
	   }
   public boolean isLoginValid(String email, String password){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from userDetails where email='"+email+"' and password='" +password+"'", null );
	      return  (res.getCount() > 0);
	   }
   public Cursor getUser(String email, String password){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from userDetails where email='"+email+"' and password='" +password+"'", null );
	      return  res;
	   }

   public boolean insertUser (UserModel obj)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(um.getName(), obj.getName());
      contentValues.put(um.getEmail(), obj.getEmail());
      contentValues.put(um.getPassword(),obj.getPassword());
      contentValues.put(um.getPhone(),obj.getPhone());	
      contentValues.put(um.getGender(),obj.getGender());
      contentValues.put(um.getDob(),obj.getDob());
      db.insert(UD_TABLE_NAME, null, contentValues);
      return true;
   }
   public boolean insertMessage (MessageFile obj)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(mm.getFrom(), obj.getFrom());
      contentValues.put(mm.getTo(), obj.getTo());
      contentValues.put(mm.getTime(),obj.getTime());
      contentValues.put(mm.getDate(),obj.getDate());
      contentValues.put(mm.getSubject(),obj.getSubject());	
      contentValues.put(mm.getMessegeText(),obj.getMessegeText());
      contentValues.put(mm.getContent(),obj.getContent());
      db.insert(MD_TABLE_NAME, null, contentValues);
      return true;
   }
   public boolean retrieveMessage (MessageFile obj)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(mm.getFrom(), obj.getFrom());
      contentValues.put(mm.getTo(), obj.getTo());
      contentValues.put(mm.getTime(),obj.getTime());
      contentValues.put(mm.getDate(),obj.getDate());
      contentValues.put(mm.getSubject(),obj.getSubject());	
      contentValues.put(mm.getMessegeText(),obj.getMessegeText());
      contentValues.put(mm.getContent(),obj.getContent());
      db.insert(MD_TABLE_NAME, null, contentValues);
      return true;
   }

}
