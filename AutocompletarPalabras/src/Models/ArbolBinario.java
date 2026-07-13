/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author User
 * @param <T>
 */
public class ArbolBinario<T extends Comparable<T>> extends ArbolBinarioBase<T> implements IArbol<T> {
    
    @Override
    public void imprimir(){
        System.out.println(toString());
    }
    @Override
    public void vaciar() {
        this.raiz = NODO_VACIO;
    }
    // ─────────────────────────────────────────────────────────────────────────
    // CREAR NODO
    // ─────────────────────────────────────────────────────────────────────────
    protected NodoBinario<T> crearNodo(T dato) {
        return new NodoBinario<>(dato);
    }

    @Override
    public boolean esArbolVacio() {
        return this.esNodoVacio(this.raiz);
    }

    @Override
    public int size() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoBinario<T> nodoActual) {
        if (esNodoVacio(nodoActual)) return 0;
        return 1 + contarNodos(nodoActual.hijoIzquierdo) + contarNodos(nodoActual.hijoDerecho);
    }

    @Override
    public int altura() {
        return calcularAlturaRecursivo(raiz);
    }

    private int calcularAlturaRecursivo(NodoBinario<T> nodoActual) {
        if (esNodoVacio(nodoActual)) return 0;
        int alturaIzquierda = calcularAlturaRecursivo(nodoActual.hijoIzquierdo);
        int alturaDerecha = calcularAlturaRecursivo(nodoActual.hijoDerecho);
        return 1 + Math.max(alturaIzquierda, alturaDerecha);
    }

    @Override
    public int nivel() {
        return 0;
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            do {
                NodoBinario<T> nodoEnTurno = pilaDeNodos.pop();
                recorrido.add(nodoEnTurno.dato);
                if (!esNodoVacio(nodoEnTurno.hijoDerecho)) {
                    pilaDeNodos.push(nodoEnTurno.hijoDerecho);
                }
                if (!esNodoVacio(nodoEnTurno.hijoIzquierdo)) {
                    pilaDeNodos.push(nodoEnTurno.hijoIzquierdo);
                }
            } while (!pilaDeNodos.empty());
        }
        return recorrido;
    }

    private void apilandoNodosEnInOrden(Stack<NodoBinario<T>> pilaDeNodos) {
        NodoBinario<T> elNodo = pilaDeNodos.peek();
        while (!esNodoVacio(elNodo.hijoIzquierdo)) {
            elNodo = elNodo.hijoIzquierdo;
            pilaDeNodos.push(elNodo);
        }
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            NodoBinario<T> nodoEnTurno;
            apilandoNodosEnInOrden(pilaDeNodos);
            do {
                nodoEnTurno = pilaDeNodos.pop();
                recorrido.add(nodoEnTurno.dato);
                if (!esNodoVacio(nodoEnTurno.hijoDerecho)) {
                    pilaDeNodos.push(nodoEnTurno.hijoDerecho);
                    apilandoNodosEnInOrden(pilaDeNodos);
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return recorrido;
    }

    private void apilandoNodosEnPostOrden(Stack<NodoBinario<T>> pilaDeNodos) {
        NodoBinario<T> elNodo = pilaDeNodos.peek();
        while (!esNodoHoja(elNodo)) {
            if (!esNodoVacio(elNodo.hijoIzquierdo)) {
                elNodo = elNodo.hijoIzquierdo;
            } else {
                elNodo = elNodo.hijoDerecho;
            }
            pilaDeNodos.push(elNodo);
        }
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            NodoBinario<T> nodoEnTurno = null;
            apilandoNodosEnPostOrden(pilaDeNodos);
            do {
                NodoBinario<T> nodoAComparar = pilaDeNodos.peek();
                if (!esNodoVacio(nodoAComparar.hijoDerecho) && (nodoAComparar.hijoDerecho!=nodoEnTurno)) {
                    pilaDeNodos.push(nodoAComparar.hijoDerecho);
                    apilandoNodosEnPostOrden(pilaDeNodos);
                }
                nodoEnTurno = pilaDeNodos.pop();
                recorrido.add(nodoEnTurno.dato);
            } while (!pilaDeNodos.isEmpty());
        }
        return recorrido;
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(raiz);
            do {
                NodoBinario<T> nodoEnTurno = colaDeNodos.poll();
                recorrido.add(nodoEnTurno.dato);
                if (!esNodoVacio(nodoEnTurno.hijoIzquierdo)) {
                    colaDeNodos.offer(nodoEnTurno.hijoIzquierdo);
                }
                if (!esNodoVacio(nodoEnTurno.hijoDerecho)) {
                    colaDeNodos.offer(nodoEnTurno.hijoDerecho);
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // INSERTAR
    // ─────────────────────────────────────────────────────────────────────────

    public void insertar(T nuevoDato) {
        NodoBinario<T> nuevoNodo = new NodoBinario<>(nuevoDato);
        if (esArbolVacio()) {
            raiz = nuevoNodo;
        } else {
            insertarEnSubarbol(raiz, nuevoNodo);
        }
    }

    private void insertarEnSubarbol(NodoBinario<T> nodoActual, NodoBinario<T> nuevoNodo) {
        int comparacion = nuevoNodo.dato.compareTo(nodoActual.dato);

        if (comparacion < 0) {
            // El nuevo dato es MENOR → va a la izquierda
            if (esNodoVacio(nodoActual.hijoIzquierdo)) {
                nodoActual.hijoIzquierdo = nuevoNodo;
            } else {
                insertarEnSubarbol(nodoActual.hijoIzquierdo, nuevoNodo);
            }
        } else if (comparacion > 0) {
            // El nuevo dato es MAYOR → va a la derecha
            if (esNodoVacio(nodoActual.hijoDerecho)) {
                nodoActual.hijoDerecho = nuevoNodo;
            } else {
                insertarEnSubarbol(nodoActual.hijoDerecho, nuevoNodo);
            }
        }
        // Si comparacion == 0, el dato ya existe → no se inserta (no se permiten
        // duplicados)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // BUSCAR
    // ─────────────────────────────────────────────────────────────────────────

    public T buscar(T datoABuscar) {
        NodoBinario<T> nodoEncontrado = buscarNodo(raiz, datoABuscar);
        return esNodoVacio(nodoEncontrado) ? null : nodoEncontrado.dato;
    }

    private NodoBinario<T> buscarNodo(NodoBinario<T> nodoActual, T datoABuscar) {
        if (esNodoVacio(nodoActual))
            return NODO_VACIO;

        int comparacion = datoABuscar.compareTo(nodoActual.dato);

        if (comparacion == 0) {
            return nodoActual; // ¡Encontrado!
        } else if (comparacion < 0) {
            return buscarNodo(nodoActual.hijoIzquierdo, datoABuscar); // Buscar a la izquierda
        } else {
            return buscarNodo(nodoActual.hijoDerecho, datoABuscar); // Buscar a la derecha
        }
    }

    public boolean contiene(T dato) {
        return !esNodoVacio(buscarNodo(raiz, dato));
    }

    public int buscarPorNivel(T dato) {
        return 0;
    }

    public int cantidadDeNodosPorNivel(T nivel) {
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SIZE
    // ─────────────────────────────────────────────────────────────────────────

    public int sizeIterativo(){ // version en recorrido por niveles
        if (esArbolVacio()){
            return 0;
        } else {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(raiz);
            int size = 0;
            do{
                NodoBinario<T> nodoEnTurno = colaDeNodos.poll();
                size++;
                if(!esNodoVacio(nodoEnTurno.hijoIzquierdo)) {
                    colaDeNodos.offer(nodoEnTurno.hijoIzquierdo);
                }
                if (!esNodoVacio(nodoEnTurno.hijoDerecho)) {
                    colaDeNodos.offer(nodoEnTurno.hijoDerecho);
                }
            }while (!colaDeNodos.isEmpty());
            return size;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ALTURA
    // ─────────────────────────────────────────────────────────────────────────

    public int alturaIterativa() { // version en recorrido por niveles
        if (esArbolVacio()) {
            return 0;
        } else {
            int altura = 0;
            int guardandoHijos = 1;
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(raiz);
            NodoBinario<T> elNodo;
            do {
                int cantidadDeNodos = guardandoHijos;
                guardandoHijos = 0;
                while (cantidadDeNodos > 0) {
                    elNodo = colaDeNodos.poll();
                    if (!esNodoVacio(elNodo.hijoIzquierdo)) {
                        colaDeNodos.offer(elNodo.hijoIzquierdo);
                        guardandoHijos++;
                    }
                    if (!esNodoVacio(elNodo.hijoDerecho)) {
                        colaDeNodos.offer(elNodo.hijoDerecho);
                        guardandoHijos++;
                    }
                    cantidadDeNodos--;
                }
                altura++;
            } while (!colaDeNodos.isEmpty());
            return altura;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // NIVEL
    // ─────────────────────────────────────────────────────────────────────────
    public int nivelIteraritvo(){ // version en recorrido por niveles
        if (esArbolVacio()){
            return 0;
        } else {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(raiz);
            NodoBinario <T> nodoEnTurno;
            int nivel = 0;
            int cantidadDeNodos;
            int hijos = 1;
            do {
                cantidadDeNodos = hijos;
                hijos = 0;
                while (cantidadDeNodos>0) {
                    nodoEnTurno = colaDeNodos.poll();
                    if (!esNodoVacio(nodoEnTurno.hijoIzquierdo)) {
                        colaDeNodos.offer(nodoEnTurno.hijoIzquierdo);
                        hijos++;
                    }
                    if (!esNodoVacio(nodoEnTurno.hijoDerecho)) {
                        colaDeNodos.offer(nodoEnTurno.hijoDerecho);
                        hijos++;
                    }
                    cantidadDeNodos--;
                }
                if (!colaDeNodos.isEmpty()) {
                    nivel++;
                }
            } while (!colaDeNodos.isEmpty());
            return nivel;
        }
    }
    public void espejo(){
        if (esArbolVacio()){
            return;
        }
        espejo(raiz);
    }
    private void espejo(NodoBinario<T> nodoEnTurno){
        if (esNodoHoja(nodoEnTurno) || esNodoVacio(nodoEnTurno)){
            return ;
        }

        NodoBinario<T> nodoAnteriorIzq = nodoEnTurno.hijoIzquierdo;
        nodoEnTurno.hijoIzquierdo = nodoEnTurno.hijoDerecho;
        nodoEnTurno.hijoDerecho = nodoAnteriorIzq;

        espejo(nodoEnTurno.hijoIzquierdo);
        espejo(nodoEnTurno.hijoDerecho);

    }

}
