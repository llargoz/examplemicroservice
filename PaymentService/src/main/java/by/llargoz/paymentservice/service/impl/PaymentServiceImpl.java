package by.llargoz.paymentservice.service.impl;

import by.llargoz.paymentservice.dto.PaymentDto;
import by.llargoz.paymentservice.entity.Payment;
import by.llargoz.paymentservice.grpc.AccountServiceGrpcImpl;
import by.llargoz.paymentservice.repository.PaymentRepository;
import by.llargoz.paymentservice.service.PaymentService;
import by.llargoz.paymentservice.service.kafka.event.PaymentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import static by.llargoz.paymentservice.mapper.PaymentMapper.PAYMENT_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ObjectMapper mapper;

    private final PaymentRepository paymentRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final AccountServiceGrpcImpl accountPaymentService;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Override
    public String createPayment(PaymentDto paymentDto) {
        Payment payment = paymentRepository.save(PAYMENT_MAPPER.toEntity(paymentDto));
        String paymentId = payment.getId().toString();
        accountPaymentService.makePayment(payment.getAmount());
        try {
            String eventMessage = mapper.writeValueAsString(
                    PaymentCreatedEvent.builder()
                            .paymentId(paymentId)
                            .title(payment.getTitle())
                            .amount(payment.getAmount())
                            .paymentDate(payment.getTimestamp())
                            .build()
            );
            CompletableFuture<SendResult<String,String>> future = kafkaTemplate
                    .send(topicName, paymentId, eventMessage);
            future.whenComplete((result, error) -> {
                if (error != null) {
                    log.error("Could not send Kafka payment event", error);
                } else {
                    log.info("Successfully sent Kafka payment event with id {}", paymentId);
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Could not serialize paymentDto", e);
        }

        return paymentId;
    }

}
