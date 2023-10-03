package co.edu.uniquindio.subasta.model;

import java.util.ArrayList;

public class Anunciante extends Persona {
    private ArrayList<Producto> listaProducto = new ArrayList<>();
    private ArrayList<Anuncio> listaAnucio = new ArrayList<>();

    private Usuario usuario;

    public Anunciante() {
    }


    public void registrarProducto(Producto producto) {
        listaProducto.add(producto);
    }

    public void EliminarProducto(Producto producto) {
        listaProducto.remove(producto);
    }

    public Producto buscarProducto(int id) {
        Producto a = null;
        for (Producto e : listaProducto) {
            if (e.getId() == id) {
                a = e;
            }
        }
        return a;
    }

    public ArrayList<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(ArrayList<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public ArrayList<Anuncio> getListaAnucio() {
        return listaAnucio;
    }

    public void setListaAnucio(ArrayList<Anuncio> listaAnucio) {
        this.listaAnucio = listaAnucio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
