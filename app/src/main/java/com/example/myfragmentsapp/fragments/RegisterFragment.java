package com.example.myfragmentsapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfragmentsapp.R;
import com.example.myfragmentsapp.User;
import com.example.myfragmentsapp.UsersData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText usernameET;
    EditText passwordET;
    EditText phoneNumberET;
    Button regButton;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        usernameET = view.findViewById(R.id.regUsernameInput);
        passwordET = view.findViewById(R.id.regPasswordInput);
        phoneNumberET = view.findViewById(R.id.regPhoneInput);
        regButton = view.findViewById(R.id.regButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String phoneNumber = phoneNumberET.getText().toString().trim();
                int userExists = 1;

                if (username.equals("") || password.equals("") || phoneNumber.equals("")) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    userExists = 0;
                }

                for (User existingUser : UsersData.getInstance().getUserList()) {

                    if (existingUser.getUsername().equals(username) ||
                            existingUser.getPassword().equals(password) ||
                            existingUser.getPhoneNumber().equals(phoneNumber)) {
                        Toast.makeText(getContext(), "User details already exists", Toast.LENGTH_SHORT).show();
                        userExists = 0;
                        break;
                    }
                }

                if (userExists == 1) {
                    User user = new User(username, password, phoneNumber, new ArrayList<>());
                    UsersData.getInstance().addUser(user, getContext());
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_mainFragment);
                }
            }
        });

        return view;
    }
}