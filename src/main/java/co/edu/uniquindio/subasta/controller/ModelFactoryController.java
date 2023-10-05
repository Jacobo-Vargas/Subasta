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
        cargarDatosBase();
    }

    private void cargarDatosBase() {
        subasta = SubastaUtil.cargarDatos();
    }

    @Override
    public List<ProductoDto> obtenerProducto(String cedula) {
        ArrayList<Producto> lista = new ArrayList<>();
        for (Anunciante a : getSubasta().getListaAnunciante()) {
            if (a.getCedula().equals(cedula)) {
                lista.addAll(a.getListaProducto());
                break;
            }
        }
        return mapper.getProductoDto(lista);
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto, String cedula) {
        boolean exito = false;
        Producto producto = mapper.productoDtoToProducto(productoDto);
        for (Anunciante a : getSubasta().getListaAnunciante()) {
            if (a.getCedula().equals(cedula)) {
                a.registrarProducto(producto);
                exito = true;
            }
        }
        return exito;
    }

    @Override
    public boolean eliminarProducto(String cedula) {
        boolean flagExiste = false;
        try {
           flagExiste = getSubasta().eliminarProducto(cedula);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarProducto(String cedulaActual, ProductoDto empleadoDto) {
        return false;
    }
//  --------------------------------------- Registro ---------------------------


    @Override
    public boolean agregarAnunciante(AnuncianteDto anuncianteDto) {
        try{
            if(!(subasta.verificarExistenciaAnunciante(anuncianteDto.cedula()))){
                getSubasta().registrarAnunciante(mapper.anuncianteDtoToAnunciante(anuncianteDto));
            }
            System.out.println(getSubasta().getListaAnunciante().size());
            return true;
        }catch (Exception e){
            return false;
        }

    }
    @Override
    public boolean agregarComprador(CompradorDto compradorDto) {
        try{
            if(!(subasta.verificarExistenciaComprador(compradorDto.cedula()))){
                Comprador c = mapper.compradorDtoToComprador(compradorDto);
                getSubasta().registrarComprador(c);

            }
            System.out.println(getSubasta().getListaCompradores().size());

            return true;
        }catch (Exception e){
            return false;
        }
    }



//   ---------------------------------------     Registro ----------------------
    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }
}
