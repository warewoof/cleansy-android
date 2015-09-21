package com.warewoof.cleansydemo.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Functions;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.menu.MenuItems;
import com.warewoof.cleansydemo.popups.OptionsMenu;
import com.warewoof.cleansydemo.popups.RegisterAccount;

public class ReceiptActivity extends FragmentActivity {

	public static final String TAG = "ChooseCleaningActivity";

	private OrderObject orderInfo;
	private final Context mContext = ReceiptActivity.this;
	private TextView registrationMessage;
	private View registrationButton;
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.screen_receipt);
		
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

	public void ClickedRegisterButton(View view) {
		Log.d(TAG, "ClickedButtonKeyRight");
		Intent intent = new Intent(mContext, RegisterAccount.class);
		startActivityForResult(intent, 0);
	}
	
	public void ClickedContinueButton(View view) {
		Log.d(TAG, "ClickedContinueButton");
		Intent intent = new Intent(mContext, SocialActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_left, R.anim.push_out_left);
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (orderInfo != null) {
    		if (orderInfo.isRegistrationCompleted()) {
    			registrationButton.setVisibility(View.INVISIBLE);
    			//registrationMessage.setVisibility(View.INVISIBLE);
    			registrationMessage.setText("Thank you for registering!");
    			
    		}
    	}
    }

	public void onResume() {
		// After a pause OR at startup
		super.onResume();
		Log.d(TAG, "OnResume called");

		if (Global.isForceExiting(mContext)) {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed Called");
		Intent intent = new Intent(mContext, SocialActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right,R.anim.push_out_right);
		finish();
		
	}
	
	private void configSlidingMenu() {
    	menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuItems()).commit();
    }
	
	private void initLayout() {
		Functions.setCustomFont(mContext, (TextView) findViewById(R.id.customTitleFont));
		
		registrationMessage = (TextView) findViewById(R.id.registrationtext);
		registrationButton = findViewById(R.id.registrationbutton);
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
		
	}
}
