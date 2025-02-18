package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import MCMClass.User;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, "MangaCollectionDatabase.db", null, 8);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table User (Username text not null, UserPassword text not null, UserGmail text not null, UserAvatar blob , UserBackground blob )");
        sqLiteDatabase.execSQL("create table Author (AuthorID text primary key  not null, AuthorName text  not null, AuthorCountry text, AuthorHometown text, AuthorYear int ,AuthorNote text )");
        sqLiteDatabase.execSQL("create table Publisher (PublisherID text primary key  not null, PublisherName text  not null, PublisherStatus text, PublisherPhone text, PublisherAddress text, PublisherNote text )");
        sqLiteDatabase.execSQL("create table MangaCollection(CollectionCover blob,CollectionID text primary key  not null, CollectionName text  not null, AuthorID text  not null , PublisherID text  not null, CollectionStatus text, ReleaseDate text, CollectionNote text ,foreign key (AuthorID) references Author(AuthorID) on delete cascade,foreign key (PublisherID) references Publisher(PublisherID) on delete cascade)");
        sqLiteDatabase.execSQL("create table Manga(CollectionID text  not null, ChapterID text not null primary key, ChapterNumber text  not null, price INTEGER not null default 0,MangaType text, MangaStatus text, MangaNote text,ReleaseDate TEXT,foreign key (CollectionID) references MangaCollection(CollectionID) on delete cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i<2){
            sqLiteDatabase.execSQL("ALTER TABLE MangaCollection ADD COLUMN CollectionCover blob");
        }
        if (i < 3) {
            sqLiteDatabase.execSQL("ALTER TABLE Manga ADD COLUMN price INTEGER NOT NULL DEFAULT 0");
        }
        if (i < 8){
            sqLiteDatabase.execSQL("drop table if exists Manga");
            sqLiteDatabase.execSQL("create table Manga(CollectionID text  not null, ChapterID text not null primary key, ChapterNumber text  not null, price INTEGER not null default 0,MangaType text, MangaStatus text, MangaNote text,ReleaseDate TEXT,foreign key (CollectionID) references MangaCollection(CollectionID))");
        }

    }

}
