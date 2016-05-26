package com.daimajia.slider.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.photoeditor.graphics.ImageProcessor;
import com.example.photoeditor.utils.ImageScannerAdapter;
import com.example.photoeditor.utils.SaveToStorageUtil;

import java.io.File;

public class Display_image extends Activity  {
	ImageView finalimage;
	ImageView save,share,back;
	private String savedImagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_share_image);

		finalimage =(ImageView)findViewById(R.id.img);
		save =(ImageView)findViewById(R.id.save_button);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveButtonClicked() ;
			}
		});
		share =(ImageView)findViewById(R.id.share_button);
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedButtonClicked();
			}
		});
		back =(ImageView)findViewById(R.id.del_button);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		//Bundle bundle = getIntent().getExtras();
		Bitmap savedImagePath=ImageProcessor.getInstance().getBitmap();
		finalimage.setImageBitmap(savedImagePath);


	}
	
	private void saveButtonClicked() {
		saveImage();
		Toast.makeText(this, R.string.photo_saved_info, Toast.LENGTH_LONG).show();
	}

	private void saveImage() {
		savedImagePath = SaveToStorageUtil.save(ImageProcessor.getInstance().getBitmap(), this);
		ImageScannerAdapter adapter = new ImageScannerAdapter(this);
		adapter.scanImage(savedImagePath);
		ImageProcessor.getInstance().resetModificationFlag();
	}
	
	private boolean imageIsAlreadySaved() {
		return savedImagePath != null && !savedImagePath.equals("");
	}

	private void sharedButtonClicked() {
		if (!imageIsAlreadySaved() || ImageProcessor.getInstance().isModified()) {
			saveImage();
		}
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/*");
		share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(savedImagePath)));
		startActivity(Intent.createChooser(share, "Share via"));
	}

	@Override
	protected void onResume() {
		Bitmap savedImagePath=ImageProcessor.getInstance().getBitmap();
		finalimage.setImageBitmap(savedImagePath);
		super.onResume();
	}
}
