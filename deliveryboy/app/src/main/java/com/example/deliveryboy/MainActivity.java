package com.example.deliveryboy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText num;
    FirebaseAuth mAuth;
    private int STORAGE_PERMISSION_CODE=1,CAMERA_PERMISSION_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = (EditText) findViewById(R.id.num);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.continue1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num_str = num.getText().toString().trim();
                    if (num_str.isEmpty() || num.length() != 10) {
                        num.setError("the following constraints are not met\n1.number is not empty\n2.number of digits should be 10");
                        num.requestFocus();
                        return;
                    }
                    String PHONENUMBER = num_str;
                    Intent i = new Intent(MainActivity.this, verify_phone_number.class);
                    i.putExtra("PHONENUMBER", PHONENUMBER);
                    startActivity(i);
                }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
 if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            } else
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

        if (mAuth.getCurrentUser() != null) {

            Intent i = new Intent(MainActivity.this, home.class);
            i.putExtra("flag","1");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
