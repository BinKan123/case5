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
    //interface
    private favAdapter.OnClickListener onClicklistener;

    public interface OnClickListener {
        void delClick(favModel delClicked);
    }


    private ArrayList<favModel> list;

    public favAdapter(ArrayList<favModel> List,favAdapter.OnClickListener onClicklistener) {
        list = List;
        this.onClicklistener = onClicklistener;
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
            @Override
            public void onClick(View view) {
                if(onClicklistener!=null)
                {
                    onClicklistener.delClick(item);;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

