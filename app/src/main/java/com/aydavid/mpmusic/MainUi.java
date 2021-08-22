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
	com.aydavid.mpmusic.MainActivity AppInstance;
	
	boolean IsListOpened = false;
	boolean IsSearchViewOpened = false;
	
	
	
	
	public MainUi(Context context, MainActivity Instance, Window window){
		AppInstance = Instance;
		_context = context;
		_window = window;
	}
	public int[] getBgColor(Bitmap bmp){
		int r, g, b;
		int i = bmp.getWidth()-1; 
		int j = bmp.getHeight()-1;
		int p = bmp.getPixel(i, j);
		if(p ==0 && !AppInstance.NOW_PLAYING_HAS_NO_BITMAP){  
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
			int R = 255-r; int G = 255-g; int B = 255-b;
			//AppInstance.toaster(r+";"+g+";"+b);
			//closeness to 127.5 correcting
			//if(r>95&&r<135 && g>95&&g<135 && b>95&&b<135){  R = 255-r*2; G= 255-g*2; B=255-b*2;}
			//individual closeness to 127.5 correcting
			R = ((r>100&&r<130)?255-r*2:R);   G = ((g>100&&g<130)?255-g*2:G);		B = ((b>100&&b<130)?255-b*2:B);
			
			//auto closeness to 127.5 correcting
			//R = 255-r*(1+(127-r/2)/127); G = 255-g*(1+(255-g)/127);   B = 255-b*(1+(127-b/2)/127);
			Uiprops.primaryColor = Color.argb(255, R, G, B);
		}
		int colors [] = {Uiprops.primaryColor, Uiprops.secondaryColor};
		return colors;
	}



	public void tintAll(){
		ColorDrawable[] transColor ={new ColorDrawable(Uiprops.primaryColor), null};
	    Uiprops.primaryColor =0; Uiprops.secondaryColor =0;
		BitmapDrawable abmp = (BitmapDrawable)songIm.getDrawable();
		int[] bgColors = getBgColor(abmp.getBitmap());
		Uiprops.primaryColor =bgColors[0];
		Uiprops.secondaryColor =bgColors[1];
		
		
		transColor[1] = new ColorDrawable(Uiprops.secondaryColor); //set after change
		TransitionDrawable trans = new TransitionDrawable(transColor);

		longl.setColorFilter(Uiprops.secondaryColor);
		relTracks.setBackgroundColor(Uiprops.secondaryColor);
		topBar.setBackgroundColor(Uiprops.secondaryColor);
		relGen.setBackgroundColor(Uiprops.secondaryColor);
		timerBar.setBackgroundColor(Uiprops.primaryColor);
		divide.setColorFilter(Uiprops.primaryColor);
		timer.setTextColor(Uiprops.primaryColor);
		timer.getBackground().setColorFilter(Uiprops.primaryColor, PorterDuff.Mode.SRC_ATOP);
		timer.getBackground().setAlpha(255/2);
		if(UI_LAYOUT_TYPE_INDEX == 2){ /*we need sleeker soluyion*/
			((ViewGroup)backBut.getParent()).getBackground().setColorFilter(Uiprops.primaryColor, PorterDuff.Mode.SRC_ATOP);
			((ViewGroup)backBut.getParent()).getBackground().setAlpha((int)(255/1.5));
		}
		songTitle.setTextColor(Uiprops.primaryColor);
		songArtist.setTextColor(Uiprops.primaryColor);
		backBut.setColorFilter(Uiprops.primaryColor);
		nextBut.setColorFilter(Uiprops.primaryColor);
	    searchBut.setColorFilter(Uiprops.primaryColor);
		shuffleBut.setColorFilter(Uiprops.primaryColor); 
		tracksLv.setBackgroundColor(Uiprops.secondaryColor);
		searchV.setBackgroundColor(Uiprops.secondaryColor);
		searchV_editT.setBackgroundColor(Uiprops.primaryColor);
		searchV_editT.setTextColor(Uiprops.secondaryColor);
		lyricsViewContainer.setBackgroundColor(Uiprops.secondaryColor);
		lyricsView.setTextColor(Uiprops.primaryColor);

		trans.startTransition(2000);

	}
    public void tintOnCreate(){

		longl.setColorFilter(Uiprops.secondaryColor);
		relTracks.setBackgroundColor(Uiprops.secondaryColor);
		topBar.setBackgroundColor(Uiprops.secondaryColor);
		timerBar.setBackgroundColor(Uiprops.secondaryColor);
		relGen.setBackgroundColor(Uiprops.secondaryColor);
		divide.setColorFilter(Uiprops.primaryColor);
		timer.setTextColor(Uiprops.primaryColor);
		songTitle.setTextColor(Uiprops.primaryColor);
		songArtist.setTextColor(Uiprops.primaryColor);
		backBut.setColorFilter(Uiprops.primaryColor);
		nextBut.setColorFilter(Uiprops.primaryColor);
	    searchBut.setColorFilter(Uiprops.primaryColor);
		shuffleBut.setColorFilter(Uiprops.primaryColor);
		tracksLv.setBackgroundColor(Uiprops.secondaryColor);
		searchV.setBackgroundColor(Uiprops.secondaryColor);	
		searchV_editT.setBackgroundColor(Uiprops.primaryColor);
		searchV_editT.setTextColor(Uiprops.secondaryColor);
	}

	
    public void uiColorAndResChanger(int i){	
		songIm.setImageResource(Uiprops.tintRes[i]);
		Uiprops.secondaryColor_Default = Color.parseColor(Uiprops.tint1[i]);
		Uiprops.primaryColor_Default = Color.parseColor(Uiprops.tint2[i]);
		Uiprops.primaryColor = Uiprops.primaryColor_Default;
        Uiprops.secondaryColor = Uiprops.secondaryColor_Default;
		tintOnCreate();
		AppInstance.cA.changeColor(Uiprops.primaryColor, Uiprops.secondaryColor, Uiprops.tintRes[i]);
		changeTypeFace();
		tracksLv.setAlpha(0.9f); 
		if(Build.VERSION.SDK_INT >= 21){
			_window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			_window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			_window.setStatusBarColor(Uiprops.secondaryColor);
		}		
	}


	public void changeUi(){
		android.view.ViewGroup.LayoutParams layoutParams= tracks.getLayoutParams();
	    layoutParams.width = (relTracks.getLayoutParams().width/10*(int) tracks.getContext().getResources().getDisplayMetrics().density);
		tracks.setLayoutParams(layoutParams);
		//Toast.makeText(MainActivity,  relTracks.getLayoutParams()+ " ", 2000).show();
	}

	public void uIChangeOnPlay(song item){
		tintAll();
		songTitle.setText(item.getSongTitle());
		songArtist.setText(item.getSongArtist());
		AppInstance.cA.changeColor(Uiprops.primaryColor, Uiprops.secondaryColor); 
		if(Build.VERSION.SDK_INT >= 21){  //change status on pla
			_window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			_window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			_window.setStatusBarColor(Uiprops.secondaryColor);
		}
	}

	public void changeTypeFace(){
	    Typeface font = Typeface.createFromAsset(_context.getAssets(), "font/nb.ttf");
		songTitle.setTypeface(font);
		songArtist.setTypeface(font);
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
	public void moveTimerBar(){		
		AppInstance.playPosition = AppInstance.mp.getCurrentPosition();
		AppInstance.playDuration = AppInstance.mp.getDuration(); 
		
		android.view.ViewGroup.LayoutParams timerBarLP= timerBar.getLayoutParams(); 
		timerBarLP.width = ((longl.getWidth()*AppInstance.playPosition)/AppInstance.playDuration);
		timerBar.setLayoutParams(timerBarLP);
		// Toast.makeText(this, longl.getWidth()+ " ", 2000).show();
		int mpPos = AppInstance.playPosition;
		int mpDur = AppInstance.playDuration;
		double doubPos =   (double)mpPos/(1000.0);	 
		double doubDur = (double)mpDur/(1000.0);
		int doubIPos = (int) (doubPos);		
		int doubIDur = (int) (doubDur);
		int mIntPos = ( (int)(mpPos/(1000*60))*60 );		
		int mIntDur = ( (int)(mpDur/(1000*60))*60 );
		String playPositionString = (int) (mpPos/(1000*60)  )+": "+ (doubPos-mIntPos <10?"0":"") +(doubIPos-mIntPos);
		String playDurationString = (int) (mpDur/(1000*60)  )+": "+ (doubDur-mIntDur <10?"0":"") +(doubIDur-mIntDur);
		timer.setText( " "+" "+" "+playDurationString + " _ " +playPositionString+" "+" "+" " );
		timer.setTextSize(12);
	}

	
	
	
	
	
	
	
	
	
	public void SetUiLayoutType(int TypeIndex){
		UI_LAYOUT_TYPE_INDEX = TypeIndex;
	}
	public void SetContentView(){
		switch (UI_LAYOUT_TYPE_INDEX){
			case 1:
				_window.setContentView(R.layout.main_style_1);
			case 2:
				_window.setContentView(R.layout.main_style_2);
			break;
			default:
				_window.setContentView(R.layout.main_style_1);
		}
	}//EO setcontentview.
	public void InitialiseViews(){
		tracksLv = (ListView) AppInstance.findViewById(R.id.tracksLv);
		tracks = (LinearLayout) AppInstance.findViewById(R.id.tracks);
		topBar = (LinearLayout) AppInstance.findViewById(R.id.topBar);
		timerBar = (LinearLayout) AppInstance.findViewById(R.id.timerBar);

		divide = (ImageView) AppInstance.findViewById(R.id.divide);
		longl = (ImageView) AppInstance.findViewById(R.id.longl);
		songIm = (ImageView) AppInstance.findViewById(R.id.songIm);
		relGen = (RelativeLayout) AppInstance.findViewById(R.id.relG);
		relTracks = (LinearLayout) AppInstance.findViewById(R.id.relTracks);
		songArtist = (TextView) AppInstance.findViewById(R.id.songArtist);
		songTitle = (TextView) AppInstance.findViewById(R.id.songTitle);
		backBut = (ImageView) AppInstance.findViewById(R.id.backBut);
		nextBut = (ImageView) AppInstance.findViewById(R.id.nextBut);
	    searchBut = (ImageView) AppInstance.findViewById(R.id.searchBut);
		shuffleBut = (ImageView) AppInstance.findViewById(R.id.shuffleBut);
        searchV  = (SearchView) AppInstance.findViewById(R.id.searchV);
		searchV_editT = (EditText) AppInstance.findViewById(R.id.searchV_editT);

		listViewContain = (LinearLayout) AppInstance.findViewById(R.id.listViewContain);
		lyricsViewContainer = (LinearLayout) AppInstance.findViewById(R.id.lyricsViewContainer);
		lyricsView = (TextView) AppInstance.findViewById(R.id.lyricsView);
		timer = (TextView) AppInstance.findViewById(R.id.timer);
	}
	public void SetListeners(){
		tracksLv.setAdapter(AppInstance.cA);
		tracksLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
					//this is the way to find selected object/item
					pos = pos-1;
					if(pos>= 0){
						AppInstance.playIndex = pos;
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
											new com.aydavid.mpmusic.LyricsMC.LyricsLoader(AppInstance).DisplayLyrics((TextView) AppInstance.findViewById(R.id.lyricsView), Playerprops.NOW_PLAYING);
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