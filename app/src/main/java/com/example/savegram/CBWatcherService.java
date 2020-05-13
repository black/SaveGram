package com.example.savegram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CBWatcherService extends Service {
    private String ClipboardCacheFolderPath=Environment.getExternalStorageState() +"/SAVEGRAM/";
    private TextView mTextMessage;
    private String DOWNLOAD_TAG = "/?__a=1";
    private RequestQueue requestQueue;
    private final String tag = "[[ClipboardWatcherService]] ";

    private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
        // Volley
        requestQueue = Volley.newRequestQueue(this);
        imageDownloader();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File folder = new File(ClipboardCacheFolderPath);
        // ClipboardCacheFolderPath is a predefined constant with the path
        // where the clipboard contents will be written
        if (!folder.exists()) { folder.mkdir(); }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            ClipData cd = cb.getPrimaryClip();
            if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                try {
                    File folder = new File(ClipboardCacheFolderPath);
                    if (!folder.exists()) { folder.mkdir(); }
                    Calendar cal = Calendar.getInstance();
                    String newCachedClip =
                            cal.get(Calendar.YEAR) + "-" +
                                    cal.get(Calendar.MONTH) + "-" +
                                    cal.get(Calendar.DAY_OF_MONTH) + "-" +
                                    cal.get(Calendar.HOUR_OF_DAY) + "-" +
                                    cal.get(Calendar.MINUTE) + "-" +
                                    cal.get(Calendar.SECOND);
                    // The name of the file acts as the timestamp (ingenious, uh?)
                    File file = new File(ClipboardCacheFolderPath + newCachedClip);
                    file.createNewFile();
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
                    bWriter.write((cd.getItemAt(0).getText()).toString());
                    bWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // get recent clipboard text
    // return the text as string
    private String getClipboardURL(){
        Pattern pattern = Patterns.WEB_URL;
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip =  clipboardManager.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(this).toString();
        }
        return null;
    }

    // extract the url
    public static String extractUrls(String input) {
        String FinalURL="";
        String[] words = input.split("\\s+");
        Pattern pattern = Patterns.WEB_URL;
        for(String word : words) {
            if(pattern.matcher(word).find()) {
                if(!word.toLowerCase().contains("http://") && !word.toLowerCase().contains("https://")) {
                    word = "http://" + word;
                }
                FinalURL = word;
            }
        }
        return FinalURL;
    }

    // final url with
    public void imageDownloader(){
        String clip = extractUrls(getClipboardURL());
        int index = clip.lastIndexOf('/');
        String mclip = clip.substring(0,index)+DOWNLOAD_TAG;
        getInstaObject(mclip);
        Log.d("URL",mclip);
    }

    // get request to get JSONObject from url
    private void getInstaObject(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject instaObject = response.getJSONObject("graphql").getJSONObject("shortcode_media");
                    String URI = instaObject.getString("display_url");
                    downloadFile(URI);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TEST",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    // download the file
    @SuppressLint("NewApi")
    private void downloadFile(String updateUrl) {
        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/SAVEGRAM/"+DownloadManager.COLUMN_TITLE+".jpg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }
}

//Reference
// https://stackoverflow.com/questions/22277598/permanently-listen-to-clipboard-changes