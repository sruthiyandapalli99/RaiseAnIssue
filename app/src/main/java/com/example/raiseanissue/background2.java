package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class background2 extends AppCompatActivity {
    Button complete;
    TextView describe;
    ImageView imageView;
    EditText msg;
    TextView issuenum;
    String issuekey;
    String issueTick;
    DatabaseReference issue;
    DatabaseReference issue1;
    ImageView im;
    Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background2);
        complete = findViewById(R.id.complete);
        describe = findViewById(R.id.desc);
        imageView = findViewById(androidx.appcompat.R.id.image);
        msg= findViewById(R.id.msg);
        issuenum = findViewById(R.id.issueidnum);
        im= findViewById(R.id.issueimage);
       // StorageReference reference = FirebaseStorage.getInstance().getReference().child("uu");
        issue = FirebaseDatabase.getInstance().getReference().child("issues table");
        issue1 = FirebaseDatabase.getInstance().getReference().child("Completed");


        Intent intent = getIntent ();
        Bundle extras = intent.getExtras();
        issueTick = extras.getString("keyticket");
        issuekey = extras.getString("keyid");
        StorageReference reference = FirebaseStorage.getInstance().getReference().child(issueTick);

        issuenum.setText("Ticket Number"+":" +issueTick);
        issue.orderByChild("issueID").equalTo(issueTick).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                issuetabledetails details = snapshot.getValue(issuetabledetails.class);
                describe.setText("Description"+":"+details.getIssuedetails());







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
            final File localfile = File.createTempFile(  issueTick, "");
            reference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                  // Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                   imageuri = Uri.fromFile(localfile.getAbsoluteFile());
                   // compressedImageFile = Compressor.getDefault(this).compressToFile(actualImageFile);

                    im.setImageURI(imageuri);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               completeissue();
               Intent intent1 = new Intent(getApplicationContext(),Bacgroundapp.class);
               startActivity(intent1);
                issue.child(issuekey).setValue(null);
                


            }
        });

    }

    public void completeissue(){
        String comment = msg.getText().toString();
        CompletedTable fill = new CompletedTable(comment,issueTick);
        issue1.push().setValue(fill);

    }


    }