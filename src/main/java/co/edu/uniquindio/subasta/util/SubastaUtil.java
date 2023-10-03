package co.edu.uniquindio.subasta.util;

import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Producto;
import co.edu.uniquindio.subasta.model.Subasta;
import co.edu.uniquindio.subasta.model.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public  class SubastaUtil {
    public SubastaUtil() {
    }


    public static Subasta cargarDatos() {

        Usuario u1 = new Usuario();
        u1.setUser("123");
        u1.setContrasenia("123");

        Usuario u2 = new Usuario();
        u2.setUser("321");
        u2.setContrasenia("321");

        Subasta subasta = new Subasta();

        Anunciante a1= new Anunciante();
        a1.setNombre("juan");
        a1.setCedula("123");
        a1.setUsuario(u1);

        Anunciante a2 = new Anunciante();
        a2.setNombre("jose");
        a2.setCedula("321");
        a2.setUsuario(u2);


        Producto producto = new Producto();

        producto.setNombre("Casa");
        a1.registrarProducto(producto);
        subasta.registrarAnunciante(a1);

        Producto producto1 = new Producto();

        producto1.setNombre("tv");
        a2.registrarProducto(producto1);
        subasta.registrarAnunciante(a2);


        return subasta;


    }

    public void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    public boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
