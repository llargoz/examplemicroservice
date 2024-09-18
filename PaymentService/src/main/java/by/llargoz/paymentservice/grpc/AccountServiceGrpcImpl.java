package by.llargoz.paymentservice.grpc;

import by.llargoz.accountpaymentservice.grpc.PaymentRequest;
import by.llargoz.accountpaymentservice.grpc.PaymentResponse;
import by.llargoz.accountpaymentservice.grpc.PaymentServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountServiceGrpcImpl {
    @GrpcClient("account-service")
    private PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub;

    public void makePayment(double amount) {
        try {
            PaymentRequest request = PaymentRequest.newBuilder()
                    .setAmount(amount)
                    .build();
            log.info("Sending gRPC payment with amount: {}", amount);
            PaymentResponse response = blockingStub.makePayment(request);
            log.info("gRPC request status: {}", response.getStatus());
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }
}
