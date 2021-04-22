package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextInputEditText loginUsername;
    private TextInputEditText loginPassword;
    private Button loginLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        firebaseAuth = FirebaseAuth.getInstance();
        loginUsername = findViewById(R.id.inputTextLogin);
        loginPassword = findViewById(R.id.registerInputTextPassword);
        loginLoginBtn = findViewById(R.id.loginBtn);


        loginLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!loginUsername.getText().toString().isEmpty()) && (!loginPassword.getText().toString().isEmpty())) {
                    loginUser();
                }
            }
        });

    }

    //Login user
    public void loginUser() {
        firebaseAuth.signInWithEmailAndPassword(loginUsername.getText().toString(), loginPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    data();
                } else {
                    showAlert();
                }
            }
        });
    }

    //Intent to MainActivity
    public void data() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Show alert
    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("User or password does not exist.");
        builder.setTitle("Error");
        builder.setPositiveButton("Accept", null);
        builder.create();
        builder.show();
    }

    public void intentToRegisterActivity(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}