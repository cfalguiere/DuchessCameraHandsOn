package com.example.duchescameramini2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DrawView extends ImageView {

    private static final String TAG = DrawView.class.getSimpleName();

	public DuchessSprite mDuchess;
	public DragFeedbackSprite mDragFeedbackSprite;
	
	public enum Action {
	    NONE, ONGOING, DRAG
	};
	private Action mode = Action.NONE;


	public DrawView(Context context, DuchessSprite duchess) {
		super(context);
		mDuchess = duchess;
		int radius = mDuchess.getActualWidth() / 4;
		mDragFeedbackSprite = new DragFeedbackSprite(mDuchess.getCenterX(), mDuchess.getCenterY(), radius);
		
        // make the Panel focusable so it can handle events
        setFocusable(true);
        setFocusableInTouchMode(true);
   	}
	

	@Override
	public void onDraw(Canvas canvas) {

		mDuchess.draw(canvas);
		if (mDragFeedbackSprite.isVisible()) mDragFeedbackSprite.draw(canvas);
	}
	
	


    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	final int action = event.getAction();

    	switch (action & MotionEvent.ACTION_MASK) {
    		case MotionEvent.ACTION_DOWN: {
                // delegating event handling to the duchess
                mDuchess.handleActionDown((int)event.getX(), (int)event.getY()); // revoir pour deporter juste la detection du touch
            	mode = Action.ONGOING;
            	Log.d(TAG,"down");
            	break;
    	    }
    	    case MotionEvent.ACTION_MOVE: {
    	    	if (mDuchess.isTouched()) {
    	    		mode = Action.DRAG;
    	    		// the duchess was picked up and is being dragged
    	    		mDuchess.setCenterX((int)event.getX());
    	    		mDuchess.setCenterY((int)event.getY());
    	    		mDragFeedbackSprite.setVisible(true);
    	    		mDragFeedbackSprite.setCenter(mDuchess);
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
    	    		mDragFeedbackSprite.setVisible(false);
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
