package com.example.deliveryboy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class verify_phone_number extends AppCompatActivity {
    private String VERIFICATIONID;
    private FirebaseAuth mAuth;
    private EditText otp;
     public int flag = 0;
     String u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_num);
        mAuth=FirebaseAuth.getInstance();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("delivery").child("profile");
       u=getIntent().getStringExtra("PHONENUMBER");

        String PHONENUMBER="+91"+u;
        sendVerificationCode(PHONENUMBER);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String s = snapshot.child("mobile").getValue().toString().trim();
                    if(s.equals(u)){
                        flag = 1;
                        System.out.println("what ever happens");

                    }

                    else {
                        System.out.println("hii");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=(EditText)findViewById(R.id.otp);
                String code=otp.getText().toString().trim();
                if(code==null||code.length()!=6){
                    otp.setError("enter valid code");
                }
                else
                verifyCode(code);

            }
        });
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VERIFICATIONID,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               Intent p = new Intent(verify_phone_number.this, home.class);
               p.putExtra("number",u);
               Intent i=new Intent(verify_phone_number.this,newUser.class);
               i.putExtra("number",u);
               if(flag==1){


                   p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(p);

               }
               else {
                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(i);
               }



           }else{
               Toast.makeText(verify_phone_number.this,task.getException().getMessage(),Toast.LENGTH_LONG);
           }
            }
        });
    }

    private  void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCALLBACK);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCALLBACK = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VERIFICATIONID=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            verifyCode(code);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(verify_phone_number.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };
}
