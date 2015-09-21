package com.warewoof.cleansydemo.menu;

import android.app.Activity;
import android.content.Context;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.warewoof.cleansy.R;

public class MenuUtil {
	
	public static SlidingMenu configMenu(Context context) {
		
		SlidingMenu menu = new SlidingMenu(context);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity((Activity) context, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);
		return menu;
	}
}