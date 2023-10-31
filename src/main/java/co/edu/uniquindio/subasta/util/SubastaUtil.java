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


        Usuario usuario=new Usuario();
        usuario.setContrasenia("123456789");
        usuario.setContrasenia("123456789");

        Comprador c=new Comprador();
        c.setNombre("juan pablo");
        c.setApellido("buitrago");
        c.setCedula("123456789");
        c.setEdad(19);
        c.setTelefono("3128638699");
        c.setDireccion("Barrio quinio");
        c.setUsuario(usuario);


        Usuario usuario2=new Usuario();
        usuario2.setContrasenia("1234567890");
        usuario2.setContrasenia("1234567890");

        Comprador  c2=new Comprador();
        c2.setNombre("lisa");
        c2.setApellido("Maya");
        c2.setCedula("1234567890");
        c2.setEdad(19);
        c2.setTelefono("21312");
        c2.setDireccion("terranova");
        c2.setUsuario(usuario2);

        subasta.getListaCompradores().add(c);
        subasta.getListaCompradores().add(c2);

        Anuncio anuncio=new Anuncio();
        anuncio.setNombre("tv");
        anuncio.setProducto(producto1);
        anuncio.setFechaPublicacion("hola mundo");
        anuncio.setDescripcion("tv en venta");
        anuncio.setProducto(producto);

        a1.getListaAnucio().add(anuncio);
         Puja puja=new Puja();
         puja.setDireccion("casa 20");
         puja.setCodigo("1");
         puja.setOfertaInicial(2);
         puja.setAnuncio(anuncio);
         //c.getListaPujas().add(puja);



        return subasta;


    }
}
