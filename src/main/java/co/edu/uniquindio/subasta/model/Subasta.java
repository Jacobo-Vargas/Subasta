package co.edu.uniquindio.subasta.model;

import co.edu.uniquindio.subasta.controller.servicies.IProductoService;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.services.ISubastaService;
import co.edu.uniquindio.subasta.util.Persistencia;

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
    private ArrayList<Anuncio> listaAnuncios = new ArrayList<>();

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
                break;

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
    public int agregarAnuncio(Anuncio anuncio) throws AnuncioException {
        int opcion = 0;
        try{
            if(listaAnuncios.stream().anyMatch(anuncio1 -> anuncio1.getCodigo().equals(anuncio.getCodigo()))){
                opcion = 1;
                throw new AnuncioException("Ya existe el codigo.");
            }else if(anuncianteLogueado.getListaAnucio().size() == 3){
                opcion = 2;
                throw new AnuncioException("Tiene 3 anuncios activos.");
            }else{
                opcion = 3;
                anuncianteLogueado.getListaAnucio().add(anuncio);
                listaAnuncios.add(anuncio);
                return opcion;
            }

        }catch (AnuncioException e){
            System.out.println(e.getMessage());
            return opcion;

        }
    }


    @Override
    public boolean eliminarAnuncio(Anuncio anuncio) throws AnuncioException {
        if(anuncianteLogueado.getListaAnucio().removeIf(anuncio1 -> anuncio1.getCodigo().equals(anuncio.getCodigo()))
                && listaAnuncios.removeIf(anuncio1 -> anuncio1.getCodigo().equals(anuncio.getCodigo()))){
            return true;
        }else{
            throw new AnuncioException("No se puedo eliminar el anuncio.");
        }
    }

    @Override
    public boolean actualizarAnuncio(Anuncio anuncio) throws AnuncioException {

        if(modificarAnuncio(listaAnuncios,anuncio) && modificarAnuncio(anuncianteLogueado.getListaAnucio(),anuncio)){
            return true;
        }else{
            throw new AnuncioException("No se pudo actualizar.");
        }

    }

    public boolean modificarAnuncio(ArrayList<Anuncio> lista,Anuncio anuncio){
        boolean actualizado = false;
        for (Anuncio a: lista) {
            if(a.getCodigo().equals(anuncio.getCodigo())){
                a.setValorInicial(anuncio.getValorInicial());
                a.setProducto(anuncio.getProducto());
                a.setDescripcion(anuncio.getDescripcion());
                a.setFoto(anuncio.getFoto());
                a.setFechaPublicacion(anuncio.getFechaPublicacion());
                a.setFechaTerminacion(anuncio.getFechaTerminacion());
                a.setNombre(anuncio.getNombre());
                a.setFoto(anuncio.getFoto());
                actualizado = true;
                break;

            }
        }
        return actualizado;
    } // para modificar la lsta de el anunciante y la global

//    ----------------------------------Crud Puja------------------------

    @Override
    public List<Puja> obtenerLitaPuja() {
        return compradorLogueado.getListaPujas();
    }

    @Override
    public boolean realizarPuja(Puja puja, String codigo) throws Exception {
        for(Puja pujaa:compradorLogueado.getListaPujas()){
            if(pujaa.getCodigo().equals(puja.getCodigo())){
                throw new Exception("la puja esta repetida");
            }
        }
        compradorLogueado.getListaPujas().add(puja);
        System.out.println(compradorLogueado.getListaPujas().size());
        System.out.println(compradorLogueado.getListaPujas().get(0));
        for(Anuncio anuncio:listaAnuncios){
            if(anuncio.getCodigo().equals(codigo)){
                anuncio.getListaPujas().add(puja);
                System.out.println(anuncio.getListaPujas().get(0));
                System.out.println(anuncio.getListaPujas().size());
                return true;
            }
        }
        throw new Exception("la puja no se pudo agregar");
    }


    @Override
    public boolean eliminarPuja(Puja puja) throws Exception {
        if (compradorLogueado.getListaPujas().removeIf(Puja -> Puja.getCodigo().equals(puja.getCodigo()))) {
            return true;
        } else {
            throw new Exception("No se puedo elimanar la puja");
        }
    }

    @Override
    public boolean actualizarPuja(Puja puja) throws Exception {
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


    @Override
    public List<Anuncio> obtenerListaAnuncio() {
        return listaAnuncios;

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

    public ArrayList<Anuncio> getListaAnuncios() {
        return listaAnuncios;
    }

    public void setListaAnuncios(ArrayList<Anuncio> listaAnuncios) {
        this.listaAnuncios = listaAnuncios;
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
