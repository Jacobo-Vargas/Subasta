package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IModelFactoryController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.mapping.mappers.MapperSubasta;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Comprador;
import co.edu.uniquindio.subasta.model.Producto;
import co.edu.uniquindio.subasta.model.Subasta;
import co.edu.uniquindio.subasta.util.Persistencia;
import co.edu.uniquindio.subasta.util.SubastaUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IModelFactoryController {
    Subasta subasta;
    MapperSubasta mapper = MapperSubasta.INSTANCE;


    public Subasta getSubasta() {
        return subasta;
    }


    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {

        System.out.println("invocación clase singleton");
        if(subasta == null){
            registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");

            //1. inicializar datos y luego guardarlo en archivos

            // cargarDatosBase();
            //salvarDatosPrueba();

            //2. Cargar los datos de los archivos
             //cargarDatosDesdeArchivos();

            //3. Guardar y Cargar el recurso serializable binario
            //cargarResourceBinario();
            //guardarResourceBinario();

            //4. Guardar y Cargar el recurso serializable XML

            cargarResourceXML();
            guardarResourceXML();

            //Siempre se debe verificar si la raiz del recurso es null
        }




    }

    private void cargarDatosBase() {
        subasta = SubastaUtil.cargarDatos();
    }

    public boolean  restaurarLogueo(){
        Comprador comprador = null;
        Anunciante anunciante = null;
        getSubasta().setCompradorLogueado(comprador);
        getSubasta().setAnuncianteLogueado(anunciante);
        if(getSubasta().getAnuncianteLogueado() == null && getSubasta().getCompradorLogueado() == null){
            return true;
        }else{
            return false;
        }
    }


//    ------------------------------------------ CRUD PRODUCTO ---------------------------------------------
    @Override
    public List<ProductoDto> obtenerProducto() {
        ArrayList<Producto> lista = new ArrayList<>(getSubasta().getAnuncianteLogueado().getListaProducto());
        return mapper.getProductoDto(lista);
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto) {
        boolean exito = false;
        Producto producto = mapper.productoDtoToProducto(productoDto);
        for (Anunciante a : getSubasta().getListaAnunciante()) {
            if (a.getCedula().equals(getSubasta().getAnuncianteLogueado().getCedula())) {
                a.getListaProducto().add(producto);
                registrarAccionesSistema("Agregar producto", 1, "agregarProducto");
                exito = true;
                guardarResourceXML();
            }
        }
        return exito;
    }

    @Override
    public boolean eliminarProducto(ProductoDto productoDto) {
        boolean eliminado = false;
        Producto producto = mapper.productoDtoToProducto(productoDto);
        for (Producto p: getSubasta().getAnuncianteLogueado().getListaProducto()) {
            if(p.getCodigo().equals(producto.getCodigo())){
                getSubasta().getAnuncianteLogueado().getListaProducto().remove(p);
                registrarAccionesSistema("Eliminar producto", 1, "eliminarProducto");
                eliminado = true;
                guardarResourceXML();
                break;

            }
        }
        return eliminado;
    }

    @Override
    public boolean actualizarProducto(ProductoDto empleadoDto) {
        return false;
    }
//  --------------------------------------- Registro ---------------------------


    @Override
    public boolean agregarAnunciante(AnuncianteDto anuncianteDto) {
        try{

            if(!(subasta.verificarExistenciaAnunciante(anuncianteDto.cedula()))){
                getSubasta().getListaAnunciante().add(mapper.anuncianteDtoToAnunciante(anuncianteDto));
                System.out.println(getSubasta().getListaAnunciante().size());
                salvarDatosPrueba();
                guardarResourceXML();
                return true;
            }else{
                System.out.println(getSubasta().getListaAnunciante().size());
                return false;
            }

        }catch (Exception e){
            return false;
        }

    }
    @Override
    public boolean agregarComprador(CompradorDto compradorDto) {
        try{
            if(!(subasta.verificarExistenciaComprador(compradorDto.cedula()))){
                Comprador c = mapper.compradorDtoToComprador(compradorDto);
                getSubasta().getListaCompradores().add(c);
                System.out.println(getSubasta().getListaCompradores().size());
                salvarDatosPrueba();
                guardarResourceXML();
                return true;
            }else{
                System.out.println(getSubasta().getListaCompradores().size());
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }





//---------------------------------------Login ------------------------------------------

    @Override
    public boolean verificarAccesoComprador(String cedula, String contrasenia) {
        boolean acceso = false;

        for (Comprador c: getSubasta().getListaCompradores()) {
            if(c.getCedula().equals(cedula) && c.getUsuario().getContrasenia().equals(contrasenia)){
                getSubasta().setCompradorLogueado(c);
                acceso = true;
            }
        }
        return acceso;
    }

    @Override
    public boolean verificarAccesoAnunciante(String cedula, String contrasenia) {
        boolean acceso = false;

        for (Anunciante a: getSubasta().getListaAnunciante()) {
            if(a.getCedula().equals(cedula) && a.getUsuario().getContrasenia().equals(contrasenia)){
                getSubasta().setAnuncianteLogueado(a);
                acceso = true;
            }
        }
        return acceso;
    }

    //--------------------------------------- fin Login ----------------------------

    //-------------------------------   PERSISTENCIA ------------------------------------

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }
}
