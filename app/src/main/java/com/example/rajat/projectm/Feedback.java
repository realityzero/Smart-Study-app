package com.example.rajat.projectm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    EditText et1,et2;
     FeedbackDb myDb=new FeedbackDb(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#3B494C"));
        window.setNavigationBarColor(Color.parseColor("#4e5b5d"));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        et1=(EditText) findViewById(R.id.editText);
        et2=(EditText) findViewById(R.id.editText2);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(getApplicationContext(), "Enter some data in both Fields", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    String name=et1.getText().toString();
                    String feed=et2.getText().toString();
                    boolean isInserted = myDb.insert2(name, feed);
                    if (isInserted == true)
                    {
                        //Toast.makeText(getApplicationContext(), "Feedback is successfully sent", Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "Feedback is successfully sent.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    else{
                        Snackbar.make(view, "There is some error.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }


            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String name=et1.getText().toString();
        String feed=et2.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            et1.setError("at least 3 characters");
            valid = false;
        } else {
            et1.setError(null);
        }
        if (feed.isEmpty() || feed.length() < 3) {
            et2.setError("at least 3 characters");
            valid = false;
        } else {
            et2.setError(null);
        }

        return valid;
    }

}
