package analizadores.sintaxis;

import errores.nodoError;
import errores.pilaError;
import tblSimbolos.tblSimbolo;

/* Automata:
    * si
    * (
    *  decision (Expresion[es] Booleana[s])
    * )
    * { (Delimitador Apertura)
    *  CONTENIDO 
    * } Delimitador Cierre
        
    * sino
    * { (Delimitador Apertura)
    *  CONTENIDO
    * } (Delimitador Cierre)
*/

public class erSiSino {
    
    String cadena;
    tblSimbolo TblSimbolo;
    erBooleana ErBooleana;
    pilaError PilaError;
    boolean estatus=false;
    int linea, beginIndex;
    
    public erSiSino(tblSimbolo TblSimbolo, String cadena, pilaError PilaError, int linea){
        this.TblSimbolo = TblSimbolo;
        this.cadena = cadena;
        this.PilaError = PilaError;
        this.linea = linea;
    }

    public boolean start(){
        elimEspacios();
        q0();
        return estatus;
    }

    public boolean getResu(){
        return ErBooleana.valiEB(cadena.substring(3, cadena.length()-2));
    }

    void q0(){
        if(cadena.substring(0, 3).equals("si(")){
            //System.out.println("q1");
            q1(getExpBool());
        }else
            PilaError.push(new nodoError(String.valueOf(linea+1),"Error de sintaxis en la estructura si" , "111"));
    }

    void q1(String expBool){
        if(expBool== null){
            PilaError.push(new nodoError(String.valueOf(linea+1),"Error se sintaxis, falta el parentesis de cierre" , "112"));
            //System.out.println("Error");
        }else{
            ErBooleana = new erBooleana(this.TblSimbolo, expBool, this.PilaError, linea);
            estatus = true;
            //if(ErBooleana.start())
                //System.out.println("chido");
            //else 
                //System.out.println("no chido");
        }
    }

    String getExpBool(){
        for(beginIndex=3;cadena.length()>beginIndex;beginIndex++)
            if(cadena.charAt(beginIndex)==')'){
                //System.out.println("c: " +cadena.substring(3, beginIndex));
                return cadena.substring(3, beginIndex);
        }/*else
            */
        return null;
    }

    void elimEspacios(){
        String[] data = cadena.split(" ");
        cadena="";
        for(String e : data){
            if(!e.equals(""))
                cadena+=e; 
        }
        //System.out.println(cadena);
    }
    
}
