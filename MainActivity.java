package com.example.paraulogiccb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    //Declaramos variables globales
    private int arrayBotones[];
    private String soluciones= "";
    private Boolean encontrado = false;

    private UnsortedArraySet uas;
    private BSTMapping bst;
    private TreeSet tr;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE ";

    /**
     * Metodo que crea la ventana principal al iniciar el programa
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Mientras no hayamos encontrado un set de letras con los cuales se pueda hacer un tuti
        // seguimos creando nuevos sets.
        while(!encontrado){
            //creamos un set de letras
            creaLetrasArray();
            try {
                //Se inicia el diccionario donde se inicializa el diccionario y se mirará que
                // con dicho set de letras se pueda hacer un tuti.
                InciarDiccionario();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //declaramos el array de botones
        arrayBotones = new int[] {R.id.button4Obligatoria, R.id.button1, R.id.button2, R.id.button3, R.id.button5, R.id.button6, R.id.button7};
        //Se settea el array de letras a los botones pertinentes.
        LetraBotonPrimero(arrayBotones);
        bst =new BSTMapping();
    }


    /**
     * Metodo startActivity que crea una nueva ventana al clickar el boton de soluciones "?".
     * Pasando como extra el string de soluciones posibles para el conjunto de letras que haya en el momento.
     * @param view
     */
    public void startActivity(View view) {

        Intent intent = new Intent(this, PantallaTodasSoluciones.class);
        soluciones = "";
        String s;
        Iterator it = tr.iterator();
        while(it.hasNext()){
            s = (String) it.next();
            if(isTuti(s)){
                soluciones += "<font color='red'>"+s+"</font> ";
            }else{
                soluciones += s+" ";
            }
        }
        intent.putExtra(EXTRA_MESSAGE, soluciones);
        startActivity(intent);
    }

    /**
     * La siguiente función se encargará de leer el diccionario filtrado y
     * añadir todas las palabras validas a un arbol que utilizarermos posteriormente
     * para comparar si la palabra introducida por el usuario es correcta
     * @throws IOException
     */
    public void InciarDiccionario() throws IOException {
        InputStream is = getResources().openRawResource ( R.raw.catala_filtrat ) ;
        BufferedReader r = new BufferedReader (new InputStreamReader(is ) ) ;
        tr= new TreeSet();
        String s=r.readLine();
        soluciones = "";
        int cantTuti = 0;
        Iterator it = uas.iterator();
        char central = (char) it.next();
        while(s != null){
            if (s.length()>=3 && s.contains(""+central)){
                boolean condicion = true;
                for(int i =0;i<s.length();i++){
                    if(!uas.contains(s.toCharArray()[i])){
                        condicion = false;
                        break;
                    }
                }
                if(condicion){
                    boolean tuti = isTuti(s);
                    tr.add(s);
                    if(tuti){
                        cantTuti++;
                    }
                }
            }
            s=r.readLine();

        }
        if(cantTuti>0){
            encontrado = true;
        }else{
            encontrado = false;
        }
        r.close();
    }

    public boolean isTuti(String palabra){
        Iterator i = uas.iterator();
        while(i.hasNext()){
            if(!palabra.contains(""+i.next())){
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo que crea todas las letras para los botones, no se repetirán letras
     * y se generarán 7 letras
     *
     */
    public void creaLetrasArray() {
        //declaro un objeto de la clase UnsortedArraySet
        uas = new UnsortedArraySet(7);
        //declaro un objeto Random
        Random rd = new Random();
        //declaro un int para saber las iteraciones que llevo
        int i = 0;

        //mientras el int i no llegue a 7 seguimos creando letras
        while (i != 7) {
            //hago un numero entre (0-25) y le sumo 97 para llegar a los valores de las letras minusculas

            int num = rd.nextInt(25) + 97;
            //hago el casting de int a letra
            char letra = (char) num;


            //si la letra no esta dentro la metemos
            if (!uas.contains(letra)) {
                //añado la letra al array
                uas.add(letra);
                //Añado uno a el int de iteraciones
                i++;
            }
        }
    }

    /**
     * Metodo que coge la letra de un boton pulsado y lo mete en el label de texto.
     * @param view
     */
    public void setLletra(View view) {
        //array de botones son int
        Button btn = (Button) findViewById(view.getId());

        TextView label = (TextView) findViewById(R.id.InserccionPalabra);
        //getter del texto del boton
        String s1 = btn.getText().toString();
        //getter del texto del label
        String s2 = label.getText().toString();

        //meto el texto al label
        label.setText(s2 + s1);

    }

    /**
     * Método que al clickar el boton "Suprimeix" coge el string que haya actualmente
     * le borra el ultimo carácter y settea otra vez el string modificado
     * al campo de texto de introduccion de palabras.
     * @param view
     */
    public void suprime(View view) {

        TextView label2 = (TextView) findViewById(R.id.InserccionPalabra);
        //getter del texto que hay en el campo de texto.
        String s1 = label2.getText().toString();

        //si el texto a borrar no es 0 entramos, hacemos getter del texto le borramos el último caracter
        //y volvemos a settear el string al campo de texto.
        if (s1.length() != 0) {
            s1 = label2.getText().toString();
            String s2 = s1.substring(0, s1.length() - 1);
            label2.setText(s2);
        }
    }

    /**
     * Método que mediante el iterador settea las letras a los botones.
     * @param a
     */
    public void LetraBotonPrimero(int a[]) {

        Iterator it = uas.iterator();
        int i = 0;

        while (it.hasNext()) {
            char c = (char) it.next();
            Button btn3 = (Button) findViewById(a[i]);
            btn3.setText("" + c);
            i++;
        }

    }


    /**
     * Método mezcla que mediante el iterador consigue las letras del UnsortedArraySet,
     * las letras se meten en un array, el array se mezcla y al final se representa el array en los botones.
     * @param view
     */
    public void Mezcla(View view) {
        Iterator it = uas.iterator();
        char arr[]=new char[6];
        int j=0;
        //avanzo la primera letra correspiente a la letra fija
        it.next();

        //conseguimos todos los elementos de la lista
        while(it.hasNext()) {
            char c = (char) it.next();
            arr[j] = c;
            j++;
        }

        //declaramos el random
        Random rand = new Random();

        //mediante el for vamos consiguiendo indices aleatorios y hacemos swap de los indices
        for (int i = 0; i < arr.length; i++) {
            int randomIndexToSwap = rand.nextInt(arr.length);
            char temp = arr[randomIndexToSwap];
            arr[randomIndexToSwap] = arr[i];
            arr[i] = temp;
        }

        //metemos el array de letras mezcladas en los botones
        LetraBotonCambio(arr,arrayBotones);

    }

    /**
     * Método que mete el array mezclado en los botones.
     *
     * @param a         array de caracteres mezclado
     * @param botones   array de botones que hay que introudicir sus letras
     */
    public void LetraBotonCambio(char a[],int botones[]) {
        //inicializamos j=1 para asi saltarnos el boton obligatorio, para asi no cambiar su contenido
        int j=1;

        //recorremos todas las letras y las metemos en los botones
        for (int i = 0; i < a.length ; i++) {
            Button btn4 = (Button) findViewById(botones[j]);
            btn4.setText("" + a[i]);
            j++;
        }

    }

    /**
     * Metodo que se activa al presionar el boton presionar, dicho metodo mira la palabra
     * que se ha escrito, mira que cumpla las restricciones y que el TreeSet contenga dicha palabra
     * la palabra se busca en el BSTMapping y se actualiza la cantidad de palabras encontradas.
     *
     * @param view
     */
    public void Introducir(View view){
        //declaro el iterador
        Iterator it = uas.iterator();
        //declaro un string
        String s1="";
        //cojo la letra obligatoria del UnsortedArraySet y la seteo a un char
        char c = (char) it.next();


        TextView label2 = (TextView) findViewById(R.id.InserccionPalabra);

                if(label2.getText()==null){//si se pulsa el botton y el getter me devuelve null no hago nada

                }else {// si hay algo asigno el getter a una string
                    s1 = label2.getText().toString();
                }

        //miro las restricciones, si se cumplen entro y actulizo los resultados
        if(s1.length()>2 && s1.contains((c+"").toLowerCase()) && tr.contains(s1)) {

            Integer res= (Integer) bst.get(s1);
            if(res!=null) {
                bst.put(s1, ++res);
            }else{
                bst.put(s1, 1);
            }
            //Actulizamos los resltados
            ActualizaResultados();


        }else {//si no enseño ek error que ha pasado
            error(s1,c);
        }
        //setteo el texto de introduccion de palabras para que cuando se introduzca
        // una palabra se vacie el campo y se pueda introducir otra sin necesidad de borrar nada.
        label2.setText("");
    }

    /**
     * Método que actualiza el TextView enseñando las palabras que se han encontrado.
     */
    private void ActualizaResultados(){

        TextView label3 = (TextView) findViewById(R.id.textoPalabras);
        label3.setText(bst.toString());
    }

    /**
     *
     * Método error que enseña un mensaje de error por pantalla dependiendo del error enseña un mensaje u otro.
     *
     * @param s Palabra con la que se ha producido un error
     * @param c Caracter central
     */
    private void error(String s, char c){
        CharSequence text;
        Context context = getApplicationContext () ;
        if (s.length()<3){
            text = "¡Palabra menor que 3 caracteres!";
        }else if(!(s.contains((c+"")))){
            text = "¡Palabra no contiene el caracter obligatorio!";
        }else{
            text= "¡Palabra no existente!";
        }

        int duration = Toast.LENGTH_LONG ;

        Toast toast = Toast.makeText (context,text,duration) ;
        toast.show () ;
    }

}