package com.example.juan.practicas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnGuardar,btnBuscar,btnActualizar,btnBorrar;
    EditText etDni,etNombres,etEmpresa,etContrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnBorrar = (Button)findViewById(R.id.btnBorrar);

        etDni = (EditText)findViewById(R.id.etDni);
        etNombres = (EditText)findViewById(R.id.etNombres);
        etEmpresa = (EditText)findViewById(R.id.etEmpresa);
        etContrato = (EditText)findViewById(R.id.etContrato);

        final ayudaBD ayudabd = new ayudaBD(getApplicationContext());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    SQLiteDatabase db =  ayudabd.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put(ayudaBD.DatosTabla.COLUMNA_ID,etDni.getText().toString());
                    valores.put(ayudaBD.DatosTabla.COLUMN_EMPRESA,etEmpresa.getText().toString());
                    valores.put(ayudaBD.DatosTabla.COLUMNA_NOMBRES,etNombres.getText().toString());
                    valores.put(ayudaBD.DatosTabla.COLUMN_TIPOCONTRATO,etContrato.getText().toString());

                    Long IdGuardado = db.insert(ayudaBD.DatosTabla.TABLE_NAME, ayudaBD.DatosTabla.COLUMNA_ID,valores);
                    Toast.makeText(getApplicationContext(),"Se almacenó el Dni:"+IdGuardado,Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"An error occurred: '{0}'"+e,Toast.LENGTH_LONG).show();

                }

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SQLiteDatabase db = ayudabd.getReadableDatabase();
                    String[] argsel = {etDni.getText().toString()};
                    String []projection = {ayudaBD.DatosTabla.COLUMNA_NOMBRES, ayudaBD.DatosTabla.COLUMN_EMPRESA, ayudaBD.DatosTabla.COLUMN_TIPOCONTRATO};
                    Cursor c = db.query(ayudaBD.DatosTabla.TABLE_NAME, projection, ayudaBD.DatosTabla.COLUMNA_ID+"=?",argsel,null,null,null);

                    c.moveToFirst();
                    etNombres.setText(c.getString(0));
                    etEmpresa.setText(c.getString(1));
                    etContrato.setText(c.getString(2));
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"An error occurred: '{0}'"+e,Toast.LENGTH_LONG).show();
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{
                   SQLiteDatabase db =  ayudabd.getWritableDatabase();
                   ContentValues valores = new ContentValues();
                   valores.put(ayudaBD.DatosTabla.COLUMNA_ID,etDni.getText().toString());
                   valores.put(ayudaBD.DatosTabla.COLUMN_EMPRESA,etEmpresa.getText().toString());
                   valores.put(ayudaBD.DatosTabla.COLUMNA_NOMBRES,etNombres.getText().toString());
                   valores.put(ayudaBD.DatosTabla.COLUMN_TIPOCONTRATO,etContrato.getText().toString());
                   String[] argsel = {etDni.getText().toString()};
                   String Selection = ayudaBD.DatosTabla.COLUMNA_ID+"=?";

                   int count = db.update(ayudaBD.DatosTabla.TABLE_NAME,valores,Selection,argsel);
               }
               catch(Exception e)
               {
                   Toast.makeText(getApplicationContext(),"An error occurred: '{0}'"+e,Toast.LENGTH_LONG).show();
               }


            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    SQLiteDatabase db =  ayudabd.getWritableDatabase();
                    String Selection = ayudaBD.DatosTabla.COLUMNA_ID+"=?";
                    String[] argsel = {etDni.getText().toString()};
                    db.delete(ayudaBD.DatosTabla.TABLE_NAME,Selection,argsel);

                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"An error occurred: '{0}'"+e,Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
