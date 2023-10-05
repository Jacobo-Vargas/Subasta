package co.edu.uniquindio.subasta.model;

import co.edu.uniquindio.subasta.controller.servicies.IProductoServicies;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.services.ISubastaService;

import java.util.ArrayList;
import java.util.List;

public class Subasta implements IProductoServicies, ISubastaService {
    private Anunciante anuncianteLogueado;
    private ArrayList<Anunciante> listaAnunciante = new ArrayList<>();
    private ArrayList<Comprador> listaCompradores = new ArrayList<>();


    public Subasta() {
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

    public Anunciante getAnuncianteLogueado() {
        return anuncianteLogueado;
    }

    public void setAnuncianteLogueado(Anunciante anuncianteLogueado) {
        this.anuncianteLogueado = anuncianteLogueado;
    }


    //--------------------------------   Registro   --------------------------------------
    @Override
    public boolean verificarExistenciaComprador(String cedula) {
        boolean existe = false;
        for (Comprador c : getListaCompradores()) {
            if (c.getCedula().equals(cedula)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    @Override
    public boolean verificarExistenciaAnunciante(String cedula) {
        boolean existe = false;
        for (Anunciante a: getListaAnunciante()) {
            if (a.getCedula().equals(cedula)){
                existe = true;
                break;
            }
        }
        return existe;
    }

    public void registrarAnunciante(Anunciante anunciante) {
        listaAnunciante.add(anunciante);
    }

    public void registrarComprador(Comprador comprador) {
        listaCompradores.add(comprador);
    }


    //----------------------listas--------------------------------

    public ArrayList<Anunciante> getListaAnunciante() {
        return listaAnunciante;
    }

    public void setListaAnunciante(ArrayList<Anunciante> listaAnunciante) {
        this.listaAnunciante = listaAnunciante;
    }

    public ArrayList<Comprador> getListaCompradores() {
        return listaCompradores;
    }

    public void setListaCompradores(ArrayList<Comprador> listaCompradores) {
        this.listaCompradores = listaCompradores;
    }
}
