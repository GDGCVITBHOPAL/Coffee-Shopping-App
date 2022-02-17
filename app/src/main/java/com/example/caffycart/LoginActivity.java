package com.example.caffycart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Objects;
import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphFloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    EditText lgnEmail, lgnPassword;
    NeumorphFloatingActionButton lgnGoogle, lgnFacebook, lgnGithub;
    NeumorphButton lgnLogin, lgnMoveToSignUpPage;
    TextView lgnForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);

        // This will remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        lgnEmail = findViewById(R.id.enterLoginEmail);
        lgnPassword = findViewById(R.id.enterLoginPassword);
        lgnGoogle = findViewById(R.id.loginWithGoogleBtn);
        lgnFacebook = findViewById(R.id.loginWithFacebookBtn);
        lgnGithub = findViewById(R.id.loginWithGithubBtn);
        lgnLogin = findViewById(R.id.loginBtn);
        lgnMoveToSignUpPage = findViewById(R.id.redirectToSignUpBtn);
        lgnForgetPassword = findViewById(R.id.forgotPasswordButton);

        // Moving to SignUp Page after clicking "Does not have an Account? SignUp"
        lgnMoveToSignUpPage.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Moving to the order creating activity
        lgnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, OrderCreatingActivity.class);
            startActivity(intent);
            finish();
        });

    }
}