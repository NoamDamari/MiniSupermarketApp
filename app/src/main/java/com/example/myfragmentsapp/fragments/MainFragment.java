package com.example.myfragmentsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfragmentsapp.CustomeAdapter;
import com.example.myfragmentsapp.FirebaseUtils;
import com.example.myfragmentsapp.Product;
import com.example.myfragmentsapp.ProductsData;
import com.example.myfragmentsapp.R;
import com.example.myfragmentsapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private TextView usernameTV;
    private String username;
    private Button continueButton;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mAuth;
    private CustomeAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        usernameTV = view.findViewById(R.id.mainUsername);
        continueButton = view.findViewById(R.id.continueButton);
        recyclerView = view.findViewById(R.id.productsRV);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        assert uid != null;
        usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username = dataSnapshot.child("username").getValue(String.class);
                    usernameTV.setText(username);
                } else {
                    Log.d("MainActivity", "User data not found in database");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Error fetching user data", databaseError.toException());
            }
        });

        products = new ArrayList<Product>();
        for (int i = 0; i < ProductsData.names.length; i++) {
            products.add(new Product(
                    ProductsData.names[i],
                    ProductsData.images[i]
            ));
        }

        adapter = new CustomeAdapter(products , getContext());
        recyclerView.setAdapter(adapter);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username" , username);
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_userListFragment , bundle);
            }
        });

        return view;
    }
}