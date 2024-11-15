package com.example.database2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText titleEditText, descriptionEditText;
    Button addButton;
    ListView notesListView;
    ArrayList<String> notesList;
    ArrayAdapter<String> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addButton = findViewById(R.id.addButton);
        notesListView = findViewById(R.id.notesListView);
        notesList = new ArrayList<>();

        loadNotes();

        addButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both title and description", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertNote(title, description);
            if (isInserted) {
                Toast.makeText(MainActivity.this, "Note added successfully", Toast.LENGTH_SHORT).show();
                loadNotes();
            } else {
                Toast.makeText(MainActivity.this, "Failed to add note", Toast.LENGTH_SHORT).show();
            }
        });

        notesListView.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedItem = notesList.get(position);
            int noteId = Integer.parseInt(selectedItem.split(":")[0]);
            boolean isDeleted = dbHelper.deleteNoteById(noteId);
            if (isDeleted) {
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                loadNotes();
            } else {
                Toast.makeText(MainActivity.this, "Failed to delete note", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    private void loadNotes() {
        Cursor cursor = dbHelper.getAllNotes();
        notesList.clear();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                notesList.add(id + ": " + title + "\n" + description);
            } while (cursor.moveToNext());
        }
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        notesListView.setAdapter(notesAdapter);
        notesAdapter.notifyDataSetChanged();
    }
}