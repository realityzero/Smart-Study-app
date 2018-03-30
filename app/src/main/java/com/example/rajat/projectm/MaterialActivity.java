package com.example.rajat.projectm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MaterialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
   // TextView fill;
    ListView listView;
    String title;
   // DBMaterial db=new DBMaterial(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        final Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#FF5252"));
        window.setNavigationBarColor(Color.parseColor("#ff6363"));

       spinner= (Spinner) findViewById(R.id.spinner);
      //  fill= (TextView) findViewById(R.id.fill_content);
        listView= (ListView) findViewById(R.id.titleList);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

    }

    private void loadSpinnerData() {
        DBMaterial db=new DBMaterial(MaterialActivity.this);
        List<String> lables=db.getAll();
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        DBMaterial db=new DBMaterial(getApplicationContext());
        String category=adapterView.getItemAtPosition(i).toString();
        List<String> items = new ArrayList<String>();
         Cursor titles=db.getAllData(category);
        titles.moveToFirst();
        if (titles.moveToFirst()) {
            do {

                items.add(titles.getString(0));

            } while (titles.moveToNext());
        }
        titles.close();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,items);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String item = ((TextView)view).getText().toString();
               //Toast.makeText(MaterialActivity.this, item, Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MaterialActivity.this, MaterialContent.class);
               intent.putExtra("EXTRA_MESSAGE", item);
               startActivityForResult(intent,0);
               //title=item;

           }
       });

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
