package com.example.duchescamera;


import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = CameraPreview.class.getSimpleName();

	private SurfaceHolder mHolder;
    private Camera mCamera;
	boolean mIsPreviewRunning = false;


    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.

    	// A vous de coder
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.

    	// A vous de coder
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // A vous de coder
        // arreter la preview
        // rectifier les parametres
        // reprendre la preview
        
    }
    
 	private void stopPreview()
 	{        
 		if (mCamera == null) return;
 		
        try {
            mCamera.stopPreview();
            mIsPreviewRunning = false;
        } catch (Exception e){
 			Log.d(TAG, "Warning trying to stop a preview: " + e.getMessage());
       }
 	}

 	private void startPreview()
 	{        
 		if (mCamera == null || mIsPreviewRunning) return;

 		try 
 		{           
 			mCamera.setPreviewDisplay(mHolder);          
 			mCamera.startPreview();
 			mIsPreviewRunning = true;
 		}
 		catch(Exception e)
 		{
 			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
 		}
 	}
    
 	private void fixRotation(Camera.Parameters parameters) {
        Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		//List<Size> size = parameters.getSupportedPreviewSizes();
        //int w = size.get(0).width;
        //int h = size.get(0).height;

        if(display.getRotation() == Surface.ROTATION_0)
        {
            //parameters.setPreviewSize(h, w);                           
            mCamera.setDisplayOrientation(90);
        }

        if(display.getRotation() == Surface.ROTATION_90)
        {
            //parameters.setPreviewSize(w, h);                           
        }

        if(display.getRotation() == Surface.ROTATION_180)
        {
            //parameters.setPreviewSize(h, w);               
        }

        if(display.getRotation() == Surface.ROTATION_270)
        {
            //parameters.setPreviewSize(w, h);
            mCamera.setDisplayOrientation(180);
        } 
 		
 	}
}