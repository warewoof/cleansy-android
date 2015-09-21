package com.warewoof.cleansydemo.popups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;
import com.warewoof.cleansydemo.screens.CleaningActivity;

public class RoomCount extends Activity {

	public static final String TAG = "ChooseAddressActivity";
	private boolean bathroomOption;
	private int currentSelectedRoomCount;
	private final Context mContext = RoomCount.this;
	private OrderObject orderInfo;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_roomcount);
        
        Intent intent = getIntent();
        //bathroomOption = intent.getBooleanExtra(CleaningActivity.ISBATHROOM, false);
		if (bathroomOption) {
			((TextView) findViewById(R.id.titleText)).setText(getResources().getString(R.string.cleaning_roomcount_no_of_bathroom_text));
		} else {
			((TextView) findViewById(R.id.titleText)).setText(getResources().getString(R.string.cleaning_roomcount_no_of_bedroom_text));
		}
		
		try {
			orderInfo = ((Global) getApplication()).orderInfo;
			currentSelectedRoomCount = highlightRoomCount(orderInfo.getRoomCount());
		} catch (Exception e) {
			currentSelectedRoomCount = highlightRoomCount(0);
		}
		
    }
    
    public void clickedCount(View view) {
    	Log.d(TAG, "ClickedCount");
    	unhighlightRoomCounts();
    	view.setSelected(true);
    	currentSelectedRoomCount = Integer.parseInt(((TextView) view).getText().toString());
    }
  
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	
    	if (bathroomOption) {
    		orderInfo.setBathroomCount(currentSelectedRoomCount);
    	} else {
    		orderInfo.setRoomCount(currentSelectedRoomCount);
    	}
    	this.finish();
    }
    
    private int highlightRoomCount(int orderInfoRoomCount) {
    	
    	unhighlightRoomCounts();
    	
    	switch (orderInfoRoomCount) {    	
    	case 5:
    		findViewById(R.id.count5).setSelected(true);
    		return 5;
    	case 4:
    		findViewById(R.id.count4).setSelected(true);
    		return 4;
    	case 3:
    		findViewById(R.id.count3).setSelected(true);
    		return 3;
    	case 2:
    		findViewById(R.id.count2).setSelected(true);
    		return 2;
    	case 1:
    		findViewById(R.id.count1).setSelected(true);
    		return 1;
    	default:
    		findViewById(R.id.count0).setSelected(true);
    		return 0;
    	}
    		
    	
    }
    
    private void unhighlightRoomCounts() {
    	
    	findViewById(R.id.count0).setSelected(false);
    	findViewById(R.id.count1).setSelected(false);
    	findViewById(R.id.count2).setSelected(false);
    	findViewById(R.id.count3).setSelected(false);
    	findViewById(R.id.count4).setSelected(false);
    	findViewById(R.id.count5).setSelected(false);
    }
    
}






