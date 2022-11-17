package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background2);
        complete = findViewById(R.id.complete);
        describe = findViewById(R.id.desc);
        imageView = findViewById(androidx.appcompat.R.id.image);
        msg= findViewById(R.id.msg);
        issuenum = findViewById(R.id.issueidnum);
        issue = FirebaseDatabase.getInstance().getReference().child("issues table");
        issue1 = FirebaseDatabase.getInstance().getReference().child("Completed");


        Intent intent = getIntent ();
        Bundle extras = intent.getExtras();
        issueTick = extras.getString("keyticket");
        issuekey = extras.getString("keyid");

        issuenum.setText("Ticket Number"+":" +issueTick);
        issue.orderByChild("issueID").equalTo(issueTick).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                issuetabledetails details = snapshot.getValue(issuetabledetails.class);
                describe.setText("Description"+":"+details.getIssuedetails());
                issue.child(issuekey).setValue(null);






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

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               completeissue();
               Intent intent1 = new Intent(getApplicationContext(),Bacgroundapp.class);
               startActivity(intent1);



            }
        });

    }

    public void completeissue(){
        String comment = msg.getText().toString();
        CompletedTable fill = new CompletedTable(comment,issueTick);
        issue1.push().setValue(fill);

    }

}