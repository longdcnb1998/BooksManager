package com.example.booksmanager.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.booksmanager.R;
import com.example.booksmanager.databinding.DialogRegisterBinding;
import com.example.booksmanager.entity.User;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.viewmodel.RegisterViewModel;

public class DialogRegister extends DialogFragment {

    private Activity activity;
    private Dialog dialog;
    private DialogRegisterBinding binding;
    private RegisterViewModel viewModel;

    private String username;
    private String pass;

    private Callback callback;

    public DialogRegister(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_register);
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);


       binding.btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               username = binding.edtUsername.getText().toString().trim();
               pass = binding.edtPass.getText().toString().trim();
               if (username.length() > 0 && pass.length() > 0) {
                   User user = new User(username, pass);
                    viewModel.insertUser(user);
                    if (callback != null){
                        callback.onRegisterSuccess();
                    }
                    dismiss();
               } else {
                   Toast.makeText(activity, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
               }
           }
       });
    }

    public interface Callback{
        void onRegisterSuccess();
    }
}
