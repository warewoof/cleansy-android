package com.warewoof.cleansydemo.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.warewoof.cleansy.R;


@SuppressLint("NewApi")
public class ServiceDescription extends Activity {


	private final Context mContext = ServiceDescription.this;
	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_servicedescription);
        
    }
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	this.finish();
    }
    
    
}
