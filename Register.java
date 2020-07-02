package com.example.purnimasecurities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    public static final String TAG2 = "TAG";
    public static final String TAG3 = "TAG";
    EditText Info_1;
    EditText Info_2;
    EditText Info_3;
    EditText Info_4;
    Button regis;
    TextView login;
    FirebaseAuth fAuth;
    ProgressBar progressBar_1;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Info_1 = (EditText) findViewById(R.id.etInfo_1);
        Info_2 = (EditText) findViewById(R.id.etInfo_2);
        Info_3 = (EditText) findViewById(R.id.etInfo_3);
        Info_4 = (EditText) findViewById(R.id.etInfo_4);
        regis = (Button) findViewById(R.id.btregis);
        login = (TextView) findViewById(R.id.tvlogin);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        progressBar_1 = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Register.class));
            finish();
        }
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Info_2.getText().toString().trim();
                String password = Info_4.getText().toString().trim();
                String full_name = Info_1.getText().toString();
                String phone_number = Info_3.getText().toString();

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

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "You have successfully registered", Toast.LENGTH_SHORT).show();
                            userId=fAuth.getCurrentUser().getUid();
                            Map<String,Object> userId=new HashMap<>();
                            userId.put("Name:",Info_1);
                            userId.put("Contact No.",Info_3);
                            userId.put("Email-ID:",Info_2);
                            fstore.collection("Users").document("_ONE");
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                    } else {
                            Toast.makeText(Register.this, "Unwanted Error" + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                            progressBar_1.setVisibility(View.GONE);
                        }
                    }
                });
            }


        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));;
            }
        });
    }
}

