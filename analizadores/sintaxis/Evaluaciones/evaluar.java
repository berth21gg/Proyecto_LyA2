package analizadores.sintaxis.Evaluaciones;

import tblSimbolos.tblSimbolo;

public class evaluar {

    String expresion;
    tblSimbolo TblSimbolo;
    
    public evaluar(String expresion, tblSimbolo TblSimbolo){
        this.expresion = expresion;
        this.TblSimbolo = TblSimbolo;
    }

    public int getResultado(){
        System.out.println("Expresión -> "+expresion);
        expresion = getVariables();
        infija inf = new infija(expresion);
        String expPostfija = inf.getPostfija();
        postfija postf = new postfija(expPostfija);
        int resultado = postf.getResultado();
        return resultado;
    }

    String getVariables(){
        String[] datos = expresion.split("");
        String cadena = "", exp = "";
        for (int i = 0; i < datos.length; i++) {
            String dato = datos[i];
            try {
                int aux = Integer.parseInt(dato);
                exp += aux;
                if(!cadena.equals("")){
                    System.out.println("cad1 "+cadena);
                    cadena="";
                }
                // Es numero, todo chido
            } catch (Exception e) {
                if(dato.equals("+")||dato.equals("-")||dato.equals("*")||
                dato.equals("/")||dato.equals("(")||dato.equals(")")){
                    // Es un operador, todo chido
                    if(!cadena.equals("")){
                        System.out.println("cad2 "+cadena);
                        System.out.println(TblSimbolo.getSimboloToken(cadena).getValor());
                        exp+=TblSimbolo.getSimboloToken(cadena).getValor();
                        cadena="";
                    }
                    exp+=dato;
                }else{
                    cadena += dato;
                }
            }
            //System.out.println("---> "+exp);
        }
        return exp;
        /*String[] exp = expresion.split("[-/*+()]");
        for (String e : exp) {
            System.out.println("---> "+e);
        }*/
    }
    
}
