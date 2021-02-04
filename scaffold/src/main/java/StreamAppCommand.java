
import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class StreamAppCommand extends Command {

    protected StreamAppCommand() {
        super("stream", "Command to launch Kafka Stream app");
    }

    @Override
    public void configure(Subparser subparser) {
    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {
        System.out.println("Launching stream app " + "user xiong");
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "energy_kafka_stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");


        StreamsConfig streamsConfig = new StreamsConfig(props);

        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> simpleFirstStream = builder.stream("events",
                Consumed.with(stringSerde, stringSerde));

//        KStream<String, String> upperCasedStream =
//                simpleFirstStream.mapValues(String::toUpperCase);
//
//        upperCasedStream.to( "out-topic",
//                Produced.with(stringSerde, stringSerde));
//        simpleFirstStream.to("test")
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);

        kafkaStreams.start();
        Thread.sleep(35000);
        System.out.println("Shutting down the streaming APP now");
        kafkaStreams.close();
    }
}
