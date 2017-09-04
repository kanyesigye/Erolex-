package com.reposolutions.emmy.erolex;

/**
 * Created by emmy on 7/27/2017.
 */



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;




public class Register extends AppCompatActivity {
    EditText pass,mail;
    TextView submit;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mail = (EditText) findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.passwrd);
        submit = (TextView)findViewById(R.id.sub);
        mAuth=FirebaseAuth.getInstance();


    }



public void addUser(View v) {
    String email = mail.getText().toString();
    String password = pass.getText().toString();


    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
         progressDialog= new ProgressDialog(Register.this);
        progressDialog.setMessage("Registering please wait...");
        progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            progressDialog.dismiss();

            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, R.string.auth_failed,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, R.string.auth_success,
                    Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder= new AlertDialog.Builder(Register.this);
                builder.setCancelable(false);
                builder.setMessage("please select a view to continue").setCancelable(true)
                        .setPositiveButton("customer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dailog, int which) {
                                ///start an intent to vendor view
                                startActivity(new Intent(Register.this,ViewMenu.class));
                            }
                        })
                        .setNegativeButton("vendor", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Register.this,EditMenu.class));
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.setTitle("choose view");
                dialog.show();


            }

        }
    });
       } else {
            Toast.makeText(this, "please fill in all fields..", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Register.this,login.class);
        startActivity(intent);
    }
 }

