/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author daw
 */
public class Nota {
    protected double nota;
    protected double ponderacion;

    public Nota() {
    }
    
    public Nota(double nota, double ponderacion) {
        this.nota = nota;
        this.ponderacion = ponderacion;
    }

    public double getNota() {
        return nota;
    }

    public double getPonderacion() {
        return ponderacion;
    }
    
    public double getProducto(){
        return ponderacion * nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setPonderacion(double ponderacion) {
        this.ponderacion = ponderacion;
    }
}
