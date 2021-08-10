package com.aydavid.mpmusic;
import java.io.*;
import java.util.*;

public class song implements Serializable
{
	
	private String title, title_Corrected;
	private String album, album_Corrected;
	private String artist,  artist_Corrected;
	private String songData;
	private int ind;
	private String repl1 = ".net";
	private String repl2 = ".org";
	private String repl3 = "www.";
	private String repl4 = ".com";
	private String repl5 = "Official";
	private String repl6 = "Video";
	private String repl7 = "Audio"; 
	//public String[] bannedTags = {repl1, repl2, repl3, repl4, repl5, repl6, repl7};
	private List<String> bannedTags;
	private String oldChar1 = "Vevo";

	public song(String songData, String title, String artist, String album, int ind  ) {
		/*
		this.title = title; this.title_Corrected = title;
		this.album = album; this.album_Corrected = album;
		this.artist = artist; this.artist_Corrected = artist;*/
		this.songData = songData;
		this.ind = ind;
		bannedTags = new ArrayList<>(Arrays.asList(repl1, repl2, repl3, repl4, repl5, repl6, repl7, "|"));
		setSongTitle(title); setSongAlbum(album); setSongArtist(artist);
	};
	
	public String getSongData() {
		return songData;
	}
	public void setSongData(String data) {
		this.songData = data;
	}
	
	public String getSongTitle() {
		return title_Corrected;
		//return title.replaceAll(  "(?i)"+repl1, "").replaceAll( "(?i)"+ repl2, "").replaceAll(  "(?i)"+  repl3,"").replace("|","");
	}
	public void setSongTitle(String title) {
		this.title = title;
		this.title_Corrected = removeTag(title);
	}
	public String getSongAlbum() {
		return album_Corrected;
		//return album.replaceAll(  "(?i)"+repl1, "").replaceAll( "(?i)"+repl2, "").replaceAll(  "(?i)"+   repl3,"").replace("|","");
	}
	public void setSongAlbum(String album) {
		this.album = album;
		this.album_Corrected = removeTag(album);
	}
	public String getSongArtist() {
		return artist_Corrected;
		//return artist.replaceAll( "(?i)"+ repl1, "").replaceAll( "(?i)"+ repl2, "").replaceAll( "(?i)"+   repl3,"").replace("|","");
	}
	public void setSongArtist(String artist) {
		this.artist = artist;
		this.artist_Corrected = removePart( removeTag(artist), oldChar1);
	}
	public int getSongInd(){
		return ind;
	}
	public void setSongInd(int ind){
		this.ind = ind;
	}
	
	
	
	
	
	
	
	
	
	
	
	 String removeTag(String wholeString){
		String [] words = wholeString.split(" ", -1);
		String wholeStringNew = "";
		for(int ind=0; words.length>ind; ind++){
			Boolean foundTag = false;
			innerLoop:
			for(int indBT=0; bannedTags.size()>indBT; indBT++){
				if(words[ind].toLowerCase().contains(bannedTags.get(indBT).toLowerCase()) ){
					foundTag = true;
					break innerLoop;
				}
				else{
					foundTag = false;
				}
			}//EO for
			if(foundTag){	}else{  wholeStringNew += ""+words[ind]+" "; }
		}//EO for
		return wholeStringNew.trim();
	}//EO removeTag
	
	
	String removePart(String wholeString, String oldChar){
		String wholeStringNew = wholeString.replaceAll("(?i)"+oldChar,  "");
		return wholeStringNew.trim();
	}//EO removePart
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}