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


public class CustomAdapter extends ArrayAdapter<song> {
	private LayoutInflater inflater;
	ArrayList<song> dat;
	ArrayList<song> dat2;
	Context context;
	song data;
	int color, tintRes;
	
	public CustomAdapter (Context context, ArrayList<song> data){
         
		super(context, 0, data);
		this.context = context;
		this.dat = data;  
		dat2 = new ArrayList <song>(data);
		inflater = LayoutInflater.from(context);
	
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent)
	{

		data = (song) getItem(position);
		
		
		ViewHolder viewHolder;
  		ViewHolderHeader vhh;
		
		

						if (position !=10){
							
							
							
							
		if (view == null) {
			view = inflater.inflate(R.layout.song, null);
			viewHolder = new ViewHolder(view);
			//view.setTag(viewHolder); 
		}
		else { viewHolder = (ViewHolder) view.getTag(); 
			}
			
			
		
		 
		 
			int limit = 100;
			StringBuilder sT = new StringBuilder(data.getSongTitle());
			if(data.getSongTitle().length()> limit){ sT.delete( limit, data.getSongTitle().length() );     }
		viewHolder.songTitle.setText(sT);
		viewHolder.songArtist.setText(position+"|  "+data.getSongArtist());
		viewHolder.songTitle.setTextSize(14);
		viewHolder.songArtist.setTextSize(10);
		viewHolder.songTitle.setTextColor(color);
	    viewHolder.songArtist.setTextColor(color);
        viewHolder.albumArt.setImageResource(tintRes);
      
		
      
      
     try{
		MediaMetadataRetriever mmr= new MediaMetadataRetriever();
		mmr.setDataSource(data.getSongData());
		byte[] dataB = mmr.getEmbeddedPicture();
		Bitmap bmp = BitmapFactory.decodeByteArray(dataB, 0, dataB.length);
		
	    viewHolder.albumArt.setImageBitmap(bmp);
       }
	   catch (Exception e){
		 //  Toast.makeText(context, " "+e, 388999).show();
	   } 
	   
	   
	   
	   
	   
	   
	  						    }
								
								
		else{ 
						if (view == null) {
				view = inflater.inflate(R.layout.header, null);
				vhh = new ViewHolderHeader(view);
				//view.setTag(vhh);
									}
				else {  //vhh = (ViewHolderHeader) view.getTag(); 
				     }
			}
		      
				

		return view;

	}
	
	
	
	
	
	public void changeColor(int color, int tintRes){
	   this.color = color;
	   this.tintRes = tintRes;
     //Toast.makeText(context, " "+color, 388999).show();
	}
	public void changeColor(int color){
		this.color = color;
         //Toast.makeText(context, " "+color, 388999).show();
	}
	
	
	
	
	
	
	
	
	
	public void filtering(String text, ArrayList <song> oG) {
		if(text.isEmpty()){
			dat.clear();
		    dat.addAll(dat2);
		} else{
			ArrayList<song> result = new ArrayList<>();
			text = text.toLowerCase();
			for(song item: dat2){
				//match by name or phone
				if(item.getSongTitle().toLowerCase().contains(text) || item.getSongArtist().toLowerCase().contains(text)){
					result.add(item);
				}
			}
			dat.clear();
			dat.addAll(result);
		}
		notifyDataSetChanged();
	}
	
	
	
	
	
	
	private class ViewHolder
	{
		TextView songArtist , songTitle;
		public ImageView albumArt;
		RelativeLayout songGen;
		
		private ViewHolder(View view)
		{
			songTitle= (TextView)view.findViewById(R.id.songTitle);
			songArtist= (TextView)view.findViewById(R.id.songArtist);
			songGen = (RelativeLayout)view.findViewById(R.id.songGen);
			albumArt = (ImageView) view.findViewById(R.id.albumArt);
		}
	}
	
	
	
	
	private class ViewHolderHeader
				{
			LinearLayout header;
		private ViewHolderHeader(View view)
		{
			header = (LinearLayout) view.findViewById(R.id.header);
		}
	}
	

	
	
	
	
	
	
	
	
}