package analizadores.sintaxis;

public class consola {
    String contenido = "";
    public consola(){
    }

    public String getConsola(){
        return contenido;
    }

    public void addConsola(String texto){
        contenido += "\n"+texto;
    }

    public void vaciar(){
        contenido = "";
    }

}
