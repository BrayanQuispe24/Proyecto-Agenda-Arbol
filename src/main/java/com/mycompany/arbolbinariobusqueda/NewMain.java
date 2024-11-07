/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.arbolbinariobusqueda;

import Negocio.ArbolB;

/**
 *
 * @author BRAYAN
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArbolB nuevoArbol=new ArbolB();
        nuevoArbol.insertar(5);
        nuevoArbol.insertar(8);
        nuevoArbol.insertar(10);
        nuevoArbol.insertar(25);
        nuevoArbol.insertar(3);
        nuevoArbol.insertar(9);
        System.out.println(nuevoArbol.toString());
        nuevoArbol.eliminar(3);
        System.out.println(nuevoArbol.toString());
        
    }
    
}
