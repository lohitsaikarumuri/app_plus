package com.example.deliveryboy;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;

public class home extends AppCompatActivity implements LocationListener{
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    FirebaseDatabase database;
    public float distance_driver;
    DatabaseReference reference,pRef;
    RecyclerView recyclerView;
    ArrayList<Model> list;
    rvAdapter adapter;
    float distance;
    Location locationA,locationB;
    Double lat,longg;
    LinearLayout linearLayout;
    String[] did;
    Model p;
    String pid;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // while(true) {

            d1 = (DrawerLayout) findViewById(R.id.d1);
            abdt = new ActionBarDrawerToggle(this, d1, R.string.Open, R.string.Close);
            abdt.setDrawerIndicatorEnabled(true);
            d1.addDrawerListener(abdt);
            abdt.syncState();
            NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        list.clear();
            //uploadData();
            nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    //  if(id==R.id.home){
                    //    Intent i=new Intent(home.this,home.class);
                    //  startActivity(i);
                    //}
                    if (id == R.id.myprofile) {
                        Intent i = new Intent(home.this, pro.class);
                        i.putExtra("number", getIntent().getStringExtra("number"));
                        startActivity(i);

                    }
                    if (id == R.id.feedback) {
                        String toS = "mars79859@gmail.com";
                        String subS = "Delivery Boy's issue";

                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, toS);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{toS});
                        email.putExtra(Intent.EXTRA_SUBJECT, subS);


                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "choose app to send mail"));


                    }
                    if (id == R.id.signout) {
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(home.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                    if (id == R.id.myorders) {
                        Intent i = new Intent(home.this, myorders.class);
                        startActivity(i);
                    }

                    return true;
                }
            });
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(home.this);
            list = new ArrayList<Model>();
            recyclerView = (RecyclerView) findViewById(R.id.rv);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            adapter = new rvAdapter(home.this, list);
            llm.setOrientation(RecyclerView.VERTICAL);
            locationA = new Location("");
            locationB = new Location("");
            //while (true){
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(adapter);
            if (ContextCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                acessLocation();

            } else {
                Dexter.withActivity(home.this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                acessLocation();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                                    builder.setTitle("Permission Denied")
                                            .setMessage("permission to access device location is permanently denied you need to go to setting to allow the permissions")
                                            .setNegativeButton("cancel", null)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    intent.setData(Uri.fromParts("package", getPackageName(), null));

                                                }
                                            })
                                            .show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(linearLayout, "You are Offline", Snackbar.LENGTH_SHORT).setAction("Action", null);
                                    View sbView = snackbar.getView();
                                    sbView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }

       // }
    }
    private void acessLocation() {
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
        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(home.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(home.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(home.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(home.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }
    private void getDeviceLocation() {
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
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if(count==10){
                                Toast.makeText(home.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                            }
                            if (mLastKnownLocation != null) {
                                locationB.setLatitude(mLastKnownLocation.getLatitude());
                                locationB.setLongitude(mLastKnownLocation.getLongitude());
                                uploadData();
                                System.out.println("-------" + mLastKnownLocation.getLatitude() + "----------------- " + mLastKnownLocation.getLongitude());

                            }else {
                                getDeviceLocation();
                                count+=1;
                            }
                        } else {
                            Toast.makeText(home.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadData() {
            reference = FirebaseDatabase.getInstance().getReference("orders");
            pRef = FirebaseDatabase.getInstance().getReference("Uploads");

            reference.addValueEventListener(new ValueEventListener() {
                int k, u = 0, m;
                ArrayList<Model> tempModel = new ArrayList<Model>();

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    m = 0;
                    //while (true){u=0;m=0;
                    did = new String[(int) dataSnapshot.getChildrenCount()];
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                  Model m = dataSnapshot1.getValue(Model.class);
//                    tempModel.add(m);
                        pid = dataSnapshot1.child("pid").getValue(String.class);
                        did[m] = dataSnapshot1.child("did").getValue(String.class);
                        tempModel.add(m,dataSnapshot1.getValue(Model.class));
                        m++;

                      //tempModel.add(dataSnapshot1.getValue(Model.class));
                        pRef.child(pid).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                lat = dataSnapshot.child("location").child("l").child("0").getValue(Double.class);
                                longg = dataSnapshot.child("location").child("l").child("1").getValue(Double.class);
                                pRef.removeEventListener(this);
                                if (lat != null && longg != null) {
                                    locationA.setLatitude(lat);
                                    locationA.setLongitude(longg);
                                    distance = locationA.distanceTo(locationB) / 1000;
                                    distance_driver = distance;
                                    System.out.println(distance + "distance2");
                                }
                                System.out.println(distance + "distance1");
                                if (u < m) {
                                    System.out.println(u);
                                    if (did[u].equals("No") && distance <= 10) {
                                        System.out.println(tempModel.get(u).getId());
                                        list.add(tempModel.get(u));
                                    }
                                }
                                u++;
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }

                        //if(dataSnapshot1.child("did").getValue().equals("No") && distance_driver<10) {
                        //    Model p = dataSnapshot1.getValue(Model.class);
                        //list.add(p);
                        //}

//u=0;
//m=0;



                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(home.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastKnownLocation=location;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}

