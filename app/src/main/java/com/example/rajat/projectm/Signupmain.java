package com.example.rajat.projectm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Signupmain extends AppCompatActivity {

    Button bStd,bTea,bSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupmain);

        bStd= (Button) findViewById(R.id.StdButton);
        bTea= (Button) findViewById(R.id.TeaButton);
        bSkip= (Button) findViewById(R.id.SkipButton);

        bStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SignupStudent.class);
                startActivityForResult(i,0);
            }
        });

        bTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SignupStudent.class);
                startActivity(i);
            }
        });

        bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivityForResult(i,1);
            }
        });
    }
}
