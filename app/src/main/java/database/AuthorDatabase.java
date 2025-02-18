package database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import MCMClass.Author;

public class AuthorDatabase {
    DatabaseHandler databaseHandler;
    SQLiteDatabase db;

    public AuthorDatabase(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }
    public String createID(){
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author",null);
        if (cursor.getCount() == 0){
            cursor.close();
            return "TG0";
        }
        else {
            cursor.moveToLast();
            String latestId = cursor.getString(0);
            cursor.close();
            latestId = latestId.substring(2);
            int number = Integer.parseInt(latestId) + 1;
            return "TG" + number;
        }
    }
    public boolean addAuthor(Author author){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AuthorID", author.getId());
        contentValues.put("AuthorName", author.getName());
        contentValues.put("AuthorCountry", author.getNationality());
        contentValues.put("AuthorHometown", author.getHomeTown());
        contentValues.put("AuthorYear", author.getYearOfBirth());
        contentValues.put("AuthorNote", author.getNote());
        if (db.insert("Author", null, contentValues)== -1){
            return false;
        }
        return true;
    }
    public boolean updateAuthor(Author author){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AuthorID", author.getId());
        contentValues.put("AuthorName", author.getName());
        contentValues.put("AuthorCountry", author.getNationality());
        contentValues.put("AuthorHometown", author.getHomeTown());
        contentValues.put("AuthorYear", author.getYearOfBirth());
        contentValues.put("AuthorNote", author.getNote());
        int rows = db.update("Author", contentValues, "AuthorID = ?", new String[]{author.getId()});
        if (rows == -1 || rows == 0){
            return false;
        }
        return true;
    }
    public boolean deleteAuthor(String id){
        db = databaseHandler.getWritableDatabase();
        int rows = db.delete("Author", "AuthorID = ?", new String[]{id});
        if (rows == -1 || rows == 0){
            return false;
        }
        return true;
    }

    public void getAuthorList(ArrayList<Author> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Author", null);
        while (cursor.moveToNext()){
            list.add(new Author(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(5), Integer.parseInt(cursor.getString(4))));
        }
        cursor.close();
    }

    public void searchAuthor(String name, ArrayList<Author> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Author where AuthorName like '%" + name + "%'", null);
        while (cursor.moveToNext()){
            list.add(new Author(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(5), Integer.parseInt(cursor.getString(4))));
        }
        cursor.close();
    }
    public Author getAuthor(String id){
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Author where AuthorID = ?" , new String[]{id});
        cursor.moveToFirst();

        return new Author(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(5), Integer.parseInt(cursor.getString(4).trim()));
    }
    public void getAuthorSpinner(ArrayList<String> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Author", null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(0) + " - " + cursor.getString(1));
        }
        cursor.close();
    }
}
