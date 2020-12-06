package com.example.charm.borrowbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AddBorrowedActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String TAG = AddBorrowedActivity.class.getSimpleName();

    private static final int NO_BORROWED_ID = -99;
    private static final String NO_BORROWED_TITLE = "";
    private static final String NO_BORROWED_AUTHOR = "";
    private static final String NO_BORROWED_PUB_YEAR = "";
    private static final String NO_BORROWED_ISBN = "";
    private static final String NO_BORROWED_LANGUAGE = "";
    private static final String NO_BORROWED_GENRE = "";
    private static final String NO_BORROWED_SERIES = "";
    private static final String NO_BORROWED_DATE = "";
    private static final String NO_BORROWED_TIME = "";
    private static final String NO_BORROWED_RETURN_DATE = "";
    private static final String NO_BORROWED_RETURN_TIME = "";

    private EditText mEt_brTitle, mEt_brAuthor, mEt_brPubYear, mEt_brISBN, mEt_brSeries;
    private TextView mTv_brBorrow_date, mTv_brBorrow_time, mTv_brReturn_date, mTv_brReturn_time;

    public static final String EXTRA_BORROWED_TITLE_REPLY = "com.example.charm.borrowbook.BORROWED_TITLE_REPLY";
    public static final String EXTRA_BORROWED_AUTHOR_REPLY = "com.example.charm.borrowbook.BORROWED_AUTHOR_REPLY";
    public static final String EXTRA_BORROWED_PUB_YEAR_REPLY = "com.example.charm.borrowbook.BORROWED_PUB_YEAR_REPLY";
    public static final String EXTRA_BORROWED_ISBN_REPLY = "com.example.charm.borrowbook.BORROWED_ISBN_REPLY";
    public static final String EXTRA_BORROWED_LANGUAGE_REPLY = "com.example.charm.borrowbook.BORROWED_LANGUAGE_REPLY";
    public static final String EXTRA_BORROWED_GENRE_REPLY = "com.example.charm.borrowbook.BORROWED_GENRE_REPLY";
    public static final String EXTRA_BORROWED_SERIES_REPLY = "com.example.charm.borrowbook.BORROWED_SERIES_REPLY";
    public static final String EXTRA_BOOK_COVER_REPLY = "com.example.charm.borrowbook.BOOK_COVER_REPLY";
    public static final String EXTRA_BORROWED_DATE_REPLY = "com.example.charm.borrowbook.BORROWED_DATE_REPLY";
    public static final String EXTRA_BORROWED_TIME_REPLY = "com.example.charm.borrowbook.BORROWED_TIME_REPLY";
    public static final String EXTRA_BORROWED_RETURN_DATE_REPLY = "com.example.charm.borrowbook.BORROWED_RETURN_DATE_REPLY";
    public static final String EXTRA_BORROWED_RETURN_TIME_REPLY = "com.example.charm.borrowbook.BORROWED_RETURN_TIME_REPLY";

    int mBorrowedId = MainActivity.BORROWED_ADD;

    Spinner mSp_brLanguage, mSp_brGenre;
    ArrayList<String> arrBrLanguage, arrBrGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrowed);

        mEt_brTitle = (EditText) findViewById(R.id.et_brTitle);
        mEt_brAuthor = (EditText) findViewById(R.id.et_brAuthor);
        mEt_brPubYear = (EditText) findViewById(R.id.et_brPubYear);
        mEt_brISBN = (EditText) findViewById(R.id.et_brISBN);
        mSp_brLanguage = (Spinner) findViewById(R.id.sp_brLanguage);
        mSp_brGenre = (Spinner) findViewById(R.id.sp_brGenre);
        mEt_brSeries = (EditText) findViewById(R.id.et_brSeries);
        mTv_brBorrow_date = (TextView) findViewById(R.id.tv_brBorrow_date);
        mTv_brBorrow_time = (TextView) findViewById(R.id.tv_brBorrow_time);
        mTv_brReturn_date = (TextView) findViewById(R.id.tv_brReturn_date);
        mTv_brReturn_time = (TextView) findViewById(R.id.tv_brReturn_time);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int id = extras.getInt(BorrowedListAdapter.EXTRA_BORROWED_ID, NO_BORROWED_ID);
            String title = extras.getString(BorrowedListAdapter.EXTRA_BORROWED, NO_BORROWED_TITLE);
            String author = extras.getString(BorrowedListAdapter.EXTRA_AUTHOR, NO_BORROWED_AUTHOR);
            String pub_year = extras.getString(BorrowedListAdapter.EXTRA_PUB_YEAR, NO_BORROWED_PUB_YEAR);
            String isbn = extras.getString(BorrowedListAdapter.EXTRA_ISBN, NO_BORROWED_ISBN);
            String language = extras.getString(BorrowedListAdapter.EXTRA_LANGUAGE, NO_BORROWED_LANGUAGE);
            String genre = extras.getString(BorrowedListAdapter.EXTRA_GENRE, NO_BORROWED_GENRE);
            String series = extras.getString(BorrowedListAdapter.EXTRA_SERIES, NO_BORROWED_SERIES);
            String borrow_date = extras.getString(BorrowedListAdapter.EXTRA_BORROW_DATE, NO_BORROWED_DATE);
            String borrow_time = extras.getString(BorrowedListAdapter.EXTRA_BORROW_TIME, NO_BORROWED_TIME);
            String return_date = extras.getString(BorrowedListAdapter.EXTRA_RETURN_DATE, NO_BORROWED_RETURN_DATE);
            String return_time = extras.getString(BorrowedListAdapter.EXTRA_RETURN_TIME, NO_BORROWED_RETURN_TIME);

            if (id != NO_BORROWED_ID && title != NO_BORROWED_TITLE) {
                mBorrowedId = id;
                mEt_brTitle.setText(title);
                mEt_brAuthor.setText(author);
                mEt_brPubYear.setText(pub_year);
                mEt_brISBN.setText(isbn);
                mEt_brSeries.setText(series);
                mTv_brBorrow_date.setText(borrow_date);
                mTv_brBorrow_time.setText(borrow_time);
                mTv_brReturn_date.setText(return_date);
                mTv_brReturn_time.setText(return_time);
            }
        }

        mSp_brLanguage = (Spinner) findViewById(R.id.sp_brLanguage);
        arrBrLanguage = new ArrayList<String>();
        arrBrLanguage.add("English");
        arrBrLanguage.add("Chinese");
        arrBrLanguage.add("Malay");
        arrBrLanguage.add("Tamil");

        ArrayAdapter<String> brLanguageAdp = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arrBrLanguage);

        brLanguageAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSp_brLanguage.setAdapter(brLanguageAdp);
        mSp_brLanguage.setOnItemSelectedListener(this);

        mSp_brGenre = (Spinner) findViewById(R.id.sp_brGenre);
        arrBrGenre = new ArrayList<String>();
        arrBrGenre.add("Adventure");
        arrBrGenre.add("Children’s fiction");
        arrBrGenre.add("Fantasy");
        arrBrGenre.add("Greek Mythology");
        arrBrGenre.add("Post-apocalyptic");
        arrBrGenre.add("Romance");
        arrBrGenre.add("Science Fiction");
        arrBrGenre.add("Self Help");
        arrBrGenre.add("Young Adult");

        ArrayAdapter<String> brGenreAdp = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arrBrGenre);

        brGenreAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSp_brGenre.setAdapter(brGenreAdp);
        mSp_brGenre.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_brLanguage){
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        }

        else if (parent.getId() == R.id.sp_brGenre){
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setBorrowDate(int year, int month, int day){
        String borrowDate = day + "/" + month + "/" + year;
        mTv_brBorrow_date.setText(borrowDate);
    }

    public void setReturnDate(int year, int month, int day){
        String returnDate = day + "/" + month + "/" + year;
        mTv_brReturn_date.setText(returnDate);
    }

    public void setBorrowTime(int hours, int minutes){
        String borrowTime;

        if (hours >= 12){
            if (minutes < 10){
                borrowTime = hours + ":0" + minutes + " PM";
            }
            else {
                borrowTime = hours + ":" + minutes + " PM";
            }
        }
        else {
            if (minutes < 10){
                borrowTime = hours + ":0" + minutes + " AM";
            }
            else {
                borrowTime = hours + ":" + minutes + " AM";
            }
        }

        mTv_brBorrow_time.setText(borrowTime);
    }

    public void setReturnTime(int hours, int minutes){
        String returnTime;

        if (hours >= 12){
            if (minutes < 10){
                returnTime = hours + ":0" + minutes + " PM";
            }
            else {
                returnTime = hours + ":" + minutes + " PM";
            }
        }
        else {
            if (minutes < 10){
                returnTime = hours + ":0" + minutes + " AM";
            }
            else {
                returnTime = hours + ":" + minutes + " AM";
            }
        }

        mTv_brReturn_time.setText(returnTime);
    }

    public void iv_brBorrow_date_clicked(View view) {
        BorrowDatePickerFragment df = new BorrowDatePickerFragment();
        df.show(getSupportFragmentManager(), "Pick a date");
    }

    public void iv_brReturn_date_clicked(View view) {
        ReturnDatePickerFragment dfReturn = new ReturnDatePickerFragment();
        dfReturn.show(getSupportFragmentManager(), "Pick a date");
    }

    public void iv_brBorrow_time_clicked(View view) {
        BorrowTimePickerFragment tf = new BorrowTimePickerFragment();
        tf.show(getSupportFragmentManager(), "Pick a Time");
    }

    public void iv_brReturn_time_clicked(View view) {
        ReturnTimePickerFragment tf = new ReturnTimePickerFragment();
        tf.show(getSupportFragmentManager(), "Pick a Time");
    }

    public void btn_add_clicked(View view) {
        String title = mEt_brTitle.getText().toString();
        String author = mEt_brAuthor.getText().toString();
        String pub_year = mEt_brPubYear.getText().toString();
        String isbn = mEt_brISBN.getText().toString();
        String language = mSp_brLanguage.getSelectedItem().toString();
        String genre = mSp_brGenre.getSelectedItem().toString();
        String series = mEt_brSeries.getText().toString();
        String borrow_date = mTv_brBorrow_date.getText().toString();
        String borrow_time = mTv_brBorrow_time.getText().toString();
        String return_date = mTv_brReturn_date.getText().toString();
        String return_time = mTv_brReturn_time.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_BORROWED_TITLE_REPLY, title);
        replyIntent.putExtra(EXTRA_BORROWED_AUTHOR_REPLY, author);
        replyIntent.putExtra(EXTRA_BORROWED_PUB_YEAR_REPLY, pub_year);
        replyIntent.putExtra(EXTRA_BORROWED_ISBN_REPLY, isbn);
        replyIntent.putExtra(EXTRA_BORROWED_LANGUAGE_REPLY, language);
        replyIntent.putExtra(EXTRA_BORROWED_GENRE_REPLY, genre);
        replyIntent.putExtra(EXTRA_BORROWED_SERIES_REPLY, series);
        replyIntent.putExtra(EXTRA_BORROWED_DATE_REPLY, borrow_date);
        replyIntent.putExtra(EXTRA_BORROWED_TIME_REPLY, borrow_time);
        replyIntent.putExtra(EXTRA_BORROWED_RETURN_DATE_REPLY, return_date);
        replyIntent.putExtra(EXTRA_BORROWED_RETURN_TIME_REPLY, return_time);

        replyIntent.putExtra(BorrowedListAdapter.EXTRA_BORROWED_ID, mBorrowedId);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
