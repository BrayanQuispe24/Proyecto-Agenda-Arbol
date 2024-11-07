/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author BRAYAN
 */
public class ExcepcionOrdenInvalido extends Exception {

    public ExcepcionOrdenInvalido() {
        super("Orden del árbol debe ser al menos 3");
    }

    public ExcepcionOrdenInvalido(String message) {
        super("message");
    }

}
