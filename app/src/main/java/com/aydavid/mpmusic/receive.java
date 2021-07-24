package com.aydavid.mpmusic;
import android.content.*;
import android.widget.*;

public class receive  extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) {
	    Toast.makeText(context, ("rt"), 2290).show();
		
if ( intent.getAction().equals("NEXT_IT")) {  Toast.makeText(context, ("33345"), 2290).show();  //context.goToNext();   
		                        }
	}
	
}