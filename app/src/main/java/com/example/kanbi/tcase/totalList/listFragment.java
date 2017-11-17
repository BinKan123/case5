package com.example.kanbi.tcase.totalList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kanbi.tcase.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;


public class listFragment extends Fragment  {

    private RecyclerView recyclerView;
    private productAdapter adapter;
    private ArrayList<productDataModel> productsList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public listFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list,container,false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


         productsList=new ArrayList<>();


        firebaseDatabase=FirebaseDatabase.getInstance();



        loadDataFromFirebase();

        return view;

    }



     private void loadDataFromFirebase() {
         databaseReference=firebaseDatabase.getReference().child("products");
         databaseReference.addChildEventListener(new ChildEventListener() {

             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 productDataModel productsData = dataSnapshot.getValue(productDataModel.class);
                 //now add to the arraylist
                 productsList.add(productsData);

                 adapter = new productAdapter(productsList);
                 //listFragment.this
                 recyclerView.setAdapter(adapter);
                 adapter.notifyDataSetChanged();

             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

    }

   /* @Override
    public void onFavClick(productDataModel favClicked) {
        /*favClicked.getImage();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(favClicked.getImage()));
        startActivity(intent);*/


        /*public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // The toggle is enabled

            } else {
                // The toggle is disabled
            }
        }
*/



    }

