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
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Functions;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.menu.MenuItems;
import com.warewoof.cleansydemo.menu.MenuUtil;
import com.warewoof.cleansydemo.popups.AddressEntry;
import com.warewoof.cleansydemo.popups.DateSelect;
import com.warewoof.cleansydemo.popups.TimeSelect;

public class AppointmentActivity extends FragmentActivity {

	private Button addressButton;
	private Button dateButton;
	private OrderObject orderInfo;
	private SlidingMenu menu;
	private final Context mContext = AppointmentActivity.this;
	
public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

		setContentView(R.layout.screen_appointment);

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
    
    public void ClickedManualAddressButton(View view) {
    	Log.d(TAG, "ClickedManualAddressButton");
    	Intent intent = new Intent(mContext, AddressEntry.class);
    	mContext.startActivity(intent);
    }
    
    public void ClickedOptionsMenuButton(View view) {
    	Log.d(TAG, "ClickedOptionsMenuButton");
    
    	menu.showMenu();
    	
    }
    
    public void ClickedDatePickButton(View view) {
    	Log.d(TAG, "ClickedDatePickButton");
    	Intent intent = new Intent(mContext, DateSelect.class);
    	mContext.startActivity(intent);
    }
    
    public void ClickedTimePickButton(View view) {
    	Log.d(TAG, "ClickedTimePickButton");
    	Intent intent = new Intent(mContext, TimeSelect.class);
    	mContext.startActivity(intent);
    }
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	
    	if (!orderInfo.isAddressCompleted()) {
    		Toast.makeText(getApplicationContext(), 
    				getResources().getString(R.string.missingaddressinfo),
    				Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	if (!orderInfo.isScheduleCompleted()) {
    		Toast.makeText(getApplicationContext(), 
    				getResources().getString(R.string.missingscheduleinfo),
    				Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	if (Global.isUserLoggedIn(mContext) ) {
    		Global.setAddressSharedPreferences(mContext, orderInfo);
    	}
    	
    	Intent chooseCleaning = new Intent(mContext, CleaningActivity.class);
    	chooseCleaning.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); 
    	
    	mContext.startActivity(chooseCleaning);
    	overridePendingTransition(R.anim.push_in_left,R.anim.push_out_left);
    	
    	finish();
    	
    }
    
    public void onResume()
	{  
		// After a pause OR at startup
		super.onResume();
		Log.d(TAG, "OnResume called");
		
		if (Global.isForceExiting(mContext)) {
			finish();
		}
		
		refreshDisplay();
		
	}
    
    private void refreshDisplay() {
    	if (orderInfo != null) {
	    	if (orderInfo.isAddressCompleted() && !orderInfo.isAddressEmpty()) {    		
				String displayText = orderInfo.getAddressStreet1() + '\n';
				if (orderInfo.getAddressStreet2().length() != 0) {
					displayText += orderInfo.getAddressStreet2() + '\n';
				}
				displayText += orderInfo.getAddressCity() + '\n';
				displayText += orderInfo.getAddressZip() + '\n';
				if (orderInfo.getAddressNotes().length() != 0) {
					displayText += "[ " + orderInfo.getAddressNotes() + " ]";
				}
			
				addressButton.setSingleLine(false);
				addressButton.setText(displayText);
			} else {
				addressButton.setSingleLine(true);
				addressButton.setText(R.string.appointment_address_button_text);
			}
			
			if (orderInfo.isScheduleCompleted()) {
				String displayText = 
						orderInfo.getDateString() + '\n' +
						orderInfo.getTime();
				
				dateButton.setSingleLine(false);
				dateButton.setText(displayText);
			} else {
				dateButton.setSingleLine(true);
				dateButton.setText(R.string.appointment_date_button_text);
			}
    	} else {
    		Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			finish();
    	}
    }
    
    private void initLayout() {
    	 // Set custom fonts
        Functions.setCustomFont(mContext, (TextView) findViewById(R.id.customTitleFont));
        Functions.setCustomFont(mContext, (TextView) findViewById(R.id.wheretext));
        Functions.setCustomFont(mContext, (TextView) findViewById(R.id.whentext));
       
        // initialize screen references
        addressButton =  (Button) findViewById(R.id.manualaddressbutton);
        dateButton =  (Button) findViewById(R.id.datepickbutton);
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
        		// TODO: check return info before simply plugging in SP data
        		Global.getUserSharedPreferences(mContext, orderInfo);
				Global.getAddressSharedPreferences(mContext, orderInfo);
        		
        		orderInfo.setAddressCompleted(true);
        		orderInfo.setContactInfoCompleted(true);
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
    
    private void configSlidingMenu() {
    	/*
    	menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuItems()).commit();
		*/
    	menu = MenuUtil.configMenu(this);
    	getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuItems()).commit();
    }

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			Log.d(TAG, "onBackPressed Called");
			finish();
		}
	}
	
	public void endActivity() {
		finish();
	}
	
}
