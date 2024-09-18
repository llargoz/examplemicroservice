package by.llargoz.accountservice.grpc;

import by.llargoz.accountpaymentservice.grpc.PaymentRequest;
import by.llargoz.accountpaymentservice.grpc.PaymentResponse;
import by.llargoz.accountpaymentservice.grpc.PaymentServiceGrpc;
import by.llargoz.accountservice.service.AccountService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AccountServiceGrpcImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    private final AccountService accountService;

    @Override
    public void makePayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        log.info("received gRPC makePayment request");
        PaymentResponse response = accountService.doPayment(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.info("send grpc makePayment response {}", response.getStatus());
    }

}
