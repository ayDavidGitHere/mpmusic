package com.aydavid.mpmusic;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.view.View.*;
import android.transition.*;
import java.util.*;
/****
addView Layoutinflater,promptsview to BigView does not maintain desired centering
Use switch visibility with popMenu
***/
public class CustomPopupMenu
{		
		public final ArrayList <View> items = new ArrayList();
		public ViewGroup view_attachTo;
		public LinearLayout promptsView;	public LayoutInflater li;
		public ViewGroup popMenu;
		public Context context;
		public CustomPopupMenu _this = this;
		public void setGravity(int gravity) {		
			promptsView.setGravity(Gravity.CENTER_HORIZONTAL);	
		}
		public int getGravity() {
			return promptsView.getGravity();
		}
		public void show() { 
			//view_attachTo.addView(promptsView);
			popMenu.setVisibility(View.VISIBLE);
		}
		public void dismiss() {
			//view_attachTo.removeView(promptsView);
			popMenu.setVisibility(View.GONE);
		}
		public void inflate(int menuRes) {   
			promptsView = (LinearLayout) li.inflate(menuRes, null);
			promptsView.setAlpha(.3f);
			for (int i = 0; i < ((ViewGroup) popMenu).getChildCount(); i++) {
				View item = (View) popMenu.getChildAt(i);
				this.items.add(item);
				item.setOnClickListener( new View.OnClickListener(){
						@Override
						public void onClick(View v){
							_this.dismiss();
						}//EO onClick
				});
			}
		}//EO inflate
		
		
		
		public void setOnMenuItemClickListener(CustomPopupMenu.OnMenuItemClickListener listener) {
			
		}
		public void setOnDismissListener(CustomPopupMenu.OnDismissListener listener) {}
		public CustomPopupMenu(final android.content.Context context, android.view.View anchor) {
			li = LayoutInflater.from(context);
			view_attachTo = (ViewGroup) anchor;//.getParent();
			popMenu = view_attachTo.findViewById(R.id.Lay_popmenu);
			
			/*
			//remove popup onlose focus
			Toast.makeText(context.getApplicationContext(), ""+view_attachTo.getId(), 2000).show();
			view_attachTo.setOnTouchListener(new View.OnTouchListener(){
				@Override
				public boolean onTouch(View view, MotionEvent p2){
					//Toast.makeText(context.getApplicationContext(), ""+view.getId(), 2000).show();
					//if(view!=popMenu)Toast.makeText(context.getApplicationContext(), "Not: "+view.getId(), 2000).show();
					return true;
				}
			});//EO setonclickli
			*/
		}
		public CustomPopupMenu(android.content.Context context, android.view.View anchor, int gravity) {}
		public CustomPopupMenu(android.content.Context context, android.view.View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {}


		public static abstract interface OnDismissListener{
			public abstract void onDismiss(android.widget.PopupMenu p1);
		}
		public static abstract interface OnMenuItemClickListener{
			public abstract boolean onMenuItemClick(android.view.View p1);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	//public android.view.View.OnTouchListener getDragToOpenListener() {}

	//public android.view.Menu getMenu() {}

	//public android.view.MenuInflater getMenuInflater() {}
		
		
		
		/*
	CustomPopupMenu popup = new CustomPopupMenu(getApplicationContext(), view);
	popup.setOnMenuItemClickListener(new CustomPopupMenu.OnMenuItemClickListener(){ 
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		if(item.getItemId() == R.id.item){
			ArrayList<View> viewsList = new ArrayList<View>();
			viewsList.add(songIm);
			viewsList.add(tracks);
			viewsList.add(relGen);
			int Colors[] = {invColor, color};
			new takeScreenshot().takeShot(MainActivity.this, viewsList, NOW_PLAYING.getSongTitle(), Colors );
		}
		if(item.getItemId() == R.id.item2){   

		}
		return false;
	}
	});
	popup.inflate(R.menu.main_menu);
	popup.setGravity(Gravity.CENTER);
	popup.show();
	*/
		
	
	
	
	
	
	
	
}
























