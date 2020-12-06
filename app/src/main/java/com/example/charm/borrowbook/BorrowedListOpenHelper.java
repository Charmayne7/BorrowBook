package com.example.charm.borrowbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BorrowedListOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BorrowBook.db";
    private static final int DB_VERSION = 2;

    private static final String BORROWED_LIST_TABLE = "borrowed_entries";
    private static final String COL_ID = "borrowed_id";
    private static final String COL_BORROWED_TITLE = "borrowed_title";
    private static final String COL_AUTHOR = "book_author";
    private static final String COL_PUB_YEAR = "published_year";
    private static final String COL_ISBN = "isbn";
    private static final String COL_LANGUAGE = "language";
    private static final String COL_GENRE = "genre";
    private static final String COL_SERIES = "series";
    private static final String COL_BORROW_DATE = "borrow_date";
    private static final String COL_BORROW_TIME = "borrow_time";
    private static final String COL_RETURN_DATE = "return_date";
    private static final String COL_RETURN_TIME = "return_time";

    public SQLiteDatabase mReadableDB, mWritableDB;

    public BorrowedListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + BORROWED_LIST_TABLE +
                " (" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_BORROWED_TITLE + " TEXT, " +
                COL_AUTHOR + " TEXT, " +
                COL_PUB_YEAR + " INTEGER, " +
                COL_ISBN + " TEXT, " +
                COL_LANGUAGE + " TEXT, " +
                COL_GENRE + " TEXT, " +
                COL_SERIES + " TEXT, " +
                COL_BORROW_DATE + " TEXT, " +
                COL_BORROW_TIME + " TEXT, " +
                COL_RETURN_DATE + " TEXT, " +
                COL_RETURN_TIME + " TEXT );";

        db.execSQL(sql);
    }

    public Borrowed borrowedQuery(int position){
        String sql = "SELECT * FROM " + BORROWED_LIST_TABLE + " ORDER BY " + COL_BORROWED_TITLE + " ASC "
                + "LIMIT " + position + " ,1";

        Cursor cursor = null;
        Borrowed entry = new Borrowed();

        try {
            if (mReadableDB == null)
                mReadableDB = getReadableDatabase();

            cursor = mReadableDB.rawQuery(sql, null);
            cursor.moveToFirst();

            entry.setBook_id(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            entry.setBook_title(cursor.getString(cursor.getColumnIndex(COL_BORROWED_TITLE)));

            entry.setAuthor(cursor.getString(cursor.getColumnIndex(COL_AUTHOR)));
            entry.setPub_year(cursor.getString(cursor.getColumnIndex(COL_PUB_YEAR)));
            entry.setIsbn(cursor.getString(cursor.getColumnIndex(COL_ISBN)));
            entry.setLanguage(cursor.getString(cursor.getColumnIndex(COL_LANGUAGE)));
            entry.setGenre(cursor.getString(cursor.getColumnIndex(COL_GENRE)));
            entry.setSeries(cursor.getString(cursor.getColumnIndex(COL_SERIES)));
            entry.setBorrowDate(cursor.getString(cursor.getColumnIndex(COL_BORROW_DATE)));
            entry.setBorrowTime(cursor.getString(cursor.getColumnIndex(COL_BORROW_TIME)));
            entry.setReturnDate(cursor.getString(cursor.getColumnIndex(COL_RETURN_DATE)));
            entry.setReturnTime(cursor.getString(cursor.getColumnIndex(COL_RETURN_TIME)));
        }
        catch (Exception e){
            Log.d ("BorrowedListOpenHelper", "Borrowed Query :" + e.getMessage());
        }
        finally {
            cursor.close();
            return entry;
        }
    }

    public long insertBorrowed(String borrowed_title, String book_author, String published_year,
                               String isbn, String language, String genre, String series,
                               String borrow_date, String borrow_time, String return_date,
                               String return_time){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(COL_BORROWED_TITLE, borrowed_title);
        values.put(COL_AUTHOR, book_author);
        values.put(COL_PUB_YEAR, published_year);
        values.put(COL_ISBN, isbn);
        values.put(COL_LANGUAGE, language);
        values.put(COL_GENRE, genre);
        values.put(COL_SERIES, series);
        values.put(COL_BORROW_DATE, borrow_date);
        values.put(COL_BORROW_TIME, borrow_time);
        values.put(COL_RETURN_DATE, return_date);
        values.put(COL_RETURN_TIME, return_time);

        try {
            if (mWritableDB == null)
                mWritableDB = getWritableDatabase();

            newId = mWritableDB.insert(BORROWED_LIST_TABLE, null, values);
        }

        catch(Exception e){
            Log.d("BorrowedListAdapter", "Insert Borrowed:" + e.getMessage());
        }

        finally {
            return newId;
        }
    }

    public int updateBorrowed(int borrowed_id, String borrowed_title, String book_author,
                              String published_year, String isbn, String language, String genre,
                              String series, String borrow_date, String borrow_time,
                              String return_date,String return_time){
        int numOfRowUpdated = -1;

        ContentValues values = new ContentValues();
        values.put(COL_BORROWED_TITLE, borrowed_title);
        values.put(COL_AUTHOR, book_author);
        values.put(COL_PUB_YEAR, published_year);
        values.put(COL_ISBN, isbn);
        values.put(COL_LANGUAGE, language);
        values.put(COL_GENRE, genre);
        values.put(COL_SERIES, series);
        values.put(COL_BORROW_DATE, borrow_date);
        values.put(COL_BORROW_TIME, borrow_time);
        values.put(COL_RETURN_DATE, return_date);
        values.put(COL_RETURN_TIME, return_time);

        try {
            if (mWritableDB == null)
                mWritableDB = getWritableDatabase();

            numOfRowUpdated = mWritableDB.update(BORROWED_LIST_TABLE, values,
                    COL_ID + "=?", new String[] {String.valueOf(borrowed_id)}); //"=?" is for parameters
        }

        catch(Exception e){
            Log.d("BorrowedListOpenHelper", "Update Borrowed:" + e.getMessage());
        }

        return numOfRowUpdated;
    }

    public int deleteBorrowed (int borrowed_id){
        int deleted = 0;

        try {
            if (mWritableDB == null)
                mWritableDB = getWritableDatabase();

            deleted = mWritableDB.delete(BORROWED_LIST_TABLE, COL_ID + "=?",
                    new String[] {String.valueOf(borrowed_id)});
        }

        catch(Exception e){
            Log.d("BorrowedListOpenHelper", "Delete Borrowed:" + e.getMessage());
        }

        return deleted;
    }

    public long count(){
        if (mReadableDB == null){
            mReadableDB = getReadableDatabase();
        }

        return DatabaseUtils.queryNumEntries(mReadableDB, BORROWED_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        /* Log.w(BorrowedListOpenHelper.class.getName(),
                "Upgrading database from version " + oldVer + " to "
                    + newVer + " where all old data will be destroyed.");*/

        db.execSQL("DROP TABLE IF EXISTS " + BORROWED_LIST_TABLE);
        onCreate(db);
    }
}
