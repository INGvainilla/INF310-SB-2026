/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author User
 */

public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    @Override
    public void insertar(T datoAInsertar) {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("Los datos no pueden ser nulos");
        }
        super.raiz = insertar(datoAInsertar, raiz);
    }

    private NodoBinario<T> insertar(T datoAInsertar, NodoBinario<T> nodoActual) {

        if (esNodoVacio(nodoActual)) {
            return crearNodo(datoAInsertar);
        }
        if (datoAInsertar.compareTo(nodoActual.dato) < 0) {
            nodoActual.hijoIzquierdo = insertar(datoAInsertar, nodoActual.hijoIzquierdo);
        } else if (datoAInsertar.compareTo(nodoActual.dato) > 0) {
            nodoActual.hijoDerecho = insertar(datoAInsertar, nodoActual.hijoDerecho);
        } else {
            throw new IllegalArgumentException("no puede haber repetidos0");
        }
        return balancear(nodoActual);
    }

    private NodoBinario<T> balancear(NodoBinario<T> nodoProblema) {
        int altIzq = alturaHijo(nodoProblema.hijoIzquierdo);
        int altDer = alturaHijo(nodoProblema.hijoDerecho);
        int diferenciaAltura = altIzq - altDer;
        if (diferenciaAltura > 1) {
            NodoBinario<T> nodoHijoDeNodoProblema = nodoProblema.hijoIzquierdo;
            altIzq = alturaHijo(nodoHijoDeNodoProblema.hijoIzquierdo);
            altDer = alturaHijo(nodoHijoDeNodoProblema.hijoDerecho);
            if (altDer > altIzq) {
                return rotacionDobleDerecha(nodoProblema);
            } else {
                return rotacionSimpleDerecha(nodoProblema);
            }

        } else if (diferenciaAltura < -1) {
            NodoBinario<T> nodoHijoDeNodoProblema = nodoProblema.hijoDerecho;
            altIzq = alturaHijo(nodoHijoDeNodoProblema.hijoIzquierdo);
            altDer = alturaHijo(nodoHijoDeNodoProblema.hijoDerecho);
            if (altIzq > altDer) {
                return rotacionDobleIzquierda(nodoProblema);
            } else {
                return rotacionSimpleIzquierda(nodoProblema);
            }
        }
        return nodoProblema;

    }
    private NodoBinario<T> rotacionSimpleDerecha(NodoBinario<T> nodoActual){
       NodoBinario<T> nodoARetornar= nodoActual.hijoIzquierdo;
       nodoActual.hijoIzquierdo= nodoARetornar.hijoDerecho;
       nodoARetornar.hijoDerecho= nodoActual;
       return nodoARetornar;
    }
    private NodoBinario<T> rotacionSimpleIzquierda(NodoBinario<T> nodoActual){
        NodoBinario<T> nodoARetornar= nodoActual.hijoDerecho;
        nodoActual.hijoDerecho= nodoARetornar.hijoIzquierdo;
        nodoARetornar.hijoIzquierdo= nodoActual;
        return nodoARetornar;
    }

    private NodoBinario<T> rotacionDobleDerecha(NodoBinario<T> nodoActual){ // RSI + RSD
        nodoActual.hijoIzquierdo=rotacionSimpleIzquierda(nodoActual.hijoIzquierdo); // primero el hago plano el palo(<) --> (/)
        return rotacionSimpleDerecha(nodoActual); // balanceo el palo ya recto (/) --> (‸)
    }
    private NodoBinario<T> rotacionDobleIzquierda(NodoBinario<T> nodoActual){
        nodoActual.hijoDerecho= rotacionSimpleDerecha(nodoActual.hijoDerecho);
        return rotacionSimpleIzquierda(nodoActual);
    }

    private int alturaHijo(NodoBinario<T> nodoACalcular) {
        if (esNodoVacio(nodoACalcular)) {
            return 0;
        } else {
            return Math.max(alturaHijo(nodoACalcular.hijoIzquierdo), alturaHijo(nodoACalcular.hijoDerecho)) + 1;
        }
    }

    @Override
    public T buscar(T datoABuscar) {
        return super.buscar(datoABuscar);
    }

    @Override
    public void eliminar(T datoAEliminar) {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("el dato no puede ser nulo");
        }
        super.raiz = eliminar(raiz, datoAEliminar);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T datoAEliminar) {
        if (super.esNodoVacio(nodoActual)) {
            throw new IllegalArgumentException("El dato no existe en el arbol ");
        }

        if (datoAEliminar.compareTo(nodoActual.dato) < 0) {
            NodoBinario<T> nodoNuevo = eliminar(nodoActual.hijoIzquierdo, datoAEliminar);
            nodoActual.hijoIzquierdo = nodoNuevo;
            return balancear(nodoActual);
        }
        if (datoAEliminar.compareTo(nodoActual.dato) > 0) {
            NodoBinario<T> nodoNuevo = eliminar(nodoActual.hijoDerecho, datoAEliminar);
            nodoActual.hijoDerecho = nodoNuevo;
            return balancear(nodoActual);
        }

        // caso donde tiene 2 hijos
        // ---caso1
        if (esNodoHoja(nodoActual)) {
            return null;
        }
        // ---caso2
        if (!esNodoVacio(nodoActual.hijoDerecho) && esNodoVacio(nodoActual.hijoIzquierdo)) {
            return nodoActual.hijoDerecho;
        }
        if (esNodoVacio(nodoActual.hijoDerecho) && !esNodoVacio(nodoActual.hijoIzquierdo)) {
            return nodoActual.hijoIzquierdo;
        }

        // ---caso 3
        T datoSucesor = obtenerDatoSuccesorInOrden(nodoActual.hijoDerecho);
        NodoBinario<T> nodoNuevo = eliminar(nodoActual.hijoDerecho, datoSucesor);
        nodoActual.hijoDerecho = nodoNuevo;
        nodoActual.dato = datoSucesor;
        return balancear(nodoActual);
    }

    // Implementar un método que verifique si el árbol actual cumple estrictamente con las propiedades de un AVL
    //  (es un árbol de búsqueda binario válido y la diferencia de altura entre el subárbol izquierdo y derecho
    //  de cualquier nodo es como máximo 1).
    public boolean estaArbolEnAVL(){
        if (esArbolVacio()){
            return true;
        }
        return  estaArbolEnAVL(raiz);
    }
    private boolean estaArbolEnAVL(NodoBinario<T> nodoActual){
        if (esNodoVacio(nodoActual)){
            return true;
        }
        int diferencia = Math.abs(alturaHijo(nodoActual.hijoIzquierdo)-alturaHijo(nodoActual.hijoDerecho));
        if (diferencia>1){
            return false;
        }
        return estaArbolEnAVL(nodoActual.hijoIzquierdo) && estaArbolEnAVL(nodoActual.hijoDerecho);
    }





}
