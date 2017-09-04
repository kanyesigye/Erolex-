package com.reposolutions.emmy.erolex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by emmy on 8/8/2017.
 */

public class AboutVendor extends AppCompatActivity {
    TextView abtven;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_vendor);
        abtven= (TextView)findViewById(R.id.textView2);

    }

    public void back(View v){

        startActivity(new Intent(this,ViewReservation.class));



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
            startActivity(new Intent(AboutVendor.this, EditMenu.class));
            return true;
        } else if (i == R.id.abt_v) {
            startActivity(new Intent(AboutVendor.this, AboutVendor.class));
            return true;

        } else if (i == R.id.ss) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(AboutVendor.this, login.class));
            return true;
        }else if (i == R.id.debt) {

            startActivity(new Intent(AboutVendor.this, Debtors.class));
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }



    }
}