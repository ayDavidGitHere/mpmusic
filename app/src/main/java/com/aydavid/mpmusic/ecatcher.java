package com.aydavid.mpmusic;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.*;
import android.view.View.*;
import java.io.*;
import android.app.*;
/**
 * Application class writing unexpected exceptions to a crash file before crashing.
 */
public class ecatcher extends Application {
	private static final String TAG = "ExceptionHandler";
	@Override
	public void onCreate() { 
		super.onCreate();
		// Setup handler for uncaught exceptions.
		final Thread.UncaughtExceptionHandler defaultHandler =
			Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable e) {
					try {
						handleUncaughtException(e);
						System.exit(1); 
					} catch (Throwable e2) {  
						Log.e(TAG, "Exception in custom exception handler", e2);
						defaultHandler.uncaughtException(thread, e);
					}
				}
			});
	}
	private void handleUncaughtException(Throwable e) throws IOException {
		Log.e(TAG, "Uncaught exception logged to local file", e);
		// Create a new unique file
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
		String timestamp;
		File file = null;
		while (file == null || file.exists()) {
			timestamp = dateFormat.format(new Date());
			file = new File(getExternalFilesDir(null), "all_crashLog_" + timestamp + ".txt");
		}
		Log.i(TAG, "Trying to create log file " + file.getPath());
		file.createNewFile();
		// Write the stacktrace to the file
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			for (StackTraceElement element : e.getStackTrace()) {
				writer.write(element.toString());
			}
			writer.write("e: "+e+";");
		} finally {
			if (writer != null) writer.close();
		}
		// You can (and probably should) also display a dialog to notify the user
	}
}