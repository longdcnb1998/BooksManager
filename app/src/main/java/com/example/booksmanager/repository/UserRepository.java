package com.example.booksmanager.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.text.Editable;

import androidx.lifecycle.LiveData;

import com.example.booksmanager.db.AppDatabase;
import com.example.booksmanager.db.CategoryDao;
import com.example.booksmanager.db.UserDao;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private CategoryDao categoryDao;

    private LiveData<List<User>> data;

    private LiveData<List<Category>> listCat;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        categoryDao = database.categoryDao();
        data = userDao.getUsers();
    }

    public LiveData<List<User>> getUsers(){
        return data;
    }

    public LiveData<List<Category>> getCategories(int id){
        return categoryDao.getCategories(id);
    }

    public void InsertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }

    public void InsertCat(Category category){
        new insertCatAsyncTask(categoryDao).execute(category);
    }

    public LiveData<List<Category>> searchCat(int id, String s) {
       return categoryDao.searchCat(id,s);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao dao;

        public insertAsyncTask(UserDao userDao) {
            this.dao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.insert(users[0]);
            return null;
        }
    }

    private class insertCatAsyncTask extends AsyncTask<Category, Void,Void> {
        private CategoryDao categoryDao;
        public insertCatAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }
}
