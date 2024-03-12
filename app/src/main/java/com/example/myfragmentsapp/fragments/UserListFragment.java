package com.example.myfragmentsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myfragmentsapp.FirebaseUtils;
import com.example.myfragmentsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    Button returnButton;
    Button logOutButton;
    ListView listView;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        returnButton = view.findViewById(R.id.returnButton);
        logOutButton = view.findViewById(R.id.logOutButton);
        listView = (ListView) view.findViewById(R.id.listView);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        FirebaseUtils.getUserProductsFromFirebase(uid, new FirebaseUtils.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<String> products) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, products);
                listView.setAdapter(adapter);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_userListFragment_to_mainFragment);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtils.signOut();
                Navigation.findNavController(view).navigate(R.id.action_userListFragment_to_loginFragment);
            }
        });
        return view;
    }
}