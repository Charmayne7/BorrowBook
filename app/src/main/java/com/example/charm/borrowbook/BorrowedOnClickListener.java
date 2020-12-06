package com.example.charm.borrowbook;

import android.view.View;

public class BorrowedOnClickListener implements View.OnClickListener {
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    int borrowed_id;
    String borrowed_title, book_author, pub_year, isbn, language, genre, series, borrow_date,
            borrow_time, return_date, return_time;

    public BorrowedOnClickListener(int borrowed_id, String borrowed_title, String book_author,
                                   String pub_year, String isbn, String language, String genre,
                                   String series, String borrow_date, String borrow_time,
                                   String return_date, String return_time) {
        this.borrowed_id = borrowed_id;
        this.borrowed_title = borrowed_title;
        this.book_author = book_author;
        this.pub_year = pub_year;
        this.isbn = isbn;
        this.language = language;
        this.genre = genre;
        this.series = series;
        this.borrow_date = borrow_date;
        this.borrow_time = borrow_time;
        this.return_date = return_date;
        this.return_time = return_time;
    }

    @Override
    public void onClick(View view) {

    }
}
