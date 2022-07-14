package com.kafka.orderservice.stream;

import com.kafka.domain.Order;
import com.kafka.orderservice.service.OrderManageService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
public class StreamOrder {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamOrder.class);
    private final OrderManageService orderManageService;
    private final StreamsBuilder streamsBuilder;

    public StreamOrder(OrderManageService orderManageService, StreamsBuilder streamsBuilder) {
        this.orderManageService = orderManageService;
        this.streamsBuilder = streamsBuilder;
    }

    @PostConstruct
    private void stream() {
        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
        KStream<Long, Order> stream = streamsBuilder.stream("payment-orders", Consumed.with(Serdes.Long(), orderSerde));

        stream.join(
                        streamsBuilder.stream("stock-orders"),
                        orderManageService::confirm, JoinWindows.of(Duration.ofSeconds(10)),
                        StreamJoined.with(Serdes.Long(), orderSerde, orderSerde))
                .peek((k, o) -> LOGGER.info("Output: {}", o)).to("orders");
    }

    @PostConstruct
    private void table() {
        KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore("orders");
        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
        KStream<Long, Order> stream = streamsBuilder.stream("orders", Consumed.with(Serdes.Long(), orderSerde));
        stream.toTable(Materialized.<Long, Order>as(store).withKeySerde(Serdes.Long()).withValueSerde(orderSerde));
    }
}
