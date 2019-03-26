/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daw
 */
public class Ponderaciones {
    private List<Nota> notas;

    public Ponderaciones() {
        notas = new ArrayList<>();
    }
    
    
    
    public ArrayList<Nota> getNotas() {
        return (ArrayList<Nota>)notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
    
}
