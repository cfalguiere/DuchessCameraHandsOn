package com.example.duchescameramini2;

public abstract class AbstractSprite {
	protected int centerX;          // the X coordinate
	protected int centerY;          // the Y coordinate
	protected boolean visible = false;

	public AbstractSprite(int centerX, int centerY, boolean visible){
		this.centerX = centerX;
		this.centerY = centerY;
		this.visible = visible;
	}
	
	public void setCenter(AbstractSprite sprite) {
		centerX = sprite.centerX;
		centerY = sprite.centerY;
	}
	
	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
