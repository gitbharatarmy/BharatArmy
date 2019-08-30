package com.bharatarmy.Utility;

import android.content.ContentValues;
import android.content.Context;
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


    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        if (!Utils.doesTableExist(db, TABLE_GALLERYUPLOAD)) {
//            String CREATE_TABLE = "CREATE TABLE " + TABLE_GALLERYUPLOAD + "("
//                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGEURI + " TEXT,"
//                    + KEY_IMAGESIZE + " TEXT,"
//                    + KEY_IMAGEUPLOADSTATUS + " INTEGER);";
//            db.execSQL(CREATE_TABLE);
//        }else{
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERYUPLOAD);
            String CREATE_TABLE = "CREATE TABLE " + TABLE_GALLERYUPLOAD + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGEURI + " TEXT,"
                    + KEY_IMAGESIZE + " TEXT , " + KEY_IMAGEUPLOADSTATUS + " TEXT  )";
            db.execSQL(CREATE_TABLE);
//        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERYUPLOAD);
        // Create tables again
        onCreate(db);
    }


    // Adding new Image Details
    public void insertImageDetails(String imageUri, String imageSize, String imageUploadStatus) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_IMAGEURI, imageUri);
        cValues.put(KEY_IMAGESIZE, imageSize);
        cValues.put(KEY_IMAGEUPLOADSTATUS, imageUploadStatus);
        // Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_GALLERYUPLOAD, null, cValues); // long newRowId =
        db.close();
    }

    // Update Image Details
    public void UpdateImageStatus(String uploadstatus, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_IMAGEUPLOADSTATUS, uploadstatus);
        db.update(TABLE_GALLERYUPLOAD, cVals, KEY_ID + " = ?", new String[]{String.valueOf(id)}); //int count =

        db.close();
    }
    public void UpdateImagePendingStatus(String uploadstatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_IMAGEUPLOADSTATUS, uploadstatus);
        db.update(TABLE_GALLERYUPLOAD, cVals, KEY_IMAGEUPLOADSTATUS + " = ?", new String[]{String.valueOf(1)}); //int count =
    }
    public List<GalleryImageModel> getAllImageData() {
        List<GalleryImageModel> list = new ArrayList<>();
        GalleryImageModel model = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_GALLERYUPLOAD, new String[]{KEY_ID, KEY_IMAGEURI, KEY_IMAGESIZE, KEY_IMAGEUPLOADSTATUS},
                KEY_IMAGEUPLOADSTATUS + "=?",
                new String[]{String.valueOf(0)}, null, null, null, null);

//        Cursor cursor=db.rawQuery("select * from "+TABLE_GALLERYUPLOAD ,null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DbHandler.KEY_ID);
            int index2 = cursor.getColumnIndex(DbHandler.KEY_IMAGEURI);
            int index3 = cursor.getColumnIndex(DbHandler.KEY_IMAGESIZE);
            int index4 = cursor.getColumnIndex(DbHandler.KEY_IMAGEUPLOADSTATUS);
            int cid = cursor.getInt(index);
            String name = cursor.getString(index2);
            String card = cursor.getString(index3);
            String code = cursor.getString(index4);
            model = new GalleryImageModel(cid, name, card, code);
            list.add(model);
        }
        Log.d("arraysize :", "" + list.size() + " arrayValue :" + list.toString());
        return list;
    }

    public List<GalleryImageModel> getPendingImageData() {
        List<GalleryImageModel> list = new ArrayList<>();
        GalleryImageModel model = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_GALLERYUPLOAD, new String[]{KEY_ID, KEY_IMAGEURI, KEY_IMAGESIZE, KEY_IMAGEUPLOADSTATUS},
                KEY_IMAGEUPLOADSTATUS + "=? OR " + KEY_IMAGEUPLOADSTATUS + "=? ",
                new String[]{String.valueOf(1),String.valueOf(0)}, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DbHandler.KEY_ID);
            int index2 = cursor.getColumnIndex(DbHandler.KEY_IMAGEURI);
            int index3 = cursor.getColumnIndex(DbHandler.KEY_IMAGESIZE);
            int index4 = cursor.getColumnIndex(DbHandler.KEY_IMAGEUPLOADSTATUS);
            int cid = cursor.getInt(index);
            String name = cursor.getString(index2);
            String card = cursor.getString(index3);
            String code = cursor.getString(index4);
            model = new GalleryImageModel(cid, name, card, code);
            list.add(model);
        }
        Log.d("arraysize :", "" + list.size() + " arrayValue :" + list.toString());
        return list;
    }

    public List<GalleryImageModel> getMediaImageData() {
        List<GalleryImageModel> list = new ArrayList<>();
        GalleryImageModel model = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_GALLERYUPLOAD, new String[]{KEY_ID, KEY_IMAGEURI, KEY_IMAGESIZE, KEY_IMAGEUPLOADSTATUS},
                KEY_IMAGEUPLOADSTATUS + "=? OR " + KEY_IMAGEUPLOADSTATUS + "=? OR " + KEY_IMAGEUPLOADSTATUS + "=?",
                new String[]{String.valueOf(2) , String.valueOf(0), String.valueOf(1)}, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DbHandler.KEY_ID);
            int index2 = cursor.getColumnIndex(DbHandler.KEY_IMAGEURI);
            int index3 = cursor.getColumnIndex(DbHandler.KEY_IMAGESIZE);
            int index4 = cursor.getColumnIndex(DbHandler.KEY_IMAGEUPLOADSTATUS);
            int cid = cursor.getInt(index);
            String name = cursor.getString(index2);
            String card = cursor.getString(index3);
            String code = cursor.getString(index4);
            model = new GalleryImageModel(cid, name, card, code);
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


}
