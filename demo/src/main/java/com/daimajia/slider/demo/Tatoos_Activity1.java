package com.daimajia.slider.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.photoeditor.graphics.ImageProcessor;
import com.myandroid.views.MultiTouchListener;
import com.myandroid.views.myView;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by waycreon3 on 22/08/2015.
 */
public class Tatoos_Activity1 extends Activity {

    ImageAdapter myImageAdapter;
    ImageProcessor imageProcessor;
    ImageView imageView, tatoos, done;
    GridView gridview;
    RelativeLayout mRelativeLayoutMain;
    boolean clickstatus = false;
    Bitmap mBitmapMain = null;
    Bitmap bm;
    ImageButton del;

    int alpha;
    SeekBar seekbarbrightness;
    private View progressBar;
    myView myViewTemp;
    boolean isDelete = false;
    ArrayList<myView> imageViewArray = new ArrayList<myView>();

    ArrayList<Mylist> mlist = new ArrayList<Mylist>();

    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    int[] tattos_grid_Images = {R.drawable.t001, R.drawable.t002, R.drawable.t003, R.drawable.t004, R.drawable.t005, R.drawable.t006, R.drawable.t007, R.drawable.t008, R.drawable.t009, R.drawable.t010,
            R.drawable.t011, R.drawable.t012, R.drawable.t013, R.drawable.t014, R.drawable.t015, R.drawable.t016, R.drawable.t017, R.drawable.t018, R.drawable.t019, R.drawable.t020,
            R.drawable.t021, R.drawable.t022, R.drawable.t023, R.drawable.t024, R.drawable.t025, R.drawable.t026, R.drawable.t027, R.drawable.t028, R.drawable.t029, R.drawable.t030,
            R.drawable.t031, R.drawable.t032, R.drawable.t033, R.drawable.t034, R.drawable.t035, R.drawable.t036, R.drawable.t037, R.drawable.t038, R.drawable.t039, R.drawable.t040,
            R.drawable.t041, R.drawable.t042, R.drawable.t043, R.drawable.t044, R.drawable.t045, R.drawable.t046, R.drawable.t047, R.drawable.t048, R.drawable.t049, R.drawable.t050,
            R.drawable.t051, R.drawable.t052, R.drawable.t053, R.drawable.t054, R.drawable.t055, R.drawable.t056, R.drawable.t057, R.drawable.t058, R.drawable.t059, R.drawable.t060,

    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view1);

        // mTv_text = new AutoResizeTextView(getApplicationContext());
        // global=((Platform)getApplication());

        mRelativeLayoutMain = (RelativeLayout) findViewById(R.id.capture_id_rl);
        gridview = (GridView) findViewById(R.id.grid_view);
        imageView = (ImageView) findViewById(R.id.image_view);
        done = (ImageView) findViewById(R.id.done_button);
        imageProcessor = ImageProcessor.getInstance();
        seekbarbrightness = (SeekBar) findViewById(R.id.slider);
         alpha = seekbarbrightness.getProgress();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //  slider.setOnSeekBarChangeListener(sliderChangeListener);
        Bitmap savedImagePath = ImageProcessor.getInstance().getBitmap();
        imageView.setImageBitmap(savedImagePath);
        tatoos = (ImageView) findViewById(R.id.tatto);
        tatoos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gridview.setVisibility(View.VISIBLE);
                myImageAdapter = new ImageAdapter(getApplicationContext(), mlist);
                gridview.setAdapter(myImageAdapter);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveButtonClicked();
            }
        });
        del = (ImageButton) findViewById(R.id.delete_button);
        del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ckhDelete();
                del.setVisibility(v.VISIBLE);
                // interstitialAds.show();
                isDelete = !isDelete;

            }
        });


        for (int i = 0; i < tattos_grid_Images.length; i++) {
            Mylist m = new Mylist();
            m.setId(tattos_grid_Images[i]);
            mlist.add(m);


        }


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                clickstatus = true;
                //  Intent i = new Intent(Tatoos_Activity1.this,
                //       EditorActivity.class);
                // i.putExtra("path", mlist.get(arg2).getId());
                //  imageView.setImageResource(mlist.get(arg2).getId());
                Log.e("path of image", "" + mlist.get(arg2).getId());


                myViewTemp = new myView(getApplicationContext());
                myViewTemp.setImageResource(mlist.get(arg2).getId());
                myViewTemp.setAlpha(alpha);

                myViewTemp.setOnTouchListener(new MultiTouchListener());
                addView(myViewTemp);

                // Drawable d = mlist.get(arg2).getId();

                bm = ((BitmapDrawable) myViewTemp.getDrawable()).getBitmap();

                Bitmap savedImagePath = ImageProcessor.getInstance().getBitmap();
                imageView.setImageBitmap(savedImagePath);


                gridview.setVisibility(View.GONE);
                del.setVisibility(View.VISIBLE);
                seekbarbrightness.setVisibility(View.VISIBLE);
                // startActivity(i);
                // finish();

            }
        });

        seekbarbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int brightness;

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {

                // TODO Auto-generated method stub

                int alpha = seekbarbrightness.getProgress();
               // textOpacitySetting.setText(String.valueOf(alpha));
                 myViewTemp.setAlpha(alpha);   //deprecated
                //image.setImageAlpha(alpha); //for API Level 16+

//                brightness = progress;
//                Bitmap bitmap = doBrightness(bm, brightness);
//                myViewTemp.setImageBitmap(bitmap);
            }
        });


    }

    public void ckhDelete() {

        if (isDelete) {
            addOnclick();
            del.setBackgroundColor(Color.RED);

        } else {
            addmultitouch();
            del.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    public void addOnclick() {
        for (myView view : imageViewArray) {

            view.setOnTouchListener(null);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                }
            });

        }
    }

    public void addmultitouch() {

        for (myView view : imageViewArray) {

            view.setOnTouchListener(new MultiTouchListener());

        }

    }

    public static Bitmap doBrightness(Bitmap src, int value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if (R > 255) {
                    R = 255;
                } else if (R < 0) {
                    R = 0;
                }

                G += value;
                if (G > 255) {
                    G = 255;
                } else if (G < 0) {
                    G = 0;
                }

                B += value;
                if (B > 255) {
                    B = 255;
                } else if (B < 0) {
                    B = 0;
                }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }


    private void saveButtonClicked() {
        ImageProcessor.getInstance().setBitmap(captureImage());
        Intent i = new Intent(Tatoos_Activity1.this, EditorActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }

    public void addView(myView myView_) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        imageViewArray.add(myView_);
        mRelativeLayoutMain.addView(myView_, params);


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

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        Activity activity;

        ArrayList<Mylist> myList = new ArrayList<Mylist>();

        public ImageAdapter(Context c, ArrayList<Mylist> myList2) {
            this.mContext = c;
            this.myList = myList2;

        }

        @Override
        public int getCount() {
            return tattos_grid_Images.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return tattos_grid_Images[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public class Holder {

            ImageView img;
        }

        Holder holder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if (rowView == null) {

                holder = new Holder();
                rowView = inflater.inflate(R.layout.grid_item, null);
                holder.img = (ImageView) rowView.findViewById(R.id.view_image);
                rowView.setTag(holder);
            } else {
                holder = (Holder) rowView.getTag();
            }

            holder.img.setImageResource(tattos_grid_Images[position]);
            return rowView;
        }


    }

}
