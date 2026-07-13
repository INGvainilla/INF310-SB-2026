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
public class GrafoPesado<T extends Comparable<T>> {

    protected List<T> listaDeVertices;
    protected List<List<AdyacenteConPeso>> listaDeAdyacencias;
    protected static final int NRO_VERTICE_INVALIDO = -1;

    public GrafoPesado() {
        listaDeAdyacencias = new ArrayList<>();
        listaDeVertices = new ArrayList<>();
    }

    public GrafoPesado(Iterable<T> vertices) {
        this();
        for (T unVertice : vertices) {
            insertarVertice(unVertice);
        }
    }

    protected void validarVertice(T unVertice) {
        if (unVertice == null) {
            throw new IllegalArgumentException("El Vertice no puede ser nulo");
        }
        if (getNumeroDeVertice(unVertice) == NRO_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("Vertice no encontrado");
        }
    }

    public boolean existeVertice(T unVertice) {
        if (unVertice == null) {
            throw new IllegalArgumentException("Vertice no puede ser nulo");
        }
        return getNumeroDeVertice(unVertice) != NRO_VERTICE_INVALIDO;
    }

    public int getNumeroDeVertice(T unVertice) {
        for (int i = 0; i < listaDeVertices.size(); i++) {
            T verticeEnTurno = listaDeVertices.get(i);
            if (verticeEnTurno.compareTo(unVertice) == 0) {
                return i;
            }
        }
        return NRO_VERTICE_INVALIDO;
    }

    public void insertarVertice(T unVertice) {
        if (existeVertice(unVertice)) {
            throw new IllegalArgumentException("Vertice ya existe en el grafo");
        }
        listaDeVertices.add(unVertice);
        List<AdyacenteConPeso> listaDeAdyacenciasDelVertice = new ArrayList<>();
        listaDeAdyacencias.add(listaDeAdyacenciasDelVertice);
    }

    public boolean existeAdyacencia(T verticeOrigen, T verticeDestino) {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        int nroVerticeOrigen = getNumeroDeVertice(verticeOrigen);
        int nroVerticeDestino = getNumeroDeVertice(verticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = listaDeAdyacencias.get(nroVerticeOrigen);

        // Buscamos usando un objeto temporal, funcionará gracias al método equals() que
        // sobrescribiste
        return adyacentesDelOrigen.contains(new AdyacenteConPeso(nroVerticeDestino));
    }

    // Se añadió el parámetro 'peso' ya que es un grafo pesado
    public void insertarArista(T verticeOrigen, T verticeDestino, double peso) {
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new IllegalArgumentException("Ya existe arista en el grafo");
        }
        int nroVerticeOrigen = getNumeroDeVertice(verticeOrigen);
        int nroVerticeDestino = getNumeroDeVertice(verticeDestino);

        List<AdyacenteConPeso> listaDeAdyacenciaOrigen = listaDeAdyacencias.get(nroVerticeOrigen);
        listaDeAdyacenciaOrigen.add(new AdyacenteConPeso(nroVerticeDestino, peso));
        Collections.sort(listaDeAdyacenciaOrigen);

        if (nroVerticeOrigen != nroVerticeDestino) {
            List<AdyacenteConPeso> listaDeAdyacenciaDestino = listaDeAdyacencias.get(nroVerticeDestino);
            listaDeAdyacenciaDestino.add(new AdyacenteConPeso(nroVerticeOrigen, peso));
            Collections.sort(listaDeAdyacenciaDestino);
        }
    }

    public int cantidadDeVertice() {
        return listaDeVertices.size();
    }

    public int cantidadDeAdyacencias() {
        int cantidadDeAristasTotal = 0;
        for (List<AdyacenteConPeso> listaDeAdyacenciaActual : listaDeAdyacencias) {
            cantidadDeAristasTotal += listaDeAdyacenciaActual.size();
        }
        return cantidadDeAristasTotal / 2; // Asumiendo que es un grafo no dirigido
    }

