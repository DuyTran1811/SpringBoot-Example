package com.example.kafkastream;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

@Component
@EnableKafkaStreams
public class KafkaStreams {
    public static final String OUTPUT_TOPIC_NAME = "even-number-topic";
    public static final String INPUT_TOPIC_NAME = "number-topic";

    @Bean
    public KStream<String, String> evenNumbersStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> input = kStreamBuilder.stream(INPUT_TOPIC_NAME);

        KStream<String, String> output = input.filter((key, value) -> key.equals("Even"));

        output.to(OUTPUT_TOPIC_NAME);
        return output;
    }
}
