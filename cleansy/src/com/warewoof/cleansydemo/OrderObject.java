package com.warewoof.cleansydemo;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;




public class OrderObject {
	private String addressStreet1;
	private String addressStreet2;
	private String addressCity;
	private String addressZip;
	private String addressNotes;
	private String mCustomerPhone;	
	//private Date cleanDate;
	private Calendar rawDate;
	//private String cleanTime;
	private int roomCount;
	private int bathroomCount;
	private boolean serviceShampooCarpet;
	private boolean serviceMoveClean;
	private boolean customerAvailable;
	private Date transactionStart;
	private boolean addressSet;
	private boolean dateSet;
	private boolean cleaningSet;
	private boolean contactInfoSet;
	private boolean registrationSet;
	public final static int CLEANING_RATE_BASIC = 0;
	public final static int CLEANING_RATE_PREMIUM = 1;
	public final static int KEY_AVAILABLE = 0;
	public final static int KEY_UNAVAILABLE = 1;
	private int mCleaningRate;
	private int mKeyAvailability;
	private int mCostEstimate;	// we never deal in decimals (cents) 
	private double mTimeEstimate;
	private String mCustomerContactName;
	private String mCustomerEmail;
	
	
	public static final String TAG = "OrderObject";
	
	public OrderObject() {
		Log.d(TAG, "initializing Order Object");
		transactionStart = Calendar.getInstance().getTime();
		rawDate = Calendar.getInstance();	
		addressStreet1 = "";
		addressStreet2 = "";
		addressCity = "";
		addressZip = "";
		mCustomerPhone = "";
		roomCount = 1;
		bathroomCount = 1;
		mCleaningRate = CLEANING_RATE_BASIC;
		mKeyAvailability = KEY_AVAILABLE;
		addressSet = false;
		dateSet = false;
	}
	
	public void setAddress(String street1, String street2, String city, String zipcode) {
		addressStreet1 = street1.trim();
		addressStreet2 = street2.trim();
		addressCity = city.trim();
		addressZip = zipcode.trim();
	}
	
	public void setAddressNotes(String notes) {
		addressNotes = notes.trim();
	}
	
	public String getAddressStreet1() { return addressStreet1; }
	public String getAddressStreet2() { return addressStreet2; }
	public String getAddressCity() { return addressCity; }	
	public String getAddressZip() { return addressZip; }
	public String getAddressNotes() { return addressNotes; }
	
	
	public void setDate(Date date) {
		//cleanDate = date;	
		rawDate.setTime(date);
		
	}
	
	public Date getDate() {
		//return cleanDate;
		return rawDate.getTime();
	}
	
	public String getDateString() {
		try
	    {
			//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");      
			DateFormat df = new SimpleDateFormat("EEEE, MMM d");
			DateFormat dateOnly = new SimpleDateFormat("d");
			return df.format(rawDate.getTime()) + getDayOfMonthSuffix(Integer.parseInt(dateOnly.format(rawDate.getTime())));	
	    }
	    catch(Exception e)
	    {
	        return null;
	    }
			
	}
	
	public void setTime(String time) {
		//cleanTime = time;
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		cal.setTime(date);
		
		rawDate.set(Calendar.HOUR, cal.get(Calendar.HOUR));
		rawDate.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		rawDate.set(Calendar.AM_PM, cal.get(Calendar.AM_PM));
		Log.d(TAG, "saved rawDate " + sdf.format(rawDate.getTime()));
	}
	
	public String getTime() {
		try
	    {     
			DateFormat df = new SimpleDateFormat("h:mma");
			Log.d(TAG, "retrieving rawDate " + df.format(rawDate.getTime()));
			return df.format(rawDate.getTime());	
	    }
	    catch(Exception e)
	    {
	        return null;
	    }
			
	}
	
	public void setRoomCount(int count) {
		roomCount = count;
	}
	
	public int getRoomCount() {
		return roomCount;
	}
	
	public void setBathroomCount(int count) {
		bathroomCount = count;
	}
	
	public int getBathroomCount() {
		return bathroomCount;
	}
	
	public void setServiceShampooCarpet(boolean opt) {
		serviceShampooCarpet = opt;
	}
	
	public boolean isServiceShampooCarpet() {
		return serviceShampooCarpet;
	}
	
	public void setServiceMoveClean(boolean opt) {
		serviceMoveClean = opt;
	}
	
	public boolean isServiceMoveClean() {
		return serviceMoveClean;
	}
	
	public void setCustomerAvailable(boolean opt) {
		customerAvailable = opt;
	}
	
	public boolean isCustomerAvailable() {
		return customerAvailable;
	}
	
