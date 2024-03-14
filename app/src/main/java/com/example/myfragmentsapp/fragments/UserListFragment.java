package com.example.myfragmentsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfragmentsapp.CustomeAdapter;
import com.example.myfragmentsapp.FirebaseUtils;
import com.example.myfragmentsapp.ItemAdapter;
import com.example.myfragmentsapp.ListItem;
import com.example.myfragmentsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    private TextView usernameTV;
    private Button returnButton;
    private Button logOutButton;
    private ItemAdapter itemAdapter;
    private RecyclerView itemsRV;
    private LinearLayoutManager linearLayoutManager;

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

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        returnButton = view.findViewById(R.id.returnButton);
        logOutButton = view.findViewById(R.id.logOutButton);
        usernameTV = view.findViewById(R.id.userListUsername);
        linearLayoutManager = new LinearLayoutManager(getContext());
        itemsRV = view.findViewById(R.id.userItemsRV);
        itemsRV.setLayoutManager(linearLayoutManager);
        itemsRV.setItemAnimator(new DefaultItemAnimator());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        Bundle bundle = getArguments();
        String username = bundle.getString("username");
        usernameTV.setText(username);

        FirebaseUtils.getUserItemsFromFirebase(uid, new FirebaseUtils.OnItemsDataLoadedListener() {
            @Override
            public void onItemsDataLoaded(List<ListItem> items) {
                itemAdapter = new ItemAdapter(items);
                itemsRV.setAdapter(itemAdapter);
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