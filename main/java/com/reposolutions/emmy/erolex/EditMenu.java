package com.reposolutions.emmy.erolex;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


/**
 * Created by emmy on 8/3/2017.
 */

public class EditMenu extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;
    public static final String VENDOR = "vendor";
    private static final String VEN = "myPrefs";
    LocationManager locationManager;
    private EditText desc, price, nameOfBizness, phoneNumber;
    private TextView loc;
    private Spinner rolex;
    private Double lat, lng;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private ImageView mImage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog progressDialog;
    private String Name;
    public static final String  v= "nameKey";
    public static final String pp = "phoneKey";
    public static final String p1 = "priceKey";
    public static final String p2 = "descKey";
    public static final String p3 = "titKey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmenu_layout);
        TextView textView = (TextView) findViewById(R.id.select);
        progressDialog = new ProgressDialog(this);
        mImage = (ImageView) findViewById(R.id.imageView);
        desc = (EditText) findViewById(R.id.desc);
        price = (EditText) findViewById(R.id.price);
        rolex = (Spinner) findViewById(R.id.title);
        nameOfBizness = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phone);
        loc = (TextView) findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("menu");

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(EditMenu.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditMenu.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditMenu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        loc.setText("latitude:" + lat + "  " + "longitude:" + lng);
                    } else {
                        loc.setText("unable to capture location!!!");
                    }
                }
            }
        });

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALLERY_INTENT);
//            }
//        });

        Toast.makeText(EditMenu.this, "Please Turn On GPS to capture location data..", Toast.LENGTH_LONG).show();

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
//            progressDialog.setMessage("uploading...");
//            progressDialog.show();
//            mStorage = FirebaseStorage.getInstance().getReference();
//            Uri uri = data.getData();
//            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
//
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    progressDialog.dismiss();
//                    Toast.makeText(EditMenu.this, "upload successful", Toast.LENGTH_SHORT).show();
//
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//
//                    Picasso.with(EditMenu.this).load(downloadUrl).fit().centerCrop().into(mImage);
//
//                    mDatabase.child("photoLink").setValue(downloadUrl.toString());
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.dismiss();
//
//                    Toast.makeText(EditMenu.this, "Upload Failed ! Try Again", Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
//        }
//
//    }


    public void addMenu(View view) {

         Name = nameOfBizness.getText().toString();
        String Phone = phoneNumber.getText().toString();
        String Price = price.getText().toString();
        String Description = desc.getText().toString();
        String Title = rolex.getSelectedItem().toString();
        if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Price) || TextUtils.isEmpty(Description) || TextUtils.isEmpty(Title)) {
            Toast.makeText(getBaseContext(), "fields empty", Toast.LENGTH_SHORT).show();
        } else {

            String longi = String.valueOf(lng);
            String lati = String.valueOf(lat);


            //add code for uploading menu details here
            String Id = mDatabase.push().getKey();
            AddMenuModal modal = new AddMenuModal(Id, Title, Description, Price, Name, Phone, lati, longi);
            mDatabase.child(Name).setValue(modal);


            Toast.makeText(EditMenu.this, "menu added successfully Thanks", Toast.LENGTH_SHORT).show();
            //an intent to the ViewReservation activity





            SharedPreferences sharedPreferences=  getSharedPreferences(VEN,Context.MODE_PRIVATE);
                      SharedPreferences.Editor editor=sharedPreferences.edit();

            editor.putString(v, Name);
            editor.putString(pp, Phone);
            editor.putString(p1, Price);
            editor.putString(p2, Description);
            editor.putString(p3, Title);
            editor.apply();

            Intent reserve = new Intent(EditMenu.this, ViewReservation.class);
            reserve.putExtra(VENDOR, Name);
            startActivity(reserve);



        }

    }
public void justLogin(View view){
    if(TextUtils.isEmpty(Name)){
        Toast.makeText(EditMenu.this,"Name Field Empty",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditMenu.this,EditMenu.class));
    }else{
        AlertDialog.Builder builder= new AlertDialog.Builder(EditMenu.this);
        builder.setCancelable(false);
        builder.setMessage("Already have an Account?").setCancelable(true)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dailog, int which) {
                        ///start an intent to vendor view
                        Intent reserve = new Intent(EditMenu.this, ViewReservation.class);
                        reserve.putExtra(VENDOR, Name);
                        startActivity(reserve);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Home");
        dialog.show();

    }

}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                onStart();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditMenu.this, login.class);
        startActivity(intent);
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
            startActivity(new Intent(EditMenu.this, EditMenu.class));
            return true;
        } else if (i == R.id.abt_v) {
            startActivity(new Intent(EditMenu.this, AboutVendor.class));
            return true;
        } else if (i == R.id.ss) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(EditMenu.this, login.class));
            return true;
        }else if (i == R.id.debt) {

            startActivity(new Intent(EditMenu.this, Debtors.class));
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }



    }
}