	public int getEstimatedCost() {
		mCostEstimate = (roomCount + bathroomCount) * 20;
		mCostEstimate = isServiceShampooCarpet() ? mCostEstimate + 40 : mCostEstimate;
		mCostEstimate = isServiceMoveClean() ? mCostEstimate + 40 : mCostEstimate;
		return mCostEstimate;
	}
	
	public String getEstimatedCostAsString() {
		return "$" + Integer.toString(getEstimatedCost());
	}
	
	@SuppressLint("DefaultLocale")
	public double getEstimatedTime() {
		mTimeEstimate = (double) ((roomCount + bathroomCount) * .5);
		mTimeEstimate = isServiceShampooCarpet() ? mTimeEstimate + 1 : mTimeEstimate;
		mTimeEstimate = isServiceMoveClean() ? mTimeEstimate + 1 : mTimeEstimate;
		return mTimeEstimate;
	}
	
	public String getEstimatedTimeAsString() {
		mTimeEstimate = getEstimatedTime();
		 if(mTimeEstimate == (int) mTimeEstimate)
		        return String.format("%d",(int) mTimeEstimate) + " hour(s)";
		    else
		        return String.format("%s", mTimeEstimate) + " hour(s)";
	}
	
	public static String convertTimeToString(double d)
	{
	    if(d == (int) d)
	        return String.format("%d",(int)d);
	    else
	        return String.format("%s",d);
	}
	
	public void setAddressCompleted(boolean state) {
		addressSet = state;
	}
	
	public boolean isAddressCompleted() {
		return addressSet;
	}
	
	public void setScheduleCompleted(boolean state) {
		dateSet = state;
	}
	
	public boolean isScheduleCompleted() {
		return dateSet;
	}
	
	public void setCleaningCompleted(boolean state) {
		cleaningSet = state;
	}
	
	public boolean isCleaningCompleted() {
		return cleaningSet;
	}
	
	public boolean isAddressEmpty() {
		return addressStreet1 + addressStreet2 + addressCity + addressZip + mCustomerPhone == "" ? true : false;
	}
	
	public static String getDayOfMonthSuffix(final int n) {
	    if (n >= 11 && n <= 13) {
	        return "th";
	    }
	    switch (n % 10) {
	        case 1:  return "st";
	        case 2:  return "nd";
	        case 3:  return "rd";
	        default: return "th";
	    }
	}
	
	public void setDateTime(int hour, int minute) {
		Calendar tempTime;
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
	}
	
	public void setCleaningRate(int cr) {
		mCleaningRate = cr;
	}
	
	public int getCleaningRate() {
		return mCleaningRate;
	}
	
	public int getOrderAgeInMinutes() {
		Date nowDate = Calendar.getInstance().getTime();
		long diff = nowDate.getTime() - transactionStart.getTime();
		return (int) ((diff / (1000*60)) % 60);
	}
	
	public void refreshTransactionAge() {
		transactionStart = Calendar.getInstance().getTime();
	}
	
	public void setKeyAvailability(int key) {
		mKeyAvailability = key;
	}
	
	public int isKeyAvailable() {
		return mKeyAvailability;
	}
	
	public String getPayPalItemDescription() {
		DateFormat df = new SimpleDateFormat("MM/dd");		
		//return getEstimatedTimeAsString() + " cleaning on " + df.format(rawDate.getTime());
		return getEstimatedTimeAsString() + (mCleaningRate == CLEANING_RATE_BASIC ? " basic": " premium") + " cleaning";
	}
	
	public void setCustomerContactName(String name) {
		mCustomerContactName = name.trim();
	}
	
	public String getCustomerContactName() {
		return mCustomerContactName;
	}
	
	public void setCustomerFirstName(String name) {
		mCustomerContactName = name.trim();
	}
	
	public void setCustomerEmail(String email) {
		mCustomerEmail = email.trim();
	}
	
	public String getCustomerEmail() {
		return mCustomerEmail;
	}
	
	public void setCustomerPhone(String phone) {
		mCustomerPhone = phone.trim();
	}
	
	public String getCustomerPhone() { 
		return mCustomerPhone; 
	}
	
	public void setContactInfoCompleted(boolean state) {
		contactInfoSet = state;
	}
	
	public boolean isContactInfoCompleted() {
		return contactInfoSet;
	}
	
	public boolean isContactInfoEmpty() {
		return mCustomerContactName + mCustomerEmail + mCustomerPhone == "" ? true : false;
	}
	
	public void setRegistrationCompleted(boolean state) {
		registrationSet = state;
	}
	
	public boolean isRegistrationCompleted() {
		return registrationSet;
	}
	
}