package com.aydavid.mpmusic;
import android.view.*;
import java.util.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.widget.*;
import android.os.*;
import android.content.*;
import android.util.*;
import android.view.inputmethod.*;
import android.text.*;
import android.view.View.*;

public class MainUi
{
	Context _context;
	Window _window;
	private int UI_LAYOUT_TYPE_INDEX = 1;
	ListView tracksLv;
	LinearLayout tracks, listViewContain, relTracks, topBar, timerBar, lyricsViewContainer;
	TextView songTitle, songArtist, timer, lyricsView;
	ImageView divide, longl, songIm, backBut, nextBut, searchBut, shuffleBut;
	RelativeLayout relGen; 
	SearchView searchV;
	EditText searchV_editT;
	MainActivity AppInstance;
	
	boolean IsListOpened = false;
	boolean IsSearchViewOpened = false;
	
	
	
	
	public MainUi(Context context, MainActivity Instance, Window window){
		AppInstance = Instance;
		_context = context;
		_window = window;
		SetUiLayoutType(1);
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
	
	
	public void dropList(){ 
    	DisplayMetrics metrics = new DisplayMetrics();
		_window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//toaster(""+metrics.heightPixels+" "+" "+metrics.densityDpi);
		/*
		 android.view.ViewGroup.LayoutParams layoutParams= tracksLv.getLayoutParams();
		 //layoutParams.height = (450*(int) tracksLv.getContext().getResources().getDisplayMetrics().density);
		 layoutParams.height = (int) (metrics.heightPixels/1.3);
		 tracksLv.setLayoutParams(layoutParams);           */

		android.view.ViewGroup.LayoutParams layoutParamsLinear= listViewContain.getLayoutParams();
	    //layoutParams.height = (450*(int) tracksLv.getContext().getResources().getDisplayMetrics().density);
		layoutParamsLinear.height = (int) (metrics.heightPixels/1.3);
		listViewContain.setLayoutParams(layoutParamsLinear);
	}
	public void closeList(){
		/*
		 android.view.ViewGroup.LayoutParams layoutParams= tracksLv.getLayoutParams();
		 layoutParams.height = 0;
		 tracksLv.setLayoutParams(layoutParams);       */
		android.view.ViewGroup.LayoutParams layoutParamsLinear= listViewContain.getLayoutParams();
	    //layoutParams.height = (450*(int) tracksLv.getContext().getResources().getDisplayMetrics().density);
		layoutParamsLinear.height = 0;
		listViewContain.setLayoutParams(layoutParamsLinear);
	}


	//controls
	public void SearchButtonClicked(){
		if(IsSearchViewOpened){ closeSV(); IsSearchViewOpened = false;}
		else{ openSV(); IsSearchViewOpened = true;}
	}
	public void openSV(){
	 	ViewGroup searchVParent = ( (ViewGroup) searchV_editT.getParent() );
	 	for(int childIndex=0; searchVParent.getChildCount()>childIndex; childIndex++){
	 		searchVParent.getChildAt(childIndex).setVisibility(View.GONE);
	 	}
	 	searchBut.setVisibility(View.VISIBLE);
	 	searchV_editT.setVisibility(View.VISIBLE);
	 	searchV_editT.setBackgroundColor(Uiprops.primaryColor);
	 	searchV_editT.setTextColor(Uiprops.secondaryColor);
	 	searchV_editT.requestFocus(); 
	 	InputMethodManager iMM = ((InputMethodManager)AppInstance.getSystemService(AppInstance.INPUT_METHOD_SERVICE));
	 	iMM.showSoftInput(searchV_editT, iMM.SHOW_FORCED);
	 	dropList();	
	}
	public void closeSV(){
		ViewGroup searchVParent = ( (ViewGroup) searchV_editT.getParent() );
		for(int childIndex=0; searchVParent.getChildCount()>childIndex; childIndex++){
	 		if( searchVParent.getChildAt(childIndex) != searchV )searchVParent.getChildAt(childIndex).setVisibility(View.VISIBLE);
		}
		searchV_editT.setVisibility(View.GONE);
	    searchV_editT.setText("");
		((InputMethodManager)AppInstance.getSystemService(AppInstance.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((AppInstance.getWindow().getDecorView().getApplicationWindowToken()), 0);
		searchV_editT.clearFocus();
	}
	
	
	
	
	
	
	
	
	
	public void SetUiLayoutType(int TypeIndex){
		UI_LAYOUT_TYPE_INDEX = TypeIndex;
	}
	public void SetContentView(){
		switch (UI_LAYOUT_TYPE_INDEX){
			case 1:
				_window.setContentView(R.layout.main);
			break;
			default:
				_window.setContentView(R.layout.main);
		}
	}//EO setcontentview.
	public void SetListeners(){
		tracksLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
					//this is the way to find selected object/item
					pos = pos-1;
					if(pos>= 0){
						MainActivity.playIndex = pos;
						song item = (song) adapterView.getItemAtPosition(pos);
						pos = item.getSongInd();   
						item =  Playerprops.SONG_LIST_ALL.get( pos );  //new type //get from first song list
						//playSong(item);
						AppInstance.PLAY_SONG(item);
					}//EO if
				}
			});

		tracksLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id){
					final int popItemPos = ( (song) adapterView.getItemAtPosition(pos-1) ).getSongInd();
					PopupMenu popup = new PopupMenu(adapterView.getContext(),view);
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){ 
							@Override
							public boolean onMenuItemClick(MenuItem item) {

								if(item.getItemId() == R.id.item){ if(popItemPos>=0) AppInstance.addToQueue(popItemPos); }
								if(item.getItemId() == R.id.item2){   if(popItemPos>=0){ new playlistDialog().playlistDialog(AppInstance, popItemPos, Playerprops.SONG_LIST_CURRENT, Uiprops.primaryColor_Default ); }   }
								return false;
							}
						});
					popup.inflate(R.menu.main_menu);
					popup.setGravity(Gravity.CENTER);
					popup.show();
					return true;
				}  });


		tracks.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if(IsListOpened){closeList(); IsListOpened = false;}else{dropList(); IsListOpened=true;}
				}});

		relTracks.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

				}}); 

		songIm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					AppInstance.songImClicked();
				}}); 
		songIm.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					try{
						final CustomPopupMenu popup = new CustomPopupMenu(_context.getApplicationContext(), relGen);
						popup.inflate(R.layout.imageview_popmenu);
						for(View item : popup.items){
							item.setOnClickListener( new View.OnClickListener(){
									@Override
									public void onClick(View v){
										popup.dismiss();
										if(v.getId() == R.id.but_takeShot){ 
											ArrayList<View> viewsList = new ArrayList<View>();
											viewsList.add(songIm);
											viewsList.add(tracks);
											viewsList.add(relGen);
											int Colors[] = {Uiprops.secondaryColor, Uiprops.primaryColor};
											new takeScreenshot().takeShot(AppInstance, viewsList, Playerprops.NOW_PLAYING.getSongTitle(), Colors );
										}
										if(v.getId() == R.id.but_showLyrics){   
											lyricsViewContainer.setVisibility( lyricsViewContainer.getVisibility() == View.VISIBLE?View.GONE:View.VISIBLE);
										}
									}//EO onClick
								});
						}//EO for
						popup.setGravity(Gravity.CENTER);
						popup.show();
					}catch(Exception e){AppInstance.toaster(e+"");}

					return true;
				}}); 

		searchBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					AppInstance.cA.filtering(searchV_editT.getText().toString(), Playerprops.SONG_LIST_ALL);
					SearchButtonClicked();
				}});


		shuffleBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent eventIntent = new Intent("SHUFFLE_LIST");
					AppInstance.sendBroadcast(eventIntent);
				}});

		nextBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
				    AppInstance.goToNext();
				}});

		backBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
					AppInstance.goToBack();  
				}});


		longl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){

				}});

		topBar.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v,MotionEvent e){
					float eX = e.getX(); float eY = e.getY();
					int position = (int)eX* (int) (AppInstance.mp.getDuration()/topBar.getWidth());
					AppInstance.seekTo(position);
					return false;
				}});

		searchV_editT.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(final CharSequence s, final int start, final int count, final
											  int after) {
				}
				@Override
				public void onTextChanged(final CharSequence s, final int start, final int before, final
										  int count) {
					//imitating submit
					if(s.toString().contains("[\\n||\\r]+")){   AppInstance.toaster("entered");
						AppInstance.cA.filtering(s.toString(), Playerprops.SONG_LIST_ALL);
						SearchButtonClicked();
					}
				}
				@Override
				public void afterTextChanged(final Editable s) {

				}

			}); //EO addTextChangedListener
		searchV_editT.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent keyEvent){
					if(keyEvent.getAction() == KeyEvent.ACTION_DOWN   || 
					   keyEvent.getAction() == KeyEvent.KEYCODE_ENTER ){   
						//toaster("entered");
						AppInstance.cA.filtering(searchV_editT.getText().toString(), Playerprops.SONG_LIST_CURRENT);
						SearchButtonClicked();
						return true;
					}
					return false;
				}
		});
		
		
		
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}