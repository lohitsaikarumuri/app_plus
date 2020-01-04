package com.example.deliveryboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class accepted extends AppCompatActivity {
    private StorageReference StorageRef;
    ImageView fi;
    TextView named;
    TextView numberd;
    TextView addressd;
    TextView fp;
    TextView in1;
    TextView iq1;
    String Id;
    String Pid,Cid,name,no,address,received,paid,in,iq;
    String Fid;
    double lat,longi,lat1,longi1;
    Button accepted;
    DatabaseReference cref,fref,pref, db;
    FirebaseDatabase firebaseDatabase;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
Intent i1=getIntent();
        if(i1.getStringExtra("flag").equals("1")) {
            Intent i = new Intent(accepted.this, home.class);
            startActivity(i);
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);
        accepted=(Button)findViewById(R.id.accept);
        Intent i = getIntent();
        Pid=i.getExtras().getString("Pid");
        fi=(ImageView)findViewById(R.id.fi);
        //Cid=getIntent().getStringExtra("Cid");
        // DatabaseReference cref,fref,pref;
        firebaseDatabase=FirebaseDatabase.getInstance();
        fref=firebaseDatabase.getReference("Farmer").child("Profile");
        cref=firebaseDatabase.getReference("customer").child("profile");
        pref=firebaseDatabase.getReference("Uploads");
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        pref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if ((dataSnapshot1.child("id").getValue(String.class)).equals(Pid)) {
                        Fid=dataSnapshot1.child("farmerId").getValue(String.class);
                        in=dataSnapshot1.child("title").getValue(String.class);
                        iq=dataSnapshot1.child("quantity").getValue(String.class);
                        received=dataSnapshot1.child("price").getValue(String.class);
                        paid=dataSnapshot1.child("price").getValue(String.class);
                        lat=dataSnapshot1.child("location").child("l").child("0").getValue(double.class);
                        longi=dataSnapshot1.child("location").child("l").child("1").getValue(double.class);
                        System.out.println(Fid+received);

                    }
                }
                fref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            System.out.println(Fid);
                            if ((dataSnapshot1.child("id").getValue().toString()).equals(Fid)) {
                                name = dataSnapshot1.child("name").getValue(String.class);
                                no = dataSnapshot1.child("mobile").getValue(String.class);
                                address = dataSnapshot1.child("address").getValue(String.class);
                                System.out.println(name + no + address + "hello");
                                named=(TextView)findViewById(R.id.name2);
                                numberd=(TextView)findViewById(R.id.number2);
                                addressd=(TextView)findViewById(R.id.add2);


                            }

                        }
                        named.setText(name);
                        numberd.setText(no);
                        addressd.setText(address);
                        StorageRef = storage.getReferenceFromUrl("gs://marsmahesh-158f1.appspot.com/farmerAadhar/" + no + ".jpg");
                        StorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(fi);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                fp=(TextView)findViewById(R.id.fp);
                fp.setText(received);
                fp=(TextView)findViewById(R.id.cp);
                fp.setText(paid);
                in1=(TextView)findViewById(R.id.in1);
                in1.setText(in);
                iq1=(TextView)findViewById(R.id.iq1);
                iq1.setText(iq);
                findViewById(R.id.nf).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strUri = "http://maps.google.com/maps?q=loc:" + lat+ "," + longi + " (" + "Label which you want" + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String x=getIntent().getStringExtra("Cid");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println(x+" x");
                    if ((dataSnapshot1.child("id").getValue().toString()).equals(x)) {
                        name = dataSnapshot1.child("name").getValue(String.class);
                        no = dataSnapshot1.child("mobile").getValue(String.class);
                        address = dataSnapshot1.child("address").getValue(String.class);
                        lat1=dataSnapshot1.child("location").child("l").child("0").getValue(double.class);
                        longi1=dataSnapshot1.child("location").child("l").child("1").getValue(double.class);
                        System.out.println(name + no + address + "hello");
                        named=(TextView)findViewById(R.id.cname2);
                        numberd=(TextView)findViewById(R.id.cnum2);
                        addressd=(TextView)findViewById(R.id.cadd2);


                    }

                }
                named.setText(name);
                numberd.setText(no);
                addressd.setText(address);
                findViewById(R.id.nc).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strUri = "http://maps.google.com/maps?q=loc:" + lat1+ "," + longi1 + " (" + "Label which you want" + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
