package com.example.duchescamera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DragFeedbackSprite extends AbstractSprite {

	private float radius;

	public DragFeedbackSprite(int x, int y, float radius) {
		super(x, y, false);
		this.radius = radius;
	}
	
	public void draw(Canvas canvas) {
		Paint dragPaint = new Paint();
		dragPaint = new Paint();
		dragPaint.setColor(Color.GREEN);
		dragPaint.setAlpha(128);
		canvas.drawCircle(centerX, centerY, radius, dragPaint);
	}

	
}
