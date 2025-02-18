package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import MCMClass.Manga;

public class MangaDatabase {
    DatabaseHandler databaseHandler;
    SQLiteDatabase db;

    public MangaDatabase(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }

    public void getMangaList(String id, ArrayList<Manga> list) {
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Manga where CollectionID = ?", new String[]{id});
        while (cursor.moveToNext()) {
            list.add(new Manga(cursor.getString(0), Integer.parseInt(cursor.getString(3)), cursor.getString(6), cursor.getString(5), cursor.getString(4), cursor.getString(2), cursor.getString(1), changeDate(cursor.getString(7))));
        }
        cursor.close();
    }

    public String createID() {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Manga", null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return "MG0";
        } else {
            cursor.moveToLast();
            String latestId = cursor.getString(1);
            cursor.close();
            latestId = latestId.substring(2);
            int number = Integer.parseInt(latestId) + 1;
            return "MG" + number;
        }
    }

    public boolean addManga(Manga manga) {
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CollectionID", manga.getIdCollection());
        contentValues.put("ChapterID", manga.getIdChapter());
        contentValues.put("ChapterNumber", manga.getChapter());
        contentValues.put("MangaType", manga.getType());
        contentValues.put("MangaStatus", manga.getStatus());
        contentValues.put("MangaNote", manga.getNote());
        contentValues.put("price", manga.getPrice());
        contentValues.put("ReleaseDate", changeDateISO(manga.getDate()));
        if (db.insert("Manga", null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public boolean updateManga(Manga manga) {
        db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CollectionID", manga.getIdCollection());
        contentValues.put("ChapterID", manga.getIdChapter());
        contentValues.put("ChapterNumber", manga.getChapter());
        contentValues.put("MangaType", manga.getType());
        contentValues.put("MangaStatus", manga.getStatus());
        contentValues.put("MangaNote", manga.getNote());
        contentValues.put("price", manga.getPrice());
        contentValues.put("ReleaseDate", changeDateISO(manga.getDate()));
        int rows = db.update("Manga", contentValues, "ChapterID = ?", new String[]{manga.getIdChapter()});
        if (rows == -1 || rows == 0) {
            return false;
        }
        return true;
    }

    public boolean deleteManga(String chapterID) {
        db = databaseHandler.getWritableDatabase();
        int rows = db.delete("Manga", "ChapterID = ?", new String[]{chapterID});
        if (rows == -1 || rows == 0) {
            return false;
        }
        return true;
    }

    public Manga getManga(String chapterID) {
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Manga where ChapterID = ?", new String[]{chapterID});
        cursor.moveToFirst();
        return new Manga(cursor.getString(0), Integer.parseInt(cursor.getString(3)), cursor.getString(6), cursor.getString(5), cursor.getString(4), cursor.getString(2), cursor.getString(1), changeDate(cursor.getString(7)));
    }

    public void seachManga(String name,String date,  ArrayList<Manga> list) {
        list.clear();
        String formattedDate = changeDateISO(date);
        Cursor cursor;
        if (name.isEmpty()){
            cursor = db.rawQuery("select * from Manga where ReleaseDate = ?", new String[]{formattedDate});
        }
         else {
            cursor = db.rawQuery("select * from Manga where ChapterNumber like '%" +name +"%'  and ReleaseDate = ?", new String[]{formattedDate});
        }

        while (cursor.moveToNext()) {
            list.add(new Manga(cursor.getString(0), Integer.parseInt(cursor.getString(3)), cursor.getString(6), cursor.getString(5), cursor.getString(4), cursor.getString(2), cursor.getString(1), changeDate(cursor.getString(7))));
        }
        cursor.close();
    }

    public String changeDate(String date) {
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
