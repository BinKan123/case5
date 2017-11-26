package com.example.kanbi.tcase.myFavorite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanbi.tcase.R;
import com.example.kanbi.tcase.totalList.productAdapter;
import com.example.kanbi.tcase.totalList.productDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


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
        final String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference=firebaseDatabase.getReference().child("favorite");
        databaseReference.child(uId).addChildEventListener(new ChildEventListener() {

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
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                /*favModel favData = dataSnapshot.getValue(favModel.class);
                //String commentKey = dataSnapshot.getKey();
                favList.add(favData);
                adapter = new favAdapter(favList);
                recyclerView.setAdapter(adapter);*/
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
               // String commentKey = dataSnapshot.getKey();
              /*  favModel favData = dataSnapshot.getValue(favModel.class);
                //String commentKey = dataSnapshot.getKey();
                favList.add(favData);
                adapter = new favAdapter(favList);
                recyclerView.setAdapter(adapter);*/
                adapter.notifyDataSetChanged();


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

