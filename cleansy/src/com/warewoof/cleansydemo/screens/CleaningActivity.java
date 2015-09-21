package com.warewoof.cleansydemo.screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Functions;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.menu.MenuItems;
import com.warewoof.cleansydemo.menu.MenuUtil;
import com.warewoof.cleansydemo.popups.ServiceDescription;

public class CleaningActivity extends FragmentActivity {

	private static final String TAG = "ChooseCleaningActivity";
	private OrderObject orderInfo;
	private final Context mContext = CleaningActivity.this;

	private Button button1;
	private Button button2;
	
	private View mQueryStatusView;
	private RetrieveQuoteTask mQuoteTask;
	
	private TextView bedroomCount;
	private TextView bathroomCount;
	
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_cleaning);

		configSlidingMenu();
		
		initLayout();
		
		initOrderInfo();
		
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
        	Log.d(TAG, "onMenuKeyUp");
        	menu.showMenu();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

	public void ClickedOptionsMenuButton(View view) {
		Log.d(TAG, "ClickedOptionsMenuButton");
		
		menu.showMenu();
	}
	
	public void clickedPickerButton(View view) {
		
		switch (view.getId()) {
		
		case R.id.bedroomaddbutton:
			incrementRoomCount(bedroomCount);
			orderInfo.setRoomCount(Integer.valueOf(bedroomCount.getText().toString()));
			break;
			
		case R.id.bedroomsubbutton:
			decrementRoomCount(bedroomCount);
			orderInfo.setRoomCount(Integer.valueOf(bedroomCount.getText().toString()));
			break;
			
		case R.id.bathroomaddbutton:
			incrementRoomCount(bathroomCount);
			orderInfo.setBathroomCount(Integer.valueOf(bathroomCount.getText().toString()));
			break;
		
		case R.id.bathroomsubbutton:
			decrementRoomCount(bathroomCount);
			orderInfo.setBathroomCount(Integer.valueOf(bathroomCount.getText().toString()));
			break;
		}
	}
	
	private void incrementRoomCount(TextView view) {
		int currentCount = Integer.parseInt(view.getText().toString());
		if (currentCount < 10 ) {
			view.setText(String.valueOf(currentCount + 1));
		}		
	}
	
	private void decrementRoomCount(TextView view) {
		int currentCount = Integer.parseInt(view.getText().toString());
		if (currentCount > 0 ) {
			view.setText(String.valueOf(currentCount - 1));
		}
	}

	public void ClickedButtonBasic(View view) {
		Log.d(TAG, "ClickedButton1");
		button1.setSelected(true);
		button2.setSelected(false);
		orderInfo.setCleaningRate(OrderObject.CLEANING_RATE_BASIC);
	}

	public void ClickedButtonPremium(View view) {
		Log.d(TAG, "ClickedButton2");
		button1.setSelected(false);
		button2.setSelected(true);
		orderInfo.setCleaningRate(OrderObject.CLEANING_RATE_PREMIUM);
	}

	public void ClickedQuestionButton(View view) {
		Log.d(TAG, "ClickedQuestionButton");
		Intent intent = new Intent(mContext, ServiceDescription.class);
		mContext.startActivity(intent);
	}

	public void ClickedBackButton(View view) {
		Log.d(TAG, "ClickedBackButton");
		Intent intent = new Intent(mContext, AppointmentActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right,R.anim.push_out_right);
		this.finish();
		
	}

	public void ClickedContinueButton(View view) {
		Log.d(TAG, "ClickedContinueButton");
		orderInfo.setCleaningCompleted(true);
		
		// TODO: Create AsyncTask for querying quote from server
		
		retrieveQuote();
	}

	public void onResume() {
		// After a pause OR at startup
		super.onResume();
		Log.d(TAG, "OnResume called");

		if (Global.isForceExiting(mContext)) {
			Log.d(TAG, "force exiting");
			finish();
		}
		
		refreshDisplay();

	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed Called");
		
		Intent intent = new Intent(mContext, AppointmentActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right,R.anim.push_out_right);
		this.finish();
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void retrieveQuote() {
		if (mQuoteTask != null) {
			return;
		}

		boolean cancel = false;

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			Toast.makeText(mContext, 
					getResources().getString(R.string.cleaning_getquote_general_error), 
					Toast.LENGTH_SHORT).show();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			//mQueryStatusMessage.setText(R.string.queryProgress);
			showProgress(true);
			mQuoteTask = new RetrieveQuoteTask();
			mQuoteTask.execute((Void) null);
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

			mQueryStatusView.setVisibility(View.VISIBLE);
			mQueryStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mQueryStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			/*
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
			*/
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mQueryStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			//mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RetrieveQuoteTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				return false;
			}
			
			// TODO: return the proper error message, instead of simple boolean

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mQuoteTask = null;
			

			if (success) {
				Log.d(TAG, "retrieve quote success");
				Intent confirmQuote = new Intent(mContext, QuoteActivity.class);
				confirmQuote.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				mContext.startActivity(confirmQuote);
				overridePendingTransition(R.anim.push_in_left,R.anim.push_out_left);
				finish();
			} else {
				Log.d(TAG, "retrieve quote failed");
				Global.setUserLoggedIn(mContext, false);
				showProgress(false);
			}
		}

		@Override
		protected void onCancelled() {
			mQuoteTask = null;
			showProgress(false);
		}
	}
	
	private void configSlidingMenu() {
		menu = MenuUtil.configMenu(this);
    	getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuItems()).commit();
    }
	
	private void initLayout() {
		Functions.setCustomFont(mContext,(TextView) findViewById(R.id.customTitleFont));
		Functions.setCustomFont(mContext,(TextView) findViewById(R.id.cleaning_1stsection_text));
		Functions.setCustomFont(mContext,(TextView) findViewById(R.id.cleaning_2stsection_text));

		button1 = (Button) findViewById(R.id.cleaningbutton1);
		button2 = (Button) findViewById(R.id.cleaningbutton2);
		
		bedroomCount = (TextView) findViewById(R.id.bedroomCountDisplay);
		bathroomCount = (TextView) findViewById(R.id.bathroomCountDisplay);
		
		mQueryStatusView = findViewById(R.id.queryStatus);		// default hidden
	}
	
	private void initOrderInfo() {
		
    	orderInfo = ((Global) getApplication()).orderInfo;
    	
    	if (Global.isUserLoggedIn(mContext)) {
        	if (orderInfo == null) {
        		Log.d(TAG, "orderInfo is null, restart application by calling login");
        		Intent intent = new Intent(mContext, LoginActivity.class);
            	mContext.startActivity(intent);
            	finish();
        	} else {
        		
            	orderInfo.refreshTransactionAge();
            	refreshDisplay();
        	}        	
        } else {
        	if (orderInfo == null) {
        		Log.d(TAG, "orderInfo is null, restart application by calling login");
        		Intent intent = new Intent(mContext, LoginActivity.class);
            	mContext.startActivity(intent);
            	finish();
        	} else {
        		orderInfo.refreshTransactionAge();
        	}
        }
    }
	
	private void refreshDisplay() {
		
		if (orderInfo != null) {
			bedroomCount.setText(Integer.toString(orderInfo.getRoomCount()));
			bathroomCount.setText(Integer.toString(orderInfo.getBathroomCount()));
			
			if (orderInfo.getCleaningRate() == OrderObject.CLEANING_RATE_BASIC) {
				button1.setSelected(true);
			} else {
				button2.setSelected(true);
			}
			
		} else {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			finish();
		}		
	}
}
