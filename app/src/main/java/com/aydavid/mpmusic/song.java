package com.aydavid.mpmusic;
import java.io.*;
import java.util.*;

public class song implements Serializable
{
	
	private String title;
	private String album;
	private String artist;
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
		
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.songData = songData;
		this.ind = ind;
	};
	
	public String getSongData() {
		return songData;
	}
	public void setSongData(String data) {
		this.songData = data;
	}
	
	public String getSongTitle() {
		title = removeTag(title);
		return title;
		//return title.replaceAll(  "(?i)"+repl1, "").replaceAll( "(?i)"+ repl2, "").replaceAll(  "(?i)"+  repl3,"").replace("|","");
	}
	public void setSongTitle(String title) {
		this.title = title;
	}
	public String getSongAlbum() {
		album = removeTag(album);
		return album;
		//return album.replaceAll(  "(?i)"+repl1, "").replaceAll( "(?i)"+repl2, "").replaceAll(  "(?i)"+   repl3,"").replace("|","");
	}
	public void setSongAlbum(String album) {
		this.album = album;
	}
	public String getSongArtist() {
		artist = removePart( removeTag(artist), oldChar1);
		return artist;
		//return artist.replaceAll( "(?i)"+ repl1, "").replaceAll( "(?i)"+ repl2, "").replaceAll( "(?i)"+   repl3,"").replace("|","");
	}
	public void setSongArtist(String artist) {
		this.artist = artist;
	}
	public int getSongInd(){
		return ind;
	}
	public void setSongInd(int ind){
		this.ind = ind;
	}
	
	
	
	
	
	
	
	
	
	
	
	 String removeTag(String wholeString){
		bannedTags = new ArrayList<>(Arrays.asList(repl1, repl2, repl3, repl4, repl5, repl6, repl7));
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
		return wholeStringNew;
	}//EO removeTag
	
	
	String removePart(String wholeString, String oldChar){
		String wholeStringNew = wholeString.replaceAll("(?i)"+oldChar,  "");
		return wholeStringNew;
	}//EO removePart
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}