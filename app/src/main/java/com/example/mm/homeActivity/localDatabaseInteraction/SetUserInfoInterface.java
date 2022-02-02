package com.example.mm.homeActivity.localDatabaseInteraction;

import android.database.sqlite.SQLiteException;

import localDatabase.Tables.UserInformation;

public interface SetUserInfoInterface {
    void insertUserInformation(UserInformation... userInformation) throws SQLiteException;
    void deleteAllUserInformation();
}
