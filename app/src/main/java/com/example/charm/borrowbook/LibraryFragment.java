package com.example.charm.borrowbook;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    private RecyclerView mRv_library_list;
    private LibraryListAdapter mAdapter;
    private LibraryListOpenHelper mLibraryDb;
    FloatingActionButton mAddLibrary;

    public static final int LIBRARY_EDIT = 1;
    public static final int LIBRARY_ADD = -1;

    public LibraryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);

        mLibraryDb = new LibraryListOpenHelper(getContext());
        SQLiteDatabase d = mLibraryDb.getReadableDatabase();

        mRv_library_list = (RecyclerView) view.findViewById(R.id.rv_libraryList);
        mAdapter = new LibraryListAdapter(getContext(), mLibraryDb);
        mRv_library_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv_library_list.setAdapter(mAdapter);

        mAddLibrary = (FloatingActionButton) view.findViewById(R.id.fab_add_library);

        mAddLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditLibraryActivity.class);
                startActivityForResult(intent, LIBRARY_EDIT);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LIBRARY_EDIT){
            if (resultCode == Activity.RESULT_OK){
                String book_title = data.getStringExtra(EditLibraryActivity.EXTRA_TITLE_REPLY);
                String book_author = data.getStringExtra(EditLibraryActivity.EXTRA_AUTHOR_REPLY);
                String pub_year = data.getStringExtra(EditLibraryActivity.EXTRA_PUB_YEAR_REPLY);
                String isbn = data.getStringExtra(EditLibraryActivity.EXTRA_ISBN_REPLY);
                String language = data.getStringExtra(EditLibraryActivity.EXTRA_LANGUAGE_REPLY);
                String genre = data.getStringExtra(EditLibraryActivity.EXTRA_GENRE_REPLY);
                String series = data.getStringExtra(EditLibraryActivity.EXTRA_SERIES_REPLY);
                Bitmap book_cover = data.getParcelableExtra(EditLibraryActivity.EXTRA_BOOK_COVER_REPLY);
                String borrow_date = data.getStringExtra(EditLibraryActivity.EXTRA_BORROW_DATE_REPLY);
                String borrow_time = data.getStringExtra(EditLibraryActivity.EXTRA_BORROW_TIME_REPLY);
                String return_date = data.getStringExtra(EditLibraryActivity.EXTRA_RETURN_DATE_REPLY);
                String return_time = data.getStringExtra(EditLibraryActivity.EXTRA_RETURN_TIME_REPLY);

                if (!TextUtils.isEmpty(book_title)){
                    int id = data.getIntExtra(LibraryListAdapter.EXTRA_ID, -99);

                    if (id == LIBRARY_ADD){
                        mLibraryDb.insertLibrary(book_title, book_author, pub_year, isbn, language,
                                genre, series, book_cover, borrow_date, borrow_time, return_date, return_time);
                    }
                    else if (id > 0){
                        mLibraryDb.updateLibrary(id, book_title, book_author, pub_year, isbn, language,
                                genre, series, book_cover, borrow_date, borrow_time, return_date, return_time);
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
