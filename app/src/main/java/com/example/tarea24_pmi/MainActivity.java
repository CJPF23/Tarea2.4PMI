package com.example.tarea24_pmi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea24_pmi.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    private DataBaseHelper dbHelper;
    EditText edtNombre;
    Button  btnSalvados, btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        edtNombre=findViewById(R.id.edtNombre);
        btnLimpiar= findViewById(R.id.btnLimpiar);
        btnSalvados = findViewById(R.id.btnFirmasSalvados);
        mainBinding.btnLimpiar.setOnClickListener(view ->{
            mainBinding.signatureView.clearCanvas();
        });

        dbHelper = new DataBaseHelper(this);

        mainBinding.btnSalvarFirma.setOnClickListener(view -> {
            Bitmap signBitmap = mainBinding.signatureView.getSignatureBitmap();
            if (signBitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                signBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                // Aquí debes insertar el byte array en la base de datos utilizando SQLiteOpenHelper o algún otro método de acceso a la base de datos
                // Por ejemplo:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("signature", byteArray);
                values.put("nombre", edtNombre.getText().toString());
                long result = db.insert(DataBaseHelper.tableName, null, values);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Firma guardada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al guardar la firma", Toast.LENGTH_SHORT).show();
                }
            }

        });



        btnSalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityLista.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


}
