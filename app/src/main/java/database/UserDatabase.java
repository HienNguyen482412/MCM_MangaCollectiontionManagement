package database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import MCMClass.User;

public class UserDatabase {
    DatabaseHandler databaseHandler;
    SQLiteDatabase db;
    public UserDatabase(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }
    //User && Account
    public boolean createAccount(User user){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", user.getName());
        contentValues.put("UserPassword", user.getPassword());
        contentValues.put("UserGmail", user.getGmail());
        if (db.insert("User", null, contentValues) == -1){
            return false;
        }
        return true;
    }
    public boolean editAccount(User user){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", user.getName());
        contentValues.put("UserPassword", user.getPassword());
        contentValues.put("UserGmail", user.getGmail());
        contentValues.put("UserAvatar", user.getAvatar());
        contentValues.put("UserBackground", user.getBackground());
        if (db.update("User",contentValues,null, null) == -1){
            return false;
        }
        return true;
    }
    public User getAccount(){
        User user = new User();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User", null);
        if (cursor.moveToFirst()){
            user.setName(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setGmail(cursor.getString(2));
            byte[] avatarBytes = cursor.getBlob(3);
            user.setAvatar(avatarBytes);
            byte[] backgroundBytes = cursor.getBlob(4);
            user.setBackground(backgroundBytes);
        }
        else {
            user.setName("");
            user.setPassword("");
        }

        cursor.close();
        return user;
    }
    public int checkExitsUser(){
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User", null);

        int totalRow =0;
        if (cursor!=null){
            totalRow = cursor.getCount();
        }
        cursor.close();
        return totalRow;
    }
    public int login(String username, String password){
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where Username = '" + username + "' and UserPassword = '" + password + "'", null);
        int totalRow = cursor.getCount();
        cursor.close();
        return totalRow;
    }
    public void deleteAccount(Activity activity){
        databaseHandler.close();
        activity.deleteDatabase("MangaCollectionDatabase.db");
        activity.finishAffinity();
    }

}
