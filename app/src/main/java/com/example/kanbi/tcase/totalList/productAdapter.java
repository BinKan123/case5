package com.example.kanbi.tcase.totalList;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.kanbi.tcase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kanbi on 15/11/2017.
 */

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {


    private ArrayList<productDataModel> list;
    private ArrayList<Object> products;
    private ArrayList<Object> favorites;
    private DatabaseReference ref;



    public productAdapter(ArrayList<productDataModel> List) {
        list = List;

        ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // TODO: Update Adapter List from new data

                Object productsList = dataSnapshot.child("products").getValue();
                Object favoritesMap = dataSnapshot.child("favorites").getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView currency;
        public TextView id;
        public ImageView image;
        public TextView price;
        public TextView title;

        public ToggleButton favBtn;

        DatabaseReference mDatabaseFav;

        public ViewHolder(View itemView) {
            super(itemView);

            currency = (TextView) itemView.findViewById(R.id.currency);
            id = (TextView) itemView.findViewById(R.id.id);
            image = (ImageView) itemView.findViewById(R.id.productImg);
            price = (TextView) itemView.findViewById(R.id.price);
            title = (TextView) itemView.findViewById(R.id.title);

            favBtn = (ToggleButton) itemView.findViewById(R.id.toggleButton);

            mDatabaseFav = FirebaseDatabase.getInstance().getReference().child("favorite");
            mDatabaseFav.keepSynced(true);
        }

    }


    @Override
    public productAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_card, viewGroup, false);

        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final productAdapter.ViewHolder holder, final int i) {

        if (holder.favBtn.isChecked()){
            holder.favBtn.setBackgroundResource(R.drawable.ic_fav_on);
        }else{
            holder.favBtn.setBackgroundResource(R.drawable.ic_favorite);
        }

        final productDataModel item = list.get(i);

        holder.currency.setText(item.getCurrency());
        holder.id.setText(String.valueOf(item.getId()));
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.title.setText(item.getTitle());

        final Context context = holder.itemView.getContext();

        Picasso.with(context).load(item.getImage()).into(holder.image);



        holder.favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
            final String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (isChecked) {
            holder.favBtn.setBackgroundResource(R.drawable.ic_fav_on);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                        ref.child("favorite").child(uId).push().setValue(item);

                        Toast.makeText(context, item.getTitle() + "  is added as favorite",
                                Toast.LENGTH_LONG).show();


                        holder.favBtn.setSelected(true);
                        holder.favBtn.setSaveEnabled(false);
                        notifyDataSetChanged();
                    }



                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });

        } else {

            holder.favBtn.setBackgroundResource(R.drawable.ic_favorite);

            ref.child("favorite").child(uId).orderByChild("title").equalTo(item.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String itemKey = childSnapshot.getKey();

                        ref.child("favorite").child(uId).child(itemKey).removeValue();
                        Toast.makeText(context, item.getTitle() + "  has been removed from favorite",
                                Toast.LENGTH_LONG).show();

                        holder.favBtn.setSelected(false);
                        holder.favBtn.setSaveEnabled(false);

                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });

        }

    }
    });

}

    @Override
    public int getItemCount() {
        return list.size();
    }

}
