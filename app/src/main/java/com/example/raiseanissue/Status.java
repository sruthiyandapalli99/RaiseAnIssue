package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Status extends AppCompatActivity {

    Button AllT,CT;
    ListView myListview;
    ArrayList<String> myarraylist = new ArrayList<>();
    HashMap<Integer, String> mykey = new HashMap<>();
    DatabaseReference issue;
    DatabaseReference issues;
    BottomNavigationView nave;
    ImageView profile,logout;
    String keka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
       // profile = findViewById(R.id.profileicon);
        logout = findViewById(R.id.logouticon);

        nave = findViewById(R.id.naver);
        nave.setSelectedItemId(R.id.status);
       // LoginActivity num12 = new LoginActivity();
      //  String numfor = num12.numfor;
       // Intent intent = getIntent();
        //Bundle extras = getIntent().getExtras();


        //keka = extras.getString("pnum");
       // System.out.println(keka);
       // Log.d("keka", keka);


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


            AllT = findViewById(R.id.AllTickets);
            CT = findViewById(R.id.Ct);
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Status.this, android.R.layout.simple_list_item_1, myarraylist);
            myListview = (ListView) findViewById(R.id.mylist);
            myListview.setAdapter(myadapter);
        final int[] position = {0};
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
                            myadapter.notifyDataSetChanged();
                            mykey.put(position[0]++, key);
                            mykey.get(key);
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
                            Intent intent = new Intent(getApplicationContext(),InProgress.class);
                            //intent.putExtra("keyid",(String) myListview.getItemAtPosition(i));
                            String keys = mykey.get(i);
                            intent.putExtra("keyticket",(String) myListview.getItemAtPosition(i));

                            // Log.d(String.valueOf(Bacgroundapp.this), keys);

                            intent.putExtra("keyid", keys);

                            Bundle extras = intent.getExtras();
                            String extra = extras.getString("keyid");
                            String extra1 = extras.getString("keyticket");
                            startActivity(intent);

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
                            String key = snapshot.getKey().toString();
                            // new ArrayList<>();
                            myarraylist.add(id.getIssueID());
                            myadapter.notifyDataSetChanged();
                            mykey.put(position[0]++, key);
                            mykey.get(key);
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
                            Intent intent = new Intent(getApplicationContext(),Completedissues.class);
                            //intent.putExtra("keyid",(String) myListview.getItemAtPosition(i));
                            String keys = mykey.get(i);
                            intent.putExtra("keyticket",(String) myListview.getItemAtPosition(i));

                            // Log.d(String.valueOf(Bacgroundapp.this), keys);

                            intent.putExtra("keyid", keys);

                            Bundle extras = intent.getExtras();
                            String extra = extras.getString("keyid");
                            String extra1 = extras.getString("keyticket");
                            startActivity(intent);

                        }
                    });

                }
            });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Status.this, LoginActivity.class);
                startActivity(i);
            }
        });
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
