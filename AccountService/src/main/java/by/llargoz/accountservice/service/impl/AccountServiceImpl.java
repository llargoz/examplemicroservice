package by.llargoz.accountservice.service.impl;

import by.llargoz.accountpaymentservice.grpc.PaymentRequest;
import by.llargoz.accountpaymentservice.grpc.PaymentResponse;
import by.llargoz.accountservice.entity.Account;
import by.llargoz.accountservice.repository.AccountRepository;
import by.llargoz.accountservice.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public PaymentResponse doPayment(PaymentRequest request) {
        Optional<Account> currentAccountState = accountRepository.findFirstByOrderByCreatedAtDesc();
        double balance = 0.0;
        if (currentAccountState.isPresent()) {
            balance = currentAccountState.get().getBalance();
        }
        Account account = Account.builder()
                .balance(balance + request.getAmount())
                .debit(0.0)
                .credit(request.getAmount())
                .build();
        accountRepository.save(account);
        return PaymentResponse.newBuilder()
                .setStatus("ACCEPTED")
                .build();
    }

    @Override
    public String getAccountStatement() {
        List<Account> accounts = accountRepository.findAllByCreatedAtGreaterThanEqualOrderByCreatedAt(
                LocalDateTime.now().with(LocalTime.MIN)
        );
        String json = null;
        try {
            json = objectMapper.writeValueAsString(accounts);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return json;
    }

}
