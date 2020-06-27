package com.example.booksmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.booksmanager.entity.Book;
import com.example.booksmanager.repository.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<Book>> data;

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
    }

    public LiveData<List<Book>> getBooks(int id){
        return repository.getBooks(id);
    }

    public void insertBook(Book book){
        repository.InsertBook(book);
    }

    public LiveData<List<Book>> searchBooks(int id, String s){
        return repository.searchBook(id,s);
    }

    public void deleteBook(Book book) {
        repository.deleteBook(book);
    }

    public void updateBook(Book newBook) {
        repository.updateBook(newBook);
    }
}
