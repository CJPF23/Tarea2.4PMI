package com.example.tarea24_pmi.Models;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tarea24_pmi.DataBaseHelper;
import com.example.tarea24_pmi.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Firmas> implements View.OnClickListener {

    private List<Firmas> Firmas;
    private Context Context;

    DataBaseHelper Conexion;
    public ListAdapter(@NonNull Context context, List<Firmas> mData) {
        super(context, R.layout.activity_signaturess, mData);
        this.Context = context;
        this.Firmas = mData;
    }

    public static class ViewHolder{
        ImageView ImgeFirma;
        TextView nombre;
    }

    @Override
    public void onClick(View v) {

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Firmas firmas = Firmas.get(position);
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(Context).inflate(R.layout.activity_signaturess,null);
        }
        ImageView imagen = view.findViewById(R.id.Imgfirma);
        TextView nombre = view.findViewById(R.id.txtNombre);


        imagen.setImageBitmap(obtenerImagen(firmas.getId()));
        nombre.setText(firmas.getNombre());
        return view;
    }

    private Bitmap obtenerImagen(String id) {
        Conexion = new DataBaseHelper(Context);
        SQLiteDatabase db = Conexion.getReadableDatabase();
        Bitmap bitmap;
        String selectQuery = "SELECT signature FROM firmas WHERE id = ?";
        // Ejecuta la consulta
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        // Verifica si se encontraron resultados
        if (cursor.moveToFirst()) {
            // Obtiene los datos de la imagen en forma de arreglo de bytes
            byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow("signature"));
            // Convierte los datos de la imagen en un objeto Bitmap
            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
        else{
            bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.limpiar);
        }
        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
        return bitmap;
    }
}
