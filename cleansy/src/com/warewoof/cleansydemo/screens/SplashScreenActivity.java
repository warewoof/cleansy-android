package com.warewoof.cleansydemo.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;

public class SplashScreenActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 1500;

    private final Context mContext = SplashScreenActivity.this;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen_splash);
        
        Global.setStateAppLaunched(SplashScreenActivity.this);
        
        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
Global.setUserLoggedIn(mContext, true);
				
				
		    	Intent intent = new Intent(mContext, AppointmentActivity.class);
		    	//intent.putExtra(Global.INTENT_EXTRA_SUCCESSFUL_LOGIN, true);
		    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	setResult(Activity.RESULT_OK);
		    	mContext.startActivity(intent);
		    	overridePendingTransition(R.anim.push_in_left,R.anim.push_out_left);
		    	
		    	finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}