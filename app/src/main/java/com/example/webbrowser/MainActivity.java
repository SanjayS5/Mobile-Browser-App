package com.example.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // TODO: 2019-10-03 Create progress bar for web loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());


        Button btn = findViewById(R.id.go);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText urlView = findViewById(R.id.editUrl);
                String url = urlView.getText().toString();

                checkUrl(url, myWebView);

//                myWebView.loadUrl(url);
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

//        ArrayList<HistoryItem> historyItems = new ArrayList<HistoryItem>();
//        historyItems.add(new HistoryItem("example.com"));
//        historyItems.add(new HistoryItem("example2.com"));
//        historyItems.add(new HistoryItem("example3.com"));
//
//        Bundle b = new Bundle();
//        Intent i = new Intent();
//
//        i.putExtras(b);


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.historyMenuOption) {
            Log.d("sanjay", "History menu activated");
            WebView webView = this.findViewById(R.id.webview);
            getBackForwardList(webView);

        }

        return super.onOptionsItemSelected(item);
    }

    public void getBackForwardList(WebView webView){
        WebBackForwardList currentList = webView.copyBackForwardList();
        int currentSize = currentList.getSize();
        for(int i = 0; i < currentSize; ++i)
        {
            WebHistoryItem item = currentList.getItemAtIndex(i);
            String url = item.getUrl();
            Log.d("sanjay", "The URL at index: " + Integer.toString(i) + " is " + url );
        }
    }
}
