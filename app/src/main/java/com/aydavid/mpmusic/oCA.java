package com.aydavid.mpmusic;


import android.app.*;
import android.os.*;
import android.view.*;
import android.view.ViewGroup.*;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.*;
import android.media.*;
import android.graphics.*;
import android.view.inputmethod.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;


public class oCA extends ArrayAdapter<song> {
	oCA thisClass;

	private LayoutInflater inflater;
	ArrayList<song> dat;
	ArrayList<song> dat_copy;

	Context context;  
	Activity MainActivity;

	//res
	song songAt;
	int color, invColor, tintRes, tint1;
	int PRIM_COLOR = Color.parseColor("#dc1437");

	//widgets
	TextView songArtist , songTitle;
	public ImageView albumArt;
	RelativeLayout songGen;
	SearchView searchV;

	//headers
	String currentPlaylist = "ALL TRACKS";
	int defaultTab_id = R.id.tab_alltracks;

	String S_slash_N;

	View _view;
	ViewGroup _viewG;
	ArrayList<ImageView> aA;
	ImageLoader imageLoader;
	




	public oCA (Context context, Activity MainActivity,ArrayList<song> data){
		super(context, 0, data);
		thisClass = oCA.this;
		this.context = context;
		this.MainActivity = MainActivity;
		this.dat = data;  
		dat_copy = new ArrayList <song>(data);

		inflater = LayoutInflater.from(context);
	    aA = new ArrayList<ImageView>();
		imageLoader = new ImageLoader(context);
	}


