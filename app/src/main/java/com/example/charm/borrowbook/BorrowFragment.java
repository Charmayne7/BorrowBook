package com.example.charm.borrowbook;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BorrowFragment extends Fragment {

    private RecyclerView mRv_borrow_list;
    private BorrowedListAdapter mAdapter;
    private BorrowedListOpenHelper mBorrowedDb;
    FloatingActionButton mAddBorrowed;

    public static final int BORROWED_EDIT = 1;
    public static final int BORROWED_ADD = -1;

    public BorrowFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);

        mBorrowedDb = new BorrowedListOpenHelper(getContext());
        SQLiteDatabase d = mBorrowedDb.getReadableDatabase();

        mRv_borrow_list = (RecyclerView) view.findViewById(R.id.rv_borrow_list);
        mAdapter = new BorrowedListAdapter(getContext(), mBorrowedDb);
        mRv_borrow_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv_borrow_list.setAdapter(mAdapter);

        mAddBorrowed = (FloatingActionButton) view.findViewById(R.id.fab_add_borrowed);

        mAddBorrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddBorrowedActivity.class);
                startActivityForResult(intent, BORROWED_EDIT);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BORROWED_EDIT){
            if (resultCode == Activity.RESULT_OK){
                String borrowed_title = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_TITLE_REPLY);
                String book_author = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_AUTHOR_REPLY);
                String pub_year = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_PUB_YEAR_REPLY);
                String isbn = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_ISBN_REPLY);
                String language = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_LANGUAGE_REPLY);
                String genre = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_GENRE_REPLY);
                String series = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_SERIES_REPLY);
                String borrow_date = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_DATE_REPLY);
                String borrow_time = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_TIME_REPLY);
                String return_date = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_RETURN_DATE_REPLY);
                String return_time = data.getStringExtra(AddBorrowedActivity.EXTRA_BORROWED_RETURN_TIME_REPLY);


                if (!TextUtils.isEmpty(borrowed_title)){
                    int id = data.getIntExtra(BorrowedListAdapter.EXTRA_BORROWED_ID, -99);

                    if (id == BORROWED_ADD){
                        mBorrowedDb.insertBorrowed(borrowed_title, book_author, pub_year, isbn, language,
                                genre, series, borrow_date, borrow_time, return_date, return_time);
                    }
                    else if (id > 0){
                        mBorrowedDb.updateBorrowed(id, borrowed_title, book_author, pub_year, isbn, language,
                                genre, series, borrow_date, borrow_time, return_date, return_time);
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
