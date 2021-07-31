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

		//loaddatafromsplasher
		Bundle bundleFromSplasher = getIntent().getExtras();
		if (bundleFromSplasher != null) {
		    declareRand = bundleFromSplasher.getInt("declareRand");
		}
		else{
			toaster("could not load from splasher");
			
		}
		if(bundleFromSplasher == null){  declareRand = new Random().nextInt(4); }
		mainUi = new MainUi(this, this, getWindow());
		mainUi.SetUiLayoutType(1);
		mainUi.SetContentView();
		mainUi.uiColorAndResChanger(declareRand);
		
		
		
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
		
		
		
		
		cA = new oCA(this, MainActivity.this, Playerprops.SONG_LIST_CURRENT);
		tracksLv.setAdapter(cA);
		mainUi.SetListeners();
        startRun(); 
		
		if(playservice.isRunning == true){
		toaster("Service is running");
		Bundle bundle = new Bundle();
		Intent eventIntent = new Intent("INIT");
		eventIntent.putExtras(bundle);
		this.sendBroadcast(eventIntent);
        }
		
		mp = new MediaPlayer();
		mp = playservice.mpServe;

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
	
	
	
		Handler handler = new Handler();
		Runnable runnable = new Runnable() {
				@Override
				public void run() {
				  if(Playerprops.NOW_PLAYING != null){
					moveTimerBar();
					if(Playerprops.NOW_PLAYING != null && playPosition > playDuration/2){
					  if(LAST_LOGGED == null || LAST_LOGGED.getSongData() != Playerprops.NOW_PLAYING.getSongData() ){
					    try{	  
							new Thread(new Runnable() { 
							@Override
								public void run(){
									LAST_LOGGED = Playerprops.NOW_PLAYING;
									new logMostPlayed().logMostPlayed(MainActivity.this, Playerprops.NOW_PLAYING.getSongData());
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
			Playerprops.NOW_PLAYING = song_item;
			new MyCustomAsyncTask().execute(song_item);
			//toaster(song_item.getSongData());
	}
	@Override
	public void SONGIM_CLICKED(boolean PLAYING){
			setSongAlpha(PLAYING);
	}
	};










 
 
 
 
 
 

	
	
	
	
	
	
	
	
	
}