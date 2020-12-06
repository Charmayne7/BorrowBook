package com.example.charm.borrowbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LibraryListAdapter extends RecyclerView.Adapter<LibraryListAdapter.LibraryViewHolder> {

    class LibraryViewHolder extends RecyclerView.ViewHolder {
        public final TextView libraryItemView;
        public final TextView mTv_library_author;
        public final TextView mTv_library_pub_year;
        public final ImageView mIv_library_book_cover;

        Button mBtn_delete_library;
        Button mBtn_edit_library;

        public LibraryViewHolder(View itemView) {
            super(itemView);
            libraryItemView = (TextView) itemView.findViewById(R.id.tv_library);
            mTv_library_author = (TextView) itemView.findViewById(R.id.tv_library_author);
            mTv_library_pub_year = (TextView) itemView.findViewById(R.id.tv_library_pub_year);
            mIv_library_book_cover = (ImageView) itemView.findViewById(R.id.iv_library_book_cover);

            mBtn_delete_library = (Button)itemView.findViewById(R.id.btn_delete_library);
            mBtn_edit_library = (Button)itemView.findViewById(R.id.btn_edit_library);
        }
    }

    private static final String TAG = LibraryListAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_AUTHOR = "AUTHOR";
    public static final String EXTRA_PUB_YEAR = "PUB_YEAR";
    public static final String EXTRA_ISBN = "ISBN";
    public static final String EXTRA_LANGUAGE = "LANGUAGE";
    public static final String EXTRA_GENRE = "GENRE";
    public static final String EXTRA_SERIES = "SERIES";
    public static final String EXTRA_BOOK_COVER = "BOOK_COVER";
    public static final String EXTRA_BORROW_DATE = "BORROW_DATE";
    public static final String EXTRA_BORROW_TIME = "BORROW_TIME";
    public static final String EXTRA_RETURN_DATE = "RETURN_DATE";
    public static final String EXTRA_RETURN_TIME = "RETURN_TIME";

    private final LayoutInflater mLibraryInflater;
    Context mLibraryContext;
    LibraryListOpenHelper mLibraryDB;

    public LibraryListAdapter(Context context, LibraryListOpenHelper db) {
        mLibraryInflater = LayoutInflater.from(context);
        mLibraryContext = context;
        mLibraryDB = db;
    }

    @NonNull
    @Override
    public LibraryListAdapter.LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLibraryInflater.inflate(R.layout.librarylist_item, parent, false);
        return new LibraryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryListAdapter.LibraryViewHolder holder, int position) {
        Library current = mLibraryDB.libraryQuery(position);
        holder.libraryItemView.setText(current.getBook_title());
        holder.mTv_library_author.setText(current.getAuthor());
        holder.mTv_library_pub_year.setText(current.getPub_year());
        //holder.mIv_library_book_cover.setImageBitmap(current.getBook_cover());      // checked
        //String book_cover = String.valueOf(current.getBook_cover());
        //holder.mIv_library_book_cover.setImageResource(book_cover);

        switch(current.getBook_id()){
            case 1: holder.mIv_library_book_cover.setImageResource(R.drawable.id_tell_you);
                break;

            case 2: holder.mIv_library_book_cover.setImageResource(R.drawable.the_maze_runner);
                break;

            case 3: holder.mIv_library_book_cover.setImageResource(R.drawable.anda_kereta);
                break;

            case 4: holder.mIv_library_book_cover.setImageResource(R.drawable.the_lightning_thief);
                break;

            case 5: holder.mIv_library_book_cover.setImageResource(R.drawable.the_sea_of_monsters);
                break;

            case 6: holder.mIv_library_book_cover.setImageResource(R.drawable.the_eye_of_minds);
                break;
        }

        holder.mBtn_edit_library.setOnClickListener(new LibraryOnClickListener(current.getBook_id(),
                current.getBook_title(), current.getAuthor(), current.getPub_year(), current.getIsbn(),
                current.getLanguage(), current.getGenre(), current.getSeries(), current.getBook_cover(),
                current.getBorrowDate(), current.getBorrowTime(), current.getReturnDate(),
                current.getReturnTime()){

            @Override
            public void onClick(View v) {
                super.onClick(v);

                Intent i = new Intent(mLibraryContext, EditLibraryActivity.class);
                i.putExtra(EXTRA_ID, book_id);
                i.putExtra(EXTRA_TITLE, book_title);
                i.putExtra(EXTRA_AUTHOR, book_author);
                i.putExtra(EXTRA_PUB_YEAR, pub_year);
                i.putExtra(EXTRA_ISBN, isbn);
                i.putExtra(EXTRA_LANGUAGE, language);
                i.putExtra(EXTRA_GENRE, genre);
                i.putExtra(EXTRA_SERIES, series);
                i.putExtra(EXTRA_BOOK_COVER, book_cover);
                i.putExtra(EXTRA_BORROW_DATE, borrow_date);
                i.putExtra(EXTRA_BORROW_TIME, borrow_time);
                i.putExtra(EXTRA_RETURN_DATE, return_date);
                i.putExtra(EXTRA_RETURN_TIME, return_time);

                ((Activity)(mLibraryContext)).startActivityForResult(i, MainActivity.LIBRARY_EDIT);
            }
        });

        final LibraryViewHolder h = holder;
        holder.mBtn_delete_library.setOnClickListener(new LibraryOnClickListener(current.getBook_id(),
                null, null, null, null, null, null,
                null, null, null, null, null, null){
            @Override
            public void onClick(View v) {
                super.onClick(v);

                int deleted = mLibraryDB.deleteLibrary(book_id);
                if (deleted > 0){
                    notifyItemRemoved(h.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (int)mLibraryDB.countLibrary();
    }
}
