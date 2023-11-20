package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class VisorAnunciosRealizarPujaViewController {


    ArrayList<AnuncioDto> anunciosDto = new ArrayList<>();
    AnuncioController anuncioController;
    int posicion;
    @FXML
    private Label lblFin;

    @FXML
    private Label lblNumOfertas;

    @FXML
    private Label lblOfertaMaxima;

    @FXML
    private Label lblPrecio;

    @FXML
    private ImageView imagenAnuncio;
    private TabPane tabPane;

    @FXML
    void anteriorAnuncio() {
        if (posicion == 0) {
            posicion = anunciosDto.size() - 1;
        } else {
            posicion -= 1;
        }
        mostrarAnuncios(posicion);
    }

    @FXML
    void siguienteAnuncio() {
        if (posicion == anunciosDto.size() - 1) {
            posicion = 0;
        } else {
            posicion += 1;
        }
        mostrarAnuncios(posicion);

    }

    public void initialize() {
        posicion = 0;
        anuncioController = new AnuncioController();
        obtenerAnuncios();
        mostrarAnuncios(posicion);
    }

    public void obtenerAnuncios() {
        anunciosDto = (ArrayList<AnuncioDto>) anuncioController.obtenerAnunciosGlobales();
    }

    public void mostrarAnuncios(int posicion) {
        AnuncioDto anuncioDto = anunciosDto.get(posicion);

        // mapea la lista a ofertas y luego compara para obtener el mayor de esas ofertas
        float oferta =anuncioDto
                .listaPujas()
                .stream()
                .map(PujaDto::oferta)
                .max(Float::compare)
                .orElse(0.0F);


        lblFin.setText(anuncioDto.fechaTerminacion());
        lblNumOfertas.setText(String.valueOf(anuncioDto.listaPujas().size()));
        lblOfertaMaxima.setText(String.valueOf(oferta));
        lblPrecio.setText(String.format("%.1f", anuncioDto.valorInicial()));
        imagenAnuncio.setImage(mostrarFoto(anuncioDto.foto()));
        efecto();
    }

    public Image mostrarFoto(String nombreArchivo) {
        /*
         * esto crea un arreglo de archivos que esten en la carpeta mencionada
         *  luego itera la lista y si encuentra uno con el mismo nombre lo retorna
         */
        Image imagen = null;
        File carpeta = new File("src/main/resources/imagenesAnuncios/");

        File[] archivos = carpeta.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().equals(nombreArchivo)) {
                    String rutaImagen = archivo.toURI().toString();
                    imagen = new Image(rutaImagen);
                }
            }
        }
        return imagen;
    }

    public void efecto() {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), imagenAnuncio);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }


    public void realizarPuja(ActionEvent actionEvent) {
        abrirVentanaUno("src/main/resources/co/edu/uniquindio/subasta/VentanaEmergenteUno.fxml","Pujar");
    }


    private void abrirVentanaUno(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root;

            try {
                root= loader.load() ;
                VentanaEmergenteUnoController ventanaEmergenteUnoController =loader.getController();
                ventanaEmergenteUnoController.recibirCodigo(anunciosDto.get(posicion).codigo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.initModality(Modality.APPLICATION_MODAL); // Para que sea modal

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void abrirVentanaDos(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root;

            try {
                root= loader.load() ;
                VentanaEmergenteDosController ventanaEmergenteDosController =loader.getController();
                ventanaEmergenteDosController.recibirCodigo(anunciosDto.get(posicion).codigo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.initModality(Modality.APPLICATION_MODAL); // Para que sea modal

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void numeroPujas() {
        abrirVentanaDos("src/main/resources/co/edu/uniquindio/subasta/VentanaEmergenteDos.fxml","Ver pujas");
    }
}

