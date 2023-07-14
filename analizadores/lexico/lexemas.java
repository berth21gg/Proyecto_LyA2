package analizadores.lexico;
import java.util.ArrayList;
import java.util.List;

public class lexemas {    
   String[] getLexemas(String linea){
    List<String> listLexemas = new ArrayList<String>();
    String[] auxiliar = linea.split(" ");
    for(String a: auxiliar)
      if(!a.equals(""))
        listLexemas.add(a);
    return listLexemas.toArray(new String[0]);
   }
}
