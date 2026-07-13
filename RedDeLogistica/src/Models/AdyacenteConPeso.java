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
public class AdyacenteConPeso implements Comparable<AdyacenteConPeso>{
    private int indiceVertice;
    private double peso;
    public AdyacenteConPeso(int vertice){
        this.indiceVertice= vertice;
    }
    public AdyacenteConPeso(int vertice , double pesoDeVertice){
        this.indiceVertice= vertice;
        this.peso= pesoDeVertice;
    }
    public int getIndiceVertice(){
        return indiceVertice;
    }
    public double getPeso(){
        return peso;
    }
    public void setPeso(double nuevoPeso){
        this.peso= nuevoPeso;
    }
    @Override
    public int compareTo(AdyacenteConPeso otroAdya) {
        Integer esteVertice = this.indiceVertice;
        Integer elOtroVertice = otroAdya.indiceVertice; // el integer es necesario para acceder al compareTo de enteros
        return esteVertice.compareTo(elOtroVertice);
    }

    @Override
    public int hashCode() {
        int hash=3;
        hash=67 * hash + this.indiceVertice;
        return hash;
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null){
            return false;
        }
        if (getClass() != otro.getClass()){
            return false;
        }
        AdyacenteConPeso other= (AdyacenteConPeso) otro;
        return this.indiceVertice == other.indiceVertice;
    }
}

