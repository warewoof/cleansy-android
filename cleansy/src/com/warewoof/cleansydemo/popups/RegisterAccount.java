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


public class RegisterAccount extends Activity {

	private OrderObject orderInfo;
	private EditText mNameField;
	private EditText emailField;
	private EditText phoneField;
	private final Context mContext = RegisterAccount.this;
	
	public static final String TAG = "ChooseAddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_registeraccount);
        
        orderInfo = ((Global) this.getApplication()).orderInfo;
        
        mNameField = (EditText) findViewById(R.id.editfullname);
        
        emailField = (EditText) findViewById(R.id.editemail);
        emailField.setText(orderInfo.getCustomerEmail());
        phoneField = (EditText) findViewById(R.id.editphone);
        phoneField.setText(orderInfo.getCustomerPhone());
        //phoneField = (EditText) findViewById(R.id.editphone);
        //phoneField.setText(orderInfo.getAddressPhone());
		
    }
    
    public void ClickedContinueButton(View view) {
    	Log.d(TAG, "ClickedContinueButton");
    	
    	orderInfo.setRegistrationCompleted(true);
    	
    	orderInfo.setCustomerContactName(mNameField.getText().toString());
    	orderInfo.setCustomerEmail(emailField.getText().toString());
    	orderInfo.setCustomerPhone(phoneField.getText().toString());
    	
    	if (!orderInfo.isContactInfoEmpty()) {
    		orderInfo.setContactInfoCompleted(true);
    	} else {
    		orderInfo.setContactInfoCompleted(false);
    	}
	
    	
    	this.finish();
    }
    
    
    
    
}
