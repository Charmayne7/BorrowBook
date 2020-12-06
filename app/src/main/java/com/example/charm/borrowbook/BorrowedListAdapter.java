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
import android.widget.TextView;

public class BorrowedListAdapter extends RecyclerView.Adapter<BorrowedListAdapter.BorrowedViewHolder>{

    class BorrowedViewHolder extends RecyclerView.ViewHolder {
        public final TextView borrowedItemView;
        public final TextView mTv_borrowed_author;
        public final TextView mTv_borrowed_pub_year;

        Button mBtn_delete;
        Button mBtn_edit;

        public BorrowedViewHolder(View itemView) {
            super(itemView);
            borrowedItemView = (TextView) itemView.findViewById(R.id.tv_borrowed);
            mTv_borrowed_author = (TextView) itemView.findViewById(R.id.tv_borrowed_author);
            mTv_borrowed_pub_year = (TextView) itemView.findViewById(R.id.tv_borrowed_pub_year);

            mBtn_delete = (Button)itemView.findViewById(R.id.btn_delete);
            mBtn_edit = (Button)itemView.findViewById(R.id.btn_edit);
        }
    }

    private static final String TAG = BorrowedListAdapter.class.getSimpleName();

    public static final String EXTRA_BORROWED_ID = "ID";
    public static final String EXTRA_BORROWED = "BORROWED";
    public static final String EXTRA_AUTHOR = "AUTHOR";
    public static final String EXTRA_PUB_YEAR = "PUB_YEAR";
    public static final String EXTRA_ISBN = "ISBN";
    public static final String EXTRA_LANGUAGE = "LANGUAGE";
    public static final String EXTRA_GENRE = "GENRE";
    public static final String EXTRA_SERIES = "SERIES";
    public static final String EXTRA_BORROW_DATE = "BORROW_DATE";
    public static final String EXTRA_BORROW_TIME = "BORROW_TIME";
    public static final String EXTRA_RETURN_DATE = "RETURN_DATE";
    public static final String EXTRA_RETURN_TIME = "RETURN_TIME";

    private final LayoutInflater mInflater;
    Context mContext;
    BorrowedListOpenHelper mDB;

    public BorrowedListAdapter(Context context, BorrowedListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
    }


    @NonNull
    @Override
    public BorrowedListAdapter.BorrowedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.borrowedlist_item, parent, false);
        return new BorrowedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedListAdapter.BorrowedViewHolder holder, int position) {
        Borrowed current = mDB.borrowedQuery(position);
        holder.borrowedItemView.setText(current.getBook_title());
        holder.mTv_borrowed_author.setText(current.getAuthor());
        holder.mTv_borrowed_pub_year.setText(current.getPub_year());

        holder.mBtn_edit.setOnClickListener(new BorrowedOnClickListener(current.getBook_id(),
                current.getBook_title(), current.getAuthor(), current.getPub_year(), current.getIsbn(),
                current.getLanguage(), current.getGenre(), current.getSeries(), current.getBorrowDate(),
                current.getBorrowTime(), current.getReturnDate(), current.getReturnTime()){

            @Override
            public void onClick(View v) {
                super.onClick(v);

                Intent i = new Intent(mContext, AddBorrowedActivity.class);
                i.putExtra(EXTRA_BORROWED_ID, borrowed_id);
                i.putExtra(EXTRA_BORROWED, borrowed_title);
                i.putExtra(EXTRA_AUTHOR, book_author);
                i.putExtra(EXTRA_PUB_YEAR, pub_year);
                i.putExtra(EXTRA_ISBN, isbn);
                i.putExtra(EXTRA_LANGUAGE, language);
                i.putExtra(EXTRA_GENRE, genre);
                i.putExtra(EXTRA_SERIES, series);
                i.putExtra(EXTRA_BORROW_DATE, borrow_date);
                i.putExtra(EXTRA_BORROW_TIME, borrow_time);
                i.putExtra(EXTRA_RETURN_DATE, return_date);
                i.putExtra(EXTRA_RETURN_TIME, return_time);

                ((Activity)(mContext)).startActivityForResult(i, MainActivity.BORROWED_EDIT);
            }
        });

        final BorrowedViewHolder h = holder;
        holder.mBtn_delete.setOnClickListener(new BorrowedOnClickListener(current.getBook_id(),
                null, null, null, null, null, null,
                null, null, null, null, null){
            @Override
            public void onClick(View v) {
                super.onClick(v);

                int deleted = mDB.deleteBorrowed(borrowed_id);
                if (deleted > 0){
                    notifyItemRemoved(h.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (int)mDB.count();
    }
}
