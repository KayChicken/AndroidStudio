package ru.mirea.kainov.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("Worker", "Starting background task...");


        for (int i = 0; i < 5; i++) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure();
            }

            Log.d("Worker", "Background task iteration " + (i + 1));
        }

        Log.d("Worker", "Background task completed.");

        return Result.success();
    }
}
