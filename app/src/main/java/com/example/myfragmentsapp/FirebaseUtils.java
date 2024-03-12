package com.example.myfragmentsapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static void addItemToUserListOnDB(String uid , String itemName , String itemQuantity , Context context) {
        DatabaseReference userProductsRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(uid)
                .child("products");
        userProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(itemName)) {

                    int intItemQuantity = Integer.parseInt(itemQuantity);
                    if (intItemQuantity <= 0) {
                        Toast.makeText(context, "Product Quantity Cant be 0", Toast.LENGTH_SHORT).show();
                    } else {
                        userProductsRef.child(itemName).setValue(itemQuantity);
                    }
                } else {
                    int intQuantity = Integer.parseInt(itemQuantity);
                    if (intQuantity > 0) {
                        userProductsRef.child(itemName).setValue(itemQuantity);
                    } else {
                        Toast.makeText(context, "Product Quantity Cant be 0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Error Updating Item data", databaseError.toException());
            }
        });

    }
    public static List<ListItem> getUserItemsFromFirebase(String uid ,final OnItemsDataLoadedListener listener ) {
        DatabaseReference userProductsRef = FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .child("products");
        List<ListItem> itemsList = new ArrayList<>();
        userProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    String itemQuantity = itemSnapshot.getValue(String.class);
                    ListItem item = new ListItem(itemName , itemQuantity);
                    itemsList.add(item);
                }
                listener.onItemsDataLoaded(itemsList);
                System.out.println("User products updated successfully");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error reading user products data: " + databaseError.getMessage());
            }
        });
        return itemsList;
    }

    public interface OnItemsDataLoadedListener {
        void onItemsDataLoaded(List<ListItem> items);
    }

    public static void removeItemFromUserListInDB(String uid, String itemName) {
        DatabaseReference userProductsRef = FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .child("products")
                .child(itemName);

        userProductsRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("FirebaseUtils", "Item removed successfully from user's list");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseUtils", "Error removing item from user's list", e);
                    }
                });
    }
}


