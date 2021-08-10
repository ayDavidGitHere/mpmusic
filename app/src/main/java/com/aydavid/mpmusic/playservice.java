package com.aydavid.mpmusic;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.media.*;
import java.util.ArrayList;
import org.apache.http.entity.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.util.concurrent.*;


public class playservice extends Service
{   



    public interface ServiceCallbacks {
		void PLAY_UI(song song_item);
		void SONGIM_CLICKED(boolean PLAYING);
	} 
	// Binder given to clients
    public final IBinder binder = new LocalBinder();
    // Registered callbacks
    public static ServiceCallbacks serviceCallbacks;
    // Class used for the client Binder.
    public class LocalBinder extends Binder {
        playservice getService() {
            // Return this instance of MyService so clients can call public methods
            return playservice.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }
	
	
	
	
	

	public static playservice instance;
	public static boolean isRunning = false;
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance = this;
		this.isRunning = true;
		
		
	    try{
		registerEventReceiver();
		mpServe = new MediaPlayer();
		mpServe.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) { 
					if(startPlaying){ LIST_TONEXT(); }
				}
		});
		}catch(Exception e){   sendCrash(e); }
		
		/*
		final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable e) {
					try {
						sendCrash( (Exception) e);
						System.exit(1); 
					} catch (Throwable e2) {  
						defaultHandler.uncaughtException(thread, e);
					}
				}
		 }); //EO handler
		 */
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{ 
	

		// TODO: Implement this method
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy(){
		isRunning = false;
		instance = null;
		super.onDestroy();
	}
	
	
	
	
	
	
	
	
	
	
	Bundle bundle;
	static MediaPlayer mpServe;
	
	private void registerEventReceiver() {
		IntentFilter eventFilter = new IntentFilter();
		eventFilter.addAction("player");
		registerReceiver(eventReceiver, eventFilter);
		registerReceiver(eventReceiver, new IntentFilter("INIT"));
		registerReceiver(eventReceiver, new IntentFilter("PLAY_ITEM"));
		registerReceiver(eventReceiver, new IntentFilter("PAUSE_SONG"));
		registerReceiver(eventReceiver, new IntentFilter("RESUME_SONG"));
		registerReceiver(eventReceiver, new IntentFilter("LIST_TONEXT"));
		registerReceiver(eventReceiver, new IntentFilter("LIST_TOBACK"));
		registerReceiver(eventReceiver, new IntentFilter("SHUFFLE_LIST"));
		registerReceiver(eventReceiver, new IntentFilter("QUEUE_ADD"));
	}
	private BroadcastReceiver eventReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//This code will be executed when the broadcast in activity B is launched
		    bundle = intent.getExtras();
			
			
			if (intent.getAction().equals("INIT")) {
				playIndex = MainActivity.playIndex;
				shClicked = MainActivity.shClicked;
				copySongList = new ArrayList<song>();  
				copySongList.addAll(Playerprops.SONG_LIST_CURRENT);
				playfromQueue = true;
				songQueue = new ArrayList<Integer>();
				INIT();
			}
			if(intent.getAction().equals("PLAY_ITEM")){
				song_item= (song) bundle.getSerializable("song_item");
				playIndex = intent.getIntExtra("playIndex", 0);
				PLAY_SONG(song_item);
			}
			if(intent.getAction().equals("PAUSE_SONG")){
				PAUSE_SONG();
			}
			if(intent.getAction().equals("RESUME_SONG")){
				RESUME_SONG();
			}
			if(intent.getAction().equals("LIST_TONEXT")){
				LIST_TONEXT();
			}
			if(intent.getAction().equals("LIST_TOBACK")){
				LIST_TOBACK();
			}
			if(intent.getAction().equals("SHUFFLE_LIST")){
				SHUFFLE_LIST();
			}
			if(intent.getAction().equals("QUEUE_ADD")){
				int songPos = intent.getIntExtra("songPos", 0);
				addToQueue(songPos);
			}
			
		}//EO ONrecieve
	};
	
	
	
	
	
	
	
	
	
	
	
    public void INIT(){
		focusLocks();
	}
	
	
	
	
	
	
	// playback conyrols
	song NOW_PLAYING;
	boolean startPlaying = false;
	song  song_item;
	public int shufflePlayIndex = -1;
	public int playIndex ;//;=MainActivity.playIndex;
    public int shClicked ;//= MainActivity.shClicked;
    public ArrayList<song> copySongList;//= MainActivity.copySongList;
    //creating Queue
	boolean playfromQueue;
	List<Integer> songQueue;
	int[] randomQueue;
	List<Integer> randList;
	
	
	
	public void PLAY_SONG(final song item){
		NOW_PLAYING = item;
		startPlaying = true;
		mpServe.reset();

		try{
			playservice.serviceCallbacks.PLAY_UI(item);
			mpServe.setDataSource( item.getSongData() );
			mpServe.prepare();
			RESUME_SONG();
		}
		catch(Exception e){ toast_serve(""+e); }

	}//EO playsong
	
	public void PAUSE_SONG(){
		mpServe.pause();
		playservice.serviceCallbacks.SONGIM_CLICKED(mpServe.isPlaying());
	}
	public void RESUME_SONG(){
		mpServe.start();   
		if(playservice.serviceCallbacks!=null) playservice.serviceCallbacks.SONGIM_CLICKED(mpServe.isPlaying());
	}
	
	public void SEEKTO_SONG(int position){
		mpServe.seekTo(position);	
	}
	
	
	
	public void LIST_TONEXT(){
		ArrayList <song> itemA = Playerprops.SONG_LIST_CURRENT;
	    try{
			if(songQueue.size() < 1){playfromQueue = false;}
			if(!playfromQueue){
				if(shClicked %2==0){ //shuffle click listener
					if(shufflePlayIndex>randList.size()-2){    createRandomQueue();    }
					shufflePlayIndex++;
					playIndex = randomQueue[shufflePlayIndex];
				}
				else{ playIndex = playIndex+1; }
				if(playIndex <Playerprops.SONG_LIST_CURRENT.size()){  
					song itemE = itemA.get(copySongList.get(playIndex).getSongInd() );
					PLAY_SONG(itemE);  
				}
			}//EO if(playfromQueue)
			else if(playfromQueue){ 
				playIndex = songQueue.get(0);
				songQueue.remove(0);
				if(playIndex <Playerprops.SONG_LIST_CURRENT.size()){  
					song itemE = itemA.get( playIndex );
					PLAY_SONG(itemE);  
				}
			}//EO else if

		}catch(Exception e){toast_E(e); }
	}//Eo goToNext()
	public void LIST_TOBACK(){
		ArrayList <song> itemA = Playerprops.SONG_LIST_CURRENT;
		if(!playfromQueue || playfromQueue){
			if(shClicked %2==0){
			    if(shufflePlayIndex<=0){  shufflePlayIndex= 1;  }
				shufflePlayIndex--;
			    playIndex = randomQueue[shufflePlayIndex];
			}
			else{ playIndex = playIndex-1; }
		}
		if(playIndex <Playerprops.SONG_LIST_CURRENT.size()){  
			song itemE = itemA.get(copySongList.get(playIndex).getSongInd() );
			PLAY_SONG(itemE);  
		}
	}
	public void SHUFFLE_LIST(){
		shClicked = shClicked+1;
		if(shClicked%2 ==0){
			createRandomQueue();
			toast_serve(" Shuffle-play Is On ");}
		else{ toast_serve(" Shuffle-play Is Off ");}
	}

	

    //Queue
	public void addToQueue(int songPos){
		playfromQueue = true;
		songQueue.add(songPos);
	}
	public void createRandomQueue(){
		shufflePlayIndex = -1;
		randomQueue = new int[copySongList.size()];
		randList = new ArrayList<Integer>();
		for(int ind=0; copySongList.size()>ind; ind++){
			randList.add(ind);
		}
		Collections.shuffle(randList);
		Integer[] rt= randList.toArray(new Integer[randList.size()]);
		for(int ind=0; randList.size()>ind; ind++){
			randomQueue[ind] = (int) rt[ind];
		}
	}
	
	
	
	
	
	
	// implementation of the OnAudioFocusChangeListener
	AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
		@Override
		public void onAudioFocusChange(int focusChange) {
			switch (focusChange) {
				case AudioManager.AUDIOFOCUS_GAIN:
					if (mPlaybackDelayed || mResumeOnFocusGain) {
						synchronized (mFocusLock) {
							mPlaybackDelayed = mpServe.isPlaying();
							mResumeOnFocusGain = false;
						}
						//toast_serve("focus gajned");
						RESUME_SONG();
					}
					break;
				case AudioManager.AUDIOFOCUS_LOSS:
					synchronized (mFocusLock) {
						// this is not a transient loss, we shouldn't automatically resume for now
						mResumeOnFocusGain = mpServe.isPlaying();//   false;
						mPlaybackDelayed = false;
					}
					PAUSE_SONG();  
					//toast_serve("focus loss");
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
					// we handle all transient losses the same way because we never duck audio books
					synchronized (mFocusLock) {
						// we should only resume if playback was interrupted
						mResumeOnFocusGain = mpServe.isPlaying();
						mPlaybackDelayed = false;
					}
					PAUSE_SONG();
					//toast_serve("focus duck");
					break;
			}//EO switch

		};

	};
	
	public void focusLocks(){  
		try{
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		AudioAttributes mPlaybackAttributes = new AudioAttributes.Builder()
			.setUsage(AudioAttributes.USAGE_MEDIA)
			.setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
			.build();  /*
		AudioFocusRequest.Builder mFocusRequestB =new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN);
		AudioFocusRequest.Builder builders[] = {
			mFocusRequestB.setAudioAttributes(mPlaybackAttributes), mFocusRequestB.setAcceptsDelayedFocusGain(true), mFocusRequestB.setWillPauseWhenDucked(true),mFocusRequestB.setOnAudioFocusChangeListener(audioFocusChangeListener) };     
		AudioFocusRequest mFocusRequest = mFocusRequestB.build();
		// requesting audio focus
		int res = mAudioManager.requestAudioFocus(mFocusRequest);  */
		int res = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		mpServe.setAudioAttributes(mPlaybackAttributes);
		synchronized(mFocusLock) {
			if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
				mPlaybackDelayed = false;
			} else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				mPlaybackDelayed = false;
				RESUME_SONG();
			} else if (res == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
				mPlaybackDelayed = true;
			}
		}
		//toast_serve("focusers");
		}catch(Exception e){   toast_E(e); }
	}
	
	final Object mFocusLock = new Object();
	boolean mPlaybackDelayed = false;
	boolean mResumeOnFocusGain = false;
	
	
	
	
	
	
	
	
	
	
	public void toast_serve(String string){  
		Toast.makeText(this, ""+string , 30000).show();
	}

	
	public void toast_E(Exception e){
			Toast.makeText(this, "at: "+e.getStackTrace()[0].getFileName()+"- "+e.getStackTrace()[0].getLineNumber()+"- e: "+e,  Toast.LENGTH_SHORT).show();
	}
	public void sendCrash(Exception e){
		try{
			Toast.makeText(this, "at: "+e.getStackTrace()[0].getFileName()+"- "+e.getStackTrace()[0].getLineNumber()+"- e: "+e,  Toast.LENGTH_SHORT).show();
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
			String timestamp;
			File file = null;
			while (file == null || file.exists()) {
				timestamp = dateFormat.format(new Date());
				file = new File(getExternalFilesDir(null), "Playservice.crashLog_" + timestamp + ".txt");
			}


			file.createNewFile();;
			// Write the stacktrace to the file
			FileWriter writer = null;
			try {
				writer = new FileWriter(file, true);
				for (StackTraceElement element : e.getStackTrace()) {
					//writer.write(element.toString());

				}
				writer.write("at: "+e.getStackTrace()[0].getFileName()+"- "+e.getStackTrace()[0].getLineNumber()+"- e: "+e);
			} finally {
				if (writer != null) writer.close();
			}

		}catch(Exception ee){   
			Toast.makeText(this, "ee: "+ee+";", Toast.LENGTH_SHORT).show();   
		}

	}//EO sendCrash();
	
	
	
	
	
	
	
	
		

}