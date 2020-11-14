package com.example.saveme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText etLocation,etMsg,etNumber;
    Button btLocation,skHelp,aboutApp,setPhnNum,emHelp;
    double latitude,longitude;
    boolean detector=false;
    List<Address> addresses;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String address;
    Location gps_loc = null, network_loc = null, final_loc = null,passive_loc=null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etLocation = findViewById(R.id.location);
        etMsg= findViewById(R.id.txtMessage);
        etNumber= findViewById(R.id.phnNumber);
        setPhnNum= findViewById(R.id.setEmergencyNumber);
        btLocation = findViewById(R.id.setLocation);
        emHelp= findViewById(R.id.emergencyHelp);
        aboutApp= findViewById(R.id.aboutApp);
        sharedPreferences=getSharedPreferences("setNum",Context.MODE_PRIVATE);
        skHelp= findViewById(R.id.seekHelp);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            String n1,n2,n3,n4,n5,name;
            n1=bundle.getString("NUM1");
            n2=bundle.getString("NUM2");
            n3=bundle.getString("NUM3");
            n4=bundle.getString("NUM4");
            n5=bundle.getString("NUM5");
            name=bundle.getString("NAME");
            editor=sharedPreferences.edit();
            editor.putString("N1",n1);
            editor.putString("N2",n2);
            editor.putString("N3",n3);
            editor.putString("N4",n4);
            editor.putString("N5",n5);
            editor.putString("NAME1",name);
            editor.apply();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
               if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_NETWORK_STATE)!=PackageManager.PERMISSION_GRANTED &&
                       ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_WIFI_STATE)!=PackageManager.PERMISSION_GRANTED)
                {
                    latitude=0.01;
                    longitude=0.01;
                }
                for(;;){
                  try{
                    gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    network_loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    passive_loc=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                }catch (Exception e){
                    e.printStackTrace();
                }
                 if(gps_loc!=null){
                        final_loc=gps_loc;
                        longitude=final_loc.getLongitude();
                        latitude= final_loc.getLatitude();
                    }
                    else if(network_loc!=null)
                    {
                        final_loc=network_loc;
                        latitude=final_loc.getLatitude();
                        longitude=final_loc.getLongitude();
                    }
                    else if(passive_loc!=null)
                    {
                        final_loc=passive_loc;
                        latitude=final_loc.getLatitude();
                        longitude=final_loc.getLongitude();
                    }
                    try {
                        Geocoder geocoder =new Geocoder(getApplicationContext(), Locale.getDefault());
                        addresses=geocoder.getFromLocation(latitude,longitude,1);
                        if(addresses!=null&&addresses.size()>0)
                        {
                            address=addresses.get(0).getAddressLine(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try{
                        Thread.sleep(5000);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
       btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLocation.setText(address);
            }
        });
        skHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dngrMsg=etMsg.getText().toString();
                    SmsManager smsmngr = SmsManager.getDefault();
                    smsmngr.sendTextMessage(etNumber.getText().toString(),null,"Hello I am "+sharedPreferences.getString("NAME1","one of your intimate")+" "+dngrMsg+"\n\n"+"My LOcation: "+etLocation.getText().toString(),null,null);
                    Toast.makeText(MainActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Sorry, Can't Contact To Your Helping Hand",Toast.LENGTH_SHORT).show();
                }
            }
        });
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,About_Phone_Activity.class);
                startActivity(intent);
            }
        });
        setPhnNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this, EnterInforActivity.class);
                startActivity(intent1);
            }
        });
        emHelp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if(address!=null) {
                        etLocation.setText(address);
                    }
                    String dngrMsg1=etMsg.getText().toString();
                    SmsManager smsmngr1 = SmsManager.getDefault();
                    smsmngr1.sendTextMessage(sharedPreferences.getString("N1", "000"),null,"Hello! I am "+sharedPreferences.getString("NAME1","one of your intimate")+" "+dngrMsg1+"\n\n"+"My LOcation: "+etLocation.getText().toString(),null,null);
                    smsmngr1.sendTextMessage(sharedPreferences.getString("N2", "000"),null,"Hello I am "+sharedPreferences.getString("NAME1","one of your intimate")+" "+dngrMsg1+"\n\n"+"My LOcation: "+etLocation.getText().toString(),null,null);
                    smsmngr1.sendTextMessage(sharedPreferences.getString("N3", "000"),null,"Hello I am "+sharedPreferences.getString("NAME1","one of your intimate")+" "+dngrMsg1+"\n\n"+"My LOcation: "+etLocation.getText().toString(),null,null);
                    smsmngr1.sendTextMessage(sharedPreferences.getString("N4", "000"),null,"Hello I am "+sharedPreferences.getString("NAME1","one of your intimate")+" "+dngrMsg1+"\n\n"+"My LOcation: "+etLocation.getText().toString(),null,null);
                    smsmngr1.sendTextMessage(sharedPreferences.getString("N5", "000"),null,"Hello I am "+sharedPreferences.getString("NAME1","one of your intimate")+" "+dngrMsg1+"\n\n"+"My LOcation: "+etLocation.getText().toString(),null,null);
                    Toast.makeText(MainActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Sorry, Can't Contact To Your Helping Hand",Toast.LENGTH_SHORT).show();
                }
                detector=true;
                return true;
            }
        });
        emHelp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if(detector) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "01916176643"));
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
    }
}
