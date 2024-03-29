package ru.mirea.kainov.toastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextText);


    }

    public void toasterUp(View view) {
        int text = editText.getText().toString().length();
        Toast toast = Toast.makeText(getApplicationContext(),String.format("Кайнов.Д.А № Х БСБО-11-21 Х Количество символов : %s",text),Toast.LENGTH_SHORT);
        toast.show();
    }
}