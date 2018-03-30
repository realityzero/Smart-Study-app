package com.example.rajat.projectm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SetStudyMaterial extends Fragment {
    EditText _title,_content,_category;
    Button _submit;
    DBMaterial db;
    //DBMaterial db=new DBMaterial(this.getActivity());
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };
        View rootView = inflater.inflate(R.layout.activity_set_study_material, container, false);
        _title= (EditText) rootView.findViewById(R.id.input_title);
        _content= (EditText) rootView.findViewById(R.id.input_content);
        _submit= (Button) rootView.findViewById(R.id.input_material);
        _category= (EditText) rootView.findViewById(R.id.input_category);
        insertdata();
      // db=new DBMaterial(this.getActivity(),"database",null,4);
    db=new DBMaterial(getActivity());

        return rootView;

    }

    private void insertdata() {
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_category.getText().toString().equalsIgnoreCase("")){
                    _category.requestFocus();
                }
                else {
                    boolean x = db.insert1(_category.getText().toString(), _title.getText().toString(), _content.getText().toString());
                    if (x == true) {
                        Toast.makeText(getActivity(), "Material Entered!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Material Not Entered!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static SetStudyMaterial newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        SetStudyMaterial sampleFragment = new SetStudyMaterial();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }
}
