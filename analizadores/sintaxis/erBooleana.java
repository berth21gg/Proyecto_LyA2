package analizadores.sintaxis;

import java.util.ArrayList;
import errores.nodoError;
import errores.pilaError;
import tblSimbolos.simbolo;
import tblSimbolos.tblSimbolo;

public class erBooleana {

    tblSimbolo TblSimbolo;
    String cadena;
    pilaError PilaError;
    int linea;
    simbolo c;
    int beginIndex = 0;
    int endIndex = 0;
    boolean estatus = false;
    autCadenas AutCadenas;
    autNumeros AutNumeros;
    
    public erBooleana(tblSimbolo TblSimbolo, String cadena, pilaError PilaError, int linea) {
        this.TblSimbolo = TblSimbolo;
        this.cadena = cadena;
        this.PilaError = PilaError;
        this.linea = linea;
    }

    public boolean start() {
        q0();
        return estatus;
    }

    void q0() {
        endIndex = getFinalIndex(cadena);
        String aux = cadena.substring(beginIndex, endIndex);
        beginIndex = endIndex;
        if (aux.equals("verdad") || aux.equals("falso") || veriEsVarBool(aux)) {
            q1();
        } else {
            AutCadenas = new autCadenas(aux);
            AutNumeros = new autNumeros(aux);
            if (AutNumeros.start() || AutCadenas.start() || veriEsVarEnte(aux) || veriEsVarText(aux)) {
                q2();
            } else {
                estatus = false;
                PilaError.push(new nodoError(String.valueOf(linea+1),
                        "Error de sintaxis, expresión booleana mal construida", "104"));
            }

        }
    }

    void q1(){
        if (beginIndex == cadena.length()) {
            qf();
        }
        else{
            if(isOpeLog()){
                q0();
            }else{
                PilaError.push(new nodoError(String.valueOf(linea+1), "Error de sintaxis, operador desconocido", "105"));
            }
        }
    }

    void q2(){
        if (isOpeComp()){
            q3();
        }else{
            PilaError.push(new nodoError(String.valueOf(linea+1), "Error de sintaxis, operador desconocido", "105"));
        }
    }

    void q3() {
        endIndex = getFinalIndex(cadena);
        String aux = cadena.substring(beginIndex, endIndex);
        beginIndex = endIndex;
        AutCadenas = new autCadenas(aux);
        AutNumeros = new autNumeros(aux);
        if (AutNumeros.start() || AutCadenas.start() || veriEsVarEnte(aux) || veriEsVarText(aux)) {
            q1();
        } else
            PilaError.push(
                    new nodoError(String.valueOf(linea+1), "Error de sintaxis, expresión booleana mal construida", "106"));
    }

    void qf() {
        estatus = true;
    }

    int getFinalIndex(String s) {
        int i = beginIndex;
        boolean b = false;
        for (; s.length() > i && !b; i++) {
            if (veriFinalIndex(s.charAt(i))) {
                i--;
                b = true;
            }
        }
        System.out.println("i: " + i);
        return i;
    }

    boolean veriFinalIndex(char c) {
        if (c == '<' || c == '>' || c == '=' || c == '&' || c == '|' || c == '!')
            return true;
        return false;
    }

    boolean veriEsVarBool(String s) {
        for (int i = 0; this.TblSimbolo.tamanio() > i; i++) {
            simbolo Simbolo = TblSimbolo.getSimbolo(i);
            if (Simbolo.getToken().equals("VARBOOLEANO") && Simbolo.getDescripcion().equals(s))
                return true;
        }
        return false;
    }

    boolean veriEsVarEnte(String s) {
        for (int i = 0; this.TblSimbolo.tamanio() > i; i++) {
            simbolo Simbolo = TblSimbolo.getSimbolo(i);
            if (Simbolo.getToken().equals("VARENTERO") && Simbolo.getDescripcion().equals(s))
                return true;
        }
        return false;
    }

    boolean veriEsVarText(String s) {
        for (int i = 0; this.TblSimbolo.tamanio() > i; i++) {
            simbolo Simbolo = TblSimbolo.getSimbolo(i);
            if (Simbolo.getToken().equals("VARTEXTO") && Simbolo.getDescripcion().equals(s))
                return true;
        }
        return false;
    }

