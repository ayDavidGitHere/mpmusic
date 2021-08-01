package com.aydavid.mpmusic;
import android.content.*;
import java.io.*;
import android.widget.*;
import android.view.*;
import android.app.*;
import java.util.*;
import android.media.*;
import android.util.*;
import android.os.*;
import android.app.*;
import android.graphics.*;
import android.view.ViewGroup.*;
import android.text.*;
import android.text.style.*;


public class playlistDialog
{
	
	 
	    Context context; 
		
	public void playlistDialog(final Context context, final int pos, ArrayList<song> songListArg, int dialogColor){

		
		this.context = context;
		final ArrayList<song> songList = songListArg;
		
		
		try{
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.playlist_dialog, null);
			LinearLayout newView = new LinearLayout(context);
			newView.setOrientation(LinearLayout.VERTICAL);
			newView.setGravity(Gravity.CENTER_HORIZONTAL);
			newView.addView(promptsView);
			MarginLayoutParams mlp = (MarginLayoutParams) promptsView.getLayoutParams();
			mlp.bottomMargin = 7; mlp.topMargin = 6;
			mlp.leftMargin = 4;
			
			try {
				
				final File path = new File(context.getExternalFilesDir(null)+"/Playlists");
				if(path.exists()){ 
						final File[] fList = path.listFiles();
						final String[] arrayOfContent = new String[fList.length];
					for(int i=0; i<fList.length; i++){
						
						final String songData= ""+songList.get(pos).getSongData();
						final String playlistName = ""+fList[i].toString().replace(context.getExternalFilesDir(null)+"/Playlists/", "" ).replace(".mpm","");
						arrayOfContent[i] = "";
						TextView playListText = new TextView(context);
						playListText.setId(i);
						playListText.setTextSize(15);
						playListText.setTextColor(Color.parseColor("#dc1437"));
						playListText.setText(playlistName.toUpperCase());
						playListText.setLayoutParams(mlp);
						newView.addView(playListText);
						

						FileInputStream fileInputStream2;
						fileInputStream2 = new FileInputStream(path+"/"+playlistName+".mpm") ;
						int read; 
						while(  (read= fileInputStream2.read())!=-1  ){
							arrayOfContent[i]= arrayOfContent[i]+(char) read;   //toaster(cont+" "+read);
						}
						fileInputStream2.close();


						playListText.setOnClickListener(new View.OnClickListener(){
								@Override
								public void onClick(View v){
									try{
										int thisClicked = v.getId();
										String thisPlaylistName = ""+fList[thisClicked].toString().replace(context.getExternalFilesDir(null)+"/Playlists/", "" ).replace(".mpm","");
										FileOutputStream fileOutputStream2;
										fileOutputStream2 = new FileOutputStream(path+"/"+thisPlaylistName+".mpm");
										fileOutputStream2.write( (arrayOfContent[thisClicked]+"%"+songData).getBytes());
										fileOutputStream2.close();
										toaster(" '"+songList.get(pos).getSongTitle().toUpperCase()+"'  is added to  '"+playlistName.toUpperCase()+"'");
									}
									catch(Exception e){toaster(""+e); }
								}
				            });// end oflistener
					}//end of ---for
				}

			} catch (Exception e) {  e.printStackTrace();   }



			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setView(newView);
			final LinearLayout containAll = (LinearLayout) promptsView.findViewById(R.id.containAll);
			final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);
			final TextView textFromInflate = (TextView) promptsView.findViewById(R.id.textView1);
			containAll.setBackgroundColor(dialogColor);
			textFromInflate.setTextColor(Color.parseColor("#dc1437"));
			newView.setBackgroundColor(dialogColor);
			
			textFromInflate.setText("Create New Playlist And Add "+" "+"'"+songList.get(pos).getSongTitle().toUpperCase()+"' ");
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Add To New Playlist", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					    String playlistName = userInput.getText().toString();
						String songData= ""+songList.get(pos).getSongData();
						FileOutputStream fileOutputStream;

						try {
							File path = new File(context.getExternalFilesDir(null)+"/Playlists");
							if(!path.exists()){  if(path.mkdir()){   } }
							fileOutputStream = new FileOutputStream(path+"/"+playlistName+".mpm");
							fileOutputStream.write(("%"+songData).getBytes());
							fileOutputStream.close();
							toaster("'"+playlistName+"' created and '"+songList.get(pos).getSongTitle()+"' is added.");
						} catch (Exception e) {  e.printStackTrace();   }
					}
				})
				.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});



			AlertDialog alertDialog;
		    alertDialog = alertDialogBuilder.create();
			alertDialog.show(); 
		}
		catch(Exception e){  toaster(e+"");}

	}//EO playlistDialog
	
	
	
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void showPlaylists(final Context context, final oCA fromOca, final View headerView, LinearLayout setTo){

		try {
			
			

			final File path = new File(context.getExternalFilesDir(null)+"/Playlists");
			if(path.exists()){ 
				final File[] fList = path.listFiles();
				final String[] arr_playlistsNames = new String[fList.length];
				
				TextView p1 = (TextView) headerView.findViewById(R.id.playlist1);
				p1.setText("");
				MarginLayoutParams mlp = (MarginLayoutParams) p1.getLayoutParams();
				mlp.bottomMargin = 10; mlp.topMargin = 10;
				setTo.removeAllViews();
				p1.setOnClickListener(new View.OnClickListener(){
						@Override
						public void onClick(View v){
							try{    
							     
							}catch(Exception e){ toaster(""+e); }
						}//EO onclick
							
				});
				
				
				
				
				for(int i=0; i<fList.length; i++){
					//toaster(""+i);
					arr_playlistsNames[i] = ""+fList[i].toString().replace(context.getExternalFilesDir(null)+"/Playlists/", "" ).replace(".mpm","");
					final TextView playListText = new TextView(context);
					android.view.ViewGroup.LayoutParams playlistLayParams =  p1.getLayoutParams();
					playListText.setLayoutParams(playlistLayParams);
					playListText.setId(i); 
					playListText.setTextSize(20);
					SpannableString text_PlaylistText= new SpannableString( "M. "+arr_playlistsNames[i].toUpperCase() );
					//text_PlaylistText.setSpan( new ForegroundColorSpan(Color.WHITE), 0, 1, 0);
					text_PlaylistText.setSpan( new RelativeSizeSpan(1.4f), 0, 1, 0);
					playListText.setText(  text_PlaylistText );
					playListText.setTextColor(Color.parseColor("#dc1437"));
					playListText.setLayoutParams(mlp);
				    setTo.addView(playListText);
				
					
					


					playListText.setOnClickListener(new View.OnClickListener(){
						@Override
						public void onClick(View v){
							try{    
								String playlistName = arr_playlistsNames[playListText.getId()];
								FileInputStream fileInputStream2;
								fileInputStream2 = new FileInputStream(path+"/"+playlistName+".mpm") ;
								int read;
								String fileContent= "";
								while(  (read= fileInputStream2.read())!=-1  ){
									fileContent= fileContent+(char) read;   
								}
								fileInputStream2.close();
								String[] contOfFile = fileContent.split("%");
							    fromOca.playlisting(contOfFile, playlistName);
							 }
							catch(Exception e){toaster(""+e); }
						}
					});// end oflistener
					
				}//end of ---for
			}

		} catch (Exception e) {  e.printStackTrace(); toaster("could not buffer playlists");   }
		
		
		
	}

	
	
	

	
	
	
	
	
	
	
	
	public void toaster( String string){
		Toast.makeText(context, string, 3000).show();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}