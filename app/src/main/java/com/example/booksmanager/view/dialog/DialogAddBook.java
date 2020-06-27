package com.example.booksmanager.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.booksmanager.R;
import com.example.booksmanager.databinding.DialogAddBookBinding;
import com.example.booksmanager.entity.Book;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.utils.KeyboardController;

public class DialogAddBook extends DialogFragment {
    private Activity activity;
    private Dialog dialog;

    private DialogAddBookBinding binding;

    private Category category;
    private CallBack callBack;

    public DialogAddBook(Category category, CallBack callBack) {
        this.category = category;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_book);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_book, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       binding.btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String name = binding.edtName.getText().toString().trim();
               String des = binding.edtDes.getText().toString().trim();
               Book book = new Book(category.getId(),name,des);
               if (book != null){
                   if (callBack != null){
                       callBack.onAddSuccess(book);
                       KeyboardController.hide(activity,binding.edtDes);
                       dismiss();
                   }
               }
           }
       });
    }

    public interface CallBack{
        void onAddSuccess(Book book);
    }
}
