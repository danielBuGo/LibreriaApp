package com.example.libreria;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.libreria.dataBase.DbRegistros;
import com.example.libreria.dataBase.dbHelper;

public class MainActivity extends AppCompatActivity {
    Button guardar, buscar, borrar, editar;
    EditText txtId, txtNombre, txtCosto, txtDispo;
    TextView mensaje;
    DbLibros oLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId = findViewById(R.id.etId);
        txtNombre = findViewById(R.id.etName);
        txtCosto = findViewById(R.id.etCoste);
        txtDispo = findViewById(R.id.etAvalabe);
        mensaje = findViewById(R.id.infoText);
        guardar = findViewById(R.id.btnGuardar);
        buscar = findViewById(R.id.btnBuscar);
        borrar = findViewById(R.id.btnBorrar);
        editar = findViewById(R.id.btnEditar);
        oLibros = new DbLibros(this, "dbLibros", null, 1);
            // Guardar Libro
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mId = txtId.getText().toString();
                String mLibro = txtNombre.getText().toString();
                String mCosto = txtCosto.getText().toString();
                String mDisponible = txtDispo.getText().toString();

                if (!mId.isEmpty() && !mLibro.isEmpty()
                        && !mCosto.isEmpty() && !mDisponible.isEmpty()){
                    if(!buscarUsuario(mId)){
                        guardarUsuario(mId, mLibro, mCosto, mDisponible,true);
                    }else {
                        mensaje.setTextColor(Color.RED);
                        mensaje.setText("Usuario EXISTENTE. Inténtelo con otro");
                    }
                }else {
                    mensaje.setTextColor(Color.RED);
                    mensaje.setText("Debe ingresar todos los datos!!");
                }

            }
        });


        //Buscar Libro
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sdbUserR = oLibros.getReadableDatabase();
                String mId = txtId.getText().toString();
                String query = "SELECT etId, nombre, costo FROM user WHERE user ='" + mId + "'";
                Cursor cUser = sdbUserR.rawQuery(query, null);
                if (cUser.moveToFirst()) { //Si encuentra el usuario
                    //Asignar los datos de la tabla cursor
                    txtNombre.setText(cUser.getString(0));
                    txtCosto.setText(cUser.getString(1));
                    txtDispo.setText(cUser.getString(2));
                } else {
                    mensaje.setTextColor(Color.RED);
                    mensaje.setText("Usuario no existe");
                }
                sdbUserR.close();
            }

        });




    }
    private boolean buscarUsuario(String mId){
        SQLiteDatabase odbLibro = oLibros.getReadableDatabase();
        String query = "SELECT etId FROM etId WHERE etId ='" + mId + "'";
        // Crear un cursor para almacenar los registros que retorna el query
        Cursor cUsers = odbLibro.rawQuery(query, null);
        if (cUsers.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
    private void guardarUsuario(String mId, String mLibro, String mCosto, String mdispo, boolean mtipoinst) {
        // Instanciar objeto de SQLiteDatabase en modo escritura
        SQLiteDatabase oUsersWrite = oLibros.getWritableDatabase();
        if (mtipoinst) {
            try {
                ContentValues cvUser = new ContentValues();
                cvUser.put("Id", mId);
                cvUser.put("Nombre", mLibro);
                cvUser.put("Costo", mCosto);
                cvUser.put("Disponibilidad", mdispo);
                oUsersWrite.insert("Id", null, cvUser);
                oUsersWrite.close();
                mensaje.setTextColor(Color.GREEN);
                mensaje.setText("Usuario agregado correctamente...");
            } catch (Exception e) {
                mensaje.setTextColor(Color.RED);
                mensaje.setText("Error al guardar. Comuníquese con el administrador");
            }
        } else {
            // Actualizar un registro o user
        }
    }


}