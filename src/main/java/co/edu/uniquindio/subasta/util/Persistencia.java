package co.edu.uniquindio.subasta.util;

import co.edu.uniquindio.subasta.exceptions.UsuarioException;
import co.edu.uniquindio.subasta.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persistencia {
    public static final String RUTA_ARCHIVO_ANUNCIANTES = "src/main/resources/persistencia/archivos/archivoAnunciantes.txt";
    public static final String RUTA_ARCHIVO_COMPRADORES = "src/main/resources/persistencia/archivos/archivoCompradores.txt";
    public static final String RUTA_ARCHIVO_PRODUCTOS = "src/main/resources/persistencia/archivos/archivoProductos.txt";

    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivos/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/Subasta_Log.txt";
    public static final String RUTA_ARCHIVO_OBJETOS = "co.edu.uniquindio.subasta/src/main/resources/persistencia/archivoObjetos.txt";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTA_XML = "src/main/resources/persistencia/model.xml";

    public static final String RUTA_ARCHIVO_COPIA_SEGURIDAD_XML = "src/main/resources/persistencia/respaldo/";





//	-------------------------------------------   LOADS       -----------------------------------------------

    private static ArrayList<Producto> procesarCadenaProductos(String cadena) {
        ArrayList<Producto> productos = new ArrayList<>();

        Pattern pattern = Pattern.compile("Producto\\{nombre='(.*?)', codigo='(.*?)', tipoArticulo=(\\w+)}");
        Matcher matcher = pattern.matcher(cadena);

        while (matcher.find()) {
            String nombre = matcher.group(1);
            String codigo = matcher.group(2);
            String tipoArticulo = matcher.group(3);

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setCodigo(codigo);
            producto.setTipoArticulo(TipoArticulo.valueOf(tipoArticulo));
            productos.add(producto);
        }

        return productos;
    }

    public static ArrayList<Anunciante> cargarAnunciantes() throws FileNotFoundException, IOException {
        ArrayList<Anunciante> anunciantes = new ArrayList<Anunciante>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ANUNCIANTES);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            Anunciante anunciante = new Anunciante();
            anunciante.setNombre(linea.split("@@")[0]);
            anunciante.setApellido(linea.split("@@")[1]);
            anunciante.setCedula(linea.split("@@")[2]);
            anunciante.setEdad(Integer.parseInt(linea.split("@@")[3]));
            anunciante.setTelefono(linea.split("@@")[4]);

            Usuario usuario = new Usuario();

            usuario.setUser(linea.split("@@")[5]);
            usuario.setContrasenia(linea.split("@@")[6]);
            anunciante.setUsuario(usuario);

            String productosString = linea.split("@@")[7];
            ArrayList<Producto> productos = procesarCadenaProductos(productosString);

            anunciante.setListaProducto(productos);
            anunciantes.add(anunciante);
        }
        return anunciantes;
    }


    public static ArrayList<Comprador> cargarEmpleados() throws FileNotFoundException, IOException {
        ArrayList<Comprador> compradores = new ArrayList<Comprador>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_COMPRADORES);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Comprador comprador = new Comprador();
            comprador.setNombre(linea.split("@@")[0]);
            comprador.setApellido(linea.split("@@")[1]);
            comprador.setCedula(linea.split("@@")[2]);
            comprador.setEdad(Integer.parseInt(linea.split("@@")[3]));
            comprador.setDireccion(linea.split("@@")[4]);

            Usuario usuario = new Usuario();

            usuario.setUser(linea.split("@@")[5]);
            usuario.setContrasenia(linea.split("@@")[6]);

            comprador.setUsuario(usuario);
            compradores.add(comprador);
        }
        return compradores;
    }

    public static ArrayList<Producto> cargarProductos() throws FileNotFoundException, IOException {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PRODUCTOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Producto producto = new Producto();
            producto.setCodigo(linea.split("@@")[0]);
            producto.setNombre(linea.split("@@")[1]);
            producto.setTipoArticulo(TipoArticulo.valueOf(linea.split("@@")[2]));

        }

        return productos;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }


    public static boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioException {

        if (validarUsuario(usuario, contrasenia)) {
            return true;
        } else {
            throw new UsuarioException("Usuario no existe");
        }

    }

    private static boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);

        for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) {
            Usuario usuarioAux = usuarios.get(indiceUsuario);
            if (usuarioAux.getUser().equalsIgnoreCase(usuario) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Usuario> cargarUsuarios(String ruta) throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(ruta);
        String linea = "";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            Usuario usuario = new Usuario();
            usuario.setUser(linea.split(",")[0]);
            usuario.setContrasenia(linea.split(",")[1]);

            usuarios.add(usuario);
        }
        return usuarios;
    }


