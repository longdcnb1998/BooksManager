package com.example.booksmanager.viewmodel;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.booksmanager.entity.Book;
import com.example.booksmanager.repository.CategoryRepository;
import com.example.booksmanager.repository.UserRepository;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;
    private CategoryRepository categoryRepository;
    private LiveData<List<User>> data;
    private LiveData<List<Category>> listCat;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        categoryRepository = new CategoryRepository(application);
        data = repository.getUsers();
    }

    public LiveData<List<User>> getUsers(){
        return data;
    }

    public LiveData<List<Category>> getCategories(int id){
        return repository.getCategories(id);
    }

    public void insertCat(Category category){
        categoryRepository.InsertCat(category);
    }


    public LiveData<List<Category>> searchCat(int id, String s) {
       return repository.searchCat(id,s);
    }
}
