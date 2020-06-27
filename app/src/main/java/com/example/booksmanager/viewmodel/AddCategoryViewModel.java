package com.example.booksmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;
import com.example.booksmanager.repository.CategoryRepository;

public class AddCategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;

    public AddCategoryViewModel(@NonNull Application application) {
        super(application);
        repository  = new CategoryRepository(application);
    }

    public void insertCat(Category category){
        repository.InsertCat(category);
    }
}
