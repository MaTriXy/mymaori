package com.daimajia.slider.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.photoeditor.editoractivity.FilterActivity;
import com.example.photoeditor.graphics.ImageProcessor;
import com.example.photoeditor.utils.BitmapScalingUtil;
import com.myandroid.views.MultiTouchListener;
import com.myandroid.views.myView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;


public class EditorActivity extends Activity implements OnClickListener {

    private static final int EDITOR_FUNCTION = 1;

    EditText edText;
    Spinner mSpinner_text_style;
    private ImageView imageView;
    AutoResizeTextView mTv_text;
    Platform global;
    String imageUri;
    // Top bar buttons
    private ImageButton brightnessButton;
    private ImageButton cropButton;
    private ImageButton rotateButton;
    private ImageButton filtersButton;

    // Bottom bar buttons
    private ImageButton backButton;
    private ImageButton shareButton;
    private ImageButton saveButton, textButton, emojiButton;

    private FrameLayout rr_1;
    RelativeLayout mRelativeLayoutMain;

    private String savedImagePath;
    private String Selected_tatoo;
    protected boolean AdLoaded = false;
    private int adsCounter = 0;
    boolean status;
    Boolean isImgAdded = false;
    Bitmap mBitmapMain = null;
    File file;
    ImageButton tattos, text, rotate,crop,filter,done;
    ImageButton mIbtn_color_text;
    String style[] = {"1.ttf", "2.ttf", "3.ttf", "4.ttf", "5.ttf", "6.ttf", "7.ttf", "8.ttf"};
    ArrayList<myView> imageViewArray = new ArrayList<myView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);

         mTv_text = new AutoResizeTextView(getApplicationContext());
         global=((Platform)getApplication());
        initComponents();
        initImageView();
        //    String adsID = getResources().getString(R.string.admob_banner);
