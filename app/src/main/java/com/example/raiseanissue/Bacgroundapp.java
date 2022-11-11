package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Bacgroundapp extends AppCompatActivity {
    ListView myListview;
    ArrayList<String> myarraylist = new ArrayList<>();
    DatabaseReference issue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacgroundapp);



        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Bacgroundapp.this,android.R.layout.simple_list_item_1,myarraylist);
        myListview = (ListView) findViewById(R.id.list);
        myListview.setAdapter(myadapter);
        issue= FirebaseDatabase.getInstance().getReference().child("issues table");
        issue.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //for(DataSnapshot data : snapshot.getChildren()){
                    issuetabledetails id = snapshot.getValue(issuetabledetails.class);
                    String key = snapshot.getKey().toString();
                    // new ArrayList<>();
                    myarraylist.add(id.getIssueID());
                    myadapter.notifyDataSetChanged();
                //}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // myadapter.notifyDataSetChanged();
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

       myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getApplicationContext(),background2.class);
               intent.putExtra("keyid",(String) myListview.getItemAtPosition(i));

               Bundle extras = intent.getExtras();
               String extra = extras.getString("keyid");
               startActivity(intent);

           }
       });

    }
}