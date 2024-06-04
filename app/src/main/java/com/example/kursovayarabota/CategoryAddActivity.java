package com.example.kursovayarabota;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CategoryAddActivity extends AppCompatActivity  {
    Button btn;
    EditText edName,edAddress,edComment;
    private FirebaseAuth firebaseAuth;
    private String name ="";
    private String address ="";
    private String comment ="";
    FloatingActionButton ibtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_add);
        firebaseAuth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.submitBtn);
        edName = findViewById(R.id.categoryEt);
        edAddress = findViewById(R.id.categoryEd);
        edComment = findViewById(R.id.categoryEf);
        ibtn = findViewById(R.id.homehome1);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateData();
            }
        });


    }
    private void invalidateData() {
        name = edName.getText().toString().trim();
        address = edAddress.getText().toString().trim();
        comment = edComment.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter Park Tittle....!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter Park Address....!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(comment)) {
            Toast.makeText(this, "Please enter Park Comment....!", Toast.LENGTH_SHORT).show();
        }
        else{
            addCategoryFirebase();
        }
    }

    private void addCategoryFirebase() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Park...");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("name",""+name);
        hashMap.put("address",""+address);
        hashMap.put("comment",""+comment);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Parks");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(CategoryAddActivity.this, "Category added successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(CategoryAddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}