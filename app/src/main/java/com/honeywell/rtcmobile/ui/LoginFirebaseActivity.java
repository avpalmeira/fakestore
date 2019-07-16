package com.honeywell.rtcmobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.honeywell.rtcmobile.R;

public class LoginFirebaseActivity extends AppCompatActivity {

    private EditText username, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        // Initialize Firebase instance
        auth = FirebaseAuth.getInstance();
    }

    public void handleLogin(View view) {

        auth.signInWithEmailAndPassword(
                username.getText().toString(),
                password.getText().toString())
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    Toast.makeText(
                                            LoginFirebaseActivity.this,
                                            "You got a response:"+user.getUid(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                } else {
                                    Toast.makeText(
                                            LoginFirebaseActivity.this,
                                            "You got an error",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        }
                );
    }
}
