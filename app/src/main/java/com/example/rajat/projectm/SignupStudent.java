package com.example.rajat.projectm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import butterknife.ButterKnife;
//import butterknife.InjectView;

public class SignupStudent extends AppCompatActivity {
  //  private static final String TAG = "SignupActivity";
    DataBaseHelper myDB;

    EditText _nameText,_emailText,_passwordText,_cpasswordText;
    Button _signupButton;
    TextView _loginLink;
    RadioButton _rt,_rs;
    // @InjectView(R.id.input_name)
//    EditText _nameText= (EditText) findViewById(R.id.input_name);
//    // @InjectView(R.id.input_email)
//    EditText _emailText= (EditText) findViewById(R.id.input_email);
//    //@InjectView(R.id.input_password)
//    EditText _passwordText= (EditText) findViewById(R.id.input_password);
//    // @InjectView(R.id.btn_signup)
//    EditText _cpasswordText= (EditText) findViewById(R.id.input_cpassword);
//    Button _signupButton= (Button) findViewById(R.id.btn_signup);
//    //  @InjectView(R.id.link_login)
//    TextView _loginLink= (TextView) findViewById(R.id.link_login);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
       // ButterKnife.inject(this);
        _nameText= (EditText) findViewById(R.id.input_name);
        _emailText= (EditText) findViewById(R.id.input_email);
        _passwordText= (EditText) findViewById(R.id.input_password);
        _cpasswordText= (EditText) findViewById(R.id.input_cpassword);
        _signupButton= (Button) findViewById(R.id.btn_signup);
        _loginLink= (TextView) findViewById(R.id.link_login);
        _rt= (RadioButton) findViewById(R.id.input_type1);
        _rs= (RadioButton) findViewById(R.id.input_type2);



        myDB=new DataBaseHelper(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    signup();

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
     //   Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

      //  _signupButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(SignupStudent.this,
//                R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String cpassword = _cpasswordText.getText().toString();
        RadioGroup grp=(RadioGroup)findViewById(R.id.RadioGroup1);
        RadioButton _type=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
        String x= _type.getText().toString();

        if (!password.equals(cpassword)) {
           //Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
            _cpasswordText.setError("Password doesn't match");
            _cpasswordText.requestFocus();
        } else {
            boolean isInserted = myDB.insertData(name, email, password,x);
            if (isInserted == true)
            {
              //  new android.os.Handler().postDelayed(
                    //    new Runnable() {
                       //     public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                               onSignupSuccess();
                                // onSignupFailed();
                                //progressDialog.dismiss();
                        //    }
                     //   }, 3000);
        }
            else{
                onSignupFailed();
            }
        }
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

       if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}