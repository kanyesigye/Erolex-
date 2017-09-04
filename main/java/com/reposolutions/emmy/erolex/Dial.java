package com.reposolutions.emmy.erolex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




/**
 * Created by emmy on 8/3/2017.
 */

public class Dial extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private RecyclerView dialRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dail_layout);
        dialRecycler=(RecyclerView)findViewById(R.id.list_phone);
        dialRecycler.setLayoutManager(new LinearLayoutManager(Dial.this));
      databaseReference=FirebaseDatabase.getInstance().getReference().child("menu");
        Toast.makeText(Dial.this,"wait.. Retrieving Contacts", Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<DialModal, Dial.DialViewHolder> mFirebaseAdapter = new FirebaseRecyclerAdapter<DialModal, Dial.DialViewHolder>
                (DialModal.class, R.layout.individual_call_row, Dial.DialViewHolder.class, databaseReference) {


            public void populateViewHolder(final Dial.DialViewHolder viewHolder, final DialModal
                    model, final int position) {

                viewHolder.resName(model.getName());
                viewHolder.resPhone(model.getPhone());


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Dial.this);
                        builder.setMessage("Would you like to call:"+model.getName()+" for a reservation?").setCancelable(true)
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dailog, int which) {
                                        ///start an implicit intent to call vendor

                                        String number =model.getPhone();

                                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                        callIntent.setData(Uri.parse("tel:" +number));
                                        startActivity(callIntent);
                                    }
                                })
                                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Call For Reservation");
                        dialog.show();
                    }

                });

            }
        };
        dialRecycler.setAdapter(mFirebaseAdapter);


    }

    public static class DialViewHolder extends RecyclerView.ViewHolder {

        private final TextView phone,title;


        public DialViewHolder(final View itemView){
            super(itemView);

            phone =(TextView) itemView.findViewById(R.id.vendor_mobile);
            title=(TextView) itemView.findViewById(R.id.vendor_joint_name);


        }


        public void resPhone(String phon) {
            String a ="PHONE:"+phon;
            phone.setText(a);

        }
        public  void resName(String titl) {
            String a ="VENDOR NAME:"+titl;
            title.setText(a);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Dial.this,ViewMenu.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rolex_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();

        if (i == R.id.get_contacts) {
            startActivity(new Intent(Dial.this, Dial.class));
            return true;
        } else if (i == R.id.abt_rolex) {
            startActivity(new Intent(Dial.this, AboutCustomer.class));
            return true;
        } else if (i == R.id.home) {
            startActivity(new Intent(Dial.this, ViewMenu.class));
            return true;
        } else if (i == R.id.sOut) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(Dial.this, login.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }



    }
}