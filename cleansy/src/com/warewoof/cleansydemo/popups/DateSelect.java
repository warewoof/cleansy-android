package com.warewoof.cleansydemo.popups;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.timessquare.CalendarPickerView;
import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;

public class DateSelect extends Activity {

	private OrderObject orderInfo;
	private String savedTime;
	private final Context mContext = DateSelect.this;
	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_dateselect);
        
        orderInfo = ((Global) this.getApplication()).orderInfo;
        
        Calendar displayspan = Calendar.getInstance();
        displayspan.add(Calendar.DATE, 50);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        
        if (orderInfo.isScheduleCompleted()) {
        	calendar.init(today, displayspan.getTime()).withSelectedDate(orderInfo.getDate());
        	savedTime = orderInfo.getTime();
        } else {
	        calendar.init(today, displayspan.getTime()).withSelectedDate(today);
        }
    }
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
    	Log.d(TAG, "Selected time in millis: " + calendar.getSelectedDate().getTime());
        

    	
    	orderInfo.setDate(calendar.getSelectedDate());
    	if (orderInfo.isScheduleCompleted()) {
    		orderInfo.setTime(savedTime);
    	}
    	Intent intent = new Intent(DateSelect.this, TimeSelect.class);
    	DateSelect.this.startActivity(intent);
    	this.finish();
    }
    
}
