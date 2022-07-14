package com.kafka.paymentservice.stream;

import com.kafka.domain.Order;
import com.kafka.paymentservice.domain.Reservation;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class StreamPayment {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamPayment.class);
    private final KafkaTemplate<Long, Order> kafkaTemplate;
    private final StreamsBuilder streamsBuilder;
    private Random random = new Random();

    public StreamPayment(KafkaTemplate<Long, Order> kafkaTemplate, StreamsBuilder streamsBuilder) {
        this.kafkaTemplate = kafkaTemplate;
        this.streamsBuilder = streamsBuilder;
    }

    @PostConstruct
    private void stream() {
        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
        JsonSerde<Reservation> rsvSerde = new JsonSerde<>(Reservation.class);
        KStream<Long, Order> stream = streamsBuilder
                .stream("orders", Consumed.with(Serdes.Long(), orderSerde))
                .peek((k, order) -> LOGGER.info("New: {}", order));

        KeyValueBytesStoreSupplier customerOrderStoreSupplier = Stores.persistentKeyValueStore("customer-orders");


        Aggregator<Long, Order, Reservation> aggregatorService = (id, order, rsv) -> {
            switch (order.getStatus()) {
                case "CONFIRMED":
                    rsv.setAmountReserved(rsv.getAmountReserved() - order.getPrice());
                    break;
                case "ROLLBACK":
                    if (!order.getSource().equals("PAYMENT")) {
                        rsv.setAmountAvailable(rsv.getAmountAvailable() + order.getPrice());
                        rsv.setAmountReserved(rsv.getAmountReserved() - order.getPrice());
                    }
                    break;
                case "NEW":
                    if (order.getPrice() <= rsv.getAmountAvailable()) {
                        rsv.setAmountAvailable(rsv.getAmountAvailable() - order.getPrice());
                        rsv.setAmountReserved(rsv.getAmountReserved() + order.getPrice());
                        order.setStatus("ACCEPT");
                    } else {
                        order.setStatus("REJECT");
                    }
                    kafkaTemplate.send("payment-orders", order.getId(), order);
                    break;
            }
            LOGGER.info("{}", rsv);
            return rsv;
        };
        stream.selectKey((k, v) -> v.getCustomerId())
                .groupByKey(Grouped.with(Serdes.Long(), orderSerde))
                .aggregate(() -> new Reservation(random.nextInt()), aggregatorService,
                        Materialized.<Long, Reservation>as(customerOrderStoreSupplier)
                                .withKeySerde(Serdes.Long())
                                .withValueSerde(rsvSerde)).toStream().peek((k, trx) -> LOGGER.info("Commit: {}", trx));
    }
}
