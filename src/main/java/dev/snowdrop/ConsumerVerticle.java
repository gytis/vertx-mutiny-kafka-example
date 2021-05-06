package dev.snowdrop;

import java.util.Map;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.kafka.client.consumer.KafkaConsumer;

public class ConsumerVerticle extends AbstractVerticle {

    private final Map<String, String> properties;

    private KafkaConsumer<String, String> consumer;

    public ConsumerVerticle(Map<String, String> properties) {
        this.properties = properties;
    }

    public Uni<Void> asyncStart() {
        System.out.println("Staring consumer verticle");

        consumer = KafkaConsumer.create(vertx, properties, String.class, String.class);
        return consumer
                .partitionsAssignedHandler(consumer::seekToBeginningAndForget)
                .handler(record -> System.out.println(record.topic() + ": " + record.value()))
                .subscribe("ping");
    }

    public void stop() {
        if (consumer != null) {
            consumer.close();
        }
    }
}
