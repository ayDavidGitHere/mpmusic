package com.aydavid.mpmusic;
import android.os.*;
import android.graphics.*;
import android.media.*;
import android.widget.*;
import android.content.*;

class asyncBack extends AsyncTask<String, Void, Bitmap> {

	Context ctx;
	int res;
	String songD;
	public void asyncBack(Context context, int resource, String songData){ 
				ctx = context;
				res = resource;
				songD = songData;
	}
	
	
	
	
	
	int _params ;
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bmpNull = BitmapFactory.decodeResource( ctx.getResources(), res);
	        try{
				
		MediaMetadataRetriever mmr= new MediaMetadataRetriever();
		//mmr.setDataSource(dat.get(0).getSongData());
		mmr.setDataSource(songD);
		byte[] dataB = mmr.getEmbeddedPicture();
		if(dataB !=null){
			Bitmap bmp = BitmapFactory.decodeByteArray(dataB, 0, dataB.length);
			//Toast.makeText(this, bmp+" ", 30000).show();
			if(bmp !=null){    return bmp; }
			return bmp;
		}
		else{      return bmpNull; }
	}
		catch (Exception e){  
			return bmpNull;  /*Toast.makeText(this, e+" ", 30000).show(); */ }
	

	}
	@Override
	protected void onPostExecute(Bitmap result) {
		// This is called when we finish

	
	}
	@Override
	protected void onPreExecute() {
		// This is called before we begin
		//Bitmap bmp = BitmapFactory.decodeResource( ctx.getResources(), res);
		//albumArt.setImageBitmap(bmp);
	}
	@Override
	protected void onProgressUpdate(Void... values) {
		// Unlikely required for this example
	}

}
	