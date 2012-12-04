package com.example.duchescameramini2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class DuchessSprite {
	private static final String TAG = DuchessSprite.class.getSimpleName();

	private Bitmap bitmap;  // the actual bitmap
	private int x;          // the X coordinate
	private int y;          // the Y coordinate
	private boolean touched;    // if duchess is touched/picked up
	private float bitmapScale;

	public DuchessSprite(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.bitmapScale = bitmap.getWidth() / 300;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
		matrix.postTranslate(x - cw, y - ch);
		canvas.drawBitmap(bitmap, matrix, null /*new Paint()*/);		   
	}

	public void handleActionDown(int eventX, int eventY) {
		float w = bitmap.getWidth()  / bitmapScale;
		float h = bitmap.getHeight()  / bitmapScale;
		
		if (eventX >= (x - w/2) && (eventX <= (x + w/2))) {
			if (eventY >= (y - h/2) && (y <= (y + h/2))) {
				// duchess touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}

}
