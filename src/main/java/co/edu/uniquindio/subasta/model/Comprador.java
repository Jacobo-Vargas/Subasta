package co.edu.uniquindio.subasta.model;

import java.io.Serial;
import java.util.ArrayList;

public class Comprador extends Persona{
    @Serial
    private static final long serialVersionUID = 1L;
    private String direccion;
    private ArrayList<Puja> listaPujas = new ArrayList<>();

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

    public void setListaPujas(ArrayList<Puja> listaPujas) {
        this.listaPujas = listaPujas;
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
