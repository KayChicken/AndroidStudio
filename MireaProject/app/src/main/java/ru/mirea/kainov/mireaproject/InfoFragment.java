package ru.mirea.kainov.mireaproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import ru.mirea.kainov.mireaproject.databinding.ActivityMainBinding;
import ru.mirea.kainov.mireaproject.databinding.FragmentInfoBinding;
import ru.mirea.kainov.mireaproject.databinding.FragmentProfileBinding;


public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(getLayoutInflater());
        binding.generateJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetJoke().execute("https://api.chucknorris.io/jokes/random");
            }
        });
        View view = binding.getRoot();
        return view;
    }







    public class GetJoke extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.textJoke.setText("Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuilder responseBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    reader.close();
                    response = responseBuilder.toString();
                } else {
                    response = "Error response code: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                response = "Error: " + e.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject responseJSON = new JSONObject(result);
                Log.d("SUKS" , String.valueOf(responseJSON));
                String joke = responseJSON.getString("value");
                binding.textJoke.setText(joke);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }


}