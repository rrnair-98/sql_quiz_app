package com.creeps.sl_app.quizapp.core_services.utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageFetcher extends AsyncTask<String,Object,Bitmap> {
    private final static String TAG="McqItemAsyncTask";
    private ImageView mImageView;
    private PostExecuteCallback postExecuteCallback;
    public ImageFetcher(ImageView ima,PostExecuteCallback ps){
        this.mImageView=ima;this.postExecuteCallback=ps;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return BitmapFactory.decodeStream(new URL(params[0]).openConnection().getInputStream());
        }catch (MalformedURLException mfe){
            Log.d(TAG,"malformed URL ");
        }catch(IOException ioe){
            Log.d(TAG,"io exception ");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null) {
            this.mImageView.setImageBitmap(bitmap);
            if(this.postExecuteCallback!=null)
                postExecuteCallback.postExecute();
        }
    }


}
