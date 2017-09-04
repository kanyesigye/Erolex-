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

public class AboutCustomer extends AppCompatActivity {
    TextView abtcus;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_customer);
        abtcus= (TextView)findViewById(R.id.textView2);

    }

    public void back(View v){

                startActivity(new Intent(this,ViewMenu.class));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rolex_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        // Handle item selection
        int i = item.getItemId();

        if (i == R.id.get_contacts) {
            startActivity(new Intent(AboutCustomer.this, Dial.class));
            return true;
        } else if (i == R.id.abt_rolex) {
            startActivity(new Intent(AboutCustomer.this, AboutCustomer.class));
            return true;
        } else if (i == R.id.home) {
            startActivity(new Intent(AboutCustomer.this, ViewMenu.class));
            return true;
        } else if (i == R.id.sOut) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(AboutCustomer.this, login.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }



    }
}