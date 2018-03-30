package com.example.rajat.projectm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FourButtonsActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_buttons);

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
                    startActivity(new Intent(FourButtonsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(SampleFragment1.newInstance("Welcome!"), R.drawable.ic_home_white_24dp, "Home"),
                new BottomBarFragment(SetQuestion.newInstance("Content for food."), R.drawable.ic_help_white_24dp, "Set Questions"),
                new BottomBarFragment(SetStudyMaterial.newInstance("Content for favorites."), R.drawable.ic_book_open_variant_white_24dp, "Study Material"),
                new BottomBarFragment(TeacherLogout.newInstance(""), R.drawable.ic_logout_white_24dp, "Logout")
        );

        final Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#3B494C"));
        window.setNavigationBarColor(Color.parseColor("#4e5b5d"));
        // Setting colors for different tabs when there's more than three of them.
        bottomBar.mapColorForTab(0, "#3B494C");
        bottomBar.mapColorForTab(1, "#00796B");
        bottomBar.mapColorForTab(2, "#7B1FA2");
        bottomBar.mapColorForTab(3, "#FF5252");

        bottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        // Item 1 Selected
                        getSupportFragmentManager().beginTransaction();
                        window.setStatusBarColor(Color.parseColor("#3B494C"));
                        window.setNavigationBarColor(Color.parseColor("#4e5b5d"));
                        break;
                    case 1:
                        //Item 2 Selected
                        SetQuestion setQuestion=new SetQuestion();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,setQuestion).commit();
                        window.setStatusBarColor(Color.parseColor("#00796B"));
                        window.setNavigationBarColor(Color.parseColor("#198679"));
                        break;
                    case 2:
                        SetStudyMaterial setStudyMaterial=new SetStudyMaterial();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,setStudyMaterial).commit();
                        window.setStatusBarColor(Color.parseColor("#7B1FA2"));
                        window.setNavigationBarColor(Color.parseColor("#8835ab"));
                        break;
                    case 3:
                        window.setStatusBarColor(Color.parseColor("#FF5252"));
                        window.setNavigationBarColor(Color.parseColor("#ff6363"));
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder=new AlertDialog.Builder(FourButtonsActivity.this);
        builder.setTitle("Logout").setIcon(R.drawable.ic_logout_grey600_24dp).setMessage("Are you sure?").setCancelable(false);
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
                Intent i = new Intent(FourButtonsActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(FourButtonsActivity.this,"Logged Out Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(FourButtonsActivity.this,FourButtonsActivity.class);
                startActivity(i);
                FourButtonsActivity.this.finish();
                Toast.makeText(FourButtonsActivity.this,"Logout Failed",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
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

    @Override
    public void onPause() {
        super.onPause();
        auth.signOut();
    }
}