package com.example.paraulogiccb;

import java.util.Iterator;
import java.util.Stack;

public class BSTMapping<K extends Comparable<K>,V> {
    //Variablees globales
    private Node root;
    private int palabras;

    /**
     * Clase Pair: Se encarga de almacenar el identificador y el valor en el mismo objeto
     */
    protected class Pair{
        private K key;
        private V value;
        /**
         *  Constructor de Pair
         * @param key   Parametro que funciona como identificador
         * @param value Cantidad de veces que aparece el identificador
         */
        public Pair(K key, V value){
            this.key = key;
            this.value = value;
        }

        /**
         * Funcion que se encarga de devolver el identificador
         * @return Devuelve el identificador
         */
        public K getKey() {
            return key;
        }

        /**
         * Funcion que se encarga de devolver la cantidad de apariciones del identificador
         * @return  Devuelve las apariciones del identificador
         */
        public V getValue() {
            return value;
        }

        /**
         * Funcion que se encarga de añadir una aparicion de la key
         */
        public void addValue(V value){
            this.value = value;
        }
    }

    /**
     * Clase Node: La siguiente clase almacena información y mantiene enlaces con otros nodos
     * La siguiente clase se encargará de generar un arbol binario por lo que tendrá enlaces por la
     * derecha y izquierda
     */
    private class Node{
        private Pair datos;
        private Node nextR;
        private Node nextL;

        /**
         * Constructor de la clase Node
         * @param key   Parametro que funciona como identificador
         * @param value Cantidad de veces que aparece el identificador
         * @param nextR Enlace hacia el nodo hijo derecho del arbol
         * @param nextL Enlace hacia el nodo hijo izquierdo del arbol
         */
        public Node(K key, V value, Node nextR, Node nextL){
            datos = new Pair(key,value);
            this.nextL=nextL;
            this.nextR=nextR;
        }
    }

    /**
     * Constructor de la clase BSTMapping y se encarga de inicializar las variables root y palabras
     */
    public BSTMapping(){
        root=null;
        palabras=0;
    }

    /**
     * La siguiente funcion se encarga de verificar si hace falta añadir un elemento al arbol
     * o simplemente basta con inclementar la cantidad de apariciones de dicho elemento
     * @param key   Elemento que se desea añadir al arbol o incrementar sus apariciones
     * @param value Cantidad de veces que ha aparecido con anterioridad dicho elemento
     * @return  Devuelve la cantidad de aparaciones de dicho elemento
     */
    V put(K key, V value) {
        Node aux;
        if(value.equals(1)){
            root = add(key,root,value);
            aux=root;
            palabras++; //Aumentamos la cantidad de elementos del arbol
        }else{
            aux = get(key,root);
            aux.datos.addValue(value); //Si ya se encuentra aumentamos value
        }
        return aux.datos.getValue();
    }

    /**
     * Funcion que se encarga de añadir un elemento al arbol
     * @param key       Elemento que se desea añadir al arbol
     * @param current   Nodo en el que nos encontramos (va cambiando segun lo vamos recorriendo hasta
     *                  encontrar un hueco)
     * @return  Devuelve el nodo nuevo que contiene la informacion que se deseaba añadir
     */
    private Node add(K key, Node current,V value) {
        if (current == null) {
            return new Node(key, value,null, null);
        } else {
            if (current.datos.getKey().compareTo(key)==0) {
                return current;
            }
            if (key.compareTo(current.datos.getKey()) < 0) {
                current.nextL = add(key, current.nextL,value);
            } else {
                current.nextR = add(key, current.nextR,value);
            }
            return current;
        }
    }

    /**
     * Funcion que se encarga de comprobar que si el elemento que se esta buscando se encuentra o no
     * @param key   Elemento que deseamos buscar en el arbol
     * @return  Devuelve la cantidad de apariciones de dicho elemento, devuelno null en caso de que
     *          no exista
     */
    public V get(K key){
        Node aux = get(key,root);
        if(aux==null){
            return null;
        }else{
            return aux.datos.getValue();
        }
    }

