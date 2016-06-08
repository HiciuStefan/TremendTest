package com.tremend.testtremend.UI;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tremend.testtremend.R;

import java.io.File;

public class BrowserActivity extends AppCompatActivity {

    Button rootStartButton;
    Button musicStartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        rootStartButton = (Button) findViewById(R.id.rootStart);
        rootStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Environment.getRootDirectory().toString());
            }
        });
        musicStartButton = (Button) findViewById(R.id.musicStart);
        musicStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
            }
        });

    }

    private void start(String s) {
        musicStartButton.setVisibility(View.GONE);
        rootStartButton.setVisibility(View.GONE);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        args.putString(BrowseFragment.PATH, s);
        fragment.setArguments(args);
        fm.beginTransaction()
                .replace(R.id.main_container, fragment, BrowseFragment.class.getCanonicalName())
                .commit();


    }


}
