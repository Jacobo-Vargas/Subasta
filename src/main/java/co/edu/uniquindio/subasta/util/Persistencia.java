package co.edu.uniquindio.subasta.util;

import co.edu.uniquindio.subasta.exceptions.UsuarioException;
import co.edu.uniquindio.subasta.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {
    public static final String RUTA_ARCHIVO_ANUNCIANTES = "src/main/resources/persistencia/archivoAnunciantes.txt";
    public static final String RUTA_ARCHIVO_COMPRADORES = "src/main/resources/persistencia/archivoCompradores.txt";
    public static final String RUTA_ARCHIVO_PRODUCTOS = "src/main/resources/persistencia/archivoProductos.txt";

    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/SubastaLog.txt";
    public static final String RUTA_ARCHIVO_OBJETOS = "co.edu.uniquindio.subasta/src/main/resources/persistencia/archivoObjetos.txt";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTA_XML = "src/main/resources/persistencia/model.xml";


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
                    "," + anunciante.getApellido() +
                    "," + anunciante.getCedula() +
                    "," + anunciante.getEdad() +
                    "," + anunciante.getTelefono() +
                    "," + anunciante.getUsuario().getUser() +
                    "," + anunciante.getUsuario().getContrasenia() +
                    "," + anunciante.getListaProducto() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido, false);
    }
    public static void guardarProductos(ArrayList<Producto> listaProductos)throws IOException{

        String contenido = "";

        for (Producto p: listaProductos) {
            contenido += p.getCodigo() +
                    "," + p.getNombre() +
                    "," + p.getTipoArticulo() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS,contenido,false);
    }


    public static void guardarCompradores(ArrayList<Comprador> listaEmpleados) throws IOException {
        String contenido = "";
        for (Comprador comprador : listaEmpleados) {
            contenido += comprador.getNombre() +
                    "," + comprador.getApellido() +
                    "," + comprador.getCedula() +
                    "," + comprador.getEdad() +
                    "," + comprador.getTelefono() +
                    "," + comprador.getDireccion() +
                    "," + comprador.getUsuario().getUser() +
                    "," + comprador.getUsuario().getContrasenia() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES, contenido, false);
    }


//	----------------------LOADS------------------------

    public static ArrayList<Anunciante> cargarAnunciantes() throws FileNotFoundException, IOException {
        ArrayList<Anunciante> anunciantes = new ArrayList<Anunciante>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ANUNCIANTES);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);//juan,arias,125454,Armenia,uni1@,12454,125444

            Anunciante anunciante = new Anunciante();
            anunciante.setNombre(linea.split(",")[0]);
            anunciante.setApellido(linea.split(",")[1]);
            anunciante.setCedula(linea.split(",")[2]);
            anunciante.setEdad(Integer.parseInt(linea.split(",")[3]));
            anunciante.setTelefono(linea.split(",")[4]);

            Usuario usuario = new Usuario();

            usuario.setUser(linea.split(",")[5]);
            usuario.setContrasenia(linea.split(",")[6]);
            anunciante.setUsuario(usuario);

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
            comprador.setNombre(linea.split(",")[0]);
            comprador.setApellido(linea.split(",")[1]);
            comprador.setCedula(linea.split(",")[2]);
            comprador.setEdad(Integer.parseInt(linea.split(",")[3]));
            comprador.setDireccion(linea.split(",")[4]);

            Usuario usuario = new Usuario();

            usuario.setUser(linea.split(",")[5]);
            usuario.setContrasenia(linea.split(",")[6]);

            comprador.setUsuario(usuario);
            compradores.add(comprador);
        }
        return compradores;
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


//	----------------------SAVES------------------------

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     *
     * @param
     * @throws IOException
     */

//    public static void guardarObjetos(ArrayList<Cliente> listaClientes, String ruta) throws IOException {
//        String contenido = "";
//
//        for (Cliente clienteAux : listaClientes) {
//            contenido += clienteAux.getNombre() + "," + clienteAux.getApellido() + "," + clienteAux.getCedula() + clienteAux.getDireccion()
//                    + "," + clienteAux.getCorreo() + "," + clienteAux.getFechaNacimiento() + "," + clienteAux.getTelefono() + "\n";
//        }
//        ArchivoUtil.guardarArchivo(ruta, contenido, true);
//    }


    //------------------------------------SERIALIZACIÓN  y XML
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