//        if (adsID != null && !adsID.isEmpty()) {
//            loadAds();
//        }

        rr_1 = (FrameLayout) findViewById(R.id.rr_1);
        rr_1.addView(mTv_text);
    }

    private void initComponents() {

        mRelativeLayoutMain = (RelativeLayout) findViewById(R.id.capture_id_rl);
        tattos = (ImageButton) findViewById(R.id.tatto);
        tattos.setOnClickListener(this);

        text = (ImageButton) findViewById(R.id.text_button);
        text.setOnClickListener(this);

        rotate = (ImageButton) findViewById(R.id.rotate_button);
        rotate.setOnClickListener(this);

        crop = (ImageButton) findViewById(R.id.crop_button);
        crop.setOnClickListener(this);


        filter = (ImageButton) findViewById(R.id.filters_button);
        filter.setOnClickListener(this);

        done = (ImageButton) findViewById(R.id.done_button);
        done.setOnClickListener(this);



    }


    @SuppressWarnings("deprecation")
    private void initImageView() {

        imageUri = getIntent().getStringExtra(getString(R.string.image_uri_flag));

        Log.e("Photo Editor", "Image URI = " + imageUri);
        imageView = (ImageView) findViewById(R.id.image_view);

        final Object data = getLastNonConfigurationInstance();

        if (data == null) {
            Log.e("data value", "= " + data);
            openBitmap(imageUri);
        } else {
            restoreBitmap();
        }

    }


    public void addView(myView myView_) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        imageViewArray.add(myView_);
        mRelativeLayoutMain.addView(myView_, params);

    }

    private void restoreBitmap() {
        Log.i("Photo Editor", "Restore bitmap");
        Bitmap b = ImageProcessor.getInstance().getBitmap();
        if (b != null) {
            imageView.setImageBitmap(b);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        Bundle saveObject = new Bundle();
        //saveObject.putInt("Bitmap", RESTORE_SAVED_BITMAP);
        return saveObject;
    }

    private void openBitmap(String imageUri) {
        Log.e("Photo Editor", "Open Bitmap");
        Bitmap b;

        try {

//            Intent i = getIntent();
//            if (i.hasExtra("path")) {
//                Log.e("tatoo---->", "path image" );
//                int Selected_tatoo = getIntent().getIntExtra("path", 0);
//                myView myViewTemp = new myView(getApplicationContext());
//                myViewTemp.setImageResource(Selected_tatoo);
//                myViewTemp.setOnTouchListener(new MultiTouchListener());
//                addView(myViewTemp);
//
//                Bitmap savedImagePath = ImageProcessor.getInstance().getBitmap();
//                imageView.setImageBitmap(savedImagePath);
//
//            }
            if (imageUri != null) {
                b = BitmapScalingUtil.bitmapFromUri(this, Uri.parse(imageUri));
                if (b != null) {
                    Log.e("Photo Editor", "Opened Bitmap Size: " + b.getWidth() + " " + b.getHeight());
                }
                ImageProcessor.getInstance().setBitmap(b);
                Log.e("Photo Editor", "Opened Bitmap Size: " + b.getWidth() + " " + b.getHeight());

                Bitmap savedImagePath = ImageProcessor.getInstance().getBitmap();
                imageView.setImageBitmap(savedImagePath);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        Bitmap savedImagePath = ImageProcessor.getInstance().getBitmap();
        imageView.setImageBitmap(savedImagePath);
        super.onResume();

    }

    private boolean imageIsAlreadySaved() {
        return savedImagePath != null && !savedImagePath.equals("");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case EDITOR_FUNCTION:
                if (resultCode == RESULT_OK) {
                    imageView.setImageBitmap(ImageProcessor.getInstance().getBitmap());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tatto:
                Tatoos_Activity();
                break;

            case R.id.text_button:
                TextBittonclick();
//                ImageProcessor.getInstance().setBitmap(captureImage());
//                mTv_text.setVisibility(View.VISIBLE);
//                mTv_text.setText("");
//                selectText_new();
                break;
            case R.id.rotate_button:
                Rotate_Activity();
                break;
            case R.id.crop_button:
                cropButtonClicked();
                break;
            case R.id.filters_button:
                filtersButtonClicked();
                break;
            case R.id.done_button:
                saveButtonClicked();
                break;


        }
    }


    private void saveButtonClicked() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        Intent i=new Intent(EditorActivity.this,Display_image.class);
        //i.putExtra("image",ImageProcessor.getInstance().getBitmap());
        startActivity(i);
        //saveImage();
        //Toast.makeText(this, R.string.photo_saved_info, Toast.LENGTH_LONG).show();
    }
    private void Tatoos_Activity() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        Intent i = new Intent(this, Tatoos_Activity1.class);
        startActivity(i);
    }

    private void Rotate_Activity() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        mTv_text.setVisibility(View.GONE);
        Intent i = new Intent(this, RotateActivity.class);
        startActivity(i);
    }
    private void cropButtonClicked() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        mTv_text.setVisibility(View.GONE);
        Intent i = new Intent(this, CropActivity.class);
        startActivity(i);
    }

    private void TextBittonclick() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        mTv_text.setVisibility(View.GONE);
        Intent i = new Intent(this, TextActivity.class);
        startActivity(i);
    }

    private void filtersButtonClicked() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        mTv_text.setVisibility(View.GONE);
        Intent i = new Intent(this, FilterActivity.class);
        startActivity(i);
    }



//    @Override
//    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
//        super.onPause();
//    }

//    /** Called when returning to the activity */
//    @Override
//    public void onResume() {
//        super.onResume();
//        AdLoaded = false;
//        if (mAdView != null) {
//            mAdView.resume();
//        }
//    }
//
//    /** Called before the activity is destroyed */
//    @Override
//    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//        super.onDestroy();
//    }

//     ====capture image====================

    private Bitmap captureImage() {
        // TODO Auto-generated method stub
        OutputStream output;

        Calendar cal = Calendar.getInstance();

        Bitmap bitmap = Bitmap.createBitmap(mRelativeLayoutMain.getWidth(), mRelativeLayoutMain.getHeight(),
                Bitmap.Config.ARGB_8888);

//		mBitmapDrawing = ThumbnailUtils.extractThumbnail(mBitmapDrawing,
//				mBitmapDrawing.getWidth(), mBitmapDrawing.getHeight());


        bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth(), bitmap.getHeight());

        Canvas b = new Canvas(bitmap);
        mRelativeLayoutMain.draw(b);

        int x = (mRelativeLayoutMain.getWidth() - imageView
                .getWidth()) / 2;
        int y = (mRelativeLayoutMain.getHeight() - imageView
                .getHeight()) / 2;

        mBitmapMain = Bitmap.createBitmap(bitmap, x, y,
                imageView.getWidth(),
                imageView.getHeight());



        // Find the SD Card path
