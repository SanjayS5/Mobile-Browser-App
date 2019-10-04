package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebBackForwardList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        Intent intent = getIntent();
//        Serializable wbfl = intent.getSerializableExtra(MainActivity.EXTRA_WBFL);

        Intent intent = this.getIntent();

        ArrayList<String> urlList = intent.getStringArrayListExtra(MainActivity.EXTRA_URL);

        for (String a : urlList) {
            Log.d("sanjay", "URL is " + a);
        }




//        List<WebBackForwardList> wbflObjects = new ArrayList<WebBackForwardList>();
//        for (String key : bundle.keySet()) {
//            Log.d("sanjay", (String) bundle.getSerializable(key));
//            wbflObjects.add((WebBackForwardList)bundle.getSerializable(key));
//        }


    }
}


