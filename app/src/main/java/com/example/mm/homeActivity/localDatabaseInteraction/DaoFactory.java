package com.example.mm.homeActivity.localDatabaseInteraction;

import android.content.Context;

import localDatabase.LocalDatabaseDao;

public interface DaoFactory {
    public LocalDatabaseDao getDao(Context context);
}
