package co.edu.uniquindio.subasta.model;

import co.edu.uniquindio.subasta.controller.servicies.IProductoServicies;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;

import java.util.ArrayList;
import java.util.List;

public class Subasta implements IProductoServicies {
    private String anuncianteLogueado;
    private ArrayList<Anunciante> listaAnunciante = new ArrayList<>();

    public void registrarAnunciante(Anunciante anunciante) {
        listaAnunciante.add(anunciante);
    }

    public void EliminarAnunciante(Anunciante anunciante) {
        listaAnunciante.remove(anunciante);
    }

    public Anunciante buscarAnunciante(String nombre) {
        Anunciante a = null;
        for (Anunciante e : listaAnunciante) {
            if (e.getNombre().equals(nombre)) {
                a = e;
            }
        }
        return a;
    }



    public Subasta() {
    }

    public ArrayList<Anunciante> getListaAnunciante() {
        return listaAnunciante;
    }

    public String getAnuncianteLogueado() {
        return anuncianteLogueado;
    }

    public void setAnuncianteLogueado(String anuncianteLogueado) {
        this.anuncianteLogueado = anuncianteLogueado;
    }

    public void setListaAnunciante(ArrayList<Anunciante> listaAnunciante) {
        this.listaAnunciante = listaAnunciante;
    }

    @Override
    public List<ProductoDto> obtenerProducto() {
        return null;
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto, String cedula) {
        return false;
    }

    @Override
    public boolean eliminarProducto(String cedula) {
        return false;
    }

    @Override
    public boolean actualizarProducto(String cedulaActual, ProductoDto empleadoDto) {
        return false;
    }
}
