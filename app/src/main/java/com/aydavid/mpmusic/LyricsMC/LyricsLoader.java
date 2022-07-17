package com.aydavid.mpmusic.LyricsMC;

import android.content.*;
import android.widget.*;
import com.aydavid.mpmusic.*;
import android.os.*;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import org.json.*;
import java.util.*;
import java.net.*;
import android.net.*;







public class LyricsLoader
{

	String lyricsString_Model = 
	"Yeah, yeah, yeah (DJ Durel)\n\n"+
	"(Yeah, Pluto)\n\n"+
	"Put a 50 round on that Glock\n\n"+
	"Plush leather when you dropping the top\n\n"+
	"I push a button, now I can't stop\n\n"+
	"I'm doing donuts, turning out the lot\n\n"+
	"Staying vibed out at the spot\n\n"+
	"A mill' ticket when I pop out\n\n"+
	"Feeding all the trill bitches lobster\n\n"+
	"I'm doing Marni and Stella McCartney\n\n"+
	"I made a full 360 out the 'partments\n\n"+
	"I'm like Picasso, I paint me a portrait\n\n"+
	"I'm getting money, taking drugs with orphans\n\n"+
	"I'm at the bottom of the mud, where it started\n\n"+
	"I need biscotti, gotta smoke by a forest\n\n"+
	"Got a flock of ten, like an arsenal (yeah)\n\n"+
	"This shit making a nigga phenomenal\n\n"+
	"Put the energy on you, you elevate\n\n"+
	"Put the cut on Fetti and sellin' it\n\n"+
	"Spend the money on me, you inhaling it\n\n"+
	"There's a lot designers to recommend (suu)\n\n"+
	"Put the racks in 'Rari, then I'm riding in it (skrrt, skrrt)\n\n"+
	"To the dealers to get on the promise land\n\n"+
	"Grip the 30 and steel with the other hand (skrrt)\n\n"+
	"Addicted to birdies and rubber bands (brr)\n\n"+
	"Dividing the brick with the scissor hands\n\n"+
	"Balenciaga racks in the shoe box (shoe box)\n\n"+
	"Upgrade the bando with new locks (new locks)\n\n"+
	"Pockets stay loaded, Guwop (Guwop)\n\n"+
	"Look at my drip, this a new drop\n\n"+
	"Smoking on forest, ZaZa (exotic)\n\n"+
	"Drinking, I'm pouring, all mine (drink)\n\n"+
	"Them niggas is boring, not mud (mud)\n\n"+
	"Sending off a hundred shots (shots)\n\n"+
	"Wet the floor like a mop\n\n"+
	"Spinnin' in soccer vans, hot box\n\n"+
	"Shoes so dirty from running from cops\n\n"+
	"Mama said sit down 'fore you get shot (mama)\n\n"+
	"Young nigga havin' a sit down\n\n"+
	"'Cause the business here with the mob (business)\n\n"+
	"Young niggas out here runnin' round\n\n"+
	"15 years old with a Glock (young nigga)\n\n"+
	"Young nigga running 'round wilding with that stick\n\n"+
	"I told him move smart (stick)\n\n"+
	"Young nigga havin' a sit down\n\n"+
	"'Cause the business here with the mob (yeah)\n\n"+
	"Button it up, I'm coming sharp (sharp)\n\n"+
	"Just a young nigga with a plot\n\n"+
	"Put surveillance all on the spot (soo)\n\n"+
	"Put a 50 ball in one knot (yeah)\n\n"+
	"Put a 50 round in that Glock\n\n"+
	"Plush leather when you dropping the top\n\n"+
	"I push a button, now I can't stop\n\n"+
	"I'm doing donuts, turning out the lot\n\n"+
	"Staying vibed out at the spot\n\n"+
	"A mill' ticket when I pop out\n\n"+
	"Feeding all the trill bitches lobsters\n\n"+
	"I'm doing Marni and Stella McCartney\n\n"+
	"I made a full 360 out the 'partments\n\n"+
	"I'm like Picasso, I paint me a portrait\n\n"+
	"I'm getting money, taking drugs with orphans\n\n"+
	"I'm at the bottom of the mud, where it started\n\n"+
	"I need biscotti, gotta smoke by a forest\n\n"+
	"Got a flock of ten, like an arsenal\n\n"+
	"This shit making a nigga phenomenal (Takeoff)\n\n"+
	"Put the energy on you, you elevate\n\n"+
	"When I count up a check, it's to motivate (motivate)\n\n"+
	"For all of my niggas, ain't gotta try to make another way (get it)\n\n"+
	"Looking at cars and stars like damn, I'ma be rich one day (rich)\n\n"+
	"You place your order from over the border\n\n"+
	"It can get shipped that way (ship it, hey)\n\n"+
	"We never settlin' (settlin')\n\n"+
	"Walk with the felony (felony)\n\n"+
	"I like a thick bitch with melanin (melanin)\n\n"+
	"We got mansions with camera surveillance (surveillance)\n\n"+
	"Look at the stars in the Wraith, I see aliens (sliens)\n\n"+
	"I could dunk on your bitch, but I laid it in (laid it in)\n\n"+
	"Bought her Birkins, bought her the wagon, Benz (wagon Benz)\n\n"+
	"Outer space with this money, on Saturn (Saturn)\n\n"+
	"Told the bitch ride this shit, go on saddle in (saddle in)\n\n"+
	"I been the nigga that's getting it (get it)\n\n"+
	"If I had started, I gotta finish (finish it)\n\n"+
	"Never switch up on the business\n\n"+
	"'Cause when you start, that's when it diminish (diminish)\n\n"+
	"I went and copped me a link\n\n"+
	"But it's not the Cuban, it's an infinity (infinity)\n\n"+
	"Pull out, he shaking and trembling (trembling)\n\n"+
	"These niggas sweeter than cinnamon (sweet, hey)\n\n"+
	"I took the bitch out to Tiffany's (tiffany's)\n\n"+
	"Bezel twinkle, this shit sickening (twinkle)\n\n"+
	"The money been talking, I'm listening (listening)\n\n"+
	"The switch on the Glock, bullets whistling\n\n"+
	"Two-door Lambo', that shit Listerine (Listerine)\n\n"+
	"The chopper gon' blow em' to misery (misery)\n\n"+
	"Load up the bando with 'phetamine ('phetamine)\n\n"+
	"The dope, it's getting service from Medellín (Medellín)\n\n"+
	"Put a 50 round in that Glock\n\n"+
	"Plush leather when you dropping the top\n\n"+
	"I push a button, now I can't stop\n\n"+
	"I'm doing donuts, turning out the lot\n\n"+
	"Staying vibed out at the spot\n\n"+
	"A mill' ticket when I pop out\n\n"+
	"Feeding all the trill bitches lobsters\n\n"+
	"I'm doing Marni and Stella McCartney\n\n"+
	"I made a full 360 out the 'partments\n\n"+
	"I'm like Picasso, I paint me a portrait\n\n"+
	"I'm getting money, taking drugs with orphans\n\n"+
	"I'm at the bottom of the mud, where it started\n\n"+
	"I need biscotti, gotta smoke by a forest\n\n"+
	"Got a flock of tens, like an arsenal\n\n"+
	"This shit making a nigga phenomenal\n\n"+
	"Put the energy on you, you elevate";
	
	
	
	
	
