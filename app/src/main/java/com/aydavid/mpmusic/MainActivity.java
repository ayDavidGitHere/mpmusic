package com.aydavid.mpmusic;


import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.view.ViewGroup.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.hardware.input.InputManager;
import android.inputmethodservice.*;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import java.util.ArrayList;
import android.media.*;
import java.io.*;
import android.view.inputmethod.*;
import android.*;
import java.util.*;
import android.util.*;
import android.content.*;
import android.graphics.drawable.shapes.*;
import java.util.concurrent.atomic.*;
import android.transition.*;
import java.lang.reflect.*;
import android.text.format.*;
import java.text.*;
import android.icu.text.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.text.*;



public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		getActionBar().hide();
		app_context = getApplicationContext();
		
		tracksLv = (ListView) findViewById(R.id.tracksLv);
		tracks = (LinearLayout) findViewById(R.id.tracks);
		topBar = (LinearLayout) findViewById(R.id.topBar);
		timerBar = (LinearLayout) findViewById(R.id.timerBar);
		
		divide = (ImageView) findViewById(R.id.divide);
		longl = (ImageView) findViewById(R.id.longl);
		songIm = (ImageView) findViewById(R.id.songIm);
		relGen = (RelativeLayout) findViewById(R.id.relG);
		relTracks = (LinearLayout) findViewById(R.id.relTracks);
		songArtist = (TextView) findViewById(R.id.songArtist);
		songTitle = (TextView) findViewById(R.id.songTitle);
		backBut = (ImageView) findViewById(R.id.backBut);
		nextBut = (ImageView) findViewById(R.id.nextBut);
	    searchBut = (ImageView) findViewById(R.id.searchBut);
		shuffleBut = (ImageView) findViewById(R.id.shuffleBut);
        searchV  = (SearchView) findViewById(R.id.searchV);
		searchV_editT = (EditText) findViewById(R.id.searchV_editT);
		
		listViewContain = (LinearLayout) findViewById(R.id.listViewContain);
		lyricsViewContainer = (LinearLayout) findViewById(R.id.lyricsViewContainer);
		lyricsView = (TextView) findViewById(R.id.lyricsView);
		timer = (TextView) findViewById(R.id.timer);
		
		//loaddatafromsplasher
		Bundle bundleFromSplasher = getIntent().getExtras();
		if (bundleFromSplasher != null) {
		    declareRand = bundleFromSplasher.getInt("declareRand");
		}
		else{
			toaster("could not load from splasher");
			songList();
		}
		
		
		
		cA = new oCA(this, MainActivity.this, Playerprops.SONG_LIST_CURRENT);
		tracksLv.setAdapter(cA);
		tracksLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
					//this is the way to find selected object/item
					pos = pos-1;
							if(pos>= 0){
					playIndex = pos;
				    item = (song) adapterView.getItemAtPosition(pos);
					pos = item.getSongInd();   
					item =  Playerprops.SONG_LIST_ALL.get( pos );  //new type //get from first song list
					//playSong(item);
					PLAY_SONG(item);
					       }//EO if
				}
			});
		
		tracksLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id){
				popItemPos = ( (song) adapterView.getItemAtPosition(pos-1) ).getSongInd();
				PopupMenu popup = new PopupMenu(adapterView.getContext(),view);
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){ 
					@Override
				    public boolean onMenuItemClick(MenuItem item) {
						
						if(item.getItemId() == R.id.item){ if(popItemPos>=0) addToQueue(popItemPos); }
						if(item.getItemId() == R.id.item2){   if(popItemPos>=0){ new playlistDialog().playlistDialog(MainActivity.this, popItemPos, Playerprops.SONG_LIST_CURRENT, Uiprops.primaryColor_Default ); }   }
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
					tClicked = tClicked+1;
					if(tClicked %2 == 0) {   dropList(); }
					else {closeList(); }
					}});
			
		relTracks.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
			   
				}}); 
					
		songIm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					songImClicked();
				}}); 
		songIm.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
				try{
				final CustomPopupMenu popup = new CustomPopupMenu(getApplicationContext(), relGen);
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
							new takeScreenshot().takeShot(MainActivity.this, viewsList, NOW_PLAYING.getSongTitle(), Colors );
						}
						if(v.getId() == R.id.but_showLyrics){   
							lyricsViewContainer.setVisibility( lyricsViewContainer.getVisibility() == View.VISIBLE?View.GONE:View.VISIBLE);
						}
					}//EO onClick
					});
				}//EO for
				popup.setGravity(Gravity.CENTER);
				popup.show();
				}catch(Exception e){toaster(e+"");}
					
				return true;
				}}); 
				
		searchBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					cA.filtering(searchV_editT.getText().toString(), Playerprops.SONG_LIST_ALL);
					sVClicked = sVClicked+1;
					sVClicked();
				}});

				
		shuffleBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent eventIntent = new Intent("SHUFFLE_LIST");
					MainActivity.this.sendBroadcast(eventIntent);
				}});
		
		nextBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
				    goToNext();
				}});
		
		backBut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
		             goToBack();  
				}});
	
	
		longl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
   
				}});
	
		topBar.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v,MotionEvent e){
					float eX = e.getX(); float eY = e.getY();
					int position = (int)eX* (int) (mp.getDuration()/topBar.getWidth());
					seekTo(position);
					return false;
				}});
				
				
					
	    //init_ui
        startRun();   //changeUi();
		//if not declared by intent bundle redeclarw
		if(bundleFromSplasher == null){  declareRand = new Random().nextInt(4); }
		mainUi = new MainUi(this, getWindow());
		mainUi.uiColorAndResChanger(declareRand);
		
		
		
		 
		 
		
		     
		if(playservice.isRunning == true){
		toaster("Service is running");
		Bundle bundle = new Bundle();
		Intent eventIntent = new Intent("INIT");
		eventIntent.putExtras(bundle);
		this.sendBroadcast(eventIntent);
        }
		
		mp = new MediaPlayer();
		mp = playservice.mpServe;

		
		searchV_editT.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count, final
											  int after) {
			}
			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before, final
										  int count) {
         		//imitating submit
		 		if(s.toString().contains("[\\n||\\r]+")){   toaster("entered");
         			cA.filtering(s.toString(), Playerprops.SONG_LIST_ALL);
					sVClicked++; 
					sVClicked();
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
						cA.filtering(searchV_editT.getText().toString(), Playerprops.SONG_LIST_CURRENT);
						sVClicked++; 
						sVClicked();
						return true;
					}
					return false;
				}
		});
		
		
		final Thread.UncaughtExceptionHandler defaultHandler =
			Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable e) {
					try {
						handleUncaughtException(e);
						System.exit(1); 
					} catch (Throwable e2) {  
						defaultHandler.uncaughtException(thread, e);
					}
				}
		}); //EO handler

		
		
		
		
		
		
	
 }//EO oncreate
	
	/** static reference to view is bad **
		but we are  using one activity
	**/
	static ListView tracksLv;
	static LinearLayout tracks, listViewContain, relTracks, topBar, timerBar, lyricsViewContainer;
	static TextView songTitle, songArtist, timer, lyricsView;
	static ImageView divide, longl, songIm, backBut, nextBut, searchBut, shuffleBut;
	static RelativeLayout relGen; 
	static SearchView searchV;
	static EditText searchV_editT;
    int tClicked = 1;
	int marg = 0;
	//CustomAdapter cA;  
	static oCA cA;
	song item;
	static int playIndex = 0;
	int popItemPos = 0;
	int declareRand = 0;
	static Boolean NOW_PLAYING_HAS_NO_BITMAP = true;
	static Context app_context;
	MainUi mainUi;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void toaster(String string){
		Toast.makeText(this, string, 3000).show();
	}
	public void toaster_onUI(final String string){
		new Thread(new Runnable() {	@Override  public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							toaster(string);
						}	
					});
		}});
	}


	public void dropList(){
    	DisplayMetrics metrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
	
	
	
	
		Handler handler = new Handler();
		Runnable runnable = new Runnable() {
				@Override
				public void run() {
				  if(NOW_PLAYING != null){
					moveTimerBar();
					if(NOW_PLAYING != null && playPosition > playDuration/2){
					  if(LAST_LOGGED == null || LAST_LOGGED.getSongData() != NOW_PLAYING.getSongData() ){
					    try{	  
							new Thread(new Runnable() { 
							@Override
								public void run(){
									LAST_LOGGED = NOW_PLAYING;
									new logMostPlayed().logMostPlayed(MainActivity.this, NOW_PLAYING.getSongData());
						        }
						    }).start();
						}
						catch(Exception e){ 
							toaster("LogMostPlayedException: "+e); 
							}
					  }//EO if
				    }//EO if
				   }//EO if
				new Handler().postDelayed(this, 10);
				}//EO run
		};//EO runnable
	  
	public void moveTimerBar(){		
		  playPosition = mp.getCurrentPosition();
		  playDuration = mp.getDuration();
		  
		  LayoutParams timerBarLP= timerBar.getLayoutParams();
		  timerBarLP.width = ((longl.getWidth()*playPosition)/playDuration);
		  timerBar.setLayoutParams(timerBarLP);
		  // Toast.makeText(this, longl.getWidth()+ " ", 2000).show();
	      int mpPos = playPosition;
		  int mpDur = playDuration;
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
		
	
	public void startRun(){
		handler.postDelayed(runnable, 10);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	boolean PLAYING = false;
	song LAST_LOGGED;
	song NOW_PLAYING;
	MediaPlayer mp;
	
	
	//playbacks
	public void songImClicked(){ 
		if(PLAYING){ songPaused(); }
		if(!PLAYING){ songResume(); }
	}
	public void songPaused(){ 
		Intent eventIntent = new Intent("PAUSE_SONG");
		this.sendBroadcast(eventIntent);
	}
	public void songResume(){
		Intent eventIntent = new Intent("RESUME_SONG");
		this.sendBroadcast(eventIntent);
	}
	public void setSongAlpha(boolean PLAYING){ 
	    this.PLAYING = PLAYING;
		if(PLAYING){  songIm.setAlpha(0.95f); }
		if(!PLAYING){ songIm.setAlpha(0.35f); }
	}
	

	
	//   CONTRO,,LS 
	public void PLAY_SONG(final song item){
		if(playservice.isRunning == true){
			Bundle bundle = new Bundle();
		    bundle.putSerializable("song_item", item);
			Intent eventIntent = new Intent("PLAY_ITEM");
			eventIntent.putExtra("playIndex", playIndex);
			eventIntent.putExtras(bundle);
			this.sendBroadcast(eventIntent);
		}
	}
	public void seekTo(int position){
		mp.seekTo(position);	
	}
	public void goToNext(){
		if(playservice.isRunning == true){
			Intent eventIntent = new Intent("LIST_TONEXT");
			this.sendBroadcast(eventIntent);
		}
	}
	public void goToBack(){
		if(playservice.isRunning == true){
			Intent eventIntent = new Intent("LIST_TOBACK");
			this.sendBroadcast(eventIntent);
		}
	}

	public void addToQueue(int songPos){
		if(playservice.isRunning == true){
			Intent eventIntent = new Intent("QUEUE_ADD");
			eventIntent.putExtra("songPos", songPos);
			this.sendBroadcast(eventIntent);
		}
	}












	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	int songImC = 1; 
	int sVClicked = 1;
	static int shClicked = 1;
	int playPosition = 0;
	int playDuration = 10000;
	String songCol = "#FF5900";
	
	
	//controls
	public void sVClicked(){
		if(sVClicked%2 ==0){ openSV(); }
		else{ closeSV(); }
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
	 	InputMethodManager iMM = ((InputMethodManager)this.getSystemService(this.INPUT_METHOD_SERVICE));
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
		((InputMethodManager)this.getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
		searchV_editT.clearFocus();
	}
	
	
	
	
	
	
	
	
	
	public void changeTypeFace(){
	    Typeface font = Typeface.createFromAsset(getAssets(), "font/nb.ttf");
		songTitle.setTypeface(font);
		songArtist.setTypeface(font);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void songList(){
		
		ArrayList<song> songList;
		songList = new ArrayList<song>();
    int songCount = 0;
	try{
	ContentResolver musicResolver = getContentResolver();
	Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] project = {android.provider.MediaStore.Audio.Media._ID+" "};
		String order = android.provider.MediaStore.Audio.Media.TITLE;
		String order2 = android.provider.MediaStore.Audio.Media.DATE_MODIFIED+" DESC";
	Cursor musicCursor = musicResolver.query(musicUri, null, null, null, order2);
     
	
	if(musicCursor!=null && musicCursor.moveToFirst()){
//get columns
		int titleColumn = musicCursor.getColumnIndex
		(android.provider.MediaStore.Audio.Media.TITLE);
		int idColumn = musicCursor.getColumnIndex
		(android.provider.MediaStore.Audio.Media._ID);
		int artistColumn = musicCursor.getColumnIndex
		(android.provider.MediaStore.Audio.Media.ARTIST);
		int albumColumn = musicCursor.getColumnIndex
		(android.provider.MediaStore.Audio.Media.ALBUM);
		int dataColumn = musicCursor.getColumnIndex
		(android.provider.MediaStore.Audio.Media.DATA);
//add songs to list
		do {
			int thisId = (int) musicCursor.getLong(idColumn);
			String thisTitle = musicCursor.getString(titleColumn);
			String thisArtist = musicCursor.getString(artistColumn);
			String thisAlbum = musicCursor.getString(albumColumn);
			String thisData = musicCursor.getString(dataColumn);
			songList.add(new song(thisData, thisTitle, thisArtist, thisAlbum, songCount));
			
		    songCount = songCount+1;
		}
		while (musicCursor.moveToNext());

		}
		
		
		Playerprops.SONG_LIST_ALL = songList;
		Playerprops.SONG_LIST_CURRENT.addAll(songList);
	}
	catch(Exception e){  Toast.makeText(this, " "+e+" ",200000000).show();
	}
	
	}//EO songList()
	
	
	
	
	
	
	

	
	
	
	
	
	
	public void Notifier(String t1, String t2, String t3, Bitmap bm, int invColor){
                //NotificationManager   notificationMgr = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationMgr.cancel(0);//
			  
	Notification.Builder mBuilder = new Notification.Builder(this); // notification icon
	mBuilder.setSmallIcon(R.drawable.m_lawn_transparent);//.setVisibility(View.GONE);
    mBuilder.setLargeIcon(bm);
	mBuilder.setContentTitle(""+t1); // title
	mBuilder.setContentText(""+t2.toUpperCase()); // body message
	mBuilder.setAutoCancel(false); // clear notification when clicke
	mBuilder.setColor(invColor );
	mBuilder.setPriority(Notification.PRIORITY_MAX);
    mBuilder.setSubText(t3);
	mBuilder.setOngoing(true);
			 
			 
	Intent intent = new Intent(this, MainActivity.class);
	PendingIntent pi = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
	mBuilder.setContentIntent(pi);

			 Intent intentNext = new Intent("NEXT");   intentNext.setAction("NEXT_IT");
			 PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, intentNext, 0);
			 
			 Intent intentBack = new Intent("BACK");   intentBack.setAction("BACK_IT");
			 PendingIntent pIntent2 = PendingIntent.getBroadcast(this, 2, intentBack, 1);
			
			 Intent intentPause = new Intent("PAUSE");   intentPause.setAction("PAUSE_IT");
			 PendingIntent pIntent3 = PendingIntent.getBroadcast(this, 3, intentPause, 2);
			 
			 mBuilder.addAction(R.drawable.back, "",pIntent2);
			 mBuilder.addAction(R.drawable.m_lawn_transparent, "",pIntent3);
			 mBuilder.addAction(R.drawable.next, "",pIntent);
			 
			
			  registerReceiver(receiver, new IntentFilter("NEXT_IT"));
			  registerReceiver(receiver, new IntentFilter("BACK_IT"));
			  registerReceiver(receiver, new IntentFilter("PAUSE_IT"));
			  //sendBroadcast(intentNext);
	          
    NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	//mNotificationManager.IMPORTANCE_HIGH
    mNotificationManager.notify(0, mBuilder.build());
	}
	       
	
			  
			  
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			              //toaster("rt");
			     if ( intent.getAction().equals("NEXT_IT")) {  //toaster("334");
			goToNext();   }
			     if ( intent.getAction().equals("BACK_IT")) {  //toaster("334");
		    goToBack();   }
			     if ( intent.getAction().equals("PAUSE_IT")) {  //toaster("334");
			songImClicked(); }
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class MyCustomAsyncTask extends AsyncTask<song, Void, Bitmap> {
        song _songItem;
		@Override
		protected void onPreExecute(){
			// This runs on the UI thread before the background thread executes.
			super.onPreExecute();
			// Do pre-thread tasks such as initializing variables.
		
		}
		@Override
		protected Bitmap doInBackground(song... params) {
			_songItem = params[0];
			return null;
		}
		@Override
		protected void onProgressUpdate(Void...p) {
			// Runs on the UI thread after publishProgress is invoked
			
		} 
		@Override
		protected void onPostExecute(final Bitmap bmp) {
			// This runs on the UI thread after complete execution of the doInBackground() method
			// This function receives result(String s) returned from the doInBackground() method.
			// Update UI with the found string.
			
			ImageLoader imageLoader = new ImageLoader(MainActivity.this);
			imageLoader.DisplayImage(_songItem.getSongData(), songIm, R.drawable.cover_image);
			final Bitmap bitmap = imageLoader.LoadedBitmap;
			if(imageLoader.BitmapIsFound){	NOW_PLAYING_HAS_NO_BITMAP= false;   }
			else{ NOW_PLAYING_HAS_NO_BITMAP = true;    }
			
			new Thread(new Runnable() {		@Override    public void run() {  
				runOnUiThread(new Runnable() {		@Override    public void run() {
					song item = _songItem;
					//new logMostPlayed().logMostPlayed(MainActivity.this, item.getSongData());
					mainUi.uIChangeOnPlay(item);
					cA.notifyDataSetChanged();
					Notifier(item.getSongArtist(), item.getSongTitle(), item.getSongAlbum(), bitmap, Uiprops.secondaryColor);
				}});
			}}).start();
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	




	
	
	
	



	private void handleUncaughtException(Throwable e) throws IOException {
		
		// Create a new unique file
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
		String timestamp;
		File file = null;
		while (file == null || file.exists()) {
			timestamp = dateFormat.format(new Date());
			file = new File(getExternalFilesDir(null), "all_crashLog_" + timestamp + ".txt");
		}
		file.createNewFile();
		// Write the stacktrace to the file
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			for (StackTraceElement element : e.getStackTrace()) { 
				//writer.write(element.toString());
			}
			writer.write("at: "+e.getStackTrace()[0].getFileName()+"- "+e.getStackTrace()[0].getLineNumber()+"; e: "+e+";");
		} finally {
			if (writer != null) writer.close();
		}
		// You can (and probably should) also display a dialog to notify the user
	}

 @Override
 protected void onResume(){
 super.onResume();

 }
 @Override
 public void onPause() {
 // First call the "official" version of this method
 super.onPause();
 //Toast.makeText(this, "In onPause", Toast.LENGTH_SHORT).show();
}
@Override
protected void onRestart()
{
// TODO: Implement this method
super.onRestart();
}
@Override
public void onDestroy() {
 // First call the "official" version of this method
 super.onDestroy();
 finish();
 //Toast.makeText(this, "In onDestroy", Toast.LENGTH_SHORT).show();
 
}










	private playservice playservice;
    private boolean bound = false;
    @Override
    protected void onStart() {
        super.onStart();
        // bind to Service
        Intent intent = new Intent(this, playservice.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from service
        if (bound) {
            //playservice.setCallbacks(null); // unregister
            //unbindService(serviceConnection);
            //bound = false;
        }
    }
    /** Callbacks for service binding, passed to bindService() */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // cast the IBinder and get MyService instance
            playservice.LocalBinder binder = (playservice.LocalBinder) service;
            playservice = binder.getService();
            bound = true;
            playservice.setCallbacks(serveCb); // register
			
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

	playservice.ServiceCallbacks serveCb = new playservice.ServiceCallbacks(){
	/* Defined by ServiceCallbacks interface */
	@Override
	public void PLAY_UI(song song_item) {
			NOW_PLAYING = song_item;
			new MyCustomAsyncTask().execute(song_item);
			//toaster(song_item.getSongData());
	}
	@Override
	public void SONGIM_CLICKED(boolean PLAYING){
			setSongAlpha(PLAYING);
	}
	};










 
 
 
 
 
 

	
	
	
	
	
	
	
	
	
}