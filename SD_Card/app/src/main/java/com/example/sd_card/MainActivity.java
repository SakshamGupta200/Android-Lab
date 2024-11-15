package com.example.sd_card;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class MainActivity extends Activity {
    EditText inputText;
    TextView response;
    Button saveButton, readButton;
    private String filename = "Umang.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    StringBuilder myData = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.myInputText);
        response = findViewById(R.id.response);
        saveButton = findViewById(R.id.saveExternalStorage);
        readButton = findViewById(R.id.getExternalStorage);
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
        } else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
    }
    private void saveData() {
        try (FileOutputStream fos = new FileOutputStream(myExternalFile)) {
            fos.write(inputText.getText().toString().getBytes());
            inputText.setText("");
            response.setText("Umang.txt saved to External Storage...");
            Toast.makeText(this, "Umang.txt saved to External Storage...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            response.setText("Error saving file.");
            Toast.makeText(this, "Error generate to save on External Storage...", Toast.LENGTH_SHORT).show();
        }
    }
    private void readData() {
        myData.setLength(0); // Clear previous data
        try (FileInputStream fis = new FileInputStream(myExternalFile);
             DataInputStream in = new DataInputStream(fis);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData.append(strLine).append("\n");
            }
            inputText.setText(myData.toString());
            response.setText("SampleFile.txt data retrieved from External Storage...");
        } catch (IOException e) {
            e.printStackTrace();
            response.setText("Error reading file.");
        }
    }
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }
    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}
