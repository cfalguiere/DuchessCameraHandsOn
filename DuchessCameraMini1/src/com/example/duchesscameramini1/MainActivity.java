package com.example.duchesscameramini1;


import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {

	CameraPreviewView mCameraPreviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

		Camera camera = Camera.open();
    	
		mCameraPreviewView = (CameraPreviewView) findViewById(R.id.cameraPreviewView);
		mCameraPreviewView.setCamera(camera);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
