package com.daimajia.slider.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.example.photoeditor.graphics.ImageProcessor;
import com.myandroid.views.MultiTouchListener;
import com.myandroid.views.myView;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by waycreon3 on 25/08/2015.
 */
public class TextActivity extends Activity {
    private ImageProcessor imageProcessor;
    private ImageView imageView;
    private Button colorclick, textStyle, done;
    private ImageButton clickText, okButton;
    private ImageButton cancelButton;
    private ImageButton rotateLeftButton;
    private ImageButton rotateRightButton;
    private EditText edText;
    private SeekBar slider;
    private View progressBar;
    private int currentAngle = 0;
    Bitmap result1;
    AutoResizeTextView mTv_text;
    ImageButton mIbtn_color_text;
    Spinner mSpinner_text_style;
    Platform global;
    TextActivity t1;
    Bitmap mBitmapMain = null;
    RelativeLayout mRelativeLayoutMain,textEffect;
    ArrayList<String> imageViewArray = new ArrayList<>();
    private FrameLayout rr_1;

    String style[] = {"1.ttf", "2.ttf", "3.ttf", "4.ttf", "5.ttf", "6.ttf", "7.ttf", "8.ttf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);

        initializeComponents();
        mTv_text = new AutoResizeTextView(getApplicationContext());
        global = ((Platform) getApplication());
        rr_1 = (FrameLayout) findViewById(R.id.rr_1);
        rr_1.addView(mTv_text);
    }

    private void initializeComponents() {
        mRelativeLayoutMain = (RelativeLayout) findViewById(R.id.capture_id_rl);
        textEffect=(RelativeLayout)findViewById(R.id.text_effact_layout);
        imageProcessor = ImageProcessor.getInstance();
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageBitmap(imageProcessor.getBitmap());
        mSpinner_text_style = (Spinner) findViewById(R.id.spinner_text_style);
        colorclick = (Button) findViewById(R.id.colorbtn);
        done = (Button) findViewById(R.id.done);
        edText = (EditText) findViewById(R.id.et_view);
//        edText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textEffect.setVisibility(View.VISIBLE);
//            }
//        });

        //  mIbtn_color_text.setBackgroundColor(global.getColor());
        colorclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorPickerDialogDemo();
              //  mTv_text.setTextColor(global.getColor());
            }
        });
//
        MyAdapter adapter = new MyAdapter(TextActivity.this, R.layout.spinner_row, style);
        mSpinner_text_style.setAdapter(adapter);

        mSpinner_text_style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                global.setPosition(arg2);
               // Typeface face = Typeface.createFromAsset(getAssets(), style[global.getPosition()]);

               // mTv_text.setTypeface(face);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Close dialog

                mTv_text.setTextColor(global.getColor());
                Typeface face = Typeface.createFromAsset(getAssets(), style[global.getPosition()]);

                mTv_text.setTypeface(face);

                String s = edText.getText().toString().trim();
                if (s.length() > 0) {
                    myView myViewTemp = new myView(getApplicationContext());
                   // myViewTemp.sette;
                    addView(s);
                    mTv_text.setText(s);
                    //mTv_text.setBackgroundColor(@android:color/transparent);


                } else {
                    Toast.makeText(TextActivity.this, "Add Some Text...", Toast.LENGTH_SHORT).show();
                }

               // edText.setVisibility(View.GONE);
                mTv_text.setTextSize(58);
                mTv_text.setOnTouchListener(new MultiTouchListener());
            }
        });

//        mTv_text.setTextSize(58);
//        mTv_text.setOnTouchListener(new MultiTouchListener());

        slider = (SeekBar) findViewById(R.id.slider);
        okButton = (ImageButton) findViewById(R.id.ok_button);
        cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageProcessor.getInstance().setBitmap(captureImage());
                imageProcessor.save();
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageProcessor.save();
                finish();
            }
        });


    }

    public void addView(String myView_) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        imageViewArray.add(myView_);
        //mRelativeLayoutMain.addView(myView_, params);

    }

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

        return mBitmapMain;

    }

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

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor,
                new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                // mIbtn_color_text.setBackgroundColor(color);
                global.setColor(color);

            }

        });
        colorPickerDialog.show();

    }

//    public void selectText_new() {
//        Log.e("text dialog is open", "open dialog");
//
//        if (mTv_text.getVisibility() == View.INVISIBLE) {
//            return;
//        }
//
//
//        ViewPagerEx.LayoutParams mParams1;
//
//        //gallery.setVisibility(View.INVISIBLE);
//
//        final Dialog dialog = new Dialog(TextActivity.this);
//        // Include dialog.xml file
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        dialog.setContentView(R.layout.text_custom_dialog);
//        dialog.setCancelable(false);
//        // Set dialog title
//
//        edText = (EditText) dialog.findViewById(R.id.et_view);
//        edText.setText("");
//        //   edText.setText("" + mTv_text.getText().toString().trim());
//        dialog.setTitle("Text Appearance");
//        dialog.show();
//        mSpinner_text_style = (Spinner) dialog.findViewById(R.id.spinner_text_style);
//        mIbtn_color_text = (ImageButton) dialog.findViewById(R.id.ibtn_color_text);
//
//        mMyAdapter adapter = new MyAdapter(TextActivity.this, R.layout.spinner_row, style);
//        mSpinner_text_style.setAdapter(adapter);
//
//        mSpinner_text_style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                // TODO Auto-generated method stub
//                global.setPosition(arg2);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//        mIbtn_color_text.setBackgroundColor(global.getColor());
//
//        mIbtn_color_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                showColorPickerDialogDemo();
//
//            }
//        });
//
//        Button declineButton = (Button) dialog.findViewById(R.id.btn_cancel);
//        // if decline button is clicked, close the custom dialog
//        declineButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Close dialog
//                dialog.dismiss();
//            }
//        });
//
//        Button Ok = (Button) dialog.findViewById(R.id.btn_ok);
//        // if decline button is clicked, close the custom dialog
//        Ok.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Close dialog
//
//                mTv_text.setTextColor(global.getColor());
//                Typeface face = Typeface.createFromAsset(getAssets(), style[global.getPosition()]);
//
//                mTv_text.setTypeface(face);
//
//                String s = edText.getText().toString().trim();
//                if (s.length() > 0) {
//                    mTv_text.setText(s);
//                    dialog.dismiss();
//
//                } else {
//                    Toast.makeText(TextActivity.this, "Add Some Text...", Toast.LENGTH_SHORT).show();
//                }
//                //galleryview.setVisibility(View.VISIBLE);
//            }
//        });
//
//        // ===========================================
//
//        mTv_text.setTextSize(58);
//        mTv_text.setOnTouchListener(new MultiTouchListener());
//
//        // alltext.add(mTv_text);
//
//    }


}