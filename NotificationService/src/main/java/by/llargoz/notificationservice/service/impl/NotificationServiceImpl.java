package by.llargoz.notificationservice.service.impl;

import by.llargoz.notificationservice.entity.Notification;
import by.llargoz.notificationservice.repository.NotificationRepository;
import by.llargoz.notificationservice.service.NotificationService;
import by.llargoz.notificationservice.service.kafka.event.PaymentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final ObjectMapper mapper;

    private final StringBuilder titleBuilder = new StringBuilder();

    private final StringBuilder bodyBuilder = new StringBuilder();

    @Override
    public void saveNotification(String eventMessage) {
        log.info("Saving Kafka notification {}", eventMessage);
        try {
            PaymentCreatedEvent event = mapper.readValue(eventMessage, PaymentCreatedEvent.class);
            titleBuilder.append("Payment: ").append(event.getTitle());
            bodyBuilder
                    .append("Amount: ").append(event.getAmount())
                    .append(" Date: ").append(event.getPaymentDate());
            Notification notification = Notification.builder()
                    .title(titleBuilder.toString())
                    .body(bodyBuilder.toString())
                    .build();
            notificationRepository.save(notification);
            titleBuilder.setLength(0);
            bodyBuilder.setLength(0);
            log.info("Kafka notification saved with id {}", notification.getId());
        } catch (JsonProcessingException e) {
            log.error("Could not parse json event", e);
        }
    }

}
