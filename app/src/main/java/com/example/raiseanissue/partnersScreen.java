package com.example.raiseanissue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;
import java.util.Random;

public class partnersScreen extends AppCompatActivity {
    Button Dcode1,Dcode2,Dcode3,Dcode4;
    Button Dmap1,Dmap2,Dmap3,Dmap4;
     TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_screen);
        Dcode1= findViewById(R.id.code1);
        Dcode2= findViewById(R.id.code2);
        Dcode3= findViewById(R.id.code3);
       // Dcode4= findViewById(R.id.code4);
        Dmap1 = findViewById(R.id.map1);
        Dmap2 = findViewById(R.id.map2);
        Dmap3 = findViewById(R.id.map3);
       // Dmap4 = findViewById(R.id.map4);
        txt = findViewById(R.id.random);

        Dmap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=54.58324582326001, -1.2381537533420894"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        Dmap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=54.56906690016378, -1.1777306954607436"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });


        Dmap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=54.57674917012187, -1.2292759356249348"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

            }
        });


     /*   Dmap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=54.55096901523138, -1.2997180173152345"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        }); */
        Dcode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomnum();
            }
        });
        Dcode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomnum();
            }
        });
        Dcode3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomnum();
            }
        });
    }

    public void randomnum(){

        Random random = new Random();
        int val = random.nextInt(1000000000-100000000)+100000000;
        txt.setText("ED-" +Integer.toString(val).concat(" is your discount code"));
    }
}