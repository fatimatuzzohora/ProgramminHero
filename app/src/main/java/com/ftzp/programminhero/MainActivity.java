package com.ftzp.programminhero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment firstFragment = new FirstFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, firstFragment)
                .commit();
    }
}