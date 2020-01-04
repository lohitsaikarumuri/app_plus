package com.example.deliveryboy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;


import com.google.firebase.auth.FirebaseAuth;

public class profile_activity extends AppCompatActivity{
    FirebaseAuth mAuth;
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
        d1=(DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
          int id= menuItem.getItemId();
        if(id==R.id.home){
            Intent i=new Intent(profile_activity.this,home.class);
            startActivity(i);
        }
        if(id==R.id.myprofile){
                     Intent i=new Intent(profile_activity.this,pro.class);
                     i.putExtra("number",getIntent().getStringExtra("number"));
                     startActivity(i);

        }
        if(id==R.id.feedback){
            String toS="mars79859@gmail.com";
            String subS="Delivery Boy's issue";

            Intent email=new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL,toS);
            email.putExtra(Intent.EXTRA_EMAIL,new String[]{toS});
            email.putExtra(Intent.EXTRA_SUBJECT,subS);


            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email,"choose app to send mail"));


        }
        if(id==R.id.signout){
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(profile_activity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
             if(id==R.id.myorders){
                 Intent i=new Intent(profile_activity.this,myorders.class);
                 startActivity(i);
             }

        return true;
         }
        });
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
     return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
