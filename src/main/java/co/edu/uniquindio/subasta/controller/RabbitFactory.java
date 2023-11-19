package co.edu.uniquindio.subasta.controller;

import com.rabbitmq.client.ConnectionFactory;
public class RabbitFactory {
    private final ConnectionFactory connectionFactory;
    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(5672);
        this.connectionFactory.setUsername("guest");
        this.connectionFactory.setPassword("guest");
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

}
