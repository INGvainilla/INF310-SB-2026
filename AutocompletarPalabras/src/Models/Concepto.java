/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author User
 */
public class Concepto implements Comparable<Concepto>{ // lo hacemos comparable x q es necesario para compararlo con otro de sus mismo tipo(conceto)
    
    protected String palabra;
    protected String definicion; // significado
    
    
    // necesito 2 constructores 1 pa agregar palabra con defincion y otro para solo palabra sin definicion
    public Concepto(String primeraPalabra , String suDefinicion){ 
        this.palabra = primeraPalabra;
        this.definicion=suDefinicion;
    }
    public Concepto(String laPalabra){
        this(laPalabra,"");
    }
    
    public String obtenerPalabra(){
        return this.palabra;
    }
    public void modificarPalabra(String nuevaPalabra){
        //this.palabra = nuevaPalabra;
        this.palabra = nuevaPalabra.trim(); //me olvide q puede tener espacios en blanco
    }
    public String obtenerDefinicion(){
        return definicion;
    }
    public void modificarDefinicion(String nuevaDefinicion){
        this.definicion= nuevaDefinicion;
    }
    
    @Override
    public int compareTo(Concepto conceptoAComparar) {
        return this.palabra.compareToIgnoreCase(conceptoAComparar.palabra);// deevuelve positivo si la palabra esta mas adelante en el ascii 
        // a contra c => 97 -99 = -2 indica q a es primero 
    }
    @Override
    public String toString() {
        return this.palabra; 
    }

    public String extraerPalabra() {
        return this.palabra; // Retorna la palabra al imprimir o buscar
    }
    public boolean empiezaCon(String prefijo) {
        return this.palabra.toLowerCase().startsWith(prefijo.toLowerCase().trim());
    }
    
    
}
