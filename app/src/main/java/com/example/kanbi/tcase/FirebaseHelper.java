package com.example.kanbi.tcase;

import com.example.kanbi.tcase.totalList.listFragment;
import com.example.kanbi.tcase.totalList.productAdapter;
import com.example.kanbi.tcase.totalList.productDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by 5261 on 2017-11-16.
 */

public class FirebaseHelper {

    DatabaseReference  databaseReference;
    Boolean saved=null;
    ArrayList<productDataModel> productList=new ArrayList<>();


    public FirebaseHelper(DatabaseReference db){
        this.databaseReference=db;
    }



    //save
    public Boolean saveData(productDataModel productList){
        if(productList==null){
            saved=false;

        }else{
            try
            {
                databaseReference.child("favorite").push().setValue(productList);
                saved=true;
            } catch(DatabaseException e){
                e.printStackTrace();
                saved=false;
            }

        }
         return saved;
    }


    //read
    public ArrayList<productDataModel> retrieve(){

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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
        return productList;
    }


    private void fetchData(DataSnapshot dataSnapshot){

        productList.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()){
            productDataModel productsData = dataSnapshot.getValue(productDataModel.class);
            productList.add(productsData);
        }

        }
    }





}
