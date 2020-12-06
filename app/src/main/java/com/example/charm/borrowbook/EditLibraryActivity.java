package com.example.charm.borrowbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EditLibraryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = EditLibraryActivity.class.getSimpleName();

    private static final int NO_ID = -99;
    private static final String NO_TITLE = "";
    private static final String NO_AUTHOR = "";
    private static final String NO_PUB_YEAR = "";
    private static final String NO_ISBN = "";
    private static final String NO_LANGUAGE = "";
    private static final String NO_GENRE = "";
    private static final String NO_SERIES = "";
    private static final String NO_BOOK_COVER = "";
    private static final String NO_BORROW_DATE = "";
    private static final String NO_BORROW_TIME = "";
    private static final String NO_RETURN_DATE = "";
    private static final String NO_RETURN_TIME = "";

    private static final int CHOOSE_IMAGE_REQUEST = 100;    // any number except 0
    private Uri imageFilePath;
    private Bitmap imageToStore;

    private EditText mEt_lbTitle, mEt_lbAuthor, mEt_lbPubYear, mEt_lbISBN, mEt_lbSeries;
    private TextView mTv_lbBorrow_date, mTv_lbBorrow_time, mTv_lbReturn_date, mTv_lbReturn_time;
    private ImageView mIv_book_cover;

    public static final String EXTRA_TITLE_REPLY = "com.example.charm.borrowbook.TITLE_REPLY";
    public static final String EXTRA_AUTHOR_REPLY = "com.example.charm.borrowbook.AUTHOR_REPLY";
    public static final String EXTRA_PUB_YEAR_REPLY = "com.example.charm.borrowbook.PUB_YEAR_REPLY";
    public static final String EXTRA_ISBN_REPLY = "com.example.charm.borrowbook.ISBN_REPLY";
    public static final String EXTRA_LANGUAGE_REPLY = "com.example.charm.borrowbook.LANGUAGE_REPLY";
    public static final String EXTRA_GENRE_REPLY = "com.example.charm.borrowbook.GENRE_REPLY";
    public static final String EXTRA_SERIES_REPLY = "com.example.charm.borrowbook.SERIES_REPLY";
    public static final String EXTRA_BOOK_COVER_REPLY = "com.example.charm.borrowbook.BOOK_COVER_REPLY";
    public static final String EXTRA_BORROW_DATE_REPLY = "com.example.charm.borrowbook.BORROW_DATE_REPLY";
    public static final String EXTRA_BORROW_TIME_REPLY = "com.example.charm.borrowbook.BORROW_TIME_REPLY";
    public static final String EXTRA_RETURN_DATE_REPLY = "com.example.charm.borrowbook.RETURN_DATE_REPLY";
    public static final String EXTRA_RETURN_TIME_REPLY = "com.example.charm.borrowbook.RETURN_TIME_REPLY";

    int mLibraryId = MainActivity.LIBRARY_ADD;

    Spinner mSp_lbLanguage, mSp_lbGenre;
    ArrayList<String> arrLbLanguage, arrLbGenre;

    Button mBtn_add_to_borrowed;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Library library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_library);

        try{
            mIv_book_cover = (ImageView) findViewById(R.id.iv_book_cover);

            mEt_lbTitle = (EditText) findViewById(R.id.et_lbTitle);
            mEt_lbAuthor = (EditText) findViewById(R.id.et_lbAuthor);
            mEt_lbPubYear = (EditText) findViewById(R.id.et_lbPubYear);
            mEt_lbISBN = (EditText) findViewById(R.id.et_lbISBN);
            mSp_lbLanguage = (Spinner) findViewById(R.id.sp_lbLanguage);
            mSp_lbGenre = (Spinner) findViewById(R.id.sp_lbGenre);
            mEt_lbSeries = (EditText) findViewById(R.id.et_lbSeries);
            mTv_lbBorrow_date = (TextView) findViewById(R.id.tv_lbBorrow_date);
            mTv_lbBorrow_time = (TextView) findViewById(R.id.tv_lbBorrow_time);
            mTv_lbReturn_date = (TextView) findViewById(R.id.tv_lbReturn_date);
            mTv_lbReturn_time = (TextView) findViewById(R.id.tv_lbReturn_time);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Bundle library_extras = getIntent().getExtras();

        if (library_extras != null) {
            int id = library_extras.getInt(LibraryListAdapter.EXTRA_ID, NO_ID);
            String title = library_extras.getString(LibraryListAdapter.EXTRA_TITLE, NO_TITLE);
            String author = library_extras.getString(LibraryListAdapter.EXTRA_AUTHOR, NO_AUTHOR);
            String pub_year = library_extras.getString(LibraryListAdapter.EXTRA_PUB_YEAR, NO_PUB_YEAR);
            String isbn = library_extras.getString(LibraryListAdapter.EXTRA_ISBN, NO_ISBN);
            String language = library_extras.getString(LibraryListAdapter.EXTRA_LANGUAGE, NO_LANGUAGE);
            String genre = library_extras.getString(LibraryListAdapter.EXTRA_GENRE, NO_GENRE);
            String series = library_extras.getString(LibraryListAdapter.EXTRA_SERIES, NO_SERIES);
            String book_cover = library_extras.getString(LibraryListAdapter.EXTRA_BOOK_COVER, NO_BOOK_COVER);
            String borrow_date = library_extras.getString(LibraryListAdapter.EXTRA_BORROW_DATE, NO_BORROW_DATE);
            String borrow_time = library_extras.getString(LibraryListAdapter.EXTRA_BORROW_TIME, NO_BORROW_TIME);
            String return_date = library_extras.getString(LibraryListAdapter.EXTRA_RETURN_DATE, NO_RETURN_DATE);
            String return_time = library_extras.getString(LibraryListAdapter.EXTRA_RETURN_TIME, NO_RETURN_TIME);

            if (id != NO_ID && title != NO_TITLE) {
                mLibraryId = id;

                mIv_book_cover.setImageResource(R.drawable.the_lightning_thief);

                mEt_lbTitle.setText(title);
                mEt_lbAuthor.setText(author);
                mEt_lbPubYear.setText(pub_year);
                mEt_lbISBN.setText(isbn);
                mEt_lbSeries.setText(series);
                mIv_book_cover.setImageBitmap(imageToStore);
                mTv_lbBorrow_date.setText(borrow_date);
                mTv_lbBorrow_time.setText(borrow_time);
                mTv_lbReturn_date.setText(return_date);
                mTv_lbReturn_time.setText(return_time);
            }
        }

        mSp_lbLanguage = (Spinner) findViewById(R.id.sp_lbLanguage);
        arrLbLanguage = new ArrayList<String>();
        arrLbLanguage.add("English");
        arrLbLanguage.add("Chinese");
        arrLbLanguage.add("Malay");
        arrLbLanguage.add("Tamil");

        ArrayAdapter<String> lbLanguageAdp = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arrLbLanguage);

        lbLanguageAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSp_lbLanguage.setAdapter(lbLanguageAdp);
        mSp_lbLanguage.setOnItemSelectedListener(this);

        mSp_lbGenre = (Spinner) findViewById(R.id.sp_lbGenre);
        arrLbGenre = new ArrayList<String>();
        arrLbGenre.add("Adventure");
        arrLbGenre.add("Children’s fiction");
        arrLbGenre.add("Fantasy");
        arrLbGenre.add("Greek Mythology");
        arrLbGenre.add("Post-apocalyptic");
        arrLbGenre.add("Romance");
        arrLbGenre.add("Science Fiction");
        arrLbGenre.add("Self Help");
        arrLbGenre.add("Young Adult");

        ArrayAdapter<String> lbGenreAdp = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arrLbGenre);

        lbGenreAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSp_lbGenre.setAdapter(lbGenreAdp);
        mSp_lbGenre.setOnItemSelectedListener(this);

        library = new Library();
