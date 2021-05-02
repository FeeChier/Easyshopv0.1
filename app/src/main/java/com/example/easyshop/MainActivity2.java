package com.example.easyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private TextView mFormFeedback;
    private ProgressBar mFormProgressBar;

    private EditText mEmailText;
    private EditText mPasswordText;
    private ImageButton btt;
    private Button mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnMenuItemClickListener(navListener);

        btt = findViewById(R.id.connexionButton);
        btt.setVisibility(View.GONE);



        ImageButton logoButton = findViewById(R.id.logoButton);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mFormFeedback = findViewById(R.id.feedback);
        mFormProgressBar = findViewById(R.id.connexion_progressbar);

        mEmailText = findViewById(R.id.email_text);
        mPasswordText = findViewById(R.id.password_text);

        mConfirmBtn = findViewById(R.id.confirmButton);

        if (mCurrentUser == null) {

            mFormProgressBar.setVisibility(View.VISIBLE);

            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mFormFeedback.setVisibility(View.VISIBLE);
                    if (task.isSuccessful()) {
                        mFormFeedback.setVisibility(View.VISIBLE);
                        mFormFeedback.setText("Signed in Anymously");
                    } else {
                        mFormFeedback.setText("There was an error signing in");
                    }
                    mFormProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();
                if (mCurrentUser != null) {
                    if (!email.isEmpty() || !password.isEmpty()) {
                        mFormProgressBar.setVisibility(View.VISIBLE);
                        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                        mCurrentUser.linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mFormFeedback.setVisibility(View.VISIBLE);

                                    if (task.isSuccessful()) {
                                        mFormFeedback.setVisibility(View.VISIBLE);
                                        mFormFeedback.setText("Votre compte à été crée !");
                                    } else {
                                        mFormFeedback.setText("Veuillez entrer un email valable...");
                                    }
                                    mFormProgressBar.setVisibility(View.INVISIBLE);
                                }

                        });
                    }

                }
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.logoButton){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                }

            }
        });
    }

    private Toolbar.OnMenuItemClickListener navListener =
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_premium:
                            selectedFragment = new PremiumFragment();
                            break;
                        case R.id.nav_options:
                            selectedFragment = new OptionFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
    }
}