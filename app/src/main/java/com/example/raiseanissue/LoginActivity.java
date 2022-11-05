package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.raiseanissue.RegistrationScreen;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LoginActivity extends AppCompatActivity {
      EditText ph,passcode;
      Button signin, newUser;
      DatabaseReference databaseReference;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ph = findViewById(R.id.Phonenumber);
        passcode = findViewById(R.id.passcode);
        signin = findViewById(R.id.loginbtn);
        newUser = findViewById(R.id.register);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("userdetails");
        List<userTable> list = new ArrayList();

        Query que = databaseReference.orderByChild("phone").equalTo(ph.getText().toString());

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(ph.getText().toString().isEmpty() || passcode.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }

                    //Query que = databaseReference.orderByChild("phone").equalTo(ph.getText().toString());

                else
                {



                    if(checkexist()){
                   databaseReference.orderByChild("phone").equalTo(ph.getText().toString()).addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                         if(snapshot.hasChildren()) {

                             userTable mob = snapshot.getValue(userTable.class);
                             String mob1 = mob.getPass();


                             if (mob1.equals(passcode.getText().toString())) {

                                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                 startActivity(intent);
                             } else {

                                 Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                             }

                         }
                         else
                         {
                             Toast.makeText(LoginActivity.this, "No account", Toast.LENGTH_SHORT).show();
                         }
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
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
                    }










                }




            }
        });


        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationScreen.class);
                startActivity(intent);
            }
        });


    }
    public Boolean checkexist(){

        databaseReference.orderByChild("phone").equalTo(ph.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    flag =0;
                }
                else
                    flag =1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(flag ==0)
        {
            return true;
        }
        else
            return false;
    }


}