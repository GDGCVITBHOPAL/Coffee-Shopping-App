package com.example.caffycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Objects;
import soup.neumorphism.NeumorphButton;

public class SignUpActivity extends AppCompatActivity {

    EditText sgnFullName, sgnAge, sgnDOB, sgnEmail, sgnPassword, sgnPhoneNumber;
    NeumorphButton sgnSignUp;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

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
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering, Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        // after clicking the signup button
        sgnSignUp.setOnClickListener(view -> {

            String strSgnFullName = sgnFullName.getText().toString();
            String strSgnAge = sgnAge.getText().toString();
            String strSgnDOB = sgnDOB.getText().toString();
            String strSgnEmail = sgnEmail.getText().toString().trim();
            String strSgnPassword = sgnPassword.getText().toString().trim();
            String strSgnPhoneNumber = sgnPhoneNumber.getText().toString();

            //checking the Date of birth
            // To-do

            //checking the name
            if(TextUtils.isEmpty(strSgnFullName)) {
                sgnFullName.setError("Invalid Name");
                sgnFullName.requestFocus();
                return;
            }

            //checking the age
            if(TextUtils.isEmpty(strSgnAge)) {
                sgnAge.setError("Invalid Age");
                sgnAge.requestFocus();
                return;
            }
            else {
                int ageValue = Integer.parseInt(strSgnAge);
                if(ageValue<5 || ageValue>150) {
                    sgnAge.setError("Invalid Age");
                    sgnAge.requestFocus();
                    return;
                }
            }

            //checking the Date Of Birth
            if(TextUtils.isEmpty(strSgnDOB)) {
                sgnDOB.setError("Invalid Date Of Birth");
                sgnDOB.requestFocus();
                return;
            }

            // checking the email
            if(TextUtils.isEmpty(strSgnEmail)) {
                sgnEmail.setError("Invalid Email!");
                sgnEmail.requestFocus();
                return;
            }
            if(!strSgnEmail.contains("@")) {
                sgnEmail.setError("Invalid Email!");
                sgnEmail.requestFocus();
                return;
            }

            //checking the password
            if(TextUtils.isEmpty(strSgnPassword)) {
                sgnPassword.setError("Invalid Password!");
                sgnPassword.requestFocus();
                return;
            }
            if(strSgnPassword.length()<5) {
                sgnPassword.setError("Minimum 6 characters required");
                sgnPassword.requestFocus();
                return;
            }

            //checking the phone number
            if(TextUtils.isEmpty(strSgnPhoneNumber) || strSgnPhoneNumber.length() != 10 || strSgnPhoneNumber.charAt(0)=='0') {
                sgnPhoneNumber.setError("Invalid Phone Number");
                sgnPhoneNumber.requestFocus();
                return;
            }

            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            // Registering the user in the firebase
            firebaseAuth.createUserWithEmailAndPassword(strSgnEmail, strSgnPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.dismiss();

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("Name", strSgnFullName);
                    data.put("Age", strSgnAge);
                    data.put("Date Of Birth", strSgnDOB);
                    data.put("Email", strSgnEmail);
                    data.put("Password", strSgnPassword);
                    data.put("Phone Number", strSgnPhoneNumber);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(strSgnFullName).setValue(data);

                    // going to login page
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    Toast.makeText(SignUpActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration Failed! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}