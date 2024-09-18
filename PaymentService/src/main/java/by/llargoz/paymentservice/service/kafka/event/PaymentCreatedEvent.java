package by.llargoz.paymentservice.service.kafka.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreatedEvent {

    private String paymentId;

    private String title;

    private Double amount;

    private LocalDateTime paymentDate;

}
