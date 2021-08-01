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
import android.content.res.*;
import android.content.pm.*;
import java.text.*;
import java.time.*;
import java.time.format.*;


public class splasher extends Activity{
	
	
	
	private final static String SAVE_INSTANCE = "example_arg";
	private int SAVE_INSTANCE_INT;
	public final int SPLASH_DISPLAY_LENGTH = 500;
	
	
	
	
	private void checkPermission() {
		if ( 
		checkSelfPermission( Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED 
		||checkSelfPermission(Manifest.permission.INTERNET) !=PackageManager.PERMISSION_GRANTED
		||checkSelfPermission( Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED) {
			requestPermissions(
					                              new String[]{Manifest.permission.WAKE_LOCK,
												  Manifest.permission.INTERNET,
												  Manifest.permission.ACCESS_NETWORK_STATE},
											  123);
		} 
	}
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);  
		if(!isTaskRoot()){       //if splasher task alrrady exist -> go to next activity which alrrady has singleInstanceLaunchMode
		    Intent mainIntent = new Intent(splasher.this, MainActivity.class);
			mainIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
			startActivity(mainIntent);
			return;
		}  
		if(sis != null) {	SAVE_INSTANCE_INT = sis.getInt(SAVE_INSTANCE);		}
		
		
		//set the content view. The XML file can contain nothing but an image, such as a logo or the
		setContentView(R.layout.splasher);
		getActionBar().hide();
		final int declareRand = new Random().nextInt(4);
		RelativeLayout relG = (RelativeLayout) findViewById(R.id.relG);
		uiColChanger(relG, declareRand);
		if(Build.VERSION.SDK_INT >= 21){  //status bar
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ccc);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
		     checkPermission();
		}
	    
		
		
		//start service
		Intent intent = new Intent(splasher.this, playservice.class);  
		startService(intent); 
	
		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
				try{
					BufferSongList bufSongList = new BufferSongList(splasher.this);
					bufSongList.load();
					Bundle bundle = new Bundle();
					bundle.putInt("declareRand", declareRand);
					Intent mainIntent = new Intent(splasher.this, MainActivity.class);
					mainIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
					mainIntent.putExtras(bundle);
				    startActivity(mainIntent);
					//splasher.this.finish();
					
				}catch(Exception e){
					e.printStackTrace();
					sendCrash(e);
				}
								}
	   }, SPLASH_DISPLAY_LENGTH);//EO 
		
			
			
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVE_INSTANCE, SAVE_INSTANCE_INT);
	}
	public void onPause(){
		super.onPause();
		//finish();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	String tint1[]= {"#1b171b", "#242628", "#1d242e", "#121212"    };
	//String tint2[] = {"#dc1436", "#dc1436", "#dc1436", "#dc143c"};
	String tint2[] = {"#ffffff", "#ffffff", "#ffffff", "#ffffff"};
	
	int ccc; 
	public void uiColChanger(View v, int i){
		v.setBackgroundColor(Color.parseColor( tint1[i]) );
		ccc = Color.parseColor(tint1[i]);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void sendCrash(Exception e){
		try{
			Toast.makeText(splasher.this, "e: "+e,  Toast.LENGTH_SHORT).show();
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
			String timestamp;
			File file = null;
			while (file == null || file.exists()) {
				timestamp = dateFormat.format(new Date());
				file = new File(getExternalFilesDir(null), "crashLog_" + timestamp + ".txt");
			}


			file.createNewFile();;
			// Write the stacktrace to the file
			FileWriter writer = null;
			try {
				writer = new FileWriter(file, true);
				for (StackTraceElement element : e.getStackTrace()) {
					//writer.write(element.toString());

				}
				writer.write("e: "+e+"; ");
			} finally {
				if (writer != null) writer.close();
			}

			Intent mainIntent = new Intent(splasher.this, MainActivity.class);
			//startActivity(mainIntent);
		}catch(Exception ee){   
			Toast.makeText(splasher.this, "ee: "+ee+";", Toast.LENGTH_SHORT).show();   
		}
		
	}//EO sendCrash();
	
		
	
	
	
	
	
	
	
	
}