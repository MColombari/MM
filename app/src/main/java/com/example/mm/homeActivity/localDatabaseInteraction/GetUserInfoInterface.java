package com.example.mm.homeActivity.localDatabaseInteraction;

import android.database.sqlite.SQLiteException;

import java.util.List;

import localDatabase.Tables.UserInformation;

public interface GetUserInfoInterface {
    List<UserInformation> getAllUserInformation() throws SQLiteException;
    void deleteAllUserInformation();
}
