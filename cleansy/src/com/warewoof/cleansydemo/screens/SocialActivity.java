package com.warewoof.cleansydemo.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Functions;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.menu.MenuItems;
import com.warewoof.cleansydemo.menu.MenuUtil;
import com.warewoof.cleansydemo.popups.ContactInfo;

public class SocialActivity extends FragmentActivity {

	public static final String TAG = "ChooseCleaningActivity";
	private OrderObject orderInfo;
	
	private SlidingMenu menu;
	private final Context mContext = SocialActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_social);

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

	public void ClickedBackButton(View view) {
		Log.d(TAG, "ClickedBackButton");
		Intent intent = new Intent(mContext, ReceiptActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right, R.anim.push_out_right);
		finish();
	}

	public void ClickedContinueButton(View view) {
		Log.d(TAG, "ClickedContinueButton");
		finish();
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
		
		if (orderInfo == null) {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed Called");
		
		Intent intent = new Intent(mContext, ReceiptActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right, R.anim.push_out_right);
		finish();
		
	}
	
	private void configSlidingMenu() {
		menu = MenuUtil.configMenu(this);
    	getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuItems()).commit();
    }
	
	private void initLayout() {
		Functions.setCustomFont(mContext, (TextView) findViewById(R.id.customTitleFont));
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
