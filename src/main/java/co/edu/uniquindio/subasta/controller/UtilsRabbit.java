package co.edu.uniquindio.subasta.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;


public class UtilsRabbit implements Runnable {

    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    Thread hiloConsumirXmlComprador;
    Thread hiloConsumirXmlAnunciante;
    Thread hiloConsumirXmlGeneral;




    public static final String QUEUE_NUEVOXML_COMPRADOR = "nuevo XML Comprador";
    public static final String QUEUE_NUEVOXML_ANUNCIANTE = "nuevo XML Anunciante";

    public static final String QUEUE_NUEVOXML_GENERAL = "nuevo XML General";

    public static final String RUTA_XML = "src/main/resources/persistencia/model.xml";





    //---------------------------------------- CONVERTIR XMl TO STRING ----------------------------
    public static String convertXmlFileToString() throws IOException {
        File xmlFile = new File(RUTA_XML);
        byte[] fileContent = Files.readAllBytes(xmlFile.toPath());
        return new String(fileContent);
    }

   // ------------------------------------------ STRING TO XML ----------------------------------
    public static void convertStringToXmlFile(String xmlString, String filePath) throws IOException {
        File xmlFile = new File(filePath);
        Files.write(xmlFile.toPath(), xmlString.getBytes());
    }

    //---------------------------------- GENERICOS RABBIT -------------------------------------------
    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecida");
    }

    public void producirMensaje(String queue, String message) { // este es el que se utiliza para producir el mensaje
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message);
        } catch (Exception e) {
            System.out.println("Error al iniciar conexion con rabbit.");
        }
    }

    public void consumirMensajesComprador1() {
        hiloConsumirXmlComprador = new Thread(this);
        hiloConsumirXmlComprador.start();
    }

    public void consumirMensajesAnunciante1(){
        hiloConsumirXmlAnunciante = new Thread(this);
        hiloConsumirXmlAnunciante.start();
    }
    public void consumirMensajesGeneral1(){
        hiloConsumirXmlGeneral = new Thread(this);
        hiloConsumirXmlGeneral.start();
    }


    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        if (currentThread == hiloConsumirXmlComprador) {
            consumirMensajesComprador();
        }
        if (currentThread == hiloConsumirXmlAnunciante) {
            consumirMensajesAnunciante();
        }
        if (currentThread == hiloConsumirXmlGeneral) {
            consumirMensajesGenericos();
        }
    }

    private void consumirMensajesComprador() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(UtilsRabbit.QUEUE_NUEVOXML_COMPRADOR, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Mensaje recibido: " + message);
            };
            while (true) {
                channel.basicConsume(UtilsRabbit.QUEUE_NUEVOXML_COMPRADOR, true, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            System.out.println("Error al consumir mensaje.");
        }
    }
    private void consumirMensajesGenericos() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(UtilsRabbit.QUEUE_NUEVOXML_COMPRADOR, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Mensaje recibido: " + message);
            };
            while (true) {
                channel.basicConsume(UtilsRabbit.QUEUE_NUEVOXML_COMPRADOR, true, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            System.out.println("Error al consumir mensaje.");
        }
    }
    private void consumirMensajesAnunciante() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(UtilsRabbit.QUEUE_NUEVOXML_COMPRADOR, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Mensaje recibido: " + message);
            };
            while (true) {
                channel.basicConsume(UtilsRabbit.QUEUE_NUEVOXML_COMPRADOR, true, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            System.out.println("Error al consumir mensaje.");
        }
    }
}
