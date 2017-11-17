package com.example.kanbi.tcase.myFavorite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanbi.tcase.R;
import com.example.kanbi.tcase.totalList.productAdapter;
import com.example.kanbi.tcase.totalList.productDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class favFragment extends Fragment  {

    private RecyclerView recyclerView;
    private favAdapter adapter;
    private ArrayList<favModel> favList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    public favFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fav,container,false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));



        favList=new ArrayList<>();


        firebaseDatabase=FirebaseDatabase.getInstance();



        loadDataFromFirebase();

        return view;

    }



    private void loadDataFromFirebase() {
        databaseReference=firebaseDatabase.getReference().child("favorite");
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                favModel favData = dataSnapshot.getValue(favModel.class);
                //now add to the arraylist
                favList.add(favData);

                adapter = new favAdapter(favList);
                recyclerView.setAdapter(adapter);





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


}

