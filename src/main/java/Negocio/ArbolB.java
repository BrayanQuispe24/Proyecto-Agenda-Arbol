/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;
import Excepciones.ExcepcionOrdenInvalido;
import java.util.Stack;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class ArbolB<T extends Comparable<T>> extends ArbolMViasBusqueda<T> {

    private final int nroMaximoDeDatos;
    private final int nroMinimoDeDatos;
    private final int nroMinimoDeHijos;

    public ArbolB() {
        super();//setea el orden y el numero de hijos a 3 
        nroMaximoDeDatos = 2;
        nroMinimoDeDatos = 1;
        nroMinimoDeHijos = 2;
    }

    public ArbolB(int orden) throws ExcepcionOrdenInvalido {
        super();
        nroMaximoDeDatos = super.orden - 1;
        nroMinimoDeDatos = nroMaximoDeDatos / 2;
        nroMinimoDeHijos = nroMinimoDeDatos + 1;
    }

    @Override
    public void insertar(T claveAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("dato a insertar no puede ser nula");
        }

        if (this.esArbolVacio()) {
            super.raiz = new NodoMVias<>(super.orden + 1, claveAInsertar);

        } else {

            NodoMVias<T> nodoAux = this.raiz;
            Stack<NodoMVias<T>> pilaDeAncestros = new Stack<>();
            do {//buscarPosicionDeClave -> lo que hace es revisar si ya existe la clave dentro de nodo
                //int posicionDeDatoEnNodo = this.buscarPosicionDeDatoEnNodo(claveAInsertar, nodoAux);
                int posicionDeDatoEnNodo = this.buscarPosicionDeDatoEnNodo(nodoAux, claveAInsertar);

                if (posicionDeDatoEnNodo != ArbolMViasBusqueda.POSICION_INVALIDA) {
                    throw new ExcepcionDatoYaExiste();
                    //nodoAux = NodoMVias.nodoVacio();
                } else {
                    if (nodoAux.esHoja()) {
                        // el nodo auxiliar es hoja y la clave no esta en el nodo
                        this.insertarDatoOrdenadoEnNodo(nodoAux, claveAInsertar);

                        if (nodoAux.numeroDeDatosNoVacios() > this.nroMaximoDeDatos) {
                            dividirNodo(nodoAux, pilaDeAncestros);
                        }

                        nodoAux = NodoMVias.nodoVacio();
                    } else {
                        //el nodo auxiliar no es una hoja y sabemos que la clave no esta en este nodo
                        int posicionPorDondeBajar = this.buscarPosicionParaBajar(nodoAux, claveAInsertar);
                        pilaDeAncestros.push(nodoAux);
                        nodoAux = nodoAux.getHijo(posicionPorDondeBajar);

                    }
                }

            } while (!NodoMVias.esNodoVacio(nodoAux));
        }

        //en  un arbol mvias siempre se inserta en un nodo hoja 		
    }

    private void dividirNodo(NodoMVias<T> nodoAux, Stack<NodoMVias<T>> pilaDeAncestros) {

        int posicionDeClavePadre = this.nroMinimoDeDatos;
        if (pilaDeAncestros.isEmpty()) {
            insertarRaiz(posicionDeClavePadre, nodoAux);
            return;
        } else {
            NodoMVias<T> nodoPadre = pilaDeAncestros.pop();
            insertarClavesYHijosAlPadre(posicionDeClavePadre, nodoAux, nodoPadre);

            if (nodoPadre.numeroDeDatosNoVacios() > this.nroMaximoDeDatos) {
                dividirNodo(nodoPadre, pilaDeAncestros);
            }
        }

    }

    private void insertarClavesYHijosAlPadre(int posicionDeClavePadre, NodoMVias<T> nodoAux, NodoMVias<T> nodoPadre) {
        T claveRaiz = nodoAux.getDato(posicionDeClavePadre);
        //crea los dos nodos
        NodoMVias<T> nodo1 = new NodoMVias<>(super.orden + 1, nodoAux.getDato(0));
        nodo1.setHijo(0, nodoAux.getHijo(0));

        NodoMVias<T> nodo2 = new NodoMVias<>(super.orden + 1, nodoAux.getDato(posicionDeClavePadre + 1));
        nodo2.setHijo(0, nodoAux.getHijo(posicionDeClavePadre + 1));

        for (int i = 1; i < posicionDeClavePadre; i++) {
            insertarClaveYValorALosNuevosNodos(nodo1, nodoAux.getDato(i), nodoAux.getHijo(i));
        }
        nodo1.setHijo(posicionDeClavePadre, nodoAux.getHijo(posicionDeClavePadre));

        for (int i = posicionDeClavePadre + 2; i < super.orden; i++) {
            insertarClaveYValorALosNuevosNodos(nodo2, nodoAux.getDato(i), nodoAux.getHijo(i));
        }
        nodo2.setHijo(nodo2.numeroDeDatosNoVacios() - 1, nodoAux.getHijo(super.orden - 1));

        nodo2.setHijo(nodo2.numeroDeDatosNoVacios(), nodoAux.getHijo(super.orden));

        //inserta los nuevos hijos
        insertarClaveYValorOrdenadoConHijos(nodoPadre, claveRaiz, nodo1, nodo2);

    }

    private void insertarClaveYValorOrdenadoConHijos(NodoMVias<T> nodoPadre, T claveRaiz,
            NodoMVias<T> nodo1, NodoMVias<T> nodo2) {
        for (int i = nodoPadre.numeroDeDatosNoVacios(); i > 0; i--) {
            if (claveRaiz.compareTo(nodoPadre.getDato(i - 1)) < 0) {
                nodoPadre.setDato(i, nodoPadre.getDato(i - 1));
                nodoPadre.setHijo(i + 1, nodoPadre.getHijo(i));
                if ((i - 1) == 0) {
                    nodoPadre.setDato(0, claveRaiz);
                    nodoPadre.setHijo(0, nodo1);
                    nodoPadre.setHijo(1, nodo2);
                }

            } else {
                nodoPadre.setDato(i, claveRaiz);
                nodoPadre.setHijo(i, nodo1);
                nodoPadre.setHijo(i + 1, nodo2);
                break;
            }
        }
    }

    private void insertarClaveYValorALosNuevosNodos(NodoMVias<T> nodoActual, T claveAInsertar, NodoMVias<T> hijoActual) {
        nodoActual.setDato(nodoActual.numeroDeDatosNoVacios(), claveAInsertar);
        nodoActual.setHijo(nodoActual.numeroDeDatosNoVacios(), hijoActual);

    }

    private void insertarRaiz(int posicionDeClavePadre, NodoMVias<T> nodoAux) {
        T claveRaiz = nodoAux.getDato(posicionDeClavePadre);

        //crea los dos nodos divididos
        NodoMVias<T> nodo1 = new NodoMVias<>(super.orden + 1, nodoAux.getDato(0));
        nodo1.setHijo(0, nodoAux.getHijo(0));

        NodoMVias<T> nodo2 = new NodoMVias<>(super.orden + 1, nodoAux.getDato(posicionDeClavePadre + 1));
        nodo2.setHijo(0, nodoAux.getHijo(posicionDeClavePadre + 1));

        for (int i = 1; i < posicionDeClavePadre; i++) {
            insertarClaveYValorALosNuevosNodos(nodo1, nodoAux.getDato(i), nodoAux.getHijo(i));
        }
        nodo1.setHijo(posicionDeClavePadre, nodoAux.getHijo(posicionDeClavePadre));

        for (int i = posicionDeClavePadre + 2; i < super.orden; i++) {
            insertarClaveYValorALosNuevosNodos(nodo2, nodoAux.getDato(i), nodoAux.getHijo(i));
        }
        nodo2.setHijo(nodo2.numeroDeDatosNoVacios() - 1, nodoAux.getHijo(super.orden - 1));

        nodo2.setHijo(nodo2.numeroDeDatosNoVacios(), nodoAux.getHijo(super.orden));

        super.raiz = new NodoMVias<>(super.orden + 1, claveRaiz);
        //inserta los nuevos hijos
        super.raiz.setHijo(0, nodo1);
        super.raiz.setHijo(1, nodo2);
    }

    private T prestarseSiguiente(NodoMVias<T> hijoNodoPadre) {

        if (hijoNodoPadre.numeroDeDatosNoVacios() > this.nroMinimoDeDatos) {

            return hijoNodoPadre.getDato(0);
        }
        return (T) NodoMVias.datoVacio();
    }

    private T prestarseAnterior(NodoMVias<T> hijoNodoPadre) {
        if (hijoNodoPadre.numeroDeDatosNoVacios() > nroMinimoDeDatos) {

            return hijoNodoPadre.getDato(hijoNodoPadre.numeroDeDatosNoVacios() - 1);
        }
        return (T) NodoMVias.datoVacio();
    }

    private void insertarDatosDelNodoAEliminar(NodoMVias<T> nodoDelDatoAEliminar,
            NodoMVias<T> siguienteHijoDelNodoPadre) {
        for (int i = 0; i < siguienteHijoDelNodoPadre.numeroDeDatosNoVacios(); i++) {

            this.insertarDatoOrdenadoEnNodo(nodoDelDatoAEliminar, siguienteHijoDelNodoPadre.getDato(i));
        }
    }

    private void prestarseOFusionar(NodoMVias<T> nodoDelDatoAEliminar, Stack<NodoMVias<T>> pilaDeAncestros,
            T datoAEliminar) {
        if (pilaDeAncestros.isEmpty()) {
            return;
        } else {

            NodoMVias<T> nodoPadre = pilaDeAncestros.pop();
            int posicionInicialDelNodoPadre = super.buscarPosicionParaBajar(nodoPadre, datoAEliminar);

            // PREGUNTA PARA PRESTARSE LA SIGUIENTE O ANTERIOR POSICION
            // verifica posicion siguiente pero antes verifica que la posicion de los hijos
            // sea correcta para hacer sus preguntas sobre
            // si va sacar la clave del siguiente o el anterior
            // if (posicionInicialDelNodoPadre < nodoPadre.nroDeClavesNoVacias()) {
            NodoMVias<T> siguienteHijoDelNodoPadre = nodoPadre.getHijo(posicionInicialDelNodoPadre + 1);

            T claveReemplazoEnElNodoPadre = prestarseSiguiente(siguienteHijoDelNodoPadre);
            if (claveReemplazoEnElNodoPadre != null) {
                super.insertarDatoOrdenadoEnNodo(nodoDelDatoAEliminar,
                        nodoPadre.getDato(posicionInicialDelNodoPadre));

                nodoDelDatoAEliminar.setHijo(nodoDelDatoAEliminar.numeroDeDatosNoVacios(),
                        siguienteHijoDelNodoPadre.getHijo(0));
                super.eliminarElDatoEnElNodo(siguienteHijoDelNodoPadre, 0);
                nodoPadre.setDato(posicionInicialDelNodoPadre, claveReemplazoEnElNodoPadre);
            } else {
                if (posicionInicialDelNodoPadre != 0) {
                    NodoMVias<T> anteriorHijoDelNodoPadre = nodoPadre.getHijo(posicionInicialDelNodoPadre - 1);
                    claveReemplazoEnElNodoPadre = prestarseAnterior(anteriorHijoDelNodoPadre);
                    if (claveReemplazoEnElNodoPadre != null) {
                        super.insertarDatoOrdenadoEnNodo(nodoDelDatoAEliminar,
                                nodoPadre.getDato(posicionInicialDelNodoPadre - 1));
                        super.eliminarElDatoEnElNodo(anteriorHijoDelNodoPadre, anteriorHijoDelNodoPadre.numeroDeDatosNoVacios() - 1);
                        nodoPadre.setDato(posicionInicialDelNodoPadre - 1, claveReemplazoEnElNodoPadre);
                    }
                } else {

                    // desde esta parte se hace el fucionar
                    super.insertarDatoOrdenadoEnNodo(nodoDelDatoAEliminar, nodoPadre.getDato(posicionInicialDelNodoPadre));
                    this.insertarDatosDelNodoAEliminar(nodoDelDatoAEliminar, siguienteHijoDelNodoPadre);
                    nodoPadre.setHijo(posicionInicialDelNodoPadre + 1, NodoMVias.nodoVacio());
                    nodoPadre.setDato(posicionInicialDelNodoPadre, (T) NodoMVias.datoVacio());
                }
            }

            if (nodoPadre.numeroDeDatosNoVacios() < this.nroMinimoDeDatos) {
                prestarseOFusionar(nodoPadre, pilaDeAncestros, datoAEliminar);
            }

        }
    }

    private NodoMVias<T> buscarNodoDelPredecesor(NodoMVias<T> nodoHijo,
            Stack<NodoMVias<T>> pilaDeAncestros) {
        while (!nodoHijo.esHijoVacio(nodoHijo.numeroDeDatosNoVacios())) {
            nodoHijo = nodoHijo.getHijo(nodoHijo.numeroDeDatosNoVacios());
        }
        return nodoHijo;
    }

    @Override
    public void eliminar(T datoAEliminar) {
        //se hara de manera iterativa 
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("Dato a eliminar no puede ser nulo");
        }
        NodoMVias<T> nodoAux = this.raiz;
        NodoMVias<T> nodoDelDatoAEliminar = NodoMVias.nodoVacio();
        Stack<NodoMVias<T>> pilaAncestros = new Stack<>();
        int posicionDeDatoAEliminar = POSICION_INVALIDA;
        //comienza la busqueda 
        do {
            posicionDeDatoAEliminar = this.buscarPosicionDeDatoEnNodo(nodoAux, datoAEliminar);
            if (posicionDeDatoAEliminar != POSICION_INVALIDA) {
                nodoDelDatoAEliminar = nodoAux;
                nodoAux = NodoMVias.nodoVacio();
            } else {
                int posicionPorDondeBajar = buscarPosicionParaBajar(nodoAux, datoAEliminar);
                pilaAncestros.push(nodoAux);
                nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
            }
        } while (!NodoMVias.esNodoVacio(nodoAux));
        if (NodoMVias.esNodoVacio(nodoDelDatoAEliminar)) {
            throw new ExcepcionDatoNoExiste();
        }
        if (nodoDelDatoAEliminar.esHoja()) {
            super.eliminarElDatoEnElNodo(nodoDelDatoAEliminar, posicionDeDatoAEliminar);
            if (nodoDelDatoAEliminar.numeroDeDatosNoVacios() < this.nroMinimoDeDatos) {

                prestarseOFusionar(nodoDelDatoAEliminar, pilaAncestros, datoAEliminar);
            }
        } else {
            //el dato a eliminar no esta en una hoja, entonces buscamos predecesor inOrden
            pilaAncestros.push(nodoDelDatoAEliminar);
            NodoMVias<T> nodoDelPredecesor = buscarNodoDelPredecesor(nodoDelDatoAEliminar.getHijo(posicionDeDatoAEliminar), pilaAncestros);
            int posicionDelReemplazo = nodoDelPredecesor.numeroDeDatosNoVacios() - 1;
            T datoDeReemplazo = nodoDelPredecesor.getDato(posicionDelReemplazo);
            nodoDelPredecesor.setDato(posicionDeDatoAEliminar, datoDeReemplazo);
            if (nodoDelPredecesor.numeroDeDatosNoVacios() < this.nroMinimoDeDatos) {
                prestarseOFusionar(nodoDelPredecesor, pilaAncestros, datoAEliminar);
            }
        }
    }

    @Override
    public Persona buscarPersona(T numero) {
        return super.buscarPersona(numero); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }


    /*public String toString() {
        return toString(this.raiz, 0);
    }

    private String toString(NodoMVias<T> nodoActual, int nivel) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return "";
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Nivel ").append(nivel).append(": ");

        // Agregamos los datos del nodo actual
        for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
            resultado.append(nodoActual.getDato(i)).append(" ");
        }
        resultado.append("\n");

        // Recorremos los hijos del nodo actual
        for (int i = 0; i < this.orden; i++) {
            if (!NodoMVias.esNodoVacio(nodoActual.getHijo(i))) {
                resultado.append(toString(nodoActual.getHijo(i), nivel + 1));
            }
        }

        return resultado.toString();
    }*/
    @Override
    public String toString() {
        String a = super.toString();
        return a;
    }

    private int obtenerPosicionDelHijo(NodoMVias<T> nodoPadre, NodoMVias<T> nodoHijo) {
        for (int i = 0; i <= nodoPadre.numeroDeDatosNoVacios(); i++) {
            if (nodoPadre.getHijo(i) == nodoHijo) {
                return i;
            }
        }
        return -1; // No encontrado, aunque idealmente no debería suceder
    }

}
