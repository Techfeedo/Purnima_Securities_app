package com.example.purnimasecurities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText Info_2;
    EditText Info_4;
    Button login;
    TextView regis;
    FirebaseAuth fAuth;
    ProgressBar progressBar_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Info_2 = (EditText) findViewById(R.id.etInfo_2);
        Info_4 = (EditText) findViewById(R.id.etInfo_4);
        login = (Button) findViewById(R.id.btlogin);
        regis = (TextView) findViewById(R.id.tvregister);

        fAuth = FirebaseAuth.getInstance();
        progressBar_1 = findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Info_2.getText().toString().trim();
                String password = Info_4.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Info_2.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Info_4.setError("Password is required!");
                    return;
                }
                if (password.length() < 6) {
                    Info_4.setError("Password must be 6 characters or more!");
                    return;
                }

                progressBar_1.setVisibility(View.VISIBLE);

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Unwanted Error" + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                            progressBar_1.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));;
            }
        });
    }
}
