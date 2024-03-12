package com.example.myfragmentsapp;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtils {
    private static FirebaseDatabase mDatabase;
    public FirebaseAuth mAuth;
    private static String currentUserId;

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public static void saveUserOnDatabase(User user, String uid) {
        assert uid != null : "User ID cannot be null";
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("users").child(uid);
        myRef.setValue(user);
    }

    public static void addProductToUserListOnDB(String userId , String productName) {
        DatabaseReference userProductsRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("products");
        userProductsRef.push().setValue(productName);
    }

    public static List<String> getUserProductsFromFirebase(String uid ,final OnDataLoadedListener listener ) {
        DatabaseReference userProductsRef = FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .child("products");
        List<String> productsList = new ArrayList<>();
        userProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    String product = productSnapshot.getValue(String.class);
                    productsList.add(product);
                }
                listener.onDataLoaded(productsList);
                System.out.println("User products updated successfully");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error reading user products data: " + databaseError.getMessage());
            }
        });
        return productsList;
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded(List<String> products);
    }
}


