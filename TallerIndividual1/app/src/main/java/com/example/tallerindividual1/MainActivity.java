package com.example.tallerindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button jugar, borrar;
    EditText rangonumero, nintentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        jugar = findViewById(R.id.buttonJugar);
        jugar.setOnClickListener(this);
        borrar = findViewById(R.id.buttonBorrar);
        borrar.setOnClickListener(this);

        rangonumero = findViewById(R.id.editTextRangoNum);
        nintentos = findViewById(R.id.editTextNintentos);
        rangonumero.setText("");//para probar si al darle al boton de retroceder esto se ejecuta y se ponen en blanco o vuelve sin ejecutar nada
        nintentos.setText("");// y efectivamente lo hacen, cuando se le da a atras se vuelve a ejecutar esto asi que es bueno reiniciar elementos al inicio

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonJugar:
                if( !rangonumero.getText().toString().isEmpty() && !nintentos.getText().toString().isEmpty()){ // comprobante de editext no vacio para numeros y letras dependiendo del tipo de editText
                    int rangoElegido = Integer.parseInt(rangonumero.getText().toString());//Integer para enteros proque con Int es para declarar, en marcadores como para otro que no sean int
                    int intentosElegidos = Integer.parseInt(nintentos.getText().toString());
                    //Toast.makeText(this, ""+rangoElegido, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),JuegoActivity.class);
                    //paso los datos
                    i.putExtra("rango_elegido", rangoElegido);
                    i.putExtra("intentos_elegidos", intentosElegidos);
                    //luego cambio a la otra actividad
                    startActivity(i);
                }else {
                    Toast.makeText(this, "Faltan datos",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonBorrar:
                borrar();
                break;
        }
    }
    public void borrar(){
        rangonumero.setText("");
        nintentos.setText("");
    }
}