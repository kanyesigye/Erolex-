package com.reposolutions.emmy.erolex;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseAuth mAuth;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServicesAvailable()) {

            Toast.makeText(this, "google play services available, time to find  a rolex", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            initMap();

        } else {
            Toast.makeText(this, "google play services not installed!!!", Toast.LENGTH_LONG).show();
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "cannot connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.child("menu").getChildren()) {
                    String rightLocation = child.child("longitude").getValue().toString();
                    String leftLocation = child.child("latitude").getValue().toString();
                    double location_left = Double.parseDouble(leftLocation);
                    double location_right = Double.parseDouble(rightLocation);
                    String rolexJoint = child.child("name").getValue().toString();
                    LatLng cod = new LatLng(location_left, location_right);
                    mMap.addMarker(new MarkerOptions().position(cod).title(rolexJoint + " is located here"));
                }

               String latitude= getIntent().getStringExtra(ViewMenu.LATITUDE);
               String longitude= getIntent().getStringExtra(ViewMenu.LONGITUDE);
               Double lx=Double.valueOf(latitude);
                Double lt=Double.valueOf(longitude);
                goToZoomLocation(lx,lt);

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void goToZoomLocation(double lat, double lng) {
        float zoom = 25;
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
        MarkerOptions options = new MarkerOptions()
                .snippet("Here")
                .position(ll)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        mMap.addMarker(options);
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
            startActivity(new Intent(Maps.this, Dial.class));
            return true;
        } else if (i == R.id.abt_rolex) {
            startActivity(new Intent(Maps.this, AboutCustomer.class));
            return true;
        } else if (i == R.id.home) {
            startActivity(new Intent(Maps.this, ViewMenu.class));
            return true;
        } else if (i == R.id.sOut) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(Maps.this, login.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }



    }


}

