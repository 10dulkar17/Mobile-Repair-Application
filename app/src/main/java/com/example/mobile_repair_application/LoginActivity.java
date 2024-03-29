package com.example.mobile_repair_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobile_repair_application.ui.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    Button btn_login;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        btn_login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.SignInGoogle();
            }
        });

        if (mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
        }

    }

    void SignInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);


    }

    private void openMainActivity() {
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);


            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d("TAG" , "sighin success" );

                            FirebaseUser user = mAuth.getCurrentUser();
                            openMainActivity();

                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d("TAG", "Sign in not Successful",task.getException());
                            Toast.makeText(LoginActivity.this, "Sign in Failed ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}
