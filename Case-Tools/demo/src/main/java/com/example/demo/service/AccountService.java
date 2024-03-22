package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public Account deposit(Long id, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public Account withdraw(Long id, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            double currentBalance = account.getBalance();
            if (currentBalance >= amount) {
                double newBalance = currentBalance - amount;
                account.setBalance(newBalance);
                return accountRepository.save(account);
            } else {
                throw new RuntimeException("Insufficient funds");
            }
        } else {
            throw new RuntimeException("Account not found");
        }
    }
}
