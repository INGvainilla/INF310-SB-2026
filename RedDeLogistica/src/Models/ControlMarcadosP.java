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
public class ControlMarcadosP {
    private List<Boolean> listaDeMarcados ;

    public ControlMarcadosP(int cantidadDeVertices){
        listaDeMarcados= new ArrayList<>();
        for (int i = 0; i < cantidadDeVertices ; i++) {
            listaDeMarcados.add(false);
        }
    }
    public void marcarVertice(int posicionDelVertice){
        if (posicionDelVertice <0){
            throw new IllegalArgumentException("No existe el vertice");
        }
        listaDeMarcados.set(posicionDelVertice,true);
    }
    public void desmarcarVertice(int posicionDelVertice){
        if (posicionDelVertice <0){
            throw new IllegalArgumentException("No existe el vertice");
        }
        listaDeMarcados.set(posicionDelVertice,false);
    }
    public void desmarcarTodosVertices(){
        for (int i = 0; i < listaDeMarcados.size(); i++) {
            listaDeMarcados.set(i,false);
        }
    }
    public boolean estaVerticeMarcado(int posicionVertice){
        return listaDeMarcados.get(posicionVertice)==true;
    }
    public boolean estanTodosMarcados(){
        for (int i = 0; i < listaDeMarcados.size(); i++) {
            if (!estaVerticeMarcado(i)){
                return false;
            }
        }
        return true;
    }
}

