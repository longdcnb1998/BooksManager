package com.example.booksmanager.db;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.booksmanager.entity.Book;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;

@Database(entities = {User.class, Category.class, Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract BookDao bookDao();

    public static String path = Environment.getExternalStorageDirectory() + "/BooksManger/books.db";

    public static synchronized AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, AppDatabase.class, "Long.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.d("ONCREATE","Database has been created.");
                            new PopulateDbAsyncTask(mInstance).execute();
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                        }

                        @Override
                        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                            super.onDestructiveMigration(db);
                        }
                    }).build();
        }
        return mInstance;
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;


        public PopulateDbAsyncTask(AppDatabase mInstance) {
            userDao = mInstance.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insert(new User("admin", "admin"));
            return null;
        }
    }
}
