package com.example.myfragmentsapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfragmentsapp.R;
import com.example.myfragmentsapp.User;
import com.example.myfragmentsapp.UsersData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class RegisterFragment extends Fragment {

    EditText usernameET;
    EditText passwordET;
    EditText phoneNumberET;
    Button regButton;
    private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();

        usernameET = view.findViewById(R.id.regUsernameInput);
        passwordET = view.findViewById(R.id.regPasswordInput);
        phoneNumberET = view.findViewById(R.id.regPhoneInput);
        regButton = view.findViewById(R.id.regButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regFunc(view);
            }
        });

        return view;
    }

    public void regFunc(View view) {

        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String phone = phoneNumberET.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Reg OK", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_mainFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Reg Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}