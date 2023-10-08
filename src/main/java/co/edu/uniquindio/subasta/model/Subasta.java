package co.edu.uniquindio.subasta.model;

import co.edu.uniquindio.subasta.controller.servicies.IProductoService;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.services.ISubastaService;

import java.util.ArrayList;
import java.util.List;

public class Subasta implements ISubastaService {
    private static final long serialVersionUID = 1L;
    private Anunciante anuncianteLogueado;
    private Comprador compradorLogueado;
    private ArrayList<Anunciante> listaAnunciante = new ArrayList<>();
    private ArrayList<Comprador> listaCompradores = new ArrayList<>();

    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();


    public Subasta() {
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
        for (Anunciante a : getListaAnunciante()) {
            if (a.getCedula().equals(cedula)) {
                existe = true;
                break;
            }
        }
        return existe;
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

}
