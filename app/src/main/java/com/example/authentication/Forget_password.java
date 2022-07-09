package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.authentication.databinding.ActivityForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Forget_password extends AppCompatActivity {
ActivityForgetPasswordBinding binding;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
      firebaseAuth=FirebaseAuth.getInstance();


      binding.loginBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(getApplicationContext(),Login.class);
              startActivity(intent);
          }
      });








        binding.sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String email=binding.email.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(Forget_password.this, "Fill up Email address", Toast.LENGTH_SHORT).show();
                binding.email.setError("Please provide valid Email");
                binding.email.requestFocus();
                return;
            }
//            else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//
//              //  Toast.makeText(Forget_password.this, "Fill up Email address", Toast.LENGTH_SHORT).show();
//                binding.email.setError("Please provide valid Email");
//                binding.email.requestFocus();
//                return;
//            }
            else {
//                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){  Toast.makeText(Forget_password.this, "Check your email...", Toast.LENGTH_SHORT).show();}
//                        else {  Toast.makeText(Forget_password.this, "Please wait...", Toast.LENGTH_SHORT).show();}
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Forget_password.this, "Error...", Toast.LENGTH_SHORT).show();
//                    }
//                });

                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Forget_password.this, "Check your email...", Toast.LENGTH_SHORT).show();
                        binding.email.requestFocus();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Forget_password.this, "Try again...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            }
        });
    }
}