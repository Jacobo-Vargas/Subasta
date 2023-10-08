package co.edu.uniquindio.subasta.model;

import java.util.ArrayList;

public class Comprador extends Persona{
    private static final long serialVersionUID = 1L;
    private String direccion;
    private final ArrayList<Puja> listaPujas = new ArrayList<>();

    private Usuario usuario;

    public Comprador() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Puja> getListaPujas() {
        return listaPujas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                "direccion='" + direccion + '\'' +
                ", listaPujas=" + listaPujas +
                ", usuario=" + usuario +
                '}';
    }
}
