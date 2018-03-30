package com.example.rajat.projectm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();
    private EditText inputEmail, inputPassword, inputUserName;
    private TextView txtDetails,txtDetails1;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userTypeConfirmed,userTypeConfirmed1;
    RadioButton _rt,_rs;

    private String userId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
//
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, Main2Activity.class));
//            finish();
//        }

        // set the view now
        setContentView(R.layout.activity_login);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("ProjectM");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#00bcd4"));
        window.setNavigationBarColor(Color.parseColor("#0097a7"));

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        inputUserName = (EditText) findViewById(R.id.userName);
        txtDetails = (TextView) findViewById(R.id.txt_user);
        txtDetails1 = (TextView) findViewById(R.id.radioTextView);
        _rt= (RadioButton) findViewById(R.id.input_type1);
        _rs= (RadioButton) findViewById(R.id.input_type2);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                final String userName = inputUserName.getText().toString().trim();
                RadioGroup grp=(RadioGroup)findViewById(R.id.RadioGroup1);
                RadioButton _type=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
                final String type = _type.getText().toString().trim();
                txtDetails1.setText(type);

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Enter Email!");
                    inputEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Enter Password!");
                    inputPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(userName)) {
                    inputUserName.setError("Enter Username!");
                    inputUserName.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                    addUserChangeListener(userName);

                                }
                            }
                        });
            }
        });
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener(String userName) {
        // User data change listener
        mFirebaseDatabase.child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.type);

                // Display newly updated name and email
                txtDetails.setText(user.type);

                // clear edit text
                inputEmail.setText("");
                inputPassword.setText("");
                inputUserName.setText("");

                userTypeConfirmed = txtDetails.getText().toString();
                userTypeConfirmed1 = txtDetails1.getText().toString();
                Log.e(TAG, userTypeConfirmed);
                Log.e(TAG, userTypeConfirmed1);
                if(!(userTypeConfirmed1.equalsIgnoreCase(user.type))){
                    Toast.makeText(LoginActivity.this, "Psst! Check your user type.", Toast.LENGTH_LONG).show();
                    auth.signOut();
                }
                else {
                    if(userTypeConfirmed.equals("Teacher")){
                        Intent intent = new Intent(LoginActivity.this, FourButtonsActivity.class);
                        startActivityForResult(intent,0);
                        finish();
                    }
                    if(userTypeConfirmed.equals("Student")){
                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                        startActivityForResult(intent,0);
                        finish();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
       // moveTaskToBack(true);
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Exit").setIcon(R.drawable.ic_logout_grey600_24dp).setMessage("Are you sure?").setCancelable(false);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(i);
               // Toast.makeText(LoginActivity.this,"Logged Out Successfully",Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(Main2Activity.this,"Logout Failed",Toast.LENGTH_SHORT).show();
                moveTaskToBack(true);
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}