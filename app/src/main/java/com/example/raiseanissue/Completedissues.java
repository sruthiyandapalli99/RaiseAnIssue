package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Completedissues extends AppCompatActivity {

    TextView describe,stat;
    ImageView imageview;
    TextView issuenum;
    String issuekey;
    String issueTick;
    DatabaseReference issue1;
    BottomNavigationView nave;
    Uri imageuri;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedissues);

        Intent intent = getIntent ();
        Bundle extras = intent.getExtras();
        issueTick = extras.getString("keyticket");
        issuekey = extras.getString("keyid");
        logout = findViewById(R.id.logouticon);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        nave = findViewById(R.id.naver);
        nave.setSelectedItemId(R.id.status);
        nave.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case  R.id.homee:
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.partner:
                        Intent i1 = new Intent(getApplicationContext(),partnersScreen.class);
                        startActivity(i1);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nearby:
                        Intent i2 = new Intent(getApplicationContext(), NearPlaces.class);
                        startActivity(i2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.status:
                        Intent i3 = new Intent(getApplicationContext(), Status.class);
                        startActivity(i3);
                        overridePendingTransition(0,0);
                        return true;
                }



                return false;
            }
        });
        // DatabaseReference issue = FirebaseDatabase.getInstance().getReference().child("issues Table");



        describe = findViewById(R.id.desc1);
        imageview = findViewById(R.id.issueimage);
        issuenum = findViewById(R.id.issueidnum);
        stat = findViewById(R.id.stat1);
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("image");
        DatabaseReference issue = FirebaseDatabase.getInstance().getReference().child("Completed");

        issuenum.setText("Ticket Number"+":" +issueTick);

        issue.orderByChild("issueID").equalTo(issueTick).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CompletedTable details = snapshot.getValue(CompletedTable.class);
                describe.setText("Admin Message"+":"+details.getMessage());



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
            final File localfile = File.createTempFile("image", "");

            reference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    // Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    imageuri = Uri.fromFile(localfile.getAbsoluteFile());
                    // compressedImageFile = Compressor.getDefault(this).compressToFile(actualImageFile);

                    imageview.setImageURI(imageuri);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        stat.setText("Completed");





    }


}
