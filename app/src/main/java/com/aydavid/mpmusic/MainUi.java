package com.aydavid.mpmusic;
import android.view.*;
import java.util.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.widget.*;
import android.os.*;
import android.content.*;

public class MainUi
{
	Context _context;
	Window _window;
	public MainUi(Context context, Window window){
		_context = context;
		_window = window;
	}
	public int[] getBgColor(Bitmap bmp){
		int r, g, b;
		int i = bmp.getWidth()-1; 
		int j = bmp.getHeight()-1;
		int p = bmp.getPixel(i, j);
		if(p ==0 && !MainActivity.NOW_PLAYING_HAS_NO_BITMAP){  
			p = bmp.getPixel(i, j/2);
			if(p ==0 ){     p = bmp.getPixel(i/2, j/2);      }
		}
		if(p == 0){ //
			Uiprops.primaryColor = Uiprops.primaryColor_Default;
			Uiprops.secondaryColor = Uiprops.secondaryColor_Default;
		}
		if(p != 0){
			r = Color.red(p);
			g = Color.green(p);
	    	b = Color.blue(p);
			Uiprops.secondaryColor =Color.argb(255, r, g, b);
			Uiprops.primaryColor =Color.argb(255, 255-r, 255-g, 255-b);
			//toaster(r+";"+g+";"+b);
			if(r>95&&r<135 && g>95&&g<135 && b>95&b<135){  
				//make sire they are not a 111
			    Uiprops.primaryColor =Color.argb(255, 255-r*2, 255-g*2, 255-b*2);
			}
		}
		int colors [] = {Uiprops.primaryColor, Uiprops.secondaryColor};
		return colors;
	}



	public void tintAll(){
		ColorDrawable[] transColor ={new ColorDrawable(Uiprops.primaryColor), null};
	    Uiprops.primaryColor =0; Uiprops.secondaryColor =0;
		BitmapDrawable abmp = (BitmapDrawable)MainActivity.songIm.getDrawable();
		int[] bgColors = getBgColor(abmp.getBitmap());
		Uiprops.primaryColor =bgColors[0];
		Uiprops.secondaryColor =bgColors[1];

		transColor[1] = new ColorDrawable(Uiprops.secondaryColor); //set after change
		TransitionDrawable trans = new TransitionDrawable(transColor);

		MainActivity.longl.setColorFilter(Uiprops.secondaryColor);
		MainActivity.relTracks.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.topBar.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.timerBar.setBackgroundColor(Uiprops.primaryColor);
		MainActivity.relGen.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.divide.setColorFilter(Uiprops.primaryColor);
		MainActivity.timer.setTextColor(Uiprops.primaryColor);
		MainActivity.timer.getBackground().setColorFilter(Uiprops.primaryColor, PorterDuff.Mode.SRC_ATOP);
		MainActivity.timer.getBackground().setAlpha(255/2);
		MainActivity.songTitle.setTextColor(Uiprops.primaryColor);
		MainActivity.songArtist.setTextColor(Uiprops.primaryColor);
		MainActivity.backBut.setColorFilter(Uiprops.primaryColor);
		MainActivity.nextBut.setColorFilter(Uiprops.primaryColor);
	    MainActivity.searchBut.setColorFilter(Uiprops.primaryColor);
		MainActivity.shuffleBut.setColorFilter(Uiprops.primaryColor); 
		MainActivity.tracksLv.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.searchV.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.searchV_editT.setBackgroundColor(Uiprops.primaryColor);
		MainActivity.searchV_editT.setTextColor(Uiprops.secondaryColor);
		MainActivity.lyricsViewContainer.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.lyricsView.setTextColor(Uiprops.primaryColor);

		trans.startTransition(2000);

	}
    public void tintOnCreate(){

		MainActivity.longl.setColorFilter(Uiprops.secondaryColor);
		MainActivity.relTracks.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.topBar.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.timerBar.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.relGen.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.divide.setColorFilter(Uiprops.primaryColor);
		MainActivity.timer.setTextColor(Uiprops.primaryColor);
		MainActivity.songTitle.setTextColor(Uiprops.primaryColor);
		MainActivity.songArtist.setTextColor(Uiprops.primaryColor);
		MainActivity.backBut.setColorFilter(Uiprops.primaryColor);
		MainActivity.nextBut.setColorFilter(Uiprops.primaryColor);
	    MainActivity.searchBut.setColorFilter(Uiprops.primaryColor);
		MainActivity.shuffleBut.setColorFilter(Uiprops.primaryColor);
		MainActivity.tracksLv.setBackgroundColor(Uiprops.secondaryColor);
		MainActivity.searchV.setBackgroundColor(Uiprops.secondaryColor);	
		MainActivity.searchV_editT.setBackgroundColor(Uiprops.primaryColor);
		MainActivity.searchV_editT.setTextColor(Uiprops.secondaryColor);
	}

	
    public void uiColorAndResChanger(int i){	
		MainActivity.songIm.setImageResource(Uiprops.tintRes[i]);
		Uiprops.secondaryColor_Default = Color.parseColor(Uiprops.tint1[i]);
		Uiprops.primaryColor_Default = Color.parseColor(Uiprops.tint2[i]);
		Uiprops.primaryColor = Uiprops.primaryColor_Default;
        Uiprops.secondaryColor = Uiprops.secondaryColor_Default;
		tintOnCreate();
		MainActivity.cA.changeColor(Uiprops.primaryColor, Uiprops.secondaryColor, Uiprops.tintRes[i]);
		changeTypeFace();
		MainActivity.tracksLv.setAlpha(0.9f); 
		if(Build.VERSION.SDK_INT >= 21){
			_window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			_window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			_window.setStatusBarColor(Uiprops.secondaryColor);
		}		
	}


	public void changeUi(){
		android.view.ViewGroup.LayoutParams layoutParams= MainActivity.tracks.getLayoutParams();
	    layoutParams.width = (MainActivity.relTracks.getLayoutParams().width/10*(int) MainActivity.tracks.getContext().getResources().getDisplayMetrics().density);
		MainActivity.tracks.setLayoutParams(layoutParams);
		//Toast.makeText(MainActivity,  MainActivity.relTracks.getLayoutParams()+ " ", 2000).show();
	}


	public void uIChangeOnPlay(song item){
		tintAll();
		MainActivity.songTitle.setText(item.getSongTitle());
		MainActivity.songArtist.setText(item.getSongArtist());
		MainActivity.cA.changeColor(Uiprops.primaryColor, Uiprops.secondaryColor); 
		if(Build.VERSION.SDK_INT >= 21){  //change status on pla
			_window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			_window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			_window.setStatusBarColor(Uiprops.secondaryColor);
		}
	}

	public void changeTypeFace(){
	    Typeface font = Typeface.createFromAsset(_context.getAssets(), "font/nb.ttf");
		MainActivity.songTitle.setTypeface(font);
		MainActivity.songArtist.setTypeface(font);
	}
	
	
	
	
	
	
}