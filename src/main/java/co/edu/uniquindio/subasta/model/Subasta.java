package co.edu.uniquindio.subasta.model;

import co.edu.uniquindio.subasta.controller.servicies.IProductoService;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.services.ISubastaService;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subasta implements ISubastaService, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Anunciante anuncianteLogueado;
    private Comprador compradorLogueado;
    private ArrayList<Anunciante> listaAnunciante = new ArrayList<>();
    private ArrayList<Comprador> listaCompradores = new ArrayList<>();

    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();


    public Subasta() {
    }


    //--------------------------------   Registro   --------------------------------------//
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
        for (Anunciante a : getListaAnunciante()) {
            if (a.getCedula().equals(cedula)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    //---------------------------------- CRUD  PRODUCTO --------------------------------//
    @Override
    public List<Producto> obtenerProducto() {
        return anuncianteLogueado.getListaProducto();
    }

    @Override
    public boolean agregarProducto(Producto producto) throws ProductoException {
        if (anuncianteLogueado.getListaProducto().add(producto)) {
            return true;
        } else {
            throw new ProductoException("No se agrego el producto.");
        }

    }

    @Override
    public boolean eliminarProducto(Producto producto) throws ProductoException {


        if (anuncianteLogueado.getListaProducto().removeIf(p -> p.getCodigo().equals(producto.getCodigo()))) {
            return true;
        } else {
            throw new ProductoException("No se pudo eliminar el producto.");
        }
    }

    @Override
    public boolean actualizarProducto(Producto producto) throws ProductoException {
        boolean actualizado = false;
        for (Producto p : anuncianteLogueado.getListaProducto()) {
            if (p.getCodigo().equals(producto.getCodigo())) {
                p.setNombre(producto.getNombre());
                p.setTipoArticulo(producto.getTipoArticulo());
                actualizado = true;

            }
        }
        if (!actualizado) {
            throw new ProductoException("No se pudo actualizar el producto.");
        }
        return actualizado;
    }


    // ----------------------------------- CRUD ANUNCIO ------------------------------------//

    @Override
    public List<Anuncio> obtenerAnuncio() {
        return anuncianteLogueado.getListaAnucio();
    }

    @Override
    public boolean agregarAnuncio(Anuncio anuncio) throws AnuncioException {
        if (anuncianteLogueado.getListaAnucio().add(anuncio)) {
            return true;
        } else {
            throw new AnuncioException("No se pudo registrar el anuncio");
        }
    }

    @Override
    public boolean eliminarAnuncio(Anuncio anuncio) {
        return false;
    }

    @Override
    public boolean actuaizarAnuncio(Anuncio anuncio) {
        return false;
    }

    //----------------------------------Crud Puja------------------------

    @Override
    public List<Puja> obtenerLitaPuja() {
        return compradorLogueado.getListaPujas();
    }

    @Override
    public boolean realizarPuja(Puja puja) throws Exception {
        if (compradorLogueado.getListaPujas().add(puja)) {
            for (int i = 0; i < listaAnunciante.size(); i++) {

                for (int j = 0; j < listaAnunciante.get(i).getListaAnucio().size(); j++) {

                    for (int h = 0; h < listaAnunciante.get(i).getListaAnucio().get(i).getListaPujas().size(); h++) {

                        if (listaAnunciante.get(i).getListaAnucio().get(i).getListaPujas().get(h).getCodigo().equals(puja.getCodigo())) {

                            listaAnunciante.get(i).getListaAnucio().get(i).getListaPujas().add(puja);
                        }
                    }
                }

            }
            return true;
        } else {
            throw new Exception("No se puedo relizar la Puja");
        }
    }

    @Override
    public boolean elimnarPuja(Puja puja) throws Exception {
        if (compradorLogueado.getListaPujas().removeIf(Puja -> Puja.getCodigo().equals(puja.getCodigo()))) {
            return true;
        } else {
            throw new Exception("No se puedo elimanar la puja");
        }
    }

    @Override
    public boolean actulizarPuja(Puja puja) throws Exception {
        boolean actulizado = false;
        for (Puja p : compradorLogueado.getListaPujas()) {
            if (p.getCodigo().equals(puja.getCodigo())) {
                p.setDireccion(puja.getDireccion());
                p.setOfertaInicial(puja.getOfertaInicial());
                p.setFechaPuja(puja.getFechaPuja());
                actulizado = true;
            }
        }
        if (actulizado) {
            return actulizado;
        } else {
            throw new Exception("No se pudo actulizar la puja");
        }
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

    public Anunciante getAnuncianteLogueado() {
        return anuncianteLogueado;
    }

    public void setAnuncianteLogueado(Anunciante anuncianteLogueado) {
        this.anuncianteLogueado = anuncianteLogueado;
    }

    public Comprador getCompradorLogueado() {
        return compradorLogueado;
    }

    public void setCompradorLogueado(Comprador compradorLogueado) {
        this.compradorLogueado = compradorLogueado;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public String toString() {
        return "Subasta{" +
                "anuncianteLogueado=" + anuncianteLogueado +
                ", compradorLogueado=" + compradorLogueado +
                ", listaAnunciante=" + listaAnunciante +
                ", listaCompradores=" + listaCompradores +
                ", listaUsuarios=" + listaUsuarios +
                '}';
    }
}
