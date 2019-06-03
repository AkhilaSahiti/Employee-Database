package com.example.employeedatabase.storage;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageStorageManager {
    private final static ImageStorageManager imageStorageManager = new ImageStorageManager();
    private final static String IMAGE_DIRECTORY_NAME = "photos";
    private final static String PNG = ".png";

    public static ImageStorageManager getInstance() {
        return imageStorageManager;
    }

    public interface ImageStorageCallback {
        void onPhotoSaved(String fileName);

        void onPhotoGenerated(Bitmap bitmap);
    }

    public void savePhoto(final Context context, final Bitmap bitmap, final ImageStorageCallback listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String fileName = UUID.randomUUID().toString() + PNG;
                    File imageFile = new File(getImagesDir(context) + "/" + fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                    fileOutputStream.close();
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onPhotoSaved(fileName);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getPhoto(final Context context, final String fileName, final ImageStorageCallback listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (fileName != null && !fileName.isEmpty()) {
                    File file = new File(fileName);
                    if (!file.exists()) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onPhotoGenerated(BitmapFactory.decodeFile(getImagesDir(context) + "/" + fileName));
                            }
                        });
                    }
                }
            }
        });
    }

    private String getImagesDir(Context context) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File imgDir = contextWrapper.getDir(IMAGE_DIRECTORY_NAME, Context.MODE_PRIVATE);
        return imgDir.getPath();
    }
}
