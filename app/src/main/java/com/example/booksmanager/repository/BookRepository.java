package com.example.booksmanager.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.booksmanager.db.AppDatabase;
import com.example.booksmanager.db.BookDao;
import com.example.booksmanager.db.CategoryDao;
import com.example.booksmanager.entity.Book;
import com.example.booksmanager.entity.Category;

import java.util.List;

public class BookRepository {
    private BookDao bookDao;

    private LiveData<List<Book>> data;

    public BookRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        bookDao = database.bookDao();
    }

    public LiveData<List<Book>> getBooks(int id){
        return bookDao.getBooks(id);
    }
    public LiveData<List<Book>> searchBook(int id,String s){
        return bookDao.searchBook(id,s);
    }

    public void InsertBook(Book book){
      new insertBookAsyncTask(bookDao).execute(book);
    }

    public void deleteBook(Book book) {
        new deleteBookAsyncTask(bookDao).execute(book);
    }

    public void updateBook(Book newBook) {
        new updateBookAsyncTask(bookDao).execute(newBook);
    }

    private class insertBookAsyncTask extends AsyncTask<Book, Void,Void> {
        private BookDao bookDao;
        public insertBookAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.insert(books[0]);
            return null;
        }
    }
    private class deleteBookAsyncTask extends AsyncTask<Book, Void,Void> {
        private BookDao bookDao;
        public deleteBookAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.delete(books[0]);
            return null;
        }
    }

    private class updateBookAsyncTask extends AsyncTask<Book, Void,Void> {
        private BookDao bookDao;
        public updateBookAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.update(books[0]);
            return null;
        }
    }

}
