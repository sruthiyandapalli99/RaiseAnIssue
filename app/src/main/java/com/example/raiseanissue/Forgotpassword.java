package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Forgotpassword extends AppCompatActivity {
    EditText ph,passcode,passcode1;
    EditText otp;
    Button sendOTP,SubmitBtn;
    DatabaseReference userinfo;
    private  boolean otpSent = false;
    private  String id;
    private String countryCode = "+44";
    int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        ph = findViewById(R.id.Phonenumber);
        passcode = findViewById(R.id.passcode);
        passcode1 = findViewById(R.id.passcode1);
        otp = findViewById(R.id.OTP);
        sendOTP = findViewById(R.id.SendOTP);
        FirebaseApp.initializeApp(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        userinfo = FirebaseDatabase.getInstance().getReference().child("userdetails");
        SubmitBtn = findViewById(R.id.submit);


        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpSent){



                    String getOTP = otp.getText().toString();
                    System.out.println(getOTP);

                    if(id.isEmpty()){
                        Toast.makeText(Forgotpassword.this, "unable to verify otp", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,getOTP);
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    FirebaseUser userDetails = task.getResult().getUser();
                                    Toast.makeText(Forgotpassword.this, "Verified", Toast.LENGTH_SHORT).show();
                                    flag = 1;
                                }
                                else{
                                    Toast.makeText(Forgotpassword.this, "something went wrong" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else
                {
                    String getMobile = ph.getText().toString();

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(countryCode+""+getMobile)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(Forgotpassword.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                    Toast.makeText(Forgotpassword.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(Forgotpassword.this, "something went wrong1"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    //requestPermissions();
                                    //new OTPReceiver().setEditText_otp(otp);

                                    otp.setVisibility(View.VISIBLE);

                                    sendOTP.setText("Verify OTP");

                                    id =s;
                                    otpSent = true;
                                }
                            }).build();
                    //requestPermissions();
                    // new OTPReceiver().setEditText_otp(otp);
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });


        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ph.getText().toString().isEmpty()||passcode.getText().toString().isEmpty()||passcode1.getText().toString().isEmpty()){

                    Toast.makeText(Forgotpassword.this, "Enter all fields!", Toast.LENGTH_SHORT).show();

                }

                else
                {
                    if(passcode.getText().toString().length()>6 ||isValidPassword(passcode.getText().toString())){
                        if(passcode.getText().toString().equals(passcode1.getText().toString())){
                            userinfo.orderByChild("phone").equalTo(ph.getText().toString()).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    userTable details = snapshot.getValue(userTable.class);
                                    String pho = details.getPhone().toString();
                                    String pa = details.getPass().toString();
                                    String us = details.getUser().toString();

                                    userTable use = new userTable(us,pho,passcode.getText().toString());
                                    userinfo.push().setValue(use);
                                   // details.getPass().replace(details.getPass().toString(), passcode.getText().toString());



                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);



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
                }

        }
        });
    }









    public static boolean isValidPassword(final String s) {

        Pattern pattern;
        Matcher matcher;
        // final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
        //pattern = Pattern.compile(PASSWORD_PATTERN);
        // matcher = pattern.matcher(password);

        //return matcher.matches();
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();

    }
}
