package com.example.booksmanager.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksmanager.R;
import com.example.booksmanager.adapter.BookAdapter;
import com.example.booksmanager.databinding.DialogBookBinding;
import com.example.booksmanager.entity.Book;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.viewmodel.BookViewModel;

import java.util.ArrayList;

public class DialogBook extends DialogFragment {

    private Activity activity;
    private Dialog dialog;
    private Category category;
    private DialogBookBinding binding;
    private BookViewModel viewModel;

    private BookAdapter adapter;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }


    public DialogBook(Category category) {
        this.category = category;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialog(activity);
        dialog.setContentView(R.layout.dialog_book);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_book,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.listBook.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,false));
        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.getBooks(category.getId()).observe((LifecycleOwner) activity, data ->{
            if (data.size() > 0){
                binding.ivEmpty.setVisibility(View.GONE);
            }
            adapter = new BookAdapter(activity, (ArrayList<Book>) data, new BookAdapter.Callback() {
                @Override
                public void onClick(Book book) {
                    if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(),DialogEdit.class.getSimpleName())){
                        new DialogEdit(book).show(getChildFragmentManager(), DialogEdit.class.getSimpleName());
                    }
                }
            });
            binding.listBook.setAdapter(adapter);
            Log.d("LongDinh",data.size() + "");
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SearchBook(category.getId(),binding.edtSearch.getText().toString().trim());
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(),DialogAddBook.class.getSimpleName())){
                    new DialogAddBook(category, new DialogAddBook.CallBack() {
                        @Override
                        public void onAddSuccess(Book book) {
                            viewModel.insertBook(book);

                        }
                    }).show(getChildFragmentManager(),DialogAddBook.class.getSimpleName());
                }
            }
        });
    }

    private void SearchBook(int id,String s) {
        if (s.length() > 0){
            viewModel.searchBooks(id,s).observe(this,data ->{
                adapter.setBooks((ArrayList<Book>) data);
            });
        }
        else {
            viewModel.getBooks(category.getId()).observe((LifecycleOwner) activity, data ->{
                adapter.setBooks((ArrayList<Book>) data);
            });
        }
    }
}
