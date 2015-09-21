package com.warewoof.cleansydemo.screens;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Functions;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.menu.MenuItems;
import com.warewoof.cleansydemo.menu.MenuUtil;
import com.warewoof.cleansydemo.popups.ContactInfo;

public class QuoteActivity extends FragmentActivity {

	public static final String TAG = "ChooseCleaningActivity";
	private OrderObject orderInfo;
	private final Context mContext = QuoteActivity.this;
	private Button registrationButton;
	private TextView price;
	private TextView time;

	private SlidingMenu menu;
	
	/**
	 * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
	 * 
	 * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
	 * from https://developer.paypal.com
	 * 
	 * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
	 * without communicating to PayPal's servers.
	 */
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "AfWouxAY4yV8jdvd_8Lg-dDYrZdeywOli6m5lI4SbCmIoshkddmUPMYJXZ7L";
	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	private static PayPalConfiguration config = new PayPalConfiguration()
	.environment(CONFIG_ENVIRONMENT)
	.clientId(CONFIG_CLIENT_ID)
	// The following are only used in PayPalFuturePaymentActivity.
	.merchantName("Cleansy")
	.merchantPrivacyPolicyUri(
			Uri.parse("https://www.example.com/privacy"))
			.merchantUserAgreementUri(
					Uri.parse("https://www.example.com/legal"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_quote);

		configSlidingMenu();
		
		initLayout();
		
		initOrderInfo();

		
		
		

		if (orderInfo == null) {
			Log.d(TAG, "orderInfo is null, restart application by calling login");
    		Intent intent = new Intent(mContext, LoginActivity.class);
        	mContext.startActivity(intent);
        	finish();
		}
		if (Global.isUserLoggedIn(mContext)) {
			refreshDisplay();
		}
        	
		
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
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

	public void ClickedBackButton(View view) {
		Log.d(TAG, "ClickedBackButton");
		Intent intent = new Intent(mContext, CleaningActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right,R.anim.push_out_right);
		finish();
		
		
	}

	public void ClickedContinueButton(View view) {
		Log.d(TAG, "ClickedContinueButton");

		
		
		if (!orderInfo.isContactInfoCompleted()) {
    		Toast.makeText(getApplicationContext(), 
    				getResources().getString(R.string.missingregistrationinfo),
    				Toast.LENGTH_SHORT).show();
    		return;
    	}
		
		if (Global.isUserLoggedIn(mContext) ) {
    		Global.setUserSharedPreferences(mContext, orderInfo);
    	}
		
		/*
		 * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
		 * Change PAYMENT_INTENT_SALE to PAYMENT_INTENT_AUTHORIZE to only
		 * authorize payment and capture funds later.
		 * 
		 * Also, to include additional payment details and an item list, see
		 * getStuffToBuy() below.
		 */
		PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

		Intent intent = new Intent(mContext, PaymentActivity.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		startActivityForResult(intent, REQUEST_CODE_PAYMENT);

	}

	private PayPalPayment getThingToBuy(String environment) {
		return new PayPalPayment(new BigDecimal(Double.toString(orderInfo.getEstimatedCost())), "USD",
				orderInfo.getPayPalItemDescription() , environment);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i(TAG, confirm.toJSONObject().toString(4));
						Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
						/**
						 * TODO: send 'confirm' (and possibly
						 * confirm.getPayment() to your server for verification
						 * or consent completion. See
						 * https://developer.paypal.com
						 * /webapps/developer/docs/integration
						 * /mobile/verify-mobile-payment/ for more details.
						 * 
						 * For sample mobile backend interactions, see
						 * https://github
						 * .com/paypal/rest-api-sdk-python/tree/master
						 * /samples/mobile_backend
						 */
						/*
						Toast.makeText(
								getApplicationContext(),
								"PaymentConfirmation info received from PayPal",
								Toast.LENGTH_LONG).show();
								*/

						Intent intent = new Intent(mContext, ReceiptActivity.class);
						mContext.startActivity(intent);
						finish();

					} catch (JSONException e) {
						Log.e(TAG, "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i(TAG, "The user canceled.");
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i(TAG,
						"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
			}
		} else {
			Log.d(TAG, "an impossible requestCode occurred: " + Integer.toString(requestCode));
		}
	}

	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
	
	public void ClickedContactInfoButton(View view) {
		Log.d(TAG, "ClickedButtonKeyRight");
		Intent intent = new Intent(mContext, ContactInfo.class);
		mContext.startActivity(intent);
	}

	public void onResume() {
		super.onResume();
		Log.d(TAG, "OnResume called");

		if (Global.isForceExiting(mContext)) {
			Log.d(TAG, "force exiting");
			finish();
		}
		
		refreshDisplay();

		if (orderInfo == null) {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			this.finish();
		}
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed Called");
		Intent intent = new Intent(mContext, CleaningActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right,R.anim.push_out_right);
		finish();
		
	}
	
	private void refreshDisplay() {
    	if (orderInfo != null) {
    		
    		price.setText(orderInfo.getEstimatedCostAsString());    		
    		time.setText("estimated time will be " + orderInfo.getEstimatedTimeAsString());
    		
	    	if (orderInfo.isContactInfoCompleted() && !orderInfo.isContactInfoEmpty()) {    		
				String displayText = orderInfo.getCustomerContactName() + '\n';
				displayText += orderInfo.getCustomerEmail() + '\n';
				displayText += orderInfo.getCustomerPhone();
			
				registrationButton.setSingleLine(false);
				registrationButton.setText(displayText);
			} else {
				registrationButton.setSingleLine(true);
				registrationButton.setText(R.string.quote_registration_button_text);
			}			
    	} else {
    		Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			this.finish();
    	}
    }
	
	private void configSlidingMenu() {
		menu = MenuUtil.configMenu(this);
    	getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuItems()).commit();
    }
	
	private void initLayout() {
		Functions.setCustomFont(mContext, (TextView) findViewById(R.id.customTitleFont));
		Functions.setCustomFont(mContext,(TextView) findViewById(R.id.quote_1stsection_title));
		Functions.setCustomFont(mContext, (TextView) findViewById(R.id.quote_3ndsection_title));

		registrationButton = (Button) findViewById(R.id.registrationbutton);
		price = (TextView) findViewById(R.id.quoteprice);
		time = (TextView) findViewById(R.id.quotetime);

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
}
