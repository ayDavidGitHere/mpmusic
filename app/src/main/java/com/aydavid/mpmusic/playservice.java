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
import android.graphics.drawable.*;
import android.graphics.*;


public class playservice extends Service
{   



    public interface ServiceCallbacks {
		void PLAY_UI(song song_item);
		void SONGIM_CLICKED(boolean PLAYING);
	} 
	// Binder given to clients
    public final IBinder binder = new LocalBinder();
    public class LocalBinder extends Binder {
        playservice getService() {
        	return playservice.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
	public static ServiceCallbacks serviceCallbacks;
    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }
	
	
	
	
	

	public static playservice instance;
	public static boolean isRunning = false;
	Bundle bundle;
	static MediaPlayer mpServe;
	
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance = this;
		this.isRunning = true;
		
		
	    try{
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
		toast_serve("serve started");
		INIT();
		startForeground(1, notification);
		return START_STICKY;//super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy(){
		isRunning = false;
		instance = null;
		super.onDestroy();
	}
	
	private void INIT(){
		playIndex = 0;
		shClicked = 1;
		copySongList = new ArrayList<song>();  
		copySongList = Playerprops.SONG_LIST_CURRENT;
		playfromQueue = true;
		songQueue = new ArrayList<Integer>();
		INIT_Notifier();
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
			mpServe.setDataSource( item.getSongData() );
			mpServe.prepare();
			RESUME_SONG();
			if(serviceCallbacks!=null) playservice.serviceCallbacks.PLAY_UI(item);
		}
		catch(Exception e){ toast_serve(""+e); }

	}//EO playsong
	public void PAUSEPLAY_SONG(){
		if(mpServe.isPlaying())PAUSE_SONG();
		else RESUME_SONG();
	}
	public void PAUSE_SONG(){
		mpServe.pause();
		if(serviceCallbacks!=null)playservice.serviceCallbacks.SONGIM_CLICKED(mpServe.isPlaying());
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
				if(playIndex <copySongList.size()){  
					song itemE = itemA.get(copySongList.get(playIndex).getSongInd() );
					PLAY_SONG(itemE);  
				}
			}//EO if(playfromQueue)
			else if(playfromQueue){ 
				playIndex = songQueue.get(0);
				songQueue.remove(0);
				if(playIndex <copySongList.size()){  
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
		if(playIndex <copySongList.size()){  
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
	
	
	
	
	
	
	
	
	
	
	
	





	
	
	
	

	RemoteViews remoteView;
	Notification notification;
	Intent intent; PendingIntent pi;
	Intent intentNext; PendingIntent pIntent;
	Intent intentBack; PendingIntent pIntent2;
	Intent intentPause; PendingIntent pIntent3;
	Intent intentShuffle;  PendingIntent shufflePI;
	Intent intentCancel; PendingIntent cancelPI;
	Notification.Builder mBuilder;
	public void INIT_Notifier(){
		registerReceiver(receiver, new IntentFilter("NEXT_IT"));
		registerReceiver(receiver, new IntentFilter("BACK_IT"));
		registerReceiver(receiver, new IntentFilter("PAUSE_IT"));
		registerReceiver(receiver, new IntentFilter("SHUFFLE_LIST"));
		registerReceiver(receiver, new IntentFilter("CANCEL_PLAYER"));
		

		intent = new Intent(this, MainActivity.class);
		pi = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK ); 
		intentNext = new Intent("NEXT");   intentNext.setAction("NEXT_IT");
		pIntent = PendingIntent.getBroadcast(this, 1, intentNext, 0);
		intentBack = new Intent("BACK");   intentBack.setAction("BACK_IT");
	    pIntent2 = PendingIntent.getBroadcast(this, 2, intentBack, 1);
		intentPause = new Intent("PAUSE");   intentPause.setAction("PAUSE_IT");
		pIntent3 = PendingIntent.getBroadcast(this, 3, intentPause, 2);
		intentShuffle = new Intent("SHUFFLE");   intentShuffle.setAction("SHUFFLE_LIST");
		shufflePI = PendingIntent.getBroadcast(this, 4, intentShuffle, 3);
		intentCancel = new Intent("CANCEL");   intentCancel.setAction("CANCEL_PLAYER");
		cancelPI = PendingIntent.getBroadcast(this, 5, intentCancel, 4);
		
		remoteView = new RemoteViews(this.getPackageName(), R.layout.notification_layout);
		mBuilder = new Notification.Builder(this);
		notification = mBuilder.build();
	}
	
	public void Notify(String t1, String t2, String t3, Bitmap bm, int invColor, int color){
		remoteView.setOnClickPendingIntent(R.id.but_back, pIntent2);
		remoteView.setOnClickPendingIntent(R.id.but_next, pIntent);
		remoteView.setOnClickPendingIntent(R.id.song_image, pIntent3);
		remoteView.setOnClickPendingIntent(R.id.but_shuffle, shufflePI);
		remoteView.setOnClickPendingIntent(R.id.but_cancel, cancelPI);
		remoteView.setImageViewBitmap(R.id.song_image, bm);
		remoteView.setTextViewText(R.id.song_title, t1);
		remoteView.setTextViewText(R.id.song_artist, t2);
		remoteView.setTextViewText(R.id.song_album, t3);
		remoteView.setInt(R.id.song_image_layout, "setBackgroundColor", invColor);
		//remoteView.setTextColor(R.id.song_title, color);remoteView.setTextColor(R.id.song_artist, color);remoteView.setTextColor(R.id.song_album, color);
		//remoteView.setInt(R.id.but_back, "setColorFilter", color);remoteView.setInt(R.id.but_next, "setColorFilter", color);remoteView.setInt(R.id.but_shuffle, "setColorFilter", color);

		mBuilder.setSmallIcon(R.drawable.cover_image);//.setVisibility(View.GONE);
		mBuilder.setLargeIcon(bm);
		mBuilder.setContentTitle(""+t1); // title
		mBuilder.setContentText(""+t2.toUpperCase()); // body message
		mBuilder.setAutoCancel(false); // clear notification when clicke
		mBuilder.setColor(invColor );
		mBuilder.setPriority(Notification.PRIORITY_MAX);
		mBuilder.setSubText(t3);
		mBuilder.setOngoing(true);
		mBuilder.setContent(remoteView);
		mBuilder.setContentIntent(pi);
		/*
		 mBuilder.addAction(R.drawable.back, "",pIntent2);
		 mBuilder.addAction(R.drawable.m_lawn_transparent, "",pIntent3);
		 mBuilder.addAction(R.drawable.next, "",pIntent);
		 */

		notification = mBuilder.build();
		if (android.os.Build.VERSION.SDK_INT >= 16) {notification.bigContentView = remoteView;}
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, notification);
	}//EO 





	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if ( intent.getAction().equals("NEXT_IT")) {  //toaster("334");
				LIST_TONEXT();
			}
			if ( intent.getAction().equals("BACK_IT")) {  //toaster("334");
		    	LIST_TOBACK();
			}
			if ( intent.getAction().equals("PAUSE_IT")) {  //toaster("334");
				PAUSEPLAY_SONG();
			}
			if ( intent.getAction().equals("SHUFFLE_LIST")) {
				SHUFFLE_LIST();
			}
			if ( intent.getAction().equals("CANCEL_PLAYER")) {
				stopSelf();
			}
		}
	};











	
	
	
	
	
	
	
	
	
	
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
	
	
	
	
	

	final Object mFocusLock = new Object();
	boolean mPlaybackDelayed = false;
	boolean mResumeOnFocusGain = false;
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