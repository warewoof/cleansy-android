package com.warewoof.cleansydemo;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;





public class Functions {
	
	
	public static void setCustomFont(Context c, TextView view) {
		 Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/Noteworthy.otf");	        
	        view.setTypeface(tf);
	}
	
	
}