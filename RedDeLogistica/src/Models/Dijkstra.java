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
public class Dijkstra<T extends Comparable<T>> {
    private GrafoPesado<T> elGrafo;  // necesitamos 3 estructuras
    private List<Double> costos;
    private List<Integer> predecesores;
    private ControlMarcadosP marcados;
    
    public Dijkstra(GrafoPesado<T> unGrafo) {
        this.elGrafo = unGrafo;
        int cantidadDeVertices = elGrafo.cantidadDeVertice();
        this.marcados = new ControlMarcadosP(cantidadDeVertices);
        this.costos = new ArrayList<>(Collections.nCopies(cantidadDeVertices, Double.MAX_VALUE));
        this.predecesores = new ArrayList<>(Collections.nCopies(cantidadDeVertices, -1));
    }


    public void ejecutarDijkstra(T origen, T destino) {
        elGrafo.validarVertice(origen);
        elGrafo.validarVertice(destino);

        // Limpiar el estado de ejecuciones anteriores !!!
       
        this.marcados.desmarcarTodosVertices();
        for (int i = 0; i < elGrafo.cantidadDeVertice(); i++) {
            costos.set(i, Double.MAX_VALUE);
            predecesores.set(i, -1);
        }
       
        int posOrigen = elGrafo.getNumeroDeVertice(origen);
        int posDestino = elGrafo.getNumeroDeVertice(destino);
        costos.set(posOrigen,0.0); // el origen se pone siempre en 0
        
        
        while(!marcados.estaVerticeMarcado(posDestino)){
            int posVerticeMenorCosto = encontrarPosVerticeDeMenorCosto();
    
             // Primero validamos si es -1 o si el costo es infinito )
            if(posVerticeMenorCosto == -1 || costos.get(posVerticeMenorCosto) == Double.MAX_VALUE){
             break; // No se puede llegar al destino
            }
    
             //  Marcamos el vértice (incluyendo si es el destino)
             marcados.marcarVertice(posVerticeMenorCosto);
    
             //  Si ya es el destino, nos salimos habiéndolo marcado correctamente
            if(posVerticeMenorCosto == posDestino){
              break;
            }
    
            Iterable<T> adyacentes = elGrafo.adyacentesDelVertice(elGrafo.getVerticePorNro(posVerticeMenorCosto )); // solo necesito los adyacentes ya el metodo se ocupa de sacarlos de la estructura
            T verticeV = elGrafo.getVerticePorNro(posVerticeMenorCosto);
            for (T adyacente : elGrafo.adyacentesDelVertice(verticeV)) {
                int posAdyacente = elGrafo.getNumeroDeVertice(adyacente);
                
                if (!marcados.estaVerticeMarcado(posAdyacente)) {
                    double pesoVA = elGrafo.pesoEntreDosVertices(verticeV, adyacente);
                    double nuevoCosto = costos.get(posVerticeMenorCosto) + pesoVA;
                    
                    if (costos.get(posAdyacente) > nuevoCosto) {
                        costos.set(posAdyacente, nuevoCosto);
                        predecesores.set(posAdyacente, posVerticeMenorCosto);
                    }
                }
            }
        }
    }

    // Para obtener el costo de la última ejecución
    public double getCostoMinimo(T destino) {
        int nroDestino = elGrafo.getNumeroDeVertice(destino);
        return costos.get(nroDestino);
    }

    // Para obtener la ruta de la última ejecución
    public List<T> getRutaOptima(T destino) {
        List<T> ruta = new ArrayList<>();
        int nroDestino = elGrafo.getNumeroDeVertice(destino);
        
        if (!marcados.estaVerticeMarcado(nroDestino)) {
            return ruta;
        }

        int actual = nroDestino;
        while (actual != -1) {
            ruta.add(elGrafo.getVerticePorNro(actual));
            actual = predecesores.get(actual);
        }
        Collections.reverse(ruta);
        return ruta;
    }
    public int encontrarPosVerticeDeMenorCosto(){
        Double minimo= Double.MAX_VALUE;
        int posMinimo=-1; // al inicio sabemos q no existe
        for (int i = 0; i < costos.size(); i++) {
            if(!marcados.estaVerticeMarcado(i)&& costos.get(i)< minimo){
                minimo= costos.get(i);
                posMinimo= i;
            }
        }
        return posMinimo;
    }
    
}
