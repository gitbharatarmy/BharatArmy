package com.bharatarmy.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bharatarmy.Adapter.MoviePosterAdapter;
import com.bharatarmy.Models.MoviePoster;
import com.bharatarmy.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;


public class MainActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView recyclerView;
    MoviePosterAdapter moviePosterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;


    }

    // Api calling GetImageGalleryData
    private final List readMoviesDetails() {
        ArrayList moviePosters = new ArrayList();
        InputStream inputStream = this.getResources().openRawResource(R.raw.sample_data);
        InputStreamReader stream = new InputStreamReader(inputStream);
        Reader var5 = (Reader)stream;
        short var6 = 8192;
        Closeable var23 = (Closeable)(var5 instanceof BufferedReader ? (BufferedReader)var5 : new BufferedReader(var5, var6));
        Throwable var25 = (Throwable)null;

        String var26;
        try {
            BufferedReader p1 = (BufferedReader)var23;
            boolean var8 = false;
            var26 = TextStreamsKt.readText((Reader)p1);
        } catch (Throwable var20) {
            var25 = var20;
            throw var20;
        } finally {
            CloseableKt.closeFinally(var23, var25);
        }
        String response = var26;
        try {
            JSONArray movieArray = new JSONArray(response);
            Iterable $receiver$iv = (Iterable)RangesKt.until(0, movieArray.length());
            Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
            Iterator var9 = $receiver$iv.iterator();

            while(var9.hasNext()) {
                int item$iv$iv = ((IntIterator)var9).nextInt();
                boolean var12 = false;
                JSONObject var16 = movieArray.getJSONObject(item$iv$iv);
                destination$iv$iv.add(var16);
            }

            $receiver$iv = (Iterable)((List)destination$iv$iv);
            Iterator var28 = $receiver$iv.iterator();

            Collection var10000;
            while(var28.hasNext()) {
                Object item$iv = var28.next();
                var10000 = (Collection)moviePosters;
                JSONObject it = (JSONObject)item$iv;
                Collection var15 = var10000;
                boolean var32 = false;
                String var10002 = it.getString("name");
                Intrinsics.checkExpressionValueIsNotNull(var10002, "it.getString(\"name\")");
                String var10003 = it.getString("imageUrl");
                Intrinsics.checkExpressionValueIsNotNull(var10003, "it.getString(\"imageUrl\")");
                MoviePoster var33 = new MoviePoster(var10002, var10003, it.getInt("width"), it.getInt("height"));
                var15.add(var33);
            }

            var10000 = (Collection)moviePosters;
        } catch (JSONException var22) {
            var22.printStackTrace();
        }
        return (List)moviePosters;
    }
}
