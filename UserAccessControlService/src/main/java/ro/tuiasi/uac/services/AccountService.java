package ro.tuiasi.uac.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.AccountServiceInterface;
import ro.tuiasi.uac.interfaces.HashingServiceInterface;
import ro.tuiasi.uac.models.Account;
import ro.tuiasi.uac.repositories.AccountRepository;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    HashingServiceInterface hashingService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByCredentials(String email, String password) {
        return this.accountRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Account addAccount(Account account) {
        account.setPassword(hashingService.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(int id) {
        accountRepository.deleteById(id);
    }
}
