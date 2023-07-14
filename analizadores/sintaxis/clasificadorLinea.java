package analizadores.sintaxis;

import analizadores.lexico.token;
import errores.nodoError;
import errores.pilaError;
import tblSimbolos.tblSimbolo;

/*
 * Clasifica el codigo por lineas
*/

public class clasificadorLinea {

    String[] codigo;
    token t;
    tblSimbolo TblSimbolo; 
    pilaError PilaError;
    pilaBloques PilaBloques;
    consola consola;

    public clasificadorLinea(String[] lineas, token tk, tblSimbolo TblSimbolo, pilaError PilaError, pilaBloques PilaBloques, consola consola){
        codigo = lineas;
        t = tk;
        this.TblSimbolo = TblSimbolo;
        this.PilaError = PilaError;
        this.PilaBloques = PilaBloques;
        this.consola = consola;
    }

    // Evaluacion de cada linea de codigo de manera singular
    public void analisisSintactico(){
        boolean bloque = false, ban = true;
        int i;
        for (i = 0; i < codigo.length; i++) {
            codigo[i] = codigo[i].trim();
            if(!codigo[i].equals("")){
                String ultimo = codigo[i].substring(codigo[i].length()-1);
                //System.out.println("Ultimo -> "+ultimo);
                //System.out.println("Linea: "+codigo[i]);
                String[] datos = valiString(codigo[i]);
                /*for (String dato : datos) {
                    System.out.println("    -> "+dato);
                }*/
                //System.out.println("===> "+datos[0]);
                String token = t.getToken(datos[0]);
                String vars[] = token.split(",");
                if(datos[0].equals("principal") && ultimo.equals("{")){
                    bloque = true;
                    PilaBloques.pila.push("principal");
                }else if((datos[0].equals("si") || datos[0].equals("sino")) && ultimo.equals("{")){
                    if(ban){
                        erSiSino ersi = new erSiSino(TblSimbolo, codigo[i], this.PilaError, i);
                        if(ersi.start()){ // Sintaxis correcta
                            try {
                                System.out.println("RESUUUUU SI"+ersi.getResu());
                                ban = ersi.getResu();
                            } catch (Exception e) {
                                System.out.println("ERROR SEMANTICOOOO 1");
                                PilaError.push(new nodoError(String.valueOf(i+1),"Error de semantica en la expresion booleana si" , "300"));
                            }
                        }else{
                            System.out.println("INCORRECTAAAAAAA");
                        }
                        PilaBloques.pila.push("si");
                    }else{
                        System.out.println("CONDICION FALSA si "+i);
                    }
                }else if(datos[0].equals("mientras") && ultimo.equals("{")){
                    if(ban){
                        erMientras erm = new erMientras(TblSimbolo, codigo[i], this.PilaError, i);
                        PilaBloques.pila.push("mientras "+i);
                        if(erm.start()){ // Sintaxis correcta
                            try {
                                System.out.println("RESUUUUU MMMM -> "+erm.getResu());
                                ban = erm.getResu();
                            } catch (Exception e) {
                                System.out.println("ERROR SEMANTICOOOO 2");
                                PilaError.push(new nodoError(String.valueOf(i+1),"Error de semantica en la expresion booleana mientras " , "301"));
                                ban = false;
                            }
                        }else{
                            System.out.println("INCORRECTAAAAAAA");
                        }
                    }else{
                        System.out.println("CONDICION FALSA mientras "+i);
                    }
                }else if(datos[0].equals("hacer") && ultimo.equals("{")){
                    if(ban){
                        PilaBloques.pila.push("hacer");
                    }else{
                        System.out.println("CONDICION FALSA hacer "+i);
                    }
                }else if ((datos[0].equals("entero") || (datos[0].equals("texto")) || (datos[0].equals("booleano"))) // Declaracion de variables
                || (vars[0].equals("VARIABLE"))){ // Validacion de variable ya declarada
                    if(ban){
                        try {
                            erVariables erv;    
                            if((vars[0].equals("VARIABLE"))){ // Iniciazada
                                erv = new erVariables(datos, codigo[i], t, this.TblSimbolo, this.PilaError, i, true);
                            }else{ // A declarar
                                erv = new erVariables(datos, codigo[i], t, this.TblSimbolo, this.PilaError, i, false);
                            }
                            erv.valiER();
                        } catch (Exception e) {
                            System.out.println("Error Sintactico");
                            PilaError.push(new nodoError(String.valueOf(i+1),"Error de sintaxis al declarar el valor de la variable" , "116"));
                        }
                    }else{
                        System.out.println("CONDICION FALSA var "+i);
                    }
                }else if (datos[0].equals("")){ // Variables ya declaradas
                    System.out.println("¿Error?");
                }else if(datos[0].equals("}")){ // Cierre de bloque
                    if(PilaBloques.estaVacia()){
                        System.out.println("ERROR en las llaves");
                        PilaError.push(new nodoError(String.valueOf(i+1),"Error de sintaxis, falto cerrar un bloque de codigo" , "100"));
                    }else{
                        System.out.println("PILA Bloques ->"+PilaBloques.pila);
                        System.out.println("Ult Bloq -> "+PilaBloques.pila.get(PilaBloques.pila.size()-1));
                        if(PilaBloques.pila.get(PilaBloques.pila.size()-1).equals("si")){
                            if(!ban){
                                ban = true;
                            }
                        }else if(PilaBloques.pila.get(PilaBloques.pila.size()-1).substring(0, 8).equals("mientras")){
                            if(ban){
                                System.out.println("Si era mientras "+i);
                                System.out.println("Volver a la linea "+PilaBloques.pila.get(PilaBloques.pila.size()-1).substring(9));
                                if(PilaError.estaVacia()){
                                    i = Integer.parseInt(PilaBloques.pila.get(PilaBloques.pila.size()-1).substring(9))-1;
                                }else{
                                    PilaError.push(new nodoError(String.valueOf(i+1),"Error de sintaxis en el bloque del ciclo" , "117"));
                                }
                            }else{
                                ban = true;
                            }
                        }
                        PilaBloques.pila.pop();
                        if(bloque){
                            System.out.println("Bloque correcto");
                        }
                    }
                }else if(datos[0].equals("//")){ // Cometarios
                    // *NO HACE NADA* 
                }else if(datos[0].equals("imprimir")){ // Cometarios
                    if(ban){
                        erImprimir eri = new erImprimir(TblSimbolo, codigo[i], this.PilaError,i+1,consola);
                        eri.valiER();
                    }else{
                        System.out.println("CONDICION FALSA imprimir "+i);
                    }
                }else{
                    PilaError.push(new nodoError(String.valueOf(i+1),"Error de sintaxis, token no reconocido" , "101"));
                }
            }
        }
        System.out.println("Pila Bloques -> "+PilaBloques.getLong());
        if(!bloque){
            PilaError.push(new nodoError(String.valueOf(i+1),"Error de sintaxis, falto el bloque de codigo principal" , "102"));
        }
        if(!PilaBloques.estaVacia()){
            PilaError.push(new nodoError(String.valueOf(i+1),"Error de sintaxis, falto cerrar un bloque de codigo" , "103"));
        }
    } 

    // Toma de parametro una cadena y la devuelve como un arrelo String separado por espacios
    String[] valiString(String p_dato) {
        String[] v_datos = null;
        int v_con, v_con2;
        try {
            int v_cont = 0, v_cont2 = 0;
            String[] v_aux = p_dato.split(" ");
            for (v_con = 0; v_con < v_aux.length; v_con++)
                if (!v_aux[v_con].equals(""))
                    v_cont++;
            v_datos = new String[v_cont];
            for (v_con2 = 0; v_con2 < v_aux.length; v_con2++)
                if (!v_aux[v_con2].equals("")) {
                    v_datos[v_cont2] = v_aux[v_con2];
                    v_cont2++;
                }
            return v_datos;
        } catch (Exception e) {
            return v_datos;
        }
    }
}
