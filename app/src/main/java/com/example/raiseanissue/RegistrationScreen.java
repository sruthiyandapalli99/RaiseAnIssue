package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationScreen extends AppCompatActivity {

     EditText Phonenum, Pass, ConfirmPass,otp,name;
     Button sendOTP, SubmitBtn;
     private  boolean otpSent = false;
     private  String id;
     private String countryCode = "+44";
     DatabaseReference userinfo;
     int flag =0;
     String PhoneNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        name = findViewById(R.id.UserName);
        Phonenum = findViewById(R.id.Phone);
        Pass = findViewById(R.id.Password);
        ConfirmPass = findViewById(R.id.Password2);
        otp = findViewById(R.id.OTP);
        sendOTP = findViewById(R.id.SendOTP);
        FirebaseApp.initializeApp(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        userinfo = FirebaseDatabase.getInstance().getReference().child("userdetails");
        SubmitBtn = findViewById(R.id.submit);




        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((name.getText().toString().isEmpty() || Phonenum.getText().toString().isEmpty()
                        ||Pass.getText().toString().isEmpty()|| ConfirmPass.getText().toString().isEmpty())){
                    Toast.makeText(RegistrationScreen.this, "Enter all fields", Toast.LENGTH_SHORT).show();

                }
                else {
                       if((Pass.getText().toString().length()>6) || isValidPassword(Pass.getText().toString())) {

                           if ((Pass.getText().toString()).equals(ConfirmPass.getText().toString())) {


                               userinfo.orderByChild("phone").equalTo(Phonenum.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {


                                       if (snapshot.exists()) {
                                           Toast.makeText(RegistrationScreen.this, "User already exists! Click on Login", Toast.LENGTH_SHORT).show();

                                       } else {
                                           user();
                                           flag = 0;
                                           name.setText("");
                                           Pass.setText("");
                                           Phonenum.setText("");
                                           ConfirmPass.setText("");
                                           Toast.makeText(RegistrationScreen.this, "Registered succesfully", Toast.LENGTH_SHORT).show();
                                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                           startActivity(intent);

                                       }

                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {
                                       Toast.makeText(RegistrationScreen.this, "Error", Toast.LENGTH_SHORT).show();
                                       Toast.makeText(RegistrationScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               });


                           } else {

                               Toast.makeText(RegistrationScreen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                           }
                       }
                       else
                       {
                           Toast.makeText(RegistrationScreen.this, "Password Not valid", Toast.LENGTH_SHORT).show();
                       }
                }
            }
        });

       // new OTPReceiver().setEditText_otp(otp);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpSent){



                    String getOTP = otp.getText().toString();
                    System.out.println(getOTP);

                    if(id.isEmpty()){
                        Toast.makeText(RegistrationScreen.this, "unable to verify otp", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,getOTP);
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    FirebaseUser userDetails = task.getResult().getUser();
                                    Toast.makeText(RegistrationScreen.this, "Verified", Toast.LENGTH_SHORT).show();
                                    flag = 1;
                                }
                                else{
                                    Toast.makeText(RegistrationScreen.this, "something went wrong" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else
                {
                   String getMobile = Phonenum.getText().toString();

                   PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(countryCode+""+getMobile)
                           .setTimeout(60L, TimeUnit.SECONDS)
                           .setActivity(RegistrationScreen.this)
                           .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                               @Override
                               public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                   Toast.makeText(RegistrationScreen.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onVerificationFailed(@NonNull FirebaseException e) {
                                   Toast.makeText(RegistrationScreen.this, "something went wrong1"+ e.getMessage(), Toast.LENGTH_SHORT).show();
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





    }

    public void user(){
        String Name = name.getText().toString();
        PhoneNum = Phonenum.getText().toString();
        String Password  = Pass.getText().toString();

        userTable details = new userTable(Name,PhoneNum,Password);
        userinfo.push().setValue(details);

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
