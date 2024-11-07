/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author BRAYAN
 */
public class ExcepcionDatoNoExiste extends RuntimeException{
    //en las excepciones de tiempo de ejecucion no nos obliugara a anotarlo en la implementacion de la clase
    public ExcepcionDatoNoExiste() {
        super("Dato no existe en el árbol");
    }

    public ExcepcionDatoNoExiste(String message) {
        super(message);
    }
    
}
