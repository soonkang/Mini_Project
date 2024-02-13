package com.sp.mini_assignment.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class favouriteCarparkHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "CarparkSQL_DB";

    private static final String TABLE_NAME = "favouriteTable";
    private static final String KEY_ID = "_id";
    private static final String ITEM_TITLE = "itemTitle";
    private static final String ITEM_IMAGE = "itemImage";
    private static final String FAVOURITE_STATUS = "fStatus";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ITEM_TITLE + " TEXT," +
            ITEM_IMAGE + " TEXT," +
            FAVOURITE_STATUS + " TEXT)";

    public favouriteCarparkHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public void insertIntoTheDatabase(String item_title, String item_image_url, String fav_status) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGE, item_image_url); // Store the image URL as a string
        cv.put(FAVOURITE_STATUS, fav_status);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
    }

    public List<Carpark> readAllData(int limit) {
        List<Carpark> carparkList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVOURITE_STATUS + "=?";
        String[] parameters = new String[]{"1"};

        Cursor cursor = db.rawQuery(query, parameters);

        Log.d("Cursor Size", "Cursor size: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            int keyIdIndex = cursor.getColumnIndex(KEY_ID);
            int itemImageIndex = cursor.getColumnIndex(ITEM_IMAGE);
            int itemTitleIndex = cursor.getColumnIndex(ITEM_TITLE);
            int favStatusIndex = cursor.getColumnIndex(FAVOURITE_STATUS);

            do {
                // Create a new Carpark object for each row in the cursor and add it to the list
                Carpark carpark = new Carpark();
                carpark.setKey_id(cursor.getInt(keyIdIndex));
                carpark.setCarparkImage(cursor.getString(itemImageIndex));
                carpark.setCarparkName(cursor.getString(itemTitleIndex));
                carpark.setFavStatus(cursor.getString(favStatusIndex));
                carparkList.add(carpark);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return carparkList;
    }

    public void removeFav(Carpark carpark) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(carpark.getKey_id())});
        Log.d("favouriteCarparkHelper", "Removed item with ID: " + carpark.getKey_id());



    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }


    public void close() {

        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private class ImageDownloaderTask extends AsyncTask<Void, Void, byte[]> {
        private final String itemTitle;
        private final String imageUrl;
        private final String favStatus;

        public ImageDownloaderTask(String itemTitle, String imageUrl, String favStatus) {
            this.itemTitle = itemTitle;
            this.imageUrl = imageUrl;
            this.favStatus = favStatus;
        }

        @Override
        protected byte[] doInBackground(Void... voids) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return outputStream.toByteArray();
            } catch (IOException e) {
                Log.e("ImageDownloader", "Error downloading image: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(byte[] imageBytes) {
            if (imageBytes != null) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ITEM_TITLE, itemTitle);
                cv.put(ITEM_IMAGE, imageUrl); // Store the image URL as a string
                cv.put(FAVOURITE_STATUS, favStatus);
                db.insert(TABLE_NAME, null, cv);
            }
        }
    }


}
