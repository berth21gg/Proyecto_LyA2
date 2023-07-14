package analizadores.lexico;
public class alfabeto {
    public boolean validar(String linea){
        int tamanio = linea.length();
        char c;
        for(int i=0; tamanio > i; i++){
            c = linea.charAt(i); 
            if(!((c >= 32 && c <=125 ) || c == 164 || c==165))
                return false;
        }
        return true;
    }
}
