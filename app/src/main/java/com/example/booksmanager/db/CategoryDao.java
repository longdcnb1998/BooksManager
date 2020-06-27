package com.example.booksmanager.db;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.booksmanager.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {


    @Insert
    void insert(Category category);

    @Query("SELECT * FROM tb_cat WHERE userId = :id")
    LiveData<List<Category>> getCategories(int id);
    @Query("SELECT * FROM tb_cat WHERE userId = :id AND name LIKE '%' || :filter  || '%' ")
    LiveData<List<Category>> searchCat(int id, String filter);
}
