/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author User
 */
public class Diccionario extends ArbolAVL<Concepto> {
    public Diccionario() {
        super(); // construimos el arbol
    }

    public void agregarConcepto(String laPalabra, String significadoDeLaPalabra) {
        Concepto nuevoConcepto;
        if (significadoDeLaPalabra.trim().isEmpty()) {
            nuevoConcepto = new Concepto(laPalabra.trim()); // llamamos al 2do constructor
        } else {
            nuevoConcepto = new Concepto(laPalabra.trim(), significadoDeLaPalabra.trim()); // al primer constructor
        }
        super.insertar(nuevoConcepto);

    }

    public void eliminarConcepto(String palabraAEliminar) {
        super.eliminar(new Concepto(palabraAEliminar));
    }

    public Concepto buscarCocepto(String palabraABuscar) {
        return super.buscar(new Concepto(palabraABuscar));
    }

    public List<Concepto> autoCompletar(String prefijo) {
        return super.buscarPorPrefijo(prefijo);
    }

    public int cantidadDeConceptos() {
        return super.size();
    }

    public List<Concepto> obtenerTodosLosConceptos() {
        return super.recorridoEnInOrden(); // con el inorden me devuelve ordenado en orden alfabetico
    }

    public void cargarDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Ignoramos las líneas vacías
                if (linea.trim().isEmpty()) {
                    continue;
                }

                // Separar la palabra y la definición usando el carácter "|"

                String[] partes = linea.split("\\|", 2);

                if (partes.length >= 1) {
                    String palabra = partes[0].trim();
                    String definicion = (partes.length == 2) ? partes[1].trim() : "";

                    if (!palabra.isEmpty()) {
                        try {
                            this.agregarConcepto(palabra, definicion);
                        } catch (IllegalArgumentException e) {
                            // no metemos palabras q ya exista en el árbol
                            System.out.println("Palabra duplicada omitida: " + palabra);
                        }
                    }
                }
            }
            System.out.println("Diccionario cargado con éxito. Total palabras: " + cantidadDeConceptos());
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo: " + e.getMessage());
        }
    }

}
