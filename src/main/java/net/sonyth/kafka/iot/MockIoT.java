package net.sonyth.kafka.iot;

import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class MockIoT {
  enum LampState {
    ON, OFF
  }

  enum DoorState {
    OPEN, CLOSED, LOCKED
  }

  static Integer motionSensor = 0;

  public static JsonObject newId() {
    return new JsonObject().put("id", UUID.randomUUID());
  }

  public static JsonObject newPayload() {
    JsonObject payload = new JsonObject();
    payload.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    payload.put("lamp", LampState.values()[new Random().nextInt(LampState.values().length)].name());
    payload.put("door", DoorState.values()[new Random().nextInt(DoorState.values().length)].name());
    motionSensor += new Random().nextInt(5);
    payload.put("motion", motionSensor);
    return payload;
  }

}
