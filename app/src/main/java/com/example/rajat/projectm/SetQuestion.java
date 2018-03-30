package com.example.rajat.projectm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class SetQuestion extends Fragment {
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    EditText ques, opa, opb, opc, opcorrect;
    Button store;
    Question q;
    DbHelper db;

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
        View rootView = inflater.inflate(R.layout.activity_set_question, container, false);
        ques = (EditText) rootView.findViewById(R.id.et_question);
        opa = (EditText) rootView.findViewById(R.id.et_opta);
        opb = (EditText) rootView.findViewById(R.id.et_optb);
        opc = (EditText) rootView.findViewById(R.id.et_optc);
        opcorrect = (EditText) rootView.findViewById(R.id.et_optcorrect);
        store = (Button) rootView.findViewById(R.id.bt_store);
        addData();
        //db = new DbHelper(this.getActivity(), "database", null, 4);
        db=new DbHelper(getActivity());
        return rootView;

    }

    private void addData() {
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(opcorrect.getText().toString().equals(opa.getText().toString())||opcorrect.getText().toString().equals(opb.getText().toString())||opcorrect.getText().toString().equals(opc.getText().toString())) {

                    boolean x = db.xx(ques.getText().toString(), opa.getText().toString(), opb.getText().toString(), opc.getText().toString(), opcorrect.getText().toString());
                    if (x == true) {
                        Toast.makeText(getActivity(), "Question Entered!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Question Not Entered!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    opcorrect.requestFocus();
                    opcorrect.setError("Type in the correct answer");
                }

//                ques.setText("");
//                opa.setText("");
//                opb.setText("");
//                opc.setText("");
//                opcorrect.setText("");

            }
        });
    }
    public static SetQuestion newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        SetQuestion sampleFragment = new SetQuestion();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }
}

