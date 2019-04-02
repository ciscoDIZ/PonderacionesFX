/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author daw
 */
public class Ponderacion {

    private List<Nota> notas;

    public Ponderacion() {
        notas = new ArrayList<>();
    }

    public Double calcularMedia() {
        return notas.stream()
                .mapToDouble(m -> m.getProducto())
                .sum() / notas.stream()
                        .mapToDouble(m -> m.getPonderacion())
                        .sum();
    }

    public boolean add(Nota n) {
        return notas.add(n);
    }

    public boolean add(Nota... n) {
        boolean resultado=false;
        for (Nota nota : n) {
            resultado=notas.add(nota);
        }
        return resultado;
    }

    public boolean remove(double ponderacion){
        boolean resultado = false;
        for (Iterator<Nota> iterator = notas.iterator(); iterator.hasNext();) {
            if(iterator.next().ponderacion == ponderacion){
                iterator.remove();
                resultado = true;
            }
            
        }
        return resultado;
    }
    
    public ArrayList<Nota> getNotas() {
        return (ArrayList<Nota>) notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
    
    public void removePonderacion(double pond){
        notas = (ArrayList<Nota>)notas.stream()
                .filter(p->p.ponderacion==pond)
                .collect(Collectors.toList());
    }

}
