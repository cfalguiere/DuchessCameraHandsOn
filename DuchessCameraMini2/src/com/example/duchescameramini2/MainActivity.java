package com.example.duchescameramini2;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private Camera mCamera;
	
	private DuchessSprite mDuchessSprite;
	
    private SoundPool mSoundPool;
    private int mShutterSoundId;
    private float mShutterSoundVolume = 0.5f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

    	setupSounds();
    	
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_main);

        mCamera = getCameraInstance();

        CameraPreview cameraPreview = new CameraPreview(this, mCamera);
        FrameLayout previewFrame = (FrameLayout) findViewById(R.id.cameraPreviewFrame);
        previewFrame.addView(cameraPreview);
 
        ViewGroup.LayoutParams previewFrameLayoutParams = computePreviewFrameLayout(previewFrame);
 		previewFrame.setLayoutParams(previewFrameLayoutParams);

		// create duchess and load bitmap
    	mDuchessSprite = new DuchessSprite(
    			BitmapFactory.decodeResource(getResources(), R.drawable.duchessfr_shadow), 
    			previewFrameLayoutParams.width * 1/3, previewFrameLayoutParams.height * 2/3); 
        DrawView drawView = new DrawView(this, mDuchessSprite);
        previewFrame.addView(drawView);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
	}

	@Override
	protected void onResume() {
		super.onResume();
 	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseCamera();
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	        // setup camera with smallest size
			Camera.Parameters parameters = c.getParameters();
			List<Size> size = parameters.getSupportedPreviewSizes();
	        parameters.setPreviewSize(size.get(0).width, size.get(0).height);
			c.setParameters(parameters);
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
    
     private ViewGroup.LayoutParams computePreviewFrameLayout(FrameLayout previewFrame) {
         
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
 		DisplayMetrics metrics = new DisplayMetrics();
 		display.getMetrics(metrics);
 		int screenHeightPixels = metrics.heightPixels;
 		int screenWidthPixels = metrics.widthPixels;

  		Camera.Parameters parameters = mCamera.getParameters();
 		Size photoSize = parameters.getPreviewSize();
 		int photoWidth = photoSize.width;
 		int photoHeight = photoSize.height;
 		float photoRatio = (float)photoHeight/photoWidth;

 		// Now change Frame dimensions to match the scaled image

 		ViewGroup.LayoutParams params = previewFrame.getLayoutParams();		
 		switch (display.getRotation()) {
			case Surface.ROTATION_0: //portrait up
	    		params.width = screenWidthPixels;
	     		params.height = Math.round(screenHeightPixels * photoRatio);        	
 				break;
 			case Surface.ROTATION_90:
	    		params.width = Math.round(screenWidthPixels * photoRatio); ;
	     		params.height = screenHeightPixels;        	
				break;
 			case Surface.ROTATION_180:
	    		params.width = screenWidthPixels;
	     		params.height = Math.round(screenHeightPixels * photoRatio);        	
				break;
 			case Surface.ROTATION_270:
	    		params.width = Math.round(screenWidthPixels * photoRatio); ;
	     		params.height = screenHeightPixels;        	
				break;
 			default:	
 				break;
 		}
 		return params;
    }
     
     public void whenSave(View view) {
         // get an image from the camera
     		PictureWriter mJpegPictureCallback = new PictureWriter(this, mDuchessSprite);
 			Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
 				public void onShutter() {
 					mSoundPool.play(mShutterSoundId, mShutterSoundVolume, mShutterSoundVolume, 1, 0, 1);
 				}
 			};
 			mCamera.takePicture(shutterCallback, null, mJpegPictureCallback);
    }

     private void setupSounds() {
     	mSoundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
     	mShutterSoundId = mSoundPool.load(this, R.raw.camera_click, 1);
   	 
     }
}
