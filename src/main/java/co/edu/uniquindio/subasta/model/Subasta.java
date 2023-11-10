package co.edu.uniquindio.subasta.model;

import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.model.services.ISubastaService;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import co.edu.uniquindio.subasta.util.Persistencia;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return anuncianteLogueado.getListaAnuncio();
    }

    @Override
    public int agregarAnuncio(Anuncio anuncio) throws AnuncioException {
        int opcion = 0;
        try {
            if (listaAnuncios.stream().anyMatch(anuncio1 -> anuncio1.getCodigo().equals(anuncio.getCodigo()))) {
                opcion = 1;
                throw new AnuncioException("Ya existe el codigo.");
            } else if (anuncianteLogueado.getListaAnuncio().size() == 3) {
                opcion = 2;
                throw new AnuncioException("Tiene 3 anuncios activos.");
            } else {
                opcion = 3;
                anuncianteLogueado.getListaAnuncio().add(anuncio);
                listaAnuncios.add(anuncio);
                return opcion;
            }

        } catch (AnuncioException e) {
            System.out.println(e.getMessage());
            return opcion;

        }
    }


    @Override
    public boolean eliminarAnuncio(Anuncio anuncio) throws AnuncioException {
        if (anuncianteLogueado.getListaAnuncio().removeIf(anuncio1 -> anuncio1.getCodigo().equals(anuncio.getCodigo()))
                && listaAnuncios.removeIf(anuncio1 -> anuncio1.getCodigo().equals(anuncio.getCodigo()))) {
            return true;
        } else {
            throw new AnuncioException("No se puedo eliminar el anuncio.");
        }
    }

    @Override
    public boolean actualizarAnuncio(Anuncio anuncio) throws AnuncioException {

        if (modificarAnuncio(listaAnuncios, anuncio) && modificarAnuncio(anuncianteLogueado.getListaAnuncio(), anuncio)) {
            return true;
        } else {
            throw new AnuncioException("No se pudo actualizar.");
        }

    }

    public boolean modificarAnuncio(ArrayList<Anuncio> lista, Anuncio anuncio) {
        boolean actualizado = false;
        for (Anuncio a : lista) {
            if (a.getCodigo().equals(anuncio.getCodigo())) {
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

    public boolean verificarPujaRepetida(Puja puja) {
        for (Puja p : compradorLogueado.getListaPujas()) {
            if (p.getCodigo().equals(puja.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarPuja(Puja puja) throws Exception {

        if (compradorLogueado.getListaPujas().removeIf(Puja -> Puja.getCodigo().equals(puja.getCodigo() ))) {
            System.out.println(compradorLogueado.getListaPujas());

            //for para quitarlo a la lista de anuncios
            for (Anuncio anuncio : listaAnuncios) {
                for (Puja puja1 : anuncio.getListaPujas()) {
                    if (puja.getCodigo().equals(puja1.getCodigo())) {
                        System.out.println(anuncio);
                        anuncio.getListaPujas().remove(puja);
                        System.out.println(anuncio);
                    }
                }
            }
            System.out.println();
            System.out.println();
            for (Anunciante anunciante : listaAnunciante) {

                for (Anuncio anuncio : anunciante.getListaAnuncio()) {

                    for (Puja puja1 : anuncio.getListaPujas()) {
                        if (puja.getCodigo().equals(puja1.getCodigo())) {
                            System.out.println(anuncio);
                            anuncio.getListaPujas().remove(puja);
                            System.out.println(anuncio);
                        }
                    }
                }
            }
            return true;
        }
         else {
            AlertaUtil.mostrarMensajeError("Codigo puja repetido");
        }
        throw new Exception("No se puede realizar puja");
    }

    @Override
    public boolean realizarPuja(Puja puja, String codigo) throws Exception {
        if (!(verificarPujaRepetida(puja))) {
            List<Puja>lista=compradorLogueado.getListaPujas().stream().filter(Puja->Puja.getCodigo().equals(puja.getCodigo())).collect(Collectors.toList());
            if(lista.size()==3){
                AlertaUtil.mostrarMensajeOk("el comprador solo puede hacer 3 pujas.");
            }else {

                ArrayList<Puja> list = compradorLogueado.getListaPujas();
                list.add(puja);
                compradorLogueado.setListaPujas(list);
                // metodo que agrega la puja a la lista global
                for (Anuncio anuncio : listaAnuncios) {
                    if (anuncio.getCodigo().equals(codigo)) {
                        anuncio.getListaPujas().add(puja);
                        System.out.println(anuncio);
                        break;
                    }
                }

                // metodo que agrega la puja a el anunciante

                for (Anunciante anunciante : listaAnunciante) {
                    for (Anuncio anunciooo : anunciante.getListaAnuncio()) {
                        if (anunciooo.getCodigo().equals(codigo)) {
                            anunciooo.getListaPujas().add(puja);
                            System.out.println(anunciooo);

                        }
                    }
                }
                return true;

            }
        } else {
            AlertaUtil.mostrarMensajeOk("numero de codigo repetiodo.");
        }
        return false;

    }

    @Override
    public List<Puja> listaPujasComprador(String codigo) {
        List<Puja>lista=new ArrayList<>();
        for(Puja puja:compradorLogueado.getListaPujas()){
            if(puja.getAnuncio().getCodigo().equals(codigo)){
                lista.add(puja);
            }
        }
        return lista;
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
