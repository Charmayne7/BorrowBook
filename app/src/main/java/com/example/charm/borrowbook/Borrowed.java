package com.example.charm.borrowbook;

public class Borrowed {

    private int mBook_id;
    private String mBook_title;
    private String mAuthor;
    private String mPub_year;
    private String mIsbn;
    private String mLanguage;
    private String mGenre;
    private String mSeries;
    private String mBorrowDate;
    private String mBorrowTime;
    private String mReturnDate;
    private String mReturnTime;

    public Borrowed(){}

    public int getBook_id() {
        return this.mBook_id;
    }

    public void setBook_id(int mBook_id) {
        this.mBook_id = mBook_id;
    }

    public String getBook_title() {
        return this.mBook_title;
    }

    public void setBook_title(String mBook_title) {
        this.mBook_title = mBook_title;
    }

    public String getAuthor() {
        return this.mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getPub_year() {
        return this.mPub_year;
    }

    public void setPub_year(String mPub_year) {
        this.mPub_year = mPub_year;
    }

    public String getIsbn() {
        return this.mIsbn;
    }

    public void setIsbn(String mIsbn) {
        this.mIsbn = mIsbn;
    }

    public String getLanguage() {
        return this.mLanguage;
    }

    public void setLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getGenre() {
        return this.mGenre;
    }

    public void setGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getSeries() {
        return this.mSeries;
    }

    public void setSeries(String mSeries) {
        this.mSeries = mSeries;
    }

    public String getBorrowDate() {
        return this.mBorrowDate;
    }

    public void setBorrowDate(String mBorrowDate) {
        this.mBorrowDate = mBorrowDate;
    }

    public String getBorrowTime() {
        return this.mBorrowTime;
    }

    public void setBorrowTime(String mBorrowTime) {
        this.mBorrowTime = mBorrowTime;
    }

    public String getReturnDate() {
        return this.mReturnDate;
    }

    public void setReturnDate(String mReturnDate) {
        this.mReturnDate = mReturnDate;
    }

    public String getReturnTime() {
        return this.mReturnTime;
    }

    public void setReturnTime(String mReturnTime) {
        this.mReturnTime = mReturnTime;
    }
}
