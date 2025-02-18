package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;

import MCMClass.Manga;
import MCMClass.MangaCollection;

public class CollectionDatabase {
    SQLiteDatabase db;
    DatabaseHandler databaseHandler;

    public CollectionDatabase(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }
    public String createID(){
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MangaCollection",null);
        if (cursor.getCount() == 0){
            cursor.close();
            return "BT0";
        }
        else {
            cursor.moveToLast();
            String latestId = cursor.getString(1);
            cursor.close();
            latestId = latestId.substring(2);
            int number = Integer.parseInt(latestId) + 1;
            return "BT" + number;
        }
    }
    public boolean addCollection(MangaCollection mangaCollection){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CollectionID", mangaCollection.getId());
        contentValues.put("CollectionName", mangaCollection.getName());
        contentValues.put("AuthorID", mangaCollection.getAuthorId());
        contentValues.put("PublisherID",mangaCollection.getPublisherId());
        contentValues.put("CollectionStatus",mangaCollection.getStatus());
        contentValues.put("ReleaseDate",changeDateISO(mangaCollection.getDate()) );
        contentValues.put("CollectionNote", mangaCollection.getNote());
        contentValues.put("CollectionCover", mangaCollection.getImage());
        if (db.insert("MangaCollection", null, contentValues) == -1){
            return false;
        }
        return true;
    }
    public boolean updateCollection(MangaCollection mangaCollection){
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CollectionID", mangaCollection.getId());
        contentValues.put("CollectionName", mangaCollection.getName());
        contentValues.put("AuthorID", mangaCollection.getAuthorId());
        contentValues.put("PublisherID",mangaCollection.getPublisherId());
        contentValues.put("CollectionStatus",mangaCollection.getStatus());
        contentValues.put("ReleaseDate",changeDateISO(mangaCollection.getDate()) );
        contentValues.put("CollectionNote", mangaCollection.getNote());
        contentValues.put("CollectionCover", mangaCollection.getImage());
        int rows = db.update("MangaCollection", contentValues, "CollectionID = ?", new String[]{mangaCollection.getId()});
        if (rows == -1 || rows == 0){
            return false;
        }
        return true;
    }
    public  boolean deleteCollection(String id){
        db = databaseHandler.getWritableDatabase();
        int rows = db.delete("MangaCollection","CollectionID = ?", new String[]{id});
        if (rows == -1 || rows == 0){
            return false;
        }
        return true;
    }
    public void getCollectionList(ArrayList<MangaCollection> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MangaCollection", null);
        if (cursor != null && cursor.getCount()> 0){
            while (cursor.moveToNext()){
                byte[] bytes = cursor.getBlob(0);
                list.add(new MangaCollection(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3), cursor.getString(5),changeDate(cursor.getString(6)) , cursor.getString(7), bytes));
            }
        }

        cursor.close();
    }
    public MangaCollection getCollection(String id){
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MangaCollection where CollectionID = ?", new String[]{id});
        cursor.moveToFirst();
        byte[] bytes = cursor.getBlob(0);
        return new MangaCollection(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3), cursor.getString(5),changeDate(cursor.getString(6)) , cursor.getString(7), bytes);
    }
    public void searchCollection(String name, ArrayList<MangaCollection> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MangaCollection where CollectionName like '%" + name + "%'", null);
        while (cursor.moveToNext()){
            byte[] bytes = cursor.getBlob(0);
            list.add(new MangaCollection(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3), cursor.getString(5),changeDate(cursor.getString(6)) , cursor.getString(7), bytes));
        }
        cursor.close();
    }
    public int getTotalChapter(String id){
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Manga where CollectionID = ?", new String[]{id});
        int total = cursor.getCount();
        cursor.close();
        return total;
    }
    public String changeDate(String date){
        String[] arr = date.split("-");
        String year = arr[0];
        String month = arr[1];
        String day   = arr[2];
        return day + "/" + month + "/" + year;
    }
    public String changeDateISO(String date){
        String[] arr = date.split("/");
        String day = arr[0];
        String month = arr[1];
        String year = arr[2];
        if (day.length() <2){
            day = "0" + day;
        }
        if (month.length()<2){
            month = "0" + month;
        }
        return year + "-" + month + "-" + day;
    }
}
