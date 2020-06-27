package com.example.booksmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksmanager.R;
import com.example.booksmanager.databinding.ItemCatBinding;
import com.example.booksmanager.entity.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Category> categories;
    private ItemCatBinding binding;
    private Callback callback;


    public CategoryAdapter(Context context, ArrayList<Category> categories,Callback callback) {
        this.context = context;
        this.categories = categories;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cat,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        viewHolder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCat(ArrayList<Category> list) {
        categories = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemCatBinding binding;

        public ViewHolder(@NonNull ItemCatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (callback != null && position != RecyclerView.NO_POSITION){
                        callback.onClick(categories.get(position));
                    }
                }
            });
        }

        public void bindData(int position){
            Category category = categories.get(position);
            if (category != null){
                binding.textViewIcon.setText(category.getName().toUpperCase().charAt(0) +"");
                binding.textViewContentCat.setText(category.getDes());
                binding.textViewTitleCat.setText(category.getName());
            }
        }
    }

    public interface Callback{
        void onClick(Category category);
    }
}
