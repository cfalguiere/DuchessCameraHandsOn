package com.example.duchescameramini2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.WindowManager;
import android.widget.ImageView;

public class DrawView extends ImageView {

    private static final String TAG = DrawView.class.getSimpleName();

	public DuchessSprite mDuchess;
	
	public enum Action {
	    NONE, ONGOING, DRAG
	};
	private Action mode = Action.NONE;


	public DrawView(Context context, DuchessSprite duchess) {
		super(context);
		mDuchess = duchess;
		
        // make the Panel focusable so it can handle events
        setFocusable(true);
        setFocusableInTouchMode(true);
   	}
	

	@Override
	public void onDraw(Canvas canvas) {

		mDuchess.draw(canvas);
		
		switch(mode) {
		case DRAG :
			drawDragFeedback(canvas);
			break;
		default :
			break;
		}
		

	}
	
	private void drawDragFeedback(Canvas canvas) {
		Paint dragPaint = new Paint();
		dragPaint = new Paint();
		dragPaint.setColor(Color.GREEN);
		dragPaint.setAlpha(128);
		int radius = Math.round(mDuchess.getBitmap().getWidth() / mDuchess.getBitmapScale() / 4);
		canvas.drawCircle(mDuchess.getX(), mDuchess.getY(), 
				radius, dragPaint);
	}
	

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	final int action = event.getAction();

    	switch (action & MotionEvent.ACTION_MASK) {
    		case MotionEvent.ACTION_DOWN: {
                // delegating event handling to the duchess
                mDuchess.handleActionDown((int)event.getX(), (int)event.getY());
            	mode = Action.ONGOING;
            	Log.d(TAG,"down");
            	break;
    	    }
    	    case MotionEvent.ACTION_MOVE: {
    	    	if (mDuchess.isTouched()) {
    	    		mode = Action.DRAG;
    	    		// the duchess was picked up and is being dragged
    	    		mDuchess.setX((int)event.getX());
    	    		mDuchess.setY((int)event.getY());
    	    		invalidate();
    	    		Log.d(TAG,"move");
    	    	} 
    	    	break;
    	    }
    	    case MotionEvent.ACTION_UP: {
    	    	Log.d(TAG,"up");
    	    	if (mode != Action.NONE) {
    	    		invalidate();
    	    	}
    	    	mode = Action.NONE;
    	    	// touch was released
    	    	if (mDuchess.isTouched()) {
    	    		mDuchess.setTouched(false);
    	    	}
    	    	break;
    	    }   
        }
        return true;
    }

    // accessors and mutators
    
	public Action getMode() {
		return mode;
	}

	public void setMode(Action mode) {
		this.mode = mode;
	}

}
