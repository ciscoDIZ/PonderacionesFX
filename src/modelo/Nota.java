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
    private final double NOTA;
    private final double PONDERACION;

    public Nota(double nota, double ponderacion) {
        this.NOTA = nota;
        this.PONDERACION = ponderacion;
    }

    public double getNota() {
        return NOTA;
    }

    public double getPonderacion() {
        return PONDERACION;
    }
    
    public double getResultado(){
        return PONDERACION * NOTA;
    }
}