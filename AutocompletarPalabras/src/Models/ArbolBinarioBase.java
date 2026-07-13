/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author User
 * @param <T>
 */
public abstract class ArbolBinarioBase<T> implements IArbol<T> {

    protected static class NodoBinario<T> {
        T dato;
        NodoBinario<T> hijoIzquierdo;
        NodoBinario<T> hijoDerecho;

        public NodoBinario(T unDato) {
            dato = unDato;
        }
    }

    protected NodoBinario<T> raiz;
    protected NodoBinario<T> NODO_VACIO = null;
    protected boolean esNodoVacio(NodoBinario<T> elNodo) {
        return elNodo == NODO_VACIO;
    }

    protected boolean esNodoHoja(NodoBinario<T> elNodo) {
        return (esNodoVacio(elNodo.hijoIzquierdo) && esNodoVacio(elNodo.hijoDerecho));
    }

    public abstract void  imprimir();
    
    private void construirRepresentacionVisual(NodoBinario<T> nodoActual, StringBuilder acumuladorTexto, String indentacionActual, boolean esElUltimoHijo) {
        if (esNodoVacio(nodoActual))
            return;
        String simboloConector = esElUltimoHijo ? "└── (Derecha) " : "├── (Izquierda) ";
        acumuladorTexto.append(indentacionActual).append(simboloConector).append("[").append(nodoActual.dato).append("]").append("\n");
        String indentacionHijos = indentacionActual + (esElUltimoHijo ? "    " : "│   ");
        boolean tieneHijoIzquierdoYDerecho = !esNodoVacio(nodoActual.hijoIzquierdo) && !esNodoVacio(nodoActual.hijoDerecho);
        if (!esNodoVacio(nodoActual.hijoIzquierdo)) {
            construirRepresentacionVisual(nodoActual.hijoIzquierdo, acumuladorTexto, indentacionHijos, !tieneHijoIzquierdoYDerecho);
        }
        if (!esNodoVacio(nodoActual.hijoDerecho)) {
            construirRepresentacionVisual(nodoActual.hijoDerecho, acumuladorTexto, indentacionHijos, true);
        }
    }

    @Override
    public String toString() {
        StringBuilder acumuladorTexto = new StringBuilder();
        if (esArbolVacio()) {
            acumuladorTexto.append("(árbol vacío)");
        } else {
            acumuladorTexto.append("[").append(raiz.dato).append("]\n");
            boolean raizTieneHijoIzquierdoYDerecho = !esNodoVacio(raiz.hijoIzquierdo) && !esNodoVacio(raiz.hijoDerecho);
            if (!esNodoVacio(raiz.hijoIzquierdo)) {
                construirRepresentacionVisual(raiz.hijoIzquierdo, acumuladorTexto,"", !raizTieneHijoIzquierdoYDerecho);
            }
            if (!esNodoVacio(raiz.hijoDerecho)) {
                construirRepresentacionVisual(raiz.hijoDerecho, acumuladorTexto,"", true);
            }
        }
        return acumuladorTexto.toString();
    }
}
