package com.bharatarmy.Utility;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.UploadService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class firebaseutils {

    public static List<GalleryImageModel> UpladingFiles;



    public static void AddFiletoQueue(String imageuri, String imagesize, String UploadStatus,Context currentcontext) {

    }

    public static void UpdateStatus(GalleryImageModel obj, String NewStatus,Context currentcontext)
    {


    }

    public static void RemoveModel(GalleryImageModel obj)
    {

    }
}
