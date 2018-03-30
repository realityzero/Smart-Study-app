package com.example.rajat.projectm;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MaterialContent extends AppCompatActivity {
    TextView title1,content1;

    Button next;
    DBMaterial db=new DBMaterial(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_content);

        title1= (TextView) findViewById(R.id.show_title);
        content1= (TextView) findViewById(R.id.show_content);
        next= (Button) findViewById(R.id.show_next);

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            String title = extras.getString("EXTRA_MESSAGE");
            title1.setText(title);
            Cursor get = db.getContent(title);
            get.moveToFirst();
            String content = get.getString(0);
            content1.setText(content);
            Cursor get2=db.getCategory(content);
            get2.moveToFirst();
            String category=get2.getString(0);

           clicknext(title,category);

        }

    }

    private void clicknext(final String title11, final String category) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor getID=db.getID(title11);
                                getID.moveToFirst();
                                 int id=getID.getInt(0);
                                    int x=id+1;
                                     getID.close();
                if(db.checkID(x)==true){

                          Cursor show=db.getContentId(x,category);
                          if(show.moveToFirst()){
                              do {
                                  title1.setText(show.getString(0));
                                  content1.setText(show.getString(1));
                              }while (show.moveToNext());
                          }
                }
                else{
                    Toast.makeText(MaterialContent.this, "No more records", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
