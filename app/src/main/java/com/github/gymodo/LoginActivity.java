package com.github.gymodo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.gymodo.sharedPreferences.MySharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextInputEditText loginUseremail;
    private TextInputEditText loginPassword;
    private Button loginLoginBtn;
    private Button googleLoginBtn;
    private String email;
    private String password;

    //public static final String PREF_FILE_NAME = "Authentication";
    public static final int REQUEST_EXIT = 12345;


    @Override
    protected void onStart() {
        super.onStart();

        String[] credential = MySharedPreferences.readSharedPref(this);
        if (credential != null) {
            email = credential[0];
            password = credential[1];

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    data();
                }
            });
        }
        //readSharedPref();
    }

    //Login google
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
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                    Toast.makeText(getApplicationContext(), "Your google account is connected to our aplication", Toast.LENGTH_SHORT).show();
                    data();
                }).addOnFailureListener(e -> showAlert());
            } catch (ApiException e) {
                e.printStackTrace();
                showAlert();
            }
        }

        if (requestCode == REQUEST_EXIT){
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        firebaseAuth = FirebaseAuth.getInstance();
        loginUseremail = findViewById(R.id.inputTextLogin);
        loginPassword = findViewById(R.id.registerInputTextPassword);
        loginLoginBtn = findViewById(R.id.loginBtn);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);

        loginLoginBtn.setOnClickListener(v -> {
            if ((!loginUseremail.getText().toString().isEmpty()) && (!loginPassword.getText().toString().isEmpty())) {
                email = loginUseremail.getText().toString();
                password = loginPassword.getText().toString();
                loginUser();
            }
        });


        //SignIn with google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) //Firebase id
                .requestEmail() //request user mail
                .build();

        signInClient = GoogleSignIn.getClient(this, gso);

        googleLoginBtn.setOnClickListener(v -> {
            //Configure Google Sign In
            signInClient.signOut();
            Intent sign = signInClient.getSignInIntent();
            startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
        });
    }

    //Login user
    public void loginUser() {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //saveSharePref();
                MySharedPreferences.saveSharePref(email, password, this);
                data();
            } else {
                showAlert();
            }
        });
    }

    //Intent to MainActivity
    public void data() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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

    public void intentToRegisterActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_EXIT);
    }

    /*
    private void saveSharePref() {
        SharedPreferences sharedPrefs = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Field saved", Toast.LENGTH_SHORT).show();
    }*/

    /*
    private void readSharedPref() {//Si el fitxer de pref tenim dades vol dir que ja ens hem autenticat
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", null);
        password = sharedPreferences.getString("password", null);
        if (email != null & password != null) {
            Toast.makeText(LoginActivity.this, "Already signing in", Toast.LENGTH_SHORT).show();
            //loginUser();
            //+data();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    data();
                }
            });
        }
    }*/



}