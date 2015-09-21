package com.warewoof.cleansydemo;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class Global extends Application {
	/* Shared Preference settings */ 
	private static final String SP_FILE_NAME = "cleansy_sp_file";  //share preference file name
	private static final String SP_CURRENT_STATE = "current_state";
	private static final String SP_STATE_APPLAUNCH = "state_applaunch";
	private static final String SP_STATE_NEWUSER = "state_newuser";
	private static final String SP_STATE_USERLOGGEDIN = "state_userloggedin";
	private static final String SP_STATE_ADDRESSSET = "state_addressset";
	private static final String SP_STATE_SCHEDULESET = "state_scheduleset";
	private static final String SP_STATE_CLEANINFOSET = "state_cleaningfoset";
	private static final String SP_STATE_PAYMENTMADE = "state_paymentmade";
	private static final String SP_STATE_ORDERACKNOWLEDGED = "state_orderacknowledged";
	
	private static final String SP_USER_FULL_NAME = "sp_user_full_name";
	private static final String SP_USER_EMAIL = "sp_user_email";
	private static final String SP_USER_PHONE = "sp_user_phone";
	private static final String SP_ADDRESS_STREET1 = "sp_address_street1";
	private static final String SP_ADDRESS_STREET2 = "sp_address_street2";
	private static final String SP_ADDRESS_CITY = "sp_address_city";
	private static final String SP_ADDRESS_ZIP = "sp_address_zip";
	private static final String SP_ADDRESS_NOTES = "sp_address_notes";
	
	public static final String INTENT_EXTRA_SUCCESSFUL_LOGIN = "intex_succesfullogin";
	public static final String SP_IS_USER_LOGGEDIN = "sp_is_user_logged_in";
	public static final String SP_FORCE_EXIT = "sp_force_exit";
	
	public static Context context;
	//private static SharedPreferences defaultPreferences;
	public static ArrayList<Object> tempMapItems = null;
	public OrderObject orderInfo;
	
	private static String TAG = "Global";
	
	@Override
	public void onCreate() {
		super.onCreate();	
		Log.d(TAG, "onCreate called");
		context = getApplicationContext();
	}
	
	public static Context getContext() {
        return context;
    }
	
	public OrderObject initOrderObject() {
		orderInfo = new OrderObject();
		return orderInfo;
	}
	
	private static void setState(Context c, String newState) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SP_CURRENT_STATE, newState);
		editor.commit();
	}	
	
	public static void setStateAppLaunched(Context c) {
		setState(c, SP_STATE_APPLAUNCH);
	}
	
	public static void setStateNewUser(Context c) {
		setState(c, SP_STATE_NEWUSER);
	}	
	
	public static void setStateUserLoggedIn(Context c) {
		setState(c, SP_STATE_USERLOGGEDIN);
	}
	
	public static void setStateAddressSet(Context c) {
		setState(c, SP_STATE_ADDRESSSET);
	}
	
	public static void setStateScheduleSet(Context c) {
		setState(c, SP_STATE_SCHEDULESET);
	}
	
	public static void setStateCleanInfoSet(Context c) {
		setState(c, SP_STATE_CLEANINFOSET);
	}
	
	public static void setStatePaymentMade(Context c) {
		setState(c, SP_STATE_PAYMENTMADE);
	}	
	
	public static void setStateOrderAcknowledged(Context c) {
		setState(c, SP_STATE_ORDERACKNOWLEDGED);
	}
	
	public static boolean isStateOrderAcknowledged(Context c) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        return settings.getString(SP_CURRENT_STATE, SP_STATE_ORDERACKNOWLEDGED).equals(SP_STATE_ORDERACKNOWLEDGED);
	}
	
	public static void setAddressSharedPreferences(Context c, OrderObject order) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SP_ADDRESS_STREET1, order.getAddressStreet1());
		editor.putString(SP_ADDRESS_STREET2, order.getAddressStreet2());
		editor.putString(SP_ADDRESS_CITY, order.getAddressCity());
		editor.putString(SP_ADDRESS_ZIP, order.getAddressZip());
		editor.putString(SP_ADDRESS_NOTES, order.getAddressNotes());
		editor.commit();
	}
	
	public static void getAddressSharedPreferences(Context c, OrderObject order) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, 0);
		order.setAddress(
				settings.getString(SP_ADDRESS_STREET1, ""), 
				settings.getString(SP_ADDRESS_STREET2, ""), 
				settings.getString(SP_ADDRESS_CITY, ""), 
				settings.getString(SP_ADDRESS_ZIP, ""));
		order.setAddressNotes(settings.getString(SP_ADDRESS_NOTES, ""));
	}
	
	public static void setUserSharedPreferences(Context c, OrderObject order) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SP_USER_FULL_NAME, order.getCustomerContactName());
		editor.putString(SP_USER_PHONE, order.getCustomerPhone());
		editor.putString(SP_USER_EMAIL, order.getCustomerEmail());
		editor.commit();
	}
	
	public static void getUserSharedPreferences(Context c, OrderObject order) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, 0);
		order.setCustomerContactName(settings.getString(SP_USER_FULL_NAME, ""));
		order.setCustomerEmail(settings.getString(SP_USER_EMAIL, ""));
		order.setCustomerPhone(settings.getString(SP_USER_PHONE, ""));
	}
	
	public static void setUserLoggedIn(Context c, boolean status) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(SP_IS_USER_LOGGEDIN, status);
		editor.commit();
	}
	
	public static boolean isUserLoggedIn(Context c) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, 0);
		return settings.getBoolean(SP_IS_USER_LOGGEDIN, false); 
	}	
	
	public static void setForceExit(Context c, boolean status) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(SP_FORCE_EXIT, status);
		editor.commit();
	}
	
	public static boolean isForceExiting(Context c) {
		SharedPreferences settings = c.getSharedPreferences(SP_FILE_NAME, 0);
		return settings.getBoolean(SP_FORCE_EXIT, false); 
	}	
}








