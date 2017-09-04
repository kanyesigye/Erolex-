package com.reposolutions.emmy.erolex;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * Created by emmy on 8/4/2017.
 */

public class ViewMenu extends AppCompatActivity {
    public static final String PRICE = "PRICE";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NAME = "NAME";
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
   private  DatabaseReference menuReference;

    public ViewMenu() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmenu_view);

        menuReference = FirebaseDatabase.getInstance().getReference().child("menu");

        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewMenu.this));
        Toast.makeText(ViewMenu.this,"wait.. Retrieving menu list", Toast.LENGTH_LONG).show();


    }


    @Override
    protected void onStart() {
        super.onStart();
 final  FirebaseRecyclerAdapter<ViewMenuModal, ViewMenuViewHolder> menuFirebaseAdapter = new FirebaseRecyclerAdapter<ViewMenuModal, ViewMenuViewHolder>
                (ViewMenuModal.class, R.layout.show_menu_single_item, ViewMenuViewHolder.class, menuReference) {
            public void populateViewHolder(final ViewMenuViewHolder viewHolder, final ViewMenuModal
                    model, final int position) {

                viewHolder.menuImage_Title(model.getMenuTitle());
                viewHolder.menuDescription(model.getMenuDscription());
                viewHolder.menuImage_Price(model.getMenuPrice());
                viewHolder.menuIdentity(model.getMenuId());
                viewHolder.menuVenName(model.getName());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewMenu.this);
                        builder.setMessage("Would you like to make a reservation for this rolex?").setCancelable(true)
                                .setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dailog, int which) {
                                        ///start an intent to reservation

                                        Intent reserve = new Intent(ViewMenu.this, Reservation.class);
                                       // reserve.putExtra("price ", int price);

                                        reserve.putExtra(PRICE, model.getMenuPrice());
                                        reserve.putExtra(NAME, model.getName());
                                        startActivity(reserve);
                                    }
                                }).setNegativeButton("Locate", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //implement code to redirect to map activity e.g

                                Intent locate = new Intent(ViewMenu.this, Maps.class);

                                String lat= model.getLatitude();
                                String lng= model.getLongitude();
                                locate.putExtra(LATITUDE,lat);
                                locate.putExtra(LONGITUDE,lng);
                                startActivity(locate);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("confirm");
                        dialog.show();
                    }

                });
            }
            };

        recyclerView.setAdapter(menuFirebaseAdapter);


    }

    public void backOut(View view) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        finish();
        startActivity(new Intent(ViewMenu.this,login.class));
    }

    //view holder for recycler view
    public static class ViewMenuViewHolder extends RecyclerView.ViewHolder {

    private final TextView menId,image_title,description,price,nn;
    //private final ImageView image_url;

    public ViewMenuViewHolder(final View itemView){
        super(itemView);
      //  image_url=(ImageView) itemView.findViewById(R.id.fetch_image);
        image_title=(TextView) itemView.findViewById(R.id.title2);
        description=(TextView) itemView.findViewById(R.id.desc2);
        price=(TextView) itemView.findViewById(R.id.price2);
        menId=(TextView) itemView.findViewById(R.id.men);
        nn=(TextView) itemView.findViewById(R.id.ven_name);

    }
public void menuImage_Title (String tit){
String a="TITLE:"+tit;

 image_title.setText(a);
}
public void menuDescription(String desc) {
    String a="INGREDIENTS:"+desc;
    description.setText(a);
}
public void menuImage_Price (String pric) {
    String a="PRICE:"+pric;
        price.setText(a);
}

    public void menuIdentity(String Id) {
        String a="MENU ID:"+Id;
        menId.setText(a);
    }

        public void menuVenName(String n) {
            String a="VENDOR NAME: "+n;

            nn.setText(a);
        }
//private void menuImage_url(String url){
//
//        Picasso.with(itemView.getContext())
//                .load(url)
//                .fit()
//                .centerCrop()
//                .into(image_url);
//    }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewMenu.this,login.class);
        startActivity(intent);

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
            startActivity(new Intent(ViewMenu.this, Dial.class));
            return true;
        } else if (i == R.id.abt_rolex) {
            startActivity(new Intent(ViewMenu.this, AboutCustomer.class));
            return true;
        } else if (i == R.id.home) {
            startActivity(new Intent(ViewMenu.this, ViewMenu.class));
            return true;
        } else if (i == R.id.sOut) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(ViewMenu.this, login.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }



    }

}


