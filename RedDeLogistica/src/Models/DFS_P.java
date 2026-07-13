/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Stack;

/**
 *
 * @author User
 * @param <T>
 */
public class DFS_P<T extends Comparable<T>> extends RecorridoGrafoP<T> {
    public DFS_P(GrafoPesado<T> unGrafo, T verticePartida) {
        super(unGrafo, verticePartida);
    }

    @Override
    public void ejecutarRecorrido(T verticePrimero) {
        elGrafo.validarVertice(verticePrimero);
        Stack<T> pilaDeVertices = new Stack<>();
        marcados.marcarVertice(elGrafo.getNumeroDeVertice(verticePrimero));
        pilaDeVertices.push(verticePrimero);

        do {
            T verticeEnTurno = pilaDeVertices.pop();// sacamos el tope de la pila
            recorrido.add(verticeEnTurno);
            Iterable<T> adyacenciasDelVertice = elGrafo.adyacentesDelVertice(verticeEnTurno); // su adyacentes serian
            // los q vamos a meter a la pila
            for (T verticeAdyacente : adyacenciasDelVertice) {
                int posicionVerticeAdyacente = elGrafo.getNumeroDeVertice(verticeAdyacente);
                if (!marcados.estaVerticeMarcado(posicionVerticeAdyacente)) {
                    marcados.marcarVertice(posicionVerticeAdyacente);
                    pilaDeVertices.push(verticeAdyacente);
                }
            }
        } while (!pilaDeVertices.isEmpty());
    }
    // Para un grafo dirigido implementar un metodo que retorne verdadero si el el
    // grafo tiene ciclo.
    // Su método debe utilizar como base de su logica alguno de los dos recorridos
    // vistos en clases

    public boolean elGrafoTieneCiclo(T verticePrimero) {

        Stack<T> pilaDeVertices = new Stack<>();
        ControlMarcadosP analizando = new ControlMarcadosP(elGrafo.cantidadDeVertice());
        ControlMarcadosP completados = new ControlMarcadosP(elGrafo.cantidadDeVertice());
        pilaDeVertices.push(verticePrimero);

        while (!pilaDeVertices.isEmpty()){
            T  verticeEnAnalisis = pilaDeVertices.peek();
            int posicionVerticeEnAnalis= elGrafo.getNumeroDeVertice(verticeEnAnalisis);
            if (!analizando.estaVerticeMarcado(posicionVerticeEnAnalis)){
                analizando.marcarVertice(posicionVerticeEnAnalis); // el primer vertice en analisis
                Iterable<T> adyacenciasDelVerticeEnAnalisis = elGrafo.adyacentesDelVertice(verticeEnAnalisis);
                for (T verticeAdyacente:adyacenciasDelVerticeEnAnalisis){
                    int posicionVerticeAdyacente = elGrafo.getNumeroDeVertice(verticeAdyacente);
                    if (analizando.estaVerticeMarcado(posicionVerticeAdyacente)){
                        return true;  // si esta marcado quiere decir q esta en ciclo x q volvimos al punto inicial
                    }
                    if (!completados.estaVerticeMarcado(posicionVerticeAdyacente)){
                        pilaDeVertices.push(verticeAdyacente);
                    }
                }

            }else{
                pilaDeVertices.pop(); // lo saco
                analizando.desmarcarVertice(posicionVerticeEnAnalis);
                completados.marcarVertice(posicionVerticeEnAnalis);

            }

        }
        return false;
    }

    public boolean esFuertementeConexoDesdeElVertice(int posicionDelVertice){
        marcados.desmarcarTodosVertices();
        T verticePartida= elGrafo.getVerticePorNro(posicionDelVertice);
        elGrafo.validarVertice(verticePartida);
        Stack<T> pilaDeVertices = new Stack<>();
        pilaDeVertices.push(verticePartida);
        marcados.marcarVertice(posicionDelVertice);
        do{
            T verticeActual = pilaDeVertices.pop();

            Iterable<T> adyacenciasDelVerticeActual = elGrafo.adyacentesDelVertice(verticeActual);
            for (T adyacente: adyacenciasDelVerticeActual){
                int posicionAdyacente = elGrafo.getNumeroDeVertice(adyacente);
                if (!marcados.estaVerticeMarcado(posicionAdyacente)){
                    marcados.marcarVertice(posicionAdyacente);
                    pilaDeVertices.push(adyacente);
                }
            }
        }while (!pilaDeVertices.isEmpty());
        return marcados.estanTodosMarcados();
    }

    public boolean esFuertementeConexo() {
        // si desde cada vertice llego a todos osea los marco
        for (int i = 0; i < elGrafo.listaDeVertices.size(); i++) {
            if (!esFuertementeConexoDesdeElVertice(i)){
                return false;
            }
        }
        return true;
    }




}