    public void eliminarVertice(T verticeAEliminar) {
        validarVertice(verticeAEliminar);

        int nroDeVerticeAEliminar = getNumeroDeVertice(verticeAEliminar);
        listaDeVertices.remove(nroDeVerticeAEliminar);
        listaDeAdyacencias.remove(nroDeVerticeAEliminar);

        AdyacenteConPeso adyacenciaAEliminar = new AdyacenteConPeso(nroDeVerticeAEliminar);

        for (List<AdyacenteConPeso> adyacentesDeUnVertice : listaDeAdyacencias) {
            adyacentesDeUnVertice.remove(adyacenciaAEliminar);

            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                AdyacenteConPeso adyacencia = adyacentesDeUnVertice.get(i);
                // Extraemos el índice para comparar
                if (adyacencia.getIndiceVertice() > nroDeVerticeAEliminar) {
                    // Creamos un nuevo objeto con el índice actualizado (n-1) conservando su peso
                    // original
                    AdyacenteConPeso nuevaAdyacencia = new AdyacenteConPeso(adyacencia.getIndiceVertice() - 1,
                            adyacencia.getPeso());
                    adyacentesDeUnVertice.set(i, nuevaAdyacencia);
                }
            }
        }
    }

    public void eliminarArista(T verticeOrigen, T verticeDestino) {
        if (!existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new IllegalArgumentException("No existe adyacencia");
        }
        int nroDeVerticeDeOrigen = getNumeroDeVertice(verticeOrigen);
        int nroDeVerticeDeDestino = getNumeroDeVertice(verticeDestino);

        List<AdyacenteConPeso> listaDeAdyacenciasOrigen = listaDeAdyacencias.get(nroDeVerticeDeOrigen);
        listaDeAdyacenciasOrigen.remove(new AdyacenteConPeso(nroDeVerticeDeDestino));

        List<AdyacenteConPeso> listaDeAdyacenciasDestino = listaDeAdyacencias.get(nroDeVerticeDeDestino);
        listaDeAdyacenciasDestino.remove(new AdyacenteConPeso(nroDeVerticeDeOrigen));
    }

    public Iterable<T> getVertices() {
        return new ArrayList<>(listaDeVertices);
    }

    public Iterable<T> adyacentesDelVertice(T elVertice) {
        validarVertice(elVertice);
        List<T> adyacentesDelVertice = new ArrayList<>();
        int nroVertice = getNumeroDeVertice(elVertice);

        List<AdyacenteConPeso> adyacentesVerticeActual = listaDeAdyacencias.get(nroVertice);
        // Iteramos sobre los objetos, no sobre Integers
        for (AdyacenteConPeso adyacencia : adyacentesVerticeActual) {
            adyacentesDelVertice.add(listaDeVertices.get(adyacencia.getIndiceVertice()));
        }
        return adyacentesDelVertice;
    }

    public T getVerticePorNro(int nroVertice) {
        if (nroVertice < 0 || nroVertice >= listaDeVertices.size()) {
            throw new IllegalArgumentException("No existe vertice ");
        }
        return listaDeVertices.get(nroVertice);
    }

    public int gradoDelVertice(T unVertice) {
        validarVertice(unVertice);
        int nroVertice = getNumeroDeVertice(unVertice);
        return listaDeAdyacencias.get(nroVertice).size();
    }

    public double pesoEntreDosVertices(T verticeOrigen, T verticeDestino) {
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        if (this.existeAdyacencia(verticeOrigen, verticeDestino)) {
            int posVerticeOrigen = this.getNumeroDeVertice(verticeOrigen);
            int posVerticeDestino = getNumeroDeVertice(verticeDestino);
            List<AdyacenteConPeso> adyacencias = listaDeAdyacencias.get(posVerticeDestino); // saco las adyacencias y
                                                                                            // busco si hay esta
            for (AdyacenteConPeso adyacenteActual : adyacencias) {
                if (adyacenteActual.getIndiceVertice() == posVerticeOrigen) {
                    return adyacenteActual.getPeso();
                }
            }
        }
        return Double.MAX_VALUE; // esto es para el algortimo de Dijsktra
    }
}