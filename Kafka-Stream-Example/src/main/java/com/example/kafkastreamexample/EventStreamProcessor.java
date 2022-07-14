package com.example.kafkastreamexample;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EventStreamProcessor {
    private final StreamsBuilder streamsBuilder;

    public EventStreamProcessor(StreamsBuilder streamsBuilder) {
        this.streamsBuilder = streamsBuilder;
    }

    @PostConstruct
    public void streamTopology() {
        KStream<String, String> kStream = streamsBuilder
                .stream("orders", Consumed.with(Serdes.String(), Serdes.String()));
        kStream.filter((key, value) -> value.startsWith("Message_"))
                .mapValues((k, v) -> v.toUpperCase()).peek((k, v) -> System.out.println("Key : " + k + " Value : " + v))
                .to("payment-orders", Produced.with(Serdes.String(), Serdes.String()));
    }
}
