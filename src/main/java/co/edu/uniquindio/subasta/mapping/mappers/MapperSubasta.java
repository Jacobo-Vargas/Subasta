package co.edu.uniquindio.subasta.mapping.mappers;

import co.edu.uniquindio.subasta.mapping.dto.*;
import co.edu.uniquindio.subasta.model.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapperSubasta {
    MapperSubasta INSTANCE= Mappers.getMapper(MapperSubasta.class);

    @Named("anuncianteToAnuncianteDto")
    AnuncianteDto anuncianteToAnuncianteDto(Anunciante anunciante);
    Anunciante anuncianteDtoToAnunciante(AnuncianteDto anuncianteDto);

    @Named("compradorToCompradorDto")
    CompradorDto compradorToCompradorDto(Comprador comprador);
    Comprador compradorDtoToComprador(CompradorDto compradorDto);

    @Named("productoToProductoDto")
    ProductoDto productoToProductoDto(Producto producto);
    Producto productoDtoToProducto(ProductoDto productoDto);

    @Named("anuncioToAnuncioDto")
    AnuncioDto anuncioToAnuncioDto(Anuncio anuncio);
    Anuncio anuncioDtoToAnuncio(AnuncioDto anuncioDto);

    @Named("pujaToPujaDto")
    PujaDto pujaToPujaDto(Puja puja);
    Puja pujaDtoToPuja(PujaDto pujaDto);


    @IterableMapping(qualifiedByName = "anuncioToAnuncioDto")
    List<AnuncioDto> getAnunciosDto(List<Anuncio> listaAnuncios);

    @IterableMapping(qualifiedByName = "productoToProductoDto")
    List<ProductoDto> getProductoDto(List<Producto> listaProductos);

    @IterableMapping(qualifiedByName = "pujaToPujaDto")
    List<PujaDto> getPujaDto(List<Puja> listaPujas);


}
