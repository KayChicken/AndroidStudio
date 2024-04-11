package ru.mirea.kainov.mireaproject;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class FileFragment extends Fragment {

    private static final int REQUEST_PICK_FILE = 1;
    private static final String ENCRYPTED_FILE_EXTENSION = ".enc";

    private Uri selectedFileUri;
    private EditText etPassword;
    private FloatingActionButton floatingButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);

        Button btnSelectFile = view.findViewById(R.id.btnSelectFile);
        Button btnEncrypt = view.findViewById(R.id.btnEncrypt);
        Button btnDecrypt = view.findViewById(R.id.btnDecrypt);
        floatingButton = view.findViewById(R.id.floatingButton);
        etPassword = view.findViewById(R.id.etPassword);

        btnSelectFile.setOnClickListener(v -> pickFile());

        btnEncrypt.setOnClickListener(v -> encryptFile());

        btnDecrypt.setOnClickListener(v -> decryptFile());

        floatingButton.setOnClickListener(v -> createFileButton(v));

        return view;
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_PICK_FILE);
    }

    private void encryptFile() {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(getContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SecretKey secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            InputStream inputStream = getContext().getContentResolver().openInputStream(selectedFileUri);
            if (inputStream == null) {
                Toast.makeText(getContext(), "Не удалось получить доступ к файлу", Toast.LENGTH_SHORT).show();
                return;
            }

            String fileName = getFileNameFromUri(selectedFileUri);
            String encryptedFileName = fileName + "_encrypted"; // добавляем "_encrypted" к имени файла
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            FileOutputStream fos = new FileOutputStream(new File(path, encryptedFileName));
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
            cos.close();
            inputStream.close();

            Toast.makeText(getContext(), "Файл зашифрован", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка при шифровании файла", Toast.LENGTH_SHORT).show();
            Log.d("Error" , e.toString());
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void decryptFile() {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(getContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SecretKey secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            ContentResolver resolver = getContext().getContentResolver();
            InputStream inputStream = resolver.openInputStream(selectedFileUri);
            if (inputStream == null) {
                Toast.makeText(getContext(), "Не удалось получить доступ к файлу", Toast.LENGTH_SHORT).show();
                return;
            }

            String fileName = getFileNameFromUri(selectedFileUri);
            String decryptedFileName = fileName.replace("_encrypted", "_uncrypted"); // убираем "_encrypted" из имени файла
            File decryptedFile = new File(getContext().getFilesDir(), decryptedFileName);
            FileOutputStream fos = new FileOutputStream(decryptedFile);

            CipherInputStream cis = new CipherInputStream(inputStream, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            cis.close();
            inputStream.close();

            Toast.makeText(getContext(), "Файл дешифрован", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка при дешифровании файла", Toast.LENGTH_SHORT).show();
        }
    }

    private SecretKey generateKey(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] salt = new byte[16];
        SecretKey keySpec = new SecretKeySpec(factory.generateSecret(new PBEKeySpec(password.toCharArray(), salt, 65536, 256)).getEncoded(), "AES");
        return keySpec;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_FILE && resultCode == getActivity().RESULT_OK && data != null) {
            selectedFileUri = data.getData();
        }
    }


    public void createFileButton(View view) {
        Intent intent = new Intent(getActivity(), CreateFileFragment.class);
        startActivity(intent);
    }
}