package analizadores.sintaxis;

import java.util.Arrays;

import analizadores.lexico.token;
import analizadores.sintaxis.Evaluaciones.evaluar;
import errores.nodoError;
import errores.pilaError;
import tblSimbolos.simbolo;
import tblSimbolos.tblSimbolo;

/*
     * [TipoDato, NombreVariable, Delimitador]
     * [NombreVariable, SignoIgual, Contenido, Delimitador]
*/
/* Sintaxis
     
     // DECLARANDO INICIALIZADA
     * TIPODEDATO (entero, texto)
     * v_var (nombre de la variable [UNICO])
     * = (Signo de igual [Asignacion])
     *  CONTENIDO
     * ; DELIMITADOR * [Inicializada] *
     
     // ASIGNACION DE CONTENIDO (VARIABLE YA DECLARADA)
     * v_var (nombre de la variable [UNICO])
     * = (Signo de igual [Asignacion])
     *  CONTENIDO
     * ; DELIMITADOR * [Inicializada] *
 */

public class erVariables {

    String[] lineas;
    String linea;
    token t;
    tblSimbolo TblSimbolo;
    pilaError PilaError;
    int clinea;
    boolean init;
    boolean q0 = false, q1 = false, q2 = false, q3 = false, q4 = false;
    String[] desctd;

    public erVariables(String[] lineas, String linea, token t, tblSimbolo TblSimbolo, pilaError PilaError, int clinea, boolean init){ // 4 o 5
        this.lineas = lineas;
        this.linea = linea;
        this.t = t;
        this.TblSimbolo = TblSimbolo;
        this.PilaError = PilaError;
        this.clinea = clinea;
        this.init = init;
    }

    public void valiER(){
        if(!init){
            // Tipo de dato
            String td = t.getToken(lineas[0]);
            desctd =  td.split(",");
            int numt = Integer.parseInt(desctd[1]);
            if((numt > 10) && (numt < 15) ){
                q0 = true;
                q0();
            }else{
                System.out.println("ERROR, NO SE RECONOCE EL TIPO DE DATO");
                PilaError.push(new nodoError(String.valueOf(clinea+1),"Error semantico, no se reconoce el tipo de dato" , "303"));
            }
        }else if(init){
            // Variables ya declaradas
            //System.out.println("YA DECLARADA");
            try {
                q5();
            } catch (Exception e) {
                System.out.println("ERROR SEMANTICOOOO 777");
                PilaError.push(new nodoError(String.valueOf(clinea+1),"Error de semantica en la expresion "+linea , "304"));
            }
        }
    }

    void q0(){
        if(q0){
            // Nombre de la variable
            String nv = t.getToken(lineas[1]);
            String[] descnv = nv.split(",");
            if(descnv[2].equals("Token no especificado")){ // No es un token existente
                q1 = true;
                q1();
                //System.out.println(t.variables);
            }else{
                System.out.println("ERROR, LA VARIABLE A DECLARAR YA ES UNA PALABRA RESERVADA");
                PilaError.push(new nodoError(String.valueOf(clinea+1),"Error semantico, la variable a deraclarar ya es una palabra reservada" , "305"));
            }
        }
    }

    void q1(){
        if(q0 && q1){
            // Signo de Igualacion
            String si = t.getToken(lineas[2]);
            String[] descsi =  si.split(",");
            if(descsi[2].equals("Operador de asignación")){
                q2 = true;
                q2();
            }else{
                System.out.println("ERROR, NO SE AGREGO EL SIMBOLO DE ASIGNACION");
                PilaError.push(new nodoError(String.valueOf(clinea+1),"Error sintactico, no se agrego el simbolo de asignacion" , "113"));
            }
        }
    }

