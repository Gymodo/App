package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.gymodo.sharedPreferences.MySharedPreferences;
import com.github.gymodo.user.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth registerFirebaseAuth;
    private TextInputEditText registerUserName;
    private TextInputEditText registerUserPassword;
    private TextInputEditText registerUserEmail;
    private Button registerSignupBtn;
    private Button btnRegisterGoogle;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;


    //Register google
    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private static final int GOOGLE_SIGN_IN_CODE = 10005;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount signInAccount = signInTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                progressBar.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);

                registerFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        //Check if user already exist
                        if (task.getResult().getAdditionalUserInfo().isNewUser()){ //Not exists, is new user
                            data(registerFirebaseAuth.getCurrentUser().getDisplayName(), task.getResult().getUser().getEmail());

                        } else {
                            Toast.makeText(getApplicationContext(), "Already have an account", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }).addOnFailureListener(e -> {

                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    showAlert(e);

                });
            } catch (ApiException e) {
                e.printStackTrace();
                showAlert(e);
            }
        }
    }


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
        btnRegisterGoogle = findViewById(R.id.btnRegisterGoogle);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linearLayout);

        registerSignupBtn.setOnClickListener(v -> {
            if ((!registerUserEmail.getText().toString().isEmpty()) && (!registerUserPassword.getText().toString().isEmpty())) {
                createUser();
            }
        });

        //SignIn with google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) //Firebase id
                .requestEmail() //request user mail
                .build();

        signInClient = GoogleSignIn.getClient(this, gso);

        btnRegisterGoogle.setOnClickListener(v -> {
            //Configure Google Sign In
            signInClient.signOut();
            Intent sign = signInClient.getSignInIntent();
            startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
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
                        data(registerUserName.getText().toString(), registerUserEmail.getText().toString());
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Intent to AfterSignUpActivity
    public void data(String userName, String email) {
        Intent intent = new Intent(this, AfterSignUp.class);
        intent.putExtra("registerUserName", userName);
        intent.putExtra("registerEmail", email);
        startActivity(intent);
        setResult(12345, null);
        finish();
    }


    public void intentToLoginActivity(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //Show alert
    public void showAlert(Exception exception) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(exception.getLocalizedMessage());
        builder.setTitle("Error");
        builder.setPositiveButton("Accept", null);
        builder.create();
        builder.show();
    }
}