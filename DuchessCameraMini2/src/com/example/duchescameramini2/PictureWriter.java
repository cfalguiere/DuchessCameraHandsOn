package com.example.duchescameramini2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class PictureWriter implements PictureCallback {
	private static final String TAG = PictureWriter.class.getSimpleName();

	private Activity mActivity;
	private DuchessSprite mDuchessSprite;
	
	public PictureWriter(Activity activity, DuchessSprite sprite) {
		mActivity = activity;
		mDuchessSprite = sprite;
	}
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera)  {
		try {
			Bitmap photo = BitmapFactory.decodeByteArray(data, 0,data.length);

			// Create a new, empty bitmap with the original size.
			Bitmap bitmap = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), 
					photo.getConfig());

			final Canvas canvas = new Canvas(bitmap);

			// draw the original image to the canvas
			canvas.drawBitmap(photo, 0, 0, null);	    	   

			// draw duchess
			float ratio = (float) photo.getHeight() / getScreenHeight();
			mDuchessSprite.draw(canvas, ratio);

			// save file
			File pictureFile = getOutputMediaFile();
			writeBitmapToFile(bitmap, pictureFile);
			addPictureToGalery(pictureFile.getPath());
		
			Log.d(TAG, "picture taken");
		} catch (Exception e) {
			Log.d(TAG, "ERROR Could not save picture: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void writeBitmapToFile(Bitmap bitmap, File pictureFile) throws FileNotFoundException, IOException {
		Log.d(TAG, "saving bitmap to file " + pictureFile);
		FileOutputStream fos = new FileOutputStream(pictureFile);		
		try {
			if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)) {
				String message = String.format("Could not write bitmap to file %1" + pictureFile.getPath());
				throw new IOException(message);
			}
		}
		finally {
			fos.flush();
			fos.close();					
		}
		
	}
	
	/** Create a File for saving the image */
	private static File getOutputMediaFile() throws IOException {
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), "DuchessCamera");
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				String message = String.format("Error creating media directory %1, check storage permissions" + mediaStorageDir);
				throw new IOException(message);
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
		File mediaFile;
		String fileName = String.format("%1$s%2$sIMG_%3$s.jpg", mediaStorageDir.getPath(), File.separator, timeStamp);
		mediaFile = new File(fileName);
		return mediaFile;
	}

	private void addPictureToGalery(String path) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		mActivity.sendBroadcast(mediaScanIntent);
	}

	private float getScreenHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE))
		  .getDefaultDisplay().getMetrics(metrics);
		return (float) metrics.heightPixels;
	}
}
