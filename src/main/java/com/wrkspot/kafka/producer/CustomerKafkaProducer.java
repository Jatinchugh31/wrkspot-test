package com.wrkspot.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrkspot.entity.Customer;
import com.wrkspot.service.FailedInstructionService;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@JBossLog
public class CustomerKafkaProducer {

    @Channel("customer-outbound")
    Emitter<String> customerEmitter;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    FailedInstructionService failedInstructionService;

    public void send(Customer customer) {
        log.infof("CustomerKafkaProducer::send >>> enter for customer: %s", customer.getCustomerId());
        try {
            UUID key = customer.getId();
            String value = objectMapper.writeValueAsString(customer);

            KafkaRecord<String, String> message = KafkaRecord.of(key.toString(), value)
                    .withAck(() -> {
                        log.info("Kafka message sent: " + key);
                        return CompletableFuture.completedFuture(null);
                    })
                    .withNack(reason -> {
                        log.errorf("Kafka send failed: %s", reason.getMessage());
                        failedInstructionService.logFailedInstruction(value, "customer-outbound", "PRODUCER", reason);
                        return CompletableFuture.completedFuture(null);
                    });

            customerEmitter.send(message);

        } catch (JsonProcessingException e) {
            failedInstructionService.logFailedInstruction("", "customer-outbound", "PRODUCER", e);
            throw new RuntimeException("Error serializing customer to JSON", e);
        }
    }
}
