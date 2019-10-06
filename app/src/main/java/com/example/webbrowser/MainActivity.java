package com.example.webbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "com.example.webbrowser.EXTRA_URL";

    public ArrayList<String> bookmarkList = new ArrayList<String>();
    public ArrayList<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String key = intent.getStringExtra("url");
        if (key != null) {
            myWebView.loadUrl(key);
        } else {
            myWebView.loadUrl("https://google.com");
        }


        Button btn = findViewById(R.id.go);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText urlView = findViewById(R.id.editUrl);
                String url = urlView.getText().toString();

                checkUrl(url, myWebView);
            }
        });

        Button backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                }
            }
        });

        Button forwardbtn = findViewById(R.id.forwardbtn);

        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myWebView.canGoForward()) {
                    myWebView.goForward();
                }
            }
        });

        Button refreshbtn = findViewById(R.id.refreshbtn);

        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String webUrl = myWebView.getUrl();
                myWebView.loadUrl(webUrl);
            }
        });

    }


    public void checkUrl (String url, WebView myWebView) {
        if (url.contains(" ") || (!url.contains("."))) {
            myWebView.loadUrl("https://www.google.com/search?q=" + url);
        } else {
            boolean result = isValid(url);
            if (result != true) {
                try {
                    url = "http://" + url;
                    myWebView.loadUrl(url);
                } catch (Exception e) {
                    myWebView.loadUrl("https://www.google.com/search?q=" + url);
                }
            } else {
                myWebView.loadUrl(url);
            }
        }
    }

    // Code below is from https://stackoverflow.com/questions/4905075/how-to-check-if-url-is-valid-in-android

    private boolean isValid(String urlString) {
        try {
            URL url = new URL(urlString);
            return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
        } catch (MalformedURLException e) {

        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        WebView webView = this.findViewById(R.id.webview);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.historyMenuOption) {
            Log.d("sanjay", "History menu activated");

            ArrayList<String> urlList = getBackForwardList(webView);

//            Intent intent = new Intent(this, History);
//            intent.putExtra()

            openHistory(urlList);

        } else if (id == R.id.addBookmark) {
            String currentUrl = webView.getUrl();
            bookmarkList.add(currentUrl);
        } else if (id == R.id.bookmarks) {
            openBookmarks(bookmarkList);
        }

        return super.onOptionsItemSelected(item);
    }

    public void openBookmarks(ArrayList<String> bookmarkList) {
        Intent intent = new Intent(this, History.class);
        intent.putStringArrayListExtra(EXTRA_URL, bookmarkList);
        startActivity(intent);
    }

    public ArrayList<String> getBackForwardList(WebView webView){
        WebBackForwardList currentList = webView.copyBackForwardList();

        int currentSize = currentList.getSize();
        for(int i = 0; i < currentSize; ++i) {
            WebHistoryItem item = currentList.getItemAtIndex(i);
            String url = item.getUrl();
            Log.d("sanjay", "The URL at index: " + Integer.toString(i) + " is " + url );
            urls.add(url);
        }

        return urls;
    }

    public void openHistory(ArrayList<String> urlList) {
        Intent intent = new Intent(this, History.class);
        intent.putStringArrayListExtra(EXTRA_URL, urlList);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("sanjay", "On RESUME called");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean toggleJS = sharedPreferences.getBoolean("switch_preference_1", false);
        WebView myWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(toggleJS);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Test", 0);
        Set bookmarksSet = pref.getStringSet("bookmarks", null);
        Set historySet = pref.getStringSet("history", null);

        if (bookmarksSet != null) {
            ArrayList list = new ArrayList(bookmarksSet);
            bookmarkList = list;
        }
        if (historySet != null) {
            ArrayList list2 = new ArrayList(historySet);
            urls = list2;
        }
    }

    public void onStop() {
        super.onStop();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Test", 0);
        SharedPreferences.Editor editor = pref.edit();
        WebView myWebView = findViewById(R.id.webview);
        getBackForwardList(myWebView);
        String currentPage = myWebView.getUrl();
        Set<String> bookmarksSet = new HashSet<String>(bookmarkList);
        Set<String> historySet = new HashSet<String>(urls);
        editor.putStringSet("bookmarks", bookmarksSet);
        editor.putStringSet("history", historySet);
        editor.putString("currentPage", currentPage);
        editor.commit();
    }
}


