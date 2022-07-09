package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;


import com.example.authentication.databinding.ActivityResisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;

public class Resister extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
   ActivityResisterBinding binding;
   FirebaseAuth firebaseAuth;
   FirebaseFirestore firestore;
   ProgressDialog progressDialog;
   Uri image_URI;
    String sx="male",bd="o+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityResisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        String [] sex={"Male","Female","Other"};
        binding.sexSpiner.setOnItemSelectedListener(this);
      ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.sex_string, android.R.layout.simple_spinner_item);
      //ArrayAdapter <String>adapter=new ArrayAdapter(Resister.this,R.layout.spiner_1_back,sex);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sexSpiner.setAdapter(adapter);
        binding.sexSpiner.setOnItemSelectedListener(this);


        String [] blood={"O+","O-","A+","A-","AB+","AB-","B+","B-"};
        binding.sexSpiner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.bood, android.R.layout.simple_spinner_item);
      //  ArrayAdapter<CharSequence> adapter5=new ArrayAdapter(Resister.this,R.layout.spiner_1_back,sex);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sexSpiner2.setAdapter(adapter2);
        binding.sexSpiner2.setOnItemSelectedListener(this);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Resister.this,Login.class);
                startActivity(intent);
            }
        });


        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select an image .."),1);
            }
        });



binding.submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        String name=binding.userName.getText().toString();
        String email=binding.userEmail.getText().toString();
        String address=binding.userAddress.getText().toString();
        String phone=binding.userPhoneNumber.getText().toString();
        String password1=binding.password1.getText().toString();
        String password2=binding.password2.getText().toString();

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(email)
                ||TextUtils.isEmpty(address)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(password1)||TextUtils.isEmpty(password2)|| TextUtils.isEmpty(sx)||TextUtils.isEmpty(bd)){
            Toast.makeText(Resister.this, "Fill up all requirement...", Toast.LENGTH_SHORT).show();

       return;
        }

        else if(!password1.equals(password2)){
            Toast.makeText(Resister.this, "Password don't match ...", Toast.LENGTH_SHORT).show();

            return;
    }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(Resister.this);
            builder.setTitle("Confirmation...")
                    .setMessage("Are you want to register in this application...")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            progressDialog=new ProgressDialog(Resister.this);
                            progressDialog.setTitle("Loading");
                            progressDialog.show();

                            firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {

                                        Register_Model model = new Register_Model(name, email, address,phone, password1, sx, bd);
                                        firestore.collection("Users")
                                                .document(email)
                                                .set(model)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Resister.this, "Successful", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(Resister.this,Home.class);
                                                        startActivity(intent);

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Resister.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                    }
                                    else {
                                        progressDialog.dismiss();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Resister.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).create().show();


        }
    }
});




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId()==R.id.sex_spiner){
            sx= adapterView.getItemAtPosition(i).toString();
        }

        if(adapterView.getId()==R.id.sex_spiner){
            bd= adapterView.getItemAtPosition(i).toString();
        }

      }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText( this, "No", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null){
            image_URI=data.getData();
            binding.profileImage.setImageURI(image_URI);
/*
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),image_URI);
                binding.profileImage.setImageBitmap(bitmap);

            }catch (Exception e){

                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }*/
        }
    }
}