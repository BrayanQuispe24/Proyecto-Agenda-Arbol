/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author BRAYAN
 */
public class ExcepcionDatoYaExiste extends RuntimeException {

    //en las excepciones de tiempo de ejecucion no nos obliugara a anotarlo en la implementacion de la clase
    public ExcepcionDatoYaExiste() {
        super("Dato ya existe en el árbol");
    }

    public ExcepcionDatoYaExiste(String message) {
        super(message);
    }

}
