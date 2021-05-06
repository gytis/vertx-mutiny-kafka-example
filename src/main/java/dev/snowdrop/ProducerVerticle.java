package dev.snowdrop;

import java.time.LocalTime;
import java.util.Map;

import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.kafka.client.producer.KafkaProducer;
import io.vertx.mutiny.kafka.client.producer.KafkaProducerRecord;

public class ProducerVerticle extends AbstractVerticle {

    private final Map<String, String> properties;

    private KafkaProducer<String, String> producer;

    public ProducerVerticle(Map<String, String> properties) {
        this.properties = properties;
    }

    public void start() {
        System.out.println("Staring producer verticle");

        producer = KafkaProducer.create(vertx, properties, String.class, String.class);
        vertx.setPeriodic(1000, id -> {
            String value = "ping " + LocalTime.now();
            System.out.println("Sending: " + value);
            producer.writeAndForget(KafkaProducerRecord.create("ping", value));
        });
    }

    public void stop() {
        if (producer != null) {
            producer.close();
        }
    }
}
