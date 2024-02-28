package com.example.myfragmentsapp;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class UsersData {
    private static UsersData instance;
    static ArrayList<User> usersList;

    private UsersData() {
        usersList = new ArrayList<>();
    }

    public static UsersData getInstance() {
        if (instance == null) {
            instance = new UsersData();
        }
        return instance;
    }
    public void addUser(User user , Context context) {
        usersList.add(user);
    }
    public ArrayList<User> getUserList() {
        return usersList;
    }
}


