// Bloque de codigo principal
principal {

// Declaracion de variables numericas
entero a = 1 ;
entero b = 10 ;

// Declaracion de variables de texto
texto c = "Hola" ;
texto d = "Mundo" ;

// Declaracion de variables booleanas
booleano ban = verdad ;
booleano opc = falso ;

// Imprimir datos en la consola
imprimir "> Todas las variables declaradas <" ;
imprimir "Variables -> ["+a+", "+b+", "+c+", "+d+", "+ban+", "+opc+"]";
imprimir "a: "+a ;
imprimir "b: "+b ;
imprimir "c: "+c ;
imprimir "d: "+d ;
imprimir "ban: "+ban ;
imprimir "opc: "+opc ;

// Validacion y reasignacion de expresiones enteras
imprimir "> Actualizacion de las variables <" ;
a = b*(23+6)-1 ;
b = 2*23+a-1 ;
imprimir "a = "+a ;
imprimir "b = "+b ;

// Declaracion de estructura de decision
imprimir "> Estructuras de decision <" ;
si (a<b||b>=a){
imprimir "Condicion 1" ;
}
si (ban&&opc){
imprimir "Condicion 2" ;
}
si (a <= b || b > a) {
imprimir "Condicion 3" ;
}
si (ban && opc && a<b){
imprimir "Condicion 4" ;
}

// Declaracion de estructura de iteracion
imprimir "> Estructuras de iteracion <" ;
entero cont = 0 ;
entero fin = 5 ;
mientras (fin > cont){
cont = cont+1 ;
imprimir "Cont: "+cont ;
}

// Llave de cierre del bloque principal
}
