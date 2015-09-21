package com.warewoof.cleansydemo.popups;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;

public class TimeSelect extends Activity {

	TextView selectedView;
	TextView previousView;
	
	private OrderObject orderInfo;
	private final Context mContext = TimeSelect.this;
	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_timeselect);
        
        orderInfo = ((Global) getApplication()).orderInfo;
        
        TextView initTime = (TextView) findViewById(R.id.t1000AM);
        if (orderInfo.isScheduleCompleted()) {
        	initTime = (TextView) findViewById(findSelectedTime());
        } 
        HighlightTime(initTime);
        selectedView = initTime;
        previousView = initTime;
    }
    
    public void ClickedTime(View view) {
    	Log.d(TAG, "ClickedTime");
    	
    	selectedView = (TextView) view;
    	HighlightTime(selectedView);
    	if (previousView != null) {
    		UnhighlightTime(previousView);
    	}    	
    	previousView = (TextView) view;	    	 
    }
    
    private void HighlightTime(TextView timeView) {
    	timeView.setTextColor(Color.WHITE);
    	timeView.setBackgroundColor(getResources().getColor(R.color.modal_choosetime_timecolorbackgroundhighlight)); 
    }
    
    private void UnhighlightTime(TextView timeView) {
    	timeView.setTextColor(getResources().getColor(R.color.modal_choosetime_timecolortextdefault));
    	timeView.setBackgroundColor(Color.WHITE); 
    }
    
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	OrderObject orderInfo = ((Global) getApplication()).orderInfo;
    	orderInfo.setTime(selectedView.getText().toString());
    	
    	orderInfo.setScheduleCompleted(true);
    	
    	this.finish();
    }
    
    private int findSelectedTime() {
    	String needle = "t"+orderInfo.getTime().replace(":", "");
    	Log.d(TAG, needle);
    	//
    	return getResources().getIdentifier(needle, "id", getPackageName());
    	
    
    }
    
}
