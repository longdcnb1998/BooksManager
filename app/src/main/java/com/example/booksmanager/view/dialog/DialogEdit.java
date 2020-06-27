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
import androidx.lifecycle.ViewModelProviders;

import com.example.booksmanager.R;
import com.example.booksmanager.databinding.DialogEditBinding;
import com.example.booksmanager.entity.Book;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.viewmodel.BookViewModel;

public class DialogEdit extends DialogFragment {

    private Activity activity;
    private Dialog dialog;
    private DialogEditBinding binding;
    private Book book;
    private BookViewModel viewModel;

    public DialogEdit(Book book) {
        this.book = book;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialog(activity);
        dialog.setContentView(R.layout.dialog_edit);
        dialog.setCancelable(false);
        dialog.show();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_edit,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        binding.edtName.setText(book.getName());
        binding.edtDes.setText(book.getDes());

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteBook(book);
                dismiss();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setName(binding.edtName.getText().toString().trim());
                book.setDes(binding.edtDes.getText().toString().trim());
                viewModel.updateBook(book);
                dismiss();
            }
        });
    }
}
