package com.example.rajat.projectm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TeacherLogout extends Fragment {
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public TeacherLogout() {
    }

    public static TeacherLogout newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        TeacherLogout teacherLogout = new TeacherLogout();
        teacherLogout.setArguments(args);

        return teacherLogout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout").setIcon(R.drawable.ic_logout_grey600_24dp).setMessage("Are you sure?").setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent i=new Intent(getActivity(),FourButtonsActivity.class);
//                startActivity(i);
//                getActivity().finish();
                Toast.makeText(getActivity(),"Logout Failed",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(),"Logged Out Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        TextView textView = new TextView(getActivity());
        textView.setText(getArguments().getString(STARTING_TEXT));

        return textView;
    }
    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}