package com.example.raiseanissue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class NearPlaces extends AppCompatActivity {
Spinner spType;
Button btfind;
BottomNavigationView nave;
SupportMapFragment supportMapFragment;
FusedLocationProviderClient client;
double currentLat=0,currentLong=0;
GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_places);
        spType = findViewById(R.id.sp_type);
        btfind = findViewById(R.id.bt_find);
        nave = findViewById(R.id.naver);
        nave.setSelectedItemId(R.id.nearby);
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
                        /*Intent i2 = new Intent(getApplicationContext(), NearPlaces.class);
                        startActivity(i2);
                        overridePendingTransition(0,0);*/
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

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        String[] placeTypeList = {"fillingstation","bank","hospital","cinemas","restaurant"};
        String[] placeNameList = {"Filling Stations","Banks", "Hospitals", "Movie Theaters","Restaurants"};
        spType.setAdapter(new ArrayAdapter<>(NearPlaces.this, android.R.layout.simple_spinner_dropdown_item,placeNameList));

        //initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);
        //checking permission
       if (ActivityCompat.checkSelfPermission(NearPlaces.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission is granted call method
            getCurrentLoc();
    }
       else{
           ActivityCompat.requestPermissions(NearPlaces.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
       }
       btfind.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int i = spType.getSelectedItemPosition();
               String url = "https://maps.googleapis.com/maps/api/place/search/json"+
                       "?location=" +currentLat +","+currentLong+
                       "&radius=5000" +
                       "&types=" + placeTypeList[i]+
                       "&sensor=true"+
                       "&key=" +"AIzaSyDkowLTVqi3eoG5ALcsFjv4XhZjopVFZEU";
                       //getResources().getString(R.string.google_map_key);
               new PlaceTask().execute(url);
           }
       });
}

    private void getCurrentLoc() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
           return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when success
                if(location!= null){
                 currentLat = location.getLatitude();
                 currentLong = location.getLongitude();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //intialize lat long
                            map = googleMap;
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat,currentLong),10));
                        }
                    });
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLoc();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                 data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {

            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line= "";
        while((line = reader.readLine()) !=null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;


    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParsar jsonParsar = new JsonParsar();
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParsar.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            map.clear();
            for(int i=0; i<hashMaps.size();i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");
                LatLng latLng = new LatLng(lat,lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                map.addMarker(options);
            }
        }
    }
}