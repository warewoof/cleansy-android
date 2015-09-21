package com.warewoof.cleansydemo.popups;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.screens.AppointmentActivity;


public class Login extends Activity {

	private OrderObject orderInfo;
	private EditText street1Field;
	private EditText street2Field;
	private EditText cityField;
	private EditText zipField;
	private EditText notesField;
	private final Context mContext = Login.this;
	
	private EditText mUserEditText;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	// Values for email and password at the time of the login attempt.
		private String mEmail;
		private String mPassword;
	
	private UserLoginTask mAuthTask = null;
	
	private static final String[] DUMMY_CREDENTIALS = new String[] {
		"foo@example.com:hello", "bar@example.com:world", "fff@fff.fff:ffff" };

	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_login);
        
        orderInfo = ((Global) this.getApplication()).orderInfo;
        
        mUserEditText = (EditText) findViewById(R.id.edituser);

 		mPasswordView = (EditText) findViewById(R.id.editpassword);
 		mPasswordView
 				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
 					@Override
 					public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
 						Log.d(TAG, "setOnEditorActionListener");
 						hideSoftKeyboard(Login.this);
 						attemptLogin(); 							
 						return true;
 					}
 				});
 		
 		mLoginFormView = findViewById(R.id.inputArea);
 		mLoginStatusView = findViewById(R.id.loginStatus);
 		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
 	
 		mUserEditText.requestFocus();
    }
    
    public void ClickedLoginButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	hideSoftKeyboard(Login.this);
		attemptLogin();
    	
    }
    
    /**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserEditText.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mUserEditText.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mUserEditText.setError(getString(R.string.error_field_required));
			focusView = mUserEditText;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mUserEditText.setError(getString(R.string.error_invalid_email));
			focusView = mUserEditText;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}
			
			// TODO: return the proper error message, instead of simple boolean

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			

			if (success) {
				Log.d(TAG, "login success");
				Global.setUserLoggedIn(mContext, true);
				
				
		    	Intent intent = new Intent(mContext, AppointmentActivity.class);
		    	//intent.putExtra(Global.INTENT_EXTRA_SUCCESSFUL_LOGIN, true);
		    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	setResult(Activity.RESULT_OK);
		    	mContext.startActivity(intent);
		    	overridePendingTransition(R.anim.push_in_left,R.anim.push_out_left);
				finish();
				
			} else {
				Log.d(TAG, "login failed");
				Global.setUserLoggedIn(mContext, false);
				showProgress(false);
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
	
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
}
