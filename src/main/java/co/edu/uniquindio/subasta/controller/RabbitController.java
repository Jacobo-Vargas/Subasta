package co.edu.uniquindio.subasta.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;

public class RabbitController{

    static ModelFactoryController modelFactoryController;

    public RabbitController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean enviarMensajeRabbit(String cola, String objeto){
        return modelFactoryController.producirMensaje(cola,objeto);
    }

    public static void hiloCompras(String cola){
        Thread hilocompras = new Thread(()->{
            consumirMensajes(cola);
        });
        hilocompras.start();
    }

    private static void consumirMensajes(String cola) {
        try {
            Connection connection = modelFactoryController.connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(cola, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                JOptionPane.showMessageDialog(null,message);
            };
            while (true) {
                channel.basicConsume(cola, true, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            System.out.println("Error al consumir mensaje.");
        }
    }
}
