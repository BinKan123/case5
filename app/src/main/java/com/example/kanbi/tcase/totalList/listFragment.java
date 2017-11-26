package com.example.kanbi.tcase.totalList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.kanbi.tcase.R;
import com.example.kanbi.tcase.myFavorite.favAdapter;
import com.example.kanbi.tcase.myFavorite.favModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.ui.auth.ui.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.data;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class listFragment extends Fragment{

    private RecyclerView recyclerView;
    private productAdapter adapter;
    private ArrayList<productDataModel> productsList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public listFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_list,container,false);

        auth = FirebaseAuth.getInstance();

        //login options

        if(auth.getCurrentUser()!=null){

        }else {

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                                    ))
                            .setTheme(R.style.LoginTheme)
                            .setLogo(R.mipmap.ic_launcher)
                            .build(),
                    RC_SIGN_IN);
        }


        user = auth.getCurrentUser();



        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        productsList=new ArrayList<>();

        firebaseDatabase=FirebaseDatabase.getInstance();

        loadDataFromFirebase();
        addUser();

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

                 recyclerView.setAdapter(adapter);


             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                 Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                 // A comment has changed, use the key to determine if we are displaying this
                 // comment and if so displayed the changed comment.
                 /*productDataModel productsData = dataSnapshot.getValue(productDataModel.class);
                 //String commentKey = dataSnapshot.getKey();
                 productsList.add(productsData);
                 adapter = new productAdapter(productsList);
                 recyclerView.setAdapter(adapter);*/
                 adapter.notifyDataSetChanged();


             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {
                 Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                 // A comment has changed, use the key to determine if we are displaying this
                 // comment and if so remove it.
                 /*productDataModel productsData = dataSnapshot.getValue(productDataModel.class);
                 //String commentKey = dataSnapshot.getKey();
                 productsList.add(productsData);
                 adapter = new productAdapter(productsList);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                // Successfully signed in
                 user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getActivity(), user.getProviderId()+"has logged in",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Log in failed",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    private void addUser(){
        databaseReference=firebaseDatabase.getReference().child("users");

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(currentUserId).child("user name:").setValue(user.getEmail());


    }

 /* @Override
  public void onStart(){
      super.onStart();

      auth.addAuthStateListener(mAuthListener);

    //  FirebaseRecyclerAdapter(Product  )

  }*/

}

