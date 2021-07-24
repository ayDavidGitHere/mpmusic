package com.aydavid.mpmusic;
import android.view.*;
import android.graphics.drawable.*;
import android.graphics.*;
import java.io.*;
import android.content.*;
import java.util.*;
import java.text.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;

public class takeScreenshot
{
	
	
	
	
	public void takeShot(Context context, ArrayList<View> viewsList, String songName, int Colors[]){
		int invColor = Colors[0];
		int color = Colors[1];

		View v = viewsList.get(2);
	    v.setDrawingCacheEnabled(true);

		//Bitmap viewBitmap = v.getDrawingCache();
		Bitmap viewBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
		
		//cropper
		float aspectRatio = viewBitmap.getWidth()/(float) viewBitmap.getHeight();
		int width = viewBitmap.getWidth();	int height = Math.round(width / aspectRatio);
		viewBitmap = Bitmap.createScaledBitmap(viewBitmap, width, height, false);
			
		Canvas viewCanvas = new Canvas(viewBitmap);
		if(true){
			viewCanvas.drawColor(invColor);
			// Draw the view onto the canvas.
			viewCanvas.scale(width/viewBitmap.getWidth(), height/viewBitmap.getHeight());
			v.draw(viewCanvas);
			//Toast.makeText(context, "viewC", 3000).show();
		}
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


		// Write the bitmap generated above into a file.
		String fileStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		OutputStream outputStream = null;
		try{
			File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), songName+"_"+fileStamp +".png");
			outputStream = new FileOutputStream(imgFile);
			outputStream.write(bytes.toByteArray());
			outputStream.close();
			Toast.makeText(context, "Screenshot For "+songName+" Saved To Pictures", 333).show();
		}
		catch(Exception e){
			Toast.makeText(context, ""+e, 333).show();
			e.printStackTrace();
		}
	}//EO takkeshhoot



	
	
	
	
	
	
	
	
	public void takeShot2(Context context, ArrayList<View> viewsList, String songName, int Colors[]){
		int invColor = Colors[0];
		int color = Colors[1];
		
		/* LinearLayout v = new LinearLayout(context);
		for(View view: viewsList){ 
            view.setId(2);
			v.addView(view);
		} */
		View v = viewsList.get(1);
		
		Bitmap viewBitmap = v.getDrawingCache();
		//Bitmap viewBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
		Canvas viewCanvas = new Canvas(viewBitmap);
		Drawable backgroundDrawable = v.getBackground();
		if(backgroundDrawable != null){
			// Draw the background onto the canvas.
			backgroundDrawable.draw(viewCanvas);
		}
		else{
			viewCanvas.drawColor(invColor);
			// Draw the view onto the canvas.
			v.draw(viewCanvas);
		}
		// Write the bitmap generated above into a file.
		String fileStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		OutputStream outputStream = null;
		try{
			File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), songName+"_"+fileStamp +".png");
			outputStream = new FileOutputStream(imgFile);
			viewBitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream);
			outputStream.close();
			Toast.makeText(context, "Screenshot For "+songName+" Taken At "+imgFile.getAbsolutePath(), 333).show();
		}
		catch(Exception e){
			Toast.makeText(context, ""+e, 333).show();
			e.printStackTrace();
		}
		
	}//EO takkeshhoot
	
	
	
	
	
	
	public void takeShot3(Context context, ArrayList<View> viewsList, String songName, int Colors[]){
		int invColor = Colors[0];
		int color = Colors[1];

		View v = viewsList.get(2);
	    v.setDrawingCacheEnabled(true);
		v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				  MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
		//v.layout(0, 0, v.getWidth(), v.getHeight()); 
		Bitmap viewBitmap = Bitmap.createBitmap(v.getDrawingCache());
		 ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		 viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		v.buildDrawingCache();
		
		
		// Write the bitmap generated above into a file.
		String fileStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		OutputStream outputStream = null;
		try{
			File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), songName+"_"+fileStamp +".png");
			outputStream = new FileOutputStream(imgFile);
			outputStream.write(bytes.toByteArray());
			outputStream.close();
			Toast.makeText(context, "Screenshot For "+songName+" Taken At "+imgFile.getAbsolutePath(), 333).show();
		}
		catch(Exception e){
			Toast.makeText(context, ""+e, 333).show();
			e.printStackTrace();
		}
		
		v.destroyDrawingCache();
	}//EO takkeshhoot
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}