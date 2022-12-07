package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userProfile extends AppCompatActivity {
    String s;
    TextView mn,un,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
         Bundle extras = intent.getExtras();
         s = extras.getString("pnum");
         DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("userdetails");

         mn = findViewById(R.id.mn);
         un = findViewById(R.id.un);
         pw = findViewById(R.id.pw);
         ref.orderByChild("phone").equalTo(s).addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                userTable details = snapshot.getValue(userTable.class);
                  mn.setText(details.getPhone());
                  un.setText(details.getUser());
                  pw.setText(details.getPass());
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



    }
}