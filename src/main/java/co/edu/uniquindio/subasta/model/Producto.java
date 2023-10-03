package co.edu.uniquindio.subasta.model;

public class Producto {
    private static int contador = 0;
    private String nombre;
    private int id;

    public Producto() {
        this.id = ++contador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
