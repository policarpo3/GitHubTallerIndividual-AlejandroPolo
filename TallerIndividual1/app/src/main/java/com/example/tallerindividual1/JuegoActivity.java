package com.example.tallerindividual1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import java.util.concurrent.ThreadLocalRandom;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener {

    Button probar, volver;
    int intentos, numeroGanador;
    int recuperamos_rango_elegido, recuperamos_intentos_elegidos;
    EditText numeroAprobar;
    TextView intentosMostrados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        probar = findViewById(R.id.buttonProbar);
        probar.setOnClickListener(this);
        volver = findViewById(R.id.buttonVolver);
        volver.setOnClickListener(this);

        numeroAprobar = findViewById(R.id.editTextNumPrueba);
        intentosMostrados = findViewById(R.id.textViewIntentosDisponibles);//para poder modificarlo en ejecucion, no es un simple text

        //Recuperar datos de la otra actividad, la 2 variables declaradas como global para usarlas abajo
        Bundle datos = this.getIntent().getExtras();
        recuperamos_rango_elegido = datos.getInt("rango_elegido");
        recuperamos_intentos_elegidos = datos.getInt("intentos_elegidos");
        //Bundle datos = this.getIntent().getExtras();int recuperamos_variable_integer = datos.getInt("variable_integer");
        //int recuperamos_variable_integer = getIntent().getIntExtra("variable_integer");
        // los 2 comentarios son 2 formas de recuperar los datos

        intentos=recuperamos_intentos_elegidos;
        intentosMostrados.setText(""+intentos);//para mostrarlos en pantalla la primera vez
        numeroGanador = (int) (Math.random()*recuperamos_rango_elegido)+1; //el problema es si se usa un mismo rango con 2 variables creo que da la misma, de ser el caso usar el otro metodo, y si se le agrega +1 no dará nunca el numero 0, es como los vectores, con un 5 seria de 0 a 4
        //numeroGanador = ThreadLocalRandom.current().nextInt(0, recuperamos_rango_elegido + 1); // otra forma de hacerlo con el import java.util.concurrent.ThreadLocalRandom;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonProbar:
                if( !numeroAprobar.getText().toString().isEmpty()){ // comprobante de editext no vacio para numeros y letras dependiendo del tipo de editText
                    int numeroAintentar = Integer.parseInt(numeroAprobar.getText().toString());//convertimos el numero extraido en el text a entero
                    if (numeroAintentar>recuperamos_rango_elegido) {
                        Toast.makeText(this, "El numero no debe pasar de "+recuperamos_rango_elegido,Toast.LENGTH_SHORT).show();
                    }else {
                        intentos = intentos - 1;//si se comprobó que tenia un numero ya entonces
                        intentosMostrados.setText(""+intentos);//para actualizar los intentos en pantalla
                        if (intentos <= 0 && (numeroAintentar != numeroGanador)) { // con ==0 bastaba pero por siacaso xD
                            //imprime has perdido, el numero era -
                            PonerVentanaperdedora();
                        } else {//si no es game over, es decir acabó todos los intentos y esa no era la respuesta
                            if (numeroAintentar > numeroGanador) {
                                //Toast.makeText(this, "El numero es menor",Toast.LENGTH_SHORT).show();
                                imprimirElNumeroEsMenor();
                            } else {
                                if (numeroAintentar < numeroGanador) {
                                    //Toast.makeText(this, "El numero es mayor",Toast.LENGTH_SHORT).show();
                                    imprimirElNumeroEsMayor();
                                } else {// si no es menor y no es mayor entonces es el numero buscado
                                    //has ganado
                                    PonerVentanaGanadora();//
                                }
                            }
                        }
                    }


                    //Toast.makeText(this, "el numero ganador es "+numeroGanador, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, ""+recuperamos_rango_elegido, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Ingrese un numero",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonVolver:
                volverAlInicio();
                break;
        }
    }
    public void volverAlInicio(){
        intentos = recuperamos_intentos_elegidos;
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
    public void imprimirElNumeroEsMenor(){
        Toast toast=Toast.makeText(JuegoActivity.this,"El numero es menor.",Toast.LENGTH_SHORT);
        //View view =toast.getView();//estas dos son para el fondo del mensaje, para que no sea transparente
        //view.setBackgroundColor(Color.GREEN);
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);//estas 2 para el texto
        toastMessage.setTextColor(Color.RED);
        toast.show();
    }
    public void imprimirElNumeroEsMayor(){
        Toast toast=Toast.makeText(JuegoActivity.this,"El numero es mayor.",Toast.LENGTH_SHORT);
        //View view =toast.getView();//estas dos son para el fondo del mensaje, para que no sea transparente
        //view.setBackgroundColor(Color.GREEN);
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);//estas 2 para el texto
        toastMessage.setTextColor(Color.RED);
        toast.show();
    }
    public void PonerVentanaGanadora(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("HAS GANADO")
                .setMessage("El numero era el "+numeroGanador+"\nLo has conseguido en "+(recuperamos_intentos_elegidos-intentos)+" intentos")
                //desde aqui hasta donde vuelvo a marcar se podria poner en una funcion porque las opciones y acciones son igual para ambos, y luego citarlos aqui
                .setNegativeButton("Jugar en el mismo rango", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        numeroGanador = (int) (Math.random()*recuperamos_rango_elegido)+1;//asignamos otro numero con el mismo rango
                        intentos=recuperamos_intentos_elegidos;//reseteamos los intentos
                        intentosMostrados.setText(""+intentos);//mostramos los nuevos intentos reseteados en pantalla
                        numeroAprobar.setText("");//reseteamos el ediText
                    }
                })
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverAlInicio();
                    }
                })
                .show();
                //Hasta aqui
    }
    public void PonerVentanaperdedora(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("HAS PERDIDO")
                .setMessage("El numero era el "+numeroGanador)
                .setNegativeButton("Jugar en el mismo rango", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        numeroGanador = (int) (Math.random()*recuperamos_rango_elegido)+1;//asignamos otro numero con el mismo rango
                        intentos=recuperamos_intentos_elegidos;//reseteamos los intentos
                        intentosMostrados.setText(""+intentos);//mostramos los nuevos intentos reseteados en pantalla
                        numeroAprobar.setText("");//reseteamos el ediText
                    }
                })
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverAlInicio();
                    }
                })
                .show();
        //EL boton negativo sale a la izquierda y el positivo a la derecha
        // esta aleta solo tiene dos botones, uno negativo y otro positivo, si agregas mas solo sale 1 psoitivo y 1 negativo
    }

}