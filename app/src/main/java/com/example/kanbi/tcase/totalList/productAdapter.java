package com.example.kanbi.tcase.totalList;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by kanbi on 15/11/2017.
 */

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {
    //interface
    private productAdapter.OnClickListener onClicklistener;

    public interface OnClickListener {
        void onFavClick(productDataModel favClicked);
    }


    private ArrayList<productDataModel> list;

    public productAdapter(ArrayList<productDataModel> List,productAdapter.OnClickListener onClicklistener) {
        list = List;
        this.onClicklistener = onClicklistener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView currency;
        public TextView id;
        public ImageView image;
        public TextView price;
        public TextView title;

        public ToggleButton favBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            currency=(TextView) itemView.findViewById(R.id.currency);
            id=(TextView) itemView.findViewById(R.id.id);
            image=(ImageView) itemView.findViewById(R.id.productImg);
            price=(TextView) itemView.findViewById(R.id.price);
            title=(TextView) itemView.findViewById(R.id.title);

            favBtn=(ToggleButton) itemView.findViewById(R.id.toggleButton);
        }

    }


    @Override
    public productAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_card, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(productAdapter.ViewHolder holder, int i) {
        final productDataModel item = list.get(i);

        holder.currency.setText(item.getCurrency());
        holder.id.setText(String.valueOf(item.getId()));
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.title.setText(item.getTitle());



        final Context context = holder.itemView.getContext();

        Picasso.with(context).load(item.getImage()).into(holder.image);

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClicklistener!=null)
                {
                    onClicklistener.onFavClick(item);;
                }
            }
        });

        holder.favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context, "Button is on",
                            Toast.LENGTH_LONG).show();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("favorite");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });

                } else {

                    Toast.makeText(context, "Button is off",
                            Toast.LENGTH_LONG).show();
                }
                //story.comment.setShowAll(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
