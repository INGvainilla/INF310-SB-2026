/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 * @param <T>
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> {

    public ArbolBinarioBusqueda() {
        super();
    }

    public void insertar(T datoAInsertar) {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("Los datos no pueden ser nulos");
        }
        if (super.esArbolVacio()) {
            super.raiz = super.crearNodo(datoAInsertar);
            return;
        }
        NodoBinario<T> nodoAnterior = super.NODO_VACIO;
        NodoBinario<T> nodoActual = super.raiz;

        do {
            T datoDelDatoActual = nodoActual.dato;
            nodoAnterior = nodoActual;
            if (datoAInsertar.compareTo(datoDelDatoActual) < 0) {
                nodoActual = nodoActual.hijoIzquierdo;
            } else if (datoAInsertar.compareTo(datoDelDatoActual) > 0) {
                nodoActual = nodoActual.hijoDerecho;
            } else {
                throw new IllegalArgumentException("El arbol no permite datos repetidos");
            }
        } while (!super.esNodoVacio(nodoActual));
        NodoBinario<T> nuevoNodo = super.crearNodo(datoAInsertar);
        if (datoAInsertar.compareTo(nodoAnterior.dato) < 0) {
            nodoAnterior.hijoIzquierdo = nuevoNodo;

        } else {
            nodoAnterior.hijoDerecho = nuevoNodo;
        }
    }

    public T buscar(T datoABuscar) {
        if (datoABuscar == null) {
            throw new IllegalArgumentException("El dato a buscar no puede ser nulo");
        }
        if (super.esArbolVacio()) {
            return null;
        }

        NodoBinario<T> nodoActual = super.raiz;

        do {
            T datoDelDatoActual = nodoActual.dato;
            if (datoABuscar.compareTo(datoDelDatoActual) < 0) {
                nodoActual = nodoActual.hijoIzquierdo;
            } else if (datoABuscar.compareTo(datoDelDatoActual) > 0) {
                nodoActual = nodoActual.hijoDerecho;
            } else {
                return nodoActual.dato;
            }
        } while (!super.esNodoVacio(nodoActual));
        return null;
    }

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
            return nodoActual;
        }
        if (datoAEliminar.compareTo(nodoActual.dato) > 0) {
            NodoBinario<T> nodoNuevo = eliminar(nodoActual.hijoDerecho, datoAEliminar);
            nodoActual.hijoDerecho = nodoNuevo;
            return nodoActual;
        }

        // ---caso1 (0 hijos)
        if (esNodoHoja(nodoActual)) {
            return null;
        }
        // ---caso2 (1 hijo)
        if (!esNodoVacio(nodoActual.hijoDerecho) && esNodoVacio(nodoActual.hijoIzquierdo)) {
            return nodoActual.hijoDerecho;
        }
        if (esNodoVacio(nodoActual.hijoDerecho) && !esNodoVacio(nodoActual.hijoIzquierdo)) {
            return nodoActual.hijoIzquierdo;
        }

        // ---caso 3 (2 hijos)
        T datoSucesor = obtenerDatoSuccesorInOrden(nodoActual.hijoDerecho);
        NodoBinario<T> nodoNuevo = eliminar(nodoActual.hijoDerecho, datoSucesor);
        nodoActual.hijoDerecho = nodoNuevo;
        nodoActual.dato = datoSucesor;
        return nodoActual;
    }

    protected T obtenerDatoSuccesorInOrden(NodoBinario<T> nodoSucesor) {
        if (esNodoVacio(nodoSucesor.hijoIzquierdo)) {
            return nodoSucesor.dato;
        }
        return obtenerDatoSuccesorInOrden(nodoSucesor.hijoIzquierdo);

    }

    public int cantidadHojasDesdeNivel(int nivel) {
        if (nivel < 0) {
            throw new IllegalArgumentException("el nivel no puede ser negativo");
        }
        return cantidadHojasDesdeNivel(super.raiz, nivel);
    }

    private int cantidadHojasDesdeNivel(NodoBinario<T> nodoActual, int nivel) {
        if (esNodoVacio(nodoActual)) {
            return 0;
        }

        // Si encontramos una hoja, verificamos si está en o por debajo del nivel
        // objetivo
        if (esNodoHoja(nodoActual)) {
            return nivel <= 0 ? 1 : 0;
        }

        // Si NO es hoja, debemos seguir bajando siempre en busca de hojas,
        // sin importar el nivel en el que estemos
        return cantidadHojasDesdeNivel(nodoActual.hijoIzquierdo, nivel - 1)
                + cantidadHojasDesdeNivel(nodoActual.hijoDerecho, nivel - 1);
    }

    
    public List<T> obtenerTresMayores() {
        List<T> resultado = new ArrayList<>();
        obtenerTresMayores(super.raiz, resultado);
        return resultado;
    }

    private void obtenerTresMayores(NodoBinario<T> nodoActual, List<T> resultado) {
        // Si el nodo es vacío o ya encontramos los 3 mayores, paramos inmediatamente
        // (poda)
        if (esNodoVacio(nodoActual) || resultado.size() >= 3) {
            return;
        }

        // Recorrer primero el subárbol derecho (donde están los elementos más
        // grandes en un BST)
        obtenerTresMayores(nodoActual.hijoDerecho, resultado);

        // Procesar la raíz actual y la rama izquierda si aún no tenemos los 3
        // elementos
        if (resultado.size() < 3) {
            resultado.add(nodoActual.dato);
            //  Recorrer el subárbol izquierdo (donde están los elementos más chicos)
            obtenerTresMayores(nodoActual.hijoIzquierdo, resultado);
        }
    }

    public void reconstruirArbolBinarioXRecorridos(List<T> listaInOrden, List<T> listaPosOrden) {
        if (listaInOrden == null || listaPosOrden == null || listaInOrden.isEmpty() || listaPosOrden.isEmpty() || listaInOrden.size() != listaPosOrden.size()) {
            return;
        }
        this.raiz = reconstruirDeInYPost(listaInOrden, 0, listaInOrden.size() - 1, listaPosOrden, 0, listaPosOrden.size() - 1);
    }

    private NodoBinario<T> reconstruirDeInYPost(List<T> inOrden, int inStart, int inEnd, List<T> posOrden, int posStart, int posEnd) {
        if (inStart > inEnd || posStart > posEnd) {
            return null;
        }
        T valorRaiz = posOrden.get(posEnd);
        NodoBinario<T> nodoRaiz = crearNodo(valorRaiz);

        int indiceRaizInOrden = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrden.get(i).compareTo(valorRaiz) == 0) {
                indiceRaizInOrden = i;
                break;
            }
        }
        if (indiceRaizInOrden == -1) {
            return null;
        }

        int numElementosIzquierda = indiceRaizInOrden - inStart;

        nodoRaiz.hijoIzquierdo = reconstruirDeInYPost(inOrden, inStart, indiceRaizInOrden - 1, posOrden, posStart, posStart + numElementosIzquierda - 1);
        nodoRaiz.hijoDerecho = reconstruirDeInYPost(inOrden, indiceRaizInOrden + 1, inEnd, posOrden, posStart + numElementosIzquierda, posEnd - 1);

        return nodoRaiz;
    }
    
    
    
    //lo q se uso para el proyecto
    public List<T> buscarPorPrefijo(String prefijo) {
        List<T> resultados = new ArrayList<>();
        buscarPorPrefijo(super.raiz, prefijo.toLowerCase(), resultados);
        return resultados;
    }
    private void buscarPorPrefijo(NodoBinario<T> nodoActual, String prefijo, List<T> resultados) {
        if (esNodoVacio(nodoActual)) {
            return;
        }
        String datoActual = nodoActual.dato.toString().toLowerCase();
        //reviso si el dato actual (concepto)  empieza con el prefijo
        if (datoActual.startsWith(prefijo)) {
            // Explorar izquierda
            buscarPorPrefijo(nodoActual.hijoIzquierdo, prefijo, resultados);
            resultados.add(nodoActual.dato);
            // Explorar derecha
            buscarPorPrefijo(nodoActual.hijoDerecho, prefijo, resultados);
        }
        // a la izquierda si  el prefijo es menor que el dato actual 
        else if (prefijo.compareTo(datoActual) < 0) {
            buscarPorPrefijo(nodoActual.hijoIzquierdo, prefijo, resultados);
        }
        // a la derecha si el prefijo es mayor
        else {
            buscarPorPrefijo(nodoActual.hijoDerecho, prefijo, resultados);
        }
    }
    
}
