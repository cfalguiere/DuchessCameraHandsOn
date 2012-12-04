package com.example.duchescameramini2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class DuchessSprite extends AbstractSprite {
	private static final String TAG = DuchessSprite.class.getSimpleName();

	private Bitmap bitmap;  // the actual bitmap
	private boolean touched;    // if duchess is touched/picked up
	private float bitmapScale;

	public DuchessSprite(Bitmap bitmap, int x, int y) {
		super(x, y, true);
		this.bitmap = bitmap;
		this.bitmapScale = bitmap.getWidth() / 300;
	}

	public int getActualWidth() {
		return Math.round(bitmap.getWidth() /  bitmapScale);		
	}
	
	public float getBitmapScale() {
		return bitmapScale;
	}


	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}


	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void draw(Canvas canvas) {
		Matrix matrix = new Matrix();
		float s = 1 / bitmapScale; 
		int cw = Math.round(bitmap.getWidth() / 2);
		int ch = Math.round(bitmap.getHeight()  / 2);
		matrix.setScale(s, s, cw, ch);
		matrix.postTranslate(centerX - cw, centerY - ch);
		canvas.drawBitmap(bitmap, matrix, null /*new Paint()*/);		   
	}

	public void handleActionDown(int eventX, int eventY) {
		float w = bitmap.getWidth()  / bitmapScale;
		float h = bitmap.getHeight()  / bitmapScale;
		
		if (eventX >= (centerX - w/2) && (eventX <= (centerX + w/2))) {
			if (eventY >= (centerY - h/2) && (centerY <= (centerY + h/2))) {
				// duchess touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}
//TODO isTouched(x, y)
}