	LyricsLoader context;
	Context appContext;
	String requestUrl;
	public LyricsLoader(Context _context){
		this.context = this;
		this.appContext = _context;
	}
	public void DisplayLyrics(TextView lyricsView, song song){
	    AsyncLyricsLoader Loader = (new AsyncLyricsLoader());
		Loader.SetView(lyricsView);
		Loader.execute(song);
	}//EO
	public String GetLyrics(song song){
		String lyricsString = this.lyricsString_Model;
		return lyricsString;
	}//EO GetLyrics
    
    private String fetchLyrics(song song) {
        try {
			String urlString = 
			"https://musicdownloadmp3.herokuapp.com/api/getlyrics_dfi?query="
			+song.getSongArtist()
			+" "
			+song.getSongTitle()
			;
            
            URL url= new URL(urlString);
            URI uri = new URI(url.getProtocol(),
            url.getUserInfo(),
            IDN.toASCII(url.getHost()),
            url.getPort(),
            url.getPath(),
            url.getQuery(),
            url.getRef());
            String correctEncodedURL = uri.toASCIIString(); 
			context.requestUrl = correctEncodedURL;
			//
			
			
			
			
            URL urlObj = new URL(context.requestUrl);
            HttpURLConnection urlConnection 
            = (HttpURLConnection) urlObj.openConnection();
            HttpURLConnection c = urlConnection;
            c.setRequestMethod("GET");
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(50000);
            c.setReadTimeout(50000);
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201: 
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }
            return "{error: true, status: "+status+"}";
        } catch (Exception e) {
            //Toast.makeText(appContext, "Failed downloading " + url + " "+ e.toString(), 2000).show();
            return "{error: true, e: "+e.toString()+"}";
        }
    }
    
    public void SetTextView(TextView tview, String content)
	{
		try{
			JSONObject jObj=  new JSONObject(); 
			if(content != "" ){   jObj = new JSONObject(content); } 
			Lyric lyric = new Lyric();
			Iterator songsKeyInJson = jObj.keys();
			while (songsKeyInJson.hasNext()){   
				//iterator
				String currentKey = (String) songsKeyInJson.next(); 
				if(currentKey.compareToIgnoreCase("error")==0) lyric.error = (jObj.getBoolean(currentKey));
				if(currentKey.compareToIgnoreCase("status")==0) lyric.status = (jObj.getString(currentKey));
				if(currentKey.compareToIgnoreCase("value")==0) lyric.value = new JSONObject(jObj.getString(currentKey));

				//sorter
				//EO while
			}//EO while
			;;;;//System.out.println("Error: "+lyric.error);
			;;;;//System.out.println("Value: "+lyric.value);
			lyric.setValues();
			;;;;//System.out.println("\n THIS IS THE FINAL LYRICS \n ");
		    ;;;;//System.out.println(lyric.linesConcated);
		    tview.setText(lyric.linesConcated);
		}
		catch(Exception e){
			;;;;//System.out.println("\n Exception: \n"+e);
		}
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     * @ AsyncLyricsLoader
     **/
    
    
	
	public class AsyncLyricsLoader extends AsyncTask<song, Void, String> {
		@Override
		protected void onPreExecute(){
			// This runs on the UI thread before the background thread executes.
			super.onPreExecute();
			// Do pre-thread tasks such as initializing variables.
		
		}
		@Override
		protected String doInBackground(song[] songs)
		{
			// TODO: Implement this method
			String result = "";
			result = fetchLyrics(songs[0]);         
			return result;
		}
		@Override
		protected void onProgressUpdate(Void...p) {
			// Runs on the UI thread after publishProgress is invoked
		} 
		@Override
		protected void onPostExecute(String lyrics_json)
		{
			// TODO: Implement this method
			super.onPostExecute(lyrics_json);
			// This runs on the UI thread after complete execution of the doInBackground() method
			// This function receives result(String s) returned from the doInBackground() method.
			// Update UI with the found string.
			Toast.makeText(context.appContext, context.requestUrl, 2000).show();
            context.SetTextView(this.view, lyrics_json);
		}
		TextView view;
		public void SetView (TextView view){
			this.view = view;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class Lyric {
	    public boolean error;
	    public String status;
	    public String e; 
	    public JSONObject value;
	    public String linesConcated;
	    public String id;
	    public JSONArray linesArray;
		public Lyric (){

		}
		public void setValues(){
			this.linesConcated = "";
			if(this.error) return;
			try{
				this.id = value.getString("LYRICS_ID");
				this.linesArray = value.getJSONArray("LYRICS_SYNC_JSON");
				;;;;//System.out.println("\n\nLyrics.setValues:\n ");
				;;;;//System.out.println("Lyrics Id: "+id);
				;;;;//System.out.println("LyricsArrayOfLines: "+ this.linesArray);
				//EO while
				for (int i = 0, size = linesArray.length(); i < size; i++)
				{	
					this.linesConcated += "\n\n"+linesArray.getJSONObject(i).getString("line");
				}
			}catch(Exception e){
				this.linesConcated = "SORRY, LYRICS NOT FOUND";
				;;;;//System.out.println("\n Exception At setLines: \n"+e);
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
}