package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Status extends AppCompatActivity {

    Button AllT,CT;
    ListView myListview;
    ArrayList<String> myarraylist = new ArrayList<>();
    DatabaseReference issue;
    DatabaseReference issues;
    BottomNavigationView nave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

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
                       /* Intent i3 = new Intent(getApplicationContext(), Status.class);
                        startActivity(i3);
                        overridePendingTransition(0,0);*/
                        return true;
                }



                return false;
            }
        });


         /*   AllT = findViewById(R.id.AllTickets);
            CT = findViewById(R.id.Ct);
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Status.this, android.R.layout.simple_list_item_1, myarraylist);
            myListview = (ListView) findViewById(R.id.mylist);
            myListview.setAdapter(myadapter);
            issue = FirebaseDatabase.getInstance().getReference().child("issues table");
            issues = FirebaseDatabase.getInstance().getReference().child("Completed");
            AllT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //issue = FirebaseDatabase.getInstance().getReference().child("issues table");
                    myarraylist.clear();
                    myadapter.clear();

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

                }
            });
            CT.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    myarraylist.clear();
                    myadapter.clear();

                    issues.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            //for(DataSnapshot data : snapshot.getChildren()){
                            CompletedTable id = snapshot.getValue(CompletedTable.class);
                            //String key = snapshot.getKey().toString();
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

                }
            });*/
        }

        public void stat(){
            AllT = findViewById(R.id.AllTickets);
            CT = findViewById(R.id.Ct);
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Status.this, android.R.layout.simple_list_item_1, myarraylist);
            myListview = (ListView) findViewById(R.id.mylist);
            myListview.setAdapter(myadapter);
            issue = FirebaseDatabase.getInstance().getReference().child("issues table");
            issues = FirebaseDatabase.getInstance().getReference().child("Completed");
            AllT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //issue = FirebaseDatabase.getInstance().getReference().child("issues table");
                    myarraylist.clear();
                    myadapter.clear();

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

                }
            });
            CT.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    myarraylist.clear();
                    myadapter.clear();

                    issues.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            //for(DataSnapshot data : snapshot.getChildren()){
                            CompletedTable id = snapshot.getValue(CompletedTable.class);
                            //String key = snapshot.getKey().toString();
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

                }
            });
        }

    }
