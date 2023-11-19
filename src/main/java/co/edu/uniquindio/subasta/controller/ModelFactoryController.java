package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IModelFactoryController;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.*;
import co.edu.uniquindio.subasta.mapping.mappers.MapperSubasta;
import co.edu.uniquindio.subasta.model.*;
import co.edu.uniquindio.subasta.util.Persistencia;
import co.edu.uniquindio.subasta.util.SubastaUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IModelFactoryController, Runnable {
    Subasta subasta;
    MapperSubasta mapper = MapperSubasta.INSTANCE;

    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    Thread hiloServicioConsumer1;
    Thread hiloConsumirPRODUCTO;

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }
    public ModelFactoryController() {

        initRabbitConnection(); //-----------------crea conexion
        System.out.println("invocación clase singleton");
        // crearCopiaDeSeguridad(); // se crea copia del xml una vez inicia el sistema

        //1. inicializar datos y luego guardarlo en archivos

        cargarDatosBase();
        //salvarDatosPrueba();

        //2. Cargar los datos de los archivos
        //cargarDatosDesdeArchivos();

        //3 binario

        //guardarResourceBinario();
        //cargarResourceBinario();

        //4 XML
          guardarResourceXML();
        //cargarResourceXML();


        if (subasta == null) { //Siempre se debe verificar si la raiz del recurso es null
            cargarDatosBase();
        }
        registrarAccionesSistema("Inicio de sistema", 1, "INICIOAPP");
    }




//    ---------------------------------------  Rabbit -----------------------------------


    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecidad");
    }

    public void producirMensaje(String queue, String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consumirMensajesServicio1(){
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }

    public void consumirProducto(){
        hiloConsumirPRODUCTO=new Thread(this);
        hiloConsumirPRODUCTO.start();
    }


    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        if(currentThread == hiloServicioConsumer1){
            consumirMensajes();
        }
        if(currentThread == hiloConsumirPRODUCTO){
            consumirProducto();
        }
    }

    private void consumirMensajes() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(UtilsRabbit.QUEUE_NUEVA_PUBLICACION, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Mensaje recibido: " + message);
                //actualizarEstado(message);
            };
            while (true) {
                channel.basicConsume(UtilsRabbit.QUEUE_NUEVA_PUBLICACION, true, deliverCallback, consumerTag -> { });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                registrarAccionesSistema(c.getNombre() + " inicio sesión.", 1, "INICIO DE SESIÓN");
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
                registrarAccionesSistema(a.getNombre() + " inicio sesión.", 1, "INICIO DE SESIÓN");
                acceso = true;
                producirMensaje(UtilsRabbit.QUEUE_NUEVA_PUBLICACION,"Acceso exitoso");
            }
        }
        return acceso;
    }


    public boolean restaurarLogueo() {

        guardarResourceXML();

        boolean logueado = false;

        if (getSubasta().getAnuncianteLogueado() != null) {
            registrarAccionesSistema(getSubasta().getAnuncianteLogueado().getNombre() + " cerró sesión.", 1, "CIERRE DE SESIÓN.");
            getSubasta().setAnuncianteLogueado(null);
            logueado = true;
        }else if (getSubasta().getCompradorLogueado() != null) {
            registrarAccionesSistema(getSubasta().getCompradorLogueado().getNombre() + " cerró sesion.", 1, "CIERRE DE SESIÓN.");
            getSubasta().setCompradorLogueado(null);
            logueado = true;
        }
        return logueado;
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
        ArrayList<Anunciante> listaAnunciante = new ArrayList<>();
        listaAnunciante = getSubasta().getListaAnunciante();
        return mapper.getListaAnunciante(listaAnunciante);

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
        ArrayList<Anuncio> lista = new ArrayList<>(getSubasta().getListaAnuncios());
        return mapper.getAnunciosDto(lista);
    }

    public List<AnuncioDto> obtenerAnunciosGlobales() {
        ArrayList<Anuncio> lista = new ArrayList<>(getSubasta().getListaAnuncios());
        return mapper.getAnunciosDto(lista);
    }

    @Override
    public String recuperarNombreAnunciante() {
        return getSubasta().getAnuncianteLogueado().getNombre();
    }

    @Override
    public int agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        Anuncio anuncio = mapper.anuncioDtoToAnuncio(anuncioDto);
        int opcion = getSubasta().agregarAnuncio(anuncio);
        if (opcion == 3) {
            guardarResourceXML();
            registrarAccionesSistema("Se agrego un anuncio.", 1, "AGREGAR ANUNCIO");
        }
        return opcion;

    }

    @Override
    public boolean eliminarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        Anuncio anuncio = mapper.anuncioDtoToAnuncio(anuncioDto);
        if (getSubasta().eliminarAnuncio(anuncio)) {
            guardarResourceXML();
            registrarAccionesSistema("Se elimino un anuncio.", 1, "ELIMINAR ANUNCIO");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean actualizarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        Anuncio anuncio = mapper.anuncioDtoToAnuncio(anuncioDto);
        if (getSubasta().actualizarAnuncio(anuncio)) {
            registrarAccionesSistema(getSubasta().getAnuncianteLogueado().getNombre() + " actualizó un anuncio.", 1, "ACTUALIZAR ANUNCIO");
            guardarResourceXML();
            return true;

        } else {
            return false;
        }
    }

//----------------------------Crud Puja---------------------------------------------


    @Override
    public List<PujaDto> obtenerLitaPuja() { //esta en Dto por es la lista que va mostrar
        ArrayList<Puja> listaPuja = new ArrayList<>(getSubasta().getCompradorLogueado().getListaPujas());
        return mapper.getPujaDto(listaPuja);

    }

    @Override
    public CompradorDto salvarCompradorLogueado() {
        return mapper.compradorToCompradorDto(getSubasta().getCompradorLogueado());
    }

    @Override
    public AnuncioDto salvarAnuncioCodigo(String codigo) {
        return mapper.anuncioToAnuncioDto(getSubasta().buscarAnuncioCodigo(codigo));
    }

    @Override
    public boolean realizarPuja(PujaDto pujaDto, String codigo) throws Exception {
        Puja puja = mapper.pujaDtoToPuja(pujaDto);
        if (getSubasta().realizarPuja(puja, codigo)) {
            guardarResourceXML();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean elimnarPuja(PujaDto pujaDto) throws Exception {
        Puja puja = mapper.pujaDtoToPuja(pujaDto);
        if (getSubasta().eliminarPuja(puja)) {
            guardarResourceXML();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AnuncioDto salvarAnuncio(String nombre) {
        for (Anuncio anuncio : getSubasta().getListaAnuncios()) {
            if (anuncio.getNombre().equals(nombre)) {
                return mapper.anuncioToAnuncioDto(anuncio);
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
