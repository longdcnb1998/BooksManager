package com.example.booksmanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.booksmanager.R;
import com.example.booksmanager.adapter.CategoryAdapter;
import com.example.booksmanager.databinding.ActivityMainBinding;
import com.example.booksmanager.entity.Book;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.view.dialog.DialogAddCategory;
import com.example.booksmanager.view.dialog.DialogBook;
import com.example.booksmanager.view.dialog.DialogLogin;
import com.example.booksmanager.viewmodel.LoginViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private CategoryAdapter adapter;
    private ActivityMainBinding binding;
    private User user;
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        initData();
    }

    private void initData() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getSupportFragmentManager(), DialogAddCategory.class.getSimpleName())){
                    new DialogAddCategory(user, new DialogAddCategory.Callback() {
                        @Override
                        public void onAddSuccess(Category category) {
                            viewModel.insertCat(category);
                        }
                    }).show(getSupportFragmentManager(),DialogAddCategory.class.getSimpleName());
                }
            }
        });
        binding.edtSearchCat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SearchCat(user.getId(), String.valueOf(s));
            }
        });
    }

    private void SearchCat(int id, String s) {
        if (s.length() >0){
            viewModel.searchCat(id,s).observe(this,data->{
                adapter.setCat((ArrayList<Category>) data);
            });
        }
        else {
            adapter.setCat(categories);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (DialogUtils.enableShowDialogFragment(getSupportFragmentManager(), DialogLogin.class.getSimpleName())){
                        new DialogLogin(true,true,new DialogLogin.CallBack() {
                            @Override
                            public void onLoginSuccess(User result) {
                                user = result;
                                viewModel = ViewModelProviders.of(MainActivity.this).get(LoginViewModel.class);
                                viewModel.getCategories(user.getId()).observe(MainActivity.this,data ->{
                                    if (data.size() >0){
                                        binding.ivEmpty.setVisibility(View.GONE);
                                    }
                                    categories = (ArrayList<Category>) data;
                                    adapter = new CategoryAdapter(MainActivity.this, (ArrayList<Category>) data, new CategoryAdapter.Callback() {
                                        @Override
                                        public void onClick(Category category) {
                                            if (DialogUtils.enableShowDialogFragment(getSupportFragmentManager(), DialogBook.class.getSimpleName())){
                                                new DialogBook(category).show(getSupportFragmentManager(),DialogBook.class.getSimpleName());
                                            }
                                        }
                                    });
                                    binding.recyclerView.setAdapter(adapter);
                                });
                            }
                        }).show(getSupportFragmentManager(),DialogLogin.class.getSimpleName());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}