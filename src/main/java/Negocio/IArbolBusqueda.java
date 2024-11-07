/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio;

import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public interface IArbolBusqueda<T extends Comparable<T>> {

    public void insertar(T dato);

    public void eliminar(T dato);

    public T buscar(T dato);

    public boolean contiene(T dato);

    public int size();

    public int altura();

    public void vaciar();

    public boolean esArbolVacio();

    public int nivel();

    public List<T> recorridoEnInOrden();

    public List<T> recorridoEnPreOrden();

    public List<T> recorridoPorNiveles();
    
    public List<T> recorridoEnPostOrden();
    
    public Persona buscarPersona(T numero);
   
}
