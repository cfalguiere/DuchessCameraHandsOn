package com.example.duchesscameramini1;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback  {

	private static final String TAG = CameraPreviewView.class.getSimpleName();

	private SurfaceHolder mHolder;
	private Camera mCamera; 


	public static final int MEDIA_TYPE_IMAGE = 1;


	public CameraPreviewView(Context context, AttributeSet attrs) {
		super(context,attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
		//mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}



	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		if (mHolder.getSurface() == null){
			// preview surface does not exist
			return;
		}

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
	    try 
	    {           
	        mCamera.setPreviewDisplay(mHolder);          
	        mCamera.startPreview();
	    }
	    catch(Exception e)
	    {
	        Log.d(TAG, "Cannot start preview", e);    
	    }
	}
	
	public Camera getCamera() {
		return mCamera;
	}

	public void setCamera(Camera mCamera) {
		this.mCamera = mCamera;
	}


}
