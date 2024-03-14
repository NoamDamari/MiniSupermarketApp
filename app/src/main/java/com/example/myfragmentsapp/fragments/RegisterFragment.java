package com.example.myfragmentsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfragmentsapp.FirebaseUtils;
import com.example.myfragmentsapp.R;
import com.example.myfragmentsapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    EditText usernameET;
    EditText passwordET;
    EditText phoneNumberET;
    EditText emailET;
    Button regButton;
    private FirebaseAuth mAuth;
    FirebaseUtils firebaseUtils;

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
        emailET = view.findViewById(R.id.regEmailInput);
        regButton = view.findViewById(R.id.regButton);
        firebaseUtils = new FirebaseUtils();

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
        String email = emailET.getText().toString().trim();

        User currentUser = new User(username, email, phone);

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getContext(), "Reg OK", Toast.LENGTH_SHORT).show();
                            FirebaseUtils.saveUserOnDatabase(currentUser , mAuth.getUid());
                            Bundle bundle = userBundle(currentUser);
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_mainFragment, bundle);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Reg Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public Bundle userBundle(User user) {
        Bundle bundle = new Bundle();
        bundle.putString("username", user.getUsername());
        bundle.putString("email", user.getEmail());
        bundle.putString("phone", user.getPhoneNumber());

        return bundle;
    }

}