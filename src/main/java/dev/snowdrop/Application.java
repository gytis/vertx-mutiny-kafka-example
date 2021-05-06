package dev.snowdrop;

import java.util.HashMap;
import java.util.Map;

import io.vertx.mutiny.core.Vertx;

public class Application {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticleAndAwait(new ProducerVerticle(getProducerProperties()));
        vertx.deployVerticleAndAwait(new ConsumerVerticle(getConsumerProperties()));
    }

    private static Map<String, String> getProducerProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return properties;
    }

    private static Map<String, String> getConsumerProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("group.id", "ping_group");

        return properties;
    }
}
