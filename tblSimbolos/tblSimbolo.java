package tblSimbolos;
import java.util.ArrayList;

public class tblSimbolo {

    ArrayList <simbolo> simbolos;

    public tblSimbolo(){
        simbolos = new ArrayList<>();
    }

    public void m_agreSimbolo(simbolo p_simbolo){
        simbolos.add(p_simbolo);
    }

    public simbolo getSimbolo(int posicion){
        return simbolos.get(posicion);
    }

    public int tamanio (){
        return simbolos.size();
    }

    public void limpTabla(){
        simbolos = new ArrayList<>();
    }

    public simbolo getSimboloToken(String descripcion){
        simbolo sim = null;
        for(int i = 0; i < simbolos.size(); i++){
            //System.out.println("Simbolos => "+simbolos.get(i).getDescripcion());
            if(simbolos.get(i).getDescripcion().equals(descripcion)){
                sim = simbolos.get(i);
            }
        }
        return sim;
    }

}
