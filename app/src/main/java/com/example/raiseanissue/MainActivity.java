package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static  final int PERMISSION_CODE =1001;
    public static  final int IMAGE_CAPTURE_CODE=1000;
    public static final int IMAGE_PICK_CODE =1000;

    Button capturebtn;
    ImageView image;
    Uri image_uri;
    Uri imager;
    String status;
    TextView txt;
    TextView number;
    Button submit;
    ImageView profile;

    ImageView logout;
    TextView describe;
    String imageexists;
    String pic;
    Button upload;
    FloatingActionButton deleteimage;
    DatabaseReference issues;
    DatabaseReference issues1;
    int count =0;
    String keka;
    BottomNavigationView nave;
    StorageReference ref;



    int val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setTitle("EuphonyHomes");



        image = findViewById(R.id.image_View);
        number = findViewById(R.id.ph);
       // profile = findViewById(R.id.profileicon);
        logout = findViewById(R.id.logouticon);
        logout.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


      //  LoginActivity num = new LoginActivity();
      //  String numfor = num.numfor;
      //  Intent intent = getIntent();
       // Bundle extras = intent.getExtras();


       //  keka = extras.getString("keynumber");

       // number.setText(keka);
        nave = findViewById(R.id.naver);
        nave.setSelectedItemId(R.id.homee);
        nave.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case  R.id.homee:
                       /* Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);*/
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


        txt = findViewById(R.id.random);
        capturebtn = findViewById(R.id.takeaphoto);
        submit = findViewById(R.id.button);
        describe = findViewById(R.id.discription);
        issues = FirebaseDatabase.getInstance().getReference().child("issues table");
        issues1 = FirebaseDatabase.getInstance().getReference().child("In process");
        upload = findViewById(R.id.uploadaphoto);
        deleteimage = findViewById(R.id.floatingActionButton2);
        deleteimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //image.setVisibility(View.GONE);
                image.setImageDrawable(null);
                count =0;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if((count ==0) || (describe.getText().toString().isEmpty())) {
                  Toast.makeText(MainActivity.this, "enter all fields", Toast.LENGTH_SHORT).show();


              }
               else
              {
                  Random random = new Random();
                  val = random.nextInt(1000000000-100000000)+100000000;
                  txt.setText("EI-" +Integer.toString(val).concat(" is your ticket ID, please note for furture communication. Thnaks for reporting!"));
                  //describe.setText("");
                  insertissuesdata();
                  describe.setText("");
                  count =0;

                  image.setImageDrawable(null);
              }


            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        //permission not enabled so request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE);

                    }
                    else
                    {
                        //permission already granted
                        PickImageFromGallery();
                    }
                }
                else
                {
                    //system os is <marsh
                    PickImageFromGallery();
                }
            }
        });

        //onbuttonclick
        capturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        //permission not enabled so request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE);

                    }
                    else
                    {
                        //permission already granted
                        OpenCamera();
                    }
                }
                else
                {
                    //system os is <marsh
                    OpenCamera();
                }
            }
           /* Random random = new Random();
            int val = random.nextInt(10000);
                txt.setText(Integer.toString(val).concat(" is your ticket ID, please note. We will send you a mail once the issue is resolved, Thnaks for reporting"));*/

        });
    }
    private void PickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void OpenCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //camera intent
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraintent, 2);

    }
   //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    OpenCamera();
                    PickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1) {
            if (resultCode == RESULT_OK || resultCode == IMAGE_PICK_CODE) {
                //pic = image_uri.toString();

                // image.setImageURI(image_uri);
                image.setImageURI(data.getData());
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("kk");
                ref.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity.this, "uploaded", Toast.LENGTH_SHORT).show();

                    }
                });
                pic = data.getData().toString();
                count =1;

            }
        }
        else if(requestCode==2){
         if (resultCode == RESULT_OK  ) {
             //setting image captured to imageview
             pic = image_uri.toString();
             image.setImageURI(image_uri);
             //imager = image_uri;
             ref = FirebaseStorage.getInstance().getReference().child("image");
             ref.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Toast.makeText(MainActivity.this, "uploaded", Toast.LENGTH_SHORT).show();

                 }
             });



             count =1;

         }
        }
    }



    public void insertissuesdata(){
        String Describeissue = describe.getText().toString();
        String imageID = pic;
        String TicketNum = "EI-"+ Integer.toString(val);
        String pho = keka;
        Intent i = new Intent(getApplicationContext(), Status.class);
        i.putExtra("pnum",pho);
        ref = FirebaseStorage.getInstance().getReference().child("image");
        try {
            final File localfile = File.createTempFile("image", "");
            ref.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    // Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    imager = Uri.fromFile(localfile.getAbsoluteFile());
                    // compressedImageFile = Compressor.getDefault(this).compressToFile(actualImageFile);

                    //im.setImageURI(imageuri);
                    StorageReference reference = FirebaseStorage.getInstance().getReference().child(TicketNum);
                    reference.putFile(imager).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }





        // issuetabledetails details2 = new issuetabledetails(num.numfor);
        //issues1.push().setValue(details2);



        issuetabledetails details = new issuetabledetails(Describeissue,imageID,TicketNum);
        issues.push().setValue(details);


    }




}