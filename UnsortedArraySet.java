package com.example.paraulogiccb;

import java.util.Iterator;

public class UnsortedArraySet<E> {

    private E[] array;
    private int n;

    /**
     * Constructor de la clase UnsortedArraySet el cual se encarga de inicializar todas las variables
     * @param max   Cantidad máxima de elemento que habrá en el array
     */
    public UnsortedArraySet(int max) {
        array = (E[]) new Object[max];
        n = 0;

    }

    /**
     * Funcion que se encarga de comprobar si un elemento se encuentra ya en el array
     * @param elem  Elemento que deseabos buscar en el array
     * @return  Devuelve true si se ha encontrado el elemento y devuelve false si no se ha encontrado
     */
    public boolean contains(E elem) {
        int i=0;
        boolean existe=false;
        //miramos todos los elementos hasta no encontrar el elemento que buscamos
        while(!existe && i<n ){
            //si el elemnto pasado por parametro es igual al del array asignamos el valor
            existe=array[i].equals(elem);
            i++;
        }
        return existe;
    }

    /**
     * Funcion que se encarga de añadir un nuevo elemento en el array
     * @param elem  Elemento que deseamos añadir
     * @return  Devuelve true si se ha añadido correctamente y false en caso contrario
     */
    public boolean add(E elem) {
        //mirando si cabe y si el elemento esta en el array
        if (n<array.length && !contains(elem)) {
            array[n]=elem;
            n++;
            return true;
        }else{
            return false;
        }

    }

    /**
     * Funcion que se encarga de eliminar un elemento del array (NO utilizado en la practica)
     * @param elem  Elemento que deseamos eliminar
     * @return  Devuelve true si se ha eliminado correctamente y devuelve false en caso contrario
     */
    public boolean remove(E elem) {
        int i=0;
        boolean existe=false;
        while(!existe && i<n ){
            existe=elem.equals(array[i]);
            i++;
        }
        if (existe) {
            array[i-1]=array[n-1];
            n--;
        }
        return existe;

    }

    /**
     * Funcion que se encarga de comprobar que el array no se encuentre vacia
     * @return  Devuelve true si el array se encuentra vacia y devuelve false en caso contrario
     */
    public boolean isEmpty() {
        return n==0;
    }

    public Iterator iterator() {
        Iterator it = new IteratorUnsortedArraySet();
        return it;
    }

    private class IteratorUnsortedArraySet implements Iterator {
        private int idxIterator;
        private IteratorUnsortedArraySet() {
            idxIterator = 0;
        }
        @Override
        public boolean hasNext() {
            return idxIterator < n;
        }
        @Override
        public Object next() {
            idxIterator++;
            return array[idxIterator-1];
        }
    }

    /**
     * Funcion que se encarga de convertir los datos que utilizamos en la clase en un String
     * @return Devuelve un String con los datos utilizados
     */
    @Override
    public String toString(){
        String res = "";
        for (int i =0;i<array.length;i++){
            res+=array[i];
        }
        return res;
    }
}