//----------------------------------  SAVES  ---------------------------------------------------


public static void cargarDatosArchivos(Subasta subasta) throws FileNotFoundException, IOException {
    //cargar archivo de anunciantes
    ArrayList<Anunciante> anunciantesCargados = cargarAnunciantes();
    if (!anunciantesCargados.isEmpty())
        subasta.getListaAnunciante().addAll(anunciantesCargados);


    //cargar archivos compradores
    ArrayList<Comprador> compradoresCargados = cargarEmpleados();
    if (!compradoresCargados.isEmpty())
        subasta.getListaCompradores().addAll(compradoresCargados);

}

    public static void guardarAnunciantes(ArrayList<Anunciante> listaAnunciante) throws IOException {

        String contenido = "";

        for (Anunciante anunciante : listaAnunciante) {
            contenido += anunciante.getNombre() +
                    "@@" + anunciante.getApellido() +
                    "@@" + anunciante.getCedula() +
                    "@@" + anunciante.getEdad() +
                    "@@" + anunciante.getTelefono() +
                    "@@" + anunciante.getUsuario().getUser() +
                    "@@" + anunciante.getUsuario().getContrasenia() +
                    "@@" + anunciante.getListaProducto() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido, false);
    }

    public static void guardarProductos(ArrayList<Producto> listaProductos) throws IOException {

        String contenido = "";

        for (Producto p : listaProductos) {
            contenido += p.getCodigo() +
                    "@@" + p.getNombre() +
                    "@@" + p.getTipoArticulo() + "<-new->";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS, contenido, false);
    }


    public static void guardarCompradores(ArrayList<Comprador> listaEmpleados) throws IOException {
        String contenido = "";
        for (Comprador comprador : listaEmpleados) {
            contenido += comprador.getNombre() +
                    "@@" + comprador.getApellido() +
                    "@@" + comprador.getCedula() +
                    "@@" + comprador.getEdad() +
                    "@@" + comprador.getTelefono() +
                    "@@" + comprador.getDireccion() +
                    "@@" + comprador.getUsuario().getUser() +
                    "@@" + comprador.getUsuario().getContrasenia() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES, contenido, false);
    }

    public static void guardarObjetos(ArrayList<Producto> listaClientes, String ruta) throws IOException {
        String contenido = "";

        for (Producto productoAux : listaClientes) {
            contenido += productoAux.getNombre() +
                    "@@" + productoAux.getCodigo() +
                    "@@" + productoAux.getTipoArticulo() + "\n";
        }
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }




    //    --------------------------------------  COPIA DE SEGURIDAD -------------------------------------


    public static void crearCopiaSeguridadXML() {

        String nombreArchivoNuevo = generarNombre("model.xml");
        try {

            Path origenPath = Paths.get(RUTA_ARCHIVO_MODELO_SUBASTA_XML);
            Path destinoPath = Paths.get(RUTA_ARCHIVO_COPIA_SEGURIDAD_XML, nombreArchivoNuevo);

            Files.createFile(destinoPath);

            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String generarNombre(String nombreArchivoNuevo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaActual = dateFormat.format(new Date());
        return nombreArchivoNuevo.replace(".xml", "_" + fechaActual + ".xml");
    }



    //    ------------------------------------ SERIALIZACIÓN  y XML --------------------------------------------------------


    public static Subasta cargarRecursoSubastaBinario() {

        Subasta subasta = null;

        try {
            subasta = (Subasta) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return subasta;
    }

    public static void guardarRecursoBancoBinario(Subasta subasta) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO, subasta);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static Subasta cargarRecursoSubastaXML() {

        Subasta subasta = null;

        try {
            subasta = (Subasta) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_SUBASTA_XML);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return subasta;

    }


    public static void guardarRecursoSubastaXML(Subasta subasta) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_SUBASTA_XML, subasta);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
