package com.example.booksmanager.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.example.booksmanager.databinding.DialogAddCatBinding;
import com.example.booksmanager.entity.Category;
import com.example.booksmanager.entity.User;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.utils.KeyboardController;
import com.example.booksmanager.viewmodel.AddCategoryViewModel;

public class DialogAddCategory extends DialogFragment {

    private Activity activity;
    private Dialog dialog;
    private User user;
    private AddCategoryViewModel viewModel;
    private DialogAddCatBinding binding;
    private Callback callback;

    public DialogAddCategory(User user, Callback callback) {
        this.user = user;
        this.callback = callback;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       dialog = DialogUtils.getNewDialog(activity);
       dialog.setCancelable(false);
       dialog.setContentView(R.layout.dialog_add_cat);
       dialog.show();
       return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_add_cat,container,false);
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
               Category category = new Category(user.getId(),name,des);

               if (callback != null){
                   callback.onAddSuccess(category);
                   KeyboardController.hide(activity,binding.edtDes);
                   dismiss();
               }
           }
       });
    }


    public interface Callback{
        void onAddSuccess(Category category);
    }
}
