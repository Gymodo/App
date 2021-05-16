package com.github.gymodo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextInputEditText loginUseremail;
    private TextInputEditText loginPassword;
    private String email;
    private String password;
    private ProgressBar loadingIcon;
    private LinearLayout loginMainContainer;
    private TextView loginLogoSplashScreen;
    private ImageView imgLoginLogoSplashScreen;

    //public static final String PREF_FILE_NAME = "Authentication";
    public static final int REQUEST_EXIT = 12345;

    @Override
    protected void onStart() {
        super.onStart();

        loadingIcon.setVisibility(View.VISIBLE);
        loginLogoSplashScreen.setVisibility(View.VISIBLE);
        imgLoginLogoSplashScreen.setVisibility(View.VISIBLE);
        loginMainContainer.setVisibility(View.GONE);

        String[] credential = MySharedPreferences.readSharedPref(this);
        if (credential != null) {

            email = credential[0];
            password = credential[1];

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    data();
                }
            });
        } else if(firebaseAuth.getCurrentUser() != null){

            data();

        } else {
            loadingIcon.setVisibility(View.GONE);
            loginLogoSplashScreen.setVisibility(View.GONE);
            imgLoginLogoSplashScreen.setVisibility(View.GONE);
            loginMainContainer.setVisibility(View.VISIBLE);
        }
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
                Log.d("googl", "AuthCred: " + authCredential.toString());
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                    Toast.makeText(getApplicationContext(), "Your google account is connected to our aplication", Toast.LENGTH_SHORT).show();

                    data();

                }).addOnFailureListener(e -> showAlert(e));
            } catch (ApiException e) {
                e.printStackTrace();
                showAlert(e);
            }
        }

        if (requestCode == REQUEST_EXIT) {
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
        Button loginLoginBtn = findViewById(R.id.loginBtn);
        Button googleLoginBtn = findViewById(R.id.googleLoginBtn);
        loadingIcon = findViewById(R.id.loadingIcon);
        loginMainContainer = findViewById(R.id.loginMainContainer);
        imgLoginLogoSplashScreen = findViewById(R.id.imgLoginLogoSplashScreen);
        loginLogoSplashScreen = findViewById(R.id.loginLogoSplashScreen);


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
                showAlert(task.getException());
            }
        });
    }

    /**
     * Intent to MainActivity
     */

    public void data() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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

    public void intentToRegisterActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_EXIT);
    }
}