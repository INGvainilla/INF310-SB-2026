/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author User
 * @param <T>
 */
public class BFS_P <T extends Comparable<T>> extends RecorridoGrafoP<T> {
    //recorrido por amplitud usando una cola
    public BFS_P(GrafoPesado<T> unGrafo , T verticeDePartida){
        super(unGrafo,verticeDePartida); // se contruye todo en el abstracto
    }
    @Override
    public void ejecutarRecorrido(T verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        Queue<T> colaDeVertices = new LinkedList<>();
        colaDeVertices.offer(verticeEnTurno); // meto el vertice donde voy a inciar el recorrido
        int posicionVerticeEnTurno = elGrafo.getNumeroDeVertice(verticeEnTurno);
        marcados.marcarVertice(posicionVerticeEnTurno); //marco el primer vertice
        do{
            T verticeActual = colaDeVertices.poll();
            int posicionDelVertice = elGrafo.getNumeroDeVertice(verticeActual);
            recorrido.add(verticeActual); // lo visito
            Iterable<T> adyacenciasDelVerticeActual= elGrafo.adyacentesDelVertice(verticeActual); //saco sus adyacentes
            //del vertice q toy analizando
            for (T adyacenteActual:adyacenciasDelVerticeActual){ // recorro la lista de sus adyacencias
                int posicionDeAdyacenteActual = elGrafo.getNumeroDeVertice(adyacenteActual);
                if (!marcados.estaVerticeMarcado(posicionDeAdyacenteActual)){ // verifico si no esta marcado para meterlo a la cola
                    colaDeVertices.offer(adyacenteActual);
                    marcados.marcarVertice(posicionDeAdyacenteActual);
                }
            }
        }while (!colaDeVertices.isEmpty());
    }
}