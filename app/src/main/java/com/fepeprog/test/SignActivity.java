package com.fepeprog.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.fepeprog.test.database.DBHandler;
import com.fepeprog.test.fragments.SignFragment;

public class SignActivity extends AppCompatActivity {
    public static final String TAG = "test_SS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.sign_frame_container, new SignFragment())
                    .commit();
        }
        DBHandler.initDB(this);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Information!")
                    .setMessage("Exit from app?")
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
