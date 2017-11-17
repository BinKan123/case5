package com.example.kanbi.tcase.myFavorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.kanbi.tcase.R;
import com.example.kanbi.tcase.totalList.productAdapter;
import com.example.kanbi.tcase.totalList.productDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by kanbi on 16/11/2017.
 */

public class favAdapter extends RecyclerView.Adapter<favAdapter.ViewHolder> {


    private ArrayList<favModel> list;

    public favAdapter(ArrayList<favModel> List) {
        list = List;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView currency;
        public TextView id;
        public ImageView image;
        public TextView price;
        public TextView title;

        public Button delBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            currency=(TextView) itemView.findViewById(R.id.currency);
            id=(TextView) itemView.findViewById(R.id.id);
            image=(ImageView) itemView.findViewById(R.id.productImg);
            price=(TextView) itemView.findViewById(R.id.price);
            title=(TextView) itemView.findViewById(R.id.title);
            delBtn=(Button) itemView.findViewById(R.id.delBtn);
        }

    }


    @Override
    public favAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_card, viewGroup, false);

        return new favAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(favAdapter.ViewHolder holder, final int i) {
        final favModel item = list.get(i);

        holder.currency.setText(item.getCurrency());
        holder.id.setText(String.valueOf(item.getId()));
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.title.setText(item.getTitle());



        final Context context = holder.itemView.getContext();

        Picasso.with(context).load(item.getImage()).into(holder.image);

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            @Override
            public void onClick(View view) {

        ref.child("favorite").orderByChild("title").equalTo(item.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String itemKey = childSnapshot.getKey();
                    ref.child("favorite").child(itemKey).removeValue();
                    notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


    }
});





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

