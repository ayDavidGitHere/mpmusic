package com.aydavid.mpmusic;
import java.util.*;
import android.widget.*;
import android.graphics.*;
import android.content.*;
import java.util.concurrent.*;
import android.os.*;
import java.io.*;
import android.media.*;
import android.graphics.drawable.*;


public class ImageLoader{
		MemoryCache memoryCache = new MemoryCache();
		FileCache fileCache;
		private Map<ImageView, String> imageViews =
		 Collections.synchronizedMap(
			new WeakHashMap<ImageView, String>());
		ExecutorService executorService; 
		Handler handler = new Handler(); 
		//handler to display images in UI thread
		public Boolean BitmapIsFound = false;
		public Bitmap LoadedBitmap = null;
		ImageLoader LoaderInstance = this;
		Context context ;
		
		public ImageLoader(Context _context) {
			context = _context;
			fileCache=new FileCache(context);
			executorService=Executors.newFixedThreadPool(5); // max number of worker threads
		}
		public void DisplayImage(String url, ImageView imageView, int stub) {
		    this.iDisplayImage(url, imageView, stub, false);
		}
		public void DisplayImage(String url, ImageView imageView, int stub, Boolean setWallpaper) {
		    this.iDisplayImage(url, imageView, stub, setWallpaper);
		}
		
		public void iDisplayImage(String url, ImageView imageView, int stub, Boolean setWallpaper) {
			try{
			imageViews.put(imageView, url);
			Bitmap CachedBitmap = memoryCache.get(url);
			if (CachedBitmap != null){ imageView.setImageBitmap(CachedBitmap); LoadedBitmap = CachedBitmap; BitmapIsFound =true;}
			else{		
				Bitmap newBitmap  //= fileCache.getBitmap(url);
							     = getBitmap(url); /*** both works well, Filechache is RAm & rom expensive**/
				if (newBitmap !=null){ 
					memoryCache.put(url, newBitmap); 
					imageView.setImageBitmap(newBitmap);
					if(setWallpaper){try{
					Intent wallpaperIntent = new Intent();
					wallpaperIntent.setAction(Intent.ACTION_SET_WALLPAPER);
					context.startActivity(wallpaperIntent);
					}catch(Exception e){  }}//EO if
					LoadedBitmap = newBitmap;
					BitmapIsFound = true;
				}
				else {
					queuePhoto(url, imageView);
					imageView.setImageResource(stub);
					//LoadedBitmap = stub;;
					BitmapIsFound = false;
				}
			}//EO else
			}catch(Exception e){Toast.makeText(context, ""+e, 300).show(); }
		}
		private void queuePhoto(String url, ImageView imageView) {
			
		}
		private Bitmap getBitmap(String url) {
			Bitmap bmp = null;
			try{
			MediaMetadataRetriever mmr= new MediaMetadataRetriever();
			mmr.setDataSource(url);
			byte[] dataB = mmr.getEmbeddedPicture(); 
			if(dataB !=null){
				BitmapFactory.Options opt=  new BitmapFactory.Options();
				opt.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(dataB, 0, dataB.length, opt);
				opt.inSampleSize = 2;//Math.max( albumArt.getWidth()/opt.outWidth, albumArt.getHeight()/opt.outHeight );
				opt.inJustDecodeBounds = false;
				bmp = BitmapFactory.decodeByteArray(dataB, 0, dataB.length);//, opt);
				return bmp;
			}
			}catch(OutOfMemoryError oME){   oME.printStackTrace();   }
			return bmp;
		}
		private Bitmap decodeFile(File f){
			Bitmap bmp = null;
			return bmp;
		}
		public void clearCache() {
		}
		
		
		
		
		
		
	public class FileCache {
		private File cacheDir;
		public FileCache(Context context) {
			cacheDir = new File(Environment.getExternalStorageDirectory()+File.separator+"albumarts");;//context.getCacheDir();
			if (!cacheDir.exists())cacheDir.mkdirs();
		}
		public void clear(){
			File[] files=cacheDir.listFiles();
			if(files==null) return;
			for(File f:files) f.delete();
		}
		public void putBitmap(String url, Bitmap bitmap){
			try{
			String filename=String.valueOf(url.hashCode());
			File f = new File(cacheDir, filename);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
			byte[] bitmapData = bos.toByteArray();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapData); 
			fos.flush();
			fos.close();
			}catch(Exception e){		Toast.makeText(context, ""+e, 300).show();       }
		}
		public Bitmap getBitmap(String url){
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeFile(cacheDir+File.separator+String.valueOf(url.hashCode()));
			if(bitmap !=null){ return bitmap;}
			else{ bitmap = LoaderInstance.getBitmap(url); }
			if(bitmap !=null ){ this.putBitmap(url, bitmap); }
			return bitmap;
		}
	}
	public class MemoryCache {
		private Map<String, Bitmap> cache = Collections.synchronizedMap(
			new LinkedHashMap<String, Bitmap>(10, 0.75f, true));
		private long size = 0; //current allocated size
		private long limit = 2000000; //max memory in bytes
		public MemoryCache() {
			setLimit(Runtime.getRuntime().maxMemory()/4); // use 25% of available heap size
		}
		public void setLimit(long new_limit) {
			limit = new_limit;
		}
		public Bitmap get(String id) {
			if (!cache.containsKey(id)) return null;
			return cache.get(id);
		}
		public void put(String id, Bitmap bitmap) {
			if (cache.containsKey(id)) size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			maintainSize();
		}
		private void maintainSize() {
			if (size > limit) {
				Iterator<Map.Entry<String, Bitmap>> iter=cache.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry<String, Bitmap> entry=iter.next();
					size-=getSizeInBytes(entry.getValue());
					iter.remove();
					if (size <= limit) break;
				} 
			}
		}
		public void clear() {
			cache.clear();
			size=0;
		}
		long getSizeInBytes(Bitmap bitmap) {
			if (bitmap == null) return 0;
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
		
	}
	
	
	public void StartAsyncFileCaching(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	
	
	
	
	
	
	
		
		
		
	}//EO imageLoaderV2
	