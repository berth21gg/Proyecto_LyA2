package errores;

public class nodoError {
    String linea;
    String descripcion;
    String codigo;

    public nodoError(String linea, String descripcion, String codigo) {
        this.linea = linea;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public String getLinea(){
        return this.linea;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public String getCodigo(){
        return this.codigo;
    }
}
