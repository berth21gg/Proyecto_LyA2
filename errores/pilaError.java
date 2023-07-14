package errores;

import java.util.Stack;

/*
 * CODIGOS
 *   0 - 100 Lexicos
 * 100 - 199 Sintacticos
 * 300 - 399 Gramaticales
 */

public class pilaError {

    Stack<nodoError> pila;
    
    public pilaError(){
        pila = new Stack<nodoError>();
    }
   
    public void push(nodoError nodo){
        pila.push(nodo);
    }

    public Object pop(){
       return pila.pop();
    }

    public boolean estaVacia(){
        return pila.isEmpty();
    }

}
