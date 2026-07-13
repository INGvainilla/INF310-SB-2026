/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

/**
 *
 * @author User
 */
public interface IArbol<T> {
     //  void insertar(T dato);
    //  void eliminar(T dato);
    //  T buscar(T dato);
    // boolean contiene(T dato);
    int size();
    int altura();
    int nivel();
    boolean esArbolVacio();
    void vaciar();
    List<T> recorridoEnPreOrden();
    List<T> recorridoEnInOrden();
    List<T> recorridoEnPostOrden();
    List<T> recorridoPorNiveles();
}
