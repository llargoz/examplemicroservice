package by.llargoz.accountservice.controller;

import by.llargoz.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<String> getAccountState() {
        String accountState = accountService.getAccountStatement();
        return ResponseEntity.status(HttpStatus.OK).body(accountState);
    }

}
