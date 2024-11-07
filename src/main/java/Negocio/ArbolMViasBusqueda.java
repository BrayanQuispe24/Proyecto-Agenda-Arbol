/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;
import Excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class ArbolMViasBusqueda<T extends Comparable<T>>
        implements IArbolBusqueda<T> {

    //lo declaramos protegido porque sus clases hijas lo podran utilizar 
    protected NodoMVias<T> raiz;
    protected int orden;
    private static final int ORDEN_MINIMO = 3;
    protected static final int POSICION_INVALIDA = -1;

    //primer constructor 
    public ArbolMViasBusqueda() {
        this.orden = ORDEN_MINIMO;
    }

    //Segundo constructor 
    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < ORDEN_MINIMO) {
            throw new Excepciones.ExcepcionOrdenInvalido();
        }
        this.orden = orden;
    }

    @Override
    public void insertar(T datoAInsertar) {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("Dato a insertar no puede ser nulo");
        }
        //si es arbol vacio simplemente creamos un nuevo nodo y se lo asignamos a la raiz
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, datoAInsertar);
        } else {
            //si el arbol no esta vacio empezamos el recorrido
            NodoMVias<T> nodoAux = this.raiz;
            do {
                int posicionDeDatoEnNodo = this.buscarPosicionDeDatoEnNodo(nodoAux, datoAInsertar);
                //una vez teniendo la posicion del dato verificamos si lo encontro
                if (posicionDeDatoEnNodo != POSICION_INVALIDA) {
                    throw new ExcepcionDatoYaExiste();
                }
                //en este punto sabemos que el dato a insertar no esta en el nodo aux
                //si es hoja
                if (nodoAux.esHoja()) {
                    //verificamos que haya espacio 
                    if (nodoAux.estanDatosLlenos()) {
                        //si no hay espacio hay que ver a quien le acoplaremos un hijo 
                        int posicionPorDondeBajar = buscarPosicionParaBajar(nodoAux, datoAInsertar);
                        //una vez que sabemos la posicion creamos un nodo
                        NodoMVias<T> nuevoNodo = new NodoMVias<>(this.orden, datoAInsertar);
                        nodoAux.setHijo(posicionPorDondeBajar, nuevoNodo);
                    } else {
                        //si no estan llenos simplemente lo insertamos
                        this.insertarDatoOrdenadoEnNodo(nodoAux, datoAInsertar);

                    }
                    nodoAux = NodoMVias.nodoVacio();
                } else {
                    //en este punto sabemos que nos es una hoja 
                    int posicionPorDondeBajar = buscarPosicionParaBajar(nodoAux, datoAInsertar);
                    //verificamos si el nodo hijo es vacio
                    if (nodoAux.esHijoVacio(posicionPorDondeBajar)) {
                        NodoMVias<T> nuevoNodo = new NodoMVias<>(this.orden, datoAInsertar);
                        nodoAux.setHijo(posicionPorDondeBajar, nuevoNodo);
                        nodoAux = NodoMVias.nodoVacio();
                    } else {
                        nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
                    }
                }
            } while (!NodoMVias.esNodoVacio(nodoAux));
        }
    }

    protected int buscarPosicionDeDatoEnNodo(NodoMVias<T> nodoActual, T claveABuscar) {
        for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
            T claveEnTurno = nodoActual.getDato(i);
            if (claveABuscar.compareTo(claveEnTurno) == 0) {
                return i;
            }
        }
        return ArbolMViasBusqueda.POSICION_INVALIDA;
    }

    protected int buscarPosicionParaBajar(NodoMVias<T> nodoAux, T datoAInsertar) {
        int i = 0;
        //con esto buscaremos la posicion
        while (i < nodoAux.numeroDeDatosNoVacios()) {
            T datoEnTurno = nodoAux.getDato(i);
            //si el dato en turno es menor aumentamos el contador
            if (datoEnTurno.compareTo(datoAInsertar) < 0) {
                i++;
            } else {
                //si no es menor entonces ya encontramos la posicion
                break;
            }
        }
        //si el ultimo dato es menor entonces devolveremos la posicion del ultimo hijo
        if (nodoAux.getDato(nodoAux.numeroDeDatosNoVacios() - 1)
                .compareTo(datoAInsertar) < 0) {
            return nodoAux.numeroDeDatosNoVacios();
        }
        return i;
    }

    protected void insertarDatoOrdenadoEnNodo(NodoMVias<T> nodoAux, T datoAInsertar) {
        boolean insertado = false;//necesitamos una bandera para controlar la insercion
        for (int i = nodoAux.numeroDeDatosNoVacios() - 1; i >= 0 && !insertado; i--) {
            T claveActual = nodoAux.getDato(i);//para poder mover los datos del arreglo tenemos que usar un
            //variable auxiliar para almacenar el dato a mover
            if (datoAInsertar.compareTo(claveActual) > 0) { // claveAInsertar > claveActual
                nodoAux.setDato(i + 1, datoAInsertar);
                insertado = true;
                //return;
            } else {
                nodoAux.setDato(i + 1, claveActual);
            }
        }
        if (!insertado) {//si entra entonces es un nodo vacio
            nodoAux.setDato(0, datoAInsertar);
        }
    }

    @Override
    public void eliminar(T datoAEliminar) {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("Dato a eliminar no puede ser nulo");
        }
        this.raiz = eliminar(this.raiz, datoAEliminar);
    }

    private NodoMVias<T> eliminar(NodoMVias<T> nodoActual, T datoAEliminar) {
        //caso base 
        if (NodoMVias.esNodoVacio(nodoActual)) {
            throw new ExcepcionDatoNoExiste();
        }
        int posicionDeDatoEnNodo = this.buscarPosicionDeDatoEnNodo(nodoActual, datoAEliminar);
        if (posicionDeDatoEnNodo != POSICION_INVALIDA) {
            //caso cuando el nodo es hoja
            if (nodoActual.esHoja()) {
                this.eliminarElDatoEnElNodo(nodoActual, posicionDeDatoEnNodo);
                if (nodoActual.numeroDeDatosNoVacios() == 0) {
                    return NodoMVias.nodoVacio();
                }
                System.out.println("entra");
                return nodoActual;

            }
            //nodoActual no es hoja, pero eld ato esta ahi, entonces
            // debo buscar un reemplazo y eliminar antes de usarlo
            T datoDeReemplazo;
            if (hayHijoEnNodoDelanteDePosicion(nodoActual, posicionDeDatoEnNodo)) {
                datoDeReemplazo = this.obtenerDatoSucesorInOrden(nodoActual, posicionDeDatoEnNodo);

            } else {
                datoDeReemplazo = this.obtenerDatoPredecesorInOrden(nodoActual, posicionDeDatoEnNodo);
            }
            nodoActual = eliminar(nodoActual, datoDeReemplazo);
            nodoActual.setDato(posicionDeDatoEnNodo, datoDeReemplazo);
            return nodoActual;
        }
        //en este punto el datoAEliminar no esta en el nodoActual
        int posicionPorDondeBajar = buscarPosicionParaBajar(nodoActual, datoAEliminar);
        NodoMVias<T> supuestoNuevoHijo = eliminar(nodoActual.getHijo(posicionPorDondeBajar), datoAEliminar);
        nodoActual.setHijo(posicionPorDondeBajar, supuestoNuevoHijo);
        return nodoActual;
    }

    private T obtenerDatoSucesorInOrden(NodoMVias<T> nodoActual, int posicionDeDatoEnNodo) {   //int i) {
        T datoRetorno = (T) NodoMVias.datoVacio();
        if (!nodoActual.esHijoVacio(posicionDeDatoEnNodo + 1)) {
            NodoMVias<T> nodoAuxiliar = nodoActual.getHijo(posicionDeDatoEnNodo + 1);
            while (!NodoMVias.esNodoVacio(nodoAuxiliar)) {
                datoRetorno = nodoAuxiliar.getDato(0);
                nodoAuxiliar = nodoAuxiliar.getHijo(0);
            }
            return datoRetorno;
        } else {
            return nodoActual.getDato(posicionDeDatoEnNodo + 1);
        }

    }

    private T obtenerDatoPredecesorInOrden(NodoMVias<T> nodoActual, int posicionDeDatoEnNodo) {
        T datoRetorno = (T) NodoMVias.datoVacio();
        if (!nodoActual.esHijoVacio(posicionDeDatoEnNodo)) {
            NodoMVias<T> nodoAuxiliar = nodoActual.getHijo(posicionDeDatoEnNodo);
            while (!NodoMVias.esNodoVacio(nodoAuxiliar)) {
                datoRetorno = nodoAuxiliar.getDato(nodoAuxiliar.numeroDeDatosNoVacios() - 1);
                nodoAuxiliar = nodoAuxiliar.getHijo(0);
            }
            return datoRetorno;
        } else {
            return nodoActual.getDato(posicionDeDatoEnNodo - 1);
        }
    }

    protected boolean hayHijoEnNodoDelanteDePosicion(NodoMVias<T> nodoActual, int posicion) {
        return !nodoActual.esHijoVacio(posicion + 1);
    }

    protected void eliminarElDatoEnElNodo(NodoMVias<T> nodoActual, int posicion) {
        for (int i = posicion; i < nodoActual.numeroDeDatosNoVacios() - 1; i++) {
            nodoActual.setDato(i, nodoActual.getDato(i + 1));
        }
        nodoActual.setDato(nodoActual.numeroDeDatosNoVacios() - 1, (T) NodoMVias.datoVacio());
    }

    @Override
    public T buscar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Dato a buscar no puede ser nulo");
        }

        NodoMVias<T> nodoAux = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoAux)) {
            //esta bandera nos servira para saber si hubo cambios de nodo
            boolean cambioElNodo = false;
            for (int i = 0; i < nodoAux.numeroDeDatosNoVacios() && !cambioElNodo; i++) {
                T datoDelNodoAux = nodoAux.getDato(i);
                if (dato.compareTo(datoDelNodoAux) == 0) {
                    return datoDelNodoAux;
                }
                if (dato.compareTo(datoDelNodoAux) < 0) {
                    nodoAux = nodoAux.getHijo(i);
                    cambioElNodo = true;
                }
            }//fin for
            if (!cambioElNodo) {
                nodoAux = nodoAux.getHijo(nodoAux.numeroDeDatosNoVacios());
            }
        }//end while
        return null;
    }

    @Override
    public Persona buscarPersona(T numero) {
        if (numero == null) {
            throw new IllegalArgumentException("Dato a buscar no puede ser nulo");
        }

        NodoMVias<T> nodoAux = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoAux)) {
            boolean cambioElNodo = false;
            for (int i = 0; i < nodoAux.numeroDeDatosNoVacios() && !cambioElNodo; i++) {
                T datoDelNodoAux = nodoAux.getDato(i);

                // Verifica si el dato es la persona buscada
                if (numero.compareTo(datoDelNodoAux) == 0) {
                    if (datoDelNodoAux instanceof Persona persona) {  // Verifica el tipo antes de castear
                        return persona;
                    } else {
                        throw new ClassCastException("El dato encontrado no es del tipo Persona.");
                    }
                }
                // Si el dato buscado es menor, navega al hijo izquierdo
                if (numero.compareTo(datoDelNodoAux) < 0) {
                    // Verifica que el hijo no sea nulo antes de asignarlo
                    if (!NodoMVias.esNodoVacio(nodoAux.getHijo(i))) {
                        nodoAux = nodoAux.getHijo(i);
                        cambioElNodo = true;
                    } else {
                        return null;  // Retorna null si el camino termina en un nodo vacío
                    }
                }
            }
            // Si no hubo cambio en el nodo, pasa al hijo derecho más lejano
            if (!cambioElNodo) {
                if (!NodoMVias.esNodoVacio(nodoAux.getHijo(nodoAux.numeroDeDatosNoVacios()))) {
                    nodoAux = nodoAux.getHijo(nodoAux.numeroDeDatosNoVacios());
                } else {
                    return null;
                }
            }
        }
        return null;  // Retorna null si no se encuentra el dato
    }

    @Override
    public boolean contiene(T dato) {
        return NodoMVias.nodoVacio() == this.buscar(dato);
    }

    @Override
    public int size() {
        int sizeTotal = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            NodoMVias<T> nodoActual = this.raiz;
            colaDeNodos.offer(nodoActual);
            do {
                nodoActual = colaDeNodos.poll();
                for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
                    sizeTotal++;
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }//fin del for
                if (!nodoActual.esHijoVacio(nodoActual.numeroDeDatosNoVacios())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()));
                }
            } while (!colaDeNodos.isEmpty());
        }
        return sizeTotal;
    }

    @Override
    public int altura() {
        int altura = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            NodoMVias<T> nodoActual = this.raiz;
            colaDeNodos.offer(nodoActual);
            do {
                int cantidadNodos = colaDeNodos.size();
                altura++;
                for (int i = 0; i < cantidadNodos; i++) {
                    nodoActual = colaDeNodos.poll();
                    for (int j = 0; j < nodoActual.numeroDeDatosNoVacios(); j++) {
                        if (!nodoActual.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoActual.getHijo(j));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.numeroDeDatosNoVacios())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()));
                    }
                }
            } while (!colaDeNodos.isEmpty());

        }
        return altura;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(raiz);
    }

    @Override
    public int nivel() {
        int nivelTotal = -1;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            NodoMVias<T> nodoActual = this.raiz;
            colaDeNodos.offer(nodoActual);
            do {
                int cantidadNodos = colaDeNodos.size();
                nivelTotal++;
                for (int i = 0; i < cantidadNodos; i++) {
                    nodoActual = colaDeNodos.poll();
                    for (int j = 0; j < nodoActual.numeroDeDatosNoVacios(); j++) {
                        if (!nodoActual.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoActual.getHijo(j));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.numeroDeDatosNoVacios())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()));
                    }
                }
            } while (!colaDeNodos.isEmpty());

        }
        return nivelTotal;
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new LinkedList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    public void recorridoEnInOrden(NodoMVias<T> nodoActual, List<T> recorrido) {
        //caso base cuando el nodo es nodoVacio
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        //caso general 
        //en el caso de que no sea un nodo vacio 
        for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getDato(i));
        }
        //falta visitar el ultimo hijo
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()), recorrido);
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<T> nodoActual, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        //caso general 
        for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
            recorrido.add(nodoActual.getDato(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        //falta visitar el ultimo hijo
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()), recorrido);
    }

    @Override
    public List<T> recorridoPorNiveles() {
        //creamos la lista 
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoMVias<T> nodoActual;
            do {
                nodoActual = colaDeNodos.poll();
                for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
                    recorrido.add(nodoActual.getDato(i));
                    //preguntamos si tiene hijo 
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }//fin for 
                if (!nodoActual.esHijoVacio(nodoActual.numeroDeDatosNoVacios())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()));
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new LinkedList<>();
        this.recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<T> nodoActual, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        //falta visitar el ultimo hijo
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()), recorrido);
        //caso general 
        for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getDato(i));

        }

    }

    public int cantidadDeHijosVacios() {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            NodoMVias<T> nodoActual;
            do {
                nodoActual = colaDeNodos.poll();
                // Contar los hijos vacíos en el nodo actual
                for (int i = 0; i < this.orden; i++) { // Cambiar a 'this.orden'
                    if (nodoActual.esHijoVacio(i)) {
                        cantidad++;
                    } else {
                        // Si el hijo no es vacío, agregarlo a la cola para su posterior procesamiento
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
            } while (!colaDeNodos.isEmpty());
        }
        return cantidad;
    }

    @Override
    public String toString() {
        return toStringNodo(raiz, "");
    }

    private String toStringNodo(NodoMVias<T> nodoActual, String ant) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodoActual.numeroDeDatosNoVacios(); i++) {
            sb.append(ant).append("║\n");
            sb.append(ant).append("╠═ Clave: ").append(nodoActual.getDato(i)).append("\n");
            sb.append(ant).append("║\n");
            if (!NodoMVias.esNodoVacio(nodoActual.getHijo(i))) {
                sb.append(ant).append("╠═ Hijo ").append(i).append(": ").append("\n");
                sb.append(toStringNodo(nodoActual.getHijo(i), ant + "║  "));
            } else {
                sb.append(ant).append("╠═ Hijo ").append(i).append(": ").append("null\n");
            }
        }
        sb.append(ant).append("║\n");
        if (!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()))) {
            sb.append(ant).append("╚═ Hijo ").append(nodoActual.numeroDeDatosNoVacios()).append(": ").append("\n");
            sb.append(toStringNodo(nodoActual.getHijo(nodoActual.numeroDeDatosNoVacios()), ant + "   "));
        } else {
            sb.append(ant).append("╚═ Hijo ").append(nodoActual.numeroDeDatosNoVacios()).append(": null");
        }
        return sb.toString();
    }

}
