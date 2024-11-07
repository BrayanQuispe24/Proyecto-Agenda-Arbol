/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class AVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {

    //en esta subclase solo reescribiremos dos metodos
    //insertar y eliminar
    //constantes de la clase
    private static final int RANGO_INFERIOR = -1;
    private static final int RANGO_SUPERIOR = 1;

    //metodos
    @Override
    public void insertar(T datoAInsertar) {
        this.raiz = insertar(this.raiz, datoAInsertar);
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodoActual, T datoAInsertar) {
        //caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return new NodoBinario(datoAInsertar);
        }
        T datoActual = nodoActual.getDato();
        //empezamos el recorrido
        if (datoAInsertar.compareTo(datoActual) < 0) {
            NodoBinario<T> supuestoNuevoHI = insertar(nodoActual.getHijoIzquierdo(), datoAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHI);
            return balancear(nodoActual);
        }
        if (datoAInsertar.compareTo(datoActual) > 0) {
            NodoBinario<T> supuestoNuevoHD = insertar(nodoActual.getHijoDerecho(), datoAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHD);
            return balancear(nodoActual);
        }
        throw new ExcepcionDatoYaExiste();
    }

    private NodoBinario<T> balancear(NodoBinario<T> nodoActual) {
        int alturaPorIzquierda = super.alturaNodo(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = super.alturaNodo(nodoActual.getHijoDerecho());
        int diferenciaDeAltura = alturaPorIzquierda - alturaPorDerecha;

        if (diferenciaDeAltura > AVL.RANGO_SUPERIOR) {
            NodoBinario<T> hijoIzquierdoDelAct = nodoActual.getHijoIzquierdo();
            alturaPorIzquierda = super.alturaNodo(hijoIzquierdoDelAct.getHijoIzquierdo());
            alturaPorDerecha = super.alturaNodo(hijoIzquierdoDelAct.getHijoDerecho());
            if (alturaPorDerecha > alturaPorIzquierda) {
                return rotacionDobleADerecha(nodoActual);
            } else {
                return rotacionSimpleADerecha(nodoActual);
            }
        } else if (diferenciaDeAltura < AVL.RANGO_INFERIOR) {
            NodoBinario<T> hijoDerechoAct = nodoActual.getHijoDerecho();
            alturaPorIzquierda = super.alturaNodo(hijoDerechoAct.getHijoIzquierdo());
            alturaPorDerecha = super.alturaNodo(hijoDerechoAct.getHijoDerecho());
            if (alturaPorIzquierda > alturaPorDerecha) {
                return this.rotacionDobleAIzquierda(nodoActual);
            } else {
                return this.rotacionSimpleAIzquierda(nodoActual);
            }
        }
        return nodoActual;
    }

    private NodoBinario<T> rotacionSimpleADerecha(NodoBinario<T> nodoActual) {
        NodoBinario<T> nodoARetornar = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoARetornar.getHijoDerecho());
        nodoARetornar.setHijoDerecho(nodoActual);
        return nodoARetornar;
    }

    private NodoBinario<T> rotacionSimpleAIzquierda(NodoBinario<T> nodoActual) {
        NodoBinario<T> nodoARetornar = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoARetornar.getHijoIzquierdo());
        nodoARetornar.setHijoIzquierdo(nodoActual);
        return nodoARetornar;
    }

    private NodoBinario<T> rotacionDobleADerecha(NodoBinario<T> nodoActual) {
        NodoBinario<T> nodoQueRotaAIzq = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoQueRotaAIzq);
        return this.rotacionSimpleADerecha(nodoActual);

    }

    private NodoBinario<T> rotacionDobleAIzquierda(NodoBinario<T> nodoActual) {
        NodoBinario<T> nodoQueRotaADer = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoQueRotaADer);
        return this.rotacionSimpleAIzquierda(nodoActual);
    }

    @Override
    public void eliminar(T dato) {
        //agregar excepcion 
        this.raiz = eliminar(this.raiz, dato);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T datoAEliminar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            throw new ExcepcionDatoNoExiste();
        }

        T datoActual = nodoActual.getDato();

        // Recorrer el árbol para buscar el dato a eliminar
        if (datoAEliminar.compareTo(datoActual) < 0) {
            NodoBinario<T> supuestoNuevoHI = this.eliminar(nodoActual.getHijoIzquierdo(), datoAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHI);
            // Balancear después de ajustar el subárbol izquierdo
            return balancear(nodoActual);
        }

        if (datoAEliminar.compareTo(datoActual) > 0) {
            NodoBinario<T> supuestoNuevoHD = this.eliminar(nodoActual.getHijoDerecho(), datoAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHD);
            // Balancear después de ajustar el subárbol derecho
            return balancear(nodoActual);
        }

        // Caso 1: Nodo hoja
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        // Caso 2: Un solo hijo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            // Balancear después de eliminar el nodo actual y devolver el hijo izquierdo
            return balancear(nodoActual.getHijoIzquierdo());
        }

        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            // Balancear después de eliminar el nodo actual y devolver el hijo derecho
            return balancear(nodoActual.getHijoDerecho());
        }

        // Caso 3: Dos hijos, obtener el sucesor in-order
        T reemplazo = this.obtenerSucesorInOrden(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHD = this.eliminar(nodoActual.getHijoDerecho(), reemplazo);
        nodoActual.setHijoDerecho(supuestoNuevoHD);
        nodoActual.setDato(reemplazo);

        // Balancear después de ajustar el subárbol derecho tras la eliminación del sucesor
        return balancear(nodoActual);
    }

    private T obtenerSucesorInOrden(NodoBinario<T> nodoActual) {
        while (!nodoActual.esVacioHijoIzquierdo()) {
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual.getDato();
    }
}
