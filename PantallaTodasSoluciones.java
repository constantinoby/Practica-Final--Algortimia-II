package com.example.paraulogiccb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

/**
 * Clase de la nueva ventana dónde se van a representar las soluciones de las letras dadas
 */
public class PantallaTodasSoluciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_todas_soluciones);


        Intent intent = getIntent () ;
        //Cogemos el texto extra pasado por el intent y lo asignamos a un string
        String soluciones = intent.getStringExtra ( MainActivity.EXTRA_MESSAGE ) ;
        poneTexto(soluciones);
    }

    /**
     * Metodo que coge el TextView de la ventana de soluciones y settea el string pasado por parametro
     * @param s
     */
    private void poneTexto(String s){
        TextView label = (TextView) findViewById(R.id.textoSoluciones);
        //Setteamos el texto con formato html para asi poder representar los tutis de color rojo
        label.setText(Html.fromHtml(s),TextView.BufferType.SPANNABLE);
    }
}