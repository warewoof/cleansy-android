package com.warewoof.cleansydemo.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;


@SuppressLint("NewApi")
public class OptionsMenu extends Activity {

	private OrderObject orderInfo;
	private final Context mContext = OptionsMenu.this;
	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_optionsmenu);
        
        orderInfo = ((Global) this.getApplication()).orderInfo;
        
        if (!Global.isUserLoggedIn(mContext)) {
        	disableButton((Button) findViewById(R.id.optionsbuttonlogout));
        }
		
    }
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");

    	overridePendingTransition(R.anim.push_in_left,R.anim.push_out_left);
    	
    	finish();
    }
    
    public void ClickedLogoutButton(View view) {
    	Log.d(TAG, "ClickedLogoutButton");
    	if (Global.isUserLoggedIn(mContext)) {
	    	Global.setUserLoggedIn(mContext, false);
	    	Global.setForceExit(mContext, true);
	    	this.finish();
    	}    	
    }
    
    private void disableButton(Button btn) {
    	btn.setClickable(false);

    	int sdk = android.os.Build.VERSION.SDK_INT;
    	if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
    		btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.disabledbutton));
    	} else {
    		btn.setBackground(getResources().getDrawable(R.drawable.disabledbutton));
    	}
    }
    
}
