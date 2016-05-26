package com.daimajia.slider.demo;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.example.photoeditor.graphics.ImageProcessor;

public class Platform extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ImageProcessor.getInstance().setApplicationCotnext(
				getApplicationContext());
	}
	

	private int color = 0xFFFF0000;
	private int position = 0;
	private String text = "";
	private int textSize = 20;
	private float opacity = 100;
	private float rotation = 0;
	private boolean isBackgroundSet = false;
	private boolean isTextSelected = true;

	private BitmapDrawable old_img;
	private BitmapDrawable new_img;
	
	private int rotationBackground = 0;
	
	private int x=1417;
	private int y=413;
	
	private int imgx=1500;
	private int imgy=309;
	
	
	private Bitmap bm1;
	

	public Bitmap getBm1() {
		return bm1;
	}
	public void setBm1(Bitmap bm1) {
		this.bm1 = bm1;
	}
	public BitmapDrawable getOld_img() {
		return old_img;
	}
	public void setOld_img(BitmapDrawable old_img) {
		this.old_img = old_img;
	}
	public BitmapDrawable getNew_img() {
		return new_img;
	}
	public void setNew_img(BitmapDrawable new_img) {
		this.new_img = new_img;
	}
	public int getImgx() {
		return imgx;
	}
	public void setImgx(int imgx) {
		this.imgx = imgx;
	}
	public int getImgy() {
		return imgy;
	}
	public void setImgy(int imgy) {
		this.imgy = imgy;
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
	
	public int getRotationBackground() {
		return rotationBackground;
	}

	public void setRotationBackground(int rotationBackground) {
		this.rotationBackground = rotationBackground;
	}

	

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRotation() {
		return rotation;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isBackgroundSet() {
		return isBackgroundSet;
	}

	public void setisBackgroundSet(boolean isBackgroundSet) {
		this.isBackgroundSet = isBackgroundSet;
	}

	public boolean getisTextSelected() {
		return isTextSelected;
	}

	public void setisTextSelected(boolean isTextSelected) {
		this.isTextSelected = isTextSelected;
	}
	
	
}
