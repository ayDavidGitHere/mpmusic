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
		getActionBar().hide();
		//loaddatafromsplasher
		Bundle bundleFromSplasher = getIntent().getExtras();
		if (bundleFromSplasher != null) {
		    declareRand = bundleFromSplasher.getInt("declareRand");
		}
		else{   	toaster("could not load from splasher");	}
		if(bundleFromSplasher == null){  declareRand = new Random().nextInt(4); }
		try{
			
		/**Order is important**/
		mainUi = new MainUi(this, this, getWindow());
		mainUi.SetUiLayoutType(2);
		mainUi.SetContentView();
		mainUi.InitialiseViews();
		cA = new oCA(this, MainActivity.this, Playerprops.SONG_LIST_CURRENT);
		mainUi.uiColorAndResChanger(declareRand);
		mainUi.SetListeners();
        startRun(); 
		
		
		
		mp = new MediaPlayer();
		mp = playservice.mpServe;
		}catch(Exception e){ toaster(""+e+"\n at:"+e.getStackTrace()[0].getFileName()+"\n"+e.getStackTrace()[0].getLineNumber()); }
		
		

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
 }//EO oncreat
	
    int tClicked = 1;
	int marg = 0;
	//CustomAdapter cA;  
	static oCA cA;
	song item;
	int popItemPos = 0;
	int declareRand = 0;
	static Boolean NOW_PLAYING_HAS_NO_BITMAP = true;
	static Context app_context;
	MainUi mainUi;
	
	
	
	
	
	
	
	
	
	
	
    
    
    
    
    
	public void toaster(String string){		Toast.makeText(this, string, 3000).show();}
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
					mainUi.moveTimerBar();
					if(Playerprops.NOW_PLAYING != null && playPosition > playDuration/2){
					  if(LAST_LOGGED == null || LAST_LOGGED.getSongData() != Playerprops.NOW_PLAYING.getSongData() ){
					    try{	  
							new Thread(new Runnable() { 
							@Override
								public void run(){
									LAST_LOGGED = Playerprops.NOW_PLAYING;
									//new logMostPlayed().logMostPlayed(MainActivity.this, Playerprops.NOW_PLAYING.getSongData());
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
	  
	public void startRun(){
		handler.postDelayed(runnable, 10);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	boolean PLAYING = false;
	song LAST_LOGGED;
	song NOW_PLAYING;
	MediaPlayer mp;
	
	
	//playbacks
	public void songImClicked(){
		playservice.PAUSEPLAY_SONG();
	}
	public void setSongAlpha(boolean PLAYING){ 
	    this.PLAYING = mp.isPlaying();
		if(PLAYING){  mainUi.songIm.setAlpha(0.95f); }
		if(!PLAYING){ mainUi.songIm.setAlpha(0.35f); }
	}
	

	
	//   CONTRO,,LS 
	public void PLAY_SONG(final song item){
		playservice.PLAY_SONG(item);
	}
	public void seekTo(int position){
		mp.seekTo(position);	
	}
	public void goToNext(){
		playservice.LIST_TONEXT();
	}
	public void goToBack(){
		playservice.LIST_TOBACK();
	}
	public void addToQueue(int songPos){
		playservice.addToQueue(songPos);
	}
	public void shuffleList(){
		playservice.SHUFFLE_LIST();
	}

	
	
	
	int songImC = 1; 
	int sVClicked = 1;
	int playPosition = 0;
	int playDuration = 10000;
	String songCol = "#FF5900";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
			
			ImageLoader imageLoader = cA.imageLoader;//new ImageLoader(MainActivity.this);
			imageLoader.DisplayImage(_songItem.getSongData(), mainUi.songIm, R.drawable.cover_image, true);
			final Bitmap bitmap = imageLoader.LoadedBitmap;
			if(imageLoader.BitmapIsFound){	NOW_PLAYING_HAS_NO_BITMAP= false;   }
			else{ NOW_PLAYING_HAS_NO_BITMAP = true;    }
			
			new Thread(new Runnable() {		@Override    public void run() {  
				runOnUiThread(new Runnable() {		@Override    public void run() {
					song item = _songItem;
					//new logMostPlayed().logMostPlayed(MainActivity.this, item.getSongData());
					mainUi.uIChangeOnPlay(item);
					cA.notifyDataSetChanged();
					playservice.Notify(item.getSongArtist(), item.getSongTitle(), item.getSongAlbum(), bitmap, Uiprops.secondaryColor, Uiprops.primaryColor);
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
		if (bound) {
           	playservice.setCallbacks(null); // unregister
            unbindService(serviceConnection);
            bound = false;
        }
		super.onDestroy();
		finish();
		//Toast.makeText(this, "In onDestroy", Toast.LENGTH_SHORT).show();

	}





	
	
	
	
	
	
	
	
 

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}