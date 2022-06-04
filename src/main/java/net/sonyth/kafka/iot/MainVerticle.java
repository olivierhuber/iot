package net.sonyth.kafka.iot;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class MainVerticle extends AbstractVerticle {
  public static final String IOT_SENSOR_TOPIC = "IoT";

  @Override
  public void start() {
    Map<String, String> config = new HashMap<>();
    config.put("bootstrap.servers", "kafka-server.aivencloud.com:99999");
    config.put("key.serializer", "io.vertx.kafka.client.serialization.JsonObjectSerializer");
    config.put("value.serializer", "io.vertx.kafka.client.serialization.JsonObjectSerializer");
    config.put("security.protocol", "SSL");
    config.put("ssl.truststore.location", "client.truststore.jks");
    config.put("ssl.truststore.password", "secret");
    config.put("ssl.keystore.type", "PKCS12");
    config.put("ssl.keystore.location", "client.keystore.p12");
    config.put("ssl.keystore.password", "secret");
    config.put("ssl.key.password", "secret");

    KafkaProducer<JsonObject, JsonObject> producer = KafkaProducer.create(vertx, config);

    producer.exceptionHandler(e -> System.err.println("Error = " + e.getMessage()));

    for (int i = 0; i < 5; i++) {
      producer.write(KafkaProducerRecord.create(IOT_SENSOR_TOPIC, MockIoT.newId(), MockIoT.newPayload()));
    }

    producer
      .close()
      .onSuccess(v -> System.out.println("Producer closed"))
      .onFailure(cause -> System.err.println("Close failed: " + cause));
  }
}