	@Override
	public int getCount() {
		if (dat == null) {
			return 0;
		}
		if (dat.size() == 0) {
			//Return 1 here to show nothing
			return 1;
		}
		// Add extra view to show the footer view
		return dat.size() + 1;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{

		if (position !=0){
			songAt = (song) getItem(position-1);
			int limit = 100;
			StringBuilder songTitleText = new StringBuilder(songAt.getSongTitle());
			if(songAt.getSongTitle().length()> limit){ songTitleText.delete( limit, songAt.getSongTitle().length() );     }
			S_slash_N = ""+ ( (defaultTab_id == R.id.tab_mostplayed)?(mostPlaysTimes.get(mostPlaysTimes.size()-position)):position );  //use number of plays or regular position

			
			ViewHolder vHolder = null;
			if (view == null || view.getTag() == null) {
				view = inflater.inflate(R.layout.song, null);
				vHolder = new ViewHolder(view);
				view.setTag(vHolder); //for some reasoon setTags works but it not held
			} else {       vHolder = (ViewHolder) view.getTag();    }
			
			try{
				vHolder.songTitle.setText(songTitleText);
				vHolder.songArtist.setText(S_slash_N+"| "+"  "+" "+songAt.getSongArtist());
				vHolder.songTitle.setTextSize(13);
				vHolder.songArtist.setTextSize(9);
				vHolder.songTitle.setTextColor(color);
		    	vHolder.songArtist.setTextColor(color);
				vHolder.albumArt.setImageResource(tintRes);
				vHolder.albumArt.setId(position);
				imageLoader.DisplayImage(songAt.getSongData(), vHolder.albumArt, tintRes);
			}catch(Exception e){  Toast.makeText(getContext(),    position+"; "+e.getStackTrace()[0].getLineNumber()+" "+e, 2000).show(); }

			





		}//EO if		
		if(position == 0){ 
			Typeface font_codar = Typeface.createFromAsset(context.getAssets(), "font/CodaR.ttf");
			Typeface font_autumn = Typeface.createFromAsset(context.getAssets(), "font/Woodwork.ttf");
			Typeface font_abelr = Typeface.createFromAsset(context.getAssets(), "font/abelr.ttf");
			view = inflater.inflate(R.layout.header, null);

			searchV = view.findViewById(R.id.searchV);
			searchV.setBackgroundColor(PRIM_COLOR);
			searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
					@Override
					public boolean onQueryTextSubmit(String query) {
						filtering(query, dat_copy);
						return true;
					}
					@Override
					public boolean onQueryTextChange(String newText) {
						filtering(newText, dat_copy);
						return true;
					}
				});//EO searchV
			ImageView searchBut = view.findViewById(R.id.searchBut);
			searchBut.setColorFilter(PRIM_COLOR);
			searchBut.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						sVClicked = sVClicked+1;
						sVClicked();
					}});


			RelativeLayout header = view.findViewById(R.id.header);
			ImageView headerArt = view.findViewById(R.id.headerArt);
			TextView xSongsFrom = view.findViewById(R.id.text_SongsFrom);
			xSongsFrom.setText(dat.size()+"");
			xSongsFrom.setTypeface(font_abelr);
			xSongsFrom.setOnClickListener(new View.OnClickListener(){    @Override
				public void  onClick(View v){
					new Thread(new Runnable() {    @Override
						public void run(){
							BufferSongList bufSongList = new BufferSongList(context);
							bufSongList.load();
					}}).start();
				}
			});
			TextView text_alltracks = view.findViewById(R.id.text_alltracks);
			text_alltracks.setText("songs from"+" "+currentPlaylist);
			text_alltracks.setTypeface(font_autumn);
			headerArt.setImageResource(tintRes);
			header.setBackgroundColor(tint1);

			final View headerView = view;
			final LinearLayout linear = view.findViewById(R.id.linear);
			final LinearLayout linear2 = view.findViewById(R.id.linear2);
			final LinearLayout allplaylistsContain = view.findViewById(R.id.allplaylists);


			final TextView tab_playlists = view.findViewById(R.id.tab_playlists);
			final TextView tab_alltracks = view.findViewById(R.id.tab_alltracks);
			final TextView tab_mostplayed = view.findViewById(R.id.tab_mostplayed);
			TextView tab_albums = view.findViewById(R.id.tab_albums);
			TextView tab_artist = view.findViewById(R.id.tab_artists);
			ArrayList <TextView> tabs = new ArrayList<TextView> ();
			tabs.add(tab_playlists);
			tabs.add(tab_alltracks);
			tabs.add(tab_mostplayed);
			tabs.add(tab_albums);
			tabs.add(tab_artist);
			final ArrayList <TextView> tabsFinal = tabs;
			for(TextView tab: tabs){
				tab.setTypeface(font_abelr);
				tab.setBackgroundColor(tint1);
			}
			view.findViewById(defaultTab_id).setBackgroundColor(invColor);
			( (TextView) view.findViewById(defaultTab_id) ).setTextColor(color);


			tab_alltracks.setOnClickListener(new View.OnClickListener(){
					@Override
					public void  onClick(View v){
			            resetList();
						defaultTab_id = R.id.tab_alltracks;
						for(TextView tab: tabsFinal){ 
							tab.setBackgroundColor(tint1);
							tab.setTextColor(PRIM_COLOR);
						}
						v.setBackgroundColor(invColor);
						tab_alltracks.setTextColor(color);
					}
				}); //EO listener
			tab_playlists.setOnClickListener(new View.OnClickListener(){
					@Override
					public void  onClick(View v){
			            linear.setVisibility(View.GONE);
						linear2.setVisibility(View.VISIBLE);
						defaultTab_id = R.id.tab_playlists;
					    new playlistDialog().showPlaylists(context, thisClass, headerView ,allplaylistsContain);
						for(TextView tab: tabsFinal){ 
							tab.setBackgroundColor(tint1);
							tab.setTextColor(PRIM_COLOR);
						}
						v.setBackgroundColor(invColor);
						tab_playlists.setTextColor(color);
					}
				}); //EO listener
			tab_mostplayed.setOnClickListener(new View.OnClickListener(){
					@Override
					public void  onClick(View v){
			            currentPlaylist = "MOST PLAYED";
						defaultTab_id = R.id.tab_mostplayed;  
						new Thread(new Runnable() { //run on thread;
								@Override
								public void run(){
									new logMostPlayed().showMostPlayed(MainActivity, thisClass);
								}}).start();;
						for(TextView tab: tabsFinal){ 
							tab.setBackgroundColor(tint1);
							tab.setTextColor(PRIM_COLOR);
						}
						v.setBackgroundColor(invColor);
						tab_mostplayed.setTextColor(color);
					}
				}); //EO listener



		}//EO if


		return view;
	}//EO getview







	class ViewHolder {
		public TextView songArtist , songTitle;
		public ImageView albumArt;
		public RelativeLayout songGen;
		public ViewHolder(View view){
			songTitle= (TextView)view.findViewById(R.id.songTitle);
			songArtist= (TextView)view.findViewById(R.id.songArtist);
			songGen = (RelativeLayout)view.findViewById(R.id.songGen);
			albumArt = (ImageView) view.findViewById(R.id.albumArt);
		}
	}

	
	
	
	
	






	public void changeColor(int color,int tint1, int tintRes){
		this.color = color;
		this.tintRes = tintRes;
		this.tint1 = tint1; 
		//Toast.makeText(context, " "+color, 388999).show();
	}
	public void changeColor(int color, int invColor){
		this.color = color;
		this.invColor = invColor;
		//Toast.makeText(context, " "+color, 388999).show();
	}

















	@Override
	public void notifyDataSetChanged()
	{
		// TODO: Implement this method
		super.notifyDataSetChanged();

		//click searchBut Again;
		//sVClicked = sVClicked+1;
		//sVClicked();
	}


	public void filtering(String text, ArrayList <song> oG) {
		if(text.isEmpty()){
			dat.clear();
		    dat.addAll(dat_copy);
		} else{
			ArrayList<song> result = new ArrayList<>();
			text = text.toLowerCase();
			for(song item: dat_copy){
				//match by name or phone
				if(item.getSongTitle().toLowerCase().contains(text) || item.getSongArtist().toLowerCase().contains(text)){
					result.add(item);
				}
			}
			dat.clear();
			dat.addAll(result);
		}
		Playerprops.SONG_LIST_CURRENT = dat;
		notifyDataSetChanged();
	}


	public void playlisting(String[] songsinPlaylistsData, String playlistName){
		if(songsinPlaylistsData.length <= 0){
			dat.clear();
		    dat.addAll(dat_copy);
		} else{
			ArrayList<song> result = new ArrayList<>();
			int playlistLen = songsinPlaylistsData.length;
			for(song item: dat_copy){   //type -> each song search through playlist for match  //effic?ncy
				for(int i=1; playlistLen>i; i++){
					songsinPlaylistsData[i] = songsinPlaylistsData[i].toLowerCase();
					if(item.getSongData().toLowerCase().contentEquals( (songsinPlaylistsData[i]) ) ){      result.add(item);   }
				}
			}//EO for
			dat.clear();
			dat.addAll(result);
			currentPlaylist = playlistName.toUpperCase();
		}//EO else

		Playerprops.SONG_LIST_CURRENT = dat;
		notifyDataSetChanged();
	}//EO playlisting


	ArrayList <Integer> mostPlaysTimes = new ArrayList<Integer>();
	public void filter_mostPlayed(ArrayList<String> mostPlays, ArrayList<Integer> mostPlaysTimes  ,String mostPlayedMonth){
		try{
			this.mostPlaysTimes.addAll(mostPlaysTimes);
			int mostPlaysLength = mostPlays.size();
			if(mostPlaysLength <= 0){
				dat.clear();
				dat.addAll(dat_copy);
			} 
			else{
				ArrayList<song> result = new ArrayList<>();
				for(int i=mostPlaysLength-1; i>=0; i--){  //each string in playlist search through all songs
					for(song item: dat_copy){ 
						String mostPlayedAt = mostPlays.get(i); 
						mostPlayedAt = mostPlays.get(i).toLowerCase();
						if(item.getSongData().toLowerCase().contentEquals( mostPlayedAt ) ){      result.add(item);   }
					}//for
				}//EO for
				dat.clear();
				dat.addAll(result);
				currentPlaylist = "MOST PLAYED"+" in "+mostPlayedMonth.toUpperCase();
			}//EO else

		}
		catch(Exception e){	  
			e.printStackTrace();
			dat.clear();
			dat.addAll(dat_copy);
		}
		notifyDataSetChanged();
	}//EO filtermost



	public void resetList(){
		dat.clear();
		dat.addAll(dat_copy);
		notifyDataSetChanged();
		currentPlaylist = "ALL TRACKS";
	}
	
	
	
	






	//controls
	int songImC = 1; 
	int sVClicked = 1;
	public void sVClicked(){
		if(sVClicked%2 ==0){ openSV(); }
		else{ closeSV(); }
		Toast.makeText(MainActivity, "clicked: "+sVClicked, Toast.LENGTH_SHORT).show();
	}
	public void openSV(){
		searchV.setVisibility(View.VISIBLE);
		searchV.setIconifiedByDefault(false);
		searchV.setQueryHint("Search Your Music Library");
		InputMethodManager iMM = ((InputMethodManager)this.MainActivity.getSystemService(this.MainActivity.INPUT_METHOD_SERVICE));
		iMM.showSoftInput(searchV, iMM.SHOW_FORCED);
	}
	public void closeSV(){
		searchV.setQuery("", false);
		searchV.setVisibility(View.GONE);
		((InputMethodManager)this.context.getSystemService(this.MainActivity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.MainActivity.getWindow().getDecorView().getApplicationWindowToken()), 0);
	}
















	class FileOperation extends AsyncTask<Integer, Void, Bitmap> {

		int _params ;
		@Override
		protected Bitmap doInBackground(Integer... params) {
			try{
				_params = params[0];
				song dataH = (song) getItem(params[0]);
				MediaMetadataRetriever mmr= new MediaMetadataRetriever();
				mmr.setDataSource( dataH.getSongData() );
				byte[] dataB = mmr.getEmbeddedPicture();
				Bitmap bmp = BitmapFactory.decodeByteArray(dataB, 0, dataB.length);

				return bmp;
			}
			catch (Exception e){
				//  Toast.mseakeText(context, " "+e, 388999).show();
				Bitmap bmp = BitmapFactory.decodeResource( context.getResources(), tintRes);
				return  bmp;

			} 
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// This is called when we finish

			//View viewHere =  (View)_viewG.getChildAt(_params);
			//viewHere = inflater.inflate(R.layout.song, _viewG);
			aA.get(_params+1).setImageBitmap(result);
		}
		@Override
		protected void onPreExecute() {
			// This is called before we begin
			//Bitmap bmp = BitmapFactory.decodeResource( context.getResources(), tintRes);
			//albumArt.setImageBitmap(bmp);
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			// Unlikely required for this example
		}

	}






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
















}