package by.llargoz.notificationservice.service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PaymentCreatedEvent {

    private String paymentId;

    private String title;

    private Double amount;

    private LocalDateTime paymentDate;

}
