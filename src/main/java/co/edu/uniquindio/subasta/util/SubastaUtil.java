package co.edu.uniquindio.subasta.util;

import co.edu.uniquindio.subasta.model.*;
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
        producto.setCodigo("0");
        producto.setTipoArticulo(TipoArticulo.DEPORTES);
        a1.getListaProducto().add(producto);
        subasta.getListaAnunciante().add(a1);

        Producto producto1 = new Producto();

        producto1.setNombre("tv");
        producto1.setCodigo("1");
        producto1.setTipoArticulo(TipoArticulo.TECNOLOGIA);
        a2.getListaProducto().add(producto1);
        subasta.getListaAnunciante().add(a2);




        return subasta;


    }
}
