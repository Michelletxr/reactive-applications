package com.br.project.consumrs;

public class EmailConsumer {

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listerner(){

    }
}
