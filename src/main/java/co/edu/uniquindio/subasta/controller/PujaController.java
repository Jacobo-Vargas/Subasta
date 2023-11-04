package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IPujaControllerService;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public class PujaController implements IPujaControllerService {
    ModelFactoryController modelFactoryController;

    public PujaController() {
        modelFactoryController=ModelFactoryController.getInstance();
    }

    @Override
    public List<PujaDto> obtenerLitaPuja() {
        return modelFactoryController.obtenerLitaPuja();
    }

    @Override
    public boolean realizarPuja(PujaDto pujaDto, String codigo) throws Exception {
        return modelFactoryController.realizarPuja(pujaDto,codigo);
    }

    @Override
    public boolean elimnarPuja(PujaDto pujaDto) throws Exception {
        return modelFactoryController.elimnarPuja(pujaDto);
    }

    @Override
    public boolean actulizarPuja(PujaDto pujaDto) throws Exception {
        return modelFactoryController.actulizarPuja(pujaDto);
    }

    @Override
    public List<AnuncioDto> obtenerListaNuncio() {
        return modelFactoryController.obtenerListaNuncio();
    }

    @Override
    public Anuncio salvarAnuncio(String nombre) {
        return modelFactoryController.salvarAnuncio(nombre);
    }
}
