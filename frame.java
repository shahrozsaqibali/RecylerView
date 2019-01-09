package com.appristine.clusterclear.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.appristine.clusterclear.R;
import com.appristine.clusterclear.framework.Utilities;
import com.appristine.clusterclear.listners.RecyclerItemClickListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class frame extends Activity implements View.OnClickListener,RecyclerItemClickListener {

    //widgets
    ImageView btnBack;
    ImageView btnCheck;
    ImageView ImagePreview;
    ImageView ivTemplatePreview;

    //Variables
    ArrayList<String> lofurl;
    RecyclerView ListDisplay;
    private FrameRecycler mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_capture_image);
        //Initialization
        btnBack = (ImageView)findViewById(R.id.btnback);
        btnCheck = (ImageView)findViewById(R.id.btncheck);
        ImagePreview = (ImageView)findViewById(R.id.framebackground);
        ivTemplatePreview = (ImageView)findViewById(R.id.ivTemplatePreview);

        lofurl = new ArrayList<String>();


        //Click Listener
        btnBack.setOnClickListener(this);
        btnCheck.setOnClickListener(this);


        //Process
        ImagePreview.setImageBitmap(Utilities.IMAGE_ARRAY);

        lofurl.add("http://clusterclear.ae/assets/admin/usersdata/c6a2d83cde2737120d6801d3a08b3657.png");
        lofurl.add("http://clusterclear.ae/assets/admin/usersdata/43d44dbf5e7987fd77ffa07e3b60b174.png");
        lofurl.add("http://clusterclear.ae/assets/admin/usersdata/1121d3d85b0721f71fc99d10d1a9b14a.png");
        lofurl.add("http://clusterclear.ae/assets/admin/usersdata/0c6d4bf1c4f4f271d110d0af7f73567c.png");
        lofurl.add("http://clusterclear.ae/assets/admin/usersdata/e8754213d3322451bb8167517e210c89.png");
        lofurl.add("http://clusterclear.ae/assets/admin/usersdata/4348824b53e41470e1f5194c3e123603.png");

        mAdapter = new FrameRecycler(lofurl,this,this);
        ListDisplay = (RecyclerView)findViewById(R.id.recycler_view_template);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        ListDisplay.setLayoutManager(mLayoutManager);
        ListDisplay.setAdapter(mAdapter);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnback:

                Intent trans = new Intent(frame.this,FileBeautyOptionActivity.class);
                startActivity(trans);
                finish();
                break;
            case R.id.btncheck:
                new FrameSet().execute();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRecyclerItemClick(int position) {

        if(mAdapter != null && mAdapter.imgurl.size() > 0)
        {
            Glide.with(this)
                    .load(mAdapter.imgurl.get(position))
                    .into(ivTemplatePreview);
        }

    }


    private class FrameSet extends AsyncTask<Void,Void,Void>
    {

        Bitmap bitmap2;
        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            if(bitmap2 != null)
            {
                bitmap2 = null;
            }
            Intent trans = new Intent(frame.this,FileBeautyOptionActivity.class);
            startActivity(trans);
            finish();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */


        @Override
        protected Void doInBackground(Void... voids) {
            ivTemplatePreview.buildDrawingCache();
            bitmap2 = ivTemplatePreview.getDrawingCache();
            Utilities.IMAGE_ARRAY = addFrame(Utilities.IMAGE_ARRAY,bitmap2);
            return null;
        }
    }
    public static Bitmap addFrame(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay;
        Bitmap bit;
        bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        bit=Bitmap.createScaledBitmap(bmp2,bmp1.getWidth(), bmp1.getHeight(),false);
        canvas.drawBitmap(bit, 0, 0, null);
        return bmOverlay;
    }
}
