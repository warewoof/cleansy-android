package com.warewoof.cleansydemo.popups;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.OrderObject;


public class AddressEntry extends Activity {

	private OrderObject orderInfo;
	private EditText street1Field;
	private EditText street2Field;
	private EditText cityField;
	private EditText zipField;
	private EditText notesField;
	private final Context mContext = AddressEntry.this;
	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_addressentry);
        
        orderInfo = ((Global) this.getApplication()).orderInfo;
        
        street1Field = (EditText) findViewById(R.id.editstreet);
        street1Field.setText(orderInfo.getAddressStreet1());
        street2Field = (EditText) findViewById(R.id.editstreet2);
        street2Field.setText(orderInfo.getAddressStreet2());
        cityField = (EditText) findViewById(R.id.editcity);
        cityField.setText(orderInfo.getAddressCity());
        zipField = (EditText) findViewById(R.id.editzip);
        zipField.setText(orderInfo.getAddressZip());
        notesField = (EditText) findViewById(R.id.editnotes);
        notesField.setText(orderInfo.getAddressNotes());
        //phoneField = (EditText) findViewById(R.id.editphone);
        //phoneField.setText(orderInfo.getAddressPhone());
		
    }
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	
    	
    	orderInfo.setAddress(street1Field.getText().toString(),
    			street2Field.getText().toString(),
    			cityField.getText().toString(), 
    			zipField.getText().toString());
    	
    	orderInfo.setAddressNotes(notesField.getText().toString());
    	
    	if (!orderInfo.isAddressEmpty()) {
    		orderInfo.setAddressCompleted(true);
    	} else {
    		orderInfo.setAddressCompleted(false);
    	}
	
    	
    	this.finish();
    }
    
}
