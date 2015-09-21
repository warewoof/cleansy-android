package com.warewoof.cleansydemo.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.warewoof.cleansy.R;
import com.warewoof.cleansydemo.Global;
import com.warewoof.cleansydemo.screens.LoginActivity;

public class MenuItems extends ListFragment {

	private MenuAdapter adapter;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new MenuAdapter(getActivity());
		
		
		adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonpromotions), 
								R.drawable.ic_menu_star));
		adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonsupport),
								R.drawable.ic_menu_help));
		adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonshare), 
								R.drawable.ic_menu_allfriends));
		adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonsettings), 
								R.drawable.ic_menu_manage));
		adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonabout), 
								R.drawable.ic_menu_info_details));	
		if (Global.isUserLoggedIn(Global.getContext())) {
			adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonlogout), 
								R.drawable.ic_menu_blocked_user));
		} else {
			adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonlogin), 
								R.drawable.ic_menu_login));
		}
		adapter.add(new MenuItem(getResources().getString(R.string.optionsbuttonexit), 
								R.drawable.ic_menu_close_clear_cancel));
		setListAdapter(adapter);
		
	}

	private class MenuItem {
		public String tag;
		public int iconRes;
		public MenuItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class MenuAdapter extends ArrayAdapter<MenuItem> {

		public MenuAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {

		Log.d("menuclick", adapter.getItem(position).tag);
        
        /* menu order and corresponding activity is manually tracked
         * order is set at onActivityCreated */
        
        switch (position) {
        
        case 0:        	
        	break;        	
        case 1:        	
        	break;
        case 2:        	
        	break;        	
        case 3:        	
        	break;
        case 4:        	
        	break;        	
        case 5:		// Logout        	
        	if (Global.isUserLoggedIn(Global.getContext())) {
    	    	Global.setUserLoggedIn(Global.getContext(), false);
    	    	Global.setForceExit(Global.getContext(), true);
        	}

    		endActivity();
        	restartActivity();
        	
        	break;
        case 6:		// Exit
        	Global.setForceExit(Global.getContext(), true);
        	endActivity();
        	break;
        }
    }
	
	private void endActivity() {
		if (getActivity() == null) {
			return;
		} else {
			getActivity().finish();
		}
		
	}
	
	private void restartActivity() {
		Intent intent = new Intent(Global.getContext(), LoginActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	Global.getContext().startActivity(intent);
	}
}
