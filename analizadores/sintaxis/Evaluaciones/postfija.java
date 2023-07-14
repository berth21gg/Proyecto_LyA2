package analizadores.sintaxis.Evaluaciones;

import java.util.Stack;

public class postfija {
    String expPostfija;
    public postfija(String expPostfija){
        this.expPostfija = expPostfija;
    }

    public int getResultado(){
        // Entrada (Expresión en Postfija)
        String[] post = expPostfija.split(" ");
        // Declaración de las pilas
        Stack<String> E = new Stack<String>(); // Pila entrada
        Stack<String> P = new Stack<String>(); // Pila de operandos
        // Añadir post (array) a la Pila de entrada (E)
        for (int i = post.length - 1; i >= 0; i--) {
            E.push(post[i]);
        }
        // Algoritmo de Evaluación Postfija
        String operadores = "+-*/%";
        while (!E.isEmpty()) {
            if (operadores.contains("" + E.peek())) {
                P.push(evaluar(E.pop(), P.pop(), P.pop()) + "");
            } else {
                P.push(E.pop());
            }
        }
        // Mostrar resultados:
        //System.out.println("Expresion: " + expPostfija);
        //System.out.println("Resultado: " + P.peek());
        int res = Integer.parseInt(P.peek());
        //System.out.println(res);
        return res;
    }

    public int evaluar(String op, String n2, String n1) {
        int num1 = Integer.parseInt(n1);
        int num2 = Integer.parseInt(n2);
        if (op.equals("+"))
            return (num1 + num2);
        if (op.equals("-"))
            return (num1 - num2);
        if (op.equals("*"))
            return (num1 * num2);
        if (op.equals("/"))
            return (num1 / num2);
        if (op.equals("%"))
            return (num1 % num2);
        return 0;
    }
}
