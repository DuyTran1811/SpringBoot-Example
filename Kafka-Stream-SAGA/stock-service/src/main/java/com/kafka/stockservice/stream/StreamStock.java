package com.kafka.stockservice.stream;

import com.kafka.domain.Order;
import com.kafka.stockservice.domain.Reservation;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;

import javax.annotation.PostConstruct;
import java.util.Random;

@Configuration
public class StreamStock {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamStock.class);
    private final KafkaTemplate<Long, Order> kafkaTemplate;
    private final StreamsBuilder streamsBuilder;
    private Random random = new Random();

    public StreamStock(KafkaTemplate<Long, Order> kafkaTemplate, StreamsBuilder streamsBuilder) {
        this.kafkaTemplate = kafkaTemplate;
        this.streamsBuilder = streamsBuilder;
    }

    @PostConstruct
    public void stream() {
        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
        JsonSerde<Reservation> rsvSerde = new JsonSerde<>(Reservation.class);
        KStream<Long, Order> stream = streamsBuilder.stream("orders", Consumed.with(Serdes.Long(), orderSerde))
                .peek((k, order) -> LOGGER.info("New: {}", order.toString()));

        KeyValueBytesStoreSupplier stockOrderStoreSupplier = Stores.persistentKeyValueStore("stock-orders");

        Aggregator<Long, Order, Reservation> aggrSrv = (id, order, rsv) -> {
            switch (order.getStatus()) {
                case "CONFIRMED":
                    rsv.setItemsReserved(rsv.getItemsReserved() - order.getProductCount());
                    break;
                case "ROLLBACK":
                    if (!order.getSource().equals("STOCK")) {
                        rsv.setItemsAvailable(rsv.getItemsAvailable() + order.getProductCount());
                        rsv.setItemsReserved(rsv.getItemsReserved() - order.getProductCount());
                    }
                    break;
                case "NEW":
                    if (order.getProductCount() <= rsv.getItemsAvailable()) {
                        rsv.setItemsAvailable(rsv.getItemsAvailable() - order.getProductCount());
                        rsv.setItemsReserved(rsv.getItemsReserved() + order.getProductCount());
                        order.setStatus("ACCEPT");
                    } else {
                        order.setStatus("REJECT");
                    }
                    kafkaTemplate.send("stock-orders", order.getId(), order)
                            .addCallback(result -> LOGGER.info("Sent: {}", result != null ? result.getProducerRecord().value() : null), ex -> {
                            });

                    LOGGER.info("{}", rsv);
                    break;
            }
            return rsv;
        };
        stream.selectKey((k, v) -> v.getProductId())
                .groupByKey(Grouped.with(Serdes.Long(), orderSerde))
                .aggregate(() -> new Reservation(random.nextInt()), aggrSrv, Materialized.<Long, Reservation>as(stockOrderStoreSupplier)
                                .withKeySerde(Serdes.Long())
                                .withValueSerde(rsvSerde)).toStream().peek((k, trx) -> LOGGER.info("Commit: {}", trx));
    }
}
