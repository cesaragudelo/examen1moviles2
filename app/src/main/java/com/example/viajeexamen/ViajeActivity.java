package com.example.viajeexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ViajeActivity extends AppCompatActivity {
    EditText jetcodviaje,jetdestino,jetcanpersonas,jetvalorpersona;
    String codviaje,destino,canpersonas,valorpersona;
    Switch jetactivo;
    long resp;
    int sw;
    ClsOpenHelper admin=new ClsOpenHelper(this,"viajes.db",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viaje);
        getSupportActionBar().hide();
        jetcodviaje=findViewById(R.id.etCodViaje);
        jetdestino=findViewById(R.id.etDestino);
        jetcanpersonas=findViewById(R.id.etCanPersonas);
        jetvalorpersona=findViewById(R.id.etValorPersona);
        jetactivo=findViewById(R.id.swactivo);
        sw=0;
    }
    public void Guardar(View view){
        codviaje=jetcodviaje.getText().toString();
        destino=jetdestino.getText().toString();
        canpersonas=jetcanpersonas.getText().toString();
        valorpersona=jetvalorpersona.getText().toString();
        if(codviaje.isEmpty() || destino.isEmpty()||
                canpersonas.isEmpty() || valorpersona.isEmpty()){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            jetcodviaje.requestFocus();
        }
        else {
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("codViaje",codviaje);
            registro.put("ciudadDestino",destino);
            registro.put("cantPersonas",canpersonas);
            registro.put("valorPersona",Integer.parseInt(valorpersona));
            if (sw == 0) {
                resp = db.insert("TblViaje", null, registro);
            }
            else {
                resp = db.update("TblViaje", registro, "codViaje='" + codviaje + "'", null);
                sw=0;
            }


            if (resp > 0){
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            db.close();


        }
    }

    public void Consultar(View view){
        codviaje=jetcodviaje.getText().toString();
        if (codviaje.isEmpty()){
            sw=1;
            Toast.makeText(this, "El codigo es requerido", Toast.LENGTH_SHORT).show();
            jetcodviaje.requestFocus();
        }
        else{
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblViaje where codViaje='" + codviaje + "'",null);
            if (fila.moveToNext()){
                sw=1;
                jetdestino.setText(fila.getString(1));
                jetcanpersonas.setText(fila.getString(2));
                jetvalorpersona.setText(fila.getString(3));
                if(fila.getString(4).equals("si"))
                    jetactivo.setChecked(true);
                else
                    jetactivo.setChecked(false);
            }
            else
                Toast.makeText(this, "Viaje no registrado", Toast.LENGTH_SHORT).show();
            db.close();
        }
    }

    public void Anular(View view){
        if(sw==0){
            Toast.makeText(this, "primero debe consultar", Toast.LENGTH_SHORT).show();
            jetcodviaje.requestFocus();
        }
        else {
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("activo","no");
            resp=db.update("TblViaje",registro,"codViaje='" + codviaje + "'",null);
            if(resp > 0){
                Toast.makeText(this, "registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else {
                Toast.makeText(this, "error anulando registro", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void Limpiar_campos(){
        jetcodviaje.setText("");
        jetvalorpersona.setText("");
        jetcanpersonas.setText("");
        jetdestino.setText("");
        sw=0;
    }

}