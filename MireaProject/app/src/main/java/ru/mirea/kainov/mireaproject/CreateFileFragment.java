package ru.mirea.kainov.mireaproject;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CreateFileFragment extends AppCompatActivity {
    private EditText fileName;
    private EditText fileInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_file_fragment);
        Button btn = findViewById(R.id.back);
        Button createButton = findViewById(R.id.saveFile);
        fileName = findViewById(R.id.fileName);
        fileInfo = findViewById(R.id.fileInformation);

        btn.setOnClickListener(v -> leaveIntent());
        createButton.setOnClickListener(v -> saveFile());

    }



    public void leaveIntent() {
        finish();
    }


    public void saveFile() {
        Log.d("Hell", "Файл сохранен");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName.getText().toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
            //	Запись строки в файл
            output.write(fileInfo.getText().toString());
            Log.d("FileLocation", "Файл сохранен в: " + file.getAbsolutePath());
            //	Закрытие потока записи
            output.close();

        } catch (IOException e) {
            Log.w("ExternalStorage", "Error	writing	" + file, e);
        }
    }
}