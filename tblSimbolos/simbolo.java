package tblSimbolos;

public class simbolo {

    public String tipoSimbolo, id, descripcion, valor, nombre, tipo, tamanio, direccion;

    public simbolo(String[] simbolo){
        tipoSimbolo = simbolo[0];
        id = simbolo[1];
        descripcion = simbolo[2];
        valor = simbolo[3];
    }

    public String getToken(){
        return this.tipoSimbolo;
    }

    public String getId(){
        return this.id;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public String getValor(){
        return this.valor;
    }
}
