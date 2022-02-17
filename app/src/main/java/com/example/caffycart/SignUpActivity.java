package com.example.caffycart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import java.util.Objects;
import soup.neumorphism.NeumorphButton;

public class SignUpActivity extends AppCompatActivity {

    EditText sgnFullName, sgnAge, sgnDOB, sgnEmail, sgnPassword, sgnPhoneNumber;
    NeumorphButton sgnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_up_activity);

        // This will remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        sgnFullName = findViewById(R.id.enterSignUpFullName);
        sgnAge = findViewById(R.id.enterSignUpAge);
        sgnDOB = findViewById(R.id.enterSignUpDateOfBirth);
        sgnEmail = findViewById(R.id.enterSignUpEmail);
        sgnPassword = findViewById(R.id.enterSignUpPassword);
        sgnPhoneNumber = findViewById(R.id.enterSignUpPhoneNumber);
        sgnSignUp = findViewById(R.id.signUpBtn);

        // after clicking the signup button
        sgnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}