package com.warewoof.cleansydemo.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Functions;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.popups.Login;


public class LoginActivity extends Activity {

	public static final String TAG = "LoginActivity";
	private static final int REQUEST_LOGIN = 1;
	
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world", "fff@fff.fff:ffff" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	//private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	/*private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;*/
	private OrderObject orderInfo;
	private final Context mContext = LoginActivity.this;
	//private View mSubmitButton; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.screen_login);
        
        orderInfo = ((Global) getApplication()).initOrderObject();        
        
        // TODO: check for previous login, if so then skip login
        if (Global.isUserLoggedIn(mContext)) {
        	Intent intent = new Intent(mContext, AppointmentActivity.class);
	    	mContext.startActivity(intent);
			finish(); 
        }
        
        Global.setForceExit(mContext, false);
        
        Functions.setCustomFont(mContext, (TextView) findViewById(R.id.customTitleFont));
        Functions.setCustomFont(mContext, (TextView) findViewById(R.id.skipLoginLink));
        Functions.setCustomFont(mContext, (TextView) findViewById(R.id.signInButton));
    }
	
    public void ClickedSkipLogin(View view) {
    	Log.d(TAG, "loginButtonClicked");
    	Intent intent = new Intent(mContext, AppointmentActivity.class);
    	//intent.putExtra(Global.INTENT_EXTRA_SUCCESSFUL_LOGIN, false);
    	Global.setUserLoggedIn(mContext, false);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);    	
    	mContext.startActivity(intent);
    	overridePendingTransition(R.anim.push_in_left,R.anim.push_out_left);
    	finish();
    }
    
    public void ClickedLoginButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
		//attemptLogin();
    	
		Intent intent = new Intent(mContext, Login.class);
		startActivityForResult(intent, REQUEST_LOGIN);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LOGIN) {
			if (resultCode == Activity.RESULT_OK) {
				finish();
			}
		}
	}
    
    

	
}