package ru.mirea.kainov.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.kainov.mireaproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private TextView userName;
    private TextView userSurname;
    private TextView userGroup;
    private EditText editUserName;
    private EditText editUserSurname;
    private EditText editUserGroup;
    private Button saveChangesButton;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userName = binding.userName;
        userSurname = binding.userSurname;
        userGroup = binding.userGroup;

        editUserName = binding.editUserName;
        editUserSurname = binding.editUserSurname;
        editUserGroup = binding.editUserGroup;
        saveChangesButton = binding.saveChanges;
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);


        userName.setText(sharedPref.getString("username", "Неизвестно"));
        userSurname.setText(sharedPref.getString("surname", "Неизвестно"));
        userGroup.setText(sharedPref.getString("group", "Неизвестно"));
        editUserName.setText(sharedPref.getString("username", "Неизвестно"));
        editUserSurname.setText(sharedPref.getString("surname", "Неизвестно"));
        editUserGroup.setText(sharedPref.getString("group", "Неизвестно"));

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", editUserName.getText().toString());
                editor.putString("surname", editUserSurname.getText().toString());
                editor.putString("group", editUserGroup.getText().toString());
                userName.setText(editUserName.getText().toString());
                userSurname.setText(editUserSurname.getText().toString());
                userGroup.setText(editUserGroup.getText().toString());
                editor.apply();
            }
        });

        return view;
    }
}