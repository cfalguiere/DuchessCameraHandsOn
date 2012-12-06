package com.example.duchescamera;

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

		// A vous de coder
	}
	

	@Override
	public void onDraw(Canvas canvas) {
		// A vous de coder
	}
	
	


    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	// A vous de coder
    	
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