    boolean isOpeLog() {
        try {
            String opeLog = cadena.substring(beginIndex, beginIndex + 2);
            System.out.println("ope" + opeLog);
            if (opeLog.equals("&&") || opeLog.equals("||") || opeLog.equals("==")) {
                beginIndex += 2;
                return true;
            }
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    boolean isOpeComp() {
        try {
            String opeComp = cadena.substring(beginIndex, beginIndex + 2);
            System.out.println("ope c: " + opeComp);
            if (opeComp.equals(">=") || opeComp.equals("<=") || opeComp.equals("==") || opeComp.equals("!=")) {
                beginIndex += 2;
                return true;
            } else {
                opeComp = cadena.substring(beginIndex, beginIndex + 1);
                System.out.println("ope1" + opeComp);
                if (opeComp.equals(">") || opeComp.equals("<")) {
                    beginIndex += 1;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("ex: " + e);
            return false;
        }
    }

    public boolean valiEB(String exp){
        ArrayList<String> expresion = new ArrayList<String>();
        String /*aux = "", */op = "", cadena = "";
        System.out.println("\nEXP -> "+exp);
        String[] datos = exp.split("");
        for (int i=0; i<datos.length; i++) {
            char c = datos[i].charAt(0);
            //System.out.println("-->"+c);
            if(veriFinalIndex(c)){ // Encuentra un simbolo de comparacion
                char c2 = datos[i+1].charAt(0);
                //System.out.println("VAR => "+cadena);
                expresion.add(cadena);
                if(veriFinalIndex(c2)){ // Operador doble
                    op = datos[i]+datos[i+1];
                    //System.out.println("op doble  "+op);
                    expresion.add(op);
                    i++;
                }else{ // Operador simple
                    op = String.valueOf(c);
                    //System.out.println("op simple "+op);
                    expresion.add(op);
                }
                //aux += " "+op+" ";
                cadena = "";
            }else{
                cadena+=c;
                //aux+=c;
            }
        }
        if(!cadena.equals("")){
            //System.out.println("ccccc "+cadena);
            expresion.add(cadena);
        }
        //System.out.println("Exp -> "+aux);
        System.out.println(expresion);
        return evaluar(expresion);
    }

    boolean evaluar(ArrayList<String> expresion){
        ArrayList<String> aux = new ArrayList<String>();
        // Primero las aritmeticas, despues las logicas
        for (int i = 0; i < expresion.size(); i++) {
            // Expresiones aritmeticas
            if(expresion.get(i).equals(">") || 
            expresion.get(i).equals("<") || 
            expresion.get(i).equals(">=") || 
            expresion.get(i).equals("<=") || 
            expresion.get(i).equals("==")){
                if(valiExpAri(expresion.get(i-1), expresion.get(i+1))){
                    System.out.println("ExpAri Valida");
                    aux.add(getExpAri(expresion.get(i-1), expresion.get(i), expresion.get(i+1))+"");
                }else{
                    System.out.println("ERROR SEMANTICOOOO 1");
                    PilaError.push(new nodoError(String.valueOf(linea+1),"Error de semantica en la expresion aritmetica" , "302"));
                }
            // Expresiones logicas
            }else if(expresion.get(i).equals("&&")){
                aux.add(expresion.get(i));
            }else if(expresion.get(i).equals("||")){
                aux.add(expresion.get(i));
            }else{
                //aux.add(expresion.get(i));
                if(TblSimbolo.getSimboloToken(expresion.get(i)).getToken().equals("VARBOOLEANO")){
                    aux.add(expresion.get(i));
                }
            }
        }
        System.out.println("exxx "+expresion);
        System.out.println("auxx "+aux);
        // Expresiones logicas
        //int cont=0;
        while(aux.size()>1){
            if(valiExpLog(aux.get(0), aux.get(1), aux.get(2))){
                System.out.println("ITERACION");
                aux.set(2, getExpLog(aux.get(0), aux.get(1), aux.get(2))+"");
                aux.remove(0);
                aux.remove(0);
            }
        }
        System.out.println("! BAN => "+aux.get(0));
        if(aux.get(0).equals("true")){
            return true;
        }else{
            return false;
        }
        
    }

    // Error semantico
    boolean valiExpAri(String varIzq, String varDer){
        if(TblSimbolo.getSimboloToken(varIzq).getToken().equals("VARENTERO") &&
           TblSimbolo.getSimboloToken(varDer).getToken().equals("VARENTERO")){
            return true;
        }else{
            return false;
        }
    }

    boolean getExpAri(String varIzq, String simbolo, String varDer){
        //System.out.println("bueeeee");        
        int vIzq = Integer.parseInt(TblSimbolo.getSimboloToken(varIzq).getValor());
        int vDer = Integer.parseInt(TblSimbolo.getSimboloToken(varDer).getValor());
        boolean res = false;
        if(simbolo.equals(">")){
            if(vIzq > vDer)
                res = true;
        }else if(simbolo.equals("<")){
            if(vIzq < vDer)
                res = true;
        }else if(simbolo.equals(">=")){
            if(vIzq >= vDer)
                res = true;
        }else if(simbolo.equals("<=")){
            if(vIzq <= vDer)
                res = true;
        }else if(simbolo.equals("==")){
            if(vIzq == vDer)
                res = true;
        }
        return res;
    }

    boolean valiExpLog(String varIzq, String simbolo, String varDer){
        if((varIzq.equals("true") || varIzq.equals("false") || TblSimbolo.getSimboloToken(varIzq).getToken().equals("VARBOOLEANO")) &&
           (varDer.equals("true") || varDer.equals("false") || TblSimbolo.getSimboloToken(varDer).getToken().equals("VARBOOLEANO"))){
            System.out.println("Bien construida");
            return true;
        }else{
            System.out.println("FFFFFFFFF");
            PilaError.push(new nodoError(String.valueOf(linea+1),"Error de semantica en la expresion logica" , "309"));
            return false;
        }
    }

    boolean getExpLog(String varIzq, String simbolo, String varDer){
        boolean izq = false, der = false;
        // Clasificacion de lado izquierdo de la expresion
        if(varIzq.equals("true")){
            izq = true;
        }else if(varIzq.equals("false")){
            izq = false;
        }else if(TblSimbolo.getSimboloToken(varIzq).getToken().equals("VARBOOLEANO")){
            String aux = TblSimbolo.getSimboloToken(varIzq).getValor();
            if(aux.equals("verdad")){
                izq = true;
            }else if(aux.equals("falso")){
                izq = false;
            }else{
                PilaError.push(new nodoError(String.valueOf(linea+1),"Error de semantica en la expresion logica (izq)" , "310"));
                System.out.println("ERROR SEMANTICOOOoOo 2");
            }
        }else{
            PilaError.push(new nodoError(String.valueOf(linea+1),"Error de semantica en la expresion logica (izq)" , "311"));
            System.out.println("ERROR SEMANTICOOOoOo 3");
        }
        // Clasificacion de lado derecho de la expresion
        if(varDer.equals("true")){
            der = true;
        }else if(varDer.equals("false")){
            der = false;
        }else if(TblSimbolo.getSimboloToken(varDer).getToken().equals("VARBOOLEANO")){
            String aux = TblSimbolo.getSimboloToken(varDer).getValor();
            if(aux.equals("verdad")){
                der = true;
            }else if(aux.equals("falso")){
                der = false;
            }else{
                PilaError.push(new nodoError(String.valueOf(linea+1),"Error de semantica en la expresion logica (der)" , "312"));
                System.out.println("ERROR SEMANTICOOOoOo 4");
            }
        }else{
            PilaError.push(new nodoError(String.valueOf(linea+1),"Error de semantica en la expresion logica (der)" , "313"));
                System.out.println("ERROR SEMANTICOOOoOo 5");
        }
        // Clasificacion de simbolos de comparacion
        if(simbolo.equals("&&")){
            if(izq && der){
                return true;
            }else{
                return false;
            }
        }else if(simbolo.equals("||")){
            if(izq || der){
                return true;
            }else{
                return false;
            }
        }else{
            PilaError.push(new nodoError(String.valueOf(linea+1),"Error de sintaxis en la estructura de la expresion logica" , "314"));
            System.out.println("ERROR SINTACTICOOOO 6");
            return false;
        }
    }

}
