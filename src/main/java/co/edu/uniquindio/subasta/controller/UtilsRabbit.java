package co.edu.uniquindio.subasta.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import jakarta.xml.bind.JAXBException;
import javax.xml.bind.JAXB;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class UtilsRabbit {
    public static final String QUEUE_NUEVA_PUBLICACION = "nuevo XML";

    public static final String RUTA_XML = "src/main/resources/persistencia/model.xml";

    public static String convertXmlFileToString() throws IOException {
        File xmlFile = new File(RUTA_XML);
        byte[] fileContent = Files.readAllBytes(xmlFile.toPath());
        return new String(fileContent);
    }

    // Convierte una cadena XML a un archivo
    public static void convertStringToXmlFile(String xmlString, String RUTA_XML) throws IOException {
        File xmlFile = new File(filePath);
        Files.write(xmlFile.toPath(), xmlString.getBytes());
    }
}
