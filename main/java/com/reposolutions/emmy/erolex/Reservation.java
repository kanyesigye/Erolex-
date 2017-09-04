package com.reposolutions.emmy.erolex;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



//import static com.reposolutions.emmy.erolex.EditMenu.REQUEST_LOCATION;

/**
 * Created by emmy on 8/3/2017.
 */

public class Reservation extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText name,pNumber;
    private Button plus, minus, quantity;
    private TextView reserve,heads;
    private Spinner note ,time,date;
    private int count;
    private String prices;
    private String tt,vendor,lat,lng;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_view);

       name=(EditText)findViewById(R.id.name);
        pNumber=(EditText)findViewById(R.id.phone);
        date=(Spinner) findViewById(R.id.date);
        time=(Spinner)findViewById(R.id.time);
        note=(Spinner)findViewById(R.id.note);

        plus=(Button)findViewById(R.id.plus);
        minus=(Button)findViewById(R.id.minus);
        quantity=(Button)findViewById(R.id.sign);
        heads=(TextView)findViewById(R.id.heading);
        reserve=(TextView)findViewById(R.id.make);
        count=0;

        mDatabase= FirebaseDatabase.getInstance().getReference().child("menu");

        vendor= getIntent().getStringExtra(ViewMenu.NAME);

        heads.setText(vendor);




    }
public void setValue(View v){

        prices= getIntent().getStringExtra(ViewMenu.PRICE);

        if(v.getId()==R.id.plus){
            count++;
            String number = String.valueOf(count);
            quantity.setText(number);
            int rt= Integer.valueOf(prices);
            tt=String.valueOf(count*rt);
            Toast.makeText(Reservation.this, "Your Total bill is: "+tt+" shillings!",Toast.LENGTH_SHORT).show();
        }else if(v.getId()==R.id.minus){
            count--;
            if(count<=1){count=1;}
            String number = String.valueOf(count);
            quantity.setText(number);
            int rt= Integer.valueOf(prices);
            tt=String.valueOf(count*rt);
            Toast.makeText(Reservation.this,"Your Total bill is: "+tt+" shillings!",Toast.LENGTH_SHORT).show();
        }


}
  public void onReserve(View v){


    String Name  =  name.getText().toString();
      String Phone=pNumber.getText().toString();
      String  Dates= date.getSelectedItem().toString();
      String Time=time.getSelectedItem().toString();
      String Note=note.getSelectedItem().toString();
      String Qty=tt;
      if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Phone) && !TextUtils.isEmpty(Dates) && !TextUtils.isEmpty(Time) && !TextUtils.isEmpty(Note)&& !TextUtils.isEmpty(Qty)){
        //  if(Note.equals("I PICK MY ORDER")||Note.equals("I PICK MY ORDER ON CREDIT")){
              String Id= mDatabase.push().getKey();
              ReservationModal modal = new ReservationModal(Id,Name,Phone,Note,Dates,Time,Qty);
              mDatabase.child(vendor).child("reservation").child(Id).setValue(modal);
              Toast.makeText(this, "Reservation Submitted to queue.. Wait for sms confirmation from: "+vendor, Toast.LENGTH_LONG).show();
//          } else{
//             final AlertDialog.Builder builder = new AlertDialog.Builder(Reservation.this);
//              builder.setMessage("your current location will be shared with vendor for delivery").setCancelable(true)
//                      .setPositiveButton("accept", new DialogInterface.OnClickListener() {
//                          @Override
//                          public void onClick(DialogInterface dailog, int which) {
//                          getMyLocation();
//                          saveCrap();
//
//                          }
//
//                          private void saveCrap() {
//                          ///////start from here next time.
//                          }
//
//                          private void getMyLocation() {
//
//                                      if (ActivityCompat.checkSelfPermission(Reservation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Reservation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                          ActivityCompat.requestPermissions(Reservation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//                                      } else {
//                                          LocationManager locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                                          Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                                          TextView loc= (TextView) findViewById(R.id.myloc);
//                                          if (location != null) {
//                                              lat = String.valueOf(location.getLatitude());
//                                              lng = String.valueOf(location.getLongitude());
//                                              loc.setText("latitude:" + lat + " " + "longitude:" + lng);
//                                          } else {
//                                              loc.setText("Unable to capture location! retry");
//                                          }
//                                      }
//                          }
//
//
//                      })
//                      .setNegativeButton("deny", new DialogInterface.OnClickListener() {
//                          @Override
//                          public void onClick(DialogInterface dialog, int which) {
//
//                              dialog.cancel();
//                          }
//                      });
//              AlertDialog dialog = builder.create();
//              dialog.setTitle("Call For Reservation");
//              dialog.show();
//          }

      }else{
          Toast.makeText(this, "please fill in all fields..", Toast.LENGTH_SHORT).show();
      }
  }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Reservation.this,ViewMenu.class);
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


        // Handle item selection
        int i = item.getItemId();

        if (i == R.id.get_contacts) {
            startActivity(new Intent(Reservation.this, Dial.class));
            return true;
        } else if (i == R.id.abt_rolex) {
            startActivity(new Intent(Reservation.this, AboutCustomer.class));
            return true;
        } else if (i == R.id.home) {
            startActivity(new Intent(Reservation.this, ViewMenu.class));
            return true;
        } else if (i == R.id.sOut) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(Reservation.this, login.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }



    }
}
