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
    
    public Double calcularMedia(){
        return notas.stream()
                .mapToDouble(m -> m.getProducto())
                .sum() / notas.stream()
                .mapToDouble(m -> m.getPonderacion())
                .sum();
    }
    
    public boolean add(Nota n){
        return notas.add(n);
    }
    
    public ArrayList<Nota> getNotas() {
        return (ArrayList<Nota>)notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
    
}
