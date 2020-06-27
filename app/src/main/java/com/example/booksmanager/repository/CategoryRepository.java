package com.example.booksmanager.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.booksmanager.db.AppDatabase;
import com.example.booksmanager.db.CategoryDao;
import com.example.booksmanager.db.UserDao;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;

import java.util.List;

public class CategoryRepository {

    private UserDao userDao;
    private CategoryDao categoryDao;

    private LiveData<List<User>> data;

    private LiveData<List<Category>> listCat;

    public CategoryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        categoryDao = database.categoryDao();
    }

    public void InsertCat(Category category){
        new insertCatAsyncTask(categoryDao).execute(category);
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
