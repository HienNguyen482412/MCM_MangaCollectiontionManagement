package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class StatisticClass {
    DatabaseHandler databaseHandler;
    SQLiteDatabase db;

    public StatisticClass(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }

    //Money
    public void getQuantityPublisher(ArrayList<String> titles, ArrayList<Integer> quantity, Integer type, String date) {
        db = databaseHandler.getReadableDatabase();
        String sql;
        if (type == 0 && date.isEmpty()){
            sql = "select PublisherName, ifnull(count(ChapterID),0) from Publisher p  left join MangaCollection c on p.PublisherID = c.PublisherID left join Manga m on m.CollectionID = c.CollectionID and m.MangaStatus not in ('Chưa có','Đã đặt hàng')  group by PublisherName";
        }
        else if (type == 1){
            sql = "select PublisherName, ifnull(count(ChapterID),0) from Publisher p  left join MangaCollection c on p.PublisherID = c.PublisherID left join Manga m on m.CollectionID = c.CollectionID and strftime('%Y-%m-%d', m.ReleaseDate) = '"+ changeDateISO(date,1) +"' and m.MangaStatus not in ('Chưa có','Đã đặt hàng') group by PublisherName";
        }
        else if (type == 2){
            sql = "select PublisherName, ifnull(count(ChapterID),0) from Publisher p  left join MangaCollection c on p.PublisherID = c.PublisherID left join Manga m on m.CollectionID = c.CollectionID and strftime('%Y-%m',m.ReleaseDate) = '"+ changeDateISO(date,2) +"' and m.MangaStatus not in ('Chưa có','Đã đặt hàng') group by PublisherName";
        }
       else{
            sql = "select PublisherName, ifnull(count(ChapterID),0) from Publisher p  left join MangaCollection c on p.PublisherID = c.PublisherID left join Manga m on m.CollectionID = c.CollectionID and strftime('%Y',m.ReleaseDate) = '"+ changeDateISO(date,3) +"' and m.MangaStatus not in ('Chưa có','Đã đặt hàng') group by PublisherName";
        }
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            titles.add(cursor.getString(0));
            quantity.add(Integer.parseInt(cursor.getString(1)));

        }


    }
    public void getMangaStatus(ArrayList<String> titles, ArrayList<Integer> values){
        titles.clear();
        values.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select  MangaStatus, count(*) as MangaCount from Manga group by MangaStatus", null);
        while (cursor.moveToNext()){
            titles.add(cursor.getString(0));
            values.add(Integer.parseInt(cursor.getString(1)));
        }
    }
    public Integer[] getMoney(int type, String date){
        Integer[] result = {0,0};
        db = databaseHandler.getReadableDatabase();
        String sql1 = "select ifnull( sum(price),0) from Manga where MangaStatus = 'Chưa có' and ";
        String sql2 = "select ifnull( sum(price),0) from Manga where MangaStatus not in ('Chưa có') and ";
        if (type == 0){
            sql1 += "strftime('%Y-%m-%d', ReleaseDate) = '"+ changeDateISO(date,1) +"'";
            sql2 += "strftime('%Y-%m-%d', ReleaseDate) = '"+ changeDateISO(date,1) +"'";
        }
        else if (type ==1){
            sql1 += "strftime('%Y-%m',ReleaseDate) = '"+ changeDateISO(date,2) +"'";
            sql2 += "strftime('%Y-%m',ReleaseDate) = '"+ changeDateISO(date,2) +"'";
        }
        else {
            sql1 += "strftime('%Y',ReleaseDate) = '"+ changeDateISO(date,3) +"'";
            sql2 += "strftime('%Y',ReleaseDate) = '"+ changeDateISO(date,3) +"'";
        }
        Cursor cursor = db.rawQuery(sql1, null);
        if (cursor.moveToNext()){
            result[0] = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        Cursor cursor1 = db.rawQuery(sql2, null);
        if (cursor1.moveToNext()){
            result[1] = Integer.parseInt(cursor1.getString(0));
        }
        cursor1.close();
        return result;
    }
    public void showDESCMangaList(ArrayList<String> list){
        list.clear();
        db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select CollectionName, ChapterID,ChapterNumber, price from MangaCollection c inner join Manga m on c.CollectionID = m.CollectionID order by price DESC", null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(0) + "-" + cursor.getString(1) + "-" + cursor.getString(2) + "-" + cursor.getString(3));
            Log.d("check",cursor.getString(0) + "-" + cursor.getString(1) + "-" + cursor.getString(2) + "-" + cursor.getString(3) );
        }
        cursor.close();
    }

    public String changeDateISO(String date, Integer type){
        String[] arr = date.split("/");
        String day = arr[0];
        String month = arr[1];
        String year = arr[2];
        if (type == 1){
            if (day.length() <2){
                day = "0" + day;
            }
            if (month.length()<2){
                month = "0" + month;
            }
            return year + "-" + month + "-" + day;
        }
        else if (type == 2){
            if (month.length()<2){
                month = "0" + month;
            }
            return year + "-" + month;
        }
        return year;

    }
}
