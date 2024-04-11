package ru.mirea.kainov.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.kainov.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private EditText groupInput;
    private EditText number;
    private EditText favorite;
    private Button confirmButton;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        confirmButton = binding.button;
        groupInput = binding.editGroup;
        favorite = binding.editFavorite;
        number = binding.editNumber;

        groupInput.setText(sharedPref.getString("GROUP", ""));
        number.setText(String.valueOf(sharedPref.getInt("NUMBER", 0)));
        favorite.setText(sharedPref.getString("FAVORITE", ""));
    }

    public void onConfirm(View view) {
        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GROUP", groupInput.getText().toString());
        editor.putInt("NUMBER", Integer.parseInt(number.getText().toString()));
        editor.putString("FAVORITE", favorite.getText().toString());
        editor.apply();
    }
}