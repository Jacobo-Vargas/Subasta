package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IModelFactoryController;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.*;
import co.edu.uniquindio.subasta.mapping.mappers.MapperSubasta;
import co.edu.uniquindio.subasta.model.*;
import co.edu.uniquindio.subasta.util.Persistencia;
import co.edu.uniquindio.subasta.util.SubastaUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IModelFactoryController {
    Subasta subasta;
    MapperSubasta mapper = MapperSubasta.INSTANCE;


    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {

        System.out.println("invocación clase singleton");

        // crearCopiaDeSeguridad(); // se crea copia del xml una vez inicia el sistema

        //1. inicializar datos y luego guardarlo en archivos

       // cargarDatosBase();
        //salvarDatosPrueba();

        //2. Cargar los datos de los archivos
        //cargarDatosDesdeArchivos();

        //3 binario

        //guardarResourceBinario();
        //cargarResourceBinario();

        //4 XML

        //guardarResourceXML();
        cargarResourceXML();

        if (subasta == null) { //Siempre se debe verificar si la raiz del recurso es null
            cargarDatosBase();
        }
        registrarAccionesSistema("Inicio de sistema", 1, "INICIOAPP");
    }

    private void cargarDatosBase() {
        subasta = SubastaUtil.cargarDatos();
    }











//  --------------------------------------- Registro ---------------------------

    @Override
    public boolean agregarAnunciante(AnuncianteDto anuncianteDto) {
        try {

            if (!(subasta.verificarExistenciaAnunciante(anuncianteDto.cedula()))) {
                getSubasta().getListaAnunciante().add(mapper.anuncianteDtoToAnunciante(anuncianteDto));
                registrarAccionesSistema("Agregar Anunciante", 1, "agregarAnunciante");
                guardarResourceXML();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean agregarComprador(CompradorDto compradorDto) {
        try {
            if (!(subasta.verificarExistenciaComprador(compradorDto.cedula()))) {
                getSubasta().getListaCompradores().add(mapper.compradorDtoToComprador(compradorDto));
                registrarAccionesSistema("Agregar Comprador", 1, "agregarComprador");
                guardarResourceXML();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


//---------------------------------------Login ------------------------------------------

    @Override
    public boolean verificarAccesoComprador(String cedula, String contrasenia) {
        boolean acceso = false;

        for (Comprador c : getSubasta().getListaCompradores()) {
            if (c.getCedula().equals(cedula) && c.getUsuario().getContrasenia().equals(contrasenia)) {
                getSubasta().setCompradorLogueado(c);
                registrarAccionesSistema( c.getNombre()+ " inicio sesión.", 1, "INICIO DE SESIÓN");
                acceso = true;
            }
        }
        return acceso;
    }

    @Override
    public boolean verificarAccesoAnunciante(String cedula, String contrasenia) {
        boolean acceso = false;

        for (Anunciante a : getSubasta().getListaAnunciante()) {
            if (a.getCedula().equals(cedula) && a.getUsuario().getContrasenia().equals(contrasenia)) {
                getSubasta().setAnuncianteLogueado(a);
                registrarAccionesSistema(a.getNombre()+ " inicio sesión.", 1, "INICIO DE SESIÓN");
                acceso = true;
            }
        }
        return acceso;
    }


    public boolean restaurarLogueo() {

        if(getSubasta().getAnuncianteLogueado() != null){
            registrarAccionesSistema(getSubasta().getAnuncianteLogueado().getNombre()+" cerró sesión.",1,"CIERRE DE SESIÓN.");
            getSubasta().setAnuncianteLogueado(null);
        }
        if(getSubasta().getCompradorLogueado() != null){
            registrarAccionesSistema(getSubasta().getCompradorLogueado().getNombre()+" cerró sesion.",1,"CIERRE DE SESIÓN.");
            getSubasta().setCompradorLogueado(null);
        }
        return getSubasta().getAnuncianteLogueado() == null && getSubasta().getCompradorLogueado() == null;
    }


    //-------------------------------   PERSISTENCIA ------------------------------------

    private void crearCopiaDeSeguridad() {
        Persistencia.crearCopiaSeguridadXML();
        registrarAccionesSistema("Copia de seguridad", 1, "crearCopiaSeguridad");
    }

    private void cargarResourceXML() {
        subasta = Persistencia.cargarRecursoSubastaXML();
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoSubastaXML(subasta);
    }

    private void cargarResourceBinario() {
        subasta = Persistencia.cargarRecursoSubastaBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoBancoBinario(subasta);
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    private void cargarDatosDesdeArchivos() {
        subasta = new Subasta();
        try {
            Persistencia.cargarDatosArchivos(subasta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarCompradores(getSubasta().getListaCompradores());
            Persistencia.guardarAnunciantes(getSubasta().getListaAnunciante());
            // Persistencia.guardarProductos(getSubasta().getAnuncianteLogueado().getListaProducto());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    ------------------------------------------ CRUD PRODUCTO ---------------------------------------------
    @Override
    public List<ProductoDto> obtenerProducto() {
        ArrayList<Producto> lista = new ArrayList<>(getSubasta().obtenerProducto());
        return mapper.getProductoDto(lista);
    }

    @Override
    public List<AnuncianteDto> obtenerListaAnunciante() {
        ArrayList<Anunciante>listaAnunciante= new ArrayList<>();
        listaAnunciante=getSubasta().getListaAnunciante();
        return  mapper.getListaAnunciante(listaAnunciante);

    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto) throws ProductoException {
        Producto producto = mapper.productoDtoToProducto(productoDto);
        if (getSubasta().agregarProducto(producto)) {
            guardarResourceXML();
            registrarAccionesSistema(getSubasta().getAnuncianteLogueado().getNombre() + " agregó un producto.", 1, "AGREGAR PRODUCTO");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(ProductoDto productoDto) throws ProductoException {
        Producto producto = mapper.productoDtoToProducto(productoDto);

        if (getSubasta().eliminarProducto(producto)) {
            registrarAccionesSistema(getSubasta().getAnuncianteLogueado().getNombre() + " eliminó un producto.", 1, "ELIMINAR PRODUCTO");

            guardarResourceXML();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean actualizarProducto(ProductoDto productoDto) throws ProductoException {

        Producto producto = mapper.productoDtoToProducto(productoDto);

        if (getSubasta().actualizarProducto(producto)) {
            registrarAccionesSistema(getSubasta().getAnuncianteLogueado().getNombre() + " actualizó un producto.", 1, "ACTUALIZAR PRODUCTO");

            guardarResourceXML();
            return true;
        } else {
            return false;
        }
    }


//    ---------------------------------------- CRUD ANUNCIO ----------------------------//


    @Override
    public List<AnuncioDto> obtenerAnuncio() {
        ArrayList<Anuncio> lista = new ArrayList<>(getSubasta().obtenerAnuncio());
        return mapper.getAnunciosDto(lista);
    }

    @Override
    public String recuperarNombreAnunciante() {
        return getSubasta().getAnuncianteLogueado().getNombre();
    }

    @Override
    public boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        Anuncio anuncio = mapper.anuncioDtoToAnuncio(anuncioDto);
        if(getSubasta().agregarAnuncio(anuncio)){
            guardarResourceXML();
            registrarAccionesSistema("Se agrego un anuncio.",1,"AGREGAR ANUNCIO");
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean eliminarAnuncio(AnuncioDto anuncioDto) {
        return false;
    }

    @Override
    public boolean actuaizarAnuncio(AnuncioDto anuncioDto) {
        return false;
    }

//----------------------------Crud Puja---------------------------------------------


    @Override
    public List<PujaDto> obtenerLitaPuja() { //esta en Dto por es la lista que va mostrar
        ArrayList<Puja>listaPuja=new ArrayList<>(getSubasta().obtenerLitaPuja());
        return mapper.getPujaDto(listaPuja);

    }

    @Override
    public boolean realizarPuja(PujaDto pujaDto) throws Exception {
        Puja puja=mapper.pujaDtoToPuja(pujaDto);
        if(getSubasta().realizarPuja(puja)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean elimnarPuja(PujaDto pujaDto) throws Exception {
        Puja puja=mapper.pujaDtoToPuja(pujaDto);
        if(getSubasta().eliminarPuja(puja)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean actulizarPuja(PujaDto pujaDto) throws Exception {
        Puja puja=mapper.pujaDtoToPuja(pujaDto);
        if(getSubasta().actualizarPuja(puja)){
            return true;
        }else {
            return  false;
        }
    }

    @Override
    public List<AnuncioDto> obtenerListaNuncio() {
        List<Anuncio>lista=new ArrayList<>();
        lista=getSubasta().obtenerListaAnuncio();
        List<AnuncioDto>listaRetornar=new ArrayList<>();
        for(int i=0;i<lista.size();i++){
            Anuncio anuncio=lista.get(i);
            listaRetornar.add(mapper.anuncioToAnuncioDto(anuncio));
        }
        return listaRetornar;
    }

    @Override
    public Anuncio salvarAnuncio(String nombre) {
        for(Anuncio anuncio:getSubasta().obtenerListaAnuncio()){
            if(anuncio.getNombre().equals(nombre)){
                return anuncio;
            }
        }
        return null;
    }
//---------------------------- GETTERS Y SETTERS ------------------------------------
    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }
    public Subasta getSubasta() {
        return subasta;
    }
}
