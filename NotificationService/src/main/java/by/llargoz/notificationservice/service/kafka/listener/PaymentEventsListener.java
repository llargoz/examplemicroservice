package by.llargoz.notificationservice.service.kafka.listener;

import by.llargoz.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventsListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void createListen(String event) {
        notificationService.saveNotification(event);
    }

}
