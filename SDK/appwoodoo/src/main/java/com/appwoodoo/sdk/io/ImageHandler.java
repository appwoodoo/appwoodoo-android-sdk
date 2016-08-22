package com.appwoodoo.sdk.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageHandler {

    public static void downloadIntoImageView(@NonNull final String imageUrl, final ImageView imageView) {

        new AsyncTask<Void, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                HttpURLConnection connection = null;
                InputStream is = null;

                try {
                    connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                    is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (Throwable e) {
                    if (!this.isCancelled()) {
                        this.cancel(true);
                    }
                } finally {
                    try {
                        if (connection != null) {
                            connection.disconnect();
                        }
                        if (is != null) {
                            is.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    imageView.setImageBitmap(result);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
                System.gc();
            }
        }.execute();
    }

}