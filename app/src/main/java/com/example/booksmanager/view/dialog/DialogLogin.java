package com.example.booksmanager.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.booksmanager.databinding.DialogLoginBinding;
import com.example.booksmanager.entity.User;
import com.example.booksmanager.utils.DialogUtils;
import com.example.booksmanager.view.MainActivity;
import com.example.booksmanager.viewmodel.LoginViewModel;

import java.util.Objects;

public class DialogLogin extends DialogFragment {

    private Activity activity;
    private Dialog dialog;
    private DialogLoginBinding binding;
    private String username;
    private String pass;
    private boolean allow = false;
    private LoginViewModel viewModel;
    private CallBack callBack;

    private boolean isFirst = false;
    private boolean isRequired = false;




    public DialogLogin(boolean isFirst, boolean isRequired, CallBack callBack) {
        this.isFirst = isFirst;
        this.isRequired = isRequired;
        this.callBack = callBack;
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
        dialog.setContentView(R.layout.dialog_login);
        dialog.show();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_login, container, false);
        initUI();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }



    @SuppressLint("FragmentLiveDataObserve")
    private void initUI() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void initData() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.edtUsername.getText().toString().trim();
                pass = binding.edtPass.getText().toString().trim();
                if (username.length() > 0 || pass.length() > 0) {
                    User user = new User(username, pass);
                    viewModel.getUsers().observe(Objects.requireNonNull(getActivity()), data -> {
                        for (int i = 0; i < data.size(); i++) {
                            if (username.equals(data.get(i).getUserName()) && pass.equals(data.get(i).getPassword())) {
                                if (callBack != null) {
                                    callBack.onLoginSuccess(data.get(i));
                                    dismiss();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(activity, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(), DialogRegister.class.getSimpleName())) {
                    new DialogRegister(new DialogRegister.Callback() {
                        @Override
                        public void onRegisterSuccess() {

                        }
                    }).show(getChildFragmentManager(), DialogRegister.class.getSimpleName());
                }
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                System.exit(0);
            }
        });
    }



    public interface CallBack {
        void onLoginSuccess(User user);
    }

}
