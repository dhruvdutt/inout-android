package com.example.jay.inout_try1.mysql_connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.jay.inout_try1.R;
import com.example.jay.inout_try1.ScaleBitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jay on 01/11/2015.
 */
public class GetImage extends AsyncTask<String,Void,Bitmap>{

    Context context;
    View view;

    public GetImage(Context context,View view){
        this.context=context;
        this.view=view;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageName=params[0];
        Bitmap bitmap=null;
        String imageUrl="http://192.168.1.105/inout/artisan/"+imageName+".jpg";
        bitmap=downloadImage(imageUrl);

        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ScaleBitmap scaleBitmap=new ScaleBitmap();
        Bitmap bm=scaleBitmap.getRoundedShape(bitmap);
        ImageView iv=(ImageView)view.findViewById(R.id.pro_pic);
        iv.setImageBitmap(bm);
        Log.i("IMAGE", bm.toString());
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private Bitmap downloadImage(String url){
        Bitmap bitmap=null;
        InputStream inputStream=null;
        BitmapFactory.Options bmOptions=new BitmapFactory.Options();
        bmOptions.inSampleSize=1;

        try{
            inputStream=getHttpConnection(url);
            bitmap=BitmapFactory.decodeStream(inputStream, null, bmOptions);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private InputStream getHttpConnection(String urlString) throws MalformedURLException {
        InputStream inputStream=null;
        URL url=new URL(urlString);
        try {
            URLConnection urlConnection=url.openConnection();
            HttpURLConnection httpURLConnection=(HttpURLConnection)urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            Log.i("DOWNLOAD", "1");
            Log.i("RESPONSE CODE", String.valueOf(httpURLConnection.getResponseCode()));
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=httpURLConnection.getInputStream();
                Log.i("HTTP_OK",inputStream.toString());
            }
            //httpURLConnection.disconnect();
            //((HttpURLConnection) urlConnection).disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
