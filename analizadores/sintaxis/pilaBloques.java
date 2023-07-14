package analizadores.sintaxis;

import java.util.Stack;

public class pilaBloques {

    Stack<String> pila = null;

    public pilaBloques(){
        pila = new Stack<String>();
    }

    public boolean estaVacia(){
        return pila.isEmpty();
    }

    int getLong(){
        return pila.size();
    }
    
}
