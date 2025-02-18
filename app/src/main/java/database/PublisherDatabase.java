package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import MCMClass.Author;
import MCMClass.Publisher;

public class PublisherDatabase {
    DatabaseHandler databaseHandler;
    SQLiteDatabase db ;

    public PublisherDatabase(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }
    public String createID(){
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Publisher",null);
        if (cursor.getCount() == 0){
            cursor.close();
            return "NXB0";
        }
        else {
            cursor.moveToLast();
            String latestId = cursor.getString(0);
            cursor.close();
            latestId = latestId.substring(3);
            int number = Integer.parseInt(latestId) + 1;
            return "NXB" + number;
        }
    }
    public boolean addPublisher(Publisher publisher){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PublisherID", publisher.getId());
        contentValues.put("PublisherName", publisher.getName());
        contentValues.put("PublisherStatus", publisher.getStatus());
        contentValues.put("PublisherPhone", publisher.getPhoneNumber());
        contentValues.put("PublisherAddress", publisher.getAddress());
        contentValues.put("PublisherNote", publisher.getNote());
        if (db.insert("Publisher", null, contentValues) == -1){
            return false;
        }
        return  true;
    }

    public boolean updatePublisher(Publisher publisher){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PublisherID", publisher.getId());
        contentValues.put("PublisherName", publisher.getName());
        contentValues.put("PublisherStatus", publisher.getStatus());
        contentValues.put("PublisherPhone", publisher.getPhoneNumber());
        contentValues.put("PublisherAddress", publisher.getAddress());
        contentValues.put("PublisherNote", publisher.getNote());
        int rows = db.update("Publisher", contentValues, "PublisherID = ?", new String[]{publisher.getId()});
        if (rows == -1 || rows == 0){
            return false;
        }
        return  true;
    }
    public boolean deletePublisher(String id){
        db = databaseHandler.getWritableDatabase();
        int rows = db.delete("Publisher", "PublisherID = ?", new String[]{id});
        if ( rows== -1 || rows == 0){
            return false;
        }
        return true;
    }
    public void getPublisherList(ArrayList<Publisher> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Publisher", null);
        while (cursor.moveToNext()){
            list.add(new Publisher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        cursor.close();
    }
    public Publisher getPublisher(String id){
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Publisher where PublisherID = ?", new String[]{id});
        cursor.moveToFirst();
            return new Publisher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));

    }
    public void searchPublisher(String name, ArrayList<Publisher> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Publisher where PublisherName like '%" + name + "%'", null);
        while (cursor.moveToNext()){
            list.add(new Publisher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        cursor.close();
    }
    public void getPublisherSpinner(ArrayList<String> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Publisher", null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(0) + " - " + cursor.getString(1));
        }
        cursor.close();
    }
}
