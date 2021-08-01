package com.aydavid.mpmusic;
import android.text.format.*;
import android.content.*;
import java.io.*;
import android.widget.*;
import android.view.*;
import android.app.*;
import android.media.*;
import android.util.*;
import android.os.*;
import android.app.*;
import java.time.*;
import java.util.*;
import org.json.*;



public class logMostPlayed
{
	
	
	Context context;
	public void logMostPlayed(Context context, String logSong){
		try{
        this.context = context;
	  
		Date date = new Date(); 
		String localizedDate = ""+date.getDate();
		String firstDate, lastDate ;
		firstDate = localizedDate;
		lastDate = ""+(date.getDate()+1);
		
		int monther = date.getMonth();
		String [] months = {"January","Febuary","March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		String getDateInMonth = "2020/"+months[monther];


			String content = "";
			File path = new File(context.getExternalFilesDir(null)+"/logMostPlayed/"+getDateInMonth+".mpm" );
			if(path.exists()){
				FileInputStream fileInputStream2;
				fileInputStream2 = new FileInputStream(path) ;
				int read;
				content = "";
				while(  (read= fileInputStream2.read())!=-1  ){
					content= content+(char) read;   //toaster(cont+" "+read);
				}
				fileInputStream2.close();
		     }//EI if path2



            JSONObject jObj=  new JSONObject();
			if(content != "" ){  jObj = new JSONObject(content);  }
			if(jObj.has(logSong)){ //if key exist already
				   int playTimes = jObj.getInt(logSong);
				   jObj.remove(logSong); 
			       jObj.put( logSong, playTimes+1 );
			}
			else{  jObj.put(logSong, 1); }
			
			
			String saveAsJsonString = jObj.toString();
			FileOutputStream fileOutputStream3;
			//if path doesn't exist create with loop.
            generatePath(path);
			fileOutputStream3 = new FileOutputStream(path);
			fileOutputStream3.write(saveAsJsonString.getBytes());
			fileOutputStream3.close();
		}
		catch(Exception e){  toaster_onUI( ""+e+""); }
	}//EO logmostplayed
	
	
	
	
	
	
	
	
	
	public void showMostPlayed(Context context, oCA fromOcA){   
	    this.context = context;
		Date date = new Date(); 
		String localizedDate = ""+date.getDate();
		String firstDate, lastDate ;
		firstDate = localizedDate;
		lastDate = ""+(date.getDate()+1);

		int monther = date.getMonth();
		String [] months = {"January","Febuary","March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		String getDateInMonth = "2020/"+months[monther];
		
		try{
			String content = "";  
			File path = new File(context.getExternalFilesDir(null)+"/logMostPlayed/"+getDateInMonth+".mpm" );
			if(path.exists()){
				FileInputStream fileInputStream2;
				fileInputStream2 = new FileInputStream(path) ;
				int read;
				content = "";
				while(  (read= fileInputStream2.read())!=-1  ){
					content= content+(char) read;   
				}
				fileInputStream2.close();
			}//EO if path.exist();

    

            JSONObject jObj=  new JSONObject();
			if(content != "" ){   jObj = new JSONObject(content);  }
			ArrayList <Integer> songsPlayTimes = new ArrayList<Integer>();
			ArrayList <String> songsFromJson = new ArrayList<String>();
			
			Iterator songsKeyInJson = jObj.keys();
			while (songsKeyInJson.hasNext()){   //iterator
				  String currentKey = (String) songsKeyInJson.next();
				  int playTimes = jObj.getInt(currentKey);
				  
				  //sorter
				 Boolean foundPlace = false;
				 int placeIndex = songsPlayTimes.size()-1;
				  while(!foundPlace){
					  if(placeIndex>-1 && playTimes < songsPlayTimes.get(placeIndex)){ 
					       foundPlace = false;
					  }
				      else{    
					       songsPlayTimes.add(placeIndex+1, playTimes);
					       songsFromJson.add(placeIndex+1, currentKey);
						   foundPlace = true;
					  }
					  placeIndex--;
				  }//EO while
			}//EO while  
			fromOcA.filter_mostPlayed(songsFromJson, songsPlayTimes, months[monther]);
		}//EO try
		catch(Exception e){  
			e.printStackTrace();
			toaster_onUI(e+"");
		}
		
	}//EO showplaylist
	
	
	
	
	
	
	
	
	public boolean generatePath(File path){
		String [] splitPath = path.toString().split("/");
		int slashIndex = 0;    
		while ( slashIndex < splitPath.length-1 ){
			if( !new File(splitPath[slashIndex]).exists() ){
				new File(splitPath[slashIndex]).mkdir();
			};
			splitPath[slashIndex+1] = splitPath[slashIndex]+"/"+splitPath[slashIndex+1];
			slashIndex++;
		}
		return true;
	}
	
	
	public void toaster( String string){
		Toast.makeText( (Activity)context, string, 3000).show();
	}
	
	public void toaster_onUI(final String string){
		((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					toaster(string);
				}	
		});
	}
	
	
	
	
	
	
	
	
	
	
}//EO all code