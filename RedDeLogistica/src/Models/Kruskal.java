/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author User
 * @param <T>
 */
public class Kruskal <T extends Comparable<T>>{
    public static class Arista<T> implements Comparable<Arista<T>> {
        private T verticeOrigen;
        private T verticeDestino;
        private double peso;
        
        public Arista(T origen, T destino, double pesoI) {
            this.verticeOrigen = origen;
            this.verticeDestino = destino;
            this.peso = pesoI;
        }

        public T getVerticeOrigen() {
            return verticeOrigen;
        }

        public T getVerticeDestino() {
            return verticeDestino;
        }

        public double getPeso() {
            return peso;
        }

        @Override
        public int compareTo(Arista<T> otraArista) {
             return Double.compare(this.peso, otraArista.peso);
        }
    }

    private GrafoPesado<T> elGrafo;
    private GrafoPesado<T> grafoAuxiliar; // El arbol de expancion minima
    public Kruskal(GrafoPesado<T> unGrafo) {
        this.elGrafo = unGrafo;
        ejecutarKruskal();
    }
    private void ejecutarKruskal() {
        int n = elGrafo.cantidadDeVertice();
        
        //  Crea un nuevo grafo no dirigido Auxiliar con todos los vértices del grafo original
        this.grafoAuxiliar = new GrafoPesado<>();
        for (int i = 0; i < n; i++) {
            this.grafoAuxiliar.insertarVertice(elGrafo.getVerticePorNro(i));
        }
        //  En una estructura auxiliar A, ordena las aristas del grafo original ascendentemente por su peso
        List<Arista<T>> aristasA = obtenerTodasLasAristas();
        Collections.sort(aristasA); // Se ordenan usando el compareTo de Arista
        // Por cada valor de A
        for (Arista<T> arista : aristasA) {
            T origen = arista.getVerticeOrigen();
            T destino = arista.getVerticeDestino();
            double peso = arista.getPeso();
            // Usamos tu BFS_P en el grafoAuxiliar para saber si origen y destino ya están conectados
            BFS_P<T> bfs = new BFS_P<>(grafoAuxiliar, origen);
            
            if (bfs.seVisitoEseVertice(destino)) {
                // Prueba Insertar la arista en el grafo auxiliar
                grafoAuxiliar.insertarArista(origen, destino, peso);
                
                //  Si crea ciclo, se elimina del grafo auxiliar
                grafoAuxiliar.eliminarArista(origen, destino);
            } else {
                // Si no crea ciclo, se queda insertada en el grafo auxiliar
                grafoAuxiliar.insertarArista(origen, destino, peso);
            }
        }
    }
    // Método auxiliar para obtener todas las aristas únicas del grafo original
    private List<Arista<T>> obtenerTodasLasAristas() {
        List<Arista<T>> lista = new ArrayList<>();
        for (int i = 0; i < elGrafo.cantidadDeVertice(); i++) {
            T origen = elGrafo.getVerticePorNro(i);
            for (T destino : elGrafo.adyacentesDelVertice(origen)) {
                int posOrigen = elGrafo.getNumeroDeVertice(origen);
                int posDestino = elGrafo.getNumeroDeVertice(destino);
                
                // Para evitar duplicados en un grafo no dirigid
                if (posOrigen < posDestino) {
                    double peso = elGrafo.pesoEntreDosVertices(origen, destino);
                    lista.add(new Arista<>(origen, destino, peso));
                }
            }
        }
        return lista;
    }
    // Getter para obtener el grafo resultante del árbol de expansión mínima
    public GrafoPesado<T> getGrafoAuxiliar() {
        return grafoAuxiliar;
    }
    // Calcula el costo total sumando los pesos de las aristas del grafoAuxiliar
    public double getCostoTotal() {
        double costo = 0;
        for (int i = 0; i < grafoAuxiliar.cantidadDeVertice(); i++) {
            T origen = grafoAuxiliar.getVerticePorNro(i);
            for (T destino : grafoAuxiliar.adyacentesDelVertice(origen)) {
                int posOrigen = grafoAuxiliar.getNumeroDeVertice(origen);
                int posDestino = grafoAuxiliar.getNumeroDeVertice(destino);
                if (posOrigen < posDestino) {
                    costo += grafoAuxiliar.pesoEntreDosVertices(origen, destino);
                }
            }
        }
        return costo;
    }
}
   
