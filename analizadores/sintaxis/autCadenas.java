package analizadores.sintaxis;

public class autCadenas {

    String cadena;
    int position = 0;
    boolean acept = false;
  
    public autCadenas(String cadena){
        this.cadena = cadena;
    }
    
    public boolean start(){
        if(cadena.length() > 0)
            q0(cadena.charAt(0));
        if(acept)
            return true;
        else
            return false;
    }

    void q0(char c){
        if(c == '"'){
            position ++;
            if(cadena.length() > position)
                q1(cadena.charAt(position)); 
        }
    }

    void q1(char c){
        if((c >= 32 && c <= 125) || c == 164 || c == 165){
            position++;
            if(c == 34)
                q3(c);
            else{
                if(cadena.length() > position)
                    q1(cadena.charAt(position));
            }
        }
    }

    void q3(char c){
        if(c == '"' && cadena.length() == position)
            acept = true;
    }
}
