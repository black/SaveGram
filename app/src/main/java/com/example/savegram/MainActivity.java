package com.example.savegram;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.savegram.Fragments.CliphistoryFragment;
import com.example.savegram.Fragments.GalleryFragment;
import com.example.savegram.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private String DOWNLOAD_TAG = "/?__a=1";
    private RequestQueue requestQueue;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Permission Check
        int PERMISSIONS_ALL = 1;
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO
        };
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this,permissions, PERMISSIONS_ALL);
        }
        // app started
        getFagement(0);
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for (String permission:permissions){
                if (ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            getFagement(item.getItemId());
            return true;
        }
    };
    public void getFagement(int position){
        switch (position) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_dashboard:
                fragment = new GalleryFragment();
                break;
            case R.id.navigation_notifications:
                fragment = new CliphistoryFragment();
                break;
        }
        if(fragment!=null){
            FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragments,fragment);
            fragmentTransaction.commit();
        }
    }

  /*  private String getClipboardURL(){
        Pattern pattern = Patterns.WEB_URL;
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip =  clipboardManager.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return extractUrls(clip.getItemAt(0).coerceToText(this).toString());
        }
        return null;
    }
*/
/*    @SuppressLint("NewApi")
    private void downloadFile(String updateUrl) {
        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/SAVEGRAM/"+DownloadManager.COLUMN_TITLE+".jpg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }

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
    }*/
/*
    public void imageDownloader(){
        String clip = getDownloadLink(getClipboardURL())+DOWNLOAD_TAG;
        mTextMessage.setText(clip);
        getInstaObject(clip);
        Log.d("URL",clip);
    }

    private String getDownloadLink(String url){
        int index=url.lastIndexOf('/');
        return url.substring(0,index);
    }*/
/*
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
    }*/

    @Override
    protected void onResume() {
        super.onResume();
    }
}