    /**
     * Funcion que se encarga de buscar un elemento
     * @param key       Elemento que se desea buscar en el arbol
     * @param current   Nodo en el que nos encontramos (va cambiando segun lo vamos recorriendo hasta
     *                  encontrar el deseado)
     * @return  Devuelve el nodo nuevo que contiene la informacion que se deseaba encontrar
     */
    private Node get(K key,Node current){
        if (current==null) { // Si l’arbre és buit: no trobat
            return null;
        } else {
            if (current.datos.getKey().equals(key)) {// Si el node conté l’element: trobat
                return current;
            }
            // Si l’element és inferior a l’element del node:
            if (key.compareTo(current.datos.getKey()) < 0) {
                return get(key, current.nextL); // cercar al fill esquerra
            } else {
                return get(key, current.nextR); // cercar al fill dret
            }
        }
    }

    /**
     * Funcion que se encarga de eliminar un elemento del arbol (NO utilizada en la practica)
     * @param key       Elemento que se desea eliminar del arbol
     * @param current   Nodo en el que nos encontramos (va cambiando segun lo vamos recorriendo hasta
     *      *           encontrar el deseado)
     * @return  Devuelve el nodo eliminado
     */
    Node remove(K key, Node current){

        if (current==null) { // Element no trobat
            return null;
        }
        if (current.datos.key.equals(key)) { // Element trobat
            if (current.nextL == null && current.nextR == null) {
                return null;
            }
            else if (current.nextL == null && current.nextR != null) {
                return current.nextR;
            } else if (current.nextL != null && current.nextR == null) {
                return current.nextL;
            }else{
                Node plowest = current.nextR;
                Node parent = current;
                while (plowest.nextL != null) {
                    parent = plowest;
                    plowest = plowest.nextL;
                }
                plowest.nextL = current.nextL;
                if (plowest != current.nextR) {
                    parent.nextL = plowest.nextR;
                    plowest.nextR = current.nextR;
                }
                return plowest;
            }

        }
        if (key.compareTo(current.datos.key) < 0) { // Subarbre esquerra
            current.nextL = remove(key, current.nextL);
        } else {// Subarbre dret
            current.nextR = remove(key, current.nextR);
        }
        return current;
    }

    /**
     * Variable que permite saber si se encuentra vacio el arbol (NO utilizado en la practica)
     * @return Devuelve true si el arbol se encuentra vacio y devuelve false si el arbol no esta vacio
     */
    private boolean isEmpty(){
        return root==null;
    }

    /**
     * Funcion que se encarga de convertir en String los datos que hay en el arbol
     * @return Devuelve un String con los datos del arbolis
     */
    @Override
    public String toString(){
        String res = " Has trobat " + palabras + " paraules: ";
        //Inicializamos el iterador
        IteratorBTSMapping recuperar = new IteratorBTSMapping();

        //Bucle hasta recuperar todos los elementos
        while (recuperar.hasNext()){
            Pair datos = (Pair) recuperar.next();
            res+= datos.getKey() + " (" + datos.getValue()+ "),";
        }
        return res;
    }

    /**
     * La siguiente clase se encarga de recuperar los elementos del arbol utilizando el metodo
     * inordre.
     */
    private class IteratorBTSMapping implements Iterator{

        private Stack<Node> iterator;

        public IteratorBTSMapping(){
            Node p;
            iterator = new Stack();
            if (root!=null){
                p = root;
                while(p.nextL!=null){
                    iterator.push(p);
                    p=p.nextL;
                }
                iterator.push(p);
            }
        }

        @Override
        public boolean hasNext() {
            return !iterator.isEmpty();
        }

        @Override
        public Object next() {
            Node p = iterator.pop();
            Pair datos = p.datos;
            if(p.nextR!=null){
                p=p.nextR;
                while (p.nextL!=null){
                    iterator.push(p);
                    p = p.nextL;
                }
                iterator.push(p);
            }
            return datos;
        }
    }
}
