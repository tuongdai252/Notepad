package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String FileNote = "savednote.txt";
    private String FileUpdate = "savedupdate.txt";
    private TextView LU; //Last Update
    private TextView NT; //Note text

    public boolean isEmptyFile(String filename) {
        File file = new File(filename);
        if(file.length() == 0) {
            return false;
        }
        return true;
    }

    private void saveUpdate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String data = formatter.format(date);
        try {
            FileOutputStream out = this.openFileOutput(FileUpdate, MODE_PRIVATE);
            out.write(data.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setText(TextView text, String str) {
        text.setText(str);
    }

    private void readUpdate() {
        try {
            FileInputStream in = this.openFileInput(FileUpdate);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            String str = "Last Update: " + sb.toString();
            setText(LU, str);
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setCurrentTime(TextView text) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currenttime = formatter.format(date);
        String str = "Last Update: " + currenttime;
        text.setText(str);
    }

    private void saveData() {
        NT = (TextView) findViewById(R.id.NoteText);
        String data = NT.getText().toString();
        try {
            FileOutputStream out = this.openFileOutput(FileNote, MODE_PRIVATE);
            out.write(data.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readData() {
        try {
            FileInputStream in = this.openFileInput(FileNote);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            setText(NT, sb.toString());
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LU = (TextView) findViewById(R.id.LastUpdate);
        NT = (TextView) findViewById(R.id.NoteText);
        if(isEmptyFile(FileNote)) {
            setCurrentTime(LU);
        }
        else {
            readUpdate();
            readData();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if(isEmptyFile(FileNote)) {
            setCurrentTime(LU);
        }
        else {
            saveUpdate();
            saveData();
        }
    }
}
