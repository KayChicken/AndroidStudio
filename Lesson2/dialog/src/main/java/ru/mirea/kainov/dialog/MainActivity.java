package ru.mirea.kainov.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }




    public void onClickShowDialog(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }


    public void onClickDateDialog(View view) {
        MyDateDialogFragment dateFragment = new MyDateDialogFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onProgressDialog(View view) {
        MyProgressDialogFragment progressFragment = new MyProgressDialogFragment();
        progressFragment.show(getSupportFragmentManager(), "progressFragment");
    }

    public void onTimeDialog(View view) {
        MyTimeDialogFragment timeFragment = new MyTimeDialogFragment();
        timeFragment.show(getSupportFragmentManager(), "timeFragment");
    }

    public void onSnackbar(View view) {
        Snackbar.make(view, "Hello Android", Snackbar.LENGTH_LONG).show();
    }
}