package com.bharatarmy.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bharatarmy.Models.GalleryImageModel;


import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "galleryUploaddb";
    private static final String TABLE_GALLERYUPLOAD = "imagedetails";
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGEURI = "imageUri";
    private static final String KEY_IMAGESIZE = "imageSize";
    private static final String KEY_IMAGEUPLOADSTATUS = "imageUploadStatus";
    private static final String KEY_VIDEOLENGTH = "videolength";
    private static final String KEY_FILETYPE = "fileType";
    private static final String KEY_VIDEOTITLE = "videoTitle";
    private static final String KEY_VIDEODESC = "videoDesc";
    private static final String KEY_VIDEOHEIGHT = "videoHeight";
    private static final String KEY_VIDEOWIDTH = "videoWidth";
    private static final String KEY_PRIVACYSETTING = "privacySetting";
    private static final String KEY_THUMBNAIL = "thumbnail";
    SQLiteDatabase db;

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_GALLERYUPLOAD + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + KEY_IMAGEURI + " TEXT , "
                + KEY_IMAGESIZE + " TEXT , " + KEY_IMAGEUPLOADSTATUS + " TEXT, "
                + KEY_VIDEOLENGTH + " TEXT , " + KEY_FILETYPE + " TEXT , "
                + KEY_VIDEOTITLE + " TEXT , " + KEY_VIDEODESC + " TEXT , "
                + KEY_VIDEOHEIGHT + " TEXT , " + KEY_VIDEOWIDTH + " TEXT , "
                + KEY_PRIVACYSETTING + " TEXT ," + KEY_THUMBNAIL + " TEXT )";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERYUPLOAD);
        // Create tables again
        onCreate(db);
    }


    // Adding new Image Details
    public void insertImageDetails(String imageUri, String imageSize, String imageUploadStatus,
                                   String videoLength, String fileType, String videoTitle,
                                   String videoDesc, String videoHeight, String videoWidth,
                                   String privacySetting, String thumnail, Context context) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_IMAGEURI, imageUri);
        cValues.put(KEY_IMAGESIZE, imageSize);
        cValues.put(KEY_IMAGEUPLOADSTATUS, imageUploadStatus);
        cValues.put(KEY_VIDEOLENGTH, videoLength);
        cValues.put(KEY_FILETYPE, fileType);
        cValues.put(KEY_VIDEOTITLE, videoTitle);
        cValues.put(KEY_VIDEODESC, videoDesc);
        cValues.put(KEY_VIDEOHEIGHT, videoHeight);
        cValues.put(KEY_VIDEOWIDTH, videoWidth);
        cValues.put(KEY_PRIVACYSETTING, privacySetting);
        cValues.put(KEY_THUMBNAIL,thumnail);
        // Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_GALLERYUPLOAD, null, cValues); // long newRowId =
        db.close();
    }

    // Update Image Details
    public void UpdateImageStatus(String uploadstatus, int id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_IMAGEUPLOADSTATUS, uploadstatus);
        db.update(TABLE_GALLERYUPLOAD, cVals, KEY_ID + " = ?", new String[]{String.valueOf(id)}); //int count =
        db.close();
    }

    public void UpdateImagePendingStatus(String uploadstatus, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_IMAGEUPLOADSTATUS, uploadstatus);
        db.update(TABLE_GALLERYUPLOAD, cVals, KEY_IMAGEUPLOADSTATUS + " = ?", new String[]{String.valueOf(1)}); //int count =
        db.close();

    }

    public List<GalleryImageModel> getAllImageData() {
        List<GalleryImageModel> list = new ArrayList<>();
        GalleryImageModel model = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_GALLERYUPLOAD, new String[]{KEY_ID, KEY_IMAGEURI, KEY_IMAGESIZE,
                        KEY_IMAGEUPLOADSTATUS, KEY_VIDEOLENGTH, KEY_FILETYPE, KEY_VIDEOTITLE, KEY_VIDEODESC,
                        KEY_VIDEOHEIGHT, KEY_VIDEOWIDTH, KEY_PRIVACYSETTING, KEY_THUMBNAIL},
                KEY_IMAGEUPLOADSTATUS + "=? ",
                new String[]{String.valueOf(0)}, null, null, null, null);

