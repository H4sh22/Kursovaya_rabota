package com.example.kursovayarabota;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    public static boolean isValid(String passwordthere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordthere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordthere.length(); p++) {
                if (Character.isLetter(passwordthere.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int p = 0; p < passwordthere.length(); p++) {
                if (Character.isDigit(passwordthere.charAt(p))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordthere.length(); s++) {
                char c = passwordthere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1 == 1 && f2 == 1 && f3 == 1)
                return true;
            return false;
        }
    }

    EditText edUsername,edEmail,edPassword,edConfirmPassword;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        edUsername = findViewById(R.id.editTextRegUsername);
        edEmail = findViewById(R.id.editTextRegEmail);
        edConfirmPassword = findViewById(R.id.editTextRegConfirmPassword);
        edPassword = findViewById(R.id.editTextRegPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String confirmPassword = edConfirmPassword.getText().toString();
                if(username.length()==0 || password.length()==0 || email.length()==0 || confirmPassword.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.compareTo(confirmPassword)==0){
                        if(isValid(password)){

                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(edEmail.getText().toString(),edPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Account successfully created", Toast.LENGTH_SHORT).show();
                                        HashMap<String, String> userInfo = new HashMap<>();
                                        userInfo.put("email", edEmail.getText().toString());
                                        userInfo.put("password", edPassword.getText().toString());
                                        userInfo.put("username", edUsername.getText().toString());
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userInfo);
                                    }
                                }
                            });
                            Toast.makeText(getApplicationContext(),"Register Inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Password must contain at least 8 characters, having leter,digit and special symbol", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Password and Confirm Password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
