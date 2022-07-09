package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScrreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(SplashScrreen.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        setContentView(R.layout.activity_splash_scrreen);

        firebaseAuth=FirebaseAuth.getInstance();
        Thread thread=new Thread(){
            public void  run(){
                try {

                    sleep(3000);
                }catch (Exception e){
                   e.printStackTrace();
                }
                finally {
                    progressDialog.dismiss();
                    if(firebaseAuth.getCurrentUser()!=null) {

                        Intent intent = new Intent(SplashScrreen.this, Home.class);
                        startActivity(intent);
                    }
                    else
                    {

                      //  Toast.makeText(SplashScrreen.this, "NO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScrreen.this, Login.class);
                        startActivity(intent);
                    }
                }
            }
        };
        thread.start();    }
}