    void q2(){
        if(q0 && q1 && q2){
            // Contenido de la variable
            String cv = t.getToken(lineas[3]);
            String[] desccv =  cv.split(",");
            // ANALISIS SEMANTICO
            if(desctd[0].equals("ENTERO")){
                if(desccv[0].equals("NUMERO"))
                    q3 = true;
            }else if(desctd[0].equals("TEXTO")){
                if(desccv[0].equals("CADENA"))
                    q3 = true;
            }else if(desctd[0].equals("BOOLEANO")){
                if(desccv[0].equals("TDBOOLEANO"))
                    q3 = true;
            }
            if(!q3){
                System.out.println("ERROR, EL TIPO DE DATO NO COINCIDE CON SU CONTENIDO");
                PilaError.push(new nodoError(String.valueOf(clinea+1),"Error de semantica, el tipo de dato no coincide con su contenido" , "306"));
            }else
                q3();
        } 
    }

    void q3(){
        if(q0 && q1 && q2 && q3){
            // Delimitador ;
            String pc = t.getToken(lineas[4]);
            String[] descpc =  pc.split(",");
            if(descpc[2].equals("Delimitador")){
                q4 = true;
                q4();
            }else{
                System.out.println("ERROR, FALTO AGREGAR EL DELIMITADOR");
                PilaError.push(new nodoError(String.valueOf(clinea+1),"Error de sintaxis, falto agregar el delimitador" , "114"));
            }
        }
        System.out.println("------ ER Variable ------");
        System.out.println("Tipo de dato : "+lineas[0]+" -> "+q0);
        System.out.println("Nombre       : "+lineas[1]+" -> "+q1);
        System.out.println("Asignacion   : "+lineas[2]+" -> "+q2);
        System.out.println("Contenido    : "+lineas[3]+" -> "+q3);
        System.out.println("Delimitador  : "+lineas[4]+" -> "+q4); 
    }

    void q4(){
        if(q0 && q1 && q2 && q3 && q4){
            t.variables.add(lineas[1]);
            System.out.println("hola");
            if(lineas[0].equals("entero"))
                this.TblSimbolo.m_agreSimbolo(new simbolo(("VARENTERO 75 "+lineas[1]+" "+lineas[3]).split(" ")));
            if(lineas[0].equals("texto"))
                this.TblSimbolo.m_agreSimbolo(new simbolo(("VARTEXTO 76 "+lineas[1]+" "+lineas[3]).split(" ")));
            if(lineas[0].equals("booleano"))
                this.TblSimbolo.m_agreSimbolo(new simbolo(("VARBOOLEANO 77 "+lineas[1]+" "+lineas[3]).split(" ")));
            System.out.println("VARIABLE DECLARADA");
        }else{
            System.out.println("NO FUE POSIBLE DECLARAR LA VARIABLE");
            PilaError.push(new nodoError(String.valueOf(clinea+1),"Error de semantica, no pudo ser declarada la variable" , "307"));
        }
        System.out.println("Variables: "+t.variables);
    }

    void q5(){
        String[] contenido = linea.trim().split("=");
        String[] separado = contenido[1].trim().split("\\Q+\\E");
        if(!lineas[1].equals("=")){
            System.out.println("LA ASIGNACION DE LA VARIABLE NO CONTIENE SIMBOLO (=)");
            PilaError.push(new nodoError(String.valueOf(clinea+1),"Error de semantica, la variable a declarar no cuenta con el simbolo de asignacion" , "308"));
        }else if(!lineas[lineas.length-1].equals(";")){
            System.out.println("FALTA EL DELIMITADOR EN LA ASIGNACION DE LA VARIABLE");
            PilaError.push(new nodoError(String.valueOf(clinea+1),"Error de sintaxis, falto el delimitador en la asignacion de la variable" , "115"));
        }else{
            // Validar la expresion
            //System.out.println("    Contenido -> "+Arrays.toString(contenido));
            
            if(TblSimbolo.getSimboloToken(lineas[0]).getToken().equals("VARENTERO")){
                // Evaluar expresion infija
                //System.out.println("Expresion INFIJA");
                evaluar ev = new evaluar(lineas[2],TblSimbolo);
                String resultado = ""+ev.getResultado();
                System.out.println("Resuuuu "+resultado);

                // tipoSimbolo, id, descripcion, valor
                String[] simbol = {"VARENTERO","75",lineas[0],resultado};
                TblSimbolo.m_agreSimbolo(new simbolo(simbol));
            }else{
                System.out.println("Separado  -> "+Arrays.toString(separado));
            }
            
        }
    }

}
