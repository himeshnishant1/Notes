package com.example.notes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.DataBase.dbHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements onItemClick{
    RecyclerView rvNote;
    recyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton floatingActionButton;
    ArrayList<DataModel> dataModels;
    dbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new dbHandler(MainActivity.this);
        dataModels = dbHandler.readNotes();
        if(dataModels.size() == 0){
            dbHandler.addNewNote("Sample Note", "10/12/2000 12:15:20", "You can delete or update this note by click on the note. If you want to add a note press the button with + sign on bottom right.");
            dataModels = dbHandler.readNotes();
        }

        rvNote = findViewById(R.id.rvNote);
        recyclerViewAdapter = new recyclerViewAdapter(dataModels, this, this::onClick);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(recyclerViewAdapter);

        floatingActionButton = findViewById(R.id.fabAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.popup_card);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                EditText title = dialog.findViewById(R.id.etEditTitle);
                EditText message = dialog.findViewById(R.id.etEditMessage);
                title.setText("");
                message.setText("");
                Button btnAdd = dialog.findViewById(R.id.btnUpdateAdd);
                btnAdd.setText("Add");
                dialog.findViewById(R.id.btnDelete).setVisibility(View.GONE);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        float r = (float)(new Random().nextInt(5));
                        float g = (float)(new Random().nextInt(5));
                        float b = (float)(new Random().nextInt(5));
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            String datetime = dtf.format(LocalDateTime.now());
                            dbHandler.addNewNote(title.getText().toString(), datetime, message.getText().toString());
                            DataModel dataModel = dbHandler.getSingleModel(datetime);
                            dataModels.add(0, dataModel);
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "New Note Added", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_card);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText title = dialog.findViewById(R.id.etEditTitle);
        EditText message = dialog.findViewById(R.id.etEditMessage);
        title.setText(dataModels.get(position).getNoteTitle());
        message.setText(dataModels.get(position).getNoteMessage());
        dialog.findViewById(R.id.btnUpdateAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float r = (float)(new Random().nextInt(5));
                float g = (float)(new Random().nextInt(5));
                float b = (float)(new Random().nextInt(5));
                DataModel dataModel = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dbHandler.updateNote(title.getText().toString(), dataModels.get(position).getNoteDateTime(), message.getText().toString());
                    dataModel = dbHandler.getSingleModel(dataModels.get(position).getNoteDateTime());
                }
                dataModels.set(position, dataModel);
                recyclerViewAdapter.notifyDataSetChanged();
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Note Updated", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.DeleteNote(dataModels.get(position).getId());
                dataModels.remove(position);
                recyclerViewAdapter.notifyDataSetChanged();
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Note Removed", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}