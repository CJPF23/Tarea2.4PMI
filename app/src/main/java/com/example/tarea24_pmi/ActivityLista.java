package com.example.tarea24_pmi;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea24_pmi.Models.Firmas;
import com.example.tarea24_pmi.Models.ListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityLista extends AppCompatActivity {

    ListView listView;
    List<Firmas> Firmas = new ArrayList<>();
    ListAdapter mAdapter;
    DataBaseHelper conexion;
    FloatingActionButton btnRegresar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        conexion = new DataBaseHelper(this);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Vuelve a la actividad anterior al presionar el botón
            }
        });


        listView = (ListView) findViewById(R.id.listView);
        obtenerTabla();
        mAdapter = new ListAdapter(this,Firmas);
        listView.setAdapter(mAdapter);

    }

    private void obtenerTabla() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        Firmas firmas = null;
        //Cursor de base de datos
        Cursor cursor = db.rawQuery(DataBaseHelper.SelectTable,null);

        //Recorremos el cursor
        while (cursor.moveToNext()){
            firmas = new Firmas();
            firmas.setId(cursor.getString(0));
            firmas.setNombre(cursor.getString(2));
            Firmas.add(firmas);
        }
        cursor.close();
    }


}