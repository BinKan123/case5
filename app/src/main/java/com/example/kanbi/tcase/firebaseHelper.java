package com.example.kanbi.tcase;

import android.content.Context;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by 5261 on 2017-11-20.
 */

public class firebaseHelper {
    Context context;


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public firebaseHelper(){

    }


    //addData to firebase
    public void addData(final ToggleButton tgBtn, final String child){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                if (!dataSnapshot.hasChild(child)) {
                    tgBtn.setBackgroundResource(R.drawable.ic_fav_on);

                    ref.child("favorite").push().setValue(child);

                }else{
                    Toast.makeText(context, child+"  has already been added as favorite",
                            Toast.LENGTH_LONG).show();


                }

                // }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //remove item from firebase




}
