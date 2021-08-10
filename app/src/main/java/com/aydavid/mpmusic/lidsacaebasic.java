package com.aydavid.mpmusic;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.view.Window.*;
import java.util.function.*;

public class lidsacaebasic
{
	
	
	public void addEvent(View view, Context context, Void callmeback){
	
	view.setOnClickListener(new View.OnClickListener(){
	@Override
	public void  onClick(View v){
         // callmeback(context, v);
	}
	});
	}
	
	
	
	public void toastIt(Context context, String string){
		Toast.makeText(context, ""+string, Toast.LENGTH_SHORT).show();
	}
	
}