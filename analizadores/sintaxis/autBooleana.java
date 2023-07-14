package analizadores.sintaxis;

public class autBooleana {
    
    String cadena;
    int position = 0;
    boolean acept = false;
  
    public autBooleana(String cadena){
        this.cadena = cadena;
    }
    
    public boolean start(){
        if(cadena.length() > 0)
            q0(cadena);
        if(acept)
            return true;
        else
            return false;
    }

    void q0(String valor){
        String v = valor.trim();
        if(v.equals("verdad") || v.equals("falso") || v.equals("1")  || v.equals("0") )
            acept = true; 
        
    }

}