//		File filepath = Environment.getExternalStorageDirectory();
//
//		// Create a new folder in SD Card
//		File dir = new File(filepath.getAbsolutePath() + "/Photo Sticker/");
//		dir.mkdirs();
//
//		mImagename = "image" + cal.getTimeInMillis() + ".png";
//
//		// Create a name for the saved image
//		file = new File(dir, mImagename);
//		MediaScannerConnection.scanFile(this, new String[] { file.getPath() }, null,
//				new MediaScannerConnection.OnScanCompletedListener() {
//					public void onScanCompleted(String path, Uri uri) {
//						// now visible in gallery
//					}
//				});

        // Show a toast message on successful save
        //Toast.makeText(MainActivity.this, "Image Saved to SD Card", Toast.LENGTH_SHORT).show();
//
//		try {
//
//			output = new FileOutputStream(file);
//			// Compress into png format image from 0% - 100%
//			mBitmapMain.compress(Bitmap.CompressFormat.PNG, 100, output);
//			output.flush();
//			output.close();
//		}
//
//		catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        return mBitmapMain;

    }

    // =====================

//*********start form text effect dialog************

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_row, parent, false);

            TextView label = (TextView) row.findViewById(R.id.textView1);
            label.setText("FONT");

            Typeface face = Typeface.createFromAsset(getAssets(), style[position]);

            label.setTypeface(face);

            return row;
        }

    }


    private void showColorPickerDialogDemo() {

        int initialColor = global.getColor();

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mIbtn_color_text.setBackgroundColor(color);
                global.setColor(color);

            }

        });
        colorPickerDialog.show();

    }

    public void selectText_new() {
        Log.e("text dialog is open","open dialog");

        if (mTv_text.getVisibility() == View.INVISIBLE) {
            return;
        }


        ViewPagerEx.LayoutParams mParams1;

        //gallery.setVisibility(View.INVISIBLE);

        final Dialog dialog = new Dialog(EditorActivity.this);
        // Include dialog.xml file

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.text_custom_dialog);
        dialog.setCancelable(false);
        // Set dialog title

        edText = (EditText) dialog.findViewById(R.id.et_view);
        edText.setText("");
     //   edText.setText("" + mTv_text.getText().toString().trim());
        dialog.setTitle("Text Appearance");
        dialog.show();
        mSpinner_text_style = (Spinner) dialog.findViewById(R.id.spinner_text_style);
        mIbtn_color_text = (ImageButton) dialog.findViewById(R.id.ibtn_color_text);

        MyAdapter adapter = new MyAdapter(EditorActivity.this, R.layout.spinner_row, style);
        mSpinner_text_style.setAdapter(adapter);

        mSpinner_text_style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                global.setPosition(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        mIbtn_color_text.setBackgroundColor(global.getColor());

        mIbtn_color_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showColorPickerDialogDemo();

            }
        });

        Button declineButton = (Button) dialog.findViewById(R.id.btn_cancel);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

        Button Ok = (Button) dialog.findViewById(R.id.btn_ok);
        // if decline button is clicked, close the custom dialog
        Ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Close dialog

                mTv_text.setTextColor(global.getColor());
                Typeface face = Typeface.createFromAsset(getAssets(), style[global.getPosition()]);

                mTv_text.setTypeface(face);

                String s = edText.getText().toString().trim();
                if (s.length() > 0) {
                    mTv_text.setText(s);
                    dialog.dismiss();

                } else {
                    Toast.makeText(EditorActivity.this, "Add Some Text...", Toast.LENGTH_SHORT).show();
                }
                //galleryview.setVisibility(View.VISIBLE);
            }
        });

        // ===========================================

        mTv_text.setTextSize(58);
        mTv_text.setOnTouchListener(new MultiTouchListener());

        // alltext.add(mTv_text);

    }


}
