package by.llargoz.paymentservice.controller;

import by.llargoz.paymentservice.dto.PaymentDto;
import by.llargoz.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentDto paymentDto) {
        String paymentId = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentId);
    }

}
