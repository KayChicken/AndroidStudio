package ru.mirea.kainov.lesson3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

//    public void nextActivity() {
//        long dateInMillis = System.currentTimeMillis();
//        String format = "yyyy-MM-dd HH:mm:ss";
//        final SimpleDateFormat sdf = new SimpleDateFormat(format);
//        String dateString = sdf.format(new Date(dateInMillis));
//        Intent intent = new Intent(this, SecondActivity.class);
//        intent.putExtra("text", String.format("Квадрат значения моего номера по списку в группе составляет 121, а текущее время %s",dateString));
//        startActivity(intent);
//    }
}