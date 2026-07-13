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
 */

public abstract class RecorridoGrafoP<T extends Comparable<T>> {
    protected GrafoPesado<T> elGrafo;
    protected ControlMarcadosP marcados;
    protected List<T> recorrido ;

    public RecorridoGrafoP(GrafoPesado<T> unGrafo , T verticePartida){
        unGrafo.validarVertice(verticePartida);
        elGrafo=unGrafo;
        marcados=new ControlMarcadosP(elGrafo.listaDeVertices.size());
        recorrido=new ArrayList<>();
        ejecutarRecorrido(verticePartida);
    }
    public abstract void ejecutarRecorrido(T verticeEnTurno );

    public Iterable<T> getRecorrido (){
        return recorrido;
    }
    public boolean seVisitoEseVertice(T vertice){
        elGrafo.validarVertice(vertice);
        int posicionDelVertice = elGrafo.getNumeroDeVertice(vertice);
        return marcados.estaVerticeMarcado(posicionDelVertice);
    }
    public boolean seVisitoTodosLosVertices(){
        return marcados.estanTodosMarcados();
    }



}

