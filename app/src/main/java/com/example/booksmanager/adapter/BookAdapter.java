package com.example.booksmanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksmanager.R;
import com.example.booksmanager.databinding.ItemBookBinding;
import com.example.booksmanager.entity.Book;

import java.util.ArrayList;

public class BookAdapter  extends RecyclerView.Adapter<BookAdapter.viewHolder>{

    private Context context;
    private ArrayList<Book> list;
    private ItemBookBinding binding;
    private Callback callback;

    public BookAdapter(Context context, ArrayList<Book> list, Callback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_book,parent,false);

        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setBooks(ArrayList<Book> books){
        list = books;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private ItemBookBinding binding;

        public viewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (callback != null && position != RecyclerView.NO_POSITION){
                        callback.onClick(list.get(position));
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bindData(int position){
           Book book = list.get(position);
           if (book != null){
               binding.textViewIcon.setText(book.getName().toUpperCase().charAt(0) +"");
               binding.textViewContentCat.setText(book.getDes());
               binding.textViewTitleCat.setText(book.getName());
           }

        }
    }

    public interface Callback{
        void onClick(Book book);
    }
}
