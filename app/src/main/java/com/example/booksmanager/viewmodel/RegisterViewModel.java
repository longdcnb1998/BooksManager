package com.example.booksmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.booksmanager.repository.UserRepository;
import com.example.booksmanager.entity.User;

import java.util.List;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<User>> data;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        data = repository.getUsers();
    }

    public LiveData<List<User>> getUsers(){
        return data;
    }

    public void insertUser(User user){
        repository.InsertUser(user);
    }
}
