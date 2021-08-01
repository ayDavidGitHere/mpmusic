package com.aydavid.mpmusic;

import java.util.*;
import android.widget.*;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.app.*;


public class BufferSongList
{	
	Context context;
	public BufferSongList(Context _context){
		context = _context;
	}
	public void load(){
		ArrayList<song> songList = new ArrayList<song>();
		int songCount = 0; 
		try{
			ContentResolver musicResolver = context.getContentResolver();
			Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			String[] project = {android.provider.MediaStore.Audio.Media._ID+" "};
			String order = android.provider.MediaStore.Audio.Media.TITLE;
			String order2 = android.provider.MediaStore.Audio.Media.DATE_MODIFIED+" DESC";
			Cursor musicCursor = musicResolver.query(musicUri, null, null, null, order2);
			if(musicCursor!=null && musicCursor.moveToFirst()){//get columns
				int titleColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media.TITLE);
				int idColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media._ID);
				int artistColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media.ARTIST);
				int albumColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media.ALBUM);
				int dataColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media.DATA);//add songs to list
				do {
					int thisId = (int) musicCursor.getLong(idColumn);
					String thisTitle = musicCursor.getString(titleColumn);
					String thisArtist = musicCursor.getString(artistColumn);
					String thisAlbum = musicCursor.getString(albumColumn);
					String thisData = musicCursor.getString(dataColumn);
					songList.add(new song(thisData, thisTitle, thisArtist, thisAlbum, songCount));
					songCount = songCount+1;
				}
				while (musicCursor.moveToNext());
			}

			Playerprops.SONG_LIST_ALL = songList;
			Playerprops.SONG_LIST_CURRENT.addAll(songList);
		}
		catch(Exception e){  //Toast.makeText(this, " "+e+" ",200000000).show();
		}
	}//EO songlist
	
	
	
	
	
	

}