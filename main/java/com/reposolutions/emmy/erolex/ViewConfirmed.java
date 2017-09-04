package com.reposolutions.emmy.erolex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by emmy on 9/1/2017.
 */

class ViewConfirmed extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmed);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reservation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        int i = item.getItemId();

        if (i == R.id.addMenu) {
            startActivity(new Intent(ViewConfirmed.this, EditMenu.class));
            return true;
        } else if (i == R.id.abt_v) {
            startActivity(new Intent(ViewConfirmed.this, AboutVendor.class));
            return true;

        } else if (i == R.id.ss) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(ViewConfirmed.this, login.class));
            return true;
        }else if (i == R.id.debt) {

            startActivity(new Intent(ViewConfirmed.this, Debtors.class));
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }



    }

}
