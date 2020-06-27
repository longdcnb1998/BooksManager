package com.example.booksmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.booksmanager.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM tb_books WHERE catId = :id ")
    LiveData<List<Book>> getBooks(int id);


    @Query("SELECT * FROM tb_books WHERE id = :id AND name LIKE '%' || :filter  || '%' ")
    LiveData<List<Book>> searchBook(int id,String filter);
}
