package by.llargoz.accountservice.service;

import by.llargoz.accountpaymentservice.grpc.PaymentRequest;
import by.llargoz.accountpaymentservice.grpc.PaymentResponse;

public interface AccountService {
    PaymentResponse doPayment(PaymentRequest request);
    String getAccountStatement();
}
