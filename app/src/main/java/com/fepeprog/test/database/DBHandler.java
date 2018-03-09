package com.fepeprog.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DBHandler {
    private static SQLiteDatabase sqLiteDatabase;
    private static Context mContext;

    public static void initDB(Context context) {
        mContext = context;
        sqLiteDatabase = new DBOpenHelper(context).getWritableDatabase();
    }

    public static ContentValues userToContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(DBSchema.Columns.EMAIL, user.getEmail());
        values.put(DBSchema.Columns.NAME, user.getName());
        values.put(DBSchema.Columns.AGE, user.getAge());
        values.put(DBSchema.Columns.PHONE_NUMBER, user.getNumber());
        values.put(DBSchema.Columns.PASSWORD, user.getPassword());
        return values;
    }

    public static UserCursorWrapper queryUsers(String whereClause, String[] whereArgs) {

        Cursor cursor = sqLiteDatabase.query(
                DBSchema.TABLE_NAME,
                null,//all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new UserCursorWrapper(cursor);
    }

    public static ArrayList<User> getUsersFromDB() {
        initDB(mContext);
        ArrayList<User> users = new ArrayList<>();
        UserCursorWrapper cursorWrapper = queryUsers(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                users.add(cursorWrapper.getUser());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return users;
    }

    public static void createUser(User user) {
        initDB(mContext);
        sqLiteDatabase.insert(DBSchema.TABLE_NAME, null, userToContentValues(user));
    }

    public static boolean userExistsEmail(String email) {
        ArrayList<User> users = getUsersFromDB();
        for (User u : users) {
            if (u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public static boolean userExistsPhone(String phone, String email) {
        if (phone == null || phone.isEmpty())
            return false;
        ArrayList<User> users = getUsersFromDB();
        for (User u : users) {
            if (u.getNumber().equals(phone) && !u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public static boolean signingIn(String email, String password) {
        ArrayList<User> users = getUsersFromDB();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public static void updateUser(User user) {
        sqLiteDatabase.update(DBSchema.TABLE_NAME, userToContentValues(user),
                DBSchema.Columns.EMAIL + " = ?",
                new String[]{user.getEmail()});
    }

    public static User getUser(String email) {
        ArrayList<User> users = getUsersFromDB();
        for (User user : users) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }
}
