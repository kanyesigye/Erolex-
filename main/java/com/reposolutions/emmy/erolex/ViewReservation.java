
package com.reposolutions.emmy.erolex;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;




/**
 * Created by emmy on 8/4/2017.
 */

public class ViewReservation extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 2;
    RecyclerView recyclerView;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public ViewReservation() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reservation);


//        AddMenuModal md=new AddMenuModal();
        String killThis = getIntent().getStringExtra(EditMenu.VENDOR);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(killThis).child("reservation").getRef();
        recyclerView = (RecyclerView) findViewById(R.id.reserve_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewReservation.this));
        Toast.makeText(ViewReservation.this, "wait.. Retrieving Reservations", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter mFirebaseAdapter = new FirebaseRecyclerAdapter<ViewReservationModal, ViewReservationViewHolder>
                (ViewReservationModal.class, R.layout.show_reservation_single_item, ViewReservationViewHolder.class, databaseReference) {


            public void populateViewHolder(final ViewReservationViewHolder viewHolder, final ViewReservationModal
                    model, final int position) {
                viewHolder.resCusId(model.getCustomerId());
                viewHolder.resName(model.getCustomerName());
                viewHolder.resPhone(model.getCustomerPhone());
                viewHolder.resNote(model.getCustomerNote());
                viewHolder.resDate(model.getCustomerDate());
                viewHolder.resTime(model.getCustomerTime());
                viewHolder.resTotal(model.getCustomerQty());


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewReservation.this);
                        builder.setMessage("Would you like to confirm this reservation ?");
                        builder.setCancelable(true);
                        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dailog, int which) {
                                ///start an intent to send customer  text and vendor notification
                                //String number = model.getCustomerPhone();
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                //intent.setData(Uri.parse("smsto:"+number));
                                intent.putExtra(Intent.EXTRA_TEXT,"Hello: "+model.getCustomerName()+"Your reservation is comfirmed with ID: "+model.getCustomerId()+"Total on Bill: "+model.getCustomerQty()+" at "+model.getCustomerTime());
                                // Verify that the intent will resolve to an activity to prevent the app from crushing
                                //if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                //}


                                viewHolder.ch.setChecked(true);
                                viewHolder.ch.setText(R.string.conf);
                                notifyMe();
                            }

                            private void notifyMe() {
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(ViewReservation.this);
                                builder.setSmallIcon(R.drawable.ic_stat_notification);
                                builder.setAutoCancel(true);
                                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                                builder.setContentTitle("E Rolex Notification");
                                builder.setContentText("You Just confirmed a reservation by: "+ model.getCustomerName());
                                builder.setSubText("Total on Bill: "+model.getCustomerQty()+" Due: "+model.getCustomerTime()+" Today");
                                NotificationManager notificationManager = (NotificationManager) getSystemService(
                                        NOTIFICATION_SERVICE);
                                notificationManager.notify(NOTIFICATION_ID, builder.build());
                            }


                        });
                        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String ref = model.getCustomerId();
                                databaseReference.getRef().child(ref).removeValue();
                                dialog.cancel();

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Reserve");
                        dialog.show();
                    }

                });

            }




        };
        recyclerView.setAdapter(mFirebaseAdapter);


    }


    public void backOut(View view) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        finish();
        startActivity(new Intent(ViewReservation.this, login.class));
    }

    //view holder for recycler view
    public static class ViewReservationViewHolder extends RecyclerView.ViewHolder {

        private final TextView phone, total, note, date, time, title, cusId;
        private final CheckBox ch;

        public ViewReservationViewHolder(final View itemView) {
            super(itemView);
            note = (TextView) itemView.findViewById(R.id.desc);
            phone = (TextView) itemView.findViewById(R.id.cus_phone);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            total = (TextView) itemView.findViewById(R.id.total);
            title = (TextView) itemView.findViewById(R.id.customerName);
            cusId = (TextView) itemView.findViewById(R.id.customerId);
               ch= (CheckBox) itemView.findViewById(R.id.checkBox);
        }


        public void resPhone(String phon) {
            String a = "CONTACT:" + phon;
            phone.setText(a);
        }

        public void resName(String titl) {
            String a = "CUSTOMER NAME:" + titl;
            title.setText(a);
        }

        public void resNote(String not) {
            String a = "NOTE:" + not;
            note.setText(a);
        }

        public void resDate(String dat) {
            if (dat.equals("TODAY")) {
                Date tt = new Date();
                String b = String.valueOf(tt.getDate());
                String a = "DATE DUE:" + b;
                date.setText(a);
            } else {
                String a = "DATE DUE:" + "UNSPECIFIED";
                date.setText(a);
            }


        }

        public void resTime(String tim) {
            String a = "TIME:" + tim;
            time.setText(a);
        }

        public void resTotal(String tota) {

            String a = "TOTAL BILL:" + tota + " SHILLINGS";
            total.setText(a);
        }

        public void resCusId(String Id) {
            String a = "ID:" + Id;
            cusId.setText(a);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewReservation.this, login.class);
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

        int i = item.getItemId();

        if (i == R.id.addMenu) {
            startActivity(new Intent(ViewReservation.this, EditMenu.class));
            return true;
        } else if (i == R.id.abt_v) {
            startActivity(new Intent(ViewReservation.this, AboutVendor.class));
            return true;

        } else if (i == R.id.ss) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(ViewReservation.this, login.class));
            return true;
        } else if (i == R.id.debt) {

            startActivity(new Intent(ViewReservation.this, Debtors.class));
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }


    }
}


