package com.fepeprog.test.database;

import android.database.Cursor;

public class UserCursorWrapper extends android.database.CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String email = getString(getColumnIndex(DBSchema.Columns.EMAIL));
        String name = getString(getColumnIndex(DBSchema.Columns.NAME));
        String number = getString(getColumnIndex(DBSchema.Columns.PHONE_NUMBER));
        String password = getString(getColumnIndex(DBSchema.Columns.PASSWORD));
        int age = getInt(getColumnIndex(DBSchema.Columns.AGE));

        return new User(email, name, number, password, age);
    }
}