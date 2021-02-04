package resources;

import api.Device;
import db.DeviceDAO;
import energy.kafka.Event;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


@Path("/devices")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {
    DeviceDAO deviceDAO;

    public DeviceResource(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @GET
    public Device getDevice(@QueryParam("device_id") Optional<String> device_id){
        String value = device_id.orElse("1");
        return new Device(value, deviceDAO.getDevice(value));
//        return "Current charging state is " + deviceDAO.getDevice(value);
    }

    @POST
    @Path("/{device_id}")
    public Device addDevice(Event event, @PathParam("device_id") String device_id) {
        System.out.println("Got device id: " + device_id);
        System.out.println("Got charging from payload: " + event.getCharging());

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("schema.registry.url", "http://localhost:8091");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");

        Producer<String, Event> producer = new KafkaProducer<String, Event>(props);

        ProducerRecord producerRecord = new ProducerRecord<String, Event>
                ("events", device_id, event);

        producer.send(producerRecord);

        producer.close();
//        deviceDAO.addDevice(uuid, Boolean.parseBoolean(status.orElse("false")));
        return new Device(device_id, 123);
//        return String.format("Added device %s with status: %s ", uuid, status);
    }
//    @GET
//    @Timed
//    public Saying sayHello(@QueryParam("name") Optional<String> name) {
//        final String value = String.format(template, name.orElse(defaultName));
//        return new Saying(counter.incrementAndGet(), value);
//    }
}