//        Cursor cursor=db.rawQuery("select * from "+TABLE_GALLERYUPLOAD ,null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DbHandler.KEY_ID);
            int index2 = cursor.getColumnIndex(DbHandler.KEY_IMAGEURI);
            int index3 = cursor.getColumnIndex(DbHandler.KEY_IMAGESIZE);
            int index4 = cursor.getColumnIndex(DbHandler.KEY_IMAGEUPLOADSTATUS);
            int index5 = cursor.getColumnIndex(DbHandler.KEY_VIDEOLENGTH);
            int index6 = cursor.getColumnIndex(DbHandler.KEY_FILETYPE);
            int index7 = cursor.getColumnIndex(DbHandler.KEY_VIDEOTITLE);
            int index8 = cursor.getColumnIndex(DbHandler.KEY_VIDEODESC);
            int index9 = cursor.getColumnIndex(DbHandler.KEY_VIDEOHEIGHT);
            int index10 = cursor.getColumnIndex(DbHandler.KEY_VIDEOWIDTH);
            int index11 = cursor.getColumnIndex(DbHandler.KEY_PRIVACYSETTING);
            int index12 = cursor.getColumnIndex(DbHandler.KEY_THUMBNAIL);

            int cid = cursor.getInt(index);
            String name = cursor.getString(index2);
            String card = cursor.getString(index3);
            String code = cursor.getString(index4);
            String length = cursor.getString(index5);
            String filetype = cursor.getString(index6);
            String videotitle = cursor.getString(index7);
            String videodesc = cursor.getString(index8);
            String videoHeight = cursor.getString(index9);
            String videoWidth = cursor.getString(index10);
            String privacySetting = cursor.getString(index11);
            String thumbnail=cursor.getString(index12);
            model = new GalleryImageModel(cid, name, card, code, length, filetype, videotitle, videodesc, videoHeight,
                    videoWidth, privacySetting,thumbnail);
            list.add(model);
        }
        Log.d("arraysize :", "" + list.size() + " arrayValue :" + list.toString());
        return list;
    }

    public List<GalleryImageModel> getPendingImageData() {
        List<GalleryImageModel> list = new ArrayList<>();
        GalleryImageModel model = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_GALLERYUPLOAD, new String[]{KEY_ID, KEY_IMAGEURI,
                        KEY_IMAGESIZE, KEY_IMAGEUPLOADSTATUS, KEY_VIDEOLENGTH, KEY_FILETYPE,
                        KEY_VIDEOTITLE, KEY_VIDEODESC, KEY_VIDEOHEIGHT, KEY_VIDEOWIDTH,
                        KEY_PRIVACYSETTING, KEY_THUMBNAIL},
                KEY_IMAGEUPLOADSTATUS + "=? OR " + KEY_IMAGEUPLOADSTATUS + "=? ",
                new String[]{String.valueOf(1), String.valueOf(0)}, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DbHandler.KEY_ID);
            int index2 = cursor.getColumnIndex(DbHandler.KEY_IMAGEURI);
            int index3 = cursor.getColumnIndex(DbHandler.KEY_IMAGESIZE);
            int index4 = cursor.getColumnIndex(DbHandler.KEY_IMAGEUPLOADSTATUS);
            int index5 = cursor.getColumnIndex(DbHandler.KEY_VIDEOLENGTH);
            int index6 = cursor.getColumnIndex(DbHandler.KEY_FILETYPE);
            int index7 = cursor.getColumnIndex(DbHandler.KEY_VIDEOTITLE);
            int index8 = cursor.getColumnIndex(DbHandler.KEY_VIDEODESC);
            int index9 = cursor.getColumnIndex(DbHandler.KEY_VIDEOHEIGHT);
            int index10 = cursor.getColumnIndex(DbHandler.KEY_VIDEOWIDTH);
            int index11 = cursor.getColumnIndex(DbHandler.KEY_PRIVACYSETTING);
            int index12 =cursor.getColumnIndex(DbHandler.KEY_THUMBNAIL);

            int cid = cursor.getInt(index);
            String name = cursor.getString(index2);
            String card = cursor.getString(index3);
            String code = cursor.getString(index4);
            String length = cursor.getString(index5);
            String filetype = cursor.getString(index6);
            String videotitle = cursor.getString(index7);
            String videodesc = cursor.getString(index8);
            String videoHeight = cursor.getString(index9);
            String videoWidth = cursor.getString(index10);
            String privacySetting = cursor.getString(index11);
            String thumbnail=cursor.getString(index12);
            model = new GalleryImageModel(cid, name, card, code, length, filetype, videotitle, videodesc, videoHeight,
                    videoWidth, privacySetting,thumbnail);
            list.add(model);
        }
        Log.d("arraysize :", "" + list.size() + " arrayValue :" + list.toString());
        return list;
    }

    public List<GalleryImageModel> getMediaImageData() {
        List<GalleryImageModel> list = new ArrayList<>();
        GalleryImageModel model = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_GALLERYUPLOAD, new String[]{KEY_ID, KEY_IMAGEURI, KEY_IMAGESIZE,
                        KEY_IMAGEUPLOADSTATUS, KEY_VIDEOLENGTH, KEY_FILETYPE, KEY_VIDEOTITLE, KEY_VIDEODESC,
                        KEY_VIDEOHEIGHT, KEY_VIDEOWIDTH, KEY_PRIVACYSETTING,KEY_THUMBNAIL},
                KEY_IMAGEUPLOADSTATUS + "=? OR " + KEY_IMAGEUPLOADSTATUS + "=? ",
                new String[]{String.valueOf(2), String.valueOf(0)}, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DbHandler.KEY_ID);
            int index2 = cursor.getColumnIndex(DbHandler.KEY_IMAGEURI);
            int index3 = cursor.getColumnIndex(DbHandler.KEY_IMAGESIZE);
            int index4 = cursor.getColumnIndex(DbHandler.KEY_IMAGEUPLOADSTATUS);
            int index5 = cursor.getColumnIndex(DbHandler.KEY_VIDEOLENGTH);
            int index6 = cursor.getColumnIndex(DbHandler.KEY_FILETYPE);
            int index7 = cursor.getColumnIndex(DbHandler.KEY_VIDEOTITLE);
            int index8 = cursor.getColumnIndex(DbHandler.KEY_VIDEODESC);
            int index9 = cursor.getColumnIndex(DbHandler.KEY_VIDEOHEIGHT);
            int index10 = cursor.getColumnIndex(DbHandler.KEY_VIDEOWIDTH);
            int index11 = cursor.getColumnIndex(DbHandler.KEY_PRIVACYSETTING);
            int index12 =cursor.getColumnIndex(DbHandler.KEY_THUMBNAIL);

            int cid = cursor.getInt(index);
            String name = cursor.getString(index2);
            String card = cursor.getString(index3);
            String code = cursor.getString(index4);
            String length = cursor.getString(index5);
            String filetype = cursor.getString(index6);
            String videotitle = cursor.getString(index7);
            String videodesc = cursor.getString(index8);
            String videoHeight = cursor.getString(index9);
            String videoWidth = cursor.getString(index10);
            String privacySetting = cursor.getString(index11);
            String thumbnail = cursor.getString(index12);
            model = new GalleryImageModel(cid, name, card, code, length, filetype, videotitle, videodesc, videoHeight,
                    videoWidth, privacySetting,thumbnail);
            list.add(model);
        }
        Log.d("arraysize :", "" + list.size() + " arrayValue :" + list.toString());
        return list;
    }

    // Delete Image Details
    public void DeleteImage(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GALLERYUPLOAD, KEY_ID + " = ?", new String[]{String.valueOf(userid)});
        db.close();
    }

    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
            super.close();
        }
    }


}
