package com.example.raiseanissue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class wel extends AppCompatActivity {

    BottomNavigationView nav;
    CheckStatus checkStatus = new CheckStatus();
    RaiseIssue raiseIssue = new RaiseIssue();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel);

        nav = findViewById(R.id.nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.wel, checkStatus).commit();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.checkStatus:
                        getSupportFragmentManager().beginTransaction().replace(R.id.wel, checkStatus).commit();
                        return true;
                    case R.id.raiseIssue:
                        getSupportFragmentManager().beginTransaction().replace(R.id.wel, raiseIssue).commit();
                        return true;
                }
                return false;
            }
        });






    }


}