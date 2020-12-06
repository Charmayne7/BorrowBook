package com.example.charm.borrowbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class LibraryListOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Library.db";
    private static final int DB_VERSION = 2;

    private static final String LIBRARY_LIST_TABLE = "library";
    private static final String COL_ID = "_id";
    private static final String COL_BOOK_TITLE = "book_title";
    private static final String COL_AUTHOR = "book_author";
    private static final String COL_PUB_YEAR = "published_year";
    private static final String COL_ISBN = "isbn";
    private static final String COL_LANGUAGE = "language";
    private static final String COL_GENRE = "genre";
    private static final String COL_SERIES = "series";
    private static final String COL_BOOK_COVER = "book_cover";
    private static final String COL_BORROW_DATE = "borrow_date";
    private static final String COL_BORROW_TIME = "borrow_time";
    private static final String COL_RETURN_DATE = "return_date";
    private static final String COL_RETURN_TIME = "return_time";

    public SQLiteDatabase mReadableDB, mWritableDB;

    Context mContext;
    private ByteArrayOutputStream mByteArrayOutputStream;
    private byte[] imageInBytes;

    public LibraryListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + LIBRARY_LIST_TABLE +
                    " (" + COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_BOOK_TITLE + " TEXT, " +
                    COL_AUTHOR + " TEXT, " +
                    COL_PUB_YEAR + " INTEGER, " +
                    COL_ISBN + " TEXT, " +
                    COL_LANGUAGE + " TEXT, " +
                    COL_GENRE + " TEXT, " +
                    COL_SERIES + " TEXT, " +
                    COL_BOOK_COVER + " BLOB, " +
                    COL_BORROW_DATE + " TEXT, " +
                    COL_BORROW_TIME + " TEXT, " +
                    COL_RETURN_DATE + " TEXT, " +
                    COL_RETURN_TIME + " TEXT );";

            db.execSQL(sql);
            existingLibrary(db);
    }

    private void existingLibrary(SQLiteDatabase db) {
        String[] title = {"I'd Tell You I Love You, But Then I'd Have To Kill You", "The Maze Runner",
                "Anda & Kereta", "The Lightning Thief", "The Sea of Monsters", "The Eyes of Minds"
        };

        String[] author = {"Ally Carter", "James Smith Dashner", "Dato’ Ir. Haji Mohamad Bin Dalib",
                "Rick Riordan", "Rick Riordan", "James Smith Dashner"};

        String[] pub_year = {"2006", "2009", "2019", "2005", "2006", "2013"};

        String[] isbn = {"978-1-408-30951-3", "978-0-385-73794-4", "978-983-097-908-3", "0-7868-5629-7",
                "0-7868-5686-6", "978-0-385-74139-2"
        };

        String[] language = {"English", "English", "Bahasa Malaysia", "English", "English", "English"};

        String[] genre = {"Romance", "Young Adult", "Self Help", "Greek Mythology", "Greek Mythology",
                "Young Adult"};

        String[] series = {"Gallagher Girls", "The Maze Runner series", "", "Percy Jackson & the Olympians",
                "Percy Jackson & the Olympians", "The Mortality Doctrine"};

        String[] book_cover = {"id_tell_you", "the_maze_runner", "anda_kereta", "the_lightning_thief",
                "the_sea_of_monsters", "the_eye_of_minds"};       // check later

        String[] borrow_date = {"", "", "", "", "", ""};
        String[] borrow_time = {"", "", "", "", "", ""};
        String[] return_date = {"", "", "", "", "", ""};
        String[] return_time = {"", "", "", "", "", ""};

        ContentValues values = new ContentValues();

        for (int i = 0; i < title.length; i++) {
            values.put(COL_BOOK_TITLE, title[i]);
            values.put(COL_AUTHOR, author[i]);
            values.put(COL_PUB_YEAR, pub_year[i]);
            values.put(COL_ISBN, isbn[i]);
            values.put(COL_LANGUAGE, language[i]);
            values.put(COL_GENRE, genre[i]);
            values.put(COL_SERIES, series[i]);
            values.put(COL_BOOK_COVER, book_cover[i]);
            values.put(COL_BORROW_DATE, borrow_date[i]);
            values.put(COL_BORROW_TIME, borrow_time[i]);
            values.put(COL_RETURN_DATE, return_date[i]);
            values.put(COL_RETURN_TIME, return_time[i]);

            db.insert(LIBRARY_LIST_TABLE, null, values);
        }

    }

    /*public void storeBookCover(Library library){
        try {
            SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
            //mWritableDB = getWritableDatabase();
            Bitmap imageToStoreBitmap = library.getBook_cover();

            mByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, mByteArrayOutputStream);

            imageInBytes = mByteArrayOutputStream.toByteArray();
            ContentValues values = new ContentValues();

            values.put(COL_BOOK_COVER, imageInBytes);

            long checkIfQueryRuns = mSQLiteDatabase.insert(LIBRARY_LIST_TABLE, null, values);

            if(checkIfQueryRuns!=-1){
            //
        }
        catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

    public Library libraryQuery(int position) {
        String sql = "SELECT * FROM " + LIBRARY_LIST_TABLE + " ORDER BY " + COL_ID + " ASC "
                + "LIMIT " + position + " ,1";

        Cursor cursor = null;
        Library entry = new Library();

        try {
            if (mReadableDB == null)
                mReadableDB = getReadableDatabase();

            cursor = mReadableDB.rawQuery(sql, null);
            cursor.moveToFirst();

            byte [] imageBytes = cursor.getBlob(cursor.getColumnIndex(COL_BOOK_COVER));
            Bitmap mBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            entry.setBook_id(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            entry.setBook_title(cursor.getString(cursor.getColumnIndex(COL_BOOK_TITLE)));
            entry.setAuthor(cursor.getString(cursor.getColumnIndex(COL_AUTHOR)));
            entry.setPub_year(cursor.getString(cursor.getColumnIndex(COL_PUB_YEAR)));
            entry.setIsbn(cursor.getString(cursor.getColumnIndex(COL_ISBN)));
            entry.setLanguage(cursor.getString(cursor.getColumnIndex(COL_LANGUAGE)));
            entry.setGenre(cursor.getString(cursor.getColumnIndex(COL_GENRE)));
            entry.setSeries(cursor.getString(cursor.getColumnIndex(COL_SERIES)));
            entry.setBook_cover(mBitmap);
            entry.setBorrowDate(cursor.getString(cursor.getColumnIndex(COL_BORROW_DATE)));
            entry.setBorrowTime(cursor.getString(cursor.getColumnIndex(COL_BORROW_TIME)));
            entry.setReturnDate(cursor.getString(cursor.getColumnIndex(COL_RETURN_DATE)));
            entry.setReturnTime(cursor.getString(cursor.getColumnIndex(COL_RETURN_TIME)));

        } catch (Exception e) {
            Log.d("LibraryListOpenHelper", "Library Query :" + e.getMessage());
        }
        finally {
            cursor.close();
            return entry;
        }
    }

    public long insertLibrary(String book_title, String book_author, String published_year,
                              String isbn, String language, String genre, String series,
                              Bitmap book_cover, String borrow_date, String borrow_time,
                              String return_date, String return_time) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(COL_BOOK_TITLE, book_title);
        values.put(COL_AUTHOR, book_author);
        values.put(COL_PUB_YEAR, published_year);
        values.put(COL_ISBN, isbn);
        values.put(COL_LANGUAGE, language);
        values.put(COL_GENRE, genre);
        values.put(COL_SERIES, series);
        values.put(COL_BOOK_COVER, imageInBytes);
        values.put(COL_BORROW_DATE, borrow_date);
        values.put(COL_BORROW_TIME, borrow_time);
        values.put(COL_RETURN_DATE, return_date);
        values.put(COL_RETURN_TIME, return_time);

        try {
            if (mWritableDB == null)
                mWritableDB = getWritableDatabase();

            newId = mWritableDB.insert(LIBRARY_LIST_TABLE, null, values);

            //Bitmap imageToStoreBitmap = library.getBook_cover();

            mByteArrayOutputStream = new ByteArrayOutputStream();
            book_cover.compress(Bitmap.CompressFormat.JPEG, 100, mByteArrayOutputStream);

            imageInBytes = mByteArrayOutputStream.toByteArray();
        }
        catch (Exception e) {
            Log.d("LibraryListAdapter", "Insert Library:" + e.getMessage());
        }
        finally {
            return newId;
        }
    }

    public int updateLibrary(int book_id, String book_title, String book_author,
                              String published_year, String isbn, String language, String genre,
                              String series, Bitmap book_cover, String borrow_date, String borrow_time,
                              String return_date, String return_time) {
        int numOfRowUpdated = -1;

        ContentValues values = new ContentValues();
        values.put(COL_BOOK_TITLE, book_title);
        values.put(COL_AUTHOR, book_author);
        values.put(COL_PUB_YEAR, published_year);
        values.put(COL_ISBN, isbn);
        values.put(COL_LANGUAGE, language);
        values.put(COL_GENRE, genre);
        values.put(COL_SERIES, series);
        values.put(COL_BOOK_COVER, imageInBytes);
        values.put(COL_BORROW_DATE, borrow_date);
        values.put(COL_BORROW_TIME, borrow_time);
        values.put(COL_RETURN_DATE, return_date);
        values.put(COL_RETURN_TIME, return_time);

        try {
            if (mWritableDB == null)
                mWritableDB = getWritableDatabase();

            numOfRowUpdated = mWritableDB.update(LIBRARY_LIST_TABLE, values,
                    COL_ID + "=?", new String[]{String.valueOf(book_id)});
        }

        catch (Exception e) {
            Log.d("LibraryListOpenHelper", "Update Library:" + e.getMessage());
        }

        return numOfRowUpdated;
    }

    public int deleteLibrary(int book_id) {
        int deleted = 0;

        try {
            if (mWritableDB == null)
                mWritableDB = getWritableDatabase();

            deleted = mWritableDB.delete(LIBRARY_LIST_TABLE, COL_ID + "=?",
                    new String[]{String.valueOf(book_id)});
        } catch (Exception e) {
            Log.d("LibraryListOpenHelper", "Delete Library:" + e.getMessage());
        }

        return deleted;
    }

    public long countLibrary() {
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }

        return DatabaseUtils.queryNumEntries(mReadableDB, LIBRARY_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + LIBRARY_LIST_TABLE);
        onCreate(db);
    }

    public Cursor search(String newText){
        Cursor result = null;

        try {
            if (mReadableDB == null)
                mReadableDB = getReadableDatabase();

            String[] columns = new String[] {"library"};
            String where = "book_title like ?";
            String[] wherearg = new String[] {"%" + newText + "%"};
            result = mReadableDB.query(LIBRARY_LIST_TABLE, columns, where, wherearg, null, null, null);
        }
        catch (Exception e){
            Log.d("LibraryListOpenHelper", "Search:" + e.getMessage());
        }

        return result;
    }
}