//        reff = FirebaseDatabase.getInstance().getReference();
        //reference = FirebaseDatabase.getInstance().getReference().child("library");
        //reff = FirebaseDatabase.getInstance().getReference("library");

        /*mBtn_add_to_borrowed = (Button) findViewById(R.id.btn_add_to_borrowed);
        mBtn_add_to_borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://borrowbook-1c4d5-default-rtdb.firebaseio.com/";
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReferenceFromUrl(url);
                //reference = rootNode.getReference("library");

                reference.setValue("First data");

                /*String title = mEt_lbTitle.getText().toString();
                String author = mEt_lbAuthor.getText().toString();
                String pub_year = mEt_lbPubYear.getText().toString();
                String isbn = mEt_lbISBN.getText().toString();
                String language = mSp_lbLanguage.getSelectedItem().toString();
                String genre = mSp_lbGenre.getSelectedItem().toString();
                String series = mEt_lbSeries.getText().toString();
                Bitmap book_cover = imageToStore;     //checked
                String borrow_date = mTv_lbBorrow_date.getText().toString();
                String borrow_time = mTv_lbBorrow_time.getText().toString();
                String return_date = mTv_lbReturn_date.getText().toString();
                String return_time = mTv_lbReturn_time.getText().toString();

                library.setBook_title(title);
                library.setAuthor(author);
                library.setPub_year(pub_year);
                library.setIsbn(isbn);
                library.setLanguage(language);
                library.setGenre(genre);
                library.setSeries(series);
                //library.setBook_cover(book_cover);
                library.setBorrowDate(borrow_date);
                library.setBorrowTime(borrow_time);
                library.setReturnDate(return_date);
                library.setReturnTime(return_time);

                reference.push().setValue(library);
                Toast.makeText(EditLibraryActivity.this, "Library details inserted successfully", Toast.LENGTH_SHORT).show();*/
            //}
        //});

    }

    public void chooseBookCover(View view){
        try{
            Intent imageIntent = new Intent();
            imageIntent.setType("image/*");

            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(imageIntent, CHOOSE_IMAGE_REQUEST);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bitmap = null;

        try{
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==CHOOSE_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&
                    data.getData()!=null){
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                mIv_book_cover.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    public void setBorrowDate(int year, int month, int day){
        String borrowDate = day + "/" + month + "/" + year;
        mTv_lbBorrow_date.setText(borrowDate);
    }

    public void setReturnDate(int year, int month, int day){
        String returnDate = day + "/" + month + "/" + year;
        mTv_lbReturn_date.setText(returnDate);
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

        mTv_lbBorrow_time.setText(borrowTime);
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

        mTv_lbReturn_time.setText(returnTime);
    }

    public void iv_lbBorrow_date_clicked(View view) {
        LibraryBorrowDatePickerFragment df = new LibraryBorrowDatePickerFragment();
        df.show(getSupportFragmentManager(), "Pick a date");
    }

    public void iv_lbReturn_date_clicked(View view) {
        LibraryReturnDatePickerFragment dfReturn = new LibraryReturnDatePickerFragment();
        dfReturn.show(getSupportFragmentManager(), "Pick a date");
    }

    public void iv_lbBorrow_time_clicked(View view) {
        LibraryBorrowTimePickerFragment tf = new LibraryBorrowTimePickerFragment();
        tf.show(getSupportFragmentManager(), "Pick a Time");
    }

    public void iv_lbReturn_time_clicked(View view) {
        LibraryReturnTimePickerFragment tf = new LibraryReturnTimePickerFragment();
        tf.show(getSupportFragmentManager(), "Pick a Time");
    }

    public void btn_add_to_borrowed_clicked(View view) {
        String title = mEt_lbTitle.getText().toString();
        String author = mEt_lbAuthor.getText().toString();
        String pub_year = mEt_lbPubYear.getText().toString();
        String isbn = mEt_lbISBN.getText().toString();
        String language = mSp_lbLanguage.getSelectedItem().toString();
        String genre = mSp_lbGenre.getSelectedItem().toString();
        String series = mEt_lbSeries.getText().toString();
        Bitmap book_cover = imageToStore;
        String borrow_date = mTv_lbBorrow_date.getText().toString();
        String borrow_time = mTv_lbBorrow_time.getText().toString();
        String return_date = mTv_lbReturn_date.getText().toString();
        String return_time = mTv_lbReturn_time.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_TITLE_REPLY, title);
        replyIntent.putExtra(EXTRA_AUTHOR_REPLY, author);
        replyIntent.putExtra(EXTRA_PUB_YEAR_REPLY, pub_year);
        replyIntent.putExtra(EXTRA_ISBN_REPLY, isbn);
        replyIntent.putExtra(EXTRA_LANGUAGE_REPLY, language);
        replyIntent.putExtra(EXTRA_GENRE_REPLY, genre);
        replyIntent.putExtra(EXTRA_SERIES_REPLY, series);
        replyIntent.putExtra(EXTRA_BOOK_COVER_REPLY, book_cover);
        replyIntent.putExtra(EXTRA_BORROW_DATE_REPLY, borrow_date);
        replyIntent.putExtra(EXTRA_BORROW_TIME_REPLY, borrow_time);
        replyIntent.putExtra(EXTRA_RETURN_DATE_REPLY, return_date);
        replyIntent.putExtra(EXTRA_RETURN_TIME_REPLY, return_time);

        replyIntent.putExtra(LibraryListAdapter.EXTRA_ID, mLibraryId);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
