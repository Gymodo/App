package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.sharedPreferences.MySharedPreferences;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth registerFirebaseAuth;
    private TextInputEditText registerUserName;
    private TextInputEditText registerUserPassword;
    private TextInputEditText registerUserEmail;
    private Button registerSignupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hooks
        registerFirebaseAuth = FirebaseAuth.getInstance();

        registerUserName = findViewById(R.id.registerInputTextName);
        registerUserPassword = findViewById(R.id.registerInputTextPassword);
        registerUserEmail = findViewById(R.id.registerInputTextEmail);
        registerSignupBtn = findViewById(R.id.btnRegister);

        registerSignupBtn.setOnClickListener(v -> {
            if ((!registerUserEmail.getText().toString().isEmpty()) && (!registerUserPassword.getText().toString().isEmpty())) {
                createUser();
            }
        });

    }


    //Create a user
    public void createUser() {
        registerFirebaseAuth.createUserWithEmailAndPassword(
                registerUserEmail.getText().toString(),
                registerUserPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        MySharedPreferences.saveSharePref(registerUserEmail.getText().toString(), registerUserPassword.getText().toString(), this);
                        data();
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Intent to AfterSignUpActivity
    public void data() {
        Intent intent = new Intent(this, AfterSignUp.class);
        intent.putExtra("registerUserName", registerUserName.toString());
        startActivity(intent);
        setResult(12345, null);
        finish();
    }


    public void intentToLoginActivity(View view) {


        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}