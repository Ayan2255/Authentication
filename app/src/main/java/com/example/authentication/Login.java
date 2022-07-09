package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authentication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
     ActivityLoginBinding binding;
     ProgressDialog progressDialog;
     FirebaseFirestore firestore;
     FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.resisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Resister.class);
                startActivity(intent);
            }
        });
        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Forget_password.class);
                startActivity(intent);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(Login.this);
                progressDialog.setTitle("Place Wail...");
                progressDialog.show();
                String email=binding.email.getText().toString();
                String password=binding.password.getText().toString();

                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Fill up all requirement...", Toast.LENGTH_SHORT).show();
                }
                else {

                    firestore.collection("Users").document(email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        String database_password=task.getResult().getString("password");
                                        if(password.equals(database_password)){
                                            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    progressDialog.dismiss();
                                                    Intent intent=new Intent(Login.this,Home.class);
                                                    startActivity(intent);
                                                    Toast.makeText(Login.this, "Successful", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Wrong password...", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Login.this, "No account found...", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });





    }
}