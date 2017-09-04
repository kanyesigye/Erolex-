package com.reposolutions.emmy.erolex;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class login extends AppCompatActivity {
    private  EditText usr,pswd;
      TextView lin,sup,bytes;
     ProgressDialog progressDialog;

 private    FirebaseAuth mAuth;
  private    FirebaseAuth.AuthStateListener mAuthListener;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
           usr = (EditText) findViewById(R.id.usrusr);
           pswd = (EditText)findViewById(R.id.passwrd);
           lin = (TextView)findViewById(R.id.logiin);
           sup = (TextView)findViewById(R.id.sup);
           bytes = (TextView)findViewById(R.id.bytes);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {


                    AlertDialog.Builder builder= new AlertDialog.Builder(login.this);
                    builder.setCancelable(false);
                    builder.setMessage("please select a view to continue").setCancelable(true)
                            .setPositiveButton("customer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dailog, int which) {
                                    ///start an intent to vendor view
                                    startActivity(new Intent(login.this,ViewMenu.class));
                                }
                            })
                            .setNegativeButton("vendor", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(login.this,EditMenu.class));
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("choose view");
                    dialog.show();

                } else {
                    // User is signed out
                    Toast.makeText(login.this,"Please login to continue..", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };





    }


    public void info(View v){
        startActivity(new Intent(login.this,about.class));
    }
    public void reg(View v){startActivity(new Intent(login.this, Register.class));}

   public void startSignIn(View v) {
       String  email = usr.getText().toString();
       String password = pswd.getText().toString();
      if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
          Toast.makeText(login.this,"fields Empty", Toast.LENGTH_SHORT).show();
      }else{
          progressDialog= new ProgressDialog(login.this);
          progressDialog.setMessage("authenticating please wait...");
          progressDialog.show();

          mAuth.signInWithEmailAndPassword(email, password)

                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                                               if  (!task.isSuccessful()) {
                              progressDialog.dismiss();
                              Toast.makeText(login.this, R.string.signin_failed, Toast.LENGTH_SHORT).show();
                          }else{
                              progressDialog.dismiss();

                                                   AlertDialog.Builder builder= new AlertDialog.Builder(login.this);
                                                   builder.setCancelable(false);
                                                   builder.setMessage("please select a view to continue").setCancelable(true)
                                                           .setPositiveButton("customer", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dailog, int which) {
                                                                   ///start an intent to vendor view
                                                                   startActivity(new Intent(login.this,ViewMenu.class));
                                                               }
                                                           })
                                                           .setNegativeButton("vendor", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   startActivity(new Intent(login.this,EditMenu.class));
                                                               }
                                                           });
                                                   AlertDialog dialog = builder.create();
                                                   dialog.setTitle("choose view");
                                                   dialog.show();
                          }

                      }
                  });
      }




   }






    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
   finish();
    }
